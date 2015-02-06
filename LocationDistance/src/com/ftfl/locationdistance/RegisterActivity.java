package com.ftfl.locationdistance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	Bundle mExtras = null;
	String imagePath = "";
	ImageView mImgPreview = null;

	EditText etLat = null, etLongt = null, etRemrk = null;
	String mLatitude = "", mLongitude = "", mRemark = "", mDate = "",
			mTime = "";
	Button mSaveBtn = null;

	LocationProfile mLocationProfile = null;
	SQDataSource mSqlSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		mSqlSource = new SQDataSource(this);

		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy",
				Locale.getDefault());
		Date date = new Date();
		mDate = dateFormat.format(date);
		mTime = dateFormat.format(date.getTime());

		mImgPreview = (ImageView) findViewById(R.id.imgPreviewT);

		if (mExtras != null) {

			imagePath = mExtras.getString("photo");
			Bitmap image = BitmapFactory.decodeFile(imagePath);
			mImgPreview.setImageBitmap(image);
		}

		etLat = (EditText) findViewById(R.id.etLat);
		etLongt = (EditText) findViewById(R.id.etLongt);
		etRemrk = (EditText) findViewById(R.id.etRemk);
		
		mSaveBtn = (Button) findViewById(R.id.bttnSave);
		mSaveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mLatitude = etLat.getText().toString();
				mLongitude = etLongt.getText().toString();
				mRemark = etRemrk.getText().toString();

				double lat = Double.parseDouble(mLatitude);
				double longt = Double.parseDouble(mLongitude);

				mLocationProfile = new LocationProfile(lat, longt, mRemark,
						imagePath, mDate, mTime);

				long inserted = mSqlSource.insert(mLocationProfile);

				if (inserted >= 0) {
					Toast.makeText(getApplicationContext(), "Data Inseted",
							Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							HomeScreenActivity.class);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Data Insertion Problem", Toast.LENGTH_LONG).show();

				}

			}
		});
	}

}
