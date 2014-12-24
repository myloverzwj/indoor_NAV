package com.modou.api;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.modou.data.resolver.BaseRet;
import com.modou.data.resolver.CheckClientVersionRet;
import com.modou.data.resolver.MapFileRet;
import com.modou.data.resolver.RegisterRet;
import com.modou.data.resolver.UserRet;
import com.modou.utils.MLog;
import com.modou.utils.StringUtils;

/**
 * 主要用于和服务器通讯的接口
 * @author changqiang.li
 *
 */
public class ClientApiService {

	private static ClientApiService instance;
	
	private Configuration config;
	private ApiUrl apiUrl;
	
	/**商店ID*/
	private String storeId;
	/**设备ID(IMEI)*/
	private String deviceId;
	/**设备型号*/
	private String deviceModel;
	/**设备IMSI*/
	private String imsi;
	/**客户端版本名称*/
	private String clientVersionName;
	/**客户端版本号*/
	private String clientVersionCode;
	/**客户端系统发行版本号*/
	private String systemVer;
	/**屏幕宽度*/
	private String wpx;
	/**屏幕高度*/
	private String hpx;
	
	private ClientApiService(Context context) {
		this.config = ConfigurationFactory.getInstance();
		apiUrl = new ApiUrl(config);
		
		deviceModel = android.os.Build.MODEL;
		TelephonyManager tpyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tpyMgr.getDeviceId() == null ? "0" : tpyMgr.getDeviceId();
		imsi = tpyMgr.getSubscriberId();
		systemVer = android.os.Build.VERSION.RELEASE;
		MLog.d("mylog", "systemVer====" + systemVer);
		try {
			PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			storeId = pkgInfo.packageName;
			storeId = storeId.substring(25, storeId.length());
//			storeId = config.getShopId();
			clientVersionCode = String.valueOf(pkgInfo.versionCode);
			clientVersionName = pkgInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		try {
			Class<Display> cls = Display.class;
			Method method = cls.getMethod("getRotation");
			Object retobj = method.invoke(display);
			int rotation = Integer.parseInt(retobj.toString());
			if (Surface.ROTATION_0 == rotation
					|| Surface.ROTATION_180 == rotation) {
				wpx = "" + display.getWidth();
				hpx = "" + display.getHeight();
			} else {
				wpx = "" + display.getHeight();
				hpx = "" + display.getWidth();
			}
		} catch (Exception e) {
			if (display.getOrientation() == 1) {
				wpx = "" + display.getHeight();
				hpx = "" + display.getWidth();
			} else {
				wpx = "" + display.getWidth();
				hpx = "" + display.getHeight();
			}
		}
	}
	
	/**封装向服务器请求数据的必要参数*/
	public List<NameValuePair> createRequestParams(String[] paramNames, String[] paramValues,
			String userId, String pwd) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 这里得参数是添加公用的参数
		nvps.add(new BasicNameValuePair("token", getToken()));
		nvps.add(new BasicNameValuePair("idcode", deviceId));
		
		if (paramNames != null && paramValues != null) {
			int count = paramNames.length;
			for (int i=0; i<count; i++) {
				nvps.add(new BasicNameValuePair(paramNames[i], paramValues[i]));
			}
		}
		return nvps;
	}
	
	/**获取版本更新信息*/
	public DataInvoker<CheckClientVersionRet> checkClientVersion() {
		String url = apiUrl.getCheckNewClientUrl();
		DataParseHandler<CheckClientVersionRet> handler = DataParseHandler.CHECK_CLIENT_VERSIN_HANDLER;
		String[] paramNames = new String[] { "versionnum" };
		String[] paramValues = new String[] { clientVersionCode };
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		return new DataInvoker<CheckClientVersionRet>(config, handler, url, nvps);
	}
	
	/**下载更新客户端
	 * @throws UnsupportedEncodingException */
	public HttpRequestBase downloadApk() throws UnsupportedEncodingException {
		String url = apiUrl.getDownloadApkUrl();
		String[] paramNames = new String[] {"serviceid", "clienttype"};
		String[] paramValues = new String[] {"download", "android"};
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return post;
	}
	
	/**获取用户商店相关配置信息*/
//	public DataInvoker<ShopConfigRet> getShopConfigRetData() {
//		String url = apiUrl.getShopConfigUrl();
//		DataParseHandler<ShopConfigRet> handler = DataParseHandler.SHOP_CONFIG_HANDLER;
//		String[] paramNames = new String[] {"aver", "cver"};
//		String curVer = getCurVer();
//		String[] paramValues = new String[] {systemVer, curVer};
//		MLog.d("mylog", "系统版本号:" + systemVer + " ,curVer:" + curVer);
//		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
//		return new DataInvoker<ShopConfigRet>(config, handler, url, nvps);
//	}
	
	/**注册用户*/
	public DataInvoker<RegisterRet> registerUser(String registerName, String password, String phoneNumber, String email) {
		String url = apiUrl.getRegisterUrl();
		DataParseHandler<RegisterRet> handler = DataParseHandler.REGISTER_HANDLER;
		String[] paramNames = new String[] {"loginname", "password", "mobile", "email"}; 
		String[] paramValues = new String[] {registerName, password, phoneNumber, email};
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		return new DataInvoker<RegisterRet>(config, handler, url, nvps);
	}
	
