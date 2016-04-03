package com.web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.server.MeetBaseServer;

@Controller
public class MeetBaseControl {
	
	@Autowired
	MeetBaseServer meetBaseServer;
	
	/**
	 * 获取某userid的所有会议室
	 * @param userid
	 * @return
	 */
	@RequestMapping("/showmeet")
	public ModelAndView showMeetbase(String userid){
		ModelAndView modelview =  new ModelAndView();
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<Map<String,Object>> meetbaselist =meetBaseServer.getMeetLive(userid);
		
		modelMap.put("data", meetbaselist);
		modelview.setViewName("meethome");
		modelview.addAllObjects(modelMap);
		return modelview;
	}
}
