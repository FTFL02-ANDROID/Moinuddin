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
import com.ftfl.shuvo.icarefinal.util.VaccineProfile;

@SuppressLint("InflateParams")
public class VaccineAdapter extends ArrayAdapter<VaccineProfile> {

	Activity context;
	ArrayList<VaccineProfile> mVccineList;
	VaccineProfile mVaccinePro;

	public VaccineAdapter(Activity context, ArrayList<VaccineProfile> eVaccineList) {

		super(context, R.layout.row_vaccine, eVaccineList);

		this.context = context;
		this.mVccineList = eVaccineList;
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
		mVaccinePro = mVccineList.get(position);
		ViewHolder holder = null;

		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_vaccine, null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.tvVcName);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvVcDate);
			holder.textId = (TextView) convertView.findViewById(R.id.dbIdVc);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		holder.textId.setText(mVaccinePro.getId().toString());
		holder.tvName.setText(mVaccinePro.getName().toString());
		holder.tvDate.setText(mVaccinePro.getDate().toString());

		return convertView;
	}

}
