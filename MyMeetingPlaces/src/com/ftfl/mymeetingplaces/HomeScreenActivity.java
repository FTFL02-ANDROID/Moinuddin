package com.ftfl.mymeetingplaces;

import com.ftfl.mymeetingplaces.util.Admob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreenActivity extends Activity {

	Button mBtnReg, mBtnRevw;
	Activity mActivity;
	Admob mAdmob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		mBtnReg = (Button) findViewById(R.id.buttonReg);
		mBtnRevw = (Button) findViewById(R.id.buttonRvw);
		/**
		 * To display the Ad, Admob is in the Util Package.
		 * */
		mActivity = HomeScreenActivity.this;
		mAdmob = new Admob(mActivity);

		mBtnReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent registerIntent = new Intent(getApplicationContext(),
						MeetingPlaceActivity.class);
				startActivity(registerIntent);
				finish();
			}
		});

		mBtnRevw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent reviewIntent = new Intent(getApplicationContext(),
						MyVisitedPlaceActivity.class);
				startActivity(reviewIntent);
				finish();
			}
		});

	}

}
