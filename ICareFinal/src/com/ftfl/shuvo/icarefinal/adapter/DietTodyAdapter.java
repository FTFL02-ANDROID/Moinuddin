package com.ftfl.shuvo.icarefinal.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.util.DietProfile;

@SuppressLint("InflateParams")
public class DietTodyAdapter extends ArrayAdapter<DietProfile> {
	
	Activity context;
	ArrayList<DietProfile> mDietList;
	DietProfile mDietPro;

	public DietTodyAdapter(Activity context, ArrayList<DietProfile> eDietList) {

		super(context, R.layout.row_todaydiet, eDietList);

		this.context = context;
		this.mDietList = eDietList;
	}
	
	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvName;
		public TextView tvMenue;
		public TextView tvTime;
		public TextView textId;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mDietPro = mDietList.get(position);
		ViewHolder holder = null;
		
		LayoutInflater inflater = context.getLayoutInflater();
		
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_todaydiet, null);
			holder = new ViewHolder();
			
			holder.tvName = (TextView) convertView.findViewById(R.id.tvMeal);
			holder.tvMenue = (TextView) convertView.findViewById(R.id.tvMenue);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.textId = (TextView) convertView.findViewById(R.id.dbID1);
		
		/************ Set holder with LayoutInflater ************/
		convertView.setTag(holder);
	} else
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.textId.setText(mDietPro.getId().toString()); 
		holder.tvName.setText(mDietPro.getName().toString());
		holder.tvMenue.setText(mDietPro.getMenu().toString()); 
		holder.tvTime.setText(mDietPro.getTime().toString());
		
		return convertView;
	}


}
