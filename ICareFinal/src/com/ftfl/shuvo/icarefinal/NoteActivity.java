package com.ftfl.shuvo.icarefinal;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQNoteSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.NoteProfile;

public class NoteActivity extends Activity implements OnDateSetListener {

	EditText etNote = null, etDate = null;
	String mNote = "", mDate = "";
	NoteProfile mNProfile = null;
	SQNoteSource mSqlSource = null;

	final Calendar mCalendar = Calendar.getInstance();

	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;

	Bundle extras = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_add);

		mSqlSource = new SQNoteSource(this);
		extras = getIntent().getExtras();

		etNote = (EditText) findViewById(R.id.noteET);
		etDate = (EditText) findViewById(R.id.dateETNT);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);
				id_To_Update = value;

				if (cursor.moveToFirst()) {

					String note = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NOTE_FIELD));
					String date = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DATE_NT_FIELD));

					etNote.setText(note);
					etDate.setText(date);
				}
			}
		}

	}

	public void saveNote(View view) {

		mNote = etNote.getText().toString();
		mDate = etDate.getText().toString();

		if (extras != null) {
			mNProfile = new NoteProfile(mNote, mDate);
			long updated = mSqlSource.updateData(id_To_Update, mNProfile);

			if (updated >= 0) {

				Toast.makeText(getApplicationContext(),
						FTFLConstants.UPDATE_DONE, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(),
						ICareActivity.class);
				startActivity(i);
				finish();
			} else
				Toast.makeText(getApplicationContext(),
						FTFLConstants.UPDATE_PRBLM, Toast.LENGTH_LONG).show();

		} else {
			mNProfile = new NoteProfile(mNote, mDate);
			long inserted = mSqlSource.insert(mNProfile);

			if (inserted >= 0) {
				Toast.makeText(getApplicationContext(),
						FTFLConstants.INSERT_DONE, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(),
						ICareActivity.class);
				startActivity(i);
				finish();
			} else
				Toast.makeText(getApplicationContext(),
						FTFLConstants.INSERT_PRBLM, Toast.LENGTH_LONG).show();
		}
	}

	public void setDate(View view) {

		mYear = mCalendar.get(Calendar.YEAR);
		mMonth = mCalendar.get(Calendar.MONTH);
		mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(NoteActivity.this, this,
				mYear, mMonth, mDay);
		dialog.show();

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {

		etDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(dayOfMonth).append("/").append(monthOfYear + 1)
				.append("/").append(year));

	}

}
