package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.DietProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class SQDietSource {
	
	// Database fields
	private SQLiteDatabase dietDataBase;
	SQLiteHelper dietDBhelper;

	List<DietProfile> dietList = new ArrayList<DietProfile>();
	public String mCurrentDate = "";

	public SQDietSource(Context context) {
		dietDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		dietDataBase = dietDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		dietDBhelper.close();
	}

	// taking current date from system
	public void currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/M/dd",
				Locale.getDefault());
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);
	}

	// insert data into the database.

	public long insert(DietProfile eChart) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_DT_FIELD, eChart.getName());
		values.put(SQLiteHelper.COLUMNL_DATE_FIELD, eChart.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_FIELD, eChart.getTime());
		values.put(SQLiteHelper.COLUMNL_MENU_FIELD, eChart.getMenu());
		values.put(SQLiteHelper.COLUMNL_ALARM_FIELD, eChart.getAlarm());

		long inserted = dietDataBase.insert(SQLiteHelper.TABLE_NAME_DIET, null,
				values);
		dietDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = dietDataBase.rawQuery(
				"select * from diet_chart where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, DietProfile eChart) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NAME_DT_FIELD, eChart.getName());
		values.put(SQLiteHelper.COLUMNL_DATE_FIELD, eChart.getDate());
		values.put(SQLiteHelper.COLUMNL_TIME_FIELD, eChart.getTime());
		values.put(SQLiteHelper.COLUMNL_MENU_FIELD, eChart.getMenu());
		values.put(SQLiteHelper.COLUMNL_ALARM_FIELD, eChart.getAlarm());

		long updated = 0;

		try {
			updated = dietDataBase.update(SQLiteHelper.TABLE_NAME_DIET, values,
					SQLiteHelper.COLUMNL_ID_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		dietDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return dietDataBase.delete(SQLiteHelper.TABLE_NAME_DIET,
				SQLiteHelper.COLUMNL_ID_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display present data from database.
	 */
	public ArrayList<DietProfile> getDietProfiles() {

		ArrayList<DietProfile> dietProfiles = new ArrayList<DietProfile>();
		this.open();
		this.currentDate();

		Cursor cursor = dietDataBase.query(SQLiteHelper.TABLE_NAME_DIET, null,
				SQLiteHelper.COLUMNL_DATE_FIELD + "= '" + mCurrentDate + "' ",
				null, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				int id = cursor.getInt(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_ID_DT_FIELD));
				String name = cursor.getString(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DT_FIELD));
				String date = cursor.getString(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
				String time = cursor.getString(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
				String menu = cursor.getString(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_MENU_FIELD));
				String alarm = cursor.getString(cursor
						.getColumnIndex(SQLiteHelper.COLUMNL_ALARM_FIELD));

				DietProfile fItem = new DietProfile(id, name, menu, date, time, alarm);
				dietProfiles.add(fItem);
				cursor.moveToNext();
			}
		}
		cursor.close();
		this.close();

		return dietProfiles;
	}

	
	/*
	 * using cursor for display weekly data from database.
	 */
	public ArrayList<DietProfile> getWeeklyDates() {
		this.open();
		this.currentDate();

		ArrayList<DietProfile> weeklyDates = new ArrayList<DietProfile>();

		Cursor cursor = dietDataBase.query(SQLiteHelper.TABLE_NAME_DIET, null,
				SQLiteHelper.COLUMNL_DATE_FIELD + "<= '" + mCurrentDate
						+ "' ORDER BY date DESC Limit 7", null, null, null,
				null);
		/*
		 * alternate way Cursor cursor = dietDataBase.rawQuery(
		 * "SELECT DISTINCT date FROM diet_chart  WHERE date <= '" +
		 * mCurrentDate + "' ORDER BY date DESC Limit 7", null);
		 */

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_DT_FIELD));
					String name = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DT_FIELD));
					String date = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
					String time = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
					String menu = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_MENU_FIELD));
					String alarm = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ALARM_FIELD));

					DietProfile fItem = new DietProfile(id, name, menu, date, time, alarm);
					weeklyDates.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return weeklyDates;
	}

	

}
