package com.modou.loc;

import com.modou.loc.adapter.GuideAdapter;
import com.modou.loc.adapter.GuideAdapter.GuideClickListener;
import com.modou.utils.MethodUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

public class GuideActivity extends Activity implements GuideClickListener {

	private final int[] resImgs = {R.drawable.use_guide_1, R.drawable.use_guide_2, 0};
	
	private GuideAdapter guideAdapter;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		initView();
	}

	private void initView() {
		guideAdapter = new GuideAdapter(this, resImgs);
		guideAdapter.setGuidItemClickListener(this);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_guide);
		mViewPager.setAdapter(guideAdapter);
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		if (viewId == R.id.btn_start) {
			setResult(Activity.RESULT_OK);
			this.finish();
		} else if (viewId == R.id.btn_share) {
			String msg = "引导页的分享语言，应该也从server端获取";//TODO 未添加分享语言， 引导页的分享语言，应该也从server端获取
//			MethodUtils.share(getApplicationContext(), msg);
		}
	}
	
}
