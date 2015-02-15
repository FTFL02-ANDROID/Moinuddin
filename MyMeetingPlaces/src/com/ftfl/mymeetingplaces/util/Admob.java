package com.ftfl.mymeetingplaces.util;

import android.app.Activity;

import com.ftfl.mymeetingplaces.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Admob {

	private InterstitialAd interstitial;
	Activity activity;

	public Admob(Activity activity) {
		this.activity = activity;

		// Prepare the Interstitial Ad
		interstitial = new InterstitialAd(activity);
		// Insert the Ad Unit ID
		interstitial.setAdUnitId("ca-app-pub-123456789/123456789");

		// Locate the Banner Ad in activity_main.xml
		AdView adView = (AdView) activity.findViewById(R.id.adView);

		// Request for Ads
		AdRequest adRequest = new AdRequest.Builder()

		// Add a test device to show Test Ads
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				/*
				 * You have to generate your own Test Device ID, because each
				 * Android Device has an unique ID. paste the Test Device ID
				 * into addTestDevice,like bellow. you'll find it in logCat.
				 * First run the app without a ID.
				 */
				.addTestDevice("9452107F6D6E3CCE0B775C75237C12F6")
				/*
				 * To show real ads, just remove the two lines of addTestDevice
				 * by commenting it
				 */
				.build();

		// Load ads into Banner Ads
		adView.loadAd(adRequest);

		// Load ads into Interstitial Ads
		interstitial.loadAd(adRequest);

		// Prepare an Interstitial Ad Listener
		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
				// Call displayInterstitial() function
				displayInterstitial();
			}
		});
	}

	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

}
