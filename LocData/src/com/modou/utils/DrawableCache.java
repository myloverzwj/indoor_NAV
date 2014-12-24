package com.modou.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

/**
 * 图片缓存类
 * @author changqiang.li
 *
 */
public class DrawableCache {
	
	private final String TAG = "DrawableCache";
	/** 是否允许磁盘缓存 **/
	public static boolean enableDiskCache = true; 
	static private DrawableCache cache;
	/** 用于Chche内容的存储 */
	private HashMap<String, MySoftRef> hashRefs;
	/** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
	private ReferenceQueue<Drawable> q;

	/**
	 * 继承SoftReference，使得每一个实例都具有可识别的标识。
	 */
	private class MySoftRef extends SoftReference<Drawable> {
		private String _key = "";

		public MySoftRef(Drawable bmp, ReferenceQueue<Drawable> q, String key) {
			super(bmp, q);
			_key = key;
		}
	}

	private DrawableCache() {
		hashRefs = new HashMap<String, MySoftRef>();
		q = new ReferenceQueue<Drawable>();
	}

	/**
	 * 取得缓存器实例
	 */
	public static DrawableCache getInstance() {
		if (cache == null) {
			cache = new DrawableCache();
		}
		return cache;
	}

	/**
	 * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
	 */
	public void addCacheDrawable(Drawable bmp, String key) {
		cleanCache();// 清除垃圾引用
		MySoftRef ref = new MySoftRef(bmp, q, key);
		hashRefs.put(key, ref);
	}

	public void addCacheImage(Drawable bmp, String key, String savePath) {
		if (bmp == null)
			return;
		String path = getSDcarPath();
		if (enableDiskCache && path != null) {
			try {
				String fileName = MD5.md5String(key);
				File file = new File(path + savePath);
				MLog.d(TAG, "file.exists=" + file.exists());
				if (!file.exists()) {
					file.mkdirs();
				}
				File picFile = new File(file.getAbsolutePath() + File.separator
						+ fileName);
				if (!picFile.exists())// 已经存在了就不保存了
				{
					FileOutputStream fos = new FileOutputStream(picFile);
					BitmapDrawable bd = (BitmapDrawable) bmp;
					Bitmap bm = bd.getBitmap();
					bm.compress(CompressFormat.PNG, 100, fos);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		addCacheDrawable(bmp, key);

	}

	/**
	 * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
	 */
	public Drawable getDrawable(String resId, int moduleId, String imgPath) {
		Drawable bmp = null;
		// 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
		if (hashRefs.containsKey(resId + moduleId)) { // 缓存中通过图片URL + 模块ID 来进行获取
			MySoftRef ref = (MySoftRef) hashRefs.get(resId);
			bmp = (Drawable) ref.get();
		} else {
			String path = getSDcarPath();
			if (enableDiskCache && path != null)// sdcard可用
			{
				try {
					File file = new File(path + imgPath + File.separator,
							MD5.md5String(resId));
					if (file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						bmp = Drawable.createFromStream(new FlushedInputStream(
								fis), "src");
						addCacheDrawable(bmp, resId);// 放到软引用中
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return bmp;
	}
	
	/**
	 * 只从SD卡中获取
	 * 避免图片缓存引用冲突
	 */
	public Drawable getDrawableBySD(String resId, String imgPath) {
		Drawable bmp = null;
			String path = getSDcarPath();
			if (enableDiskCache && path != null)// sdcard可用
			{
				try {
					File file = new File(path + imgPath + File.separator,
							MD5.md5String(resId));
					if (file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						bmp = Drawable.createFromStream(new FlushedInputStream(
								fis), "src");
						addCacheDrawable(bmp, resId);// 放到软引用中
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		return bmp;
	}

	private void cleanCache() {
		MySoftRef ref = null;
		while ((ref = (MySoftRef) q.poll()) != null) {
			hashRefs.remove(ref._key);
		}
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break;
						// we reached EOF
					} else {
						bytesSkipped = 1;
						// we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	/**
	 * 清除Cache内的全部内容
	 */
	public void clearCache() {
		cleanCache();
		hashRefs.clear();
		System.gc();
		System.runFinalization();
	}
	
	public static String getSDcarPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}
		return null;
	}
	
	/**
	 * 获得保存全路径，如果没有sd卡，选择内存
	 * @param ctx
	 * @return
	 */
	public static File getSavePathFile(Context ctx,String file){
		String path = getSDcarPath();
		if(path == null)path = ctx.getFilesDir().getAbsolutePath();
			
		if(file != null)
			return new File(path, file);
		else
			return new File(path);
	}
	/** 删除指定文件 */
	public static boolean deleteFile(File file){
		if(file.exists()){
			return file.delete();
		}
		return false;
	}
}