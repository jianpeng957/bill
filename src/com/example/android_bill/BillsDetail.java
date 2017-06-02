package com.example.android_bill;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 账单详情页面
 * @author Jianpeng
 *
 */
public class BillsDetail extends Activity {
	private DBOperator db;
	private ListView itemListView;
	private TextView moneyTextView;
	private List<String> itemList;
	private List<Bill> billList;
	private ArrayAdapter<String> adapter;
	private String[] itemcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bills);

		db = new DBOperator(MainActivity.getContext());
		creatitemListView();
		creatMoneyTextView();
	}

	// 获取数据库中的数据并渲染到视图
	public void creatitemListView() {
		itemListView = (ListView) findViewById(R.id.ListView);
		itemList = new ArrayList<String>();
		billList = db.query();

		// Toast.makeText(MainActivity.getContext(), "" + (billList.isEmpty()),
		// Toast.LENGTH_SHORT).show();
		for (int i = 0; i < billList.size(); i++) {
			Bill bill = billList.get(i);
			String item = bill.getItem();
			String inorout = bill.getInorout();
			float fee = bill.getFee();
			String time = bill.getTime();
			String comment = bill.getComment();

			// itemList.add(inorout + " " + item + " " + fee + "\t\t " + time +
			// " " + comment);
			// itemList.add(String.format("%3s%8s %-9.1f\t\t%-13s\t\t%-15s",
			// inorout, item, fee, time, comment));
			itemList.add(String.format("%3s%8s        %-9s\t\t%-13s\t\t%-15s", inorout, item, String.valueOf(fee), time,
					comment));
		}

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		itemListView.setAdapter(adapter);

		itemListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		itemListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(1, 100, 1, "编辑");
				menu.add(1, 101, 1, "删除");
			}
		});
	}

	// 注册选项点击事件
	public boolean onContextItemSelected(MenuItem menuItem) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) menuItem.getMenuInfo();
		int position = menuInfo.position;
		String itemList = (String) itemListView.getItemAtPosition(position);
		itemcontent = itemList.split("  +| \t+");

		for (int i = 0; i < itemcontent.length; i++)
			itemcontent[i] = itemcontent[i].trim();

		switch (menuItem.getItemId()) {
		case 100:
			Intent intent = new Intent(BillsDetail.this, Update.class);

			intent.putExtra("inorout", itemcontent[0]);
			intent.putExtra("item", itemcontent[1]);
			intent.putExtra("fee", itemcontent[2]);
			intent.putExtra("time", itemcontent[3]);
			intent.putExtra("comment", itemcontent[4]);
			intent.putExtra("itemcontent", itemcontent);
			menuItem.setIntent(intent);
			break;

		case 101:
			new AlertDialog.Builder(this).setTitle("Warning").setMessage("你确认删除吗?")
					.setPositiveButton("确 认", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							db.delete(itemcontent);
							onResume();
						}
					}).setNegativeButton("取 消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							;
						}
					}).show();
			break;
		}
		return false;
	}

	public void creatMoneyTextView() {
		moneyTextView = (TextView) findViewById(R.id.TotalMoney);
		moneyTextView.setText(db.getBillsTotal());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, 100, 1, "筛选查询");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 100:
			final String[] options = new String[] { "月份筛选", "年份筛选", "自定义筛选" };

			new AlertDialog.Builder(this).setItems(options, new OnClickListener() {
				// 点击任何一个列表选项都会触发这个方法
				// arg1：点击的是哪一个选项
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:

						break;

					case 1:

						break;
					case 2:

						break;
					}

					Toast.makeText(BillsDetail.this, options[which], Toast.LENGTH_SHORT).show();
				}
			}).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		onCreate(null);
	}
}
