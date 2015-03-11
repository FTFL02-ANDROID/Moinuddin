package com.ftfl.shuvo.icarefinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQDoctorSource;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.util.Admob;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class DrViewActivity extends Activity {

	TextView tvName = null, tvSpecial = null, tvPhn = null, tvEmail = null;
	SQDoctorSource mSqlSource = null;
	Bundle extras = null;
	String mNumber = "", mEmail = "";
	
	Admob mAdmob;
	Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_view);
		
		mActivity = DrViewActivity.this;
		mAdmob = new Admob(mActivity);

		mSqlSource = new SQDoctorSource(this);
		extras = getIntent().getExtras();

		tvName = (TextView) findViewById(R.id.tvDrNameVw);
		tvSpecial = (TextView) findViewById(R.id.tvDrSpecVw);
		tvPhn = (TextView) findViewById(R.id.tvDNumber);
		tvEmail = (TextView) findViewById(R.id.tvDEmail);

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
					
					mNumber = phone;
					mEmail = email;

					tvName.setText("Dr. " + name);
					tvSpecial.setText(special);
					tvPhn.setText(phone);
					tvEmail.setText(email);
				}
			}
		}

	}
	
	public void performDCall(View view) {

		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ mNumber));
		startActivity(callIntent);
	}

	public void performDSms(View view) {

		Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
				+ mNumber));
		startActivity(smsIntent);
	}

	public void performDEmail(View view) {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { mEmail });
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT, "body of email");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

}
