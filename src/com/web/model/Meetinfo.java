package com.web.model;

import com.web.dao.IBean;
/**
 * 会议室基本信息
 * @author Administrator
 *
 */
public class Meetinfo implements IBean{
	private int roomID;
	private String roomName;
	private  int maxUserCount;
	private String isSupportLive;//是否支持直播
	private String isSupportMediaUpload;//是否支持在线录制
	private String businessinfoID; //1为超清，2为高清，5标清
	private String  hopeStartTime;//开始时间
	private String hopeEndTime; //会议室结束时间
	private int departID;//必填项
	private float LiveBalance;//必填项
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public int getMaxUserCount() {
		return maxUserCount;
	}
	public void setMaxUserCount(int maxUserCount) {
		this.maxUserCount = maxUserCount;
	}
	public String getIsSupportLive() {
		return isSupportLive;
	}
	public void setIsSupportLive(String isSupportLive) {
		this.isSupportLive = isSupportLive;
	}
	public String getIsSupportMediaUpload() {
		return isSupportMediaUpload;
	}
	public void setIsSupportMediaUpload(String isSupportMediaUpload) {
		this.isSupportMediaUpload = isSupportMediaUpload;
	}
	public String getBusinessinfoID() {
		return businessinfoID;
	}
	public void setBusinessinfoID(String businessinfoID) {
		this.businessinfoID = businessinfoID;
	}
	public String getHopeStartTime() {
		return hopeStartTime;
	}
	public void setHopeStartTime(String hopeStartTime) {
		this.hopeStartTime = hopeStartTime;
	}
	public String getHopeEndTime() {
		return hopeEndTime;
	}
	public void setHopeEndTime(String hopeEndTime) {
		this.hopeEndTime = hopeEndTime;
	}
	public int getDepartID() {
		return departID;
	}
	public void setDepartID(int departID) {
		this.departID = departID;
	}
	public float getLiveBalance() {
		return LiveBalance;
	}
	public void setLiveBalance(float liveBalance) {
		LiveBalance = liveBalance;
	}
	
	
}
