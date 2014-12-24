package com.modou.loc.entity;

import android.net.wifi.ScanResult;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-8 下午3:47:06
 * 类说明:
 * wifi实体类
 */
public class WifiEntity {

	/**是否解锁(wifi处于开放状态)*/
	private boolean isOpen;
	
	/**wifi扫描纪录*/
	private ScanResult scanResult;

	public WifiEntity(ScanResult sr) {
		scanResult = sr;
		isOpen = false;
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public ScanResult getScanResult() {
		return scanResult;
	}

	public void setScanResult(ScanResult scanResult) {
		this.scanResult = scanResult;
	}
	
	
}
