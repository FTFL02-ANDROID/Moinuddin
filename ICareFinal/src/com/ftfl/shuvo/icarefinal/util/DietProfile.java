package com.ftfl.shuvo.icarefinal.util;

public class DietProfile {

	Integer mId = 0;
	String mName = "";
	String mMenu = "";
	String mDate = "";
	String mTime = "";
	String mAlarm = "";

	public DietProfile(Integer eId, String eName, String eMenu, String eDate,
			String eTime, String eAlarm) {
		this.mId = eId;
		this.mName = eName;
		this.mMenu = eMenu;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mAlarm = eAlarm;
	}

	public DietProfile(String eName, String eMenu, String eDate, String eTime,
			String eAlarm) {
		this.mName = eName;
		this.mMenu = eMenu;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mAlarm = eAlarm;
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getMenu() {
		return mMenu;
	}

	public void setMenu(String mMenu) {
		this.mMenu = mMenu;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String mTime) {
		this.mTime = mTime;
	}

	public String getAlarm() {
		return mAlarm;
	}

	public void setAlarm(String mAlarm) {
		this.mAlarm = mAlarm;
	}

}
