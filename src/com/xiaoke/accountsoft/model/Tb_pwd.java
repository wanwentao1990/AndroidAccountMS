package com.xiaoke.accountsoft.model;

public class Tb_pwd {

	private String username;
	private String password;
	
	public Tb_pwd(){
		super();
	}
	public Tb_pwd(String username, String password){
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
}
