package com.modou.utils;

import com.modou.loc.service.LocService;

import android.content.Context;
import android.content.Intent;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-5-24 上午11:39:15
 * 类说明:
 * 定位工具类
 */
public class LocTool {

	/**启动定位服务*/
	public static void startLocService(Context ctx) {
		Intent service = new Intent(LocService.START_LOC_SERVICE);
		ctx.startService(service);
	}
	
}
