package com.ftfl.shuvo.icarefinal;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQMHSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class MHViewActivity extends Activity {

	TextView tvName = null, tvPurpose = null, tvDateTime = null;
	ImageView mPhoto = null;
	SQMHSource mSqlSource = null;
	Bundle extras = null;
	String mPath = "";
	
	boolean isImageFitToScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mh_view);

		mSqlSource = new SQMHSource(this);
		extras = getIntent().getExtras();

		tvName = (TextView) findViewById(R.id.tvDNameVw);
		tvPurpose = (TextView) findViewById(R.id.tvPurposeVw);
		tvDateTime = (TextView) findViewById(R.id.tvDateTime);
		mPhoto = (ImageView) findViewById(R.id.imgPrev);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

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
						mPath = photo;
						BitmapFactory.Options options = new BitmapFactory.Options();
						// down sizing image
						options.inSampleSize = 2;
						Bitmap bitmap = BitmapFactory
								.decodeFile(photo, options);

						mPhoto.setImageBitmap(bitmap);
					}

					tvName.setText("Dr. " + name);
					tvPurpose.setText(purpose);
					tvDateTime.setText(date + "  " + time);
				}
			}
		}
	}
	
	public void showImage(View view){
		
		File imageFile = new File(mPath);
		Uri picUri = Uri.fromFile(imageFile);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(picUri, "image/*");
		startActivity(intent);
		
	}

}
