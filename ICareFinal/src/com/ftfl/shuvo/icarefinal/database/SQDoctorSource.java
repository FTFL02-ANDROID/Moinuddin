package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.DoctorProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQDoctorSource {

	// Database fields
	private SQLiteDatabase doctDataBase;
	SQLiteHelper doctDBhelper;

	List<DoctorProfile> doctList = new ArrayList<DoctorProfile>();
	public String mCurrentDate = "";

	public SQDoctorSource(Context context) {
		doctDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		doctDataBase = doctDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		doctDBhelper.close();
	}

	// taking current date from system
	// @SuppressLint("SimpleDateFormat")
	public void currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy",
				Locale.getDefault());
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);
	}

	// insert data into the database.

	public long insert(DoctorProfile eChart) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_DR_FIELD, eChart.getName());
		values.put(SQLiteHelper.COLUMNL_SPECIAL_FIELD, eChart.getSpeciality());
		values.put(SQLiteHelper.COLUMNL_PHONE_DR_FIELD, eChart.getPhone());
		values.put(SQLiteHelper.COLUMNL_EMAIL_FIELD, eChart.getEmail());

		long inserted = doctDataBase.insert(SQLiteHelper.TABLE_NAME_DOCTOR,
				null, values);
		doctDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = doctDataBase.rawQuery("select * from doctor where _id="
				+ id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, DoctorProfile eDr) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_DR_FIELD, eDr.getName());
		values.put(SQLiteHelper.COLUMNL_SPECIAL_FIELD, eDr.getSpeciality());
		values.put(SQLiteHelper.COLUMNL_PHONE_DR_FIELD, eDr.getPhone());
		values.put(SQLiteHelper.COLUMNL_EMAIL_FIELD, eDr.getEmail());

		long updated = 0;

		try {
			updated = doctDataBase.update(SQLiteHelper.TABLE_NAME_DOCTOR,
					values, SQLiteHelper.COLUMNL_ID_DR_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		doctDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return doctDataBase.delete(SQLiteHelper.TABLE_NAME_DOCTOR,
				SQLiteHelper.COLUMNL_ID_DR_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<DoctorProfile> getAllData() {
		this.open();

		ArrayList<DoctorProfile> allData = new ArrayList<DoctorProfile>();

		Cursor cursor = doctDataBase.query(SQLiteHelper.TABLE_NAME_DOCTOR,
				null, null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_DR_FIELD));
					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DR_FIELD));
					String special = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_SPECIAL_FIELD));
					String phone = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_DR_FIELD));
					String email = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_EMAIL_FIELD));

					DoctorProfile fItem = new DoctorProfile(id, name, special,
							phone, email);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}

}
