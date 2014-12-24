package com.modou.loc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final int curVersion = 1;
	
	public DBHelper(Context context) {
		super(context, DBUtil.DB_NAME, null, curVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建商品表
		db.execSQL("create table " + DBUtil.SHOP_TABLE_NAME + "(" + DBUtil.SHOP_AUTO_ID + 
				" integer PRIMARY KEY AUTOINCREMENT, " + DBUtil.SHOP_ID + 
				" char, " + DBUtil.SHOP_NAME + " char, " +  DBUtil.SHOP_IMG_URL + " char)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
