package com.ftfl.shuvo.icarefinal.fragment;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftfl.shuvo.icarefinal.R;

public class HomeTabb extends Fragment {
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	public static final String TAG = HomeTabb.class.getSimpleName();
	public static final String ITEM_NAME = "itemName";

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public static HomeTabb newInstance() {
		return new HomeTabb();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater
				.inflate(R.layout.tabb_home_fragment, container, false);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) v.findViewById(R.id.pagerHome);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		return v;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			//Bundle args = null;

			switch (position) {
			case 0:
				fragment = new HomeFragment();
				/*args = new Bundle();
				args.putInt(DietTodayFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);*/
				break;
			
			case 1:
				fragment = new HmVacnFragment();
				/*args = new Bundle();
				args.putInt(DietWeekFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);*/
				break;
			
			}
			return fragment;

		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.hmtittle1).toUpperCase(l);
			case 1:
				return getString(R.string.hmtittle2).toUpperCase(l);
			}
			return null;
		}

	}

}
