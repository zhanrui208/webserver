package com.web.base.session;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @since 2012-3-28
 * @author gzb
 */
public class CookieUtil {
    
    public static final String IP_ADDRESS_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    public static final String HOST_NAME_REGEX = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$";


    /**
     * 读取给定名称的COOKIE值，如果不存在，则返回NULL。
     * 
     * @param req
     * @param name
     * @return
     */
    public static String readCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
    
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 创建跨子域名的cookie
     * @param request
     * @param cookieName
     * @param cookieValue
     * @return
     */
    public static Cookie createCrossDomainCookie(HttpServletRequest request,String cookieName, String cookieValue){
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath("/");
        String domain = parseCookieTopDomain(request.getServerName());
        if(!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setHttpOnly(true);
        return cookie;
    }
    
    
    /**
     * 判断是否是子域
     * @param serverName
     * @return
     */
    public static boolean isSubDomain(String serverName){
        if(!StringUtils.isEmpty(serverName) && serverName.matches(HOST_NAME_REGEX)) {
            String[] fregments =  StringUtils.split(serverName, '.');
            if(fregments.length>1) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 将域名设置顶级域名上，兼容开发环境的机器名或ip模式

     * @param request
     * @return
     */
    private static String parseCookieTopDomain(String serverName) {
        if(isSubDomain(serverName)) {
            String[] fregments =  StringUtils.split(serverName, '.');
            StringBuffer topDomain= new StringBuffer();
            //FIXME 添加Guava的InternetDomainName处理之前,先用比较挫的方法解决紧急问题
            if(serverName.endsWith(".gov.cn") || serverName.endsWith(".edu.cn") || serverName.endsWith(".com.cn") || serverName.endsWith(".com.hk") || serverName.endsWith(".com.tw")){
                topDomain.append(".").append(fregments[fregments.length-3]).append(".").append(fregments[fregments.length-2])
                .append(".").append(fregments[fregments.length-1]);
            }else{
                topDomain.append(".").append(fregments[fregments.length-2])
                .append(".").append(fregments[fregments.length-1]);            	
            }
            return topDomain.toString();
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(parseCookieTopDomain("test.qianhong.cn"));
        System.out.println(parseCookieTopDomain("qianhong.com"));
        System.out.println(parseCookieTopDomain("192.168.1.221"));
        System.out.println(parseCookieTopDomain("localhost"));
    }
    

}
