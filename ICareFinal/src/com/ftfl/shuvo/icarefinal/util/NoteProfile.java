package com.ftfl.shuvo.icarefinal.util;

public class NoteProfile {

	Integer mId = 0;
	String mNotes = "";
	String mDate = "";

	public NoteProfile(Integer eId, String eNotes, String eDate) {
		this.mId = eId;
		this.mNotes = eNotes;
		this.mDate = eDate;
	}

	public NoteProfile(String eNotes, String eDate) {
		this.mNotes = eNotes;
		this.mDate = eDate;
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer mId) {
		this.mId = mId;
	}

	public String getNotes() {
		return mNotes;
	}

	public void setNotes(String mNotes) {
		this.mNotes = mNotes;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

}
