package com.ftfl.shuvo.icarefinal.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.VCViewActivity;
import com.ftfl.shuvo.icarefinal.VaccineActivity;
import com.ftfl.shuvo.icarefinal.adapter.VaccineAdapter;
import com.ftfl.shuvo.icarefinal.database.SQVacSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;
import com.ftfl.shuvo.icarefinal.util.VaccineProfile;

public class VCListFragment extends Fragment {

	public static final String TAG = VCListFragment.class.getSimpleName();

	ActionMode mActionMode = null;
	VaccineAdapter adapter = null;

	ListView mListView = null;
	SQVacSource mSqlSource = null;
	VaccineProfile mVacPro = null;
	ArrayList<VaccineProfile> mVCProfiles = null;

	TextView textId = null;
	int id_To_Update = 0;

	public static VCListFragment newInstance() {
		return new VCListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_vaccine_list,
				container, false);

		mSqlSource = new SQVacSource(getActivity());

		mVCProfiles = mSqlSource.getAllData();

		adapter = new VaccineAdapter(getActivity(), mVCProfiles);

		// adding mAdapter to the list view.
		mListView = (ListView) rootView.findViewById(R.id.listVaccine);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// arg1 is used to get the view. dbID1 is declared in the
				// listrow, which is hidden/gone
				textId = (TextView) arg1.findViewById(R.id.dbIdVc);
				String proID = textId.getText().toString();
				// in order to use for view, delete and edit in DataBase
				id_To_Update = Integer.parseInt(proID);

				viewProfile(id_To_Update);

			}
		});

		mListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (mActionMode != null) {
							return false;
						}
						mActionMode = getActivity().startActionMode(
								mActionModeCallback);
						textId = (TextView) view.findViewById(R.id.dbIdVc);
						String proID = textId.getText().toString();
						// in order to use for view, delete and edit in DataBase
						id_To_Update = Integer.parseInt(proID);
						view.setSelected(true);
						return true;
					}
				});

		return rootView;
	}

	public void viewProfile(int profileID) {

		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "id" is the
															// key...
		Intent intent = new Intent(getActivity(), VCViewActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			mode.setTitle("Options");
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.listmenu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.edit:
				editProfile(id_To_Update);
				mode.finish(); // Action picked, so close the CAB
				return true;

			case R.id.delete:
				deleteProfile(id_To_Update);
				mVCProfiles.clear();
				mVCProfiles = mSqlSource.getAllData();
				adapter = new VaccineAdapter(getActivity(), mVCProfiles);
				adapter.notifyDataSetChanged();
				mListView.setAdapter(adapter);
				mode.finish();
				return true;

			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};

	public void deleteProfile(int profileID) {

		mSqlSource.deleteData(profileID);
	}

	public void editProfile(int profileID) {
		Bundle dataBundle = new Bundle();
		dataBundle.putInt(FTFLConstants.KEY_ID, profileID); // "id" is the
															// key...
		Intent intent = new Intent(getActivity(), VaccineActivity.class);
		intent.putExtras(dataBundle);
		startActivity(intent);
	}

}
