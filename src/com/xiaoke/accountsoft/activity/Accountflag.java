package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.dao.FlagDAO;
import com.xiaoke.accountsoft.model.Tb_flag;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Accountflag extends Activity {

	private EditText editText;
	private Button btnSaveButton,btnCancelButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountflag);
		editText = (EditText)findViewById(R.id.txtFlag);
		btnSaveButton = (Button)findViewById(R.id.btnflagSave);
		btnCancelButton = (Button)findViewById(R.id.btnflagCancel);
		
		btnSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String string = editText.getText().toString();
				if (!string.isEmpty()) {
					FlagDAO flagDAO = new FlagDAO(Accountflag.this);
					Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId() + 1, string);
					flagDAO.add(tb_flag);
					Toast.makeText(Accountflag.this, "【新增便签】数据添加成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(Accountflag.this, "请输入便签！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editText.setText("");
				finish();
			}
		});
	}
}
