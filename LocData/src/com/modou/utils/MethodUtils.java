package com.modou.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.modou.loc.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.widget.Toast;

public class MethodUtils {

	/**
	 * 日期转换
	 * @param millisecond	日期毫秒数   
	 * @return
	 */
	public static String parseDate(long millisecond) {
		Date date = new Date(millisecond);
		DateFormat res = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return res.format(date);
	}
	
	/**
	 * 根据获取的Wifi信号强度划分等级
	 * @param value	wifi强度
	 * @return
	 */
	public static int parseWifiLevel(int value) {
		if (value <= 0 && value >= -50) {
			return 0;
		} else if (value < -50 && value >= -70) {
			return 1;
		} else if (value < -70 && value >= -80) {
			return 2;
		} else {
			return 3;
		}
	}
	
	/**获取版本号*/
	public static String getVerionName(Context ctx) {
		try {
			PackageInfo pkgInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			return pkgInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分享功能
	 * @param ctx	活动上下文
	 * @param msg	分享信息
	 */
	public static void share(Context ctx, String msg) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, R.string.setting_share_way);
		intent.putExtra(Intent.EXTRA_TEXT, msg);
		ctx.startActivity(intent);
	}
	
	public static void showToast(Context ctx, String res) {
		Toast.makeText(ctx, res, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(Context ctx, int resId) {
		Toast.makeText(ctx, resId, Toast.LENGTH_SHORT).show();
	}
	
	// 获取指定视图中心点在屏幕中的坐标
	public static int[] getViewCenterOnScreen(View v, float width) {
		int[] location = new int[2];
		v.getLocationInWindow(location);
		location[0] = location[0] + (int) (v.getWidth() - width) / 2;
		location[1] = location[1] + (int) (v.getWidth() - width) / 2;
		return location;
	}
	
	public static float[] convertColors(int color) {
		int blue=color&255;
		int green=color>>8&255;
		int red=color>>16&255;
		
		float r = red*1.0f/255;
		float g = green*1.0f/255;
		float b = blue*1.0f/255;
		//RGBA
		float[] colors = new float[] { 
				r, g, b, 0f,
				r, g, b, 0f,
				r, g, b, 0f,
				r, g, b, 0f };
		return colors;
	}
	
	private static long preBackTime; // 记录上次按back键的时间
	public static boolean canExit(Context ctx) {
		if (System.currentTimeMillis() - preBackTime < 1500) {
			preBackTime = 0;
			return true;
		}
		preBackTime = System.currentTimeMillis();
		ToastUtils.show(ctx, R.string.exit_app_msg);
		return false;
	}
	
}
