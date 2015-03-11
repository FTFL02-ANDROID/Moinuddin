package com.ftfl.shuvo.icarefinal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQMHSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.MedicalProfile;

public class MHistoryActivity extends Activity implements OnTimeSetListener,
		OnDateSetListener {

	EditText etName = null, etPurpose = null, etDate = null, etTime = null,
			etPhoto = null;
	Button mBtnPic = null;
	String mName = "", mPurpose = "", mDate = "", mTime = "", mPhoto = "";
	SQMHSource mSqlSource = null;
	MedicalProfile mProfile = null;

	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	final Calendar mCalendar = Calendar.getInstance();

	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;

	ImageView mImage = null;
	public static String mCurrentPhotoPath = "";
	private Uri mFileUri = null; // file url to store image/video

	Bundle extras = null;
	int id_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mh_add);

		mSqlSource = new SQMHSource(this);
		extras = getIntent().getExtras();

		etName = (EditText) findViewById(R.id.namETMh);
		etPurpose = (EditText) findViewById(R.id.purpsETMH);
		etDate = (EditText) findViewById(R.id.dateETMH);
		etTime = (EditText) findViewById(R.id.timeETMH);
		mImage = (ImageView) findViewById(R.id.imgPrev);
		mBtnPic = (Button) findViewById(R.id.imgBtn);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);
				id_To_Update = value;

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_MH_FIELD));
					String purpose = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_PURPOSE_FIELD));
					String date = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DATE_MH_FIELD));
					String time = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_TIME_MH_FIELD));
					String photo = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));

					if (photo != null) {
						mCurrentPhotoPath = photo;
						BitmapFactory.Options options = new BitmapFactory.Options();
						// down sizing image
						options.inSampleSize = 8;
						Bitmap bitmap = BitmapFactory
								.decodeFile(photo, options);

						mImage.setImageBitmap(bitmap);
					}

					etName.setText(name);
					etPurpose.setText(purpose);
					etDate.setText(date);
					etTime.setText(time);
				}
			}
		}

		mBtnPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				captureImage();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(), FTFLConstants.NO_CAMERA,
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}
	}

	public void saveMHistory(View view) {

		mName = etName.getText().toString();
		mPurpose = etPurpose.getText().toString();
		mDate = etDate.getText().toString();
		mTime = etTime.getText().toString();

		if (extras != null) {
			mProfile = new MedicalProfile(mName, mPurpose, mDate, mTime,
					mCurrentPhotoPath);
			long updated = mSqlSource.updateData(id_To_Update, mProfile);

			if (updated >= 0) {

				Toast.makeText(getApplicationContext(),
						FTFLConstants.UPDATE_DONE, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(),
						ICareActivity.class);
				startActivity(i);
				finish();
			} else
				Toast.makeText(getApplicationContext(),
						FTFLConstants.UPDATE_PRBLM, Toast.LENGTH_LONG).show();

		} else {

			mProfile = new MedicalProfile(mName, mPurpose, mDate, mTime,
					mCurrentPhotoPath);

			long inserted = mSqlSource.insert(mProfile);

			if (inserted >= 0) {
				Toast.makeText(getApplicationContext(),
						FTFLConstants.INSERT_DONE, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(),
						ICareActivity.class);
				startActivity(i);
				finish();
			} else
				Toast.makeText(getApplicationContext(),
						FTFLConstants.INSERT_PRBLM, Toast.LENGTH_LONG).show();
		}
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
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		mFileUri = getOutputMediaFileUri(FTFLConstants.MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

		// start the image capture Intent
		startActivityForResult(intent,
				FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == FTFLConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				previewCapturedImage();

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

	private void previewCapturedImage() {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inSampleSize = 8;
			Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(),
					options);

			mImage.setImageBitmap(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

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

	public void setDate(View view) {

		mYear = mCalendar.get(Calendar.YEAR);
		mMonth = mCalendar.get(Calendar.MONTH);
		mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(MHistoryActivity.this,
				this, mYear, mMonth, mDay);
		dialog.show();

	}

	public void setTime(View view) {

		// Process to get Current Time
		mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		mMinute = mCalendar.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(MHistoryActivity.this,
				this, mHour, mMinute, false);
		tpd.show();

	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

		mSetHour = hourOfDay;
		mSetMinute = minute;
		int hour = 0;
		String st = "";
		if (hourOfDay > 12) {
			hour = hourOfDay - 12;
			st = "PM";
		}

		else if (hourOfDay == 12) {
			hour = hourOfDay;
			st = "PM";
		}

		else if (hourOfDay == 0) {
			hour = hourOfDay + 12;
			st = FTFLConstants.PM;
		} else {
			hour = hourOfDay;
			st = FTFLConstants.AM;
		}
		etTime.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		etDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(dayOfMonth).append("/").append(monthOfYear + 1)
				.append("/").append(year));
	}

}
