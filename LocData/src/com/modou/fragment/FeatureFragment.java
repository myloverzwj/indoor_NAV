package com.modou.fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.modou.loc.BuildSelActivity;
import com.modou.loc.CitySelActivity;
import com.modou.loc.HotPointActivity;
import com.modou.loc.MainUIActivity;
import com.modou.loc.R;
import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.utils.StorageUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class FeatureFragment extends BaseFragment {

	private View layerBtnOpenCarMode;
	private CheckBox checkBoxCarModeState;
	
	private View layerCitySel;
	private View layerHotPointType;
	private TextView txtViewCitySel;
	private TextView txtViewHotPointSel;
	
	private boolean isWifiOpen; // wifi是否打开
	private boolean isCarModeOpen; // 是否开启车载模式
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_build_type_sel, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setLeftBtnImg(R.drawable.ic_sliding_menu);
		setRightBtnImg(R.drawable.icon_recommend);
		setTxtTitle(R.string.app_name);
		layerBtnOpenCarMode = getView().findViewById(R.id.layer_btn_open_car_mode);
		layerBtnOpenCarMode.setOnClickListener(this);
		checkBoxCarModeState = (CheckBox) getView().findViewById(R.id.checkbox_car_mode_open_state);
		checkBoxCarModeState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isCarModeOpen = isChecked;
				setCheckBoxOpenModeState(isCarModeOpen);
			}
		});
		layerCitySel = getView().findViewById(R.id.layer_city_sel);
		layerCitySel.setOnClickListener(this);
		layerHotPointType = getView().findViewById(R.id.layer_hotline_sel);
		layerHotPointType.setOnClickListener(this);
		getView().findViewById(R.id.txtview_city_title).setOnClickListener(this);
		getView().findViewById(R.id.txtview_city_sel_name).setOnClickListener(this);
		getView().findViewById(R.id.txtview_hotbuild_title).setOnClickListener(this);
		getView().findViewById(R.id.txtview_hotpoint_sel_name).setOnClickListener(this);
		// 设置自定义字体
		Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/ultralight.ttf");
		txtViewCitySel = (TextView) getView().findViewById(R.id.txtview_city_sel_name);
		txtViewCitySel.setTypeface(fontFace);
		txtViewHotPointSel = (TextView) getView().findViewById(R.id.txtview_hotpoint_sel_name);
		txtViewHotPointSel.setTypeface(fontFace);
	}
	
	private void initData() {
		WifiManager mWifiMgr = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
		isWifiOpen = mWifiMgr.isWifiEnabled();	//获取当前wifi状态(开、关)
		
		// 目前修改为客户端启动时，检测是否开启wifi模块，如果没有开启，则强制开启。
		if (!isWifiOpen) {
			mWifiMgr.setWifiEnabled(true);
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		String citySel = StorageUtil.getCitySel(getActivity());
		txtViewCitySel.setText(citySel);
		String hotPointSel = StorageUtil.getHotBuild(getActivity());
		txtViewHotPointSel.setText(hotPointSel);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		int viewId = v.getId();
		if (viewId == R.id.btn_left) {
			MainUIActivity mainAty = (MainUIActivity) getActivity();
			SlidingMenu slidingMenu = mainAty.getSlidingMenu();
			slidingMenu.showMenu(true);
		} else if (viewId == R.id.layer_btn_open_car_mode) {
			isCarModeOpen = !isCarModeOpen;
			setCheckBoxOpenModeState(isCarModeOpen);
		} else if (viewId == R.id.layer_city_sel || viewId == R.id.txtview_city_title || viewId == R.id.txtview_city_sel_name) {
			Intent intent = new Intent(getActivity(), CitySelActivity.class);
			startActivity(intent);
		} else if (viewId == R.id.layer_hotline_sel || viewId == R.id.txtview_hotbuild_title || viewId == R.id.txtview_hotpoint_sel_name) {
			Intent intent = new Intent(getActivity(), HotPointActivity.class);
			startActivity(intent);
		} else if (viewId == R.id.btn_right) {
			//TODO 右侧按钮点击事件
		}
		
	}
	
	/**
	 * 设置车载模式 CheckBox组件的显示状态
	 * @param checked	是否选中
	 */
	private void setCheckBoxOpenModeState(boolean checked) {
		checkBoxCarModeState.setChecked(checked);
		DataTransferMgr2.getInstance().setCarModeOpen(checked);
	}
	
}
