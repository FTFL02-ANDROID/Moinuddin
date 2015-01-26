package com.ftfl.googlemap.util;

public class SComplexInfo {
	
	Integer id = 0;
	String name = null;
	String address = null;
	String description = null;
	double latitude = 0.0;
	double longitude = 0.0;
	String closeDay = null;
	String openTime = null;
	
	public SComplexInfo(String name, String address, String description,
			double latitude, double longitude, String closeDay, String openTime) {
		
		this.name = name;
		this.address = address;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.closeDay = closeDay;
		this.openTime = openTime;
	}

	public SComplexInfo(Integer id, String name, String address,
			String description, double latitude, double longitude,
			String closeDay, String openTime) {
		
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.closeDay = closeDay;
		this.openTime = openTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCloseDay() {
		return closeDay;
	}

	public void setCloseDay(String closeDay) {
		this.closeDay = closeDay;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

}
