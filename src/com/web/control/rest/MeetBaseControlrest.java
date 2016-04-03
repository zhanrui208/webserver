package com.web.control.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.base.session.SessionManager;
import com.web.control.base.Imp.BaseController;
import com.web.model.MeetLiveBase;
import com.web.model.Userinfo;
import com.web.server.MeetBaseServer;

@Controller
public class MeetBaseControlrest  extends BaseController{
	Logger logger =LoggerFactory.getLogger("com.web.control.rest.MeetBaseControlrest");
	
	@Autowired
	MeetBaseServer meetBaseServer;
	
	/***
	 * 创建会议室
	 * @param meetLiveBase
	 * @return
	 */
	@RequestMapping("/createmeetbase")
	public String createMeetbase(MeetLiveBase meetLiveBase){
		logger.info("创建会议室/createmeetbase请求，meet={},password={}",meetLiveBase);
		Map<String, Object> map = initMessage();
		try {
			meetBaseServer.createMeetLive(meetLiveBase);
		} catch (Exception e) {
			processError(map, e);
			logger.error("createmeetbase:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}
	
	
	/**
	 * 更改会议室
	 * @param meetLiveBase
	 * @return
	 */
	@RequestMapping("/updatemeetbase")
	public String updateMeetbase(MeetLiveBase meetLiveBase){
		logger.info("创建会议室/updateMeetbase请求，meet={},password={}",meetLiveBase);
		Map<String, Object> map = initMessage();
		try {
			int roomid = meetLiveBase.getRoomID();
			if (roomid == 0){
				map.put("error", "数据错误，请刷新");
				map.put("errorCode", 500);
			}else{
				meetBaseServer.updateMeetLive(meetLiveBase,roomid+"");
			}
			
		} catch (Exception e) {
			processError(map, e);
			logger.error("updatemeetbase:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}
	
}
