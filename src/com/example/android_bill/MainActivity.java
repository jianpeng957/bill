package com.example.android_bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 主程序页面
 */
public class MainActivity extends Activity implements OnItemSelectedListener {
	private static Context context;
	private List<String> itemList;
	private List<String> inOrOutList;
	private ArrayAdapter<String> itemAdapter;
	private ArrayAdapter<String> inoroutAdapter;
	private DBOperator db;

	private Spinner itemSpinner;
	private Spinner inOrOutSpinner;
	private Button btnSure;
	private Button btnClear;
	private Button btnChangeDate;
	private EditText editTime;
	private EditText editFee;
	private EditText editComment;

	/*
	 * 统一初始化步骤
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 获取当前Activity上下文
		context = getApplicationContext();
		// 获取数据库操作对象
		db = new DBOperator(MainActivity.getContext());

		createView();
		createButton();
		createItemSpinner();
		createInOrOutSpinner();
		createTimePicker();
	}

	public static Context getContext() {
		return context;
	}

	// 创建面板
	private void createView() {
		btnSure = (Button) findViewById(R.id.BtnSure);
		btnClear = (Button) findViewById(R.id.BtnClear);
		btnChangeDate = (Button) findViewById(R.id.BtnChangeDate);

		itemSpinner = (Spinner) findViewById(R.id.ItemSpinner);
		inOrOutSpinner = (Spinner) findViewById(R.id.InOrOutSpinner);

		editTime = (EditText) findViewById(R.id.EditTime);
		editFee = (EditText) findViewById(R.id.EditFee);
		editComment = (EditText) findViewById(R.id.EditComment);
	}

	// 创建菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, 100, 1, "账目详情");
		menu.add(1, 101, 1, "设置");
		menu.add(1, 102, 1, "退出");
		return true;
	}

	// 注册菜单选项事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 100:
			Intent billsdetailIntent = new Intent(MainActivity.this, BillsDetail.class);
			item.setIntent(billsdetailIntent);
			break;
		case 101:
			Intent settingIntent = new Intent(MainActivity.this, Setting.class);
			item.setIntent(settingIntent);
			break;
		case 102:
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	// 创建按钮并注册事件
	public void createButton() {
		btnSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editFee.getText().toString().equals("")) {
					Toast.makeText(MainActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
					return;
				}

				String item = itemSpinner.getSelectedItem().toString();
				String inorout = inOrOutSpinner.getSelectedItem().toString();
				Double fee = Double.parseDouble(editFee.getText().toString());
				String time = editTime.getText().toString();
				String comment = editComment.getText().toString();

				if (db.insert(item, inorout, fee, time, comment))
					cleanContent();
				Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
			}
		});
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cleanContent();
				Toast.makeText(MainActivity.this, "清理成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// 创建初始化多选项
	public void createItemSpinner() {
		itemList = new ArrayList<String>();
		itemList.add("家庭");
		itemList.add("工作");
		itemList.add("学校");
		itemList.add("生活");

		itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemList);
		itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		itemSpinner.setAdapter(itemAdapter);
		itemSpinner.setOnItemSelectedListener(this);
	}

	// 创建收入/支出类型选项
	public void createInOrOutSpinner() {
		inOrOutList = new ArrayList<String>();
		inOrOutList.add("收入");
		inOrOutList.add("支出");

		inoroutAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inOrOutList);
		inoroutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inOrOutSpinner.setAdapter(inoroutAdapter);
		inOrOutSpinner.setOnItemSelectedListener(this);
	}

	// 创建时间选择器
	public void createTimePicker() {

		final Calendar c = Calendar.getInstance();
		editTime.setText(
				c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");

		btnChangeDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerFragment datePickerFrg = new DatePickerFragment() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						editTime.setText(year + "年" + (month + 1) + "月" + day + "日");
					}
				};
				datePickerFrg.show(getFragmentManager(), "datePickerFrg");
			}
		});
	}

	// 添加清空内容按钮
	public void cleanContent() {
		Calendar c = Calendar.getInstance();
		itemSpinner.setSelection(0);
		inOrOutSpinner.setSelection(0);
		editFee.setText("");
		editTime.setText("" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"
				+ c.get(Calendar.DAY_OF_MONTH) + "日");
		editComment.setText("");
	}
	
	// 调整多选项
	public void adjustItemSpinner(Bundle bundle) {
		String itemName = bundle.getString("itemname");
		for (int i = 0; i < itemAdapter.getCount(); i++) 
			if (itemName.equals(itemAdapter.getItem(i))) 
				return;

		if (!itemName.equals("")) 
			itemAdapter.add(itemName);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
