package com.ftfl.shuvo.icarefinal.util;

public class MedicalProfile {

	Integer mId = 0;
	String mName = "";
	String mPurpose = "";
	String mDate = "";
	String mTime = "";
	String mPhoto = "";

	public MedicalProfile(Integer eId, String eName, String ePurpose, String eDate,
			String eTime, String ePhoto) {
		this.mId = eId;
		this.mName = eName;
		this.mPurpose = ePurpose;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mPhoto = ePhoto;
	}

	public MedicalProfile(String eName, String ePurpose, String eDate,
			String eTime, String ePhoto) {
		this.mName = eName;
		this.mPurpose = ePurpose;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mPhoto = ePhoto;
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

	public String getPurpose() {
		return mPurpose;
	}

	public void setPurpose(String mPurpose) {
		this.mPurpose = mPurpose;
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

	public String getPhoto() {
		return mPhoto;
	}

	public void setPhoto(String mPhoto) {
		this.mPhoto = mPhoto;
	}

}
