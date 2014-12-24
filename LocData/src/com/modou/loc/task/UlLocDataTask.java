package com.modou.loc.task;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.modou.utils.HttpUtil;
import com.modou.utils.StorageUtil;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-13 下午5:21:28
 * 类说明:
 * 上传用户行走轨迹任务类
 * 
 * 客户端启动或者退出时上传之前定位的数据文件（有可能有多个文件），
 * 当文件上传成功后，将该文件删除。
 */
public class UlLocDataTask extends AsyncTask<String, String, String> {

	private Context mContext;
	private String urlServer;
	
	public UlLocDataTask(Context ctx) {
		this.mContext = ctx;
		
		urlServer = "URL上传地址";//TODO URL上传地址
	}
	
	@Override
	protected String doInBackground(String... params) {
		String storagePath = StorageUtil.getLocDataFilesPath(mContext);
		File file = new File(storagePath);
		File files[] = file.listFiles();
		if (files != null) {
			try {
			for (File f : files) {
				if (f != null && f.isFile()) {
					HttpUtil.post(f, urlServer);
				}
			}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}
