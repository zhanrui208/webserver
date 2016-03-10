package com.web.common.base;

import java.util.Map;

public interface IBase {

	public void logError(Object error,Object errorCode,Object message);
	
	public void logError(Object obj,Throwable e);
	
	public void logInfo(Object obj);	
	
	public void addMessage(String error,int errorCode,String message,Map<String,Object> map)throws Exception;
	
	public void addError(String error,int errorCode,String message,Map<String,Object> map)throws Exception;
}
