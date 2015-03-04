package com.xiaoke.accountsoft.model;

public class Tb_flag {

	private int _id;
	private String flag;
	
	public Tb_flag(){
		super();
	}
	public  Tb_flag(int id, String flag) {
		super();
		this._id = id;
		this.flag = flag;
	}
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
