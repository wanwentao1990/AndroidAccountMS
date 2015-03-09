package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.dao.PwdDAO;
import com.xiaoke.accountsoft.model.Tb_pwd;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sysset extends Activity {

	private EditText newPasswordText,passwordText;
	private Button btnSet,btnCancle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysset);
		newPasswordText = (EditText)findViewById(R.id.txtSysusername);
		passwordText = (EditText)findViewById(R.id.txtSyspassword);
		btnSet = (Button)findViewById(R.id.btnSysconfirm);
		btnCancle = (Button)findViewById(R.id.btnSyscancle);
		
		btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (newPasswordText.getText().toString().equals(passwordText.getText().toString())) {
					PwdDAO pwdDAO = new PwdDAO(Sysset.this);
					String username = readUsername();
					Tb_pwd tb_pwd = new Tb_pwd(username, passwordText.getText().toString());
					pwdDAO.update(tb_pwd);
					Toast.makeText(Sysset.this, "【密码】修改成功！", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(Sysset.this, "两次输入的密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
					newPasswordText.setText("");
					passwordText.setText("");
				}
			}
		});
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newPasswordText.setText("");
				passwordText.setText("");
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
	
	private String readUsername() {
		SharedPreferences sharedPreferences = getSharedPreferences("username", Activity.MODE_PRIVATE);
		String usernameString = sharedPreferences.getString("username", "");
		return usernameString;
	}
}
