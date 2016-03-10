package com.web.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.base.session.Session;
import com.web.base.session.SessionManager;
import com.web.model.Userinfo;
import com.web.server.UserinfoServer;

@Controller
public class UserinfoControl {	
	
	
	@RequestMapping(value ="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value ="/regedit")
	public String regedit(Userinfo userinfo){
		return "regedit";
	}

	@RequestMapping(value ="/resetpwd")
	public String resetPwd(){
		return "resetpwd";
	}
	
	@RequestMapping(value ="/upuserinfo")
	public String upuserinfo(){
		return "upuserinfo";
	}
	
	@RequestMapping(value ="/userhome")
	public String userhome(HttpServletRequest res){
		String userid =SessionManager.getUserSession(res);
		System.out.println("userid:" +userid);
		
		return "userhome";
	}
	

}
