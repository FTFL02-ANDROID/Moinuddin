package com.ftfl.volumebylight;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class LightVolumeActivity extends Activity implements
		SensorEventListener {

	SensorManager mySensorManager;
	Sensor LightSensor;
	MediaPlayer mp_file;
	AudioManager mAudioManager;
	long mLastValue = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_volume);

		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		// instantiate an instance of MediaPlayer
		mp_file = MediaPlayer.create(this, R.raw.song);
		
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
	}

	@Override
	protected void onResume() {
		mySensorManager.registerListener(this, LightSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mySensorManager.unregisterListener(this, LightSensor);
		super.onPause();
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			float currentLight = event.values[0];
			
			// start playing the media file
			
			
			long light = (long) currentLight;
			long diff = light - mLastValue;
			if (diff > 2) {
				mLastValue = light;
				mp_file.start();
				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				
			} else if (currentLight < 2) {
				mLastValue = light;
				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				
			} 
		} else {
			onStop();
		}
	}
	

	@Override
	protected void onStop() {
		super.onStop();
		// deallocate all memory
		if (mp_file != null) {
			if (mp_file.isPlaying()) {
				mp_file.stop();
			}
			mp_file.release();
			mp_file = null;
		}
	}

}
