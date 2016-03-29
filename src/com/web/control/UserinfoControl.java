package com.web.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	Logger logger =LoggerFactory.getLogger("com.web.control.rest.UserinfoControl");
	
	@Autowired
	UserinfoServer userinfoServer;
	
	@RequestMapping(value ="/login")
	public String login(){
		logger.info("接受login请求");
		return "login";
	}
	
	@RequestMapping(value ="/regedit")
	public String regedit(HttpServletRequest res){
		logger.info("接受regedit请求");
		saveToken(res);
		return "regedit";
	}

	@RequestMapping(value ="/resetpwd")
	public String resetPwd(HttpServletRequest res){
		logger.info("接受resetpwd请求");
		saveToken(res);
		return "resetpwd";
	}
	
	@RequestMapping(value ="/upuserinfo")
	public String upuserinfo(){
		logger.info("接受upuserinfo请求");
		return "upuserinfo";
	}
	
	/**
	 * 
	 * @param res
	 * @return
	 */
	@RequestMapping(value ="/userhome")
	public String userhome(HttpServletRequest res){
		logger.info("接受userhome请求");
		return "userhome";
	}
	
	/**
	 * 忘记密码，跳转到密码修改界面
	 * @param res
	 * @return
	 */
	@RequestMapping(value ="/forgetpwd")
	public String forgetpwd(HttpServletRequest res){
		logger.info("接受forgetpwd请求");
		saveToken(res);
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
		logger.info("接受activateUser请求，username="+username);
		Map<String, Object> map = initMessage();
		try {
			byte[] userbyte = Coder.decryptBASE64(username);
			username = new String(userbyte, Constant.UTF_8);
			userinfoServer.activateUser(username, randomNum, map);
			if ((boolean) map.get("success")){
				Map<String,Object> modelMap = new HashMap<String,Object>();
				modelMap.put("info","已成功激活该账号，请登录！");
				model.addAllAttributes(modelMap);
				
				return "activeInfo";
			}else{
				model.addAllAttributes(map);
				return "activeInfo";
			}
			
		} catch (Exception e) {
			processError(map, e);
			model.addAllAttributes(map);
			return "activeInfo";
		}
	}
	@RequestMapping(value = "/updatepwd")
	public ModelAndView UpdatePwd(HttpServletRequest res,String rand,String token) {
		logger.info("接受updatepwd请求，rand="+rand);
		Map<String, Object> map = initMessage();
		ModelAndView mov = new ModelAndView();
		try {
			//验证token
			if (!checkToken(res,token)) {
				map.put("error", "不能重复提交");
				map.put("errorCode", 500);
				mov.setViewName("errPage");
			}			
			String username = (String) res.getSession().getAttribute(rand);
			res.getSession().setAttribute("username", username);
			if (StringUtils.isEmpty(username)){
				map.put("error", "修改密码超时");
				map.put("errorCode", 500);
				mov.setViewName("errPage");
			}else{
				mov.setViewName("updatepwd");
				mov.addObject("username", username);
			}
			
		} catch (Exception e) {
			processError(map, e);
		}
		mov.addObject("data", map);
		return mov;
	}
	
	
	/**
		创建meet
	 */
	@RequestMapping(value ="/createmeet")
	public String createmeet(HttpServletRequest res){
		logger.info("接受createmeet请求，");
		saveToken(res);
		return "editmeet";
	}
	
	/**
		修改meet
	 */
	@RequestMapping(value ="/editmeet")
	public ModelAndView editmeet(HttpServletRequest res){
		logger.info("接受editmeet请求，");
		ModelAndView mov = new ModelAndView();
		saveToken(res);
		mov.addObject("meet", "1");
		mov.setViewName("editmeet");
		return mov;
	}
}
