package com.modou.loc.module.map2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:51:40
 * 类说明:
 * 绘制折线、直线图形
 */
public class ShapeFoldLine extends GraphicsObject {

	public ShapeFoldLine(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
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
		
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		
		// 顶点颜色值数组，每个顶点4个色彩值RGBA
		float[] colors = new float[] {
//			0, 0, 1, 0, // 黄
//			0, 1, 0, 0, // 红
//			1, 1, 0, 0, // 黄
//			1, 0, 0, 0 // 红
				
			0.63671875f, 0.76953125f, 0.22265625f, 0.0f
		};
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
						4*4, mColorBuffer);
//		GLES20.glUniform4fv(maColorHandle, 1, colors, 0);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maColorHandle);
		
		GLES20.glLineWidth(5);//设置线的宽度
		
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
		
	}

}
