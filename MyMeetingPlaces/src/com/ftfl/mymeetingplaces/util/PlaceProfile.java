package com.ftfl.mymeetingplaces.util;

public class PlaceProfile {

	Integer mId = 0;
	Double mLatitude = 0.00;
	Double mLongitude = 0.00;
	String mPlaceName = "";
	String mPhoto = "";
	String mDate = "";
	String mTime = "";

	public PlaceProfile() {
	}

	public PlaceProfile(Integer eId, Double eLatitude, Double eLongitude,
			String ePlaceName, String ePhoto, String eDate, String eTime) {
		this.mId = eId;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mPlaceName = ePlaceName;
		this.mPhoto = ePhoto;
		this.mDate = eDate;
		this.mTime = eTime;
	}

	public PlaceProfile(Double eLatitude, Double eLongitude, String ePlaceName,
			String ePhoto, String eDate, String eTime) {
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mPlaceName = ePlaceName;
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

	public void setLat(Double eLatitude) {
		this.mLatitude = eLatitude;
	}

	public Double getLongi() {
		return mLongitude;
	}

	public void setLongi(Double eLongitude) {
		this.mLongitude = eLongitude;
	}

	public String getPlaceName() {
		return mPlaceName;
	}

	public void setPlaceName(String eRemark) {
		this.mPlaceName = eRemark;
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
