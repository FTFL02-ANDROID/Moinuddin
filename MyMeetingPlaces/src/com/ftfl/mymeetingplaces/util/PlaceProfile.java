package com.ftfl.mymeetingplaces.util;

public class PlaceProfile {

	Integer mId = 0;
	Double mLatitude = 0.00;
	Double mLongitude = 0.00;
	String mPlaceName = "";
	String mPhoto = "";
	String mDate = "";
	String mTime = "";
	String mName = "";
	String mNumber = "";
	String mEmail = "";

	public PlaceProfile() {
	}

	public PlaceProfile(Integer eId, Double eLatitude, Double eLongitude,
			String ePlaceName, String ePhoto, String eDate, String eTime, String eName, String eNumber, String eEmail) {
		this.mId = eId;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mPlaceName = ePlaceName;
		this.mPhoto = ePhoto;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mName = eName;
		this.mNumber = eNumber;
		this.mEmail = eEmail;
	}

	public PlaceProfile(Double eLatitude, Double eLongitude, String ePlaceName,
			String ePhoto, String eDate, String eTime, String eName, String eNumber, String eEmail) {
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mPlaceName = ePlaceName;
		this.mPhoto = ePhoto;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mName = eName;
		this.mNumber = eNumber;
		this.mEmail = eEmail;
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

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getNumber() {
		return mNumber;
	}

	public void setNumber(String mNumber) {
		this.mNumber = mNumber;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

}
