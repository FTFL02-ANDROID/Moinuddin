package com.ftfl.mymeetingplaces;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.database.SQDataSource;
import com.ftfl.mymeetingplaces.util.FTFLConstants;
import com.ftfl.mymeetingplaces.util.PlaceProfile;

public class MeetingPlaceActivity extends Activity {

	EditText etPlace = null, etLat = null, etLong = null;
	Button mBtnPic = null, mBtnSave = null;
	ImageView mImage = null;

	String mLatitude = "", mLongitude = "", mPlaceName = "", mDate = "",
			mTime = "";
	double lat = 0.00, longt = 0.00;

	PlaceProfile mPlcProfile = null;
	SQDataSource mSqlSource = null;
	Location mLocation;

	public static String mCurrentPhotoPath = "";
	private Uri mFileUri = null; // file url to store image/video

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meetingplace);

		mImage = (ImageView) findViewById(R.id.imgPreview);
		mBtnPic = (Button) findViewById(R.id.btnCapturePicture);
		mBtnSave = (Button) findViewById(R.id.bttnSave);

		etPlace = (EditText) findViewById(R.id.etPlace);
		etLat = (EditText) findViewById(R.id.etLat);
		etLong = (EditText) findViewById(R.id.etLongt);

		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy",
				Locale.getDefault());
		Date date = new Date();
		mDate = dateFormat.format(date);

		Time time = new Time();
		time.setToNow();
		mTime = time.hour + ":" + time.minute;

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(), FTFLConstants.NO_CAMERA,
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

		mBtnPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				captureImage();

			}
		});

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
		mLatitude = String.valueOf(lat);
		mLongitude = String.valueOf(longt);

		etLat.setText(mLatitude);
		etLat.setFocusable(false);

		etLong.setText(mLongitude);
		etLong.setFocusable(false);

		mSqlSource = new SQDataSource(this);

		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mPlaceName = etPlace.getText().toString();

				mPlcProfile = new PlaceProfile(lat, longt, mPlaceName,
						mCurrentPhotoPath, mDate, mTime);

				long inserted = mSqlSource.insert(mPlcProfile);

				if (inserted >= 0) {
					Toast.makeText(getApplicationContext(),
							FTFLConstants.INSERTED, Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							HomeScreenActivity.class);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							FTFLConstants.INSERT_PROBLEM, Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	private void captureImage() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		mFileUri = getOutputMediaFileUri(FTFLConstants.MEDIA_TYPE_IMAGE);

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mFileUri);

		// start the image capture Intent
		startActivityForResult(intent,
				FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable(FTFLConstants.KEY_URI, mFileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		mFileUri = savedInstanceState.getParcelable(FTFLConstants.KEY_URI);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// display it in image view
				previewCapturedImage();

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void previewCapturedImage() {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			// down sizing image
			options.inSampleSize = 8;
			Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(),
					options);

			mImage.setImageBitmap(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				FTFLConstants.IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(FTFLConstants.IMAGE_DIRECTORY_NAME,
						"Oops! Failed create "
								+ FTFLConstants.IMAGE_DIRECTORY_NAME
								+ " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == FTFLConstants.MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = mediaFile.getAbsolutePath();
		return mediaFile;
	}

}
