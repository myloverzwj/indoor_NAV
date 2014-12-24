package com.modou.utils;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

/**
 * 图片异步加载缓存类
 *@author changqiang.li
 */
public class LoadImageAsyncTask {

	private DrawableCache imageCache;
	private static LoadImageAsyncTask instance;
	private Context ctx;
	
	public static synchronized LoadImageAsyncTask getInstance(Context ctx){
		if(instance  == null){
			instance = new LoadImageAsyncTask(ctx);
		}
		return instance;
	}
	
	private LoadImageAsyncTask(Context ctx) { 
		imageCache = DrawableCache.getInstance();
		this.ctx = ctx;
	}

	public Drawable loadDrawable(final String imageUrl, final int moduleId, final ImageCallback imageCallback) {
		if(imageUrl == null)return null;
		// 缓存图片存储位置
		final String path = StorageUtil.SAVE_ROOT_PATH + File.separator + StorageUtil.CACHE_SAVE_PATH;
		Drawable dmp;
		if ((dmp = imageCache.getDrawable(imageUrl, moduleId, path)) != null) {
			return dmp;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (imageCallback != null) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				if (null != drawable) {
					imageCache.addCacheImage(drawable, imageUrl, path);
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}
	
	public Drawable loadDrawableOtherPath(final String imageUrl, final ImageCallback imageCallback) {
		if(imageUrl == null)return null;
		// 缓存图片存储位置
		final String path = StorageUtil.SAVE_ROOT_PATH + File.separator + StorageUtil.CACHE_SAVE_PATH;
		Drawable dmp;
		if ((dmp = imageCache.getDrawableBySD(imageUrl, path)) != null) {
			return dmp;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (imageCallback != null) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				if (null != drawable) {
					imageCache.addCacheImage(drawable, imageUrl, path);
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}
	
	public Drawable loadDrawableNoCache(final String imageUrl, final ImageCallback imageCallback) {
		if(imageUrl == null)return null;
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (imageCallback != null) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				if (null != drawable) {
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}

	public Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			HttpURLConnection connection;
			// 网络检测
			ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni.getType() == ConnectivityManager.TYPE_MOBILE){
                String host = android.net.Proxy.getDefaultHost();
                int port = android.net.Proxy.getDefaultPort();
                if (host != null && port != -1) {
                    InetSocketAddress inetAddress = new InetSocketAddress(host, port);
                    java.net.Proxy.Type proxyType = java.net.Proxy.Type.valueOf(m.getProtocol().toUpperCase());
                    java.net.Proxy javaProxy = new java.net.Proxy(proxyType, inetAddress);
                    connection = (HttpURLConnection) m.openConnection(javaProxy);
	            } else {
	            	connection = (HttpURLConnection) m.openConnection();
	            }
            }else {
				connection = (HttpURLConnection) m.openConnection();
			}
			connection.setDoInput(true);
			i = connection.getInputStream();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Drawable d = null;
		if (i != null) {
			d = Drawable.createFromStream(new FlushedInputStream(i), "src");
		}
		return d;
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

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
}