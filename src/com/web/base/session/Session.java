package com.web.base.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.model.Userinfo;


/**
 * 会话信息
 */
public interface Session {
    String getFrom();

    void setFrom(String from);
    
    /**
     * CID
     * 
     * @return
     */
    public String getCid();

    /**
     * 会话ＩＤ
     * 
     * @return
     */
    public String getId();

    /**
     * 用户ID
     * 
     */
    public String getUserId();

 

    /**
     * 获取会话相关信息
     * 
     * @param group
     * @param key
     * @return
     */
    public Object get(String group, String key);

    /**
     * 删除会话指定组相关信息
     * 
     * @param group
     */
    public void remove(String group);

    /**
     * 职务
     * 
     * @return
     */
    public String getJobTitle();

    public String getDepartment();

   
    /**
     * 设置会话相关信息
     * 
     * @param group
     * @param key
     * @param value
     */
    public void set(String group, String key, Object value);

    /**
     * 将修改过的会话相关信息进行持久化存储
     */
    public void flush();

    /**
     * 网络名称
     * 
     * @return
     */
    public String getNetworkName();

    /**
     * EMAIL
     * 
     * @return
     */
    public String getEmail();

    /**
     * 用户姓名
     * 
     * @return
     */
    public String getUserName();

 

    /**
     * 个人头像ID
     * 
     * @return
     */
    public String getPhotoId();


    /**
     * 是否管理员
     * 
     * @return
     */
    public boolean isAdmin();

    public String setCid();
    
    public Userinfo getUserInfo();
    
    public Userinfo setUserInfo();
	
}