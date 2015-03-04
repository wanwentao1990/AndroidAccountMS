package com.xiaoke.accountsoft.activity;

import java.util.Calendar;

import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.model.Tb_inaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddInaccount extends Activity {
	
	protected static final int DATE_DIALOG_ID = 0;
	EditText txtInMoney,txtInTime,txtInHandler,txtInMark;
	Spinner spInType;
	Button btnInSaveButton;
	Button btnCancelButton;
	private int mYear,mMonth,mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addinaccount);
		
		txtInMoney = (EditText)findViewById(R.id.txtInMoney);
		txtInTime = (EditText)findViewById(R.id.txtInTime);
		txtInHandler = (EditText)findViewById(R.id.txtInHandler);
		txtInMark = (EditText)findViewById(R.id.txtInMark);
		spInType = (Spinner)findViewById(R.id.spInType);
		btnInSaveButton = (Button)findViewById(R.id.btnInSave);
		btnCancelButton = (Button)findViewById(R.id.btnInCancel);
		
		txtInTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();
		
		btnInSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strInMoneyString = txtInMoney.getText().toString();
				if (!strInMoneyString.isEmpty()) {
					InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
					Tb_inaccount tb_inaccount = new Tb_inaccount(inaccountDAO.getMaxId()+1, Double.parseDouble(strInMoneyString), txtInTime.getText().toString(), spInType.getSelectedItem().toString(), txtInHandler.getText().toString(), txtInMark.getText().toString());
					inaccountDAO.add(tb_inaccount);
					Toast.makeText(AddInaccount.this, "【新增收入】数据添加成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(AddInaccount.this, "请输入收入金额！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtInHandler.setText("");
				txtInMark.setText("");
				txtInMoney.setText("");
				txtInMoney.setHint("0.00");
				txtInTime.setText("");
				txtInTime.setHint("2011-01-01");
				spInType.setSelection(0);
			}
		});
	}
	
	private void updateDisplay() {
		txtInTime.setText(new StringBuilder().append(mYear).append('-').append(mMonth).append('-').append(mDay));
	}
	
	protected Dialog onCreaDialog(int id){
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener,mYear,mMonth,mDay);
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
