package com.modou.loc.service;

import com.modou.loc.module.sensor.SensorMgr;
import com.modou.loc.module.wifi.WifiMgr;
import com.modou.loc.task.SensorDataTask;
import com.modou.loc.task.WifiDataTask_old;
import com.modou.utils.MLog;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-5-24 上午10:01:04
 * 类说明:
 * 后台定位服务
 * 用于定时获取wifi和Sensor相关数据，并记录到文件中。
 */
public class LocService extends Service {

	public static final String START_LOC_SERVICE = "com.modou.loc.start_loc_service";
	private AsyncTask<Void, Void, Void> wifiTask, sensorTask;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		MLog.d("定位服务onCreate...");
		init();
		super.onCreate();
	}

	private void init() {
		// 启动Wifi定位数据采集任务
		if (wifiTask == null || wifiTask.isCancelled()) {
			wifiTask = new WifiDataTask_old(getApplicationContext());
			wifiTask.execute();
		}
		
		// 启动Sensor定位数据采集任务
//		if (sensorTask == null || sensorTask.isCancelled()) {
//			sensorTask = new SensorDataTask(getApplicationContext());
//			sensorTask.execute();
//		}
		SensorMgr.getInstance().init(getApplicationContext());
	}
	
	@Override
	public void onDestroy() {
		SensorMgr.getInstance().gc();
		super.onDestroy();
	}

}
