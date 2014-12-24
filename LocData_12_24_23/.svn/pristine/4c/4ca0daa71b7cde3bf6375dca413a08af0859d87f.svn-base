package com.modou.fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.modou.loc.R;
import com.modou.utils.AMapUtil;
import com.modou.utils.MLog;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonalFragment extends BaseFragment implements AMapLocationListener, OnGeocodeSearchListener {

	private ProgressDialog progDialog = null;
	private LatLonPoint latLonPoint;
	private LocationManagerProxy locationManager;
	private GeocodeSearch geocoderSearch;
	private String addressName;
	private AMap aMap;
	private UiSettings mUiSettings;
	private MapView mapView;
	private Marker geoMarker;
	private Marker regeoMarker;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_personal, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView(savedInstanceState);
		initData();
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView(Bundle savedInstanceState) {
		mapView = (MapView) getView().findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			mUiSettings.setScaleControlsEnabled(true);
			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1.0f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1.0f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		}
		geocoderSearch = new GeocodeSearch(getActivity());
		geocoderSearch.setOnGeocodeSearchListener(this);
	}

	private void initData() {
		locationManager = LocationManagerProxy.getInstance(getActivity());
		// API定位采用GPS定位方式，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		locationManager.requestLocationUpdates(
				LocationManagerProxy.GPS_PROVIDER, 2000, 10, this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		mapView.onDestroy();
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destory();
		}
		locationManager = null;
		super.onDestroy();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double lat = location.getLatitude();
			Double lng = location.getLongitude();
			MLog.d("获取到的经度：" + lng + " ,纬度：" + lat);

			latLonPoint = new LatLonPoint(lat, lng);
			getAddress(latLonPoint);
		}
	}

	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 逆地理编码回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						AMapUtil.convertToLatLng(latLonPoint), 15));
				regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
			}
		}
	}
	
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	
	/**
	 * 显示进度条对话框
	 */
	public void showDialog() {
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
	}

	/**
	 * 隐藏进度条对话框
	 */
	public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
}
