package com.modou.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

public class PhoneInfoUtils {

	private static int screenWidth;
	private static int screenHeight;
	
	/**
	 * 解析手机屏幕信息,在可以横竖屏切换的手机需要在onConfigurationChanged的方法中调用
	 */
	public static void parseScreenInfo(Context context) {
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		/*WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		try {
			Class<Display> cls = Display.class;
			Method method = cls.getMethod("getRotation");
			Object retobj = method.invoke(display);
			int rotation = Integer.parseInt(retobj.toString());
			// 判断横屏还是屏
			if (Surface.ROTATION_0 == rotation
					|| Surface.ROTATION_180 == rotation) {
				screenWidth = display.getWidth();
				screenHeight = display.getHeight();
			} else {
				screenWidth = display.getHeight();
				screenHeight = display.getWidth();
			}
		} catch (Exception e) {
			if (display.getOrientation() == 1) {
				screenWidth = display.getHeight();
				screenHeight = display.getWidth();
			} else {
				screenWidth = display.getWidth();
				screenHeight = display.getHeight();
			}
		}*/
	}

	/**
	 * 获得屏幕宽度
	 */
	public static int getWidth() {

		if (screenWidth == 0) {
			Log.e("PhoneInfoUtils", "您需要调用 parseScreenInfo()后才能获得宽度");
		}
		return screenWidth;
	}

	/**
	 * 获得屏幕高度
	 */
	public static int getHeight() {
		if (screenHeight == 0)
			Log.e("PhoneInfoUtils", "您需要调用 parseScreenInfo()后才能获得高度");
		return screenHeight;
	}
	
}
