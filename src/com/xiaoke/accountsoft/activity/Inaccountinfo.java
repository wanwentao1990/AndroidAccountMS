package com.xiaoke.accountsoft.activity;

import java.util.List;

import com.xiaoke.accountsoft.dao.InaccountDAO;
import com.xiaoke.accountsoft.model.Tb_inaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Inaccountinfo extends Activity {

	public static final String FLAG = "id";
	ListView lvinfo;
	String strType = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inaccountinfo);
		
		lvinfo = (ListView)findViewById(R.id.lvinaccountinfo);
		showInfo();
		lvinfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String strInfo = String.valueOf( ((TextView)view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));
				Intent intent = new Intent(Inaccountinfo.this,InfoManage.class);
				intent.putExtra(FLAG, new String[]{strid,strType});
				startActivity(intent);
			}
		});
	}
	
	public void showInfo() {
		String[] strInfo = null;
		ArrayAdapter<String> arrayAdapter = null;
		strType = "btnininfo";
		InaccountDAO inaccountDAO = new InaccountDAO(Inaccountinfo.this);
		List<Tb_inaccount> listinfos = inaccountDAO.getScrollData(0, (int)inaccountDAO.getCount());
		strInfo = new String[listinfos.size()];
		int m = 0;
		for(Tb_inaccount tb_inaccount : listinfos){
			strInfo[m]=tb_inaccount.getId()+"|"+tb_inaccount.getType()+" "+String.valueOf(tb_inaccount.getMoney())+"Ԫ          "+tb_inaccount.getTime();
			m++;
		}
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfo);
		lvinfo.setAdapter(arrayAdapter);
		
	}
}
