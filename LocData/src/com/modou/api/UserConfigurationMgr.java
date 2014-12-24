package com.modou.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.modou.loc.entity.UserInfo;
import com.modou.utils.AesEncrypt;
import com.modou.utils.MLog;
import com.modou.utils.StorageUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

/**
 *@author 李长强  E-mail: xyylchq@163.com
 *@version 创建时间：2013-1-10下午09:34:52
 * 用户相关配置信息管理类
 */
public class UserConfigurationMgr {

	private static UserConfigurationMgr instance = null;
	
	/**用户是否已登录*/
	private boolean isLogin;
	/**用户信息实体*/
	private UserInfo userInfo;
	/**图片下载目录*/
	private String imgSavePath;

	private UserConfigurationMgr() {}
	
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	/**是否自动登录*/
	public boolean isAutoLogin(Context context) {
		return UserSharedPreferences.getInstance(context).isAutoLogin();
	}

	/**
	 * 设置自动登录状态
	 * @param isAutoLogin
	 */
	public void setAutoLogin(Context context, boolean isAutoLogin) {
		UserSharedPreferences.getInstance(context).setAutoLogin(isAutoLogin);
	}

	/**获取登录用户名*/
	public String getLoginName(Context context) {
		String loginName = UserSharedPreferences.getInstance(context).getLoginName();
//		loginName = DesEncrypt.getDesString(loginName);
		return loginName;
	}
	
	/**获取登录密码*/
	public String getLoginPwd(Context context) {
		String loginPwd = UserSharedPreferences.getInstance(context).getLoginPwd();
//		loginPwd = DesEncrypt.getDesString(loginPwd);
		return loginPwd;
	}
	
	/**
	 * 保存用户名、密码
	 * @param loginName
	 * @param loginPwd
	 */
	public void setLoginInfo(Context context, String loginName, String loginPwd) {
//		loginName = DesEncrypt.getEncString(loginName);
//		loginPwd = DesEncrypt.getEncString(loginPwd);
		UserSharedPreferences.getInstance(context).setLoginInfo(loginName, loginPwd);
	}

	/**获取图片下载目录*/
	public String getImgSavePath(Context context) {
		imgSavePath = UserSharedPreferences.getInstance(context).getImgSavePath();
		if (imgSavePath == null) // 如果用户没有设置存储目录，则使用默认存储位置
			imgSavePath = StorageUtil.getImgSavePath(context);
		return imgSavePath;
	}
	
