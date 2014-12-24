package com.modou.loc.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.modou.api.ClientApiService;
import com.modou.api.DataInvoker;
import com.modou.data.resolver.MapFileRet;
import com.modou.loc.db.DBMgr;
import com.modou.loc.entity.Building;
import com.modou.loc.receiver.DlMapReceiver;
import com.modou.utils.MLog;
import com.modou.utils.StorageUtil;
import com.modou.utils.StringUtils;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-13 下午4:35:12
 * 类说明:
 * 地图文件下载任务类
 * 请求接口时传递"商城ID",server端返回下载地址，客户端进行下载操作。
 */
public class DlMapFileTask extends AsyncTask<String, Void, Boolean> {

	private Context mContext;
	private Building building;
	
	public DlMapFileTask(Context ctx, Building building) {
		this.mContext = ctx;
		this.building = building;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		/*// TODO 请求server端接口,获取下载地址
		ClientApiService apiService = ClientApiService.getInstance(mContext);
		if (params == null) {
			MLog.e("下载地图数据时，传递的参数为空 params=" + params);
			return null;
		}
		String mapId = building.getId();
		DataInvoker<MapFileRet> invoker = apiService.getDlMapFile(mapId);
		invoker.invoke();
		
		MapFileRet ret = invoker.getRet();
		String dlMapUrl = null;
		if (ret != null && ret.isSucces()) {
			dlMapUrl = ret.getDlUrl();
			dlMapFile(dlMapUrl);
		}
		
		return dlMapUrl;*/
		
		String dlMapUrl = building.getDlMapUrl();
		String dlMapId = building.getId();
		boolean result = dlMapFile(dlMapId, dlMapUrl);
		
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			DlMapReceiver.successDlMapAction(mContext, building.getId());
			// 下载成功后，向数据库中插入一条记录
			DBMgr.getInstance().addShop(building.getId(), building.getName(), building.getIconUrl());
		} else {
			DlMapReceiver.failDlMapAction(mContext, building.getId());
		}
		super.onPostExecute(result);
	}
	
	/**
	 * 下载地图文件
	 * @param fileName
	 * @param dlMapUrl
	 */
	private boolean dlMapFile(String fileName, String dlMapUrl) {
		InputStream is = null;
		HttpURLConnection connection = null;
		RandomAccessFile randomAccessFile = null;
		try {
			URL sourceUrl = new URL(dlMapUrl);
			connection = (HttpURLConnection) sourceUrl.openConnection();
			connection.setConnectTimeout(50000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept","application/vnd.android.package-archive,image/gif,image/jpeg,image/pjpeg,application/x-shockwave-flash,application/xaml+xml,application/vnd.ms-xpsdocument,application/x-ms-xbap,application/x-ms-application,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/msword,*/*");                                             
			connection.setRequestProperty("Accept-Language","zh-CN");
			connection.setRequestProperty("Referer",sourceUrl.toString());
			connection.setRequestProperty("Charset","UTF-8");
			connection.setRequestProperty( "User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");                                             
			connection.setRequestProperty("Connection","Keep-Alive");
			long totalSize = connection.getContentLength();
			
			String saveFile = StorageUtil.getDlMapFilePath(mContext) + fileName + ".xml";
			randomAccessFile = new RandomAccessFile(saveFile, "rwd");
			is = connection.getInputStream();
			int read = 0;
			byte[] bytes = new byte[4096];
			while (((read = is.read(bytes)) != -1) && !isCancelled()) {
				randomAccessFile.write(bytes, 0, read);
			}
			
			// 检测是否下载成功
			if (randomAccessFile.length() == totalSize) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (randomAccessFile != null)
					randomAccessFile.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
}
