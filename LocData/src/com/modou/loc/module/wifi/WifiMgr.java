package com.modou.loc.module.wifi;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.loc.data.transfer.WifiDataBean;
import com.modou.utils.MLog;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-5-24 下午2:40:09
 * 类说明:
 * Wifi数据管理类
 */
public class WifiMgr {

	private static WifiMgr instance = null; 
	
	private WifiManager mWifiManager;
	private Context mContext;
	/**定位次数*/
	private long time;
	
	/**WIFI数组最大容量*/
	private int WIFI_ARR_SIZE = 1000;//TODO 和定时器成比例
	/**wifi数据数组*/
	private WifiDataBean[] wifiDataArrs = new WifiDataBean[WIFI_ARR_SIZE];
	
	private WifiMgr() {
	}
	
	/**初始化操作*/
	public void init(Context ctx) {
		mContext = ctx;
		mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	}
	
	/**
	 * 获取初始WIFI信息
	 * 返回格式：
	 * [{"mac_address":"6c:e8:73:b1:45:0a","cur_time":1400916601520,"signal_strength":-55},
	 *  {"mac_address":"08:10:76:6f:e9:7f","cur_time":1400916601520,"signal_strength":-83},
	 *  {......}]
	 * @return	当前手机周围wifi设备信息
	 */
	public WifiDataBean[] getInitWifiData(Context ctx) {
		WifiManager mWifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> wifiList = mWifiManager.getScanResults();
		if (wifiList == null)
			return null;
		
		int size = wifiList.size();
		if (size == 0)
			return null;
		WifiDataBean[] arr = new WifiDataBean[size];
		
		ScanResult scan = null;
		WifiDataBean wifiBean = null;
		try {
			for (int i = 0; i < size; i++) {
				scan = wifiList.get(i);
				if (scan == null)
					continue;
				wifiBean = new WifiDataBean();
				wifiBean.setCurTime(System.currentTimeMillis());
				wifiBean.setMacAddress(scan.BSSID);
				wifiBean.setSignalStrength(scan.level);
				
				arr[i] = wifiBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return arr;
	}

	/**
	 * 获取当前手机周围wifi设备信息
	 * @return
	 */
	public void addLocData() {
		if (mContext == null)
			return;
		if (mWifiManager == null) {
			mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		}
		List<ScanResult> wifiList = mWifiManager.getScanResults();
		if (wifiList == null)
			return;
		int size = wifiList.size();
		ScanResult scan = null;
		WifiDataBean wifiBean = null;
		
		for (int i = 0; i < size; i++) {
			
			scan = wifiList.get(i);
			if (scan == null)
				continue;
			
			wifiBean = new WifiDataBean();
			wifiBean.setCurTime(System.currentTimeMillis());
			wifiBean.setMacAddress(scan.BSSID);
			wifiBean.setSignalStrength(scan.level);

			DataTransferMgr2.getInstance().addWifiData(wifiBean);
		}
		
	}
	
	public void gc() {
		instance = null;
	}
	
	public static WifiMgr getInstance() {
		if (instance == null) {
			instance = new WifiMgr();
		}
		return instance;
	}
	
}
