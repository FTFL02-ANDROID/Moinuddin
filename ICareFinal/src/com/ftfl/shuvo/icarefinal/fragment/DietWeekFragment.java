package com.ftfl.shuvo.icarefinal.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.DietViewActivity;
import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.adapter.DietWkAdapter;
import com.ftfl.shuvo.icarefinal.database.SQDietSource;
import com.ftfl.shuvo.icarefinal.util.DietProfile;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class DietWeekFragment extends Fragment {

	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	ListView mListView = null;
	SQDietSource mSqlSource = null;
	DietProfile mDietPro = null;

	TextView textId = null;
	int id_To_Update = 0;

	public DietWeekFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_diet_week,
				container, false);

		mSqlSource = new SQDietSource(getActivity());

		ArrayList<DietProfile> dietProfiles = mSqlSource.getWeeklyDates();

		DietWkAdapter adapter = new DietWkAdapter(getActivity(), dietProfiles);

		// adding mAdapter to the list view.
		mListView = (ListView) rootView.findViewById(R.id.listWeek);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// arg1 is used to get the view. dbID1 is declared in the
				// listrow, which is hidden/gone
				textId = (TextView) arg1.findViewById(R.id.dbIDWk);
				String proID = textId.getText().toString();
				// in order to use for view, delete and edit in DataBase
				id_To_Update = Integer.parseInt(proID);

				viewProfile(id_To_Update);

			}
		});

		return rootView;
	}

	public void viewProfile(int profileID) {

		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "id" is the
															// key...
		Intent intent = new Intent(getActivity(), DietViewActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

}
