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
 * 更新数据页面 - 与主程序页面大致相同，但有明显的编辑提示框
 * @author Jianpeng
 *
 */
public class Update extends Activity implements OnItemSelectedListener {
	private static Context context;
	private List<String> itemList;
	private List<String> inOrOutList;
	private ArrayAdapter<String> itemAdapter;
	private ArrayAdapter<String> inoroutAdapter;
	private DBOperator db;

	private Spinner itemSpinner;
	private Spinner inOrOutSpinner;
	private Button btnSure;
	private Button btnCancel;
	private Button btnChangeDate;
	private EditText editTime;
	private EditText editFee;
	private EditText editComment;

	private String inorout;
	private String item;
	private String fee;
	private String time;
	private String comment;
	private String[] itemcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updating);

		context = getApplicationContext();
		db = new DBOperator(Update.getContext());

		createView();
		createButton();
		createItemSpinner();
		createInOrOutSpinner();
		createTimePicker();
		getIntentFromBillsDetail();
		setValueForView();
	}

	public static Context getContext() {
		return context;
	}

	private void getIntentFromBillsDetail() {
		Intent intent = getIntent();
		inorout = intent.getStringExtra("inorout");
		item = intent.getStringExtra("item");
		fee = intent.getStringExtra("fee");
		time = intent.getStringExtra("time");
		comment = intent.getStringExtra("comment");
		itemcontent = intent.getStringArrayExtra("itemcontent");
	}

	private void setValueForView() {
		inOrOutSpinner.setSelection(inoroutAdapter.getPosition(inorout));
		itemSpinner.setSelection(itemAdapter.getPosition(item));
		editFee.setText(fee);
		editTime.setText(time);
		editComment.setText(comment);
	}

	private void createView() {
		btnSure = (Button) findViewById(R.id.BtnSure);
		btnCancel = (Button) findViewById(R.id.BtnCancel);
		btnChangeDate = (Button) findViewById(R.id.BtnChangeDate);

		itemSpinner = (Spinner) findViewById(R.id.ItemSpinner);
		inOrOutSpinner = (Spinner) findViewById(R.id.InOrOutSpinner);

		editTime = (EditText) findViewById(R.id.EditTime);
		editFee = (EditText) findViewById(R.id.EditFee);
		editComment = (EditText) findViewById(R.id.EditComment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, 100, 1, "账目详情");
		menu.add(1, 101, 1, "退出程序");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 100:
			Intent intent = new Intent(Update.this, BillsDetail.class);
			item.setIntent(intent);
			break;
		case 101:
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void createButton() {
		btnSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editFee.getText().toString().equals("")) {
					Toast.makeText(Update.this, "请输入金额", Toast.LENGTH_SHORT).show();
					return;
				}

				int specifyid = db.query(itemcontent[0], itemcontent[1], itemcontent[2], itemcontent[3],
						itemcontent[4]);

				itemcontent[0] = inOrOutSpinner.getSelectedItem().toString();
				itemcontent[1] = itemSpinner.getSelectedItem().toString();
				itemcontent[2] = editFee.getText().toString();
				itemcontent[3] = editTime.getText().toString();
				itemcontent[4] = editComment.getText().toString();

				db.update(specifyid, itemcontent);

				Update.this.finish();
				Toast.makeText(Update.this, "插入成功", Toast.LENGTH_SHORT).show();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Update.this.finish();
				Toast.makeText(Update.this, "退出成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

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

	public void createInOrOutSpinner() {
		inOrOutList = new ArrayList<String>();
		inOrOutList.add("收入");
		inOrOutList.add("支出");

		inoroutAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inOrOutList);
		inoroutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inOrOutSpinner.setAdapter(inoroutAdapter);
		inOrOutSpinner.setOnItemSelectedListener(this);
	}

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

	public void cleanContent() {
		Calendar c = Calendar.getInstance();
		itemSpinner.setSelection(0);
		inOrOutSpinner.setSelection(0);
		editFee.setText("");
		editTime.setText("" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"
				+ c.get(Calendar.DAY_OF_MONTH) + "日");
		editComment.setText("");
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