	/**设置下载图片的存储位置*/
	public boolean setImgSavePath(Context context, String savePath) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + savePath;
		File file = new File(path);
		if (file.mkdirs()) {
			UserSharedPreferences.getInstance(context).setImgSavePath(path + File.separator);
			return true;
		}
		return false;
	}
	
	/**获取搜索历史*/
	public List<String> getSearchHistorys(Context ctx) {
		return UserSharedPreferences.getInstance(ctx).getSearchHistorys();
	}
	
	/**
	 * 保存搜索记录
	 */
	public void saveSearchHistory(Context ctx, String str) {
		List<String> arrs = getSearchHistorys(ctx);
		if (arrs == null || arrs.size() <= 0) {
			arrs = new ArrayList<String>();
		} else {
			// 判断要添加的搜索记录是否已存在
			boolean isExist = false;
			for (String s : arrs) {
				if (s.equals(str)) {
					arrs.remove(s);
					isExist = true;
					break;
				}
			}
			
			// 如果不存在，则检测判断历史记录是否已经超出上限20, 如果超出，则删除最后一项
			if (!isExist) {
				int size = arrs.size();
				if (size >= 20)
					arrs.remove(size - 1);
			}
			
		}
		arrs.add(0, str);
		
		// 将搜索记录拼接成字符串
		StringBuffer sb = new StringBuffer();
		for (String s : arrs) {
			sb.append(s);
			sb.append("-");
		}
		String content = sb.substring(0, sb.length() - 1);
		MLog.d("mylog", "保存的内容=========" + content);
		UserSharedPreferences.getInstance(ctx).saveSearchHistory(content);
	}
	
	/**清空搜索历史*/
	public void clearSearchHistory(Context ctx) {
		UserSharedPreferences.getInstance(ctx).clearSearchHistory();
	}
	
	public static UserConfigurationMgr getInstance() {
		if (instance == null)
			instance = new UserConfigurationMgr();
		return instance;
	}
	
}

 class UserSharedPreferences {
	
	private Context context;
	private SharedPreferences spf;
	private final String DOWNLOAD_IMG_SAVE_PATH_PREFERENCE = "user_preference";
	
	private final String IMG_SAVE_PATH = "img_save_path";
	private final String SEARCH_KEYS = "search_keys";
	
	private final String LOGIN_NAME = "login_name";
	private final String LOGIN_PWD = "login_pwd";
	private final String AUTO_LOGIN = "auto_login";
	
	private static UserSharedPreferences instance = null;
	
	private UserSharedPreferences (Context context) {
		this.context = context;
		spf = context.getSharedPreferences(DOWNLOAD_IMG_SAVE_PATH_PREFERENCE, 0);
	}
	
	/**
	 * 设置下载图片存储位置
	 * @param path	图片存储位置
	 */
	public void setImgSavePath(String path) {
		Editor editor = spf.edit();
		editor.putString(IMG_SAVE_PATH, path);
		editor.commit();
	}
	
	/**获取图片下载位置*/
	public String getImgSavePath() {
		return spf.getString(IMG_SAVE_PATH, null);
	}
	
	/**
	 * 保存搜索历史
	 * 用“-”号分割, 如： 爱国者-联想-洗面乳
	 */
	public void saveSearchHistory(String content) {
		Editor editor = spf.edit();
		editor.putString(SEARCH_KEYS, content);
		editor.commit();
	}
	
	/**获取搜索历史*/
	public List<String> getSearchHistorys() {
		String content = spf.getString(SEARCH_KEYS, null);
		if (content == null)
			return null;
		
		String[] arrs = content.split("-");
		if (arrs == null || arrs.length <= 0)
			return null;
		
		List<String> datas = new ArrayList<String>(arrs.length);
		for (String s : arrs) {
			datas.add(s);
		}
		return datas;
	}
	
	/**清空搜索历史*/
	public void clearSearchHistory() {
		Editor editor = spf.edit();
		editor.remove(SEARCH_KEYS);
		editor.commit();
	}
	
	/**是否自动登录*/
	public boolean isAutoLogin() {
		return spf.getBoolean(AUTO_LOGIN, true);
	}

	/**
	 * 设置自动登录状态
	 * @param isAutoLogin
	 */
	public void setAutoLogin(boolean isAutoLogin) {
		Editor editor = spf.edit();
		editor.putBoolean(AUTO_LOGIN, isAutoLogin);
		editor.commit();
	}

	/**获取登录用户名*/
	public String getLoginName() {
		return spf.getString(LOGIN_NAME, null);
	}
	
	/**获取登录密码*/
	public String getLoginPwd() {
		String pwd = spf.getString(LOGIN_PWD, null);
		MLog.d("pwd===============" + pwd);
		try {
			pwd = AesEncrypt.decrypt("W3E4FCVBYTREW239", pwd);
			MLog.d("pwd=========222======" + pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pwd;
	}
	
	/**
	 * 保存用户名、密码
	 * @param loginName
	 * @param loginPwd
	 */
	public void setLoginInfo(String loginName, String loginPwd) {
		try {
			loginPwd = AesEncrypt.encrypt("W3E4FCVBYTREW239", loginPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Editor editor = spf.edit();
		editor.putString(LOGIN_NAME, loginName);
		editor.putString(LOGIN_PWD, loginPwd);
		editor.commit();
	}
	
	public static UserSharedPreferences getInstance(Context context) {
		if (instance == null) {
			instance = new UserSharedPreferences(context);
		}
		return instance;
	}
}
