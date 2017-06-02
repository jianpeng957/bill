package com.example.android_bill;

import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置页面 - 在此基础上可以添加新功能 - 由于时间问题没来得及
 * @author Jianpeng
 *
 */
public class Setting extends Activity {
	private EditText addItem;
	private TextView textviewBillSetting;
	private TextView textviewAbout;

	private Button btnAdjustItem;
	private Button btnInfo;

	private Spinner itemSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		createView();
		createButton();
	}

	private void createView() {
		addItem = new EditText(Setting.this);

		itemSpinner = (Spinner) findViewById(R.id.ItemSpinner);
		
		textviewBillSetting = (TextView) findViewById(R.id.TextViewBillSetting);
		textviewAbout = (TextView) findViewById(R.id.TextViewAbout);

		btnAdjustItem = (Button) findViewById(R.id.BtnAdjustItem);
		btnInfo = (Button) findViewById(R.id.BtnInfo);
	}

	private void createButton() {
		btnAdjustItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] options = { "添加", "删除" };
				new AlertDialog.Builder(Setting.this).setItems(options, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							new AlertDialog.Builder(Setting.this).setView(addItem).setTitle("请输入")
									.setPositiveButton("确 认", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											Log.d("setting", "1");
//											Log.d("setting", itemSpinner.toString());
//											ArrayAdapter<String> itemAdapter = ((ArrayAdapter<String>) itemSpinner.getAdapter());
//											Log.d("setting", "2");
//											String itemName = addItem.getText().toString();
//											for (int i = 0; i < itemAdapter.getCount(); i++) 
//												if (itemName.equals(itemAdapter.getItem(i))) 
//													return;
//
//											if (!itemName.equals("")) 
//												itemAdapter.add(itemName);
											
//											Bundle bundle = new Bundle();
//											bundle.putString("itemname", addItem.getText().toString());
//											new MainActivity().adjustItemSpinner();
											
											Toast.makeText(Setting.this, addItem.getText().toString(),
													Toast.LENGTH_SHORT).show();
											onResume();
										}
									}).setNegativeButton("取 消", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub

										}
									}).show();
							break;

						case 1:
							break;
						}
					}
				}).show();
			}
		});
		btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Setting.this).setTitle("版权信息").setMessage("窜天猴三人组所做~")
						.setPositiveButton("返 回", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								;
							}
						}).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		onCreate(null);
	}
}
