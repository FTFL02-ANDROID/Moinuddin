package com.ftfl.googlemap;

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

import com.ftfl.googlemap.database.SQDataSource;
import com.ftfl.googlemap.database.SQLiteHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends Activity {

	// Google Map
	GoogleMap googleMap;
	Location location;
	LatLng currentPosition, shopingPosition;

	Bundle extras = null;
	SQDataSource sqlSource = null;
	int id_To_Update = 0;

	double latit = 0.0;
	double longi = 0.0;
	String name = null;
	String address = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);

		sqlSource = new SQDataSource(this);
		extras = getIntent().getExtras();

		if (extras != null) {
			int value = extras.getInt("id");
			if (value > 0) {
				// means this is the view part not the add contact part.
				Cursor cursor = sqlSource.getData(value);
				id_To_Update = value;
				if (cursor.moveToFirst()) {

					name = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					String lat = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					String lngt = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					address = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));

					latit = Double.parseDouble(lat);
					longi = Double.parseDouble(lngt);

				}
			}
		}

		try {
			// Loading map
			initilizeMap();

			shopingPosition = new LatLng(latit, longi);
			googleMap.addMarker(new MarkerOptions()
					.position(shopingPosition)
					.snippet(address)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
					.title(name));

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
			googleMap.setMyLocationEnabled(true);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			location = locationManager.getLastKnownLocation(provider);

			LocationListener locationListener = new LocationListener() {

				@Override
				public void onLocationChanged(Location arg0) {
					// redraw the marker when get location update.
					drawMarker(location);
				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub

				}

			};

			locationManager.requestLocationUpdates(provider, 1000 * 20 * 1, 10,
					(android.location.LocationListener) locationListener);

			// Showing the current location in Google Map
			googleMap
					.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));

			// Zoom in the Google Map
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void drawMarker(Location location) {
		googleMap.clear();
		currentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());
		googleMap.addMarker(new MarkerOptions()
				.position(currentPosition)
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
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.normal:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
