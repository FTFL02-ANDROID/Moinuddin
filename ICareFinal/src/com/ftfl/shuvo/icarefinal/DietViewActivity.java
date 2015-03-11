package com.ftfl.shuvo.icarefinal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.database.SQDietSource;
import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.util.Admob;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class DietViewActivity extends Activity {

	TextView tvName = null, tvMenue = null, tvDate = null, tvTime = null;
	ImageView mAlarm = null;
	SQDietSource mSqlSource = null;
	Bundle extras = null;
	Admob mAdmob;
	Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diet_view);
		
		mActivity = DietViewActivity.this;
		mAdmob = new Admob(mActivity);
		mSqlSource = new SQDietSource(this);
		extras = getIntent().getExtras();

		tvName = (TextView) findViewById(R.id.tvMealDtv);
		tvMenue = (TextView) findViewById(R.id.tvMenuDtv);
		tvDate = (TextView) findViewById(R.id.tvDateDtv);
		tvTime = (TextView) findViewById(R.id.tvTimeDtv);
		mAlarm = (ImageView) findViewById(R.id.alarm);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_NAME_DT_FIELD));
					String date = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_DATE_FIELD));
					String time = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_TIME_FIELD));
					String menu = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_MENU_FIELD));
					String alarm = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_ALARM_FIELD));
					
					int eAlarm = Integer.parseInt(alarm);
					
					if(eAlarm == 1){
						mAlarm.setVisibility(0);
					}

					tvName.setText(name);
					tvMenue.setText(menu);
					tvDate.setText(date);
					tvTime.setText(time);
				}
			}
		}
	}

}
