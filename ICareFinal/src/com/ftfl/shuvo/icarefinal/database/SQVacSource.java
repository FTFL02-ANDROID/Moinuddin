package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.VaccineProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQVacSource {

	// Database fields
	private SQLiteDatabase vacDataBase;
	SQLiteHelper vacDBhelper;

	List<VaccineProfile> vacList = new ArrayList<VaccineProfile>();
	public String mCurrentDate = "";

	public SQVacSource(Context context) {
		vacDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		vacDataBase = vacDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		vacDBhelper.close();
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

	public long insert(VaccineProfile eChart) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_VC_FIELD, eChart.getName());
		values.put(SQLiteHelper.COLUMNL_REASON_FIELD, eChart.getReason());
		values.put(SQLiteHelper.COLUMNL_DATE_VC_FIELD, eChart.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_VC_FIELD, eChart.getTime());

		long inserted = vacDataBase.insert(SQLiteHelper.TABLE_NAME_VACCINE,
				null, values);
		vacDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = vacDataBase.rawQuery("select * from vaccine where _id="
				+ id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, VaccineProfile eChart) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_VC_FIELD, eChart.getName());
		values.put(SQLiteHelper.COLUMNL_REASON_FIELD, eChart.getReason());
		values.put(SQLiteHelper.COLUMNL_DATE_VC_FIELD, eChart.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_VC_FIELD, eChart.getTime());

		long updated = 0;

		try {
			updated = vacDataBase.update(SQLiteHelper.TABLE_NAME_VACCINE,
					values, SQLiteHelper.COLUMNL_ID_VC_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		vacDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return vacDataBase.delete(SQLiteHelper.TABLE_NAME_VACCINE,
				SQLiteHelper.COLUMNL_ID_VC_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<VaccineProfile> getAllData() {
		this.open();

		ArrayList<VaccineProfile> allData = new ArrayList<VaccineProfile>();

		Cursor cursor = vacDataBase.query(SQLiteHelper.TABLE_NAME_VACCINE,
				null, null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_VC_FIELD));
					String name = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_VC_FIELD));
					String reason = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_REASON_FIELD));
					String date = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_VC_FIELD));
					String time = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_VC_FIELD));

					VaccineProfile fItem = new VaccineProfile(id, name, reason,
							date, time);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}

}
