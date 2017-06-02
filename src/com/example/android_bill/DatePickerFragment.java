package com.example.android_bill;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * 时间选择器
 * @author Jianpeng
 *
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	private int year;
	private int month;
	private int day;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		this.year = c.get(Calendar.YEAR);
		this.month = c.get(Calendar.MONTH);
		this.day = c.get(Calendar.DAY_OF_MONTH);
		return new MyDatePickerDialog(getActivity(), this, this.year, this.month, this.day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {

	}

	public String getValue() {
		return "" + this.year + "年" + this.month + "月" + this.day + "日";
	}

}