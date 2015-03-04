package com.xiaoke.accountsoft.activity;

import com.xiaoke.accountsoft.extra.ItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	GridView info;
	String[] titles = new String[]{"新增支出","新增收入","我的支出","我的收入","数据管理","系统设置","收支便签","退出当前账号"};
	int[] images = new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		info = (GridView)findViewById(R.id.gvInfo);
		ItemAdapter itemAdapter = new ItemAdapter(titles, images, this);
		info.setAdapter(itemAdapter);
		info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				switch (position) {
				case 0:
					intent = new Intent(MainActivity.this,AddOutaccount.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this,AddInaccount.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this,Outaccoutinfo.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(MainActivity.this,Inaccountinfo.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(MainActivity.this,Showinfo.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(MainActivity.this,Sysset.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(MainActivity.this,Accountflag.class);
					startActivity(intent);
					break;
				case 7:
					Thread thread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							wirteCacheData();
						}
					});
					thread.start();
					intent = new Intent(MainActivity.this,Login.class);
//					intent.putExtra("flag", "exit");
					startActivity(intent);
					finish();
					break;
				}
				
			}
		});
	}
	
	public void exit() {
		
	}
	
	public void wirteCacheData() {
		SharedPreferences mySharedPreferences = getSharedPreferences("loginstatus", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("loginstatus", "false");
		editor.commit();
		Log.e("wwtlog", "退出数据写入成功！");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
