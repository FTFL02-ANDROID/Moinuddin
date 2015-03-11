package com.ftfl.shuvo.icarefinal.util;

public class DoctorProfile {

	Integer mId = 0;
	String mName = "";
	String mSpeciality = "";
	String mPhone = "";
	String mEmail = "";

	public DoctorProfile(Integer eId, String eName, String eSpeciality,
			String ePhone, String eEmail) {
		this.mId = eId;
		this.mName = eName;
		this.mSpeciality = eSpeciality;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
	}

	public DoctorProfile(String eName, String eSpeciality, String ePhone,
			String eEmail) {
		this.mName = eName;
		this.mSpeciality = eSpeciality;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
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

	public String getSpeciality() {
		return mSpeciality;
	}

	public void setSpeciality(String mSpeciality) {
		this.mSpeciality = mSpeciality;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

}
