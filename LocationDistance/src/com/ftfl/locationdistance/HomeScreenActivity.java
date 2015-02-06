package com.ftfl.locationdistance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreenActivity extends Activity {
	
	Button mBtnReg, mBtnRetrv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		mBtnReg = (Button) findViewById(R.id.bttnRegisterHm);
		mBtnRetrv = (Button) findViewById(R.id.bttnRetrieveHm);
		
		mBtnReg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent registerIntent = new Intent(getApplicationContext(),CaptureActivity.class);
				startActivity(registerIntent);
				finish();
			}
		});
		
		mBtnRetrv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent retrieveIntent = new Intent(getApplicationContext(),DisplayActivity.class);
				startActivity(retrieveIntent);
				
			}
		});
	}


}
