package com.ftfl.emergencycallonshake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ShakeCallActivity extends Activity {

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	String mPhoneNumner = null, mName = null;
	EditText etName = null, etPhone = null;
	Button mBttnSave = null;

	SharedPreferences sPrefs = null;

	public static final String MyPREFERENCES = "MyPrefs";
	public static final String KEY_PROFILE = "Profile";
	public static final String KEY_ID = "id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake_call);

		etName = (EditText) findViewById(R.id.etName);
		etPhone = (EditText) findViewById(R.id.etPhone);
		mBttnSave = (Button) findViewById(R.id.btnSave);

		sPrefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		if (sPrefs.contains(KEY_PROFILE)) {

			mBttnSave.setVisibility(View.GONE);
			
			String name = sPrefs.getString(KEY_PROFILE, "");
			String phn = sPrefs.getString(KEY_ID, "");
			
			mPhoneNumner = phn;
			etName.setText(name);
			etName.setClickable(false);
			etName.setFocusable(false);

			etPhone.setText(phn);
			etPhone.setClickable(false);
			etPhone.setFocusable(false);

		}

		mBttnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mName = etName.getText().toString();
				mPhoneNumner = etPhone.getText().toString();

				Editor sEdit = sPrefs.edit();
				sEdit.putString(KEY_PROFILE, mName);
				sEdit.putString(KEY_ID, mPhoneNumner);
				sEdit.commit();

				Intent intent = new Intent(getApplicationContext(),
						ShakeCallActivity.class);
				startActivity(intent);
				finish();
			}
		});

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {

				Intent callIntent = new Intent(Intent.ACTION_CALL,
						Uri.parse("tel:" + mPhoneNumner));
				startActivity(callIntent);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

}
