package com.ftfl.shuvo.icarefinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQDoctorSource;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.util.DoctorProfile;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class DoctorActivity extends Activity {

	EditText etName = null, etSpecial = null, etPhn = null, etEmail = null;
	String mName = "", mSpecial = "", mPhn = "", mEmail = "";
	DoctorProfile mProfile = null;
	SQDoctorSource mSqlSource = null;

	Bundle extras = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_add);

		mSqlSource = new SQDoctorSource(this);
		extras = getIntent().getExtras();

		etName = (EditText) findViewById(R.id.drNameET);
		etSpecial = (EditText) findViewById(R.id.drSpcET);
		etPhn = (EditText) findViewById(R.id.drNumET);
		etEmail = (EditText) findViewById(R.id.drEmlET);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DR_FIELD));
					String special = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_SPECIAL_FIELD));
					String phone = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_DR_FIELD));
					String email = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_EMAIL_FIELD));

					etName.setText(name);
					etSpecial.setText(special);
					etPhn.setText(phone);
					etEmail.setText(email);
				}
			}
		}
	}

	public void saveDoctor(View view) {

		mName = etName.getText().toString();
		mSpecial = etSpecial.getText().toString();
		mPhn = etPhn.getText().toString();
		mEmail = etEmail.getText().toString();

		if (extras != null) {
			mProfile = new DoctorProfile(mName, mSpecial, mPhn, mEmail);
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

			mProfile = new DoctorProfile(mName, mSpecial, mPhn, mEmail);
			long inserted = mSqlSource.insert(mProfile);

			if ((isValidEmail(mEmail)) && inserted >= 0) {
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

	public final static boolean isValidEmail(CharSequence viewEmail) {
		if (TextUtils.isEmpty(viewEmail)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(viewEmail)
					.matches();
		}
	}

}
