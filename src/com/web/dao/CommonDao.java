package com.web.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.base.util.ArrayUtil;
import com.web.dao.page.IPage;




/**
 * 数据库操作的工具类 <br>
 * note:特定数据库的sql需要控制在各个dao中 <br>
 * 对jdbc的轻量封装
 * 
 * @author teddy_wu
 * 
 */
public class CommonDao implements IDao{

	private static Logger logger = LoggerFactory.getLogger(CommonDao.class);


	// 页属性
//	@Autowired
	private IPage page;

	// 连接管理器
	private static ConnectionProvider connectionProvider;

	// 每个线程共享一个连接
	private static ThreadLocal<Connection> connWrapper = new ThreadLocal<Connection>();

	
	public CommonDao() {
	}

	// /////////////////////////
	// 默认连接的处理方法
	public static Connection getConnection() throws Exception {

		Connection connection = connWrapper.get();
		if (connection != null && !connection.isClosed()) {
			return connection;
		}

		// 取连接并放入线程属性域中
		connection = connectionProvider.getConnection();
		if (connection == null) {
			throw new SQLException("无法获取数据库连接");
		}
		connWrapper.set(connection);
		return connection;
	}

	// 如果当前线程有连接则调用该方法关闭掉
	public static void closeConnection() {

		Connection conn = connWrapper.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Can not close database connection", e);
			}
			// 释放掉保存的对象
			connWrapper.remove();
		}

	}

	public void closeConnection(Connection conn) throws Exception {

		// note:由调用方主动关闭连接
		// if (conn != null) {
		// conn.close();
		// }
	}

	/**
	 * 通过id获取数据库对象
	 * @param id 主键值
	 * @param tableName 要查询的表
	 * @param idColumnName id对应的数据库主键列名
	 * @return
	 */
	public Map<String,Object> queryById(String id,String tableName,String idColumnName) throws Exception{
		String sql="select * from "+tableName +" where "+idColumnName+"=?";
		return querySingle(sql,new Object[]{id});
	}
	/**
	 * 等同于 queryById(id,tableName,"id");
	 * @param id
	 * @param tableName
	 * @return
	 */
	public Map<String,Object> queryById(String id,String tableName) throws Exception{
		return queryById(id,tableName,"id");
	}
	// ///////////////////////

	/**
	 * 查询单一的实体对象
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T extends IBean> T querySingleBean(String sql, Class<T> clazz)
			throws Exception {
		return this.querySingleBean(sql, new Object[0], clazz);
	}

	public <T extends IBean> T querySingleBean(String sql, Object[] params,
			Class<T> clazz) throws Exception {
		List<T> beans = this.queryBeans(sql, params, clazz);
		if (beans == null || beans.isEmpty()) {
			return null;
		}
		return beans.get(0);
	}

	public <T extends IBean> T querySingleBean(String sql, Class<T> clazz,
			Object... params) throws Exception {
		if (params == null) {
			return querySingleBean(sql, (Object[]) null, clazz);
		}
		return querySingleBean(sql, params, clazz);
	}

	/**
	 * 支持分页的查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz,
			int offset, int limit) throws Exception {
		return queryBeans(sql, null, clazz, offset, limit);
	}

	/**
	 * 
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz)
			throws Exception {
		return this.queryBeans(sql, new Object[0], clazz);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Object[] params,
			Class<T> clazz, int offset, int limit) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> result = null;
		String pageSQL = formatPageSQL(sql, offset, limit);
		try {
			ps = conn.prepareStatement(pageSQL);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			int colCount = RSMD.getColumnCount();
			result = new LinkedList<T>();
			// 所有方法
			Method[] methods = clazz.getMethods();
			while (rs.next()) {
				T bean = (T) clazz.newInstance();
				for (int i = 1; i <= colCount; i++) {
//					Object value = rs.getObject(i);
//					if (value == null) {// 忽略掉值为null的字段
//						continue;
//					}

					String name = RSMD.getColumnName(i);
					Method method = this.getMethod("set", methods, name);
					if (method == null) { // 找不到对应的方法，则忽略掉该字段
						// TD:是否需要记录下日志
						continue;
					}

					
					// value的类型 通过不同的jdbc驱动返回的类型有可能不大一样，此调用setter方法有可能就会出现匹配错误
					// 这里需要有个智能决策的逻辑
					Class[] arguments = method.getParameterTypes();
					if (arguments.length != 1) {// 参数值必须有且只有一个
						continue;
					}

					// 进行可能的类型转化
					Object resultSetValue = JdbcUtils.getResultSetValue(rs, i, arguments[0]);
					if(resultSetValue==null){
						continue;
					}
//					Object convertedValue = typeConvertor.convert(arguments[0], value);
					// 调用setter方法设置值
					method.invoke(bean, resultSetValue);
				}
				result.add(bean);
			}
		}catch(Exception e){
			logger.error("",e);
		} finally {
			cleanup(rs, ps);
			this.closeConnection(conn);
		}
		return result;
	}

	// 根据数据库的列名返回指定的方法，忽略大小写
	protected Method getMethod(String methodPrefix, Method[] all, String colname) {
		for (Method method : all) {
			String name = method.getName();
			if (name.startsWith(methodPrefix)) {
				name = name.substring(methodPrefix.length());
				if (name.equalsIgnoreCase(colname)) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T extends IBean> List<T> queryBeans(String sql, Object[] params,
			Class<T> clazz) throws Exception {
		return queryBeans(sql, params, clazz, 0, 0);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz,
			Object[] params) throws Exception {
		return queryBeans(sql, params, clazz, 0, 0);
	}

	public <T extends IBean> List<T> queryBeans(String sql, Class<T> clazz,
			int offset, int limit, Object... params) throws Exception {
		return queryBeans(sql, (Object[])params, clazz, offset, limit);
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> query(String sql) throws Exception {
		return this.query(sql, (Object[])null);
	}

	public List<Map<String, Object>> query(String sql, int offset, int limit)
			throws Exception {
		return this.query(sql, (Object[])null, (String[])null, offset, limit);
	}

	public List<Map<String, Object>> query(String sql, String[] colnameDefines)
			throws Exception {
		return this.query(sql, (Object[])null, colnameDefines);
	}

	public List<Map<String, Object>> query(String sql, String[] colnameDefines,
			int offset, int limit) throws Exception {
		return this.query(sql, new Object[0], colnameDefines, offset, limit);
	}

	public List<Map<String, Object>> query(String sql, Object[] params)
			throws Exception {
		return this.query(sql, params, (String[])null);
	}
	public Map<String, Object> querySingle(String sql, Object[] params)
			throws Exception {
		List<Map<String, Object>> result = query(sql, params);
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	public List<Map<String, Object>> query(String sql, Object[] params,
			int offset, int limit) throws Exception {
		return query(sql, params, null, offset, limit);

	}
	/**
	 * 查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> query(String sql, Object[] params,
			String[] colnameDefines) throws Exception {
		return query(sql, params, colnameDefines, 0, 0);
	}
	
	public List<Map<String, Object>> query(String sql, Object[] params,
			String[] colnameDefines, int offset, int limit) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = null;
		try {
			String pageSQL = formatPageSQL(sql, offset, limit);
			ps = conn.prepareStatement(pageSQL);
			if(params!=null && params.length>0){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			int colCount = RSMD.getColumnCount();
			result = new LinkedList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> bean = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					Object value = rs.getObject(i);
					String name = RSMD.getColumnName(i);
					
					// map中存储自定义的列名，而不是数据库返回的名字，此解决不同数据库返回的大小写问题
					if (colnameDefines != null) {
						for (String dcolname : colnameDefines) {
							if (dcolname.equalsIgnoreCase(name)) {
								name = dcolname;
								break;
							}
						}
					} else {// 如果不指定列定义（colnameDefines），则以大写列明返回，客户程序在调用时由此可不关系数据库列明的大小写问题
						
						// NOTE:在mCloud这边只使用MySQL，保持列名大小写不变
						// name = name.toUpperCase();
						
					}
					
					bean.put(name, value);
				}
				result.add(bean);
			}
		} finally {
			cleanup(rs, ps);
			this.closeConnection(conn);
		}
		return result;
	}

	/**
	 * @deprecated
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T extends IBean> int countBeans(String sql, Object[] params,
			Class<T> clazz) throws Exception {
		List<T> result = this.queryBeans(sql, params, clazz);
		return result == null ? -1 : result.size();

	}

	/**
	 * 查询数据库中的单一整形字段
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public int queryIntegerValue(String sql, Object[] params) throws Exception {
		List<Map<String, Object>> result = this.query(sql, params, null);
		if (result == null || result.isEmpty()) {
			return -1;
		}
		Map<String, Object> data = result.get(0);
		Object[] ret = data.values().toArray();
		if (ret.length == 0 || ret[0] == null) {
			return -1;
		}
		return ((Number) ret[0]).intValue();
	}

	public int queryIntegerValue(String sql) throws Exception {
		return this.queryIntegerValue(sql, new Object[0]);
	}

	public int count(String sql) throws Exception {
		return this.queryIntegerValue(sql, new Object[0]);
	}

	public int count(String sql, Object[] params) throws Exception {
		return this.queryIntegerValue(sql, params);

	}

	/**
	 * 执行数据的更新操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int execute(String sql) throws Exception {
		return this.execute(sql, new Object[0]);
	}

	// note:UPDATE和DELETE操作能返回被操作到的行数，INSERT成功则都是返回0
	public int execute(String sql, Object[] params) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}

			return ps.executeUpdate();

		} finally {
			cleanup(ps);
			this.closeConnection(conn);
		}

	}

	/**
	 * 执行数据创建，并返回主键值
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String  getcreate(String sql) throws Exception {
		return this.getcreate(sql, new Object[0]);
	}
	
	/**
	 * 执行数据创建，并返回主键值
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getcreate(String sql, Object[] params) throws Exception {
		String roomid ="";
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
			
			ResultSet newid = ps.getGeneratedKeys();
			newid.next();
			roomid = newid.getInt(1)+"";

		} finally {
			cleanup(ps);
			this.closeConnection(conn);
		}
		return roomid;
	}	
	
	/**
	 * 执行存储过程
	 * 
	 * @param callableSQL
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int executeCall(String callableSQL, Object[] params)
			throws Exception {

		Connection conn = getConnection();
		CallableStatement ps = null;
		try {
			ps = conn.prepareCall(callableSQL);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}

			return ps.executeUpdate();

		} finally {
			cleanup(ps);
			this.closeConnection(conn);
		}

	}

	public boolean existByCondition(String tableName, String condition,
			Object... params) throws Exception {

		return this.count("select count(0) from " + tableName + " where "
				+ condition, params) != 0;

	}

	/**
	 * 执行删除操作
	 * 
	 * @param tableName
	 * @param conditions
	 * @param params
	 * @throws Exception
	 */
	public int delete(String tableName, String conditions, Object... params)
			throws Exception {
		StringBuffer sql = new StringBuffer("DELETE FROM ");
		sql.append(tableName);
		sql.append(" WHERE ");
		sql.append(conditions);
		return this.execute(sql.toString(), params);

	}

	public <T extends IBean> int createBean(T bean, String tableName)
			throws Exception {

		return this.createBean(bean, tableName, null);

	}

	/**
	 * 在数据库中创建一个对象： 使用约定优于配置的方式 <br>
	 * 1.所有的get属性的字段与数据库中对应 <br>
	 * 2.表名与实体名对应(或者需要将表名传递进来)<br>
	 * 
	 * @param <T>
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int createBean(T bean, String tableName,
			List<String> ignoreFieldNames) throws Exception {

		// 分析bean中的数据
		Class clazz = bean.getClass();
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		Field[] fields = clazz.getDeclaredFields();
		Method[] allMethods = clazz.getMethods();
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				String name = f.getName();

				if (ignoreFieldNames != null && ignoreFieldNames.contains(name)) {
					continue;
				}

				// 忽略javabean字段的大小写
				Method method = this.getMethod("get", allMethods, name);
				if (method != null) {
					data.put(f.getName(), method.invoke(bean, new Object[0]));

				}

			}

		}

		// 合成insert SQL语句
		StringBuffer sql = new StringBuffer("INSERT INTO " + tableName
				+ data.keySet().toString().replace("[", "(").replace("]", ")")
				+ " VALUES(");

		List<Map.Entry<String, Object>> entrys = new LinkedList<Map.Entry<String, Object>>(
				data.entrySet());
		int size = entrys.size();
		Object[] params = new Object[size];
		for (int i = 0; i < size; i++) {
			Map.Entry<String, Object> entry = entrys.get(i);
			sql.append("?");
			if (i != size - 1) {
				sql.append(",");
			}
			params[i] = entry.getValue();
		}

		sql.append(")");

		// 执行sql
		return this.execute(sql.toString(), params);

	}

	public <T extends IBean> int createBean(T bean) throws Exception {
		return this.createBean(bean, bean.getClass().getSimpleName());

	}

	/**
	 * 
	 * @param <T>
	 * @param bean
	 * @param idcol
	 * @param tableName
	 * @return
	 * @throws Exception
	 */

	private static boolean isIgnoreCols(String colName, String[] ignoreCols) {
		for (String ignoreCol : ignoreCols) {
			if (ignoreCol.equalsIgnoreCase(colName)) {
				return true;
			}
		}

		return false;

	}

	public <T extends IBean> int updateBeanByCondition(T bean,
			String condition, String tableName) throws Exception {
		return updateBeanByCondition(bean, condition, tableName, new String[0]);

	}

	public <T extends IBean> int updateBeanByCondition(T bean,
			String condition, String tableName, String[] ignoreCols)
			throws Exception { 
		// 分析bean中的数据
		Class clazz = bean.getClass();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Field[] fields = clazz.getDeclaredFields();
		Method[] allMethods = clazz.getMethods();
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				String name = f.getName();

				// 有需要忽略的列，则不生成指定的字段的sql
				if (ignoreCols != null && isIgnoreCols(name, ignoreCols)) {
					continue;
				}

				Method method = this.getMethod("get", allMethods, name);
				if (method != null) {
					Object value = method.invoke(bean, new Object[0]);
					if(value!=null){
						data.put(f.getName() + "=?", value);
					}
				}
			}
		}
		// 合成update SQL语句
		String sql = "UPDATE " + tableName + " SET "
				+ data.keySet().toString().replace("[", "").replace("]", "")
				+ " WHERE " + condition;
		return this.execute(sql.toString(), data.values().toArray());

	}

	/**
	 * 更新实体,过滤null值的属性字段
	 * 
	 * @param <T>
	 * @param bean
	 * @param idcol
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String idcol,
			String tableName) throws Exception {
		Class clazz = bean.getClass();
		Method method = getMethod("get", clazz.getMethods(), idcol);
		Object invoke = method.invoke(bean, new Object[] {});
		return updateBean(bean, tableName, (idcol + "=?"),
				new Object[] { invoke });
	}

	/**
	 * 更新实体,过滤null值的属性字段
	 * 
	 * @param <T>
	 * @param bean
	 *            实体
	 * @param tableName
	 *            表名
	 * @param whereSql
	 *            sql中的where段
	 * @param whereParams
	 *            where的?的值
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String tableName,
			String whereSql, Object[] whereParams) throws Exception {
		return updateBean(bean, tableName, whereSql, whereParams, true);
	}

	/**
	 * 更新实体
	 * 
	 * @param <T>
	 * @param bean
	 *            实体
	 * @param tableName
	 *            表名
	 * @param whereSql
	 *            sql中的where段
	 * @param whereParams
	 *            where的?的值
	 * @param ignoreNull
	 *            是否过滤null值的属性字段
	 * @return
	 * @throws Exception
	 */
	public <T extends IBean> int updateBean(T bean, String tableName,
			String whereSql, Object[] whereParams, boolean ignoreNull)
			throws Exception {
		// 分析bean中的数据
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE ");
		sb.append(tableName);
		sb.append(" SET ");
		List<Object> params = new ArrayList<Object>();
		List<String> fv = new ArrayList<String>();
		Class clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			String name = f.getName();
			Method method = null;
			String capitalize = StringUtils.capitalize(name);
			try {
				method = clazz.getMethod("get" + capitalize);
			} catch (Exception e) {
			}
			if (method == null) {
				Class<?> type = f.getType();
				if (type == Boolean.class || type == boolean.class) {
					try {
						method = clazz.getMethod("is" + capitalize);
					} catch (Exception e) {
					}
				}
			}
			if (method == null) {
				continue;
			}
			Object value = method.invoke(bean, new Object[0]);
			if (value == null && ignoreNull) {
				continue;
			}
			fv.add(name + "=?");
			params.add(value);
		}
		sb.append(ArrayUtil.join(fv, ","));
		sb.append(" where ");
		sb.append(whereSql);
		params.addAll(Arrays.asList(whereParams));
		return this.execute(sb.toString(), params.toArray());
	}

	public <T extends IBean> int updateBean(T bean, String idcol,
			String tableName, String[] ignoreCols) throws Exception {
		// 分析bean中的数据
		Class clazz = bean.getClass();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Field[] fields = clazz.getDeclaredFields();
		Method[] allMethods = clazz.getMethods();
		Object idvalue = "";
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				String name = f.getName();

				// 有需要忽略的列，则不生成指定的字段的sql
				if (ignoreCols != null && isIgnoreCols(name, ignoreCols)) {
					continue;
				}
				Method method = this.getMethod("get", allMethods, name);
				if (method != null) {
					Object value = method.invoke(bean, new Object[0]);
					data.put(f.getName() + "=?", value);
					if (name.equals(idcol)) {// 获得ID条件
						idvalue = value;
					}
				}
			}
		}
		// 合成update SQL语句
		String sql = "UPDATE " + tableName + " SET "
				+ data.keySet().toString().replace("[", "").replace("]", "")
				+ " WHERE " + idcol + "='" + idvalue + "'";
		return this.execute(sql.toString(), data.values().toArray());
	}

	public static ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		CommonDao.connectionProvider = connectionProvider;
	}

