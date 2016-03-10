package com.web.control.base;

import java.util.Map;

public interface IBaseController {
	public Map<String,Object> initMessage();	
	
	public void processError(Map<String,Object> map,Throwable e);
	
	public Object ERROR(Map<String,Object> map);
	
	public Object SUCCESS(Map<String,Object> map);
	
	public Object getService(String name);
}
