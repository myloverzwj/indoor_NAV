package com.modou.loc.module.map2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.modou.loc.module.map2.config.CoordUtil;
import com.modou.utils.MLog;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 下午9:20:29
 * 类说明:
 * 用户行走轨迹类
 */
public class WalkPath extends GraphicsObject {

	/**用户行走轨迹集合*/
	private List<Point> pointList;
	
	private float[] pointArr;
	private int index;
	
	private ShapeWalkHead shapeHead;
	
	public WalkPath(Context ctx, ShapeWalkHead head) {
		super(ctx);
		
		shapeHead = head;
		pointList = new ArrayList<Point>();
		
		
		//TODO 测试数据
//		addPoint(0.5f, -0.5f);
//		addPoint(0f, -1f);
//		addPoint(1f, -1f);
//		addPoint(0.5f, -0.5f);
		
//		addPoint(1, 0);
//		addPoint(-2, 2);
//		addPoint(-1, 0);
//		addPoint(0, 4);
//		addPoint(-4, 0);
		
		initVertexData();
		initShader(ctx);
	}
	
	boolean isInit;//TODO 是否初始化过，暂时这样写,用于解决绘制时，线条闪屏问题
	@Override
	protected void initVertexData() {
		super.initVertexData();
		
		int arrLen = pointArr == null ? 0 : pointArr.length;
		vCount = pointArr == null ? 0 : arrLen / 3;
		// 创建顶点坐标数据缓冲
		ByteBuffer vbb = ByteBuffer.allocateDirect(arrLen * 4);
		vbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer(); // 转换为Float型缓冲
		if (pointArr != null)
			mVertexBuffer.put(pointArr); // 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0); // 设置缓冲区起始位置
		
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		
		if (!isInit) {
			isInit = true;
			// 顶点颜色值数组，每个顶点4个色彩值RGBA
			float[] colors = new float[] {
				0, 0, 0, 0, // 黄
				0, 0, 0, 0, // 红
				0, 0, 0, 0, // 黄
				0, 0, 0, 0 // 红
			};
			// 创建顶点着色数据缓冲
			ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
			cbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
			mColorBuffer = cbb.asFloatBuffer(); // 转换为Float型缓冲
			mColorBuffer.put(colors); // 向缓冲区中放入顶点着色数据
			mColorBuffer.position(0); // 设置缓冲区起始位置
		}
	}
	
	long preTime = System.currentTimeMillis();
	@Override
	public void Draw(GL10 gl) {
		//TODO 测试数据
//		if (System.currentTimeMillis() - preTime > 1500) {
//			preTime = System.currentTimeMillis();
//			addPoint((float)Math.random() * 3, (float)Math.random() * 3);
//		}
		
		
		if (mVertexBuffer == null) // 表明轨迹从未发生变化，还没有初始化
			return;
		
		// 制定使用某套shader程序
		GLES20.glUseProgram(mProgram);
		
		// 将最终变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
						MatrixState.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
						false, 3 * 4, mVertexBuffer);
		// 为画笔指定顶点着色数据
		GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
						4 * 4, mColorBuffer);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maColorHandle);
		
		GLES20.glLineWidth(2);//设置线的宽度
		
		GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vCount);
	}

	/**
	 * 向行为轨迹中加入新坐标点
	 * @param x
	 * @param y
	 * @param z
	 */
	public void addPoint(float x, float y, float z) {
		addPoint(new Point(x, y, z));
	}
	
	/**
	 * 向行为轨迹中加入新坐标点
	 * @param x
	 * @param y
	 */
	public void addPoint(float x, float y) {
		addPoint(x, y, 0);
	}
	
	public void updateUserTrack(Point[] pts) {
		pointList.clear();
		
		if (pts != null) {
			int len = pts.length;
			Point p = null;
			for (int i = 0;  i< len; i++) {
				p = pts[i];
				float tx = CoordUtil.toGLX(p.getX());
				float ty = CoordUtil.toGLY(p.getY());
				MLog.d("转换前的定位坐标x:" + p.getX() + " ,y:" + p.getY() + " ,转换后的坐标x:" + tx + " ,y:" + ty);
				p.setX(tx);
				p.setY(ty);
				pointList.add(p);
			}
			
			initDrawData();
		}
	}
	
	/**
	 * 添加用户行为轨迹点
	 * @param p	新的轨迹点
	 */
	public void addPoint(Point p) {
		//重新转换定位点坐标
//		float tx = CoordUtil.toGLX(p.getX());
//		float ty = CoordUtil.toGLY(p.getY());
//		MLog.d("转换前的定位坐标x:" + p.getX() + " ,y:" + p.getY() + " ,转换后的坐标x:" + tx + " ,y:" + ty);
//		p.setX(tx);
//		p.setY(ty);
		
		
		pointList.add(p);
		
		initDrawData();
	}
	
	private void initDrawData() {
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
		
//		shapeHead.updatePosition(pointList.get(pointList.size()-1));
		initVertexData();
	}

	private float dx, dy;
	public void move(float dx, float dy) {
		this.dx += dx;
		this.dy += dy;
	}
	
}
