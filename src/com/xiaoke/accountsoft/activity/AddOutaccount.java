package com.xiaoke.accountsoft.activity;

import java.util.Calendar;

import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.dao.OutaccountDAO;
import com.xiaoke.accountsoft.model.Tb_inaccount;
import com.xiaoke.accountsoft.model.Tb_outaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddOutaccount extends Activity {

	protected static final int DATE_DIALOG_ID = 0;
	EditText txtOutMoney,txtOutTime,txtOutAddress,txtOutMark;
	Spinner spOutType;
	Button btnOutSaveButton;
	Button btnOutCancelButton;
	private int mYear,mMonth,mDay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addoutaccount);
		
		txtOutMoney = (EditText)findViewById(R.id.txtOutMoney);
		txtOutTime = (EditText)findViewById(R.id.txtOutTime);
		txtOutAddress = (EditText)findViewById(R.id.txtOutAddress);
		txtOutMark = (EditText)findViewById(R.id.txtOutMark);
		spOutType = (Spinner)findViewById(R.id.spOutType);
		btnOutCancelButton = (Button)findViewById(R.id.btnOutCancel);
		btnOutSaveButton = (Button)findViewById(R.id.btnOutSave);
		
		
		txtOutTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		final Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_WEEK);
		updateDisplay();
		
		btnOutSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strOutMoneyString = txtOutMoney.getText().toString();
				if (!strOutMoneyString.isEmpty()) {
					OutaccountDAO outaccountDAO = new OutaccountDAO(AddOutaccount.this);
					Tb_outaccount tb_outaccount = new Tb_outaccount(outaccountDAO.getMaxId()+1, Double.parseDouble(strOutMoneyString), txtOutTime.getText().toString(), spOutType.getSelectedItem().toString(), txtOutAddress.getText().toString(), txtOutMark.getText().toString());
					outaccountDAO.add(tb_outaccount);
					Toast.makeText(AddOutaccount.this, "【新增支出】数据添加成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(AddOutaccount.this, "请输入支出金额！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnOutCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtOutAddress.setText("");
				txtOutMark.setText("");
				txtOutMark.setText("");
				txtOutMoney.setHint("0.00");
				txtOutMoney.setText("");
				txtOutTime.setText("");
				txtOutTime.setHint("2011-01-01");
				spOutType.setSelection(0);
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
	
	private void updateDisplay() {
		txtOutTime.setText(new StringBuilder().append(mYear).append('-').append(mMonth).append('-').append(mDay));
	}
	
	protected Dialog onCreaDialog(int id){
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
}
