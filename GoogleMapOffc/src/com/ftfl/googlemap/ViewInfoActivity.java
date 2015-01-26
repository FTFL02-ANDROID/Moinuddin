package com.ftfl.googlemap;

import com.ftfl.googlemap.database.SQDataSource;
import com.ftfl.googlemap.database.SQLiteHelper;
import com.ftfl.googlemap.util.SComplexInfo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

public class ViewInfoActivity extends Activity {
	
	EditText etName = null, etAddress = null, etDesc = null, etLat = null,
			etLongt = null, etClsd = null, etOpnd = null;
	
	SQDataSource sqlSource = null;
	SComplexInfo eInfo;
	int id_To_Update = 0;
	Bundle extras = null;
	
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

		sqlSource = new SQDataSource(this);
		extras = getIntent().getExtras();
		
		if (extras != null) {
			
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
