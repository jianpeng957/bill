package com.example.android_bill;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class MyDatePickerDialog extends DatePickerDialog {

	@SuppressWarnings("deprecation")
	public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);

		this.setTitle("选择指定的日期");
		this.setButton2("确认", (OnClickListener) null);
		this.setButton("取消", this);

	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		this.setTitle("选择指定的日期");
	}

}