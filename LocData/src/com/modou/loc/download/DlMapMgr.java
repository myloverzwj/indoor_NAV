package com.modou.loc.download;

import java.util.HashMap;

import android.content.Context;

import com.modou.loc.entity.Building;
import com.modou.loc.task.DlMapFileTask;


/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-10 下午7:55:07
 * 类说明:
 * 地图下载管理类
 */
public class DlMapMgr {

	private static DlMapMgr instance = null;
	private Context mContext;
	
	/**地图下载任务列表*/
	private HashMap<String, DlMapFileTask> dlTasks = new HashMap<String, DlMapFileTask>();
	
	private DlMapMgr() {}
	
	public void init(Context ctx) {
		this.mContext = ctx;
	}
	
	/**
	 * 添加下载地图任务
	 * @param building
	 */
	public void addTask(Building building) {
		DlMapFileTask task = new DlMapFileTask(mContext, building);
		task.execute();
		
		dlTasks.put(building.getId(), task);
	}
	
	/**
	 * 是否在下载队列中
	 * @param buildId	建筑物ID
	 * @return
	 */
	public boolean isDownloading(String buildId) {
		return dlTasks.containsKey(buildId);
	}
	
	public static DlMapMgr getInstance() {
		if (instance == null)
			instance = new DlMapMgr();
		return instance;
	}
	
}
