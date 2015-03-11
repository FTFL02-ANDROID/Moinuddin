package com.ftfl.shuvo.icarefinal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.ftfl.shuvo.icarefinal.database.SQLiteHelper;
import com.ftfl.shuvo.icarefinal.database.SQVacSource;
import com.ftfl.shuvo.icarefinal.util.FTFLConstants;

public class VCViewActivity extends Activity {

	TextView tvName = null, tvReason = null, tvDate = null, tvTime = null;
	SQVacSource mSqlSource = null;
	Bundle extras = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaccine_view);

		mSqlSource = new SQVacSource(this);
		extras = getIntent().getExtras();

		tvName = (TextView) findViewById(R.id.tvVcNmVw);
		tvReason = (TextView) findViewById(R.id.tvVcReason);
		tvDate = (TextView) findViewById(R.id.tvVcDateVw);
		tvTime = (TextView) findViewById(R.id.tvVcTimeVw);

		if (extras != null) {
			int value = extras.getInt(FTFLConstants.KEY_ID);

			if (value > 0) {
				Cursor cursor = mSqlSource.getData(value);

				if (cursor.moveToFirst()) {

					String name = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_NAME_VC_FIELD));
					String reason = cursor.getString(cursor
							.getColumnIndex(SQLiteHelper.COLUMNL_REASON_FIELD));
					String date = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_DATE_VC_FIELD));
					String time = cursor
							.getString(cursor
									.getColumnIndex(SQLiteHelper.COLUMNL_TIME_VC_FIELD));

					tvName.setText(name);
					tvReason.setText(reason);
					tvDate.setText(date);
					tvTime.setText(time);
				}
			}
		}
	}

}
