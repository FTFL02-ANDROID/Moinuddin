package com.ftfl.shuvo.icarefinal.adapter;

import java.util.ArrayList;

import com.ftfl.shuvo.icarefinal.R;
import com.ftfl.shuvo.icarefinal.util.NoteProfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class NoteAdapter extends ArrayAdapter<NoteProfile> {
	
	Activity context;
	ArrayList<NoteProfile> mNoteList;
	NoteProfile mNote;

	public NoteAdapter(Activity context, ArrayList<NoteProfile> eNoteList) {

		super(context, R.layout.row_note, eNoteList);

		this.context = context;
		this.mNoteList = eNoteList;
	}
	
	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView note;
		public TextView date;
		public TextView textId;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/***** Get each Model object from Array list ********/
		mNote = mNoteList.get(position);
		ViewHolder holder = null;
		
		LayoutInflater inflater = context.getLayoutInflater();
		
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.row_note, null);
			holder = new ViewHolder();
			holder.note = (TextView) convertView.findViewById(R.id.tvNoteNm);
			holder.date = (TextView) convertView.findViewById(R.id.tvNtDate);
			holder.textId = (TextView) convertView.findViewById(R.id.dbIdNt);
		
		/************ Set holder with LayoutInflater ************/
		convertView.setTag(holder);
	} else
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.textId.setText(mNote.getId().toString()); 
		holder.note.setText(mNote.getNotes().toString());
		holder.date.setText(mNote.getDate().toString());


		return convertView;
	}

}
