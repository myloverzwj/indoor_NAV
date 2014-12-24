package com.modou.data.resolver;

import org.json.JSONObject;

/**
 *@author 李长强  E-mail: xyylchq@163.com
 *@version 创建时间：2013-1-15上午09:47:06
 * 注册用户解析类
 */
public class RegisterRet extends BaseRet {

	private String userId;
	
	public RegisterRet(JSONObject json) {
		super(json);
		
		if (json == null)
			return;
		userId = json.optString("result");
	}
	
	public String getUserId() {
		return userId;
	}
}
