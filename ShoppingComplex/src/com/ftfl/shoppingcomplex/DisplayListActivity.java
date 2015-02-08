package com.ftfl.shoppingcomplex;

import java.util.ArrayList;

import com.ftfl.shoppingcomplex.R;
import com.ftfl.shoppingcomplex.adapter.MyAdapter;
import com.ftfl.shoppingcomplex.database.SQDataSource;
import com.ftfl.shoppingcomplex.util.FTFLConstants;
import com.ftfl.shoppingcomplex.util.SComplexInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayListActivity extends Activity {

	ListView mListView = null;
	SQDataSource mSqlSource = null;
	ArrayList<SComplexInfo> mSComplexList = null;
	AlertDialog.Builder mBuilder = null;
	TextView tvId = null;
	int mid_To_Update = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displaylist);

		mListView = (ListView) findViewById(R.id.viewList);

		mSqlSource = new SQDataSource(this);
		mBuilder = new AlertDialog.Builder(this);

		mSComplexList = mSqlSource.getAllData();

		MyAdapter arrayAdapter = new MyAdapter(this, mSComplexList);
		// adding it to the list view.
		mListView.setAdapter(arrayAdapter);

		// how many options you want to give for Alert Dialog box
		final String[] option = new String[] { FTFLConstants.VIEW_MAP,
				FTFLConstants.EDIT, FTFLConstants.DEL, FTFLConstants.VIEW_INFO };

		ArrayAdapter<String> adapterDialog = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);// default layout

		mBuilder.setAdapter(adapterDialog,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.e("Selected Item", String.valueOf(which));
						// position of the arrayList is "which"
						if (which == FTFLConstants.KEY_0) {
							viewMap(mid_To_Update);
						}
						if (which == FTFLConstants.KEY_1) {
							/*
							 * mBuilder.setMessage(R.string.editProfile); to do
							 * this, you've to declare/create a new mBuilder.
							 */
							editProfile(mid_To_Update);
						}
						if (which == FTFLConstants.KEY_2) {

							mBuilder.setMessage(R.string.deleteItemp)
									.setPositiveButton(
											R.string.yes,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													deleteProfile(mid_To_Update);
												}
											})
									.setNegativeButton(
											R.string.no,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// User cancelled the dialog
												}
											});
							// create mBuilder after declaring all its
							// attributes.
							AlertDialog delDialog = mBuilder.create();
							delDialog.show();
						}
						if (which == FTFLConstants.KEY_3) {

							viewInfo(mid_To_Update);
						}
					}
				});
		final AlertDialog dialog = mBuilder.create();// create mBuilder after
														// declaring all its
														// attributes.
		dialog.setTitle(getString(R.string.option));

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// arg1 is used to get the view. dbID3 is declared in the
				// listrow, which is hidden/gone
				tvId = (TextView) arg1.findViewById(R.id.dbID);
				String proID = tvId.getText().toString();
				// in order to use for view, delete and edit in DataBase
				mid_To_Update = Integer.parseInt(proID);
				dialog.show(); // to show dialog box of edit and delete

			}
		});
	}

	/**
	 * to delete existing profile
	 */
	public void deleteProfile(int profileID) {

		mSqlSource.deleteData(profileID);

		Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(getApplicationContext(),
				DisplayListActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * to edit existing profile
	 */
	public void editProfile(int profileID) {
		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "mId" is the
															// key...
		Intent intent = new Intent(this, SComplexActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
		finish();
	}

	/**
	 * to show the profileData
	 */
	public void viewMap(int profileID) {
		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "mId" is the
															// key...
		Intent intent = new Intent(getApplicationContext(),
				GoogleMapActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

	public void viewInfo(int profileID) {
		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "mId" is the
															// key...
		Intent intent = new Intent(getApplicationContext(),
				ViewInfoActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.Create_Profile:
			Intent intent = new Intent(getApplicationContext(),
					SComplexActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
