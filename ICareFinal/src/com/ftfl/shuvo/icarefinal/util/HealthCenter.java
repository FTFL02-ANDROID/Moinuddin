package com.ftfl.shuvo.icarefinal.util;

public class HealthCenter {
	
	Integer mId = 0;
	String mName = "";
	String mAddress = "";
	String mPhone = "";
	Double mLat = 0.00;
	Double mLang = 0.00;
	
	public HealthCenter(Integer eId, String eName, String eAddress, String ePhone,
			Double eLat, Double eLang) {
		this.mId = eId;
		this.mName = eName;
		this.mAddress = eAddress;
		this.mPhone = ePhone;
		this.mLat = eLat;
		this.mLang = eLang;
	}

	public HealthCenter(String eName, String eAddress, String ePhone,
			Double eLat, Double eLang) {
		this.mName = eName;
		this.mAddress = eAddress;
		this.mPhone = ePhone;
		this.mLat = eLat;
		this.mLang = eLang;
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

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public Double getLat() {
		return mLat;
	}

	public void setLat(Double mLat) {
		this.mLat = mLat;
	}

	public Double getLang() {
		return mLang;
	}

	public void setLang(Double mLang) {
		this.mLang = mLang;
	}
	
	

}
