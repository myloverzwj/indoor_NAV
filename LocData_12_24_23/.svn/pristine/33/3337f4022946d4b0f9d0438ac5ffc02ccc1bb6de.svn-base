package com.modou.loc.module.map2.config;

import java.util.List;

import com.modou.loc.module.map2.Point;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-2 下午9:04:39
 * 类说明:
 * 地图单元类
 */
public class Cell {
	/**单元名称*/
	private String name;
	/**单元类型*/
	private String type;
	/**形状类型*/
	private String shape;
	/**坐标点集合*/
	private List<Point> points;
	/**开始的角度*/
	private float startDeg;
	/**弧度*/
	private float arcDeg;
	/**弧度角度*/
	private float radius;
	/**圆心坐标*/
	private Point centerPoint;
	/**颜色值*/
	private int color;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public float getStartDeg() {
		return startDeg;
	}
	public void setStartDeg(float startDeg) {
		this.startDeg = startDeg;
	}
	public float getArcDeg() {
		return arcDeg;
	}
	public void setArcDeg(float arcDeg) {
		this.arcDeg = arcDeg;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public Point getCenterPoint() {
		return centerPoint;
	}
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}
	@Override
	public String toString() {
		return "Cell [name=" + name + ", type=" + type + ", shape=" + shape
				+ ", points=" + getPointsInfo() + ", startDeg=" + startDeg + ", arcDeg="
				+ arcDeg + "]";
	}
	
	private String getPointsInfo() {
		int size = points == null ? 0 : points.size();
		Point point = null;
		StringBuffer sb = new StringBuffer();
		sb.append("{ ");
		for (int i = 0; i < size; i++) {
			point = points.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(i + ": ");
			sb.append(point.toString());
		}
		sb.append("}");
		return sb.toString();
	}
	
	
}
