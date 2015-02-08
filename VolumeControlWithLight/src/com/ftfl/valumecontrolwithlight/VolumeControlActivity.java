package com.ftfl.valumecontrolwithlight;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class VolumeControlActivity extends ActionBarActivity implements
		SensorEventListener {

	public SensorManager mSensorManager;
	public Sensor mLight = null;
	long mLastValue = 0;
	AudioManager mAudioManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_control);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		if (mLight == null) {
			Toast.makeText(getApplicationContext(),
					"No light sensor available...", Toast.LENGTH_SHORT).show();
		}

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		synchronized (this) {
			float lux = event.values[0];

			long light = (long) lux;

			long differenceValue = light - mLastValue;
			if (differenceValue > 10) {
				mLastValue = light;
				Toast.makeText(getApplicationContext(), "Volume Up",
						Toast.LENGTH_SHORT).show();

				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			}

			else if (differenceValue < -10) {
				mLastValue = light;
				Toast.makeText(getApplicationContext(), "Volume Down",
						Toast.LENGTH_LONG).show();

				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			}

		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mLight,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
}
