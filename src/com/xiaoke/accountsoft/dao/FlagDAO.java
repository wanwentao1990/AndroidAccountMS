package com.xiaoke.accountsoft.dao;

import java.util.ArrayList;
import java.util.List;

import com.xiaoke.accountsoft.model.Tb_flag;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FlagDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	
	public FlagDAO(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBOpenHelper(context);
	}
	
	public void add(Tb_flag tb_flag) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_flag (_id,flag) values (?,?)", new Object[]{
				tb_flag.getId(),tb_flag.getFlag()
		});
	}
	
	public void delete(Integer... ids) {
		db = helper.getWritableDatabase();
		if (ids.length > 0) {
			StringBuilder sbBuilder = new StringBuilder();
			for (int i = 0; i < ids.length; i++) {
				sbBuilder.append('?').append(',');
			}
			sbBuilder.deleteCharAt(sbBuilder.length() - 1);
			db.execSQL("delete from tb_flag where _id in (" + sbBuilder + ")", (Object[])ids);
		}
	}
	
	public void update(Tb_flag tb_flag) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_flag set flag = ? where _id = ?", new Object[]{tb_flag.getFlag(),tb_flag.getId()});
	}
	
	public Tb_flag find(int id) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,flag from tb_flag where _id = ?", new String[]{
				String.valueOf(id)
		});
		if (cursor.moveToNext()) {
			Tb_flag tmpFlag = new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("flag")));
			return tmpFlag;
		}
		return null;
	}
	
	public List<Tb_flag> getScrollData(int start, int count) {
		db = helper.getWritableDatabase();
		List<Tb_flag> tb_flags = new ArrayList<Tb_flag>();
		Cursor cursor = db.rawQuery("select * from tb_flag limit ?,? ", new String[]{
				String.valueOf(start),String.valueOf(count)
		});
		while (cursor.moveToNext()) {
			Tb_flag tmFlag = new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("flag")));
			tb_flags.add(tmFlag);
		}
		return tb_flags;
	}
	
	public long getCount() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}
	
	public int getMaxId() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);
		while (cursor.moveToLast()) {
			return cursor.getInt(0);
		}
		return 0 ;
	}
}
