package com.ftfl.mymeetingplaces.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "meeting_places";
	public static final String COLUMNL_ID_FIELD = "_id";
	public static final String COLUMNL_LATITUDE_FIELD = "latitude";
	public static final String COLUMNL_LONGITUDE_FIELD = "longiitude";
	public static final String COLUMNL_PLACE_FIELD = "place";
	public static final String COLUMNL_DATE_FIELD = "date";
	public static final String COLUMNL_TIME_FIELD = "time";
	public static final String COLUMNL_PHOTO_FIELD = "photo_path";
	public static final String COLUMNL_NAME_FIELD = "name";
	public static final String COLUMNL_NUMBER_FIELD = "phone_number";
	public static final String COLUMNL_EMAIL_FIELD = "email";

	private static final String DATABASE_NAME = "MyPlace.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_CRATE_SQL = "create table " + TABLE_NAME
			+ "(" + COLUMNL_ID_FIELD + " INTEGER PRIMARY KEY, "
			+ COLUMNL_LATITUDE_FIELD + " text, " + COLUMNL_LONGITUDE_FIELD
			+ " text, " + COLUMNL_PLACE_FIELD + " text, " + COLUMNL_DATE_FIELD
			+ " text, " + COLUMNL_TIME_FIELD + " text, " + COLUMNL_PHOTO_FIELD
			+ " text, " + COLUMNL_NAME_FIELD + " text, " + COLUMNL_NUMBER_FIELD
			+ " text, " + COLUMNL_EMAIL_FIELD + " text)";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL(TABLE_CRATE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

}
