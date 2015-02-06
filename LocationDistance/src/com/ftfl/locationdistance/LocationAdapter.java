package com.ftfl.locationdistance;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<LocationProfile> {

	Activity mContext;
	ArrayList<LocationProfile> mListLocation;
	LocationProfile mLocation;
	String mDistance = "";

	public LocationAdapter(Activity context, ArrayList<LocationProfile> eLocation, String eDistance) {

		super(context, R.layout.row, eLocation);

		this.mContext = context;
		this.mListLocation = eLocation;
		this.mDistance = eDistance;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {
		
		public ImageView imageView = null;
		public TextView tvLat = null;
		public TextView tvLongt = null;
		public TextView tvRemark = null;
		public TextView tvDistnt = null;
		public TextView textId = null;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mLocation = mListLocation.get(position);

		ViewHolder holder = null;

		/*********** Layout inflator to call external xml layout () ***********/
		LayoutInflater inflater = mContext.getLayoutInflater();

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			convertView = inflater.inflate(R.layout.row, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			holder.tvLat = (TextView) convertView.findViewById(R.id.tvLat);
			holder.tvLongt = (TextView) convertView.findViewById(R.id.tvLongt);
			holder.tvRemark = (TextView) convertView.findViewById(R.id.tvRemk);
			holder.tvDistnt = (TextView) convertView.findViewById(R.id.tvDistnt);
			holder.textId = (TextView) convertView.findViewById(R.id.dbID);
			

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		/*
		 * tvId to use for delete and edit in DataBase
		 */
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap image = BitmapFactory.decodeFile(mLocation.getPhoto().toString(), options);
		
		holder.imageView.setImageBitmap(image);
		holder.textId.setText(mLocation.getId().toString());
		holder.tvLat.setText(mLocation.getLat().toString());
		holder.tvLongt.setText(mLocation.getLongi().toString());
		holder.tvRemark.setText(mLocation.getRemark().toString());
		holder.tvDistnt.setText(mDistance);

		return convertView;
	}
}
