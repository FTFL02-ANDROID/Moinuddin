package com.ftfl.locationdistance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQDataSource {

	// Database fields
	private SQLiteDatabase mLocationDatabase;
	SQLiteHelper mLocationDBhelper;

	List<LocationProfile> complxList = new ArrayList<LocationProfile>();

	public SQDataSource(Context context) {
		mLocationDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		mLocationDatabase = mLocationDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		mLocationDBhelper.close();
	}

	// insert data into the database.

	public long insert(LocationProfile eLocation) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_LATITUDE_FIELD, eLocation.getLat());
		values.put(SQLiteHelper.COLUMNL_LONGITUDE_FIELD, eLocation.getLongi());
		values.put(SQLiteHelper.COLUMNL_REMARKS_FIELD, eLocation.getRemark());
		values.put(SQLiteHelper.COLUMNL_DATE_FIELD, eLocation.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_FIELD, eLocation.getTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eLocation.getPhoto());

		long inserted = mLocationDatabase.insert(SQLiteHelper.TABLE_NAME, null,
				values);
		mLocationDatabase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = mLocationDatabase.rawQuery(
				"select * from shoping_complex where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, LocationProfile eLocation) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_LATITUDE_FIELD, eLocation.getLat());
		values.put(SQLiteHelper.COLUMNL_LONGITUDE_FIELD, eLocation.getLongi());
		values.put(SQLiteHelper.COLUMNL_REMARKS_FIELD, eLocation.getRemark());
		values.put(SQLiteHelper.COLUMNL_DATE_FIELD, eLocation.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_FIELD, eLocation.getTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eLocation.getPhoto());

		long updated = 0;

		try {
			updated = mLocationDatabase.update(SQLiteHelper.TABLE_NAME, values,
					SQLiteHelper.COLUMNL_ID_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		mLocationDatabase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return mLocationDatabase.delete(SQLiteHelper.TABLE_NAME,
				SQLiteHelper.COLUMNL_ID_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<LocationProfile> getAllData() {
		this.open();

		ArrayList<LocationProfile> allInfo = new ArrayList<LocationProfile>();

		Cursor cursor = mLocationDatabase.query(SQLiteHelper.TABLE_NAME, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_FIELD));

					String latitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					String longitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					String remark = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_REMARKS_FIELD));
					String date = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
					String time = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
					String imagePath = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));

					double lat = Double.parseDouble(latitude);
					double lont = Double.parseDouble(longitude);

					LocationProfile sComplex = new LocationProfile(id, lat,
							lont, remark, imagePath, date, time);
					allInfo.add(sComplex);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allInfo;
	}

}
