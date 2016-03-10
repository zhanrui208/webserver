package com.web.control.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.base.session.SessionManager;
import com.web.control.base.Imp.BaseController;
import com.web.model.Userinfo;
import com.web.server.UserinfoServer;

@Controller
@RequestMapping("/rest")
public class UserinfoControlrest extends BaseController {
	@Autowired
	UserinfoServer userinfoServer;

	/**
	 * 默认不记住密码
	 * @param req
	 * @param response
	 * @param username
	 * @param password
	 * @param remember
	 * @return
	 */
	@RequestMapping(value = "/dologin")
	@ResponseBody
	public Object checklogin(HttpServletRequest req,
			HttpServletResponse response, String username, String password,
			@RequestParam(defaultValue="0") String remember) {
		Map<String, Object> map = initMessage();
		try {
			userinfoServer.login(username, password, map);
			if ((boolean) map.get("success")) {
				Userinfo userinfo = userinfoServer.getUserinfo(username);
				SessionManager.saveUserSession(req,
						userinfo.getUserID() + "");
				if (remember.equals("1")) {// 记住密码参数
					SessionManager.setUserCookie(req, response, userinfo, true);
				}

			}
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESSJson(map);
	}
	

	

	/**
	 * 注册
	 * 
	 * @param userinfo
	 * @return
	 */
	@ResponseBody  
	@RequestMapping(value = "/doregedit")
	public String checkregedit(Userinfo userinfo) {
		Map<String, Object> map = initMessage();
		try {
			userinfoServer.regedit(userinfo, map);
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
	}


	
	/**
	 * 修改密码
	 * 
	 * @param username
	 * @param oldpassword
	 * @param newpassword
	 * @return
	 */
	@RequestMapping(value = "/doresetpwd")
	public String doresetPwd(String username, String oldpassword,
			String newpassword) {
		Map<String, Object> map = initMessage();
		userinfoServer.resetpwd(username, oldpassword, newpassword, map);
		return "doresetpwd";
	}

	@RequestMapping(value = "/doupuserinfo")
	public String doupuserinfo() {
		return "doregedit";
	}
	
	
	@ResponseBody 
	@RequestMapping(value = "/checkuser")
	public Object checkUser(String username) {
		Map<String, Object> map = initMessage();
		try {
			userinfoServer.checkUser(username, map);
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESSJson(map);
	}

	
}
