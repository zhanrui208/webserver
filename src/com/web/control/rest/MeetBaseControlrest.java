package com.web.control.rest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.base.session.SessionManager;
import com.web.control.base.Imp.BaseController;
import com.web.model.MeetLiveBase;
import com.web.model.Userinfo;
import com.web.server.MeetBaseServer;

@Controller
@RequestMapping("/rest")
public class MeetBaseControlrest  extends BaseController{
	Logger logger =LoggerFactory.getLogger("com.web.control.rest.MeetBaseControlrest");
	
	@Autowired
	MeetBaseServer meetBaseServer;
	
	/**
	 * 查看userid的所有会议室
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getmeetbase", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String showMeetbase(HttpServletRequest req,int offset,int limit){
		logger.info("创建会议室/getmeetbase请求，");
		Map<String, Object> map = initMessage();
		try {
			String userid = SessionManager.getUserSession(req);
			List<Map<String,Object>> meetbaselist =meetBaseServer.getMeetLive(userid,offset,limit);
			map.put("data", meetbaselist);
		} catch (Exception e) {
			processError(map, e);
			logger.error("getmeetbase:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}
	
	/**
	 * 查看meetid的所有信息
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getmeetbasebyroomid", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String getMeetbasebyimeetd(HttpServletRequest req,String meetid){
		logger.info("创建会议室/getmeetbasebymeetid请求，");
		Map<String, Object> map = initMessage();
		try {
			List<MeetLiveBase> meetbaselist =meetBaseServer.getMeetLivebyRoomId(meetid);
			if (meetbaselist != null && !meetbaselist.isEmpty()){
				map.put("data", meetbaselist.get(0));
			}else{
				map.put("data", null);
			}
		} catch (Exception e) {
			processError(map, e);
			logger.error("getmeetbase:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}

	
	/**
	 * 更改会议室
	 * @param meetLiveBase
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savemeet", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String saveMeetbase(HttpServletRequest req){
		Enumeration<String>	names= req.getParameterNames();
		Map<String, String> roomBaseMap = new  HashMap<String, String>();
		while (names.hasMoreElements()){
			String key = names.nextElement();
			String value = (String) req.getParameter(key);
			roomBaseMap.put(key, value);
			System.out.println(value);
		}
		logger.info("创建会议室/updateMeetbase请求，meet={},password={}",roomBaseMap);
		Map<String, Object> map = initMessage();
		try {
			boolean newroom =  false;//是否是新增的标识
			if (roomBaseMap.get("newroom").equals("1")){
				newroom =true;
			}
			int roomid = 0 ; //会议室号
			if (roomBaseMap.get("roomid")!=null && roomBaseMap.get("roomid").length()>0){
				roomid =Integer.parseInt(roomBaseMap.get("roomid"));
			}
			String userid = SessionManager.getUserSession(req);
			
			if (newroom){
				MeetLiveBase meetLiveBase = meetBaseServer.getRoomBase(roomBaseMap,true);
				meetLiveBase.setUserID(Integer.parseInt(userid));
				meetBaseServer.createMeetLive(meetLiveBase);
			}else{
				MeetLiveBase meetLiveBase = meetBaseServer.getRoomBase(roomBaseMap,false);
				meetBaseServer.updateMeetLive(meetLiveBase,roomid+"");
			}
			
		} catch (Exception e) {
			processError(map, e);
			logger.error("savemeet:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}
	
	
	
}
