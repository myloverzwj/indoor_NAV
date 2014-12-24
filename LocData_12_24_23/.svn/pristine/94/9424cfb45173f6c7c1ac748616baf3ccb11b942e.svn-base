package com.modou.data.resolver;

import org.json.JSONObject;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-13 下午4:49:40
 * 类说明:
 * 地图文件解析类
 */
public class MapFileRet extends BaseRet {

	/**地图名称*/
	private String mapName;
	/**地图下载url*/
	private String dlUrl;
	
	public MapFileRet(JSONObject jb) {
		super(jb);
		
		if (isSucces()) {
			dlUrl = jb.optString("mapFileUrl"); //TODO mapFileUrl字段有可能会更改
			mapName = jb.optString("dlUrl");
		}
	}

	public String getDlUrl() {
		return dlUrl;
	}
	
	public String getMapName() {
		return mapName;
	}
	
}
