package com.xiaoke.accountsoft.model;

public class Tb_inaccount {
	
	private int _id;
	private double money;
	private String time;
	private String type;
	private String handle;
	private String mark;
	
	public Tb_inaccount() {
	// TODO Auto-generated constructor stub
		super();
	}
	
	public Tb_inaccount(int id, double money, String time, String type, String handle, String mark){
		super();
		this._id = id;
		this.money = money;
		this.time = time;
		this.type = type;
		this.handle = handle;
		this.mark = mark;
	}
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}

