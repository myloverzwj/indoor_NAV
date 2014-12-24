package com.modou.loc.db;

import java.util.ArrayList;
import java.util.List;

import com.modou.loc.entity.Building;
import com.modou.utils.StringUtils;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-13 上午10:49:21
 * 类说明:
 * 数据库逻辑操作管理类
 */
public class DBMgr {

	/**商场信息列表*/
	private ArrayList<Building> shopList;
	
	private static DBMgr instance = null;
	
	private DBMgr () {
		
	}
	
	/**
	 * 获取商城信息集合
	 * @return	商城信息集合
	 */
	public ArrayList<Building> getShopList() {
		if (shopList == null) {
			shopList = DBDao.getInstance().getShops();
		}
		return shopList;
	}
	
	/**
	 * 向数据库中添加一条商场记录
	 * @param shopId	商场ID
	 * @param shopName	商场名称
	 * @param shopImgUrl 商场图片URL
	 */
	public void addShop(String shopId, String shopName, String shopImgUrl) {
		if (StringUtils.isEmpty(shopId) || StringUtils.isEmpty(shopName) ||
				StringUtils.isEmpty(shopImgUrl)) {
			throw new RuntimeException("向数据库中添加一条商场记录时，参数非法 shopId=" + shopId + 
					" ,shopName=" + shopName + " ,shopImgUrl=" + shopImgUrl);
		}
		
		DBDao.getInstance().addShop(shopId, shopName, shopImgUrl);
		addCacheData(shopId, shopName, shopImgUrl, shopImgUrl);
	}
	
	/**将数据添加到缓存中*/
	private void addCacheData(String shopId, String shopName, String shopImgUrl, String buildingID) {
		if (shopList == null) {
			shopList = new ArrayList<Building>();
			shopList.add(new Building(shopId, shopName, shopImgUrl, buildingID));
		} else {
			boolean isExist = isExistInCache(shopId);
			if (isExist) {
				shopList.add(new Building(shopId, shopName, shopImgUrl, buildingID));
			}
		}
		
	}

	/**
	 * 检测某个商城ID是否在缓存列表中
	 * @param shopId	商城ID
	 * @return 是否在缓存列表中 
	 */
	public boolean isExistInCache(String shopId) {
		int size = shopList.size();
		Building shop = null;
		for (int i = 0; i < size; i++) {
			shop = shopList.get(i);
			if (shop == null)
				continue;
			if (shopId.equals(shop.getId())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查看数据库中某商场信息是否已经下载
	 * @param shopId	商场ID
	 * @return	true: 已下载		false: 未下载
	 */
	private boolean isHasDatas(String shopId) {
		if (StringUtils.isEmpty(shopId)) {
			throw new RuntimeException("查看数据库中某商场信息是否已经下载时 shopId属性值非法 shopId=" + shopId);
		}
		
		boolean res = DBDao.getInstance().isHasDatas(shopId);
		return res;
	}
	
	/**
	 * 删除某条商场信息
	 * @param shopId	商场ID
	 */
	public void delShopRecord(String shopId) {
		if (StringUtils.isEmpty(shopId)) {
			throw new RuntimeException("删除某条商场信息时 shopId属性值非法 shopId=" + shopId);
		}
		
		DBDao.getInstance().delShopRecord(shopId);
		
		delCacheRecord(shopId);
	}
	
	/**
	 * 删除缓存中的记录
	 * @param shopId	商城ID
	 */
	private void delCacheRecord(String shopId) {
		int size = shopList.size();
		Building shop = null;
		for (int i = 0; i < size; i++) {
			shop = shopList.get(i);
			if (shop == null)
				continue;
			if (shopId.equals(shop.getId())) {
				shopList.remove(i);
				break;
			}
		}
	}
	
	public static DBMgr getInstance() {
		if (instance == null) {
			instance = new DBMgr();
		}
		return instance;
	}

	/**
	 * 设置缓存中的数据
	 * @param shopList
	 */
	public void setCacheShopDatas(ArrayList<Building> shopList) {
		this.shopList = shopList;
	}

	/**初始化商城数据*/
	public void initShopDatas() {
		if (shopList == null) {
			getShopList();
		}
	}
}
