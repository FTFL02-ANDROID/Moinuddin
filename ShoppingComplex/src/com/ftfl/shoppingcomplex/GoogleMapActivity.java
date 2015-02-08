package com.ftfl.shoppingcomplex;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ftfl.shoppingcomplex.R;
import com.ftfl.shoppingcomplex.database.SQDataSource;
import com.ftfl.shoppingcomplex.database.SQLiteHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends Activity {

	// Google Map
	GoogleMap mGoogleMap = null;
	Location mLocation = null;
	LatLng mCurrentPosition = null, mShopingPosition = null;

	Bundle mExtras = null;
	SQDataSource mSqlSource = null;
	int mId_To_Update = 0;

	double mLatit = 0.0;
	double mLongi = 0.0;
	String mName = null;
	String mAddress = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);

		mSqlSource = new SQDataSource(this);
		mExtras = getIntent().getExtras();

		if (mExtras != null) {
			int value = mExtras.getInt("mId");
			if (value > 0) {
				// means this is the view part not the add contact part.
				Cursor cursor = mSqlSource.getData(value);
				mId_To_Update = value;

				if (cursor.moveToFirst()) {

					mName = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					String lat = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					String lngt = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					mAddress = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));

					mLatit = Double.parseDouble(lat);
					mLongi = Double.parseDouble(lngt);

				}
			}
		}

		try {
			// Loading map
			initilizeMap();

			mShopingPosition = new LatLng(mLatit, mLongi);
			mGoogleMap.addMarker(new MarkerOptions()
					.position(mShopingPosition)
					.snippet(mAddress)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
					.title(mName));

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			boolean enabledGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// Check if enabled and if not send user to the GSP settings
			// Better solution would be to display a dialog and suggesting to
			// go to the settings
			if (!enabledGPS) {
				Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}

			// Enabling MyLocation Layer of Google Map
			mGoogleMap.setMyLocationEnabled(true);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the mName of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			mLocation = locationManager.getLastKnownLocation(provider);

			LocationListener locationListener = new LocationListener() {

				@Override
				public void onLocationChanged(Location arg0) {
					// redraw the marker when get mLocation update.
					drawMarker(mLocation);
				}

				@Override
				public void onProviderDisabled(String provider) {
				}

				@Override
				public void onProviderEnabled(String provider) {
				}

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

			};

			locationManager.requestLocationUpdates(provider, 1000 * 20 * 1, 10,
					(android.location.LocationListener) locationListener);

			// Showing the current mLocation in Google Map
			mGoogleMap.moveCamera(CameraUpdateFactory
					.newLatLng(mCurrentPosition));

			// Zoom in the Google Map
			mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (mGoogleMap == null) {
			mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void drawMarker(Location location) {
		
		mGoogleMap.clear();
		
		mCurrentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());
		
		mGoogleMap.addMarker(new MarkerOptions()
				.position(mCurrentPosition)
				.snippet(
						"Lat:" + location.getLatitude() + " Lng:"
								+ location.getLongitude())
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.title("ME"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.satellite:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.normal:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
