package com.modou.loc.module.map2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class ShowSurfaceView extends GLSurfaceView {

	private SceneRenderer mRenderer;
	private float mPreviousX; // 上次的触控位置X坐标
	private float mPreviousY; // 上次的触控位置Y坐标
	
	private boolean multiTouch = false;
	private boolean touched = false;
	private float initialDistance;
	private final VectorF pinchVector = new VectorF();
	private float lastScale = 1.0f;
	private float maxScale = 5.0f;
	private float preScale = -1;
	
	public ShowSurfaceView(Context context) {
		super(context);
		init();
	}

	private void init() {
		this.setEGLContextClientVersion(2);
		mRenderer = new SceneRenderer(getContext());
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
		//TODO 将渲染模式调整为脏模式，在进行平移、放大、缩小，和更新点时来进行重绘，调用requestRender.这样应该比较减小cpu的使用
		setFocusableInTouchMode(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
        float x = event.getX();
        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	        	touched = true;
	        	Log.d("mylog", "按下事件");
	        	break;
	        case MotionEvent.ACTION_MOVE:
	        	if (event.getPointerCount() > 1) {
	        		Log.d("mylog", "多点==if===");
	        		multiTouch = true;
	        		if (initialDistance > 0) {
	        			pinchVector.set(event);
	        			pinchVector.calculateLength();
	        			
	        			float distance = pinchVector.length;
	        			if (initialDistance != distance) {
	        				float newScale = (distance / initialDistance) * lastScale;
	        				if (newScale <= maxScale) {
	        					if (preScale != -1) {
	        						if (newScale - preScale > 0) {	// 放大
	        							mRenderer.zoomOut();
	        						} else {	// 缩小
	        							mRenderer.zoomIn();
	        						}
	        					}
	        					preScale = newScale;
	        				}
	        			}
	        		} else {
	        			initialDistance = MathUtils.distance(event);
	        		}
	        	} else {
	        		if (!multiTouch) {
			            float dy = y - mPreviousY;//计算触控笔Y位移
			            float dx = x - mPreviousX;//计算触控笔X位移 
			            mRenderer.MoveCamera(dx, dy);
	        		}
		            multiTouch = false;
		            Log.d("mylog", "开始移动: x-===" + x + " ,y====" + y + " ,mPreX ===" + mPreviousX + " mPreY===" + mPreviousY);
	        	}
	            break;
	        case MotionEvent.ACTION_UP:
	        	multiTouch = false;
	        	preScale = -1;
	        	initialDistance = 0;
	        	Log.d("mylog", "action Up========");
	        	break;
        }
//        Log.d("mylog", "multiTouch===" + multiTouch + " ,event.getPointerCount()===" + event.getPointerCount());
        if (event.getPointerCount() == 1) {
	        mPreviousY = y;//记录触控笔位置
	        mPreviousX = x;//记录触控笔位置
	        Log.d("mylog", "触控笔位置 x = " + x + " ,y=" + y);
        } else {
        	Log.d("mylog", "多点触控模式");
        }
        return true;
	}

	/**
	 * 重新载入地图
	 */
	public void reLoadMap() {
		mRenderer.changeFloor(1);//TODO 暂时这样写，默认加载第一张地图
		requestRender();
	}

	/**
	 * 更改楼层信息
	 * @param floorIndex	楼层索引
	 */
	public void changeFloor(int floorIndex) {
		mRenderer.changeFloor(floorIndex);
		requestRender();
	}

	/**
	 * 添加用户行为轨迹点
	 * @param p	用户行为轨迹点
	 */
	public void addUserTrack(Point p) {
		mRenderer.addUserTrack(p);
	}
	
	public void updateUserTrack(Point[] pts) {
		mRenderer.updateUserTrack(pts);
	}
	
	/**
	 * 获取当前地图文件名称
	 * @return	当前地图文件名称
	 */
	public String getcurMapFilePath() {
		return mRenderer.getCurMapFilePath();
	}
	
}
