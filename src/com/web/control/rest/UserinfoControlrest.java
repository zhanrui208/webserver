package com.web.control.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.base.Coder;
import com.web.base.Constant;
import com.web.base.session.SessionManager;
import com.web.common.RandomGenerator;
import com.web.control.base.Imp.BaseController;
import com.web.model.Userinfo;
import com.web.model.Userregedit;
import com.web.server.UserinfoServer;

@Controller
@RequestMapping("/rest")
public class UserinfoControlrest extends BaseController {
	@Autowired
	UserinfoServer userinfoServer;

	/**
	 * 默认不记住密码
	 * 
	 * @param req
	 * @param response
	 * @param username
	 * @param password
	 * @param remember
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dologin", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String checklogin(HttpServletRequest req,
			HttpServletResponse response, String username, String password,
			@RequestParam(defaultValue = "0") String remember) {
		Map<String, Object> map = initMessage();
		try {
			userinfoServer.login(username, password, map);
			if ((boolean) map.get("success")) {
				Userinfo userinfo = userinfoServer.getUserinfo(username);
				SessionManager.saveUserSession(req, userinfo.getUserID() + "");
				if (remember.equals("1")) {// 记住密码参数
					SessionManager.setUserCookie(req, response, userinfo, true);
				}
			}
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
	}

	/**
	 * 注册
	 * 
	 * @param userinfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doregedit", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String checkregedit(HttpServletRequest res, Userregedit userregedit,
			String token) {
		Map<String, Object> map = initMessage();
		try {
			String sessiontoken = SessionManager.getSession(res, "token");
			if (!token.equals(sessiontoken)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}

			if (!userinfoServer.checkUser(userregedit.getUserName())) {
				userinfoServer.regedit(userregedit, map);
				SessionManager.removeSession(res, "toekn");
			} else {
				map.put("error", "该用户名已被注册,请重新输入！");
				map.put("errorCode", 500);
			}
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
	@ResponseBody
	@RequestMapping(value = "/doresetpwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String doresetPwd(HttpServletRequest res, String username,
			String oldpassword, String newpassword, String token) {
		Map<String, Object> map = initMessage();
		try {
			String sessiontoken = SessionManager.getSession(res, "token");
			if (!token.equals(sessiontoken)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			SessionManager.removeSession(res, "toekn");
			userinfoServer.resetpwd(username, oldpassword, newpassword, map);
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
	}

	@ResponseBody
	@RequestMapping(value = "/doupdatepwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String doUpdatePwd(HttpServletRequest res, String username,
			String password, String token) {
		Map<String, Object> map = initMessage();
		try {
			String sessiontoken = SessionManager.getSession(res, "token");
			if (!token.equals(sessiontoken)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			if (userinfoServer.updatepwd(username, password)){
				SessionManager.removeSession(res, "token");
			}else{
				map.put("error", "更新密码失败");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
	}

	@ResponseBody
	@RequestMapping(value = "/doforgetpwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String doforgetpwd(HttpServletRequest res, String username,
			String token) {
		Map<String, Object> map = initMessage();
		try {
			String sessiontoken = SessionManager.getSession(res, "token");
			if (!token.equals(sessiontoken)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			SessionManager.removeSession(res, "token");
			userinfoServer.forgetpwd(username, username, map);
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
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


	
	/**
	 * 更新密码
	 * @param res
	 * @param username
	 * @param randomNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatepwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public Object updatePwd(HttpServletRequest res,String username,String password,String token) {
		Map<String, Object> map = initMessage();
		try {
			String sessiontoken = SessionManager.getSession(res, "token");
			if (!token.equals(sessiontoken)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			if (userinfoServer.updatepwd(username, password)){
				SessionManager.removeSession(res, "token");
			}else{
				map.put("error", "修改密码失败");
				map.put("errorCode", 500);
			}
		} catch (Exception e) {
			processError(map, e);
		}
		return SUCCESS(map);
	}
	
//	/**
//	 * 更新密码
//	 * @param res
//	 * @param username
//	 * @param randomNum
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/updatepwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
//	public ModelAndView updatePwd(HttpServletRequest res,String randomNum) {
//		ModelAndView model = new ModelAndView("updatepwd");
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			
//			String username = SessionManager.getSession(res, "username");
//			
//			
////			byte[] userbyte = Coder.decryptBASE64(username);
////			username = new String(userbyte, Constant.UTF_8);
//						
//			map.put("user", "username");
//			
//			model.addAllObjects(map);
//			
//			return model;
//		} catch (Exception e) {
//			processError(map, e);
//		}
//		return model;
//	}
}
