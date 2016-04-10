package com.web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.base.session.SessionManager;
import com.web.control.base.Imp.BaseController;
import com.web.model.MeetLiveBase;
import com.web.server.MeetBaseServer;

@Controller
public class MeetBaseControl extends BaseController{
	Logger logger =LoggerFactory.getLogger("com.web.control.MeetBaseControl");
	
	@Autowired
	MeetBaseServer meetBaseServer;
	
	/**
	 * 获取某userid的所有会议室
	 * @param userid
	 * @return
	 */
	@RequestMapping("/meethome")
	public String showMeetbase(String userid){
		logger.info("接受showmeet请求");
		return "meethome";
	}
	

	/**
		创建meet
	 */
	@RequestMapping(value ="/createmeet")
	public String createmeet(HttpServletRequest res){
		logger.info("接受createmeet请求，");
		//saveToken(res);
		return "editmeet";
	}	
	
	/**
		修改meet
	 */
	@RequestMapping(value ="/editmeet")
	public ModelAndView editmeet(HttpServletRequest res){
		logger.info("接受editmeet请求，");
		String roomid = (String) res.getParameter("roomid");
		String userid = SessionManager.getUserSession(res);
		ModelAndView mov = new ModelAndView();
		
		List<MeetLiveBase>  meetLiveBaseList =  meetBaseServer.getMeetLiveListByRoomidAndUserId(userid, roomid);
		if (meetLiveBaseList ==null || meetLiveBaseList.size() == 0 ){
			mov.setViewName("errPage");
			return mov;
		}else{
//			mov.addObject("data",meetLiveBaseList.get(0));
		}
		mov.addObject("roomid", roomid);
		mov.setViewName("editmeet");
		return mov;
	}
}
