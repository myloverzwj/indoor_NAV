package com.modou.api;
//package com.modou.shop.api;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.ref.SoftReference;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.modou.shop.impl.ImageCallback;
//
//import android.graphics.drawable.Drawable;
//import android.os.Handler;
//import android.os.Message;
//
///**
// * 异步图片加载类
// * @author changqiang.li
// * 首次加载图片会根据指定的URL向服务器获取数据，
// * 然后把从服务器获取到得数据加载到内存中，
// * 当再次加载显示该图片的时候，会先向内存中查找对应数据，
// * 如果在内存中找到，则说明这张图片不是首次加载，直接返回该数据，
// * 否则继续向服务器请求数据。
// */
//public class AsyncImageLoader {
//
//	private Map<String, SoftReference<Drawable>> imgCache;
//	
//	public AsyncImageLoader() {
//		imgCache = new HashMap<String, SoftReference<Drawable>>();
//	}
//	
//	/**
//	 * 加载图片
//	 * @param imgUrl		图片下载路径
//	 * @param imgCallBack	回调接口
//	 * @return Drawable		图片对象			
//	 */
//	public Drawable loadDrawable(final String imgUrl, final ImageCallback imgCallBack) {
//		// 判断图片是否在缓存中
//		if (imgCache.containsKey(imgUrl)) {
//			SoftReference<Drawable> softRef = imgCache.get(imgUrl);
//			Drawable drawable = softRef.get();
//			if (drawable != null)
//				return drawable;
//		}
//		
//		// 创建一个回调手柄
//		final Handler handler = new Handler() {
//			public void handleMessage(Message msg) {
//				imgCallBack.imageLoaded((Drawable)msg.obj, imgUrl);
//			}
//		};
//		
//		// 创建一个下载图片任务，从服务器获取图片
//		DownloadImgTask downloadImgTask = new DownloadImgTask(imgUrl, handler);
//		new Thread(downloadImgTask).start();
//		
//		return null;
//	}
//	
//	/**图片下载类*/
//	class DownloadImgTask implements Runnable {
//		
//		Handler callBackHandler;
//		String imgUrl;
//		
//		public DownloadImgTask (String imgUrl, Handler handler) {
//			callBackHandler = handler;
//			this.imgUrl = imgUrl;
//		}
//		
//		public void run() {
//			Drawable drawable = loadImgeFromUrl(imgUrl);
//			imgCache.put(imgUrl, new SoftReference<Drawable>(drawable));
//			Message msg = callBackHandler.obtainMessage(0, drawable);
//			callBackHandler.sendMessage(msg);
//		}
//		
//		private Drawable loadImgeFromUrl(String imgUrl) {
//			URL url;
//			InputStream inStream = null;
//			try {
//				url = new URL(imgUrl);
//				inStream = (InputStream) url.getContent();
//			} catch (MalformedURLException e1) {
//				e1.printStackTrace();
//			} catch(IOException e) {
//				e.printStackTrace();
//			}
//			
//			Drawable drawable = Drawable.createFromStream(inStream, "src");
//			return drawable;
//		}
//	}
//	
//}
