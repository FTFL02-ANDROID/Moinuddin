package com.ftfl.shoppingcomplex.util;

public class SComplexInfo {

	Integer mId = 0;
	String mName = null;
	String mAddress = null;
	String mDescription = null;
	double mLatitude = 0.0;
	double mLongitude = 0.0;
	String mCloseDay = null;
	String mOpenTime = null;
	String mPhotoPath = null;

	public SComplexInfo(String name, String address, String description,
			double latitude, double longitude, String closeDay,
			String openTime, String ePhotoPath) {

		this.mName = name;
		this.mAddress = address;
		this.mDescription = description;
		this.mLatitude = latitude;
		this.mLongitude = longitude;
		this.mCloseDay = closeDay;
		this.mOpenTime = openTime;
		this.mPhotoPath = ePhotoPath;
	}

	public SComplexInfo(Integer id, String name, String address,
			String description, double latitude, double longitude,
			String closeDay, String openTime, String ePhotoPath) {

		this.mId = id;
		this.mName = name;
		this.mAddress = address;
		this.mDescription = description;
		this.mLatitude = latitude;
		this.mLongitude = longitude;
		this.mCloseDay = closeDay;
		this.mOpenTime = openTime;
		this.mPhotoPath = ePhotoPath;
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		this.mAddress = address;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public String getCloseDay() {
		return mCloseDay;
	}

	public void setCloseDay(String closeDay) {
		this.mCloseDay = closeDay;
	}

	public String getOpenTime() {
		return mOpenTime;
	}

	public void setOpenTime(String openTime) {
		this.mOpenTime = openTime;
	}

	public String getPhotoPath() {
		return mPhotoPath;
	}

	public void setPhotoPath(String ePhotoPath) {
		this.mPhotoPath = ePhotoPath;
	}

}
