package com.ftfl.shoppingcomplex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.ftfl.shoppingcomplex.R;
import com.ftfl.shoppingcomplex.database.SQDataSource;
import com.ftfl.shoppingcomplex.database.SQLiteHelper;
import com.ftfl.shoppingcomplex.util.FTFLConstants;
import com.ftfl.shoppingcomplex.util.SComplexInfo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SComplexActivity extends Activity {

	EditText etName = null, etAddress = null, etDesc = null, etLat = null,
			etLongt = null, etClsd = null, etOpnTym = null;

	String mName = null, mAddress = null, mDesc = null, mLatitude = null,
			mLongitude = null, mClsdDay = null, mOpenTime = null;

	SQDataSource mSqlSource = null;
	Button mSaveBtn = null;
	SComplexInfo mSInfo = null;
	int mId_To_Update = 0;
	Bundle mExtras = null;
	TextView tvTittle = null;

	// will store the path of cropped image
	private static String mCurrentPhotoPath = null;

	private Uri mFileUri = null; // file url to store image/video
	private Uri mCropUri = null; // file url to store Crop Image

	private ImageView mImgPreview = null;
	private Button mBtnCapturePicture = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scomplex);

		etName = (EditText) findViewById(R.id.etName);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etDesc = (EditText) findViewById(R.id.etDesc);
		etLat = (EditText) findViewById(R.id.etLat);
		etLongt = (EditText) findViewById(R.id.etLongt);
		etClsd = (EditText) findViewById(R.id.etClosed);
		etOpnTym = (EditText) findViewById(R.id.etOpened);

		mSqlSource = new SQDataSource(this);
		mExtras = getIntent().getExtras();

		mSaveBtn = (Button) findViewById(R.id.addBtn);
		tvTittle = (TextView) findViewById(R.id.tittleAdd);

		mImgPreview = (ImageView) findViewById(R.id.imgPreview);
		mBtnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);

		performEditSComplex();
		performSaveSComplex();

		mBtnCapturePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				captureImage();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(),
					"Sorry! Your device doesn't support camera",
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

	}

	/**
	 * Checking device has camera hardware or not
	 * */
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

	/**
	 * Capturing Camera Image will launch camera app request image capture
	 */
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		mFileUri = getOutputMediaFileUri(FTFLConstants.MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

		// start the image capture Intent
		startActivityForResult(intent,
				FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on screen orientation
		// changes
		outState.putParcelable(FTFLConstants.FILE_URI_KEY, mFileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		mFileUri = savedInstanceState.getParcelable(FTFLConstants.FILE_URI_KEY);
	}

	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image
				// display it in image view
				previewCapturedImage();
				// carry out the crop operation
				performCrop();
			} // user is returning from cropping the image
			else if (requestCode == FTFLConstants.PIC_CROP_CODE) {
				previewCropped();

			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/**
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage() {
		try {
			// mImgPreview.setVisibility(View.VISIBLE);
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();
			/**
			 * downsizing image as it throws OutOfMemory Exception for larger
			 * images
			 */
			options.inSampleSize = 8; // should be power of 2.
			Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(),
					options);

			mImgPreview.setImageBitmap(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void previewCropped() {

		try {
			// bitmap factory
			BitmapFactory.Options options = new BitmapFactory.Options();
			/**
			 * down sizing image as it throws OutOfMemory Exception for larger
			 * images
			 **/
			options.inSampleSize = 8; // should always be, power of 2

			final Bitmap bitmap = BitmapFactory.decodeFile(mCropUri.getPath(),
					options);

			mImgPreview.setImageBitmap(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void performCrop() {

		try {
			/**
			 * call the standard crop action intent (the user device may not
			 * support it)
			 */
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(mFileUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 0);
			cropIntent.putExtra("aspectY", 0);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 150);
			// retrieve data on return, true for bitmap type
			cropIntent.putExtra("return-data", false);

			mCropUri = getOutputMediaFileUri(FTFLConstants.MEDIA_TYPE_CROP);
			// save output image in uri
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropUri);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, FTFLConstants.PIC_CROP_CODE);

		} catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast
					.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * ------------ Helper Methods ----------------------
	 * */

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

		// External sdcard mLocation
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

		// Create a media file mName
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());

		File mediaFile;

		if (type == FTFLConstants.MEDIA_TYPE_IMAGE) {

			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

		} else if (type == FTFLConstants.MEDIA_TYPE_CROP) {

			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + "CROPPED_" + timeStamp + ".jpg");

			// Save a file: path for use with ACTION_VIEW intents
			mCurrentPhotoPath = mediaFile.getAbsolutePath();
		} else {
			return null;
		}

		return mediaFile;
	}

	/**
	 * To Save Shopping Complex Info.
	 */
	public void performSaveSComplex() {
		mSaveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mName = etName.getText().toString();
				mAddress = etAddress.getText().toString();
				mDesc = etDesc.getText().toString();
				mLatitude = etLat.getText().toString();
				mLongitude = etLongt.getText().toString();
				mClsdDay = etClsd.getText().toString();
				mOpenTime = etOpnTym.getText().toString();

				double lat = Double.parseDouble(mLatitude);
				double longt = Double.parseDouble(mLongitude);

				if (mExtras != null) {
					mSInfo = new SComplexInfo(mName, mAddress, mDesc, lat,
							longt, mClsdDay, mOpenTime, mCurrentPhotoPath);
					long updated = mSqlSource.updateData(mId_To_Update, mSInfo);

					if (updated >= 0) {

						Toast.makeText(getApplicationContext(), "Data Updated",
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(getApplicationContext(),
								DisplayListActivity.class);
						startActivity(i);
						finish();
					} else
						Toast.makeText(getApplicationContext(),
								"Data Upgradetion Problem", Toast.LENGTH_LONG)
								.show();

				} else {
					mSInfo = new SComplexInfo(mName, mAddress, mDesc, lat,
							longt, mClsdDay, mOpenTime, mCurrentPhotoPath);
					long inserted = mSqlSource.insert(mSInfo);

					if (inserted >= 0) {
						Toast.makeText(getApplicationContext(), "Data Inseted",
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(getApplicationContext(),
								DisplayListActivity.class);
						startActivity(i);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"Data Insertion Problem", Toast.LENGTH_LONG)
								.show();

					}

				}
			}
		});
	}

	/**
	 * To edit Shopping complex Info.
	 */
	public void performEditSComplex() {

		if (mExtras != null) {

			mSaveBtn.setText(FTFLConstants.UPDATE);
			tvTittle.setText(FTFLConstants.EDIT_CMPLX);
			int value = mExtras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				// means this is the view part not the add contact part.
				Cursor cursor = mSqlSource.getData(value);
				mId_To_Update = value;

				if (cursor.moveToFirst()) {

					String name = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					String address = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_ADDRESS_FIELD));
					String description = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DESCRIPTION_FIELD));
					String latitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					String longitude = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					String closed = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_CLOSED_FIELD));
					String opened = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_OPENED_FIELD));
					String imagePath = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));

					mCurrentPhotoPath = imagePath;

					if (imagePath != null) {
						File f = new File(imagePath);
						BitmapFactory.Options option = new BitmapFactory.Options();
						option.inSampleSize = 0;

						try {
							Bitmap image = BitmapFactory.decodeStream(
									new FileInputStream(f), null, option);

							mImgPreview.setImageBitmap(image);

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					etName.setText(name);
					etName.setEnabled(true);
					etName.setFocusable(true);
					etName.setClickable(true);

					etAddress.setText(address);
					etAddress.setEnabled(true);
					etAddress.setFocusable(true);
					etAddress.setClickable(true);

					etDesc.setText(description);
					etDesc.setEnabled(true);
					etDesc.setFocusable(true);
					etDesc.setClickable(true);

					etLat.setText(latitude);
					etLat.setEnabled(true);
					etLat.setFocusable(true);
					etLat.setClickable(true);

					etLongt.setText(longitude);
					etLongt.setEnabled(true);
					etLongt.setFocusable(true);
					etLongt.setClickable(true);

					etClsd.setText(closed);
					etClsd.setEnabled(true);
					etClsd.setFocusable(true);
					etClsd.setClickable(true);

					etOpnTym.setText(opened);
					etOpnTym.setEnabled(true);
					etOpnTym.setFocusable(true);
					etOpnTym.setClickable(true);
				}
			}

		}
	}

}
