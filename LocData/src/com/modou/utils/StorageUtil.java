package com.modou.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-5-24 上午9:49:57
 * 类说明:
 * 存储相关操作类
 * 记录下载文件保存位置，检查是否有可用存储设备，
 *下载文件是否存在，是否有可用存储空间等等。
 */
public class StorageUtil {

	/**存储根目录*/
	public static final String SAVE_ROOT_PATH = "/indoor";
	/**缓存图片下载子目录*/
	public static final String CACHE_SAVE_PATH = "cache";
	/**图片保存路径*/
	public static final String IMG_SAVE_PATH = "imgs";
	/**用户定位行为轨迹文件保存路径*/
	public static final String LOC_DATA_SAVE_PATH = "loc_data_file_save_path";
	/**地图文件保存路径*/
	public static final String MAP_FILE_PATH = "map_file_path";
	
	
	/**检测是否有可用的SD卡存储设备*/
	public static boolean getStorageState() {
		final String dirState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(dirState))
			return true;
		return false;
	}
	
	/** 获取下载文件外部设备存储路径 */
	public static String getExternalStoragePath(Context context) {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
		+ File.separator + "Android" + File.separator + "data"
		+ File.separator + context.getPackageName() + File.separator
		+ "files" + File.separator;
	}
	
	/** 获取下载文件内部设备（内存）存储路径 */
	public static String getInternalStoragePath(Context context) {
		return context.getFilesDir().getAbsolutePath() + File.separator;
	}
	
	/**
	 * 创建文件
	 * @param fileName	文件名称
	 * @param context	活动上下文
	 * @return
	 */
	public static File createFile(String fileName, Context context) {
		String path = getInternalStoragePath(context) + fileName + ".txt";
		try {
			File file = new File(path);
			File dir = file.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
			if (!file.exists())
				file.createNewFile();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 创建文件
	 * @param fileName	文件名称
	 * @param context	活动上下文
	 * @return
	 */
	public static File createDlMapFile(String fileName, Context context) {
		String path = getInternalStoragePath(context) + fileName + ".xml";
		try {
			File file = new File(path);
			File dir = file.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
			if (!file.exists())
				file.createNewFile();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**应用是否是第一次启动*/
	public static boolean isFirstAppStart(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		boolean isFirstStart = sp.getBoolean(Const.IS_FIRST_START_APP, true);
		return isFirstStart;
	}
	
	/**
	 * 设置应用的启动状态
	 * @param ctx
	 * @param flag
	 */
	public static void setFirstAppStart(Context ctx, boolean flag) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(Const.IS_FIRST_START_APP, flag);
		editor.commit();
	}
	
	/**是否显示状态栏图标信息*/
	public static boolean isShowStatusBarInfo(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		boolean isShowStatusBar = sp.getBoolean(Const.IS_SHOW_STATUS_BAR_INFO, true);
		return isShowStatusBar;
	}
	
	/**
	 * 设置是否显示状态栏图标信息
	 * @param ctx
	 * @param flag
	 */
	public static void setStatusBarInfo(Context ctx, boolean flag) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(Const.IS_SHOW_STATUS_BAR_INFO, flag);
		editor.commit();
	}
	
	/**是否启动自动检查升级*/
	public static boolean isAutoUpdateVersion(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		boolean isFirstStart = sp.getBoolean(Const.IS_AUTO_CHECK_UPDATE_VERSION, true);
		return isFirstStart;
	}
	
	/**
	 * 设置是否启动自动检查升级
	 * @param ctx
	 * @param flag
	 */
	public static void setAutoUpdateVersion(Context ctx, boolean flag) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(Const.IS_AUTO_CHECK_UPDATE_VERSION, flag);
		editor.commit();
	}
	
	/**是否接收最新消息*/
	public static boolean isAcceptNews(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		boolean isAccept = sp.getBoolean(Const.IS_ACCEPT_NEWS, true);
		return isAccept;
	}
	
	/**
	 * 设置是否接收最新消息
	 * @param ctx
	 * @param flag
	 */
	public static void setAcceptNews(Context ctx, boolean flag) {
		SharedPreferences sp = ctx.getSharedPreferences(Const.SHARE_DATAS, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(Const.IS_ACCEPT_NEWS, flag);
		editor.commit();
	}
	
	/**获取图片的存储路径*/
	public static String getImgSavePath(Context context) {
		return Environment.getExternalStorageDirectory() + File.separator + SAVE_ROOT_PATH 
				+ File.separator + StorageUtil.IMG_SAVE_PATH + File.separator;
	}
	
	/**获取用户定位数据文件存储路径*/
	public static String getLocDataFilesPath(Context ctx) {
		return Environment.getExternalStorageDirectory() + File.separator + SAVE_ROOT_PATH 
				+ File.separator + StorageUtil.LOC_DATA_SAVE_PATH + File.separator;
	}
	
	/**获取地图下载文件存储路径*/
	public static String getDlMapFilePath(Context ctx) {
		return Environment.getExternalStorageDirectory() + File.separator + SAVE_ROOT_PATH 
				+ File.separator + StorageUtil.MAP_FILE_PATH + File.separator;
	}
	
}
