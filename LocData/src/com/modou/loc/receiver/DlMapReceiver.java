package com.modou.loc.receiver;

import com.modou.utils.Const;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-10 下午3:25:19
 * 类说明:
 * 下载地图广播
 * 开始下载、下载失败或下载成功时发送广播
 */
public abstract class DlMapReceiver extends BroadcastReceiver {

	/**开始下载广播*/
	public final static String MAP_START_DL = "com.modou.loc.receiver.MAP_DL_START";
	
	/**下载失败广播*/
	public final static String MAP_FAIL_DL = "com.modou.loc.receiver.MAP_DL_FAIL";
	
	/**下载成功广播*/
	public final static String MAP_SUCESS_DL = "com.modou.loc.receiver.MAP_DL_SUCCESS";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (context == null || intent == null)
			return;
		
		String action = intent.getAction();
		if (action == null)
			return;
		
		if (MAP_START_DL.equals(action)) {
			String mapId = intent.getStringExtra(Const.MAP_ID);
			startDlMap(mapId);
		} else if (MAP_FAIL_DL.equals(action)) {
			String mapId = intent.getStringExtra(Const.MAP_ID);
			failDlMap(mapId);
		} else if (MAP_SUCESS_DL.equals(action)) {
			String mapId = intent.getStringExtra(Const.MAP_ID);
			successDlMap(mapId);
		}
	}
	
	/**
	 * 开始下载地图
	 * @param mapId	地图ID
	 */
	protected abstract void startDlMap(String mapId);

	/**
	 * 地图下载失败
	 * @param mapId
	 */
	protected abstract void failDlMap(String mapId);
	
	/**
	 * 地图下载成功
	 * @param mapId
	 */
	protected abstract void successDlMap(String mapId);
	
	/**
	 * 开始下载地图广播
	 * @param ctx
	 * @param mapId
	 */
	public static void startDlMapAction(Context ctx, String mapId) {
		Intent intent = new Intent();
		intent.setAction(MAP_START_DL);
		intent.putExtra(Const.MAP_ID, mapId);
		ctx.sendBroadcast(intent);
	}
	
	/**
	 * 地图下载失败广播
	 * @param ctx
	 * @param mapId
	 */
	public static void failDlMapAction(Context ctx, String mapId) {
		Intent intent = new Intent();
		intent.setAction(MAP_FAIL_DL);
		intent.putExtra(Const.MAP_ID, mapId);
		ctx.sendBroadcast(intent);
	}
	
	/**
	 * 地图下载成功广播
	 * @param ctx
	 * @param mapId
	 */
	public static void successDlMapAction(Context ctx, String mapId) {
		Intent intent = new Intent();
		intent.setAction(MAP_SUCESS_DL);
		intent.putExtra(Const.MAP_ID, mapId);
		ctx.sendBroadcast(intent);
	}
	
	public void register(Context ctx) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(MAP_START_DL);
		filter.addAction(MAP_FAIL_DL);
		filter.addAction(MAP_SUCESS_DL);
		ctx.registerReceiver(this, filter);
	}
}
