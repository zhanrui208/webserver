package com.web.control.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
	Logger logger =LoggerFactory.getLogger("com.web.control.rest.UserinfoControlrest");
	
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
	public String dologin(HttpServletRequest req,
			HttpServletResponse res, String username, String password,
			@RequestParam(defaultValue = "0") String remember) {
		logger.info("登陆/dologin请求，username={},password={}",username,password);
		Map<String, Object> map = initMessage();
		try {
			if (userinfoServer.login(username, password, map)) {
				Userinfo userinfo = userinfoServer.getUserinfo(username);
				SessionManager.saveUserSession(req, userinfo.getUserID() + "");
				SessionManager.saveSession(req, "username", username);
				if (remember.equals("1")) {// 记住密码参数
					SessionManager.setUserCookie(req, res, userinfo, true);
				}
			}
		} catch (Exception e) {
			processError(map, e);
			logger.error("dologin:err:"+e.getMessage());
		}
		
		return SUCCESS(map);
	}

	/**
	 * 注销
	 * @param req
	 * @param response
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dologout", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String dologout(HttpServletRequest req,
			HttpServletResponse response, String username) {
		logger.info("注销/dologout请求，username={}",username);
		Map<String, Object> map = initMessage();
		try {
			SessionManager.logout(req);
			SessionManager.removeSession(req, "username");
		} catch (Exception e) {
			processError(map, e);
			logger.error("dologin:err:"+e.getMessage());
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
		logger.info("注册/doregedit请求，username={}",userregedit.getUserName());
		Map<String, Object> map = initMessage();
		try {
			//验证token
			if (!checkToken(res,token)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}

			if (!userinfoServer.checkUser(userregedit.getUserName())) {
				userinfoServer.regedit(userregedit, map);
				if ((boolean) map.get("success")){
					clearToken(res);
				}
			} else {
				map.put("error", "该用户名已被注册,请重新输入！");
				map.put("errorCode", 500);
			}
		} catch (Exception e) {
			processError(map, e);
			logger.error("doregedit:err:"+e.getMessage());
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
		logger.info("修改密码/doresetpwd请求，username={},password={}",username,newpassword);
		Map<String, Object> map = initMessage();
		try {
			//验证token
			if (!checkToken(res,token)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			SessionManager.removeSession(res, "toekn");
			userinfoServer.resetpwd(username, oldpassword, newpassword, map);
			if ((boolean) map.get("success")){
				clearToken(res);
			}
		} catch (Exception e) {
			processError(map, e);
			logger.error("doresetpwd:err:"+e.getMessage());
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
		logger.info("验证用户/checkuser请求，username={}",username);
		Map<String, Object> map = initMessage();
		try {
			userinfoServer.checkUser(username, map);	
		} catch (Exception e) {
			processError(map, e);
			logger.error("checkuser:err:"+e.getMessage());
		}
		return SUCCESSJson(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/doforgetpwd",produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public String doforgetpwd(HttpServletRequest res,String username,String code,String token) {
		logger.info("忘记密码/doforgetpwd请求，username={}",username);
		Map<String, Object> map = initMessage();
		try {
//			//验证token
//			if (!checkToken(res,token)) {
//				map.put("error", "不能重复提交");
//				map.put("errorCode", 500);
//				return SUCCESS(map);
//			}
			
			String usernameTemp = (String) res.getSession().getAttribute("username");
			if (StringUtils.isEmpty(usernameTemp)){
				map.put("error", "验证码已超时！");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			if (!usernameTemp.equals(username)){
				map.put("error", "邮箱不匹配！");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
		
			String checkcode = (String) res.getSession().getAttribute("code");
			if (checkcode == null){
				map.put("error", "验证码已超时！");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
			if (!code.equals(checkcode)){
				map.put("error", "验证码错误！");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			String rand =RandomGenerator.genRandomNum(20);
			res.getSession().setAttribute(rand, username);
			map.put("data", "updatepwd?rand="+rand);
			clearToken(res);	
		} catch (Exception e) {
			processError(map, e);
			logger.error("doforgetpwd:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}	
	
	@ResponseBody
	@RequestMapping(value = "/sendcode", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public Object sendCode(HttpServletRequest res,String username,String token) {
		logger.info("发送验证码/sendcode请求，username={}",username);
		Map<String, Object> map = initMessage();
		try {
			//验证token
			if (!checkToken(res,token)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
			if (!userinfoServer.checkUser(username)){
				map.put("error", "用户不存在");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
			String code=RandomGenerator.genRandomNum(6);
			res.getSession().setMaxInactiveInterval(2*60);//超时2分钟
			res.getSession().setAttribute("code", code);
			res.getSession().setAttribute("username", username);
			
			String email=username;
			userinfoServer.sendCode(email,code,map);
			if ((boolean) map.get("success")){
				clearToken(res);
			}else{
				map.put("error", "修改密码失败");
				map.put("errorCode", 500);
			}
		} catch (Exception e) {
			processError(map, e);
			logger.info("sendcode:err:"+e.getMessage());
		}
		return SUCCESS(map);
	}	
	

	
	/**
	 * 更新密码
	 * @param res
	 * @param username
	 * @param randomNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doupdatepwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public Object doUpdatePwd(HttpServletRequest res,String username,String password,String token) {
		logger.info("更新密码/doupdatepwd请求，username={}，password={}",username,password);
		Map<String, Object> map = initMessage();
		try {
//			//验证token
//			if (!checkToken(res,token)) {
//				map.put("error", "不能重复提交");
//				map.put("errorCode", 500);
//				return SUCCESS(map);
//			}
			String usernameTemp = (String) res.getSession().getAttribute("username");
			if (StringUtils.isEmpty(username)){
				map.put("error", "修改密码超时失败");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
			if (!usernameTemp.equals(username)){
				map.put("error", "用户名发生未知错误！");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
			
			if (userinfoServer.updatepwd(username, password)){
				clearToken(res);
			}else{
				map.put("error", "修改密码失败");
				map.put("errorCode", 500);
				return SUCCESS(map);
			}
		} catch (Exception e) {
			logger.info("doupdatepwd:err:"+e.getMessage());
			processError(map, e);
		}
		return SUCCESS(map);
	}
	
}
