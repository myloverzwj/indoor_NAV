package com.modou.loc.entity;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-4 下午3:49:11 类说明: 楼层实体类
 */
public class Floor {
	private int id;
	private String name;
	private boolean isSelected;

	public Floor() {

	}
	
	public Floor(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
