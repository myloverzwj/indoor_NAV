package com.modou.loc.data.transfer;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-22 上午8:43:35
 * 类说明:
 * Wifi数据实体类
 */
public class WifiDataBean {

	/**MAC地址*/
	private String macAddress;
	/**当前定位时间*/
	private long curTime;
	/**信号强度*/
	private int signalStrength;
	
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public long getCurTime() {
		return curTime;
	}
	public void setCurTime(long curTime) {
		this.curTime = curTime;
	}
	public int getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}
	
	@Override
	public String toString() {
		return "WifiDataBean [macAddress=" + macAddress + ", curTime="
				+ curTime + ", signalStrength=" + signalStrength + "]";
	}
	
	
}
