package com.web.common.base.Imp;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.common.base.IBase;



public class Base implements IBase{
	
	private static Logger logger = LoggerFactory.getLogger(Base.class);
	
	public void logError(Object error,Object errorCode,Object message){
		
		logger.error(error+"["+errorCode+"/"+message+"]");
	}
	
	public void logError(Object obj,Throwable e){
		
		String msg = null;
		if(obj != null){
			msg = obj.toString();
		}
		logger.error(msg,e);
	}
	
	public void logInfo(Object obj){
		
		if(obj == null){

			return;
		}
		logger.info(obj.toString());
	}

	public void addMessage(String error,int errorCode,String message,
			Map<String,Object> map)throws Exception{
		
		map.put("error",error);
		map.put("errorCode", errorCode);
		logger.info(error+"["+errorCode+"/"+message+"]");
	}
	
	public void addError(String error,int errorCode,String message,
			Map<String,Object> map)throws Exception{
		map.put("success",false);
		map.put("error",error);
		map.put("errorCode", errorCode);
		logger.error(error+"["+errorCode+"/"+message+"]");
	}
}
