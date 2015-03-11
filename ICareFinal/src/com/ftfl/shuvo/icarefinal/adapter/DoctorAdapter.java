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
import com.ftfl.shuvo.icarefinal.util.DoctorProfile;

@SuppressLint("InflateParams")
public class DoctorAdapter extends ArrayAdapter<DoctorProfile> {

	Activity context;
	ArrayList<DoctorProfile> mDrList;
	DoctorProfile mDrPro;

	public DoctorAdapter(Activity context, ArrayList<DoctorProfile> eNoteList) {

		super(context, R.layout.row_doctor, eNoteList);

		this.context = context;
		this.mDrList = eNoteList;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvName;
		public TextView tvSpecial;
		public TextView textId;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mDrPro = mDrList.get(position);
		ViewHolder holder = null;

		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_doctor, null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.tvDrName);
			holder.tvSpecial = (TextView) convertView
					.findViewById(R.id.tvSpecial);
			holder.textId = (TextView) convertView.findViewById(R.id.dbIdDr);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		holder.textId.setText(mDrPro.getId().toString());
		holder.tvName.setText(mDrPro.getName().toString());
		holder.tvSpecial.setText(mDrPro.getSpeciality().toString());

		return convertView;
	}

}
