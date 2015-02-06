package com.ftfl.locationdistance;

public class LocationProfile {
	
	Integer mId = 0;
	Double mLatitude = 0.0;
	Double mLongitude = 0.0;
	String mRemark = "";
	String mPhoto = "";
	String mDate = "";
	String mTime = "";
	
	public LocationProfile(){
	}
	public LocationProfile(Integer eId, Double eLatitude, Double eLongitude,
			String eRemark, String ePhoto, String eDate, String eTime) {
		this.mId = eId;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mRemark = eRemark;
		this.mPhoto = ePhoto;
		this.mDate = eDate;
		this.mTime = eTime;
	}
	public LocationProfile(Double eLatitude, Double eLongitude, String eRemark,
			String ePhoto, String eDate, String eTime) {
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mRemark = eRemark;
		this.mPhoto = ePhoto;
		this.mDate = eDate;
		this.mTime = eTime;
	}
	public Integer getId() {
		return mId;
	}
	public void setId(Integer eId) {
		this.mId = eId;
	}
	public Double getLat() {
		return mLatitude;
	}
	public void setLatitude(Double eLatitude) {
		this.mLatitude = eLatitude;
	}
	public Double getLongi() {
		return mLongitude;
	}
	public void setLongitude(Double eLongitude) {
		this.mLongitude = eLongitude;
	}
	public String getRemark() {
		return mRemark;
	}
	public void setRemark(String eRemark) {
		this.mRemark = eRemark;
	}
	public String getPhoto() {
		return mPhoto;
	}
	public void setPhoto(String ePhoto) {
		this.mPhoto = ePhoto;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String eDate) {
		this.mDate = eDate;
	}
	public String getTime() {
		return mTime;
	}
	public void setTime(String eTime) {
		this.mTime = eTime;
	}
	
	

}
