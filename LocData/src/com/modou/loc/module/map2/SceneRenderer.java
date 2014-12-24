package com.modou.loc.module.map2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.modou.loc.MapActivity;
import com.modou.loc.module.map2.config.CoordUtil;
import com.modou.loc.module.map2.config.MapConfig;
import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;
import com.modou.utils.PhoneInfoUtils;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:31:31
 * 类说明:
 * 场景渲染器
 */
public class SceneRenderer implements Renderer {

	private Context mContext;
	private Map mMap;
	
	Triangle triangle = null;
	
	public SceneRenderer(Context ctx) {
		mContext = ctx;
		MLog.d("SceneRenerer====================构造方法===============");
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//设置屏幕背景色RGBA
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);  
        MLog.d("SceneRender-----------------onSurfaceCreate---------------");
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //打开背面剪裁   
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        
        // 创建地图场景对象
        mMap = new Map(mContext, this);
        triangle = new Triangle();
        
        PhoneInfoUtils.parseScreenInfo(mContext);
        screenWidth = PhoneInfoUtils.getWidth();
		screenHeight = PhoneInfoUtils.getHeight();
		CoordUtil.init(screenWidth, screenHeight);
		
		MapConfig.getInstance().initMapData();
		mMap.initData();
		mMap.setScreenSize(screenWidth, screenHeight);
		
		Log.d("mylog", "screenW====" + screenWidth + " ,screenH===" + screenHeight);
		
		// 地图数据加载完毕后，启动定位任务
        ((MapActivity)mContext).startLoc();
        
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//设置视窗大小及位置 
    	GLES20.glViewport(0, 0, width, height);
    	//计算GLSurfaceView的宽高比
        Constant.ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
        MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
		// 调用此方法产生摄像机9参数位置矩阵
		MatrixState.setCamera(0, 3f, 20, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        
        //初始化变换矩阵
        MatrixState.setInitStack();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//清除深度缓冲与颜色缓冲
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        MatrixState.pushMatrix();
        MatrixState.translate(xOffset, yOffset, 0);
        MatrixState.scale(zoom, zoom, 0);
//        Log.d("mylog", "dx=====" + x + " ,dy===" + y);
//        triangle.draw(MatrixState.getFinalMatrix());
        // 只有加载完毕后才进行绘制
//        if(mMap.isLoadOver()) {
        	mMap.Draw(gl);
//        }
        MatrixState.popMatrix();
	}

	public void move(float dx, float dy) {
		xOffset = dx;
		yOffset = dy;
	}
	
	
	private float zoom = 1;
	private float xOffset = 0;
	private float yOffset = 0;
	private int screenWidth, screenHeight;
	public void MoveCamera(float x, float y) {
		xOffset += 2.0f * x / screenWidth;
		yOffset -= 2.0f * y / screenHeight;
//		Log.d("mylog", "xOffset=" + xOffset + " ,yOffset=" + yOffset);
	}

	public void zoomIn() {
		zoom -= 0.025;
		if (zoom <= 0.5)
			zoom = 0.5f;
	}

	public void zoomOut() {
		zoom += 0.025;
		if (zoom >= 1.5)
			zoom = 2;
	}

	/**
	 * 切换楼层
	 * @param floorIndex	楼层索引
	 */
	public void changeFloor(int floorIndex) {
		mMap.reloadMap(floorIndex);
	}
	
	/**
	 * 添加用户行为轨迹点
	 * @param p	用户行为轨迹点
	 */
	public void addUserTrack(Point p) {
		mMap.addUserTrack(p);
	}
	
	public void updateUserTrack(Point[] pts) {
		mMap.updateUserTrack(pts);
	}

	/**
	 * 获取当前地图文件名称
	 * @return	当前地图文件名称
	 */
	public String getCurMapFilePath() {
		return mMap.getCurMapFileName();
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String info = (String) msg.obj;
			MethodUtils.showToast(mContext, info);
		}
	};
	
	public void showMsg(String info) {
		Message msg = handler.obtainMessage();
		msg.obj = info;
		handler.sendMessage(msg);
	}
	
}
