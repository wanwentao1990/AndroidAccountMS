package com.xiaoke.accountsoft.activity;

import android.util.*;
import com.xiaoke.accountsoft.dao.PwdDAO;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity{

	private EditText txtUsername, txtPassword;
	private Button btnLogin, btnRegister, btnCancle;
	private SoundPool soundPool;
	
	final static int CODE = 0x717;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = new Intent(Login.this,MainActivity.class);
//		Intent extraIntent = getIntent();
		if (readCacheData()) {
			startActivity(intent);
			finish();
		}
		
		setContentView(R.layout.login);
		
		soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
		txtUsername = (EditText)findViewById(R.id.txtUsername);
		txtPassword = (EditText)findViewById(R.id.txtPassword);
		txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnRegister = (Button)findViewById(R.id.btnRegister);
		btnCancle = (Button)findViewById(R.id.btnCancle);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
 				PwdDAO pwdDAO = new PwdDAO(Login.this);
				if (!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
					final String username = txtUsername.getText().toString();
					String password = txtPassword.getText().toString();
					new Thread(new Runnable() {
						public void run() {
							writeUsername(username);
						}
					}).start();
					if (pwdDAO.getCount() > 0 && pwdDAO.find(username).getPassword().equals(password)) {
						Thread thread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								writeCacheData();
							}
						});
						thread.start();
						startActivity(intent);
						finish();
					}else{
						Toast.makeText(Login.this,"请输入正确的用户名或密码！" , Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(Login.this, "请输入完整信息！", Toast.LENGTH_SHORT).show();
				}
				txtPassword.setText("");
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this,Register.class);
				startActivityForResult(intent, CODE);
			}
		});
		
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_DPAD_LEFT:	
//			soundPool.play(soundPool.load(Login.this, R.raw.clicksound, 1), 1, 1, 0, 0, 1);
//			break;
//
//		default:
//			soundPool.play(soundPool.load(Login.this, R.raw.clicksound, 1), 1, 1, 0, 0, 1);
//			break;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event){
//		int action = event.getAction();  
//		if (action == MotionEvent.ACTION_DOWN) {
//			soundPool.play(soundPool.load(Login.this, R.raw.clicksound, 1), 1, 1, 0, 0, 1);
//			Log.e("wwtlog", "Touch Down!");
//			return true;
//		}
//		return super.onTouchEvent(event);
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE && resultCode == CODE) {
			Bundle bundle = data.getExtras();
			txtUsername.setText(bundle.getString("username"));
			txtPassword.setText(bundle.getString("password"));
		}
	}
	
	private void writeCacheData() {
		SharedPreferences mySharedPreferences = getSharedPreferences("loginstatus", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("loginstatus", "true");
		editor.commit();
		Log.e("wwtlog", "登录数据写入成功！");
	}
	
	private boolean readCacheData() {
		SharedPreferences sharedPreferences = getSharedPreferences("loginstatus", 
				Activity.MODE_PRIVATE); 
				String status = sharedPreferences.getString("loginstatus", "");
				if (status.equals("true")) {
					return true;
				}else {
					return false;
				}
	}
	
	private void writeUsername(String userNameString) {
		SharedPreferences sharedPreferences = getSharedPreferences("username", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("username", userNameString);
		editor.commit();
	}
	
}
