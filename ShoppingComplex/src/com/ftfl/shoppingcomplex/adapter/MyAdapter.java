package com.ftfl.shoppingcomplex.adapter;

import java.util.ArrayList;

import com.ftfl.shoppingcomplex.R;
import com.ftfl.shoppingcomplex.util.SComplexInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<SComplexInfo> {

	Activity mContext;
	ArrayList<SComplexInfo> mListSInfo;
	SComplexInfo mSCompInfo;

	public MyAdapter(Activity context, ArrayList<SComplexInfo> eInfo) {

		super(context, R.layout.rowlist, eInfo);

		this.mContext = context;
		this.mListSInfo = eInfo;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView nameTxt = null;
		public TextView openTxt = null;
		public TextView textId = null;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mSCompInfo = mListSInfo.get(position);

		ViewHolder holder = null;

		/*********** Layout inflator to call external xml layout () ***********/
		LayoutInflater inflater = mContext.getLayoutInflater();

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			convertView = inflater.inflate(R.layout.rowlist, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/
			holder = new ViewHolder();
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name);
			holder.openTxt = (TextView) convertView.findViewById(R.id.openTime);
			holder.textId = (TextView) convertView.findViewById(R.id.dbID);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		/*
		 * tvId to use for delete and edit in DataBase
		 */
		holder.textId.setText(mSCompInfo.getId().toString());
		holder.nameTxt.setText(mSCompInfo.getName().toString());
		holder.openTxt.setText(mSCompInfo.getOpenTime().toString());

		return convertView;
	}

}
