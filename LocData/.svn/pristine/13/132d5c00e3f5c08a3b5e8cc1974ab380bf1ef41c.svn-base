package com.modou.loc;

import com.modou.utils.MLog;
import com.modou.utils.StorageUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-14 下午5:12:20
 * 类说明:
 * 应用设置页面
 */
public class AppSetActivity extends BaseActivity {

	private View vPanelStatusBar, vPanelAppVersion, vPanelAcceptNews;
	private CheckBox cBoxStatusBar, cBoxAppVersion, cBoxAcceptNews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appset);
		
		initView();
		initData();
	}

	private void initView() {
		setTxtTitle(R.string.app_set);
		setLeftBtn(R.drawable.btn_back_bg, R.string.back);
		
		vPanelStatusBar = findViewById(R.id.layer_status_bar);
		vPanelStatusBar.setOnClickListener(this);
		vPanelAppVersion = findViewById(R.id.layer_app_version);
		vPanelAppVersion.setOnClickListener(this);
		vPanelAcceptNews = findViewById(R.id.layer_accept_news);
		vPanelAcceptNews.setOnClickListener(this);
		
		cBoxStatusBar = (CheckBox) findViewById(R.id.cbox_status_bar);
		cBoxStatusBar.setOnCheckedChangeListener(checkBoxChangeListener);
		cBoxAppVersion = (CheckBox) findViewById(R.id.cbox_app_version);
		cBoxAppVersion.setOnCheckedChangeListener(checkBoxChangeListener);
		cBoxAcceptNews = (CheckBox) findViewById(R.id.cbox_accept_news);
		cBoxAcceptNews.setOnCheckedChangeListener(checkBoxChangeListener);
	}

	private void initData() {
		// 应用启动，初始化加载当前设置项的状态
		boolean isShowStatusBar = StorageUtil.isShowStatusBarInfo(getApplicationContext());
		cBoxStatusBar.setChecked(isShowStatusBar);
		boolean isAutoUpdateVer = StorageUtil.isAutoUpdateVersion(getApplicationContext());
		cBoxAppVersion.setChecked(isAutoUpdateVer);
		boolean isAcceptNews = StorageUtil.isAcceptNews(getApplicationContext());
		cBoxAcceptNews.setChecked(isAcceptNews);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == vPanelStatusBar) {
			cBoxStatusBar.setChecked(!cBoxStatusBar.isChecked());
		} else if (v == vPanelAppVersion) {
			cBoxAppVersion.setChecked(!cBoxAppVersion.isChecked());
		}else if (v == vPanelAcceptNews) {
			cBoxAcceptNews.setChecked(!cBoxAcceptNews.isChecked());
		} else if (v.getId() == R.id.btn_left) {
			finish();
		}
	}
	
	private OnCheckedChangeListener checkBoxChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// 保存应用设置的状态
			if (buttonView == cBoxStatusBar) {
				StorageUtil.setStatusBarInfo(getApplicationContext(), isChecked);
			} else if (buttonView == cBoxAppVersion) {
				StorageUtil.setAutoUpdateVersion(getApplicationContext(), isChecked);
			} else if (buttonView == cBoxAcceptNews) {
				StorageUtil.setAcceptNews(getApplicationContext(), isChecked);
			}
		}
	};
	
}
