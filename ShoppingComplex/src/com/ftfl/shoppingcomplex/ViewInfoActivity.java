package com.ftfl.shoppingcomplex;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.ftfl.shoppingcomplex.database.SQDataSource;
import com.ftfl.shoppingcomplex.database.SQLiteHelper;
import com.ftfl.shoppingcomplex.util.FTFLConstants;
import com.ftfl.shoppingcomplex.util.SComplexInfo;

public class ViewInfoActivity extends Activity {

	EditText etName = null, etAddress = null, etDesc = null, etLat = null,
			etLongt = null, etClsd = null, etOpnd = null;

	SQDataSource mSqlSource = null;
	SComplexInfo mSInfo = null;
	int mId_To_Update = 0;
	Bundle mExtras = null;

	private ImageView mImgPreview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewinfo);

		etName = (EditText) findViewById(R.id.etName);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etDesc = (EditText) findViewById(R.id.etDesc);
		etLat = (EditText) findViewById(R.id.etLat);
		etLongt = (EditText) findViewById(R.id.etLongt);
		etClsd = (EditText) findViewById(R.id.etClosed);
		etOpnd = (EditText) findViewById(R.id.etOpened);
		mImgPreview = (ImageView) findViewById(R.id.imgPreview);

		mSqlSource = new SQDataSource(this);
		mExtras = getIntent().getExtras();

		if (mExtras != null) {

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

					if (imagePath != null) {

						Bitmap image = BitmapFactory.decodeFile(imagePath);
						mImgPreview.setImageBitmap(image);
					}

					etName.setText(name);
					etName.setEnabled(false);
					etName.setFocusable(false);
					etName.setClickable(false);

					etAddress.setText(address);
					etAddress.setEnabled(false);
					etAddress.setFocusable(false);
					etAddress.setClickable(false);

					etDesc.setText(description);
					etDesc.setEnabled(false);
					etDesc.setFocusable(false);
					etDesc.setClickable(false);

					etLat.setText(latitude);
					etLat.setEnabled(false);
					etLat.setFocusable(false);
					etLat.setClickable(false);

					etLongt.setText(longitude);
					etLongt.setEnabled(false);
					etLongt.setFocusable(false);
					etLongt.setClickable(false);

					etClsd.setText(closed);
					etClsd.setEnabled(false);
					etClsd.setFocusable(false);
					etClsd.setClickable(false);

					etOpnd.setText(opened);
					etOpnd.setEnabled(false);
					etOpnd.setFocusable(false);
					etOpnd.setClickable(false);
				}
			}

		}

	}

}
