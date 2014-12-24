package com.modou.data.resolver;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *@author 李长强  E-mail: xyylchq@163.com
 *@version 创建时间：2013-1-9下午05:41:13
 * 版本更新数据解析类
 */
public class CheckClientVersionRet extends BaseRet {

	private static NewClient newClient;
	private boolean needupdate;
	private String updateContent;
	
	public CheckClientVersionRet(JSONObject json) {
		super(json);
		
		needupdate = json.optBoolean("needupdate");
		updateContent = json.optString("updatecontent");
//		if(!json.isNull("newClient")) {
//			newClient = NewClient.parseNewClient(json.optJSONObject("newClient"));
//		}//TODO 暂时屏蔽
	}
	
	public NewClient getNewClient() {
		return newClient;
	}
	
	public static final class NewClient {
		private String versionName;
		private int apkBytes;
		private String desc;
		private String download;
		private String dltoken;
		
		static final NewClient parseNewClient(JSONObject json) {
			if (json == null) {
				return null;
			}
			NewClient newClient = new NewClient();
			try {
				newClient.versionName = json.getString("versionname");
				newClient.apkBytes = json.getInt("apkbytes");
				newClient.desc = json.getString("updatecontent");
				newClient.download = json.getString("download");
				newClient.dltoken = json.optString("dltoken");
			} catch (JSONException e) {
			}
			return newClient;
		}

		private NewClient() {
		}


		public int getApkBytes() {
			return apkBytes;
		}

		public String getDownloadToken() {
			return dltoken;
		}

		public String getVersionName() {
			return versionName;
		}

		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getDownload() {
			return download;
		}

		public void setDownload(String download) {
			this.download = download;
		}

		public String getDltoken() {
			return dltoken;
		}

		public void setDltoken(String dltoken) {
			this.dltoken = dltoken;
		}

		public void setApkBytes(int apkBytes) {
			this.apkBytes = apkBytes;
		}
	}
	
	public boolean isNeedUpdate() {
		return needupdate;
	}
	
	public String getUpdateContent() {
		return updateContent;
	}
}
