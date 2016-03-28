package com.web.dao.server;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import sun.util.logging.resources.logging;

import com.web.dao.CommonDao;
import com.web.model.Userinfo;
import com.web.model.Userregedit;

@Repository
public class UserinfoDao extends CommonDao{
	
	private static String m_TableName="t_userinfo";
	private static String m_ReTableName="t_userregedit";
	
	
	public List<Userinfo> getUserList() throws Exception{
		String sql =" select * from " +  m_TableName ;
		List<Userinfo> userlist = queryBeans(sql, Userinfo.class);
		return userlist;
	}
	
	public List<Userinfo> getUserList(String id) throws Exception{
		Object params[] =  new Object[1];
		params[0]=id;
		
		String sql =" select * from " +  m_TableName + " where UserID = ?";
		List<Userinfo> userlist = queryBeans(sql, Userinfo.class,params);
		return userlist;
	}
	
	public Userinfo geSingleUser() throws Exception{
		String sql =" select * from " +  m_TableName;
		List<Userinfo> userlist = queryBeans(sql, Userinfo.class);
		return userlist.get(0);
	}
	
	public Userinfo geSingleUser(String id) throws Exception{
		Object params[] =  new Object[1];
		params[0]=id;
		String sql =" select * from " +  m_TableName + " where UserID = ?";
		List<Userinfo> userlist = queryBeans(sql, Userinfo.class);
		return userlist.get(0);
	}
	
	public Userinfo geSingleUserbyusername(String username) throws Exception{
		String sql =" select * from " +  m_TableName + " where UserName = ?";
		List<Userinfo> userlist = queryBeans(sql, Userinfo.class,new Object[]{username});
		return userlist.get(0);
	}
	
	public Map<String,Object> getBaseInfo() throws Exception{
		Object params[] =  new Object[1];
		 String sql =" select "+ getBaseinfoFields() +" from " +  m_TableName + " ";
		 Map<String,Object> infoMap = querySingle(sql,params);
		 return infoMap;
	}
	
	public Map<String,Object> getBaseInfo(String id) throws Exception{

		Object params[] =  new Object[1];
		params[0]=id;
			
		 String sql =" select "+ getBaseinfoFields() +" from " +  m_TableName + " where UserID = ?";
		 Map<String,Object> infoMap = querySingle(sql, params);
		 return infoMap;
	}
	
	/**
	 * 验证账号密码
	 * @param UserName
	 * @param Password
	 * @return
	 */
	public boolean checkPwd(String UserName,String Password) throws Exception{
		boolean isTrue = false;
		String sql =" select 1 from " +  m_TableName + " where UserName = ? and Password=? ";
		Object params[] =  new Object[2];
		params[0]=UserName;
		params[1]=Password;
		 List<Map<String,Object>> list=query(sql, params);
		 if  (list!=null && list.size()>0){
			 isTrue=true;
		 }
		return isTrue;
	}
	
	/**
	 * 验证用户名是否注册
	 * @param UserName
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserName(String UserName) throws Exception{
		boolean isTrue = false;
		String sql =" select 1 from " +  m_TableName + " where UserName = ? ";
		 List<Map<String,Object>> list=query(sql, new Object[]{UserName});
		 if  (list!=null && list.size()>0){
			 isTrue=true;
		 }
		return isTrue;
	}
	
	/**
	 * 验证用户名和邮箱是否匹配
	 * @param UserName
	 * @param Email
	 * @return
	 * @throws Exception
	 */
	public boolean checkEmail(String UserName,String Email) throws Exception{
		boolean isTrue = false;
		String sql =" select 1 from " +  m_TableName + " where UserName = ? and EMail=? ";
		Object params[] =  new Object[2];
		params[0]=UserName;
		params[1]=Email;
		 List<Map<String,Object>> list=query(sql, params);
		 if  (list!=null && list.size()>0){
			 isTrue=true;
		 }
		return isTrue;
	}
	
	
	

	/**
	 * 用户基本信息定义
	 * @return
	 */
	private String getBaseinfoFields(){
		String baseinfo=" UserID,UserName,DisplayName,DisplayName,Sex,Status,Mobile,TelePhone,EMail,DepartID,AdminRole ";
		return baseinfo;
	}
	
	/**
	 **创建用户数据
	 * @param userinfo
	 * @return
	 */
	public boolean creUserinfo(Userinfo userinfo){
		boolean isTrue = false;
		try {
			int count = createBean(userinfo,m_TableName);
			if (count >0){
				isTrue=true;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
	
	
	/**
	 **创建用户数据
	 * @param userinfo
	 * @return
	 */
	public boolean creUserregedit(Userregedit userregedit){
		boolean isTrue = false;
		try {
			int count = createBean(userregedit,m_ReTableName);
			if (count >0){
				isTrue=true;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
	
	/**
	 * 获取注册的数据
	 * @param username
	 * @param randomNum
	 * @return
	 */
	public Userregedit getregedit(String username,String randomNum){
		boolean isTrue = false;
		try {
			String sql =" select * from " +  m_ReTableName + " where UserName = ? and RandomNum=? ";
			List<Userregedit> userreglist = queryBeans(sql, Userregedit.class,new Object[]{username,randomNum});
			if (userreglist!=null && userreglist.size()>0){
				return userreglist.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public boolean updateUserinfo(Userinfo userinfo,String whereSql,String whereParams[] ){
		boolean isTrue = false;
		try {
			int count = updateBean(userinfo, m_TableName, whereSql, whereParams);
			if (count >0){
				isTrue=true;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
	
	
	public boolean delUserinfo(Userinfo userinfo,String conditions,String params[] ){
		boolean isTrue = false;
		try {
			int count = delete(m_TableName, conditions, params);
			if (count >0){
				isTrue=true;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
	
	
	public boolean updatePwd(String username,String newpassword){
		boolean isTrue = false;
		try {
			String sql =" update " +  m_TableName + " set Password = ? where UserName = ?";
			String params[]={newpassword,username};
			int count = execute(sql, params);
			if (count >0){
				isTrue=true;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
}
