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
import com.ftfl.shuvo.icarefinal.util.HealthCenter;

@SuppressLint("InflateParams")
public class HCenterAdapter extends ArrayAdapter<HealthCenter> {

	Activity context;
	ArrayList<HealthCenter> mHCList;
	HealthCenter mHCPro;

	public HCenterAdapter(Activity context, ArrayList<HealthCenter> eHCList) {

		super(context, R.layout.row_health_center, eHCList);

		this.context = context;
		this.mHCList = eHCList;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvName;
		public TextView textId;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mHCPro = mHCList.get(position);
		ViewHolder holder = null;

		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_health_center, null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.tvHcNm);
			holder.textId = (TextView) convertView.findViewById(R.id.dbIdHC);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		holder.textId.setText(mHCPro.getId().toString());
		holder.tvName.setText(mHCPro.getName().toString());

		return convertView;
	}

}
