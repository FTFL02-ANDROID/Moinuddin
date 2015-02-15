package com.ftfl.mymeetingplaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.adapter.MyPlaceAdapter;
import com.ftfl.mymeetingplaces.database.SQDataSource;
import com.ftfl.mymeetingplaces.util.FTFLConstants;
import com.ftfl.mymeetingplaces.util.PlaceProfile;

public class MyVisitedPlaceActivity extends Activity {

	ListView mListView = null;
	SQDataSource mSqlSource = null;
	ArrayList<PlaceProfile> mPlaceList = null;
	public static String mDistance = "";
	Location mLocation = null;
	Button mBtnHome = null;
	TextView tvCLocation = null;
	double lat = 0.00, longt = 0.00;

	TextView textId = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvisitedplace);

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		boolean enabledGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabledGPS) {
			Toast.makeText(this, FTFLConstants.GPS_NOT_FOUND, Toast.LENGTH_LONG)
					.show();
		}

		String providerGPS = LocationManager.GPS_PROVIDER;
		String providerNetwork = LocationManager.NETWORK_PROVIDER;
		// Getting Current Location
		mLocation = locationManager.getLastKnownLocation(providerGPS);

		if (mLocation == null) {
			mLocation = locationManager.getLastKnownLocation(providerNetwork);
		}

		if (mLocation != null) {

			lat = mLocation.getLatitude();
			longt = mLocation.getLongitude();
		}
		tvCLocation = (TextView) findViewById(R.id.tvCLocation);
		tvCLocation.setText("Your Current Location " + "\nLAT: " + lat
				+ " LNG: " + longt);

		mListView = (ListView) findViewById(R.id.listView);

		mSqlSource = new SQDataSource(this);
		mPlaceList = mSqlSource.getAllData();

		MyPlaceAdapter arrayAdapter = new MyPlaceAdapter(this, mPlaceList,
				mLocation);
		// adding it to the list view.
		mListView.setAdapter(arrayAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// arg1 is used to get the view. dbID is declared in the
				// listrow, which is hidden/gone
				textId = (TextView) arg1.findViewById(R.id.dbID);
				String proID = textId.getText().toString();
				// in order to use for delete and edit in DataBase
				id_To_Update = Integer.parseInt(proID);

				Bundle dataBundle = new Bundle();
				dataBundle.putInt("id", id_To_Update); // "id" is the
														// key...
				Intent intent = new Intent(MyVisitedPlaceActivity.this,
						DisplayInfoActivity.class);
				intent.putExtras(dataBundle);
				startActivity(intent);

			}
		});

		mBtnHome = (Button) findViewById(R.id.btnHome);
		mBtnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						HomeScreenActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

}
