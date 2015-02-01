package com.ftfl.shoppingcomplex.database;

import java.util.ArrayList;
import java.util.List;

import com.ftfl.shoppingcomplex.util.SComplexInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQDataSource {

	// Database fields
	private SQLiteDatabase mSCmplxDatabase;
	SQLiteHelper mCmplxDBhelper;

	List<SComplexInfo> complxList = new ArrayList<SComplexInfo>();

	public SQDataSource(Context context) {
		mCmplxDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		mSCmplxDatabase = mCmplxDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		mCmplxDBhelper.close();
	}

	// insert data into the database.

	public long insert(SComplexInfo eInfo) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_FIELD, eInfo.getName());
		values.put(SQLiteHelper.COLUMNL_ADDRESS_FIELD, eInfo.getAddress());
		values.put(SQLiteHelper.COLUMNL_DESCRIPTION_FIELD,
				eInfo.getDescription());
		values.put(SQLiteHelper.COLUMNL_LATITUDE_FIELD, eInfo.getLatitude());
		values.put(SQLiteHelper.COLUMNL_LONGITUDE_FIELD, eInfo.getLongitude());
		values.put(SQLiteHelper.COLUMNL_CLOSED_FIELD, eInfo.getCloseDay());
		values.put(SQLiteHelper.COLUMNL_OPENED_FIELD, eInfo.getOpenTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eInfo.getPhotoPath());

		long inserted = mSCmplxDatabase.insert(SQLiteHelper.TABLE_NAME, null,
				values);
		mSCmplxDatabase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = mSCmplxDatabase.rawQuery(
				"select * from shoping_complex where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, SComplexInfo eInfo) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_FIELD, eInfo.getName());
		values.put(SQLiteHelper.COLUMNL_ADDRESS_FIELD, eInfo.getAddress());
		values.put(SQLiteHelper.COLUMNL_DESCRIPTION_FIELD,
				eInfo.getDescription());
		values.put(SQLiteHelper.COLUMNL_LATITUDE_FIELD, eInfo.getLatitude());
		values.put(SQLiteHelper.COLUMNL_LONGITUDE_FIELD, eInfo.getLongitude());
		values.put(SQLiteHelper.COLUMNL_CLOSED_FIELD, eInfo.getCloseDay());
		values.put(SQLiteHelper.COLUMNL_OPENED_FIELD, eInfo.getOpenTime());
		values.put(SQLiteHelper.COLUMNL_PHOTO_FIELD, eInfo.getPhotoPath());

		long updated = 0;

		try {
			updated = mSCmplxDatabase.update(SQLiteHelper.TABLE_NAME, values,
					SQLiteHelper.COLUMNL_ID_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		mSCmplxDatabase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return mSCmplxDatabase.delete(SQLiteHelper.TABLE_NAME,
				SQLiteHelper.COLUMNL_ID_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<SComplexInfo> getAllData() {
		this.open();

		ArrayList<SComplexInfo> allInfo = new ArrayList<SComplexInfo>();

		Cursor cursor = mSCmplxDatabase.query(SQLiteHelper.TABLE_NAME, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_FIELD));
					String name = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					String address = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));
					String description = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DESCRIPTION_FIELD));
					String latitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					String longitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					String closed = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_CLOSED_FIELD));
					String opened = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_OPENED_FIELD));
					String imagePath = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));

					double lat = Double.parseDouble(latitude);
					double lont = Double.parseDouble(longitude);

					SComplexInfo sComplex = new SComplexInfo(id, name, address,
							description, lat, lont, closed, opened, imagePath);
					allInfo.add(sComplex);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allInfo;
	}

}
