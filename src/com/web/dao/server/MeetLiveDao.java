package com.web.dao.server;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.dao.CommonDao;
import com.web.dao.IBean;
import com.web.model.Liveshopping;
import com.web.model.MeetLiveBase;
import com.web.model.Meetinfo;
import com.web.model.Roomusers;

@Repository
public class MeetLiveDao extends CommonDao {
	private static String m_ROOMINFO = "t_roominfo";
	private static String m_LIVESHOPPING = "t_liveshopping";
	private static String m_ROOMUSERS="t_roomusers";
	
	//----------------1
	private String getBaseSQL() throws Exception{
		String sql = " select " + getSel("t1",Meetinfo.class) + "," + getSel("t2",Liveshopping.class) 
				+ " from " + m_ROOMINFO + " t1" 
				+ " inner join " + m_LIVESHOPPING + " t2  on t1.RoomID=t2.RoomID "
				+ " inner join " + m_ROOMUSERS + " t3 on t1.RoomID = T3.RoomID" ; 
		return sql;
	}
	
	//会议室基本信息
	public List<MeetLiveBase> getMeetBaseList() throws Exception {
		return getMeetBaseListByRoomidAndUserId(null,null);
	}

	/**
	 * 按用户ID和会议室ID获取所有会议室
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<MeetLiveBase> getMeetBaseListByRoomidAndUserId(String roomid,String userid) throws Exception {
		return getMeetBaseList(roomid,userid,-1,-1);
	}
	/**
	 * 按用户ID获取所有会议室
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<MeetLiveBase> getMeetBaseListByUserId(String userid) throws Exception {
		return getMeetBaseList(null,userid,-1,-1);
	}
	/**
	 * 按用户ID获取所有会议室
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<MeetLiveBase> getMeetBaseListByUserId(String userid,int offset,int limit) throws Exception {
		return getMeetBaseList(null,userid,offset,limit);
	}
	/**
	 * 按roomID获取所有会议室
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<MeetLiveBase> getMeetBaseListByRoomId(String roomid) throws Exception {
		return getMeetBaseList(roomid,null,-1,-1);
	}
	
	
	public List<MeetLiveBase> getMeetBaseList(String roomid,String userid,int offset, int limit) throws Exception {
		String sqlwhere ="";
		String params[] = null;
		if (roomid!=null && userid !=null){
			sqlwhere = " where t1.RoomID = ? and t3.UserID = ? ";
			params = new String[]{roomid,userid};
		}else if(roomid==null && userid !=null){
			sqlwhere = " where t3.UserID = ? ";
			params = new String[]{userid};
		}else if(roomid!=null && userid == null){
			sqlwhere = " where t1.RoomID = ? ";
			params = new String[]{roomid};
			
		}
		List<MeetLiveBase> meetlist = queryBeans(getBaseSQL()+sqlwhere,params, MeetLiveBase.class,offset,limit);
		return meetlist;
	}
	//----------------1
	
	////获取单独的room信息
	//----------------2
	private String getMeetinfo() throws Exception{
		String sql = " select " + getSel("t1",Meetinfo.class)
				+ " from " + m_ROOMINFO + " t1 ";
		return sql;
	}
	public List<Meetinfo> getMeetinfoList() throws Exception {
		return getMeetinfoListByRoomId(null);
	}

	public List<Meetinfo> getMeetinfoListByRoomId(String roomid) throws Exception {
		return getMeetinfoList(roomid,-1,-1);
	}
	
	public List<Meetinfo> getMeetinfoList(String roomid,int offset, int limit) throws Exception {
		String sqlwhere = "";
		if (roomid !=null){
			sqlwhere = " where t1.RoomID = ?";
		}
		List<Meetinfo> meetlist = queryBeans(getMeetinfo()+sqlwhere, Meetinfo.class,offset,limit,roomid);
		return meetlist;
	}
	//----------------2
	
	//获取支付信息
	//----------------3
	private String getLiveshop() throws Exception{
		String sql = " select " + getSel("t2",Liveshopping.class)
				+ " from " + m_LIVESHOPPING + " t2" ;
		return sql;
	}
	
	public List<Meetinfo> getLiveshopList() throws Exception {
		return getLiveshopListByRoomId(null);
	}

	public List<Meetinfo> getLiveshopListByRoomId(String roomid) throws Exception {
		return getLiveshopList(roomid,-1,-1);
	}
	
	public List<Meetinfo> getLiveshopList(String roomid,int offset, int limit) throws Exception {
		String sqlwhere = "";
		if (roomid !=null){
			sqlwhere = " where t2.RoomID = ?";
		}
		List<Meetinfo> meetlist = queryBeans(getLiveshop()+sqlwhere, Meetinfo.class,offset,limit,roomid);
		return meetlist;
	}
	//----------------3
	
	
	//根据用户获取所有roomid----------------4
	public List<Map<String,Object>> getRoomIdListByUserId(String userid) throws Exception {
		return getRoomIdList(userid,-1,-1);
	}
	public List<Map<String,Object>> getRoomIdListByUserId(String userid,int offset, int limit) throws Exception {
		return getRoomIdList(userid,offset,limit);
	}
	
	public List<Map<String,Object>> getRoomIdList(String userid,int offset, int limit) throws Exception {
		String sql = " select RoomID from " + m_ROOMUSERS ;
		String sqlwhere = "";
		if (userid !=null){
			sqlwhere = " where UserID = ? ";
		}
		List<Map<String,Object>> roomidlist = query(sql+sqlwhere,new Object[]{userid},offset,limit);
		return roomidlist;
	}
	//----------------4
	
	
	//----------------5
	private String getRoomShop() throws Exception{
		String sql = " select " + getSelFileds1()
				+ " from " + m_ROOMINFO + " t1" 
				+ " inner join " + m_LIVESHOPPING + " t2  on t1.RoomID=t2.RoomID ";
		return sql;
	}
	
	private String getSelFileds1() throws Exception{
		return connectFileds("t1" ,getSel("t1",Meetinfo.class) ,"t2",getSel("t2",Meetinfo.class));
	}
	
	public List<Meetinfo> getRoomShopList() throws Exception {
		return getRoomShopListById(null);
	}

	public List<Meetinfo> getRoomShopListById(String roomid) throws Exception {
		return getRoomShopList(roomid,-1,-1);
	}
	
	public List<Meetinfo> getRoomShopList(String roomid,int offset, int limit) throws Exception {
		String sqlwhere = "";
		if (roomid !=null){
			sqlwhere = " where t1.RoomID = ?";
		}
		List<Meetinfo> meetlist = queryBeans(getRoomShop()+sqlwhere, Meetinfo.class,offset,limit,roomid);
		return meetlist;
	}
	//----------------5

	//----6：根据用户和会议室名称查询会议室记录
	public Meetinfo geSingleMeetByRoomNameAndUserid(String userid,String name) throws Exception{
		String sql =" select " + getSelFileds2()  
					+ " from " +  m_ROOMINFO + " t1 "	
					+ " inner join " + m_ROOMUSERS +" t3  on t1.RoomID = t3.RoomID"
					+ " where t1.RoomName= ?  t3.userid = ? " 
					+ " order  by t1.RoomID desc ";
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class,new String[]{userid,name});
		return Meetlist.get(0);
	}
	
	private String getSelFileds2() throws Exception{
		return connectFileds("t1" ,getSel("t1",Meetinfo.class) ,"t3",getSel("t3",Roomusers.class));
	}
	//-----6
	
	//----7：根据会议室名称查询会议室记录
	public Meetinfo geSingleMeetByRoomName(String name) throws Exception{
		String sql = " select RoomID from " + m_ROOMINFO 
					+ " where RoomName= ?   " 
					+ " order  by RoomID desc ";
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class,new String[]{name});
		return Meetlist.get(0);
	}
	//-----7
	
//------------------------------------------------------------	
//------------------------------------------------------------	
	/**
	 * table 字段必须要＆cls对应
	 * 
	 * @param table
	 * @param pre
	 * @param obj
	 * @return
	 */
	private <T extends IBean> String  getSel(String pre, Class<T> cls) throws Exception {
		StringBuffer sbu = new StringBuffer();
		String sel ="";
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			sbu.append(pre + "." + field.getName() + ",");
		}
		sel = sbu.toString();
		sel = sel.substring(0, sel.length() - 1);
		return sel;
	}
	
	/**
	 * 去掉重复的字段
	 * @param sql1
	 * @param sql2
	 * 
	 * t1.a,t1.b    t2.a,t2.c   变成  t1.a,t1.b t2.a as a1,t2.c 
	 * 
	 * @return
	 */
	private String connectFileds(String pre1, String sql1,String pre2,String sql2){
		String sqlFileds = sql1;
		sql1 = sql1.replace(pre1 + ".", "");
		sql2 = sql2.replace(pre2 + ".", "");
		String fields1[] = sql1.split(",");
		String fields2[] = sql2.split(",");
		List<String> fields1List = Arrays.asList(fields1);
		List<String> fieldsList2 = new LinkedList<String>();
		for(int i = 0 ;i<fields2.length;i++){
			if (fields1List.contains(fields2[i].trim()) || fieldsList2.contains(fields2[i].trim())){
				sqlFileds = sqlFileds + "," + pre2 +"."+ fields2[i].trim()  + " as " + fields2[i].trim()+i;
				fieldsList2.add(fields2[i].trim()+i);
			}else{
				sqlFileds = sqlFileds + "," + pre2 + "." + fields2[i].trim();
			}
		}
 		return sqlFileds;
	}
	
	public static void main(String arg[]){
		MeetLiveDao mm = new MeetLiveDao();
		String pre1="t1";
		String sql1="t1.a,t1.b";
		String pre2="t2";
		String sql2="t2.a,t2.c";
		String s = mm.connectFileds(pre1,sql1,pre2,sql2);
		System.out.println(s);
	}
	
