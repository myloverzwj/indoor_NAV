package com.modou.loc;

import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.loc.module.sensor.SensorMgr;
import com.modou.loc.task.SensorDataTask;
import com.modou.loc.task.WifiDataTask;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 该类暂无用到
 */
public class MainActivity extends BaseActivity {

	private WifiDataTask wifiDataTask;
	private SensorDataTask sensorDataTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
//		initData();
		
		
		startLoc();
	}

	private void startLoc() {
		wifiDataTask = new WifiDataTask(this);
		wifiDataTask.start();
		
		sensorDataTask = new SensorDataTask(this);
		sensorDataTask.start();
		
		DataTransferMgr2.getInstance().init(null);
		DataTransferMgr2.getInstance().start();
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {//TODO 暂时屏蔽数据采集任务
//		wifiDataTask = new WifiDataTask(this);
//		wifiDataTask.start();
		
//		sensorDataTask = new SensorDataTask(this);
//		sensorDataTask.start();
		
		
		
		// 获取手机设备支持参数，并显示到页面上
		String res = SensorMgr.getInstance().getSupportSensorInfo();
		TextView txtviewContent = (TextView) findViewById(R.id.txtview_msg);
		txtviewContent.setText(res);
	}
	
	@Override
	protected void onDestroy() {
		
//		wifiDataTask.cancel();
//		sensorDataTask.cancel();
		
		super.onDestroy();
	}
	
}
