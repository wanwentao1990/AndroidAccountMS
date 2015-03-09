package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.dao.FlagDAO;
import com.xiaoke.accountsoft.model.Tb_flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FlagManager extends Activity {

	private EditText editText;
	Button btnEdit,btnDel;
	String strId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flagmanage);
		
		editText = (EditText)findViewById(R.id.txtFlagManage);
		btnEdit = (Button)findViewById(R.id.btnFlagManageEdit);
		btnDel = (Button)findViewById(R.id.btnFlagManageDelete);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String[] strInfos = bundle.getStringArray(Inaccountinfo.FLAG);
		strId = strInfos[0];
		final FlagDAO flagDAO = new FlagDAO(FlagManager.this);
		editText.setText(flagDAO.find(Integer.parseInt(strId)).getFlag());
		
		btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Tb_flag tb_flag = new Tb_flag();
				tb_flag.setId(Integer.parseInt(strId));
				tb_flag.setFlag(editText.getText().toString());
				flagDAO.update(tb_flag);
				
				Toast.makeText(FlagManager.this, "【便签数据】修改成功！", Toast.LENGTH_SHORT).show();
			}
		});
		
		btnDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flagDAO.delete(Integer.parseInt(strId));
				Toast.makeText(FlagManager.this, "【便签数据】删除成功", Toast.LENGTH_SHORT).show();
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
