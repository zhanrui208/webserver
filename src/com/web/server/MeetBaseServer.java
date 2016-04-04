package com.web.server;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dao.server.LiveshoppingDao;
import com.web.dao.server.MeetLiveDao;
import com.web.dao.server.MeetinfoDao;
import com.web.model.Liveshopping;
import com.web.model.MeetLiveBase;
import com.web.model.Meetinfo;
import com.web.model.Roomusers;

@Service
public class MeetBaseServer {
	Logger logger =LoggerFactory.getLogger("com.web.server.MeetinfoServer");
	
	@Autowired
	LiveshoppingDao liveshoppingDao;
	
	@Autowired
	MeetinfoDao meetinfoDao;
	
	@Autowired
	MeetLiveDao meetLiveDao;
	
	/**
	 * 获取某个userid的所有会议室信息
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>>  getMeetLive(String userid,int offset,int limit){
		List<Map<String, Object>> meetbaselist =null;
		try{
			meetbaselist =  meetLiveDao.getRoomIdListByUserId(userid,offset,limit);
		}catch (Exception e){
			logger.info("geSingleMeetById:" + e);
		}
		return meetbaselist;
	}
	
	/**
	 * 创建会议室信息
	 * @param meetLiveBase
	 * @return
	 */
	public int createMeetLive(MeetLiveBase meetLiveBase){
		int count = 0;
		try{
			Meetinfo meetinfo = getMeetinfoFrom(meetLiveBase);
			count=meetLiveDao.createMeetinfo(meetinfo);
			
			Liveshopping liveshopping =getLiveshoppingFrom(meetLiveBase);
			count =meetLiveDao.createLiveshop(liveshopping);
			
			Roomusers roomusers = getRoomusersFrom(meetLiveBase);
			count = meetLiveDao.createRoomusers(roomusers);
			
		}catch(Exception e){
			logger.info("createMeetLive:" + e);
		}
		return count;
	}
	
	/**
	 * 更新会议室内容
	 * @param meetLiveBase
	 * @param roomid
	 * @return
	 */
	public int updateMeetLive(MeetLiveBase meetLiveBase,String roomid){
		int count = 0;
		try{
			Meetinfo meetinfo = getMeetinfoFrom(meetLiveBase);
			count=meetLiveDao.upMeetinfoById(meetinfo, roomid);
			
			Liveshopping liveshopping =getLiveshoppingFrom(meetLiveBase);
			count =meetLiveDao.upLiveshopById(liveshopping,roomid);
			
		}catch(Exception e){
			logger.info("createMeetLive:" + e);
		}
		return count;
	}
	
	
	
	private Meetinfo getMeetinfoFrom(MeetLiveBase meetLiveBase){
		Meetinfo meetinfo = new Meetinfo();
		meetinfo.setRoomID(meetLiveBase.getRoomID());
		meetinfo.setBusinessinfoID(meetLiveBase.getBusinessinfoID());
		meetinfo.setRoomName(meetLiveBase.getRoomName());
		meetinfo.setIsSupportLive(meetLiveBase.getIsSupportLive());
		meetinfo.setIsSupportMediaUpload(meetLiveBase.getIsSupportMediaUpload());
		meetinfo.setHopeStartTime(meetLiveBase.getHopeStartTime());
		meetinfo.setHopeEndTime(meetLiveBase.getHopeEndTime());
		return meetinfo;
	}
	
	private Liveshopping getLiveshoppingFrom(MeetLiveBase meetLiveBase){
		Liveshopping liveshopping = new Liveshopping();
		liveshopping.setRoomID(meetLiveBase.getRoomID());
		liveshopping.setProcessFlag(meetLiveBase.getProcessFlag());
		liveshopping.setRecordOnline(meetLiveBase.getRecordOnline());
		liveshopping.setCreateTimer(meetLiveBase.getCreateTimer());
		liveshopping.setUploadFile(meetLiveBase.getUploadFile());
		return liveshopping;
	}
	
	private Roomusers getRoomusersFrom(MeetLiveBase meetLiveBase){
		Roomusers roomusers = new Roomusers();
		roomusers.setRoomID(meetLiveBase.getRoomID());
		roomusers.setUserID(meetLiveBase.getUserID());
		return roomusers;
	}
	
}
