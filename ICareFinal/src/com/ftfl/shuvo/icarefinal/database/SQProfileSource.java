package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.MyProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQProfileSource {

	// Database fields
	private SQLiteDatabase profileDataBase;
	SQLiteHelper proDBhelper;

	List<MyProfile> profileList = new ArrayList<MyProfile>();
	public String mCurrentDate = "";

	public SQProfileSource(Context context) {
		proDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		profileDataBase = proDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		proDBhelper.close();
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

	public long insert(MyProfile eProfile) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_FIELD, eProfile.getName());
		values.put(SQLiteHelper.COLUMNL_AGE_FIELD, eProfile.getAge());
		values.put(SQLiteHelper.COLUMNL_HEIGHT_FIELD, eProfile.getHeight());
		values.put(SQLiteHelper.COLUMNL_WEIGHT_FIELD, eProfile.getWeight());
		values.put(SQLiteHelper.COLUMNL_DISEASES_FIELD, eProfile.getDiseases());
		values.put(SQLiteHelper.COLUMNL_PHONE_FIELD, eProfile.getPhone());

		long inserted = profileDataBase.insert(SQLiteHelper.TABLE_NAME_PR,
				null, values);
		profileDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = profileDataBase.rawQuery(
				"select * from my_profile where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, MyProfile eprofile) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_FIELD, eprofile.getName());
		values.put(SQLiteHelper.COLUMNL_AGE_FIELD, eprofile.getAge());
		values.put(SQLiteHelper.COLUMNL_HEIGHT_FIELD, eprofile.getHeight());
		values.put(SQLiteHelper.COLUMNL_WEIGHT_FIELD, eprofile.getWeight());
		values.put(SQLiteHelper.COLUMNL_DISEASES_FIELD, eprofile.getDiseases());
		values.put(SQLiteHelper.COLUMNL_PHONE_FIELD, eprofile.getPhone());

		long updated = 0;

		try {
			updated = profileDataBase.update(SQLiteHelper.TABLE_NAME_PR,
					values, SQLiteHelper.COLUMNL_ID_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		profileDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return profileDataBase.delete(SQLiteHelper.TABLE_NAME_PR,
				SQLiteHelper.COLUMNL_ID_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<MyProfile> getAllData() {
		this.open();

		ArrayList<MyProfile> allData = new ArrayList<MyProfile>();

		Cursor cursor = profileDataBase.query(SQLiteHelper.TABLE_NAME_PR, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_FIELD));
					String name = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					String age = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_AGE_FIELD));
					String height = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_HEIGHT_FIELD));
					String weight = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_WEIGHT_FIELD));
					String diseases = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DISEASES_FIELD));
					String phone = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_FIELD));

					MyProfile fItem = new MyProfile(id, name, age, height,
							weight, diseases, phone);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}

}
