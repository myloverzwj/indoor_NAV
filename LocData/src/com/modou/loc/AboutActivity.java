package com.modou.loc;

import com.modou.utils.MethodUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-14 下午5:11:13
 * 类说明:
 * 关于页面
 */
public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		initView();
		initData();
	}

	private void initView() {
		setTxtTitle(R.string.about_app);
		setLeftBtn(R.drawable.btn_back_bg, R.string.back);
	}

	private void initData() {
		TextView txtviewVersion = (TextView) findViewById(R.id.txtview_cur_version);
		String curVer = MethodUtils.getVerionName(getApplicationContext());
		txtviewVersion.setText(getString(R.string.cur_ver, curVer));
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		int viewId = v.getId();
		if (viewId == R.id.btn_left) {
			finish();
		}
	}
	
}
