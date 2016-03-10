package com.web.control.base.Imp;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;

import com.web.common.base.Imp.Base;
import com.web.control.base.IBaseController;



public class BaseController extends Base implements IBaseController{

	public Map<String,Object> initMessage(){
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("success", true);
		map.put("error", null);
		map.put("errorCode", 100);
		map.put("data", null);
		return map;
	}
	
	public void processError(Map<String,Object> map,Throwable e){
		
		if(StringUtils.isEmpty((String)map.get("error"))){
			if(e != null && e.getMessage() != null){
				
				map.put("error",e.getMessage());				
			}else if(e != null && e.getCause() != null){
				
				map.put("error",e.getCause().getMessage());
			}else if(e != null){
				map.put("error",e.toString());
			}
			map.put("errorCode", 1);
		}
		map.put("success", false);
		logError("出现错误了^^:",e);
	}
	
	public String ERROR(Map<String,Object> map){		

		return JSONObject.fromObject(map).toString();
	}
	
	public JSONObject ERRORJson(Map<String,Object> map){		

		return JSONObject.fromObject(map);
	}
	
	public String SUCCESS(Map<String,Object> map){

		return JSONObject.fromObject(map).toString();
	}

	public JSONObject SUCCESSJson(Map<String,Object> map){

		return JSONObject.fromObject(map);
	}
	
	public Object getService(String name){
		
		return ContextLoader.getCurrentWebApplicationContext().getBean(name);
	}
    protected boolean ASSERTNOTBLANK(Object ... os){
		for(Object o:os){
			if(o==null){
				return false;
			}else{
				if(org.apache.commons.lang.StringUtils.isBlank(o.toString())){
					return false;
				}
			}
		}
		return true;
	}
    protected boolean ASSERTLENGTH(int minlength,int maxlength,Object ... os){
		for(Object o:os){
			if(o==null){
				return false;
			}else{
				if(org.apache.commons.lang.StringUtils.isBlank(o.toString())){
					return false;
				}else{
					if(o.toString().length()>maxlength || o.toString().length()<minlength){
						return false;
					}
				}
			}
		}
		
		return true;
	}
	protected void ERROR(String error,int errorCode,Map<String,Object> map){
		map.put("success",false);
		map.put("error",error);
		map.put("errorCode", errorCode);
	}
	
}