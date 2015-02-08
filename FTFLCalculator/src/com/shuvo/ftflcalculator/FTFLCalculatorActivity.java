package com.shuvo.ftflcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FTFLCalculatorActivity extends ActionBarActivity {

	Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bdot, badd, bsub, bmul,
			bdiv, beq, clearScreen;
	EditText et;
	double val1 = 0.0;
	double val2;
	boolean add, sub, div, mul;
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftflcalculator);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button5);
		b5 = (Button) findViewById(R.id.button6);
		b6 = (Button) findViewById(R.id.button7);
		b7 = (Button) findViewById(R.id.button9);
		b8 = (Button) findViewById(R.id.button10);
		b9 = (Button) findViewById(R.id.button11);
		b0 = (Button) findViewById(R.id.button14);
		bdot = (Button) findViewById(R.id.button13);
		badd = (Button) findViewById(R.id.button4);
		bsub = (Button) findViewById(R.id.button8);
		bmul = (Button) findViewById(R.id.button12);
		bdiv = (Button) findViewById(R.id.button16);
		beq = (Button) findViewById(R.id.button15);
		et = (EditText) findViewById(R.id.editText1);
		clearScreen = (Button) findViewById(R.id.button17);

		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "1");
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "2");
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "3");
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "4");
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "5");
			}
		});
		b6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "6");
			}
		});
		b7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "7");
			}
		});
		b8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "8");
			}
		});
		b9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText(et.getText() + "9");
			}
		});
		b0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				et.setText(et.getText() + "0");

			}
		});
		bdot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// so app will not get more than 1 dot
				if (count > 0) {
					return;
				}
				et.setText(et.getText() + ".");

				count++;

			}
		});

		badd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					val1 = Double.parseDouble(et.getText() + "");
					add = true;
					et.setText(null);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				}
			}
		});
		bsub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					val1 = Double.parseDouble(et.getText() + "");
					sub = true;
					et.setText(null);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				}
			}
		});
		bdiv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					val1 = Double.parseDouble(et.getText() + "");
					div = true;
					et.setText(null);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				}
			}
		});
		bmul.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					val1 = Double.parseDouble(et.getText() + "");
					mul = true;
					et.setText(null);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block

				}
			}
		});

		beq.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//if u don't enter 2 values, it'll handle this error
				try {
					val2 = Double.parseDouble(et.getText() + "");

					if (add == true) {
						et.setText(val1 + val2 + "");
						add = false;
					}
					if (sub == true) {
						et.setText(val1 - val2 + "");
						sub = false;
					}
					if (mul == true) {
						et.setText(val1 * val2 + "");
						mul = false;
					}
					if (div == true) {
						et.setText(val1 / val2 + "");
						div = false;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					Toast.makeText(FTFLCalculatorActivity.this,
							"Click a valid Number", Toast.LENGTH_SHORT).show();
				}
			}

		});

		clearScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et.setText("");
				count = 0; // it'll help the app to take dot again

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ftflcalculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
