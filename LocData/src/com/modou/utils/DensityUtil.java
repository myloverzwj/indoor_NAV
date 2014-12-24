package com.modou.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 单位之间的转换类
 * 
 * @Description: 单位之间的转换类
 * @File: DimensionUtility.java
 */
public class DensityUtil {

	public static int width, height;

	/**
	 * 根据手机的分辨率从 dip的单位转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素)的单位转成为 dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	* 获取当前分辨率下指定单位对应的像素大小（根据设备信息）
	* px,dip,sp -> px
	* @param unit TypedValue.COMPLEX_UNIT_*
	* @param size
	* @return 
	*/ 
	public static float getRawSize(Context context, int unit, float size) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(unit, size, metrics);
	}
}
