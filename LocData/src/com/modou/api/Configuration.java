package com.modou.api;

/**
 * 服务配置信息实体类
 * @author changqiang.li
 * 记录服务器的相关配置
 */
public class Configuration {
	public static enum RUNTIME_SERVER_TYPE {
		PRODUCT, TEST_PRODUCT, STAGINIG, DEVELOPER
	}
	
	private RUNTIME_SERVER_TYPE serverType = RUNTIME_SERVER_TYPE.PRODUCT;
	private int httpPort = 80;
	private int httpsPort = 443;
	private int timeOut = 30 * 1000;
	private String privatekey;
	private String snum;				//渠道号
	private boolean supportSSL = false;
	private String rootPath = "/";
	private String hostName;
	private String signCode;
	private String shopId;
	
	public String getHttpURL() {
		return "http://" + this.getHostName() + ":" + this.getHttpPort() + this.getRootPath();
	}
	
	public String getHttpsURL() {
		return "http://" + this.getHostName() + ":" + this.getHttpsPort() + this.getRootPath();
	}
	
	public RUNTIME_SERVER_TYPE getServerType() {
		return serverType;
	}
	public void setServerType(RUNTIME_SERVER_TYPE serverType) {
		this.serverType = serverType;
	}
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	public int getHttpsPort() {
		return httpsPort;
	}
	public void setHttpsPort(int httpsPort) {
		this.httpsPort = httpsPort;
	}
	public boolean isSupportSSL() {
		return supportSSL;
	}
	public void setSupportSSL(boolean supportSSL) {
		this.supportSSL = supportSSL;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getPrivatekey() {
		return privatekey;
	}
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}
	public String getSnum() {
		return snum;
	}
	public void setSnum(String snum) {
		this.snum = snum;
	}
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
}
