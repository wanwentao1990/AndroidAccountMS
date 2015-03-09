package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.dao.PwdDAO;
import com.xiaoke.accountsoft.model.Tb_pwd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity{

	private EditText txtRusername, txtRpassword, txtRpasswordConfirm;
	private Button	btnRconfirm, btnRcancle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		btnRcancle = (Button)findViewById(R.id.btnRcancle);
		btnRconfirm = (Button)findViewById(R.id.btnRconfirm);
		txtRusername = (EditText)findViewById(R.id.txtRusername);
		txtRpassword = (EditText)findViewById(R.id.txtRpassword);
		txtRpasswordConfirm = (EditText)findViewById(R.id.txtRpasswordconfirm);
		txtRpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);  //隐藏已输入密码
		txtRpasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		btnRconfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!txtRusername.getText().toString().isEmpty() && !txtRpassword.getText().toString().isEmpty()) {
					String username = txtRusername.getText().toString();
					String password = txtRpassword.getText().toString();
					PwdDAO pwdDAO = new PwdDAO(Register.this);
					if (null != pwdDAO.find(username)) {
						Toast.makeText(Register.this, "该用户名已被注册！", Toast.LENGTH_SHORT).show();
						return;
					}else {
						if (password.equals(txtRpasswordConfirm.getText().toString())) {
							Tb_pwd tb_pwd = new Tb_pwd(username, password);
							pwdDAO.add(tb_pwd);
							Intent intent = new Intent(Register.this,Login.class);
							Bundle bundle = new Bundle();
							bundle.putString("username", username);
							bundle.putString("password", password);
							intent.putExtras(bundle);
							setResult(0x717, intent);
							finish();
						}else {
							Toast.makeText(Register.this, "两次输入的密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
							txtRpassword.setText("");
							txtRpasswordConfirm.setText("");
						}
					}
				}else{
					Toast.makeText(Register.this, "请补全注册信息！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnRcancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (null != MainActivity.localService) {
			MainActivity.localService.playMusic();
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		MainActivity.localService.pauseMusic();
	}
}
