package com.modou.fragment;

import com.modou.loc.AboutActivity;
import com.modou.loc.AppSetActivity;
import com.modou.loc.FeedBackActivity;
import com.modou.loc.R;
import com.modou.utils.MethodUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreFragment extends BaseFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_more, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
//		setLeftBtnImg(R.drawable.icon_recommend);
		setTxtTitle(R.string.more);
		
		getView().findViewById(R.id.layer_app_set).setOnClickListener(this);
		getView().findViewById(R.id.layer_invite_friends).setOnClickListener(this);
		getView().findViewById(R.id.layer_check_new_app_version).setOnClickListener(this);
		getView().findViewById(R.id.layer_feedback).setOnClickListener(this);
		getView().findViewById(R.id.layer_grade_to_me).setOnClickListener(this);
		getView().findViewById(R.id.layer_attention_weixin).setOnClickListener(this);
		getView().findViewById(R.id.layer_about_app).setOnClickListener(this);
		
	}

	private void initData() {
		// 微信号
		String weixinNum = "中国合伙人";	//TODO 暂时写死
		TextView txtViewWeixinNum = (TextView) getView().findViewById(R.id.txtview_weixin_num);
		txtViewWeixinNum.setText(getString(R.string.weixin_num, weixinNum));
		// 设置版本号
		String verNum = MethodUtils.getVerionName(getActivity());
		TextView txtViewVer = (TextView) getView().findViewById(R.id.txtview_version_name);
		txtViewVer.setText(verNum);
	}
	
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.layer_app_set:
			startActivity(new Intent(getActivity(), AppSetActivity.class));
			break;
		case R.id.layer_invite_friends:
			MethodUtils.share(getActivity(), "新型智能定位终端，为您快速定位.");
			break;
		case R.id.layer_check_new_app_version:
			break;
		case R.id.layer_feedback:
			startActivity(new Intent(getActivity(), FeedBackActivity.class));
			break;
		case R.id.layer_grade_to_me:
			break;
		case R.id.layer_attention_weixin:
			break;
		case R.id.layer_about_app:
			startActivity(new Intent(getActivity(), AboutActivity.class));
			break;
		}
	}
	
}
