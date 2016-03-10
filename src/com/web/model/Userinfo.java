package com.web.model;

import org.springframework.stereotype.Repository;

import com.web.dao.IBean;
/**
 * DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `UserID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `UserName` varchar(64) NOT NULL,
  `Password` varchar(64) NOT NULL,
  `DisplayName` varchar(64) DEFAULT NULL,
  `Sex` char(1) DEFAULT '0',
  `Status` char(1) DEFAULT NULL,
  `Mobile` varchar(24) DEFAULT NULL,
  `TelePhone` varchar(24) DEFAULT NULL,
  `EMail` varchar(64) DEFAULT NULL,
  `UserLevel` char(1) DEFAULT '0',
  `DepartID` int(11) unsigned DEFAULT NULL,
  `NodeID` char(64) DEFAULT NULL,
  `AdminRole` char(1) DEFAULT NULL,
  `PwdQuestion` varchar(64) DEFAULT NULL,
  `PwdAnswer` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  KEY `UserID` (`UserID`)
) ENGINE=MyISAM AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8;

 * @author Administrator
 *
 */
public class Userinfo implements IBean{
	private long UserID;
	private String UserName;
	
	private String Password;
	private String DisplayName;
	private String Sex;
	private String Status;	
	private String Mobile;
	private String TelePhone;
	private String EMail;
	private String UserLevel;	
	private long DepartID;
	private String NodeID;
	private String AdminRole;
	private String PwdQuestion;	
	private String PwdAnswer;
	public Long getUserID() {
		return UserID;
	}
	public void setUserID(Long userID) {
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
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getTelePhone() {
		return TelePhone;
	}
	public void setTelePhone(String telePhone) {
		TelePhone = telePhone;
	}
	public String getEMail() {
		return EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	public String getUserLevel() {
		return UserLevel;
	}
	public void setUserLevel(String userLevel) {
		UserLevel = userLevel;
	}
	public long getDepartID() {
		return DepartID;
	}
	public void setDepartID(long departID) {
		DepartID = departID;
	}
	public String getNodeID() {
		return NodeID;
	}
	public void setNodeID(String nodeID) {
		NodeID = nodeID;
	}
	public String getAdminRole() {
		return AdminRole;
	}
	public void setAdminRole(String adminRole) {
		AdminRole = adminRole;
	}
	public String getPwdQuestion() {
		return PwdQuestion;
	}
	public void setPwdQuestion(String pwdQuestion) {
		PwdQuestion = pwdQuestion;
	}
	public String getPwdAnswer() {
		return PwdAnswer;
	}
	public void setPwdAnswer(String pwdAnswer) {
		PwdAnswer = pwdAnswer;
	}
	
	
	
}
