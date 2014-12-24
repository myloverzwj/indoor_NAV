package com.modou.utils;

import com.modou.loc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast提示信息类
 */
public class ToastUtils {
	 private static Toast mToast;
	    private static Handler mHandler = new Handler();
	    private static Runnable r = new Runnable() {
	        public void run() {
	            mToast.cancel();
	        }
	    };

	public static void show(Context ctx, String msg) {
		if (ctx != null)
			getToast(ctx, msg).show();
	}

	public static void show(Context ctx, int resId) {
		if (ctx != null)
			getToast(ctx, resId).show();
	}

	public static void show(Context ctx, int resId, int resIcon) {
		if (ctx != null)
			getToast(ctx, resId, resIcon).show();
	}

	public static void show(Context ctx, String msg, int resIcon) {
		if (ctx != null)
			getToast(ctx, msg, resIcon).show();
	}

	@SuppressLint("ShowToast")
	private static Toast getToast(Context ctx, int resId) {
		Toast toast = Toast.makeText(ctx, resId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		return toast;
	}

	@SuppressLint("ShowToast")
	private static Toast getToast(Context ctx, String text) {
		Toast toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		return toast;
	}

	private static Toast getToast(Context ctx, int resId, int resIcon) {
		Toast toast = new Toast(ctx);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		TextView tv = new TextView(ctx);
		tv.setText(resId);
		tv.setCompoundDrawablesWithIntrinsicBounds(resIcon, 0, 0, 0);
		toast.setView(tv);
		return toast;
	}

	private static Toast getToast(Context ctx, String resId, int resIcon) {
		Toast toast = new Toast(ctx);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		TextView tv = new TextView(ctx);
		tv.setText(resId);
		tv.setCompoundDrawablesWithIntrinsicBounds(resIcon, 0, 0, 0);
		toast.setView(tv);
		return toast;
	}

	public static void showAutoToast(Context ctx, String resId, int resIcon) {
		mHandler.removeCallbacks(r);
		if(mToast==null){
			mToast = new Toast(ctx);			
		}		
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setDuration(Toast.LENGTH_SHORT);
		TextView tv = new TextView(ctx);
		tv.setBackgroundColor(Color.argb(155, 0, 0, 0)); // 背景透明度
		tv.setCompoundDrawablePadding(DensityUtil.dip2px(ctx, 5));
		tv.setTextColor(ctx.getResources().getColor(R.color.white));
		tv.setText(resId);
		tv.setCompoundDrawablesWithIntrinsicBounds(0, resIcon, 0, 0);
		mToast.setView(tv);
		tv.setPadding(DensityUtil.dip2px(ctx, 50), DensityUtil.dip2px(ctx, 40),
				DensityUtil.dip2px(ctx, 50), DensityUtil.dip2px(ctx, 25));
		 mHandler.postDelayed(r, 3000);
		mToast.show();
	}

	

}
