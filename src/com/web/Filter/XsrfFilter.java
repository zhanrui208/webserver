package com.web.Filter;



import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 防御XSRF糯虫,目前只用判断header中的Origin进行简单判断
 * @author rui_zhan
 * 2016-02-16
 */
public class XsrfFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(XsrfFilter.class);
    private static List<String> URISlist = new LinkedList<String>(); 

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        if (check((HttpServletRequest) request)) {
        	chain.doFilter(request, response);	
        }
	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//只对下面几个请求进行拦截
		URISlist.add("/microblog/rest/microblog/send");
		URISlist.add("/microblog/rest/like/add");
		URISlist.add("/microblog/rest/like/remove");
	}
	
	/**
	 * 判断Origin是否是本机的地址
	 * @param request
	 * @return
	 */
	private boolean check(HttpServletRequest request){
		String  URI = request.getRequestURI();	
		if (hasURI(URI)){
			String RequestURL =request.getRequestURL().toString();
			int start  = RequestURL.indexOf(URI);
			if (start<0) return false;
			
			String host = RequestURL.substring(0,start);
			//必须POST请求
			if  (!StringUtils.isEmpty(request.getHeader("Origin"))&&!request.getHeader("Origin").equals(host)){
				return false;
			}
		}	
		return true;
	}

	private boolean hasURI(String s){
		if(URISlist.contains(s)){
			return true;
		}else{
			return false;
		}
	}
}
