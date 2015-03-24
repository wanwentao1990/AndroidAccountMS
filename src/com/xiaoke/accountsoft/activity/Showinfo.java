package com.xiaoke.accountsoft.activity;

import java.util.ArrayList;
import java.util.List;

import com.xiaoke.accountsoft.dao.FlagDAO;
import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.dao.OutaccountDAO;
import com.xiaoke.accountsoft.model.Tb_flag;
import com.xiaoke.accountsoft.model.Tb_inaccount;
import com.xiaoke.accountsoft.model.Tb_outaccount;
import com.xiaoke.accountsoft.view.RTPullListView;
import com.xiaoke.accountsoft.view.RTPullListView.OnRefreshListener;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Showinfo extends Activity {
	
	static final String ACTION_NAME = "showInfoListUpdateBroad";
	private static final int LOAD_MORE_SUCCESS = 3;
	private static final int LOAD_NEW_INFO = 5;
	private RTPullListView pullListView;
	private ProgressBar moreProgressBar;
	
	private List<String> dataList;
	private ArrayAdapter<String> adapter;
	private String strType;
	private Button outButton,inButton,flagbButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);
		pullListView = (RTPullListView)findViewById(R.id.pullListView);
		outButton = (Button)findViewById(R.id.btnoutinfo);
		inButton = (Button)findViewById(R.id.btnininfo);
		flagbButton = (Button)findViewById(R.id.btnflaginfo);
		dataList = new ArrayList<String>();
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.list_footview, null);
		RelativeLayout footView = (RelativeLayout)view.findViewById(R.id.list_footview);
		moreProgressBar = (ProgressBar)view.findViewById(R.id.footer_progress);
		pullListView.addFooterView(footView);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
		pullListView.setAdapter(adapter);
		ShowInfo(R.id.btnflaginfo);
		
		pullListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final String dataString = dataList.get(position - 1);

				new AlertDialog.Builder(Showinfo.this).setTitle("系统提示")//设置对话框标题  
				  
			     .setMessage("请确认删除该条数据！")//设置显示的内容  
			  
			     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
			  
			          
			  
			         @Override  
			  
			         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
			  
			             // TODO Auto-generated method stub  
			  
							if (strType.equals("btnoutinfo")) {
								RemoveData(R.id.btnoutinfo, dataString);
							}else if (strType.equals("btnininfo")) {
//								ShowInfo(R.id.btnininfo);
								RemoveData(R.id.btnininfo, dataString);
							}else if (strType.equals("btnflaginfo")) {
//								ShowInfo(R.id.btnflaginfo);
								RemoveData(R.id.btnflaginfo, dataString);
							} 
			  
			         }  
			  
			     }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮  
			  
			          
			  
			         @Override  
			  
			         public void onClick(DialogInterface dialog, int which) {//响应事件  
			  
			             // TODO Auto-generated method stub  
			         }  
			  
			     }).show();//在按键响应事件中显示此对话框  
				return false;
			}
		});
		
		pullListView.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
							dataList.clear();
							Message message = myHandler.obtainMessage();
							message.what = LOAD_NEW_INFO;
							myHandler.sendMessage(message);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
			}
		});
		
		footView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
							dataList.add("!!!!!!!!!!!!!!!!!!!!");
							Message message = myHandler.obtainMessage();
							message.what = LOAD_MORE_SUCCESS;
							myHandler.sendMessage(message);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
			}
		});
		
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
		
		pullListView.setOnItemClickListener(new OnItemClickListener() {

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
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String typeString = intent.getStringExtra("strType");
			String action = intent.getAction();
			if (action.equals(ACTION_NAME)) {
				if (typeString.equals("btnoutinfo")) {
//					ShowInfo(R.id.btnoutinfo);
				}else if (typeString.equals("btnininfo")) {
//					ShowInfo(R.id.btnininfo);
				}else if (typeString.equals("btnflaginfo")) {
//					ShowInfo(R.id.btnflaginfo);
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
		dataList.clear();
		switch (intType) {
		case R.id.btnoutinfo:
//			currentTypeId = R.id.btnoutinfo;
			strType="btnoutinfo";
			OutaccountDAO outaccountDAO = new OutaccountDAO(Showinfo.this);
			List<Tb_outaccount> listoutinfosList = new ArrayList<Tb_outaccount>();
			listoutinfosList = outaccountDAO.getScrollData(0, (int)outaccountDAO.getCount());
			for (Tb_outaccount tb_outaccount : listoutinfosList){
				dataList.add(tb_outaccount.getId()+"|"+tb_outaccount.getType()+" "+String.valueOf(tb_outaccount.getMoney())+"元"+tb_outaccount.getTime());
			}
			break;
		case R.id.btnininfo:
//			currentTypeId = R.id.btnininfo;
			strType="btnininfo";
			InaccountDAO inaccountDAO = new InaccountDAO(Showinfo.this);
			List<Tb_inaccount> listininfoList = new ArrayList<Tb_inaccount>();
			listininfoList = inaccountDAO.getScrollData(0, (int)inaccountDAO.getCount());
			for (Tb_inaccount tb_inaccount : listininfoList){
				dataList.add(tb_inaccount.getId()+"|"+tb_inaccount.getType()+" "+String.valueOf(tb_inaccount.getMoney())+"元"+tb_inaccount.getTime());
			}
			break;
		case R.id.btnflaginfo:
//			currentTypeId = R.id.btnflaginfo;
			strType="btnflaginfo";
			FlagDAO flagDAO = new FlagDAO(Showinfo.this);
			List<Tb_flag> list = new ArrayList<Tb_flag>();
			list = flagDAO.getScrollData(0, (int)flagDAO.getCount());
			int n = 0;
			for (Tb_flag tb_flag : list){
				dataList.add(tb_flag.getId()+"|"+tb_flag.getFlag());
				if (dataList.get(n).length() > 15) {
					String tmpStr = dataList.get(n);  
					dataList.remove(n); 
					dataList.add(tmpStr.substring(0,15)+"......");
				}
				n++;
			}
			break;
		default:
			break;
			}
		adapter.notifyDataSetChanged();
		}
	
	public void RemoveData(int intType,String string ) {
		int endIndex = string.indexOf("|");
		String str = string.substring(0, endIndex);
		int index = Integer.valueOf(str);
		switch (intType) {
		case R.id.btnoutinfo:
			OutaccountDAO outaccountDAO = new OutaccountDAO(Showinfo.this);
			outaccountDAO.delete(index);
			break;
		case R.id.btnininfo:
			strType="btnininfo";
			InaccountDAO inaccountDAO = new InaccountDAO(Showinfo.this);
			inaccountDAO.delete(index);
			break;
		case R.id.btnflaginfo:
//			currentTypeId = R.id.btnflaginfo;
			strType="btnflaginfo";
			FlagDAO flagDAO = new FlagDAO(Showinfo.this);
			flagDAO.delete(index);
			break;
		default:
			break;
			}
		adapter.notifyDataSetChanged();
		}
	
	 private Handler myHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case LOAD_MORE_SUCCESS:
					moreProgressBar.setVisibility(View.GONE);
//					Android listView数据刷新，类似于reload()
					adapter.notifyDataSetChanged();
					pullListView.setSelectionfoot();
					break;

				case LOAD_NEW_INFO:
					if (strType.equals("btnininfo")) {
						ShowInfo(R.id.btnininfo);
					}else if (strType.equals("btnoutinfo")) {
						ShowInfo(R.id.btnoutinfo);
					}else if (strType.equals("btnflaginfo")) {
						ShowInfo(R.id.btnflaginfo);
					}
					pullListView.onRefreshComplete();
					break;
				default:
					break;
				}
			}
	    	
	    };
	
}
