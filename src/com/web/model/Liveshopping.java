package com.web.model;

import com.web.dao.IBean;
/**
 * 支付
 * @author Administrator
 *
 */
public class Liveshopping implements IBean{
	private int roomID;
	private String recordOnline;//是否在线录制
	private String uploadFile;//是否支持上传文件
	private String  createTimer;
	private String  processFlag;//处理标志，0为未处理，1为处理，下单时状态为未处理，即默认为0
	private int money;
	private int codeRate;
	private String webUserName;
	private String webPassword;
	private String updateTimer;
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getCodeRate() {
		return codeRate;
	}
	public void setCodeRate(int codeRate) {
		this.codeRate = codeRate;
	}
	public String getWebUserName() {
		return webUserName;
	}
	public void setWebUserName(String webUserName) {
		this.webUserName = webUserName;
	}
	public String getWebPassword() {
		return webPassword;
	}
	public void setWebPassword(String webPassword) {
		this.webPassword = webPassword;
	}
	public String getUpdateTimer() {
		return updateTimer;
	}
	public void setUpdateTimer(String updateTimer) {
		this.updateTimer = updateTimer;
	}
	
}
