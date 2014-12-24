package com.modou.loc.module.map2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:53:33
 * 类说明:
 * 绘制圆形
 */
public class ShapeCircle extends GraphicsObject {

	private ByteBuffer mIndexBuffer;// 顶点构建索引数据缓冲
	
	public ShapeCircle(Context ctx) {
		super(ctx);
	}

	/**
	 * 起点和终点是该图形所对的外围矩形的斜对角线所在的端点
	 * @param startP
	 * @param endP
	 */
	public void addPoint(Point startP, Point endP) {
		// 根据两对角坐标点算出圆的宽度和高度
		float cW = Math.abs(startP.getX() - endP.getX());
		float cH = Math.abs(startP.getY() - endP.getY());
		// 算出半径
		cW /= 2;
		cH /= 2;
		
		// 先添加圆心坐标，其他点都是基于该点连接
		Point centerPoint = new Point();
		if (startP.getX() < endP.getX()) {
			centerPoint.setX(startP.getX() + cW);
		} else {
			centerPoint.setX(endP.getX() - cW);
		}
		
		if (startP.getY() < endP.getY()) {
			centerPoint.setY(startP.getY() + cH);
		} else {
			centerPoint.setY(endP.getY() - cH);
		}
//		addPoint(centerPoint);
		
		int pSize = 40; // 记录圆的等分个数，值越大，效果越好
		float PI = 3.1416f;
		Point point = null;
		float x,y;
		for (int i = 0; i <= pSize; i++) {
			x = (float) (cW * Math.cos(2 * i * PI / pSize));
			y = (float) (cH * Math.sin(2 * i * PI / pSize));
			// 求出弧上的点后，再加上中心点的坐标点,即得到真实位置
			x += centerPoint.getX();
			y += centerPoint.getY() + cH*2; //TODO 这里乘以2没搞明白是怎么回事
			point = new Point(x, y);
			addPoint(point);
		}
	}
	
	public void addPoint(Point p) {
		pointList.add(p);
		
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
	
	@Override
	protected void initVertexData() {
		super.initVertexData();
		
		vCount = pointArr.length / 3;
		// 创建顶点坐标数据缓冲
		ByteBuffer vbb = ByteBuffer.allocateDirect(pointArr.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer(); // 转换为Float型缓冲
		mVertexBuffer.put(pointArr); // 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0); // 设置缓冲区起始位置
		
		byte indices[] = new byte[vCount];
		for(int i=0; i<vCount; i++){
			indices[i] = (byte) i;
		}

		// 创建三角形构造索引数据缓冲
		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);// 向缓冲区中放入三角形构造索引数据
		mIndexBuffer.position(0);// 设置缓冲区起始位置
		
		// 顶点颜色值数组，每个顶点4个色彩值RGBA
		int count = 0;
        float colors[]=new float[vCount*4];
        colors[count++] = 1; 
        colors[count++] = 1; 
        colors[count++] = 1; 
        colors[count++] = 0;
        for(int i=4; i<colors.length; i+=4){
        	colors[count++] = 1; 
        	colors[count++] = 0; 
        	colors[count++] = 0; 
        	colors[count++] = 0;
        }
		// 创建顶点着色数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mColorBuffer = cbb.asFloatBuffer(); // 转换为Float型缓冲
		mColorBuffer.put(colors); // 向缓冲区中放入顶点着色数据
		mColorBuffer.position(0); // 设置缓冲区起始位置
	}
	
	@Override
	public void Draw(GL10 gl) {
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
//		GLES20.glUniform4fv(maColorHandle, 1, colors, 0);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		
//		GLES20.glLineWidth(5);//设置线的宽度
		
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
//		GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, vCount,
//				GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
	}

}
