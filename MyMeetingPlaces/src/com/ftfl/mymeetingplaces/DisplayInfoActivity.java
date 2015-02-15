package com.ftfl.mymeetingplaces;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Intents;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mymeetingplaces.database.SQDataSource;
import com.ftfl.mymeetingplaces.database.SQLiteHelper;

public class DisplayInfoActivity extends Activity {

	ImageView mImage = null;
	TextView tvDateTym = null, tvPlace = null, tvLatLng = null, tvName = null,
			tvNumber = null, tvEmail = null;
	Button mEdit = null, mDel = null;
	String mDate = "", mTime = "", mPlace = "", mLat = "", mLng = "",
			mName = "", mNumber = "", mEmail = "", mPicPath = "";

	Bundle extras = null;
	int id_To_Update = 0;
	SQDataSource mSqlSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displayinfo);

		tvDateTym = (TextView) findViewById(R.id.tvDateTime);
		tvPlace = (TextView) findViewById(R.id.tvDPlace);
		tvLatLng = (TextView) findViewById(R.id.tvLngLat);
		tvName = (TextView) findViewById(R.id.tvDName);
		tvNumber = (TextView) findViewById(R.id.tvDNumber);
		tvEmail = (TextView) findViewById(R.id.tvDEmail);

		mImage = (ImageView) findViewById(R.id.imgPrev);

		mSqlSource = new SQDataSource(this);
		extras = getIntent().getExtras();

		if (extras != null) {
			int value = extras.getInt("id");
			if (value > 0) {
				// means this is the view part not the add contact part.
				Cursor cursor = mSqlSource.getData(value);
				id_To_Update = value;
				if (cursor.moveToFirst()) {

					mLat = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LATITUDE_FIELD));
					mLng = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_LONGITUDE_FIELD));
					mPlace = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PLACE_FIELD));
					mDate = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
					mTime = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
					mPicPath = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_PHOTO_FIELD));
					mName = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_FIELD));
					mNumber = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NUMBER_FIELD));
					mEmail = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_EMAIL_FIELD));

					tvDateTym.setText("Date: " + mDate + " Time: " + mTime);
					tvPlace.setText("Place: " + mPlace);
					tvLatLng.setText("LAT: " + mLat + " LNG: " + mLng);
					tvName.setText(mName);
					tvNumber.setText(mNumber);
					tvEmail.setText(mEmail);

					if (mPicPath != null) {
						BitmapFactory.Options options = new BitmapFactory.Options();
						// down sizing image
						options.inSampleSize = 8;
						Bitmap bitmap = BitmapFactory.decodeFile(mPicPath,
								options);

						mImage.setImageBitmap(bitmap);
					}

				}
			}

		}
	}

	public void performCall(View view) {

		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ mNumber));
		startActivity(callIntent);
	}

	public void performSms(View view) {

		Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
				+ mNumber));
		startActivity(smsIntent);
	}

	public void performEmail(View view) {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { mEmail });
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT, "body of email");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void performAdd(View view) {

		// Creates a new Intent to insert a contact
		Intent intent = new Intent(Intents.Insert.ACTION);
		// Sets the MIME type to match the Contacts Provider
		intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

		// Inserts a phone number
		intent.putExtra(Intents.Insert.PHONE, mNumber);
		/*
		 * In this example, sets the phone type to be a work phone. You can set
		 * other phone types as necessary.
		 */
		intent.putExtra(Intents.Insert.PHONE_TYPE, Phone.TYPE_WORK);

		startActivity(intent);

	}

	public void performDelete(View view) {

		mSqlSource.deleteData(id_To_Update);

		Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(getApplicationContext(),
				MyVisitedPlaceActivity.class);
		startActivity(intent);
		finish();
	}

}
