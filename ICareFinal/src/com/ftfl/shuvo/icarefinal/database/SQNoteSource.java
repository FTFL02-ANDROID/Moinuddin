package com.ftfl.shuvo.icarefinal.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ftfl.shuvo.icarefinal.util.NoteProfile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQNoteSource {

	// Database fields
	private SQLiteDatabase noteDataBase;
	SQLiteHelper noteDBhelper;

	List<NoteProfile> noteList = new ArrayList<NoteProfile>();
	public String mCurrentDate = "";

	public SQNoteSource(Context context) {
		noteDBhelper = new SQLiteHelper(context);

	}

	// open a method for writable database
	public void open() throws SQLException {
		noteDataBase = noteDBhelper.getWritableDatabase();
	}

	// close database connection
	public void close() {
		noteDBhelper.close();
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

	public long insert(NoteProfile eNote) {

		this.open();

		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NOTE_FIELD, eNote.getNotes());
		values.put(SQLiteHelper.COLUMNL_DATE_NT_FIELD, eNote.getDate());

		long inserted = noteDataBase.insert(SQLiteHelper.TABLE_NAME_NOTE, null,
				values);
		noteDataBase.close();
		return inserted;
	}

	public Cursor getData(int id) {
		this.open();
		Cursor cursor = noteDataBase.rawQuery(
				"select * from note_list where _id=" + id + "", null);
		return cursor;
	}

	// update database by Id
	public long updateData(Integer id, NoteProfile eNote) {
		this.open();
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.COLUMNL_NOTE_FIELD, eNote.getNotes());
		values.put(SQLiteHelper.COLUMNL_DATE_NT_FIELD, eNote.getDate());

		long updated = 0;

		try {
			updated = noteDataBase.update(SQLiteHelper.TABLE_NAME_NOTE, values,
					SQLiteHelper.COLUMNL_ID_NT_FIELD + "=" + id, null);

		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
		}

		noteDataBase.close();
		return updated;
	}

	// delete data form database.
	public Integer deleteData(Integer id) {
		this.open();
		return noteDataBase.delete(SQLiteHelper.TABLE_NAME_NOTE,
				SQLiteHelper.COLUMNL_ID_NT_FIELD + " = ? ",
				new String[] { Integer.toString(id) });

	}

	/*
	 * using cursor for display All data from database.
	 */
	public ArrayList<NoteProfile> getAllData() {
		this.open();

		ArrayList<NoteProfile> allData = new ArrayList<NoteProfile>();

		Cursor cursor = noteDataBase.query(SQLiteHelper.TABLE_NAME_NOTE, null,
				null, null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {
					int id = cursor.getInt(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ID_NT_FIELD));
					String note = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NOTE_FIELD));
					String date = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DATE_NT_FIELD));

					NoteProfile fItem = new NoteProfile(id, note, date);
					allData.add(fItem);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return allData;
	}

}
