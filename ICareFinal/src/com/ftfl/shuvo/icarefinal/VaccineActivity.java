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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQVacSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.VaccineProfile;

public class VaccineActivity extends Activity implements OnTimeSetListener,
		OnDateSetListener {

	EditText etName = null, etReason = null, etDate = null, etTime = null;
	String mName = "", mReason = "", mDate = "", mTime = "";
	SQVacSource mSqlSource = null;
	VaccineProfile mProfile = null;

	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	final Calendar mCalendar = Calendar.getInstance();

	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;

	Bundle extras = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaccine_add);

		mSqlSource = new SQVacSource(this);
		extras = getIntent().getExtras();

		etName = (EditText) findViewById(R.id.vcNameET);
		etReason = (EditText) findViewById(R.id.vcReasonET);
		etDate = (EditText) findViewById(R.id.dateETVC);
		etTime = (EditText) findViewById(R.id.timeETVC);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

				if (cursor.moveToFirst()) {

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

					etName.setText(name);
					etReason.setText(reason);
					etDate.setText(date);
					etTime.setText(time);
				}
			}
		}

	}

	public void saveVaccine(View view) {

		mName = etName.getText().toString();
		mReason = etReason.getText().toString();
		mDate = etDate.getText().toString();
		mTime = etTime.getText().toString();

		if (extras != null) {
			mProfile = new VaccineProfile(mName, mReason, mDate, mTime);
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

			mProfile = new VaccineProfile(mName, mReason, mDate, mTime);
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

		DatePickerDialog dialog = new DatePickerDialog(VaccineActivity.this,
				this, mYear, mMonth, mDay);
		dialog.show();

	}

	public void setTime(View view) {

		// Process to get Current Time
		mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		mMinute = mCalendar.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(VaccineActivity.this, this,
				mHour, mMinute, false);
		tpd.show();

	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		etDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(dayOfMonth).append("/").append(monthOfYear + 1)
				.append("/").append(year));
	}

}
