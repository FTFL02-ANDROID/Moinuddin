package com.ftfl.shuvo.icarefinal.adapter;

import java.util.ArrayList;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.util.DietProfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class DietWkAdapter extends ArrayAdapter<DietProfile> {
	
	Activity context;
	ArrayList<DietProfile> mDietList;
	DietProfile mDietPro;

	public DietWkAdapter(Activity context, ArrayList<DietProfile> eDietList) {

		super(context, R.layout.row_wkdiet, eDietList);

		this.context = context;
		this.mDietList = eDietList;
	}
	
	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvName;
		public TextView tvDate;
		public TextView textId;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mDietPro = mDietList.get(position);
		ViewHolder holder = null;
		
		LayoutInflater inflater = context.getLayoutInflater();
		
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_wkdiet, null);
			holder = new ViewHolder();
			
			holder.tvName = (TextView) convertView.findViewById(R.id.tvMealWk);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvDateWk);
			holder.textId = (TextView) convertView.findViewById(R.id.dbIDWk);
		
		/************ Set holder with LayoutInflater ************/
		convertView.setTag(holder);
	} else
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.textId.setText(mDietPro.getId().toString()); 
		holder.tvName.setText(mDietPro.getName().toString());
		holder.tvDate.setText(mDietPro.getDate().toString());
		
		return convertView;
	}


}
