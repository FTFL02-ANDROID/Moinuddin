package com.ftfl.shuvo.icarefinal.util;

public class VaccineProfile {

	Integer mId = 0;
	String mName = "";
	String mReason = "";
	String mDate = "";
	String mTime = "";

	public VaccineProfile(Integer eId, String eName, String eReason, String eDate,
			String eTime) {
		this.mId = eId;
		this.mName = eName;
		this.mReason = eReason;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public VaccineProfile(String eName, String eReason, String eDate,
			String eTime) {
		this.mName = eName;
		this.mReason = eReason;
		this.mDate = eDate;
		this.mTime = eTime;
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

	public String getReason() {
		return mReason;
	}

	public void setReason(String mReason) {
		this.mReason = mReason;
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

}
