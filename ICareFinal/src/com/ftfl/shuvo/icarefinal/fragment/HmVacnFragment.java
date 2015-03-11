package com.ftfl.shuvo.icarefinal.fragment;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.util.Admob;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class HmVacnFragment extends Fragment {
	
	Admob mAdmob;
	Activity mActivity;
	
	public HmVacnFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_web_vccn, container,
				false);
		mActivity = getActivity();
		try {
			mAdmob = new Admob(mActivity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebView webView = (WebView) rootView.findViewById(R.id.webView);
		webView.loadUrl("http://www.icddrb.org/what-we-do/services/family-health-practice/vaccinations");
		return rootView;
	}

}
