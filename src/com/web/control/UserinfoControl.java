package com.web.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.web.base.Coder;
import com.web.base.Constant;
import com.web.base.session.Session;
import com.web.base.session.SessionManager;
import com.web.common.RandomGenerator;
import com.web.control.base.Imp.BaseController;
import com.web.model.Userinfo;
import com.web.server.UserinfoServer;

@Controller
public class UserinfoControl extends BaseController{	
	
	@Autowired
	UserinfoServer userinfoServer;
	
	@RequestMapping(value ="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value ="/regedit")
	public String regedit(HttpServletRequest res){
		String token = System.currentTimeMillis()+"";
		SessionManager.saveSession(res, "token","reg"+token);
		return "regedit";
	}

	@RequestMapping(value ="/resetpwd")
	public String resetPwd(HttpServletRequest res){
		String token = System.currentTimeMillis()+"";
		SessionManager.saveSession(res, "token","repwd"+token);
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
	
	
	@RequestMapping(value ="/forgetpwd")
	public String forgetpwd(HttpServletRequest res){
		String token = System.currentTimeMillis()+"";
		SessionManager.saveSession(res, "token","forgetpwd"+token);
		return "forgetpwd";
	}
	
	
	/**
	 * 邮箱激活页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activateUser")
	public String activateUser(HttpServletRequest req,ModelMap model, String username,
			String randomNum) {
		Map<String, Object> map = initMessage();
		try {
			byte[] userbyte = Coder.decryptBASE64(username);
			username = new String(userbyte, Constant.UTF_8);
			userinfoServer.activateUser(username, randomNum, map);
			if ((boolean) map.get("success")){
				String token = System.currentTimeMillis()+RandomGenerator.genRandomChar(8);
				
				Map<String,Object> modelMap = new HashMap<String,Object>();
				SessionManager.saveSession(req, "token", token);
				modelMap.put("username", username);
				modelMap.put("token", token);
				model.addAllAttributes(modelMap);
				
				return "updatepwd";
			}else{
				model.addAllAttributes(map);
				return "activeErr";
			}
			
		} catch (Exception e) {
			processError(map, e);
			model.addAllAttributes(map);
			return "activeErr";
		}
	}

//	@RequestMapping(value = "/updatepwd")
//	public ModelAndView updatepwd(String error){
//		Map<String, Object> map = initMessage();
//		ModelAndView mov = new ModelAndView();
//		map.put("error", error);
//		mov.addAllObjects(map);
//		mov.setViewName("updatepwd");
//		return mov;
//	}
//	
//	@RequestMapping(value = "/activeErr")
//	public String activeErr(Model model){
//		model.addAttribute("error", "你是个大坏蛋");
//		return "redirect:updatepwd";
//	}
}
