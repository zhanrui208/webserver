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
	 * 获取某个roomid的会议室信息
	 * @param userid
	 * @return
	 */
	public List<MeetLiveBase>  getMeetLivebyRoomId(String meetid){
		List<MeetLiveBase>  meetLiveBaseList =null;
		try{
			meetLiveBaseList=  meetLiveDao.getMeetBaseListByRoomId(meetid);
		}catch (Exception e){
			logger.info("geSingleMeetById:" + e);
		}
		return meetLiveBaseList;
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
			
			//获取创建的roomid值
			meetinfo =meetLiveDao.geSingleMeetByRoomName(meetLiveBase.getUserID()+"",meetLiveBase.getRoomName());
			int roomid = meetinfo.getRoomID();
			
			Liveshopping liveshopping =getLiveshoppingFrom(meetLiveBase);
			liveshopping.setRoomID(roomid);
			count =meetLiveDao.createLiveshop(liveshopping);
			
			Roomusers roomusers = getRoomusersFrom(meetLiveBase);
			roomusers.setRoomID(roomid);
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
	
		meetinfo.setBusinessinfoID(meetLiveBase.getBusinessinfoID());
		if (meetLiveBase.getRoomID() != 0){
			meetinfo.setRoomID(meetLiveBase.getRoomID());
		}
		if (meetLiveBase.getRoomName() != null){
			meetinfo.setRoomName(meetLiveBase.getRoomName());
		}
		if (meetLiveBase.getIsSupportLive() != null){
			meetinfo.setIsSupportLive(meetLiveBase.getIsSupportLive());
		}
		if (meetLiveBase.getIsSupportMediaUpload() != null){
			meetinfo.setIsSupportMediaUpload(meetLiveBase.getIsSupportMediaUpload());
		}
		if (meetLiveBase.getHopeStartTime() != null){
			meetinfo.setHopeStartTime(meetLiveBase.getHopeStartTime());
		}
		if (meetLiveBase.getHopeEndTime() != null){
			meetinfo.setHopeEndTime(meetLiveBase.getHopeEndTime());
		}
		return meetinfo;
	}
	
	private Liveshopping getLiveshoppingFrom(MeetLiveBase meetLiveBase){
		Liveshopping liveshopping = new Liveshopping();
		if (meetLiveBase.getRoomID() != 0){
			liveshopping.setRoomID(meetLiveBase.getRoomID());
		}
		if (meetLiveBase.getProcessFlag() != null){
			liveshopping.setProcessFlag(meetLiveBase.getProcessFlag());
		}
		if (meetLiveBase.getRecordOnline() != null){
			liveshopping.setRecordOnline(meetLiveBase.getRecordOnline());
		}
		if (meetLiveBase.getCreateTimer() != null){
			liveshopping.setCreateTimer(meetLiveBase.getCreateTimer());
		}
		if (meetLiveBase.getUploadFile() != null){
			liveshopping.setUploadFile(meetLiveBase.getUploadFile());
		}
		return liveshopping;
	}
	
	private Roomusers getRoomusersFrom(MeetLiveBase meetLiveBase){
		Roomusers roomusers = new Roomusers();
		if (meetLiveBase.getRoomID() !=0){
			roomusers.setRoomID(meetLiveBase.getRoomID());
		}
		if (meetLiveBase.getUserID() !=0){
			roomusers.setUserID(meetLiveBase.getUserID());
		}
		return roomusers;
	}
	
	/**
	 * 根据传过来的参数设置room的值
	 * @param map
	 * @return
	 */
	public MeetLiveBase getRoomBase(Map<String,String> map,boolean newroom){
		MeetLiveBase meetLiveBase = new MeetLiveBase();
		if (map.get("zhibo") !=null){
			String isSupportLive=(String) map.get("zhibo");
			meetLiveBase.setIsSupportLive(isSupportLive);
		}
		if (map.get("luzhi") !=null){
			String isSupportMediaUpload=(String) map.get("luzhi");
			meetLiveBase.setIsSupportMediaUpload(isSupportMediaUpload);
		}
		if (map.get("usercounts") !=null){
			int maxUserCount=Integer.parseInt(map.get("usercounts"));
			meetLiveBase.setMaxUserCount(maxUserCount);
		}
//		if (map.get("") !=null){
//			meetLiveBase.setRecordOnline(recordOnline);
//		}
		if (map.get("meetname") !=null){
			String roomName = (String) map.get("meetname");
			meetLiveBase.setRoomName(roomName);
		}
		if (newroom){
			
		}
//		if (map.get("") !=null){
//			meetLiveBase.setUploadFile(uploadFile);
//		}
//		if (map.get("") !=null){
//			meetLiveBase.setUserID(userID);
//		}
		return meetLiveBase;
//		if (map.get("businessinfoID") !=null){
//		String businessinfoID="";
//		meetLiveBase.setBusinessinfoID(businessinfoID);
//	}		
//		private int roomID;
//		private int userID;
//		private String roomName;
//		private  int maxUserCount;
//		private String isSupportLive;//是否支持直播
//		private String isSupportMediaUpload;//是否支持在线录制
//		private String businessinfoID; //1为超清，2为高清，5标清
//		private String  hopeStartTime;//开始时间
//		private String hopeEndTime; //会议室结束时间
//		private String recordOnline;//是否在线录制
//		private String uploadFile;//是否支持上传文件
//		private String  createTimer;//创建时间
//		private String  processFlag;//处理标志，0为未处理，1为处理，下单时状态为未处理，即默认为0
	}
	
}
