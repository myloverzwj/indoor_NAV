/*
 * ECE 480 Spring 2011
 * Team 2 Design Project
 * Matt Gottshall
 * Jake D'Onofrio
 * Gordie Stein
 * Andrew Kling
 */
package com.modou.loc.module.map2;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.modou.utils.MethodUtils;

import android.content.Context;
import android.opengl.GLES20;

public abstract class GraphicsObject {
	
	protected int mProgram; // 自定义渲染管线着色器程序id
	protected int muMVPMatrixHandle; // 总变换矩阵引用
	protected int maPositionHandle; // 顶点位置属性引用
	protected int maColorHandle; // 顶点颜色属性引用
	protected int maTexCoorHandle; // 顶点纹理坐标属性引用id
	protected int texId;			// 纹理图片ID
	protected String mVertexShader; // 顶点着色器代码脚本
	protected String mFragmentShader; // 片元着色器代码脚本
	
	protected FloatBuffer mTexCoorBuffer; // 顶点纹理坐标数据数据
	protected FloatBuffer mVertexBuffer; // 顶点坐标数据缓冲
	protected FloatBuffer mColorBuffer; // 顶点着色数据缓冲
	protected int vCount; // 顶点数量
	protected float[] colors; // 图形颜色
	
	protected List<Point> pointList;
	protected float[] pointArr;
	protected int index;
	
	protected Context mContext;
	
	public GraphicsObject(Context ctx) {
		this.mContext = ctx;
		
		// 默认颜色
		colors = new float[] {
				1, 0, 0, 0, // 黄
				1, 0, 0, 0, // 红
				1, 0, 0, 0, // 黄
				1, 0, 0, 0 // 红
		};
		pointList = new ArrayList<Point>();
		initShader(ctx);
	}
	
	/**
	 * 读取配置文件中的颜色
	 * @param color
	 */
	public void setColor(int color) {
		colors = MethodUtils.convertColors(color);
	}
	
//	protected void init() {
//		initVertexData();
//		initShader(ctx);
//	}
	
	protected void addPoint(Point p) {
		pointList.add(p);
	}
	
	protected void addPoint(float x, float y) {
		addPoint(x, y, 0);
	}
	
	protected void addPoint(float x, float y, float z) {
		pointList.add(new Point(x, y, z));
	}
	
	/**
	 * 一次性将要描绘的点全部加入
	 * @param points
	 */
	protected void addPoints(List<Point> points) {
		pointList = points;
		initPointArrs();
	}
	
	/**初始化顶点坐标与着色数据的方法*/
	protected void initVertexData() {
		
	}
	
	/**创建Float数组对象*/
	protected void initPointArrs() {
		int size = pointList.size();
		// 重新创建Float数组对象
		pointArr = new float[size * 3]; // 一个point对象中保存了x、y、z3个坐标值
		index = 0;
		
		Point point = null;
		for (int i = 0; i < size; i++) {
			point = pointList.get(i);
			pointArr[index++] = point.getX();
			pointArr[index++] = point.getY();
			pointArr[index++] = point.getZ();
		}
	}
	
	/**初始化着色器方法*/
	public void initShader(Context ctx) {
		// 加载顶点着色器的脚本内容
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", ctx.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", ctx.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// 创建程序中顶点位置属性引用id
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		//获取程序中顶点纹理坐标属性引用id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
		// 获取程序中顶点颜色属性引用id
		maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor"); //TODO 现在改成了vColor,之前是aColor,这样相当于取不到色值，默认就成了黑色，需更改
		// 获取程序中总变换矩阵引用id
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}
	
	public abstract void Draw(GL10 gl);
}
