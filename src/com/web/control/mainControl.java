package com.web.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.control.base.Imp.BaseController;
import com.web.server.mainServer;

@Controller
public class mainControl extends BaseController{
	
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
	

	@RequestMapping(value ="/test")
	protected String handleRequestInternal() throws Exception { 
		ModelAndView model = new ModelAndView(); 
		model.addObject("msg", "hello world"); 
		return "test"; 
	} 
	
	@RequestMapping(value ="/test1")
	protected String handleRequestInternal1(ModelMap model) throws Exception { 
		model.addAttribute("msg", "hello world"); 
		return "test"; 
	} 
	
	@RequestMapping(value ="/test2")
	protected String handleRequestInternal2(ModelAndView model) throws Exception { 
		model.addObject("msg", "hello world"); 
		return "test"; 
	} 
	

}
