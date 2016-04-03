package com.web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.server.MeetBaseServer;

@Controller
public class MeetBaseControl {
	Logger logger =LoggerFactory.getLogger("com.web.control.MeetBaseControl");
	
	@Autowired
	MeetBaseServer meetBaseServer;
	
	/**
	 * 获取某userid的所有会议室
	 * @param userid
	 * @return
	 */
	@RequestMapping("/showmeet")
	public String showMeetbase(String userid){
		logger.info("接受showmeet请求");
		return "meethome";
	}
	
	/**
	 * 显示编辑会议室的页面
	 * @param roomid
	 * @return
	 */
	@RequestMapping("/editmeet")
	public String editMeetbase(String roomid){
		logger.info("接受editmeet请求");
		return "editmeet";
	}	
	
}
