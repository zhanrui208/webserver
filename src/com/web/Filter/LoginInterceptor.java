package com.web.Filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.base.session.SessionManager;

/**
* 登陆拦截器.
*
* @author
*/
public class LoginInterceptor extends HandlerInterceptorAdapter {
   private static final String[] IGNORE_URI = {"","/","home","videomeet","about","login", "regedit","activateUser","updatepwd"};

   @Override
   public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
       boolean flag = false;
       String url = req.getRequestURL().toString();
       System.out.println(">>>: " + url);
       for (String s : IGNORE_URI) {
           if (url.contains(s)) {
               flag = true; //不需要验证的写在这里
               break;
           }
       }
       if (!flag) {
    	   String Userid  = (String) req.getSession().getAttribute(SessionManager.CURRENT_SESSION_ATTR);
    	   if (StringUtils.isEmpty(Userid)){ //判断有没有登录
    		   res.sendRedirect("account/login");
    		   flag= false;
    	   }
    	   //cookie验证方式
    	   if  (SessionManager.checkRememberMe(req, res)){
    		   flag= true;
    	   }
       }
       return flag;
   }

   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       super.postHandle(request, response, handler, modelAndView);
   }  
}