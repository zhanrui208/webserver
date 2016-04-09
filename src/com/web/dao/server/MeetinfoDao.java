package com.web.dao.server;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.dao.CommonDao;
import com.web.model.Meetinfo;

@Repository
public class MeetinfoDao  extends CommonDao{
	private static String m_TableName="t_roominfo";
	
	public List<Meetinfo> getMeetList() throws Exception{
		String sql =" select * from " +  m_TableName ;
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class);
		return Meetlist;
	}
	
	public List<Meetinfo> getMeetList(String id) throws Exception{
		Object params[] =  new Object[1];
		params[0]=id;
		
		String sql =" select * from " +  m_TableName + " where MeetID = ?";
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class,params);
		return Meetlist;
	}
	
	public Meetinfo geSingleMeet() throws Exception{
		String sql =" select * from " +  m_TableName;
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class);
		return Meetlist.get(0);
	}
	
	public Meetinfo geSingleMeet(String id) throws Exception{
		Object params[] =  new Object[1];
		params[0]=id;
		String sql =" select * from " +  m_TableName + " where MeetID = ?";
		List<Meetinfo> Meetlist = queryBeans(sql, Meetinfo.class);
		return Meetlist.get(0);
	}
	
}
