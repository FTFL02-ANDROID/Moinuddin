package com.ftfl.shuvo.icarefinal;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQDietSource;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.util.DietProfile;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class DietActivity extends Activity implements OnTimeSetListener,
		OnDateSetListener {

	EditText etName = null, etMenue = null, etDate = null, etTime = null;
	String mName = "", mMenue = "", mDate = "", mTime = "", mAlarm = "0";
	SQDietSource mSqlSource = null;
	DietProfile mProfile = null;

	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	final Calendar mCalendar = Calendar.getInstance();

	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	CheckBox cbAlarm = null;

	Bundle extras = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diet_add);

		mSqlSource = new SQDietSource(this);
		extras = getIntent().getExtras();

		etName = (EditText) findViewById(R.id.mealNameETDt);
		etMenue = (EditText) findViewById(R.id.menuETD);
		etDate = (EditText) findViewById(R.id.dateETD);
		etTime = (EditText) findViewById(R.id.timeETD);
		cbAlarm = (CheckBox) findViewById(R.id.checkBoxAlarm);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);
				id_To_Update = value;

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DT_FIELD));
					String date = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
					String time = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
					String menu = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_MENU_FIELD));
					String alarm = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ALARM_FIELD));

					int eAlarm = Integer.parseInt(alarm);

					if (eAlarm == 1) {
						cbAlarm.isChecked();
					}

					etName.setText(name);
					etMenue.setText(menu);
					etDate.setText(date);
					etTime.setText(time);
				}
			}
		}

	}

	public void saveDiet(View view) {

		// Setting Alarm
		if (cbAlarm.isChecked()) {

			mAlarm = "1";

			Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
			alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, mSetHour);
			alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, mSetMinute);
			alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(alarmIntent);
		}

		mName = etName.getText().toString();
		mMenue = etMenue.getText().toString();
		mDate = etDate.getText().toString();
		mTime = etTime.getText().toString();

		if (extras != null) {
			mProfile = new DietProfile(mName, mMenue, mDate, mTime, mAlarm);
			long updated = mSqlSource.updateData(id_To_Update, mProfile);

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

			mProfile = new DietProfile(mName, mMenue, mDate, mTime, mAlarm);
			long inserted = mSqlSource.insert(mProfile);

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

		DatePickerDialog dialog = new DatePickerDialog(DietActivity.this, this,
				mYear, mMonth, mDay);
		dialog.show();

	}

	public void setTime(View view) {

		// Process to get Current Time
		mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		mMinute = mCalendar.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(DietActivity.this, this,
				mHour, mMinute, false);
		tpd.show();

	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		mSetHour = hourOfDay;
		mSetMinute = minute;
		int hour = 0;
		String st = "";
		if (hourOfDay > 12) {
			hour = hourOfDay - 12;
			st = "PM";
		}

		else if (hourOfDay == 12) {
			hour = hourOfDay;
			st = "PM";
		}

		else if (hourOfDay == 0) {
			hour = hourOfDay + 12;
			st = FTFLConstants.PM;
		} else {
			hour = hourOfDay;
			st = FTFLConstants.AM;
		}
		etTime.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		if (dayOfMonth < 10) {
			etDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(year).append("/").append(monthOfYear + 1)
					.append("/").append("0" + dayOfMonth));
		} else
			etDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(year).append("/").append(monthOfYear + 1)
					.append("/").append(dayOfMonth));

	}

}
