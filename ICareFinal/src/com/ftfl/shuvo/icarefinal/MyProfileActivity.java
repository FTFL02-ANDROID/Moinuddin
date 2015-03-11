package com.ftfl.shuvo.icarefinal;

import com.ftfl.shuvo.icarefinal.database.SQProfileSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.MyProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyProfileActivity extends Activity {

	EditText etName = null, etAge = null, etWeight = null, etHeight = null,
			etPhn = null, etDiseases = null;
	String mName = "", mAge = "", mWeight = "", mHeight = "", mPhn = "",
			mDiseases = "";
	MyProfile mProfile = null;
	SQProfileSource mSqlSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_add);

		mSqlSource = new SQProfileSource(this);

		/*if (!(mSqlSource == null)) {
			Toast.makeText(this, "You can't add more Profile",
					Toast.LENGTH_SHORT).show();
			Intent ii = new Intent(getApplicationContext(), ICareActivity.class);
			startActivity(ii);
			finish();
		}*/

		etName = (EditText) findViewById(R.id.nameETP);
		etAge = (EditText) findViewById(R.id.ageETP);
		etWeight = (EditText) findViewById(R.id.weightETP);
		etHeight = (EditText) findViewById(R.id.heightETP);
		etPhn = (EditText) findViewById(R.id.phnETP);
		etDiseases = (EditText) findViewById(R.id.majordiseaseETP);

	}

	public void saveProfile(View view) {

		mName = etName.getText().toString();
		mAge = etAge.getText().toString();
		mWeight = etWeight.getText().toString();
		mHeight = etHeight.getText().toString();
		mPhn = etPhn.getText().toString();
		mDiseases = etDiseases.getText().toString();

		mProfile = new MyProfile(mName, mAge, mHeight, mWeight, mDiseases, mPhn);

		long inserted = mSqlSource.insert(mProfile);

		if (inserted >= 0) {
			Toast.makeText(getApplicationContext(), FTFLConstants.INSERT_DONE,
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(getApplicationContext(), ICareActivity.class);
			startActivity(i);
			finish();
		} else
			Toast.makeText(getApplicationContext(), FTFLConstants.INSERT_PRBLM,
					Toast.LENGTH_LONG).show();

	}

}
