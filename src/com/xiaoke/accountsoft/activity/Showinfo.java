package com.xiaoke.accountsoft.activity;

import java.util.ArrayList;
import java.util.List;

import com.xiaoke.accountsoft.dao.FlagDAO;
import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.dao.OutaccountDAO;
import com.xiaoke.accountsoft.model.Tb_flag;
import com.xiaoke.accountsoft.model.Tb_inaccount;
import com.xiaoke.accountsoft.model.Tb_outaccount;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Showinfo extends Activity {
	
	static final String ACTION_NAME = "showInfoListUpdateBroad";
	
	private ListView lvinfoListView;
	private String strType;
//	private int    currentTypeId;
	private Button outButton,inButton,flagbButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);
		lvinfoListView = (ListView)findViewById(R.id.lvinfo);
		outButton = (Button)findViewById(R.id.btnoutinfo);
		inButton = (Button)findViewById(R.id.btnininfo);
		flagbButton = (Button)findViewById(R.id.btnflaginfo);
		ShowInfo(R.id.btnflaginfo);
		
		outButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowInfo(R.id.btnoutinfo);
			}
		});
		
		inButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowInfo(R.id.btnininfo);
			}
		});
		
		flagbButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowInfo(R.id.btnflaginfo);
			}
		});
		
		lvinfoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String string = ((TextView)view).getText().toString();
				String strid = string.substring(0,string.indexOf("|"));
				Intent intent = null;
				if (strType.equals("btnoutinfo") || strType.equals("btnininfo")) {
					intent = new Intent(Showinfo.this,InfoManage.class);
					intent.putExtra(Inaccountinfo.FLAG, new String[]{strid,strType});
				}else if (strType.equals("btnflaginfo")){
					intent = new Intent(Showinfo.this,FlagManager.class);
					intent.putExtra(Inaccountinfo.FLAG, new String[]{strid,strType});
				}
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		registerBoradcastReceiver();
	}
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String typeString = intent.getStringExtra("strType");
			String action = intent.getAction();
			if (action.equals(ACTION_NAME)) {
//				刷新列表
				if (typeString.equals("btnoutinfo")) {
					ShowInfo(R.id.btnoutinfo);
				}else if (typeString.equals("btnininfo")) {
					ShowInfo(R.id.btnininfo);
				}else if (typeString.equals("btnflaginfo")) {
					ShowInfo(R.id.btnflaginfo);
				}
			}
		}
	};
	
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	public void ShowInfo(int intType) {
		String[] strinfoStrings = null;
		ArrayAdapter<String> adapter = null;
		switch (intType) {
		case R.id.btnoutinfo:
//			currentTypeId = R.id.btnoutinfo;
			strType="btnoutinfo";
			OutaccountDAO outaccountDAO = new OutaccountDAO(Showinfo.this);
			List<Tb_outaccount> listoutinfosList = new ArrayList<Tb_outaccount>();
			listoutinfosList = outaccountDAO.getScrollData(0, (int)outaccountDAO.getCount());
			strinfoStrings = new String[listoutinfosList.size()];
			int i = 0;
			for (Tb_outaccount tb_outaccount : listoutinfosList){
				strinfoStrings[i] = tb_outaccount.getId()+"|"+tb_outaccount.getType()+" "+String.valueOf(tb_outaccount.getMoney())+"元"+tb_outaccount.getTime();
				i++;
			}
			break;
		case R.id.btnininfo:
//			currentTypeId = R.id.btnininfo;
			strType="btnininfo";
			InaccountDAO inaccountDAO = new InaccountDAO(Showinfo.this);
			List<Tb_inaccount> listininfoList = new ArrayList<Tb_inaccount>();
			listininfoList = inaccountDAO.getScrollData(0, (int)inaccountDAO.getCount());
			strinfoStrings = new String[listininfoList.size()];
			int m = 0;
			for (Tb_inaccount tb_inaccount : listininfoList){
				strinfoStrings[m] = tb_inaccount.getId()+"|"+tb_inaccount.getType()+" "+String.valueOf(tb_inaccount.getMoney())+"元"+tb_inaccount.getTime();
				m++;
			}
			break;
		case R.id.btnflaginfo:
//			currentTypeId = R.id.btnflaginfo;
			strType="btnflaginfo";
			FlagDAO flagDAO = new FlagDAO(Showinfo.this);
			List<Tb_flag> list = new ArrayList<Tb_flag>();
			list = flagDAO.getScrollData(0, (int)flagDAO.getCount());
			strinfoStrings = new String[list.size()];
			int n = 0;
			for (Tb_flag tb_flag : list){
				strinfoStrings[n] = tb_flag.getId()+"|"+tb_flag.getFlag();
				if (strinfoStrings[n].length() > 15) {
					strinfoStrings[n] = strinfoStrings[n].substring(0,15)+"......";
				}
				n++;
			}
			break;
		default:
			break;
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strinfoStrings);
		lvinfoListView.setAdapter(adapter);
	}
	
}
