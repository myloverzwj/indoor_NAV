package com.modou.loc.module.map2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.modou.utils.MLog;

import android.content.Context;
import android.opengl.GLES20;
import android.util.FloatMath;
import android.util.Log;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:54:40
 * 类说明:
 * 绘制圆弧形状
 */
public class ShapeArc extends GraphicsObject {
	
	public ShapeArc(Context ctx) {
		super(ctx);
	}
	
	/**
	 * 初始化弧形数据
	 * 起点是扇形所在的圆的圆心，终点是扇形的弧的中点。
	 * @param startP		起点(圆心)
	 * @param endP			弧的中点
	 * @param arcDeg		总度数(夹角度数)
	 * @param startDeg		从X轴到起始边的角度(在X轴下方时，startdeg为正，在X轴上方时，startdeg为负)
	 */
	public void initShapeDatas(Point startP, Point endP, float arcDeg, float startDeg) {
		// 添加圆心
		addPoint(startP);
		// 算出直径
		double powX = Math.pow((endP.getX() - startP.getX()), 2);
		double powY = Math.pow(endP.getY()-startP.getY(), 2);
		double diameter = Math.sqrt(powX + powY);
		// 半径
		double R = diameter / 2;
		// 将度数转化为弧度
		double radians = Math.toRadians(arcDeg);
		
		int pSize = 40;
		float PI = 3.1416f;
		Point point = null;
		float x,y;
		for (int i=0; i<pSize; i++) {//TODO 弧度得需要求出起始弧度
			x = (float) (R * Math.cos(i * radians / pSize));
			y = (float) (R * Math.sin(i * radians / pSize));
			x += startP.getX();
			y += startP.getY();
			point = new Point(x, y);
			Log.d("mylog", "扇形点的坐标x=" + x + " ,y=" + y);
			addPoint(point);
		}
	}
	
	/**
	 * 已知圆心点和弧的两个端点进行画弧
	 * @param centerPoint	圆心点
	 * @param startPoint	起点
	 * @param endPoint		终点
	 */
	public void initShapeDatas(Point centerPoint, Point startPoint, Point middlePoint, Point endPoint) {
		
		//TODO 测试数据
		Point tx = computePoint(startPoint, middlePoint, endPoint);
		MLog.d("tx=====" + tx.toString() + " ,cx====" + centerPoint.toString());
//		addPoint(centerPoint);
//		addPoint(startPoint);
//		addPoint(endPoint);
		
		List<Point> ps = MathUtils.computeCirlePoint(centerPoint, startPoint, middlePoint, endPoint);
		pointList.addAll(ps);

	    int size = pointList.size();
		// 重新创建Float数组对象
		pointArr = new float[size * 3];
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
		mVertexBuffer.put(pointArr);		// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0); // 设置缓冲区起始位置
		
		// 顶点颜色值数组，每个顶点4个色彩值RGBA
		int count = 0;
        float color[]=new float[vCount*4];
        for(int i=0; i<color.length; i+=4){
        	// 对每一个顶点都进行赋值
        	color[count++] = colors[0]; 
        	color[count++] = colors[1]; 
        	color[count++] = colors[2]; 
        	color[count++] = 0;
        }
		// 创建顶点着色数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(color.length * 4);
		cbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mColorBuffer = cbb.asFloatBuffer(); // 转换为Float型缓冲
		mColorBuffer.put(color); // 向缓冲区中放入顶点着色数据
		mColorBuffer.position(0); // 设置缓冲区起始位置
	}
	
	@Override
	public void Draw(GL10 gl) {
		if (mVertexBuffer == null)
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
		
		GLES20.glLineWidth(5);//设置线的宽度
		
		GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vCount);
	}

	/**3点定位*/
	private Point computePoint(Point p1, Point p2, Point p3) {
		float x1 = p1.getX();
		float x2 = p2.getX();
		float x3 = p3.getX();
		float y1 = p1.getY();
		float y2 = p2.getY();
		float y3 = p3.getY();
		
		// 圆心X坐标点
		float x = ((y3-y1)*(y2-y1)*(y3-y2) - (x2+x3)*(x2-x3)*(y2-y1) + (x1+x2)*(x1-x2)*(y3-y2))/
				((y3-y2)*(x1-x2) - (y2-y1)*(x2-x3)) / 2;
		
		// 圆心Y坐标点
		float y = (x2-x2)/(y3-y2)*(x - (x2+x3)/2) + (y2+y3)/2;
		
		return new Point(x, y);
	}

}
