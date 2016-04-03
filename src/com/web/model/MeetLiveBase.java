package com.web.model;

import com.web.dao.IBean;
/**
 * 会议室基本信息和支付信息
 * @author Administrator
 *
 */
public class MeetLiveBase  implements IBean{
	private int roomID;
	private int userID;
	private String roomName;
	private String isSupportLive;//是否支持直播
	private String isSupportMediaUpload;//是否支持在线录制
	private String businessinfoID; //1为超清，2为高清，5标清
	private String  hopeStartTime;//开始时间
	private String hopeEndTime; //会议室结束时间
	private String recordOnline;//是否在线录制
	private String uploadFile;//是否支持上传文件
	private String  createTimer;//创建时间
	private String  processFlag;//处理标志，0为未处理，1为处理，下单时状态为未处理，即默认为0
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	public String getRecordOnline() {
		return recordOnline;
	}
	public void setRecordOnline(String recordOnline) {
		this.recordOnline = recordOnline;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getCreateTimer() {
		return createTimer;
	}
	public void setCreateTimer(String createTimer) {
		this.createTimer = createTimer;
	}
	public String getProcessFlag() {
		return processFlag;
	}
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
	
	
}