//------------------------------------------------------------	
//------------------------------------------------------------		
	
	//新增会议室和支付信息
	
	/**
	 * 创建会议室信息
	 * @param meetinfo
	 * @return
	 * @throws Exception
	 */
	public int createMeetinfo(Meetinfo meetinfo) throws Exception{
		return createBean(meetinfo,m_ROOMINFO);
	}
	/**
	 * 创建会议室支付信息
	 * @param liveshopping
	 * @return
	 * @throws Exception
	 */
	public int createLiveshop(Liveshopping liveshopping) throws Exception{
		return createBean(liveshopping,m_LIVESHOPPING);
	}
	
	/**
	 * 会议室与用户关系表
	 * @param roomusers
	 * @return
	 * @throws Exception
	 */
	public int createRoomusers(Roomusers roomusers) throws Exception{
		return createBean(roomusers,m_ROOMUSERS);
	}
	
	
	
	//更新会议室和支付信息
	
	/**
	 * 按ID更新会议室信息
	 * @param meetinfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int upMeetinfoById(Meetinfo meetinfo,String id) throws Exception{
		String whereSql =" RoomID = ? ";
		String[] whereParams = new String[]{id};
		boolean ignoreNull =true;
		String[] ignoreCols = new String[]{"roomID"};
		return upMeetinfo(meetinfo,whereSql,whereParams,ignoreNull,ignoreCols);
	}
	
	/**
	 * 按ID更新会议室支付信息
	 * @param liveshopping
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int upLiveshopById(Liveshopping liveshopping,String id) throws Exception{
		String whereSql =" RoomID = ? ";
		String[] whereParams = new String[]{id};
		boolean ignoreNull =true;
		return upLiveshop(liveshopping,whereSql,whereParams,ignoreNull);
	}
	
	
	public int upMeetinfo(Meetinfo meetinfo,String whereSql, Object[] whereParams, boolean ignoreNull) throws Exception{
		return updateBean(meetinfo,m_ROOMINFO,whereSql,whereParams,ignoreNull);
	}
	public int upMeetinfo(Meetinfo meetinfo,String whereSql, Object[] whereParams, boolean ignoreNull,String ignoreCols[]) throws Exception{
		return updateBean(meetinfo,m_ROOMINFO,whereSql,whereParams,ignoreNull,ignoreCols);
	}
	
	public int upLiveshop(Liveshopping liveshopping,String whereSql, Object[] whereParams, boolean ignoreNull) throws Exception{
		return updateBean(liveshopping,m_LIVESHOPPING,whereSql,whereParams,ignoreNull);
	}
}
