package com.ftfl.mymeetingplaces.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.mymeetingplaces.R;
import com.ftfl.mymeetingplaces.util.PlaceProfile;

public class MyPlaceAdapter extends ArrayAdapter<PlaceProfile> {

	Activity mContext = null;
	ArrayList<PlaceProfile> mListPlace = null;
	PlaceProfile mPlace = null;
	public static String mDistance = "";
	Location mLocation = null;
	double mToLat = 0.00, mToLong = 0.00;
	double fromLat = 0.00, fromLongt = 0.00;

	public MyPlaceAdapter(Activity context, ArrayList<PlaceProfile> ePlace,
			Location eLoation) {

		super(context, R.layout.row, ePlace);

		this.mContext = context;
		this.mListPlace = ePlace;
		this.mLocation = eLoation;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public ImageView imageView = null;
		public TextView tvLat = null;
		public TextView tvLongt = null;
		public TextView tvPlace = null;
		public TextView tvDistnt = null;
		public TextView tvTime = null;
		public TextView tvDate = null;
		public TextView textId = null;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mPlace = mListPlace.get(position);

		if (mLocation != null) {
			mToLat = mLocation.getLatitude();
			mToLong = mLocation.getLongitude();
		}

		fromLat = mPlace.getLat();
		fromLongt = mPlace.getLongi();

		calculateDistance(fromLat, fromLongt, mToLat, mToLong);

		ViewHolder holder = null;

		/*********** Layout inflator to call external xml layout () ***********/
		LayoutInflater inflater = mContext.getLayoutInflater();

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			convertView = inflater.inflate(R.layout.row, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imgView);
			holder.tvLat = (TextView) convertView.findViewById(R.id.tvLat);
			holder.tvLongt = (TextView) convertView.findViewById(R.id.tvLongt);
			holder.tvPlace = (TextView) convertView.findViewById(R.id.tvPlace);
			holder.tvDistnt = (TextView) convertView.findViewById(R.id.tvDist);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.textId = (TextView) convertView.findViewById(R.id.dbID);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		/*
		 * tvId to use for delete and edit in DataBase
		 */
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 16;
		Bitmap image = BitmapFactory.decodeFile(mPlace.getPhoto().toString(),
				options);

		holder.imageView.setImageBitmap(image);
		holder.textId.setText(mPlace.getId().toString());
		holder.tvLat.setText("Lat: " + mPlace.getLat().toString());
		holder.tvLongt.setText("Long: " + mPlace.getLongi().toString());
		holder.tvPlace.setText("Place: " + mPlace.getPlaceName().toString());
		holder.tvDate.setText("Date:" + mPlace.getDate().toString());
		holder.tvTime.setText("Time:" + mPlace.getTime().toString());
		holder.tvDistnt.setText("Distance: " + mDistance + " KM");

		return convertView;
	}

	public static double calculateDistance(double fromLatitude,
			double fromLongitude, double toLatitude, double toLongitude) {

		float results[] = new float[1];

		try {
			Location.distanceBetween(fromLatitude, fromLongitude, toLatitude,
					toLongitude, results);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		results[0] /= 1000D;

		mDistance = decimalFormat.format(results[0]); // in KiloMeter.
		double doubleDistance = Double.parseDouble(mDistance);
		return doubleDistance;
	}
}
