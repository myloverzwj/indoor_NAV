package com.modou.data.resolver;

import org.json.JSONObject;

import com.modou.utils.MLog;

/**
 * 数据抽象类
 * @author changqiang.li
 * 所有数据实体类都继承该类, 该类主要获取服务器返回的参数头信息（返回是否正确标识、返回信息...）
 */
public class BaseRet {
	
	private String result;
	private String retMsg;
	private String keep;	// 数据是否更新 （keep==null表明有说句， 否则说明无数据更新）
	private String server_data_time;	// server时间戳(上次server数据更新时间)
	
	public BaseRet(){}
	
	public BaseRet(JSONObject json) {
		result = json.optString("resultCode");
		retMsg = json.optString("reason");
		keep = json.optString("keep");
		String updatetime = json.optString("updatetime");
		if (updatetime != null && !"".equals(updatetime)) {
			server_data_time = updatetime;
		}
	}
	
	public final boolean isSucces() {
		MLog.d("mylog", "result:" + result);
		return "0".equals(result) || "2002".equals(result);
	}
	
	public final boolean isReLogin() {
		return "0010".equals(result);
	}
	
	public final boolean isFail() {
		return !isSucces() && !isReLogin();
	}
	
	/**数据是否有刷新*/
	public final boolean isRefreshData() {
		MLog.d("mylog", "keep=============" + keep);
		return keep == null || "".equals(keep);
	}
	
	final public String getResult() {
		return result;
	}

	final public String getRetMsg() {
		return retMsg;
	}
	
	public final String getSverUpdateTime() {
		return server_data_time;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("result:");
		sb.append(result);
		sb.append(",retMsg:");
		sb.append(retMsg);
		sb.append(",keep:");
		sb.append(keep);
		sb.append(",sysMsg:");
		sb.append(",servereDataTime:");
		sb.append(server_data_time);
		return sb.toString();
	}

}
