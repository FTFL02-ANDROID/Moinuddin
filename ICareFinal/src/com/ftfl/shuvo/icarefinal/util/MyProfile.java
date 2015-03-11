package com.ftfl.shuvo.icarefinal.util;

public class MyProfile {

	int mId = 0;
	String mName = "";
	String mAge = "";
	String mHeight = "";
	String mWeight = "";
	String mDiseases = "";
	String mPhone = "";
	
	public MyProfile(){
		
	}

	public MyProfile(int eId, String eName, String eAge, String eHeight,
			String eWeight, String eDiseases, String ePhone) {
		this.mId = eId;
		this.mName = eName;
		this.mAge = eAge;
		this.mHeight = eHeight;
		this.mWeight = eWeight;
		this.mDiseases = eDiseases;
		this.mPhone = ePhone;
	}

	public MyProfile(String eName, String eAge, String eHeight, String eWeight,
			String eDiseases, String ePhone) {
		this.mName = eName;
		this.mAge = eAge;
		this.mHeight = eHeight;
		this.mWeight = eWeight;
		this.mDiseases = eDiseases;
		this.mPhone = ePhone;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getAge() {
		return mAge;
	}

	public void setAge(String mAge) {
		this.mAge = mAge;
	}

	public String getHeight() {
		return mHeight;
	}

	public void setHeight(String mHeight) {
		this.mHeight = mHeight;
	}

	public String getWeight() {
		return mWeight;
	}

	public void setWeight(String mWeight) {
		this.mWeight = mWeight;
	}

	public String getDiseases() {
		return mDiseases;
	}

	public void setDiseases(String mDiseases) {
		this.mDiseases = mDiseases;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

}
