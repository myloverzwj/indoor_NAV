package com.modou.loc.module.map2;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:54:02
 * 类说明:
 * 绘制扇形
 */
public class ShapeSector extends ShapeArc {

	public ShapeSector(Context ctx) {
		super(ctx);
	}

	@Override
	public void initShapeDatas(Point centerPoint, Point startPoint,Point mPoint,
			Point endPoint) {
		addPoint(centerPoint);
		super.initShapeDatas(centerPoint, startPoint, mPoint, endPoint);
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
		
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
	}
	
}
