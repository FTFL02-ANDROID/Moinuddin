package com.ftfl.googlemap;

import com.ftfl.googlemap.database.SQDataSource;
import com.ftfl.googlemap.database.SQLiteHelper;
import com.ftfl.googlemap.util.SComplexInfo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SComplexActivity extends Activity {

	EditText etName = null, etAddress = null, etDesc = null, etLat = null,
			etLongt = null, etClsd = null, etOpnd = null;
	String name, address, desc, latitude, longitude, clsdDay, openTime;
	SQDataSource sqlSource = null;
	Button saveBtn = null;
	SComplexInfo eInfo;
	int id_To_Update = 0;
	Bundle extras = null;
	TextView tittleTxt = null;

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
		etOpnd = (EditText) findViewById(R.id.etOpened);

		sqlSource = new SQDataSource(this);
		extras = getIntent().getExtras();

		saveBtn = (Button) findViewById(R.id.addBtn);
		tittleTxt = (TextView) findViewById(R.id.tittleAdd);

		if (extras != null) {
			saveBtn.setText("Update");
			tittleTxt.setText("Edit Complex");
			int value = extras.getInt("id");
			if (value > 0) {
				// means this is the view part not the add contact part.
				Cursor cursor = sqlSource.getData(value);
				id_To_Update = value;
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

					etOpnd.setText(opened);
					etOpnd.setEnabled(true);
					etOpnd.setFocusable(true);
					etOpnd.setClickable(true);
				}
			}

		}

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				name = etName.getText().toString();
				address = etAddress.getText().toString();
				desc = etDesc.getText().toString();
				latitude = etLat.getText().toString();
				longitude = etLongt.getText().toString();
				clsdDay = etClsd.getText().toString();
				openTime = etOpnd.getText().toString();

				double lat = Double.parseDouble(latitude);
				double longt = Double.parseDouble(longitude);

				if (extras != null) {
					eInfo = new SComplexInfo(name, address, desc, lat, longt,
							clsdDay, openTime);
					long updated = sqlSource
							.updateData(id_To_Update, eInfo);

					if (updated >= 0) {

						Toast.makeText(getApplicationContext(),
								"Data Updated", Toast.LENGTH_LONG)
								.show();
						Intent i = new Intent(getApplicationContext(),
								DisplayListActivity.class);
						startActivity(i);
						finish();
					} else
						Toast.makeText(getApplicationContext(),
								"Data Upgradetion Problem", Toast.LENGTH_LONG)
								.show();

				} else {
					eInfo = new SComplexInfo(name, address, desc, lat, longt,
							clsdDay, openTime);
					long inserted = sqlSource.insert(eInfo);

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

}
