package com.modou.fragment;

import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.modou.loc.MainUIActivity;
import com.modou.loc.R;
import com.modou.loc.adapter.AroundWifiAdapter;
import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.loc.entity.WifiEntity;
import com.modou.loc.receiver.WifiReceiver;
import com.modou.utils.MLog;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class FeatureFragment extends BaseFragment {

	private View layerBtnOpenWifi;
	private View layerBtnOpenCarMode;
	private View layerContentWifi;
	private View layerNoWifiArea;
	private CheckBox checkBoxWifiState;
	private CheckBox checkBoxCarModeState;
	private ListView wifiListView;
	private AroundWifiAdapter mAdapter;
	private WifiReceiver wifiReceiver;
	private WifiManager mWifiMgr;
	
	private boolean isWifiOpen; // wifi是否打开
	private boolean isCarModeOpen; // 是否开启车载模式
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_feature, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}
	
	@Override
	public void onResume() {
		addReceiver();
		super.onResume();
	}

	private void addReceiver() {
		wifiReceiver = new WifiReceiver(getActivity()) {
			
			@Override
			public void scanResults(List<WifiEntity> result) {
				if (mAdapter == null) {
					mAdapter = new AroundWifiAdapter(getActivity(), result);
					wifiListView.setAdapter(mAdapter);
				} else {
					mAdapter.resetData(result);
				}
			}
		};
		
	}

	private void initView() {
		wifiListView = (ListView) getView().findViewById(R.id.listview_wifi);
		setLeftBtnImg(R.drawable.ic_sliding_menu);
		setRightBtnImg(R.drawable.icon_recommend);
		layerBtnOpenWifi = getView().findViewById(R.id.layer_btn_open_wifi);
		layerBtnOpenWifi.setOnClickListener(this);
		layerBtnOpenCarMode = getView().findViewById(R.id.layer_btn_open_car_mode);
		layerBtnOpenCarMode.setOnClickListener(this);
		layerContentWifi = getView().findViewById(R.id.layer_wifi_content);
		layerNoWifiArea = getView().findViewById(R.id.layer_no_wifi_area);
		checkBoxWifiState = (CheckBox) getView().findViewById(R.id.checkbox_wifi_open_state);
		checkBoxWifiState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isWifiOpen = isChecked;
				setCheckBoxWifiState(isWifiOpen);
				setWifiShowViewState(isWifiOpen);
			}
		});
		checkBoxCarModeState = (CheckBox) getView().findViewById(R.id.checkbox_car_mode_open_state);
		checkBoxCarModeState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isCarModeOpen = isChecked;
				setCheckBoxOpenModeState(isCarModeOpen);
			}
		});
	}
	
	private void initData() {
		mWifiMgr = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
		isWifiOpen = mWifiMgr.isWifiEnabled();	//获取当前wifi状态(开、关)
		
		// 目前修改为客户端启动时，检测是否开启wifi模块，如果没有开启，则强制开启。
		if (!isWifiOpen) {
			mWifiMgr.setWifiEnabled(true);
		}
		
		checkBoxWifiState.setChecked(isWifiOpen);
		
	}
	
	@Override
	public void onPause() {
		if (wifiReceiver != null) {
			getActivity().unregisterReceiver(wifiReceiver);
			mAdapter = null; // 销毁广播后，将数据适配器也释放掉
		}
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
		} else if (viewId == R.id.layer_btn_open_wifi) {
			isWifiOpen = !isWifiOpen;
			mWifiMgr.setWifiEnabled(isWifiOpen);
			setWifiShowViewState(isWifiOpen);
			setCheckBoxWifiState(isWifiOpen);
		} else if (viewId == R.id.layer_btn_open_car_mode) {
			isCarModeOpen = !isCarModeOpen;
			setCheckBoxOpenModeState(isCarModeOpen);
		} else if (viewId == R.id.btn_right) {
			//TODO 右侧按钮点击事件
		}
	}
	
	/**
	 * 设置wifi CheckBox组件的显示状态
	 * @param checked	是否选中
	 */
	private void setCheckBoxWifiState(boolean checked) {
		checkBoxWifiState.setChecked(checked);
	}
	
	/**
	 * 设置车载模式 CheckBox组件的显示状态
	 * @param checked	是否选中
	 */
	private void setCheckBoxOpenModeState(boolean checked) {
		checkBoxCarModeState.setChecked(checked);
		DataTransferMgr2.getInstance().setCarModeOpen(checked);
	}
	
	/**
	 * 根据wifi当前的状态，来显示或隐藏当前容器
	 * @param checked
	 */
	private void setWifiShowViewState(boolean checked) {
		if (checked) {
			layerContentWifi.setVisibility(View.VISIBLE);
			layerNoWifiArea.setVisibility(View.GONE);
		} else {
			layerContentWifi.setVisibility(View.GONE);
			layerNoWifiArea.setVisibility(View.VISIBLE);
		}
	}
	
}