	/**用户登录*/
	public DataInvoker<UserRet> userLogin(String loginName, String password) {
		String url = apiUrl.getLoginUrl();
		DataParseHandler<UserRet> handler = DataParseHandler.LOGIN_HANDLER;
		String[] paramNames = new String[] {"loginname", "password"};
		String[] paramValues = new String[] {loginName, password};
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		return new DataInvoker<UserRet>(config, handler, url, nvps);
	}
	
//	/**修改密码*/
//	public DataInvoker<UpdatePwdRet> updatePwd(String loginName, String oldPwd, String newPwd) {
//		String url = apiUrl.getUpdatePwdUrl();
//		DataParseHandler<UpdatePwdRet> handler = DataParseHandler.UPDATE_PASSWORD_HANDLER;
//		String[] paramNames = new String[] {"loginname", "oldpassword", "newpassword"};
//		String[] paramValues = new String[] {loginName, oldPwd, newPwd};
//		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
//		return new DataInvoker<UpdatePwdRet>(config, handler, url, nvps);
//	}
	
	/**意见反馈*/
	public DataInvoker<BaseRet> addSuggestion(String loginName, String content) {
		String url = apiUrl.getSuggestionUrl();
		DataParseHandler<BaseRet> handler = DataParseHandler.BASE_PARSE_HANDLER;
		String[] paramNames = new String[] {"loginname", "content"};
		String[] paramValues = new String[] {loginName, content};
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		return new DataInvoker<BaseRet>(config, handler, url, nvps);
	}
	
	/**获取loading页图片*/
//	public DataInvoker<LoadingImgRet> getLoadingImg() {
//		String url = apiUrl.getLoadingImgUrl();
//		DataParseHandler<LoadingImgRet> handler = DataParseHandler.LOADING_IMG_PARSE_HANDLER;
//		List<NameValuePair> nvps = createRequestParams(null, null, null, null);
//		return new DataInvoker<LoadingImgRet>(config, handler, url, nvps);
//	}
	
//	/**客户端支付成功，向server端发送支付成功信息*/
//	public DataInvoker<BaseRet> sendPaySuccessRecord(String orderId, 
//			String orderNumber, String payStatus, float totalfee, String buyerId, String subject) {
//		String url = apiUrl.getSendPaySuccessURL();
//		DataParseHandler<BaseRet> handler = DataParseHandler.BASE_PARSE_HANDLER;
//		String[] paramNames = new String[] {"orderid", "ordernumber", "paystatus", "totalfee", "buyerID", "subject"};
//		String[] paramValues = new String[] {orderId, orderNumber, payStatus, Float.toString(totalfee), buyerId, subject};
//		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
//		return new DataInvoker<BaseRet>(config, handler, url, nvps);
//	}
	
	/**
	 * 获取下载地图URL回调
	 * @param mapId	地图ID
	 */
	public DataInvoker<MapFileRet> getDlMapFile(String mapId) {
		String url = apiUrl.getDlMapURL();
		DataParseHandler<MapFileRet> handler = DataParseHandler.DL_MAP_URL_PARSE_HANDLER;
		String[] paramNames = new String[] { "mapId" };
		String[] paramValues = new String[] { mapId };
		List<NameValuePair> nvps = createRequestParams(paramNames, paramValues, null, null);
		return new DataInvoker<MapFileRet>(config, handler, url, nvps);
	}
	
	/**
	 * 格式:  商店id|时间|私钥|(0手机/1浏览器)|设备型号|屏幕宽度|屏幕高度
	 * @return
	 */
	private String getToken() {
		StringBuffer sb = new StringBuffer();
		sb.append(storeId);
		sb.append("|");
//		sb.append(System.currentTimeMillis());
//		sb.append("|");
		sb.append(config.getSignCode());
		sb.append("|");
		sb.append(config.getPrivatekey());
		sb.append("|");
		sb.append(0);
//		sb.append("|");
//		sb.append(deviceModel);
//		sb.append("|");
//		sb.append(wpx);
//		sb.append("|");
//		sb.append(hpx);
		return sb.toString();
	}
	
	/**
	 * 客户端当前版本号
	 * 自定义规则 (storeId + 框架版本号 + 客户端版本号)
	 * @return
	 */
	public String getCurVer() {
		StringBuffer sb = new StringBuffer();
		sb.append(getStoreId());
		sb.append("_");
		sb.append(1); // 框架版本号，暂时写死,因为server端没定好
		sb.append("_");
		sb.append(clientVersionCode);
		String curVer = sb.toString();
		return curVer;
	}
	
	/**获取商店ID号*/
	public String getStoreId() {
		return storeId;
	}
	
	/**获取渠道号*/
	public String getSum() {
		return config.getSnum();
	}
	
	/**获取手机IMEI*/
	
	public static synchronized ClientApiService getInstance(Context cxt) {
		if (instance != null)
			return instance;
		instance = new ClientApiService(cxt);
		return instance;
	}
	
	/**获取当前应用版本名称*/
	public String getVersionName() {
		return clientVersionName;
	}
	
	/**获取当前应用版本号*/
	public String getVersionCode() {
		return clientVersionCode;
	}

}
