package com.web.model;

import java.sql.Time;

import org.joda.time.DateTime;

import com.web.common.RandomGenerator;
import com.web.dao.IBean;

public class Userregedit  implements IBean{
	private long UserID;
	private String UserName;
	private String Password;
	private String DisplayName;
	private String Mobile;
	private String EMail;
	private String RandomNum;
	private String  Createdate;
	
	public long getUserID() {
		return UserID;
	}
	public void setUserID(long userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getEMail() {
		return EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	public String getRandomNum() {
		return RandomNum;
	}
	public void setRandomNum(String randomNum) {
		RandomNum = randomNum;
	}
	public String getCreatedate() {
		return Createdate;
	}
	public void setCreatedate(String createdate) {
		Createdate = createdate;
	}
	
}
