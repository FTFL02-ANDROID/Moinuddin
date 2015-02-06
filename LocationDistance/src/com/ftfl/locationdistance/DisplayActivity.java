package com.ftfl.locationdistance;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayActivity extends Activity {

	ListView mListView = null;
	SQDataSource mSqlSource = null;
	ArrayList<LocationProfile> mLocationList = null;
	public static String mDistance = "";
	LocationProfile mLDistance;
	Location mLocation = null;
	GoogleMap mGoogleMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		boolean enabledGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabledGPS) {
			Toast.makeText(this,
					"GPS signal not found, Enable for better result",
					Toast.LENGTH_LONG).show();
		}

		// Enabling MyLocation Layer of Google Map
	//	mGoogleMap.setMyLocationEnabled(true);
		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// Getting the mName of the best provider
		String provider = locationManager.getBestProvider(criteria, true);

		// Getting Current Location
		mLocation = locationManager.getLastKnownLocation(provider);

		mListView = (ListView) findViewById(R.id.listView);

		mSqlSource = new SQDataSource(this);
		mLocationList = mSqlSource.getAllData();

		for (int i = 0; i < mLocationList.size(); i++) {
			mLDistance = mLocationList.get(i);
		}

		double fromLat = mLDistance.getLat();
		double fromLongt = mLDistance.getLongi();

		double toLat = mLocation.getLatitude();
		double toLongt = mLocation.getLongitude();

		calculateDistance(fromLat, fromLongt, toLat, toLongt);

		LocationAdapter arrayAdapter = new LocationAdapter(this, mLocationList,
				mDistance);
		// adding it to the list view.
		mListView.setAdapter(arrayAdapter);

	}

	public static double calculateDistance(double fromLatitude,
			double fromLongitude, double toLatitude, double toLongitude) {

		float results[] = new float[1];

		try {
			Location.distanceBetween(fromLatitude, fromLongitude, toLatitude,
					toLongitude, results);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int dist = (int) results[0];
		if (dist <= 0)
			return 0D;

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		results[0] /= 1000D;
		mDistance = decimalFormat.format(results[0]); // in KiloMeter.
		double d = Double.parseDouble(mDistance);
		return d;
	}

}
