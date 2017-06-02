package com.example.android_bill;

/**
 * Bill - 账单数据对象
 * @author Jianpeng
 *
 */
public class Bill {
	private String item;
	private String inorout;
	private float fee;
	private String time;
	private String comment;

	public Bill() {
	}

	public Bill(String item, String inorout, float fee, String time, String comment) {
		super();
		this.item = item;
		this.inorout = inorout;
		this.fee = fee;
		this.time = time;
		this.comment = comment;
	}

	public String getItem() {
		return item;
	}

	public String getInorout() {
		return inorout;
	}

	public float getFee() {
		return fee;
	}

	public String getTime() {
		return time;
	}

	public String getComment() {
		return comment;
	}
}
