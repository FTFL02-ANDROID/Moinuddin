package com.ftfl.shuvo.icarefinal.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQProfileSource;
import com.ftfl.shuvo.icarefinal.util.OnShakeListener;
import com.ftfl.shuvo.icarefinal.util.ShakeDetector;

public class ProfileFragment extends Fragment {

	public static final String TAG = ProfileFragment.class.getSimpleName();

	TextView tvName, tvAge, tvWeight, tvHeight, tvPhn, tvDiseases;
	SQProfileSource mSqlSource;
	String mPhoneNumner = "";

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	public static ProfileFragment newInstance() {
		return new ProfileFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		mSqlSource = new SQProfileSource(getActivity());

		tvName = (TextView) rootView.findViewById(R.id.tvName);
		tvAge = (TextView) rootView.findViewById(R.id.tvAge);
		tvWeight = (TextView) rootView.findViewById(R.id.tvWeight);
		tvHeight = (TextView) rootView.findViewById(R.id.tvHeight);
		tvPhn = (TextView) rootView.findViewById(R.id.tvPhn);
		tvDiseases = (TextView) rootView.findViewById(R.id.tvDises);

		int value = 1;
		Cursor cursor = mSqlSource.getData(value);
		if (cursor.moveToFirst()) {

			String name = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
			String age = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_AGE_FIELD));
			String height = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_HEIGHT_FIELD));
			String weight = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_WEIGHT_FIELD));
			String diseases = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_DISEASES_FIELD));
			String phone = cursor.getString(cursor
					.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_FIELD));

			mPhoneNumner = phone;

			tvName.setText(name);
			tvAge.setText(age + " years");
			tvHeight.setText("Height " + height + " cm");
			tvWeight.setText("Weight " + weight + " K.G");
			tvDiseases.setText("Major Diseases: " + diseases);
			tvPhn.setText("Emergency :" + phone);

		}

		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
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

		return rootView;
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
