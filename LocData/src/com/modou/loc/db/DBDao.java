package com.modou.loc.db;

import java.util.ArrayList;
import java.util.List;

import com.modou.loc.entity.Building;
import com.modou.utils.MLog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *	数据库业务操作类
 */
public class DBDao {

	private DBHelper dbHelper;
	
	private static DBDao instance;
	
	private DBDao(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	/**
	 * 使用前必须调用
	 * @param ctx
	 */
	public static void init(Context ctx) {
		if (instance == null) {
			instance = new DBDao(ctx);
		}
	}
	
	public static DBDao getInstance() {
		return instance;
	}
	
	public static void destory() {
		if (instance != null) {
			instance.closeDB();
		}
	}
	
	/**
	 * 向数据库中添加一条商场记录
	 * @param shopId	商场ID
	 * @param shopName	商场名称
	 * @param shopImUrl 商场图片URL
	 */
	public void addShop(String shopId, String shopName, String shopImUrl) {
		try {
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			String sql = "insert into " + DBUtil.SHOP_TABLE_NAME + "(" + DBUtil.SHOP_ID + " ," + 
			DBUtil.SHOP_NAME + " ," + DBUtil.SHOP_IMG_URL + ") values (?,?,?)";
			Object[] args = {shopId, shopName, shopImUrl};
			database.execSQL(sql, args);
			database.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看数据库中某商场信息是否已经下载
	 * @param shopId	商场ID
	 * @return	true: 已下载		false: 未下载
	 */
	public boolean isHasDatas(String shopId) {
		MLog.d("mylog", "查看数据库中某商场信息是否已经下载");
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		String sql = "select count(*) from " + DBUtil.SHOP_TABLE_NAME + " where " + DBUtil.SHOP_ID + "=?";
		Cursor cursor = database.rawQuery(sql, new String[]{ shopId });
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		database.close();
		return count > 0;
	}
	
	/**
	 * 获取本地商场信息列表
	 * @return	商场信息集合
	 */
	public ArrayList<Building> getShops() {
		MLog.d("mylog", "获取商场列表");
		ArrayList<Building> arrs = new ArrayList<Building>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
		database = dbHelper.getWritableDatabase();
		String sql = "select " + DBUtil.SHOP_ID + " ," + DBUtil.SHOP_NAME + " ,"  + 
		DBUtil.SHOP_IMG_URL + " from " + DBUtil.SHOP_TABLE_NAME;
		cursor = database.rawQuery(sql, null);
		Building shop = null;
		while(cursor.moveToNext()) {
			shop = new Building();
			shop.setId(cursor.getString(0));
			shop.setName(cursor.getString(1));
			shop.setIconUrl(cursor.getString(2));
			arrs.add(shop);
		}
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
			if (database != null) {
				database.close();
				database = null;
			}
		}
		return arrs;
	}
	
	/**
	 * 删除某条商场信息
	 * @param shopId	商场ID
	 */
	public void delShopRecord(String shopId) {
		MLog.d("mylog", "删除某条商场信息");
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		database.delete(DBUtil.SHOP_TABLE_NAME, DBUtil.SHOP_ID + "=?", new String[]{ shopId });
		database.close();
	}
	
	/**关闭数据库*/
	public void closeDB() {
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
