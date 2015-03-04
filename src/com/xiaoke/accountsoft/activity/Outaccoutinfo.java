package com.xiaoke.accountsoft.activity;

import java.util.List;

import com.xiaoke.accountsoft.dao.OutaccountDAO;
import com.xiaoke.accountsoft.model.Tb_outaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Outaccoutinfo extends Activity {

	public static final String FLAG = "id";
	ListView lvinfo;
	String strType = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outaccountinfo);
		
		lvinfo = (ListView)findViewById(R.id.lvoutaccountinfo);
		showInfo();
		lvinfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String strInfo = String.valueOf( ((TextView)view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));
				Intent intent = new Intent(Outaccoutinfo.this,InfoManage.class);
				intent.putExtra(FLAG, new String[]{strid,strType});
				startActivity(intent);
			}
		});
	}
	
	public void showInfo() {
		String[] strInfo = null;
		ArrayAdapter<String> arrayAdapter = null;
		strType = "btnoutinfo";
		OutaccountDAO outaccountDAO = new OutaccountDAO(Outaccoutinfo.this);
		List<Tb_outaccount> listinfos = outaccountDAO.getScrollData(0, (int)outaccountDAO.getCount());
		strInfo = new String[listinfos.size()];
		int m = 0;
		for(Tb_outaccount tb_outaccount : listinfos){
			strInfo[m]=tb_outaccount.getId()+"|"+tb_outaccount.getType()+" "+String.valueOf(tb_outaccount.getMoney())+"Ԫ          "+tb_outaccount.getTime();
			m++;
		}
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfo);
		lvinfo.setAdapter(arrayAdapter);
		
	}
}
