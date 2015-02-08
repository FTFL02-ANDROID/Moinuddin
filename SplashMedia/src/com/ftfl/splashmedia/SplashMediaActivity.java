package com.ftfl.splashmedia;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class SplashMediaActivity extends Activity {

	MediaPlayer splashSound = null;
	Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_media);
		
		handler = new Handler();
		
		/*Gets your 
		soundfile from res/raw/desire.ogg */
		splashSound = MediaPlayer
				.create(SplashMediaActivity.this, R.raw.desire);
		splashSound.start(); // <<<play sound on Splash Screen
		handler.postDelayed(runnable, 5000);
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			
			// stop the sound
			splashSound.stop();
			splashSound.release();
			
			// start your Next Activity here
			Intent i = new Intent(SplashMediaActivity.this, MainActivity.class);
			startActivity(i);

			// close this activity
			finish();
		}
	};

}
