package com.modou.loc.receiver;

import java.util.ArrayList;
import java.util.List;

import com.modou.loc.entity.WifiEntity;
import com.modou.utils.MLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-8 下午4:49:09
 * 类说明:
 * wifi变化广播接收器
 * 当wifi有变化的时候，重新获取wifi数据列表，并将数据展示到页面中
 */
public abstract class WifiReceiver extends BroadcastReceiver {

	private Context mContext;
	private WifiManager mWifiMgr;
	private List<WifiEntity> mData;
	
	public WifiReceiver (Context ctx) {
		mContext = ctx;
		mWifiMgr = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		// 初始化时就建立一个空数据集合列表，用于存储扫描到的数据
		mData = new ArrayList<WifiEntity>();
		
		addReceiver();
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		List<ScanResult> arr = mWifiMgr.getScanResults();
		if (arr == null || arr.size() == 0)
			return;
		
		mData.clear();
		for (ScanResult s : arr) {
//			MLog.d("wifi", "level==" + s.level);
			mData.add(new WifiEntity(s));
		}
		scanResults(mData);
	}
	
	/**
	 * wifi有变化时调用该方法，传递扫描数据集合
	 * @param result	扫描结果
	 */
	public abstract void scanResults(List<WifiEntity> result);

	/**
	 * 注册wifi变化广播
	 */
	private void addReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		mContext.registerReceiver(this, filter);
	}
	
}
