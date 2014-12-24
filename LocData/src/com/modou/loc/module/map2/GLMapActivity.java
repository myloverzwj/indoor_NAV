package com.modou.loc.module.map2;

import com.modou.loc.module.map2.config.MapConfig;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GLMapActivity extends Activity {

	private ShowSurfaceView mSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSurfaceView = new ShowSurfaceView(this);
		setContentView(mSurfaceView);
		
		init();
	}

	private void init() {
		//TODO 测试数据
		
		// 地图信息初始化，应用启动一定先调用init方法
		MapConfig.getInstance().init(getApplicationContext());
		
		
	}
	
}
