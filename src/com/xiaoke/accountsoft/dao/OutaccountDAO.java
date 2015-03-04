package com.xiaoke.accountsoft.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoke.accountsoft.model.Tb_outaccount;


public class OutaccountDAO {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	
	public OutaccountDAO(Context context){
		helper = new DBOpenHelper(context);
	}
	
	public void add(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into tb_outaccount (_id,money,time,type,address,mark) values (?,?,?,?,?,?)",new Object[]{
				tb_outaccount.getId(),tb_outaccount.getMoney(),tb_outaccount.getTime(),tb_outaccount.getType(),tb_outaccount.getAddress(),tb_outaccount.getMark()
		});
	}
	
	public void update(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();
		db.execSQL("update tb_outaccount set money=?,time=?,type=?,address=?,mark=? where _id=?",new Object[]{
				tb_outaccount.getMoney(),tb_outaccount.getTime(),tb_outaccount.getType(),tb_outaccount.getAddress(),tb_outaccount.getMark(),tb_outaccount.getId()
		});
	}
	
	public Tb_outaccount find(int id) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select _id,money,time,type,address,mark from tb_outaccount where _id=?", new String[]{
				String.valueOf(id)
		});
		if (cursor.moveToNext()) {
			Tb_outaccount tmpOutaccount = new Tb_outaccount(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getDouble(cursor.getColumnIndex("money")), cursor.getString(cursor.getColumnIndex("time")), cursor.getString(cursor.getColumnIndex("type")), cursor.getString(cursor.getColumnIndex("address")), cursor.getString(cursor.getColumnIndex("mark")));
			return tmpOutaccount;
		}
		return null;
	}
	
	public void delete(Integer... ids) {
		if (ids.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ids.length; i++) {
				sb.append('?').append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			db = helper.getWritableDatabase();
			db.execSQL("delete from tb_outaccount where _id in (" + sb + ")",(Object[])ids);
		}
	}
	
	public List<Tb_outaccount> getScrollData(int start, int count) {
		List<Tb_outaccount> tb_inaccount = new ArrayList<Tb_outaccount>();
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from tb_outaccount limit ?,?",new String[]{String.valueOf(start),String.valueOf(count)});
		while (cursor.moveToNext()) {
			Tb_outaccount tmpOutccount = new Tb_outaccount(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getDouble(cursor.getColumnIndex("money")), cursor.getString(cursor.getColumnIndex("time")), cursor.getString(cursor.getColumnIndex("type")), cursor.getString(cursor.getColumnIndex("address")), cursor.getString(cursor.getColumnIndex("mark")));
			tb_inaccount.add(tmpOutccount);
		}
		return tb_inaccount;
	}
	
	public long getCount() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}
	
	public int getMaxId() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount",null);
		while (cursor.moveToLast()) {
			return cursor.getInt(0);
		}
		return 0;
	}
}
