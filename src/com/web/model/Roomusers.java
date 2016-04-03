package com.web.model;

import com.web.dao.IBean;

public class Roomusers implements IBean{
	private int roomID;
	private int userID;
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
	
}
