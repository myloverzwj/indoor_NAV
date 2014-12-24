package com.modou.data.resolver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.modou.loc.entity.UserInfo;
import com.modou.utils.MLog;
import com.modou.utils.StringUtils;

/**
 *@author 李长强  E-mail: xyylchq@163.com
 *@version 创建时间：2013-6-22下午03:22:25
 *用户信息数据解析类
 */
public class UserRet extends BaseRet {

	private UserInfo userInfo;
	
	public UserRet(JSONObject json) {
		super(json);
		
		if (isSucces()) {
			json = json.optJSONObject("result");
			parseJsonObj(json);
		}
	}

	private void parseJsonObj(JSONObject json) {
		List<String> postAddressArr = new ArrayList<String>();
		userInfo = new UserInfo();
//		userInfo.setUserId(json.optString("id"));
//		userInfo.setCreateDate(json.optString("createdate"));
//		userInfo.setEmail(json.optString("email"));
//		userInfo.setLastLoginTime(json.optString("lastlogintime"));
//		userInfo.setAccount(json.optString("loginname"));
//		userInfo.setLoginNum(json.optLong("loginnum"));
//		userInfo.setPhoneNumber(json.optString("mobile"));
//		userInfo.setOrderTel(json.optString("orderTel"));
//		userInfo.setPassword(json.optString("password"));
//		userInfo.setPostcode(json.optString("postalcode"));
//		userInfo.setName(json.optString("username"));
//		userInfo.setUserType(json.optInt("usertype"));
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
}
