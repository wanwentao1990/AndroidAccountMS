package com.xiaoke.accountsoft.dao;

import java.util.ArrayList;
import java.util.List;

import com.xiaoke.accountsoft.model.Tb_pwd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

public class PwdDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	
	public PwdDAO(Context context) {
		helper = new DBOpenHelper(context);
	}
	
	public void add(Tb_pwd tb_pwd) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_pwd (username,password) values (?,?)", new Object[]{
				tb_pwd.getUserName(),tb_pwd.getPassword()
		});
		Log.e("Add", "Success");
	}
	
	public void delete(String username) {
		db = helper.getWritableDatabase();
		db.execSQL("delete from tb_pwd where username=?", new Object[]{
				username
		});
	}
	
	public void update(Tb_pwd tb_pwd) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_pwd set password=? where username=?", new Object[]{
				tb_pwd.getPassword(),tb_pwd.getUserName()
		});
	}
	
	public Tb_pwd find(String username) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select username,password from tb_pwd where username=? ", new String[]{
				username
		});
		if (cursor.moveToNext()) {
			Tb_pwd tmPwd = new Tb_pwd(cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("password")));
			return tmPwd;
		}
		return null;
	}
	
	public List<Tb_pwd> getScrollData(int start, int count) {
		db = helper.getWritableDatabase();
		List<Tb_pwd> tb_pwds = new ArrayList<Tb_pwd>();
		Cursor cursor = db.rawQuery("select * from tb_pwd limit ?,?", new String[]{
				String.valueOf(start),String.valueOf(count)
		});
		while (cursor.moveToNext()) {
			Tb_pwd tmpPwd = new Tb_pwd(cursor.getString(cursor.getColumnIndex("username")), cursor.getString(cursor.getColumnIndex("password")));
			tb_pwds.add(tmpPwd);
		}
		return tb_pwds;
	}
	
	public long getCount(){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(username) from tb_pwd", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}
}
