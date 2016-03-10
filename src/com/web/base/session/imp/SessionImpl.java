package com.web.base.session.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.code.morphia.annotations.Transient;
import com.web.base.session.Session;
import com.web.model.Userinfo;

public class SessionImpl implements Session{
    private static final long serialVersionUID = 2101336536987318518L;

    private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);

    private String userId;
    private String currentTenantId;
    private String loginUserId;

    private String cId;


    
    @Transient
    private boolean rememberMe;
    

    @Transient
    private Map<String, Map<String,Object>> items = new HashMap<String, Map<String,Object>>();
    
    @Transient
    private String jobTitle = null;
    @Transient
    private String department = null;
	@Override
	public String getFrom() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setFrom(String from) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getCid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object get(String group, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void remove(String group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getJobTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDepartment() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void set(String group, String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getNetworkName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPhotoId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String setCid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Userinfo getUserInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Userinfo setUserInfo() {
		// TODO Auto-generated method stub
		return null;
	}
    
	
    
    
}
