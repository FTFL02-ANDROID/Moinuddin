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

import com.ftfl.shuvo.icarefinal.HCViewActivity;
import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.adapter.HCenterAdapter;
import com.ftfl.shuvo.icarefinal.database.SQHCSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.HealthCenter;

public class HCListFragment extends Fragment {

	public static final String TAG = HCListFragment.class.getSimpleName();

	ListView mListView = null;
	SQHCSource mSqlSource = null;
	HealthCenter mHCntrPro = null;

	String[] mName = { "Galaxy Hospital", "United Hospital", "Appolo Hospital",
			"MediNova Medical", "Ahsania Mission C.Hospital",
			"Armi Hospital-Erectors House", "Aysha Memorial Hospital",
			"Bangkok Hospital Office", "BIRDEM Hospital", "Care Hospital",
			"City Dental College & Hospital", "Dhaka Shishu Hospital",
			"Gastro Liver Hospital", "Gulshan Maa O Shishu Clinic", "Harun Eye Foundation" };
	String[] mAddress = { "Mirpur-10",
			"Plot # 15, Road # 71, Gulshan-2, Dhaka",
			"Plot # 81, Block # E, Bashudhara R/A, Dhaka",
			"House#71/A, Road#5/A, Dhanmondi R/A, Dhaka",
			"Plot No. M-1/C, Mirpur-14, Dhaka",
			"(10th floor), 18, Kamal Ataturk Avenue, Banani, Dhaka",
			"74/G/75, Pecock Squar, New Airport Road, Mohakhali, Dhaka",
			"Bangladesh-House#108, Road#8, Block# C, Banani, Dhaka",
			"122, Kazi Nazrul Islam Avenue, Shahbag, Dhaka",
			"2/1-A Iqbal Road, Mohammadpur (on Main Mirpur Road),Dhaka",
			"Malibag, Chowdhury Para, Dhaka", "Sher-E- Bangla Nagar, Dhaka",
			"69/D, Green Road, Panthapath, Dhaka", "H-11, R-2/A, Bl-J, Baridhara, Dhaka",
			"H-31, R-06, Dhanmondi R/A, Dhaka" };

	String[] mPhn = { "+880-2-8050193", "+8802-8836000 ", "+8802-8401661",
			"+8802-9144280", "+8802-9008919", "+8802-9893528", "+8802-9122689",
			"+8802-8856057", "+8802-9661551", "+8802-9134407", "+8802-9341662",
			"+8802-8114571", "+8802-8625393", "+8802-8822738", "+8802-8617871" };

	Double[] mLat = { 23.806726, 23.779885, 23.700922, 23.742016, 23.790597,
			23.793964, 23.777628, 23.790764, 23.810332, 23.810332, 23.753013,
			23.771750, 23.751510, 23.814677, 23.771773 };

	Double[] mLang = { 90.367927, 90.413218, 90.428220, 90.379455, 90.355390,
			90.403907, 90.405450, 90.404040, 90.412518, 90.412518, 90.423309,
			90.377189, 90.386137, 90.411683, 90.377355 };

	TextView textId = null;
	int id_To_Update = 0;

	public static HCListFragment newInstance() {
		return new HCListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_hc_list, container,
				false);

		mSqlSource = new SQHCSource(getActivity());

		HealthCenter hC_one;

		if (mSqlSource.profileNumber() == 0) {
			for (int i = 0; i < mName.length; i++) {

				hC_one = new HealthCenter(mName[i], mAddress[i], mPhn[i],
						mLat[i], mLang[i]);
				mSqlSource.insert(hC_one);
			}
		}

		ArrayList<HealthCenter> hCntrProfiles = mSqlSource.getAllData();

		HCenterAdapter adapter = new HCenterAdapter(getActivity(),
				hCntrProfiles);

		// adding mAdapter to the list view.
		mListView = (ListView) rootView.findViewById(R.id.listHC);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// arg1 is used to get the view. dbID1 is declared in the
				// listrow, which is hidden/gone
				textId = (TextView) arg1.findViewById(R.id.dbIdHC);
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
		Intent intent = new Intent(getActivity(), HCViewActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

}
