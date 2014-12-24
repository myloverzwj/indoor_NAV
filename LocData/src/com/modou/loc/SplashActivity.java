package com.modou.loc;

import com.modou.loc.db.DBDao;
import com.modou.loc.db.DBMgr;
import com.modou.utils.Const;
import com.modou.utils.StorageUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-3 下午3:46:55
 * 类说明:
 * 应用启动数据加载页面
 */
public class SplashActivity extends BaseActivity {

	final long startTime = System.currentTimeMillis();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		checkGuideState();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		switch (requestCode) {
		case Const.GUIDE_ACTIVITY_REQUEST_CODE:
			initData();
			break;
		}
	}
	
	private void checkGuideState() {
		boolean isFirstStart = StorageUtil.isFirstAppStart(getApplicationContext());
		if (isFirstStart) {
			StorageUtil.setFirstAppStart(getApplicationContext(), false);
			
			Intent intent = new Intent(this, GuideActivity.class);
			startActivityForResult(intent, Const.GUIDE_ACTIVITY_REQUEST_CODE);
		} else {
			initData();
		}
	}

	private void initData() {
		
		// 数据加载任务类
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// 数据库初始化
				DBDao.init(getApplicationContext());
				return null;
			}
			
			protected void onPostExecute(Void result) {
				loadData();
			}
			
		}.execute();
	}

	private void loadData() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// 获取本地已下载商场信息列表
				DBMgr.getInstance().initShopDatas();
				
				return null;
			}
			
			protected void onPostExecute(Void result) {
				long useTime = System.currentTimeMillis() - startTime;
				long delayMillis = 2000;
				delayMillis = delayMillis < useTime ? 0 : delayMillis - useTime;
				mHandler.sendEmptyMessageDelayed(0, delayMillis);
			}
			
		}.execute();
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			showContentLayer();
		}
	};
	
	private void showContentLayer() {
		Intent intent = new Intent(SplashActivity.this, MainUIActivity.class);
		startActivity(intent);
		finish();
	}
}
