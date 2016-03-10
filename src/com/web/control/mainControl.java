package com.web.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.server.mainServer;

@Controller
public class mainControl {
	
	@Autowired
	mainServer mainserver;
		
	@RequestMapping(value = { "", "/", "/home" })
	public String showhome(){
		return "home";
	}
	
	@RequestMapping(value ="/videomeet")
	public String showvideomeet(){
		return "videomeet";
	}
	
	@RequestMapping(value ="/about")
	public String showabout(){
		return "about";
	}
	

	
	
	public  String sendMailto(){
//		String res= mainserver.sendmainto();
		return "";
	}
}