//	public TypeConvertor getTypeConvertor() {
//		return typeConvertor;
//	}
//
//	public void setTypeConvertor(TypeConvertor typeConvertor) {
//		this.typeConvertor = typeConvertor;
//	}

	public IPage getPage() {
		return page;
	}

	public void setPage(IPage page) {
		this.page = page;
	}

	public void cleanup(Statement stat) {

		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}

	public void cleanup(ResultSet rs, Statement stat) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
			}
		}
		cleanup(stat);
	}

	/**
	 * 格式化分页的SQL语句
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	private String formatPageSQL(String sql, int offset, int limit) {
		if (offset < 0) {
			offset = 0;
		}
		if (limit < 0) {
			limit = 0;
		}
		if (offset == 0 && limit == 0) {
			return sql;
		}
		if (offset > 0 && limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		return page.synthesisPage(sql, offset, limit);
	}

	/**
	 * 辅助方法，打印出RS中的所有内容
	 * 
	 * @param rs
	 * @throws Exception
	 */
	protected void printRS(ResultSet rs) throws Exception {

		rs.getMetaData();
		ResultSetMetaData RSMD = rs.getMetaData();
		int colCount = RSMD.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= colCount; i++) {
				Object value = rs.getObject(i);
				System.out.println(value);

			}
		}

	}

	// 获得自动的ID
	public static String autoID() {
		return UUID.randomUUID().toString();
	}

	// 获得域数据
	// 忽略大小写
	public static Object getField(String key, Map<String, Object> record) {
		Object data = record.get(key);
		data = data != null ? data : record.get(key.toUpperCase());
		return data;
	}

	public static String getString(String key, Map<String, Object> record) {
		Object o = getField(key, record);
		if (o == null) {
			return null;
		}

		return String.valueOf(o);

	}

	// 执行某个事务
	/**
	 * 在同一个事务中用到同一个连接
	 */
	public static Object doTransaction(Transaction trans) throws Exception {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try {
			Object ret = trans.execute(); // 多条的语句在执行中...
			conn.commit();
			return ret;
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
			closeConnection();
		}

	}

	/**
	 * 查询某个字段的值
	 * @param result 返回string
	 * @throws Exception
	 */
	public String queryString(String sql, Object[] params) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result=null;
		try {
			ps = conn.prepareStatement(sql);
			if(params!=null && params.length>0){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData RSMD = rs.getMetaData();
			int colCount = RSMD.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= colCount; i++) {
					result= (String)rs.getObject(i);
				}
			}
		} finally {
			cleanup(rs, ps);
			this.closeConnection(conn);
		}
		return result;
	}
	/**
	 * 通过指定的条件，修改表指定字段的值
	 * @author leon_yan
	 * @param tableName ：表名
	 * @param updateData:需要修改的字段及需要修改的字段的值
	 * @param condition：指定的条件
	 * @return 返回修改成功的行数
	 * @throws Exception 
	 */
	public int updatePartialByCondition(String tableName,Map<String,Object> updateData,String condition) throws Exception{
		if(StringUtils.isEmpty(tableName)){
			logger.warn("tablename is empty!");
			return 0;
		}
		if(updateData == null || updateData.size()==0){
			logger.warn("update data is empty!");
			return 0;
		}
		StringBuffer sqlSB = new StringBuffer();
		sqlSB.append("update ").append(tableName).append(" set ");
		Iterator<Entry<String,Object>> itr = updateData.entrySet().iterator();
		List<Object> paramList = new ArrayList<Object>();
		while(itr.hasNext()){
			Entry entry = itr.next();
			String key = (String)entry.getKey();
			Object value = (Object)entry.getValue();
			sqlSB.append(key).append("=?");
			paramList.add(value);
			if(itr.hasNext()){
				sqlSB.append(",");
			}
		}
		sqlSB.append(condition);
		return this.execute(sqlSB.toString(), paramList.toArray());

	}
}
