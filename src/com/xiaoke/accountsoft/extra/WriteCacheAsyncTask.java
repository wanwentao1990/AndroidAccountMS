package com.xiaoke.accountsoft.extra;

import com.xiaoke.accountsoft.until.ContextUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class WriteCacheAsyncTask extends AsyncTask<Integer	, Integer, String> {

	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		wirteCacheData();
		return null;
	}
	//������UI�̵߳ĺ������˴�����ˢ�½���	
    @Override  
    protected void onPostExecute(String result) {  
    	Log.e("wwtlog", "�첽��������������");
    } 
	
	public void wirteCacheData() {
		Context  c= ContextUtil.getInstance();
		SharedPreferences mySharedPreferences = c.getSharedPreferences("loginstatus", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("loginstatus", "false");
		editor.commit();
		Log.e("wwtlog", "�˳�����д��ɹ���");
	}

}
