package com.ftfl.shuvo.icarefinal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.R;

public class HomeFragment extends Fragment {


	public HomeFragment() {
	}
	
	TextView tvTips, tvNotice, tvHeader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		
		 tvTips = (TextView) rootView.findViewById(R.id.tvGTips);
		 tvTips.setText(getString(R.string.generaltips));
		 
		 tvNotice = (TextView) rootView.findViewById(R.id.tvVInfo);
		 tvNotice.setText(getString(R.string.imptips));
		 
		 tvHeader = (TextView) rootView.findViewById(R.id.tvHead);
		 tvHeader.setText(getString(R.string.remember));
		 
		return rootView;
	}

}
