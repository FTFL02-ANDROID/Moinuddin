package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.HealthCenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQHCSource {

	// Database fields
	private SQLiteDatabase hcDataBase;
	SQLiteHelper hcDBhelper;

	List<HealthCenter> hcList = new ArrayList<HealthCenter>();
	public String mCurrentDate = "";

	public SQHCSource(Context context) {
		hcDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		hcDataBase = hcDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		hcDBhelper.close();
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

	public long insert(HealthCenter eHC) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_HC_FIELD, eHC.getName());
		values.put(SQLiteHelper.COLUMNL_ADDRESS_FIELD, eHC.getAddress());
		values.put(SQLiteHelper.COLUMNL_PHONE_HC_FIELD, eHC.getPhone());
		values.put(SQLiteHelper.COLUMNL_LAT_FIELD, eHC.getLat());
		values.put(SQLiteHelper.COLUMNL_LANG_FIELD, eHC.getLang());

		long inserted = hcDataBase.insert(SQLiteHelper.TABLE_NAME_HC, null,
				values);
		hcDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = hcDataBase.rawQuery(
				"select * from health_center where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, HealthCenter eHC) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_HC_FIELD, eHC.getName());
		values.put(SQLiteHelper.COLUMNL_ADDRESS_FIELD, eHC.getAddress());
		values.put(SQLiteHelper.COLUMNL_PHONE_HC_FIELD, eHC.getPhone());
		values.put(SQLiteHelper.COLUMNL_LAT_FIELD, eHC.getLat());
		values.put(SQLiteHelper.COLUMNL_LANG_FIELD, eHC.getLang());

		long updated = 0;

		try {
			updated = hcDataBase.update(SQLiteHelper.TABLE_NAME_HC, values,
					SQLiteHelper.COLUMNL_ID_HC_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		hcDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return hcDataBase.delete(SQLiteHelper.TABLE_NAME_HC,
				SQLiteHelper.COLUMNL_ID_HC_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<HealthCenter> getAllData() {
		this.open();

		ArrayList<HealthCenter> allData = new ArrayList<HealthCenter>();

		Cursor cursor = hcDataBase.query(SQLiteHelper.TABLE_NAME_HC, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_HC_FIELD));
					String name = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_HC_FIELD));
					String address = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));
					String phone = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_HC_FIELD));
					String lat = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_LAT_FIELD));
					String lang = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_LANG_FIELD));

					double lati = Double.parseDouble(lat);
					double langi = Double.parseDouble(lang);

					HealthCenter fItem = new HealthCenter(id, name, address,
							phone, lati, langi);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}
	
	public int profileNumber() {
		this.open();
		Cursor mCursor = hcDataBase.query(SQLiteHelper.TABLE_NAME_HC, null,
				null, null, null, null, null);
		return mCursor.getCount();
		
	}

}
