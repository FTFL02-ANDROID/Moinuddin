package com.ftfl.googlemap;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GoogleMapActivity extends Activity {

	// Google Map
	GoogleMap googleMap;
	Location location;
	LatLng currentPosition;
	
	float b = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);

		try {
			// Loading map
			initilizeMap();

			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			 location = locationManager
					.getLastKnownLocation(provider);
			
			if (location != null) {
				// PLACE THE INITIAL MARKER
				drawMarker(location);
			}

			LocationListener locationListener = new LocationListener() {

				@Override
				public void onLocationChanged(Location arg0) {
					// TODO Auto-generated method stub
					drawMarker(location);
				}
			};
			
			locationManager.requestLocationUpdates(provider, 1000*60*1, b,
					(android.location.LocationListener) locationListener);
			
			// Showing the current location in Google Map
	        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));

	        // Zoom in the Google Map
	        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

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
						"Lat:" + location.getLatitude() + "Lng:"
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
