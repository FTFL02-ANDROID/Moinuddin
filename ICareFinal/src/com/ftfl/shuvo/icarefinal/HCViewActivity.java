package com.ftfl.shuvo.icarefinal;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQHCSource;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class HCViewActivity extends Activity {

	TextView tvName = null, tvAddress = null, tvPhn = null, tvDistance = null;
	SQHCSource mSqlSource = null;
	Bundle extras = null;
	
	Location mLocation = null;
	double mLat = 0.00, mLongt = 0.00;
	public static String mDistance = "";
	String mNumber = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hc_view);

		mSqlSource = new SQHCSource(this);
		extras = getIntent().getExtras();

		tvName = (TextView) findViewById(R.id.tvHCNmVw);
		tvAddress = (TextView) findViewById(R.id.tvHcAddVw);
		tvPhn = (TextView) findViewById(R.id.tvHNumber);
		tvDistance = (TextView) findViewById(R.id.tvDistance);
		
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

			mLat = mLocation.getLatitude();
			mLongt = mLocation.getLongitude();
		}

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_HC_FIELD));
					String address = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));
					String phone = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHONE_HC_FIELD));
					String lat = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_LAT_FIELD));
					String lang = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_LANG_FIELD));
					
					mNumber = phone;
					double lati = Double.parseDouble(lat);
					double langi = Double.parseDouble(lang);
					
					calculateDistance(mLat, mLongt, lati, langi);

					tvName.setText(name);
					tvAddress.setText(address);
					tvPhn.setText(phone);
					tvDistance.setText(mDistance+ " K.M");
				}
			}
		}
	}
	
	public void performHCall(View view) {

		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ mNumber));
		startActivity(callIntent);
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

		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		results[0] /= 1000D;

		mDistance = decimalFormat.format(results[0]); // in KiloMeter.
		double doubleDistance = Double.parseDouble(mDistance);
		return doubleDistance;
	}

}
