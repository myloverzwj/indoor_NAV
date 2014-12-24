package com.modou.loc.module.map2;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 下午9:30:04
 * 类说明:
 * 点实体类
 */
public class Point {

	/**X坐标*/
	private float x;
	/**Y坐标*/
	private float y;
	/**Z坐标*/
	private float z;
	
	public Point(){
		
	}
	
	public Point(float x, float y) {
		this(x, y, 0);
	}
	
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	@Override
	public Point clone() {
		return new Point(x, y, z);
	}
	
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
}
