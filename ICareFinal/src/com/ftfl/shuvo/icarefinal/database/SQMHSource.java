package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.MedicalProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQMHSource {

	// Database fields
	private SQLiteDatabase mhDataBase;
	SQLiteHelper mhDBhelper;

	List<MedicalProfile> mhList = new ArrayList<MedicalProfile>();
	public String mCurrentDate = "";

	public SQMHSource(Context context) {
		mhDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		mhDataBase = mhDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		mhDBhelper.close();
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

	public long insert(MedicalProfile eMH) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_MH_FIELD, eMH.getName());
		values.put(SQLiteHelper.COLUMNL_PURPOSE_FIELD, eMH.getPurpose());
		values.put(SQLiteHelper.COLUMNL_DATE_MH_FIELD, eMH.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_MH_FIELD, eMH.getTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eMH.getPhoto());

		long inserted = mhDataBase.insert(SQLiteHelper.TABLE_NAME_MH, null,
				values);
		mhDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = mhDataBase.rawQuery(
				"select * from medical_history where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, MedicalProfile eMH) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_MH_FIELD, eMH.getName());
		values.put(SQLiteHelper.COLUMNL_PURPOSE_FIELD, eMH.getPurpose());
		values.put(SQLiteHelper.COLUMNL_DATE_MH_FIELD, eMH.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_MH_FIELD, eMH.getTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eMH.getPhoto());

		long updated = 0;

		try {
			updated = mhDataBase.update(SQLiteHelper.TABLE_NAME_MH, values,
					SQLiteHelper.COLUMNL_ID_MH_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		mhDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return mhDataBase.delete(SQLiteHelper.TABLE_NAME_MH,
				SQLiteHelper.COLUMNL_ID_MH_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<MedicalProfile> getAllData() {
		this.open();

		ArrayList<MedicalProfile> allData = new ArrayList<MedicalProfile>();

		Cursor cursor = mhDataBase.query(SQLiteHelper.TABLE_NAME_MH, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_MH_FIELD));
					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_MH_FIELD));
					String purpose = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_PURPOSE_FIELD));
					String date = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DATE_MH_FIELD));
					String time = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_TIME_MH_FIELD));
					String photo = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));

					MedicalProfile fItem = new MedicalProfile(id, name,
							purpose, date, time, photo);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}

}
