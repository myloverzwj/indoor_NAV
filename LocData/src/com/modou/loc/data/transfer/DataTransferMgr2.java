package com.modou.loc.data.transfer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.net.wifi.WifiManager;

import com.modou.loc.module.map2.Map;
import com.modou.loc.module.map2.Point;
import com.modou.loc.module.map2.ShowSurfaceView;
import com.modou.loc.module.wifi.WifiMgr;
import com.modou.loc.task.SensorDataTask;
import com.modou.utils.MLog;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-22 下午9:19:51
 * 类说明:
 * java层和C++层数据交换管理类
 * 该类存储定位所包含的数据数组
 */
public class DataTransferMgr2 {
	
	private Timer timer;
	private TimerTask task;
	
	/**调用C++类库频率*/
	public static final int DELAY_PER = 1000;
	
	/**wifi数据数组*/
	private ArrayList<WifiDataBean> wifiDataArrs = new ArrayList<WifiDataBean>();
	/**陀螺仪传感器数据数组*/
	private ArrayList<SensorDataBean> gyroscopeArrs = new ArrayList<SensorDataBean>();
	/**磁阻传感器数据数组*/
	private ArrayList<SensorDataBean> magneticArrs = new ArrayList<SensorDataBean>();
	/**加速度传感器数据数组*/
	private ArrayList<SensorDataBean> accelerometerArrs = new ArrayList<SensorDataBean>();

	
	private static DataTransferMgr2 instance;
	/**地图场景显示类*/
	private ShowSurfaceView showSurfaceView;
	/**是否开启车载模式*/
	private boolean isCarModeOpen;
	
	private DataTransferMgr2(){}
	
	/**
	 * 初始化函数
	 * @param mMap	地图场景显示类
	 */
	public void init(ShowSurfaceView surfaceView) {
		this.showSurfaceView = surfaceView;
		
		String fileName = showSurfaceView.getcurMapFilePath();
		WifiDataBean[] arr = WifiMgr.getInstance().getInitWifiData(showSurfaceView.getContext());
		initLoc(fileName, isCarModeOpen, arr);
	}
	
	/**开启用户行走路线轨迹定位任务*/
	public void start() {
		timer = new Timer();
		if (task != null) {
			task.cancel();
		}
		task = new DataTimerTask();
		timer.schedule(task, 0, DELAY_PER);
	}
	
	/**关闭用户行走路线轨迹定位任务*/
	public void cancel() {
		task.cancel();
		timer.cancel();
		MLog.d("数据采集定时任务关闭");
	}
	
	class DataTimerTask extends TimerTask {
		@Override
		public void run() {
			// 用户行为轨迹点
			dataAnalysis();
		}
	}
	
	
	/**
	 * 定位数据分析
	 */
	private void dataAnalysis() {
		WifiDataBean[] wifiTmpArr = getWifiCacheData();
		SensorDataBean[] gyroscopeTmpArr = getGyroscopeCacheData();
		SensorDataBean[] magneticTmpArr = getMagneticCacheData();
		SensorDataBean[] accelerometerTmpArr = getAccelerometerCacheData();
//		MLog.d("定位原数据 wifiData=" + wifiTmpArr.length + " ,gyroscopeData=" + gyroscopeTmpArr.length
//				+ " ,magneticData=" + magneticTmpArr.length + " ,accelerometerData=" + accelerometerTmpArr.length);
		Point[] points = getUserTrack(wifiTmpArr, 0, 0, gyroscopeTmpArr, 0, 0, 
				magneticTmpArr, 0, 0, accelerometerTmpArr, 0, 0);
		if (points != null) {
			showSurfaceView.updateUserTrack(points);
		}
	}
	
	
	/**
	 * 获取当前时钟频率下采集到的wifi数据
	 */
	private WifiDataBean[] getWifiCacheData() {
		WifiDataBean[] tempArr = null;
		
		synchronized (wifiDataArrs) {
			int size = wifiDataArrs.size();
			if (size == 0)
				return null;
			
			tempArr = new WifiDataBean[size];
			for (int i = 0; i < size; i++) {
				tempArr[i] = wifiDataArrs.get(i);
			}
			
			// 清空之前缓存的数据
			wifiDataArrs.clear();
		}
		return tempArr;
	}
	
	/**
	 * 获取当前时钟频率下采集到的陀螺仪传感器数据
	 */
	private SensorDataBean[] getGyroscopeCacheData() {
		SensorDataBean[] tempArr = null;
		
		synchronized (gyroscopeArrs) {
			int size = gyroscopeArrs.size();
			if (size == 0)
				return null;
			
			tempArr = new SensorDataBean[size];
			for (int i = 0; i < size; i++) {
				tempArr[i] = gyroscopeArrs.get(i);
			}
			
			// 清空之前缓存的数据
			gyroscopeArrs.clear();
		}
		return tempArr;
	}
	
	/**
	 * 获取当前时钟频率下采集到的磁阻传感器数据
	 */
	private SensorDataBean[] getMagneticCacheData() {
		SensorDataBean[] tempArr = null;
		
		synchronized (magneticArrs) {
			int size = magneticArrs.size();
			if (size == 0)
				return null;
			
			tempArr = new SensorDataBean[size];
			for (int i = 0; i < size; i++) {
				tempArr[i] = magneticArrs.get(i);
			}
			
			// 清空之前缓存的数据
			magneticArrs.clear();
		}
		return tempArr;
	}

	/**
	 * 获取当前时钟频率下采集到的加速度传感器数据
	 */
	private SensorDataBean[] getAccelerometerCacheData() {
		SensorDataBean[] tempArr = null;
		
		synchronized (accelerometerArrs) {
			int size = accelerometerArrs.size();
			if (size == 0)
				return null;
			
			tempArr = new SensorDataBean[size];
			for (int i = 0; i < size; i++) {
				tempArr[i] = accelerometerArrs.get(i);
			}
			
			// 清空之前缓存的数据
			accelerometerArrs.clear();
		}
		return tempArr;
	}
	
	/**
	 * 添加wifi数据实体类
	 * @param wifiBean
	 */
	public void addWifiData(WifiDataBean wifiBean) {
		if (wifiBean != null) {
//			MLog.d("添加的wifi数据:" + wifiBean.toString());
			wifiDataArrs.add(wifiBean);
		}
	}
	
	/**
	 * 添加陀螺仪sensor数据实体类
	 * @param sensorBean
	 */
	public synchronized void addGyroscopeSensorData(SensorDataBean sensorBean) {
		if (sensorBean != null) {
			synchronized (gyroscopeArrs) {
//				MLog.d("添加的陀螺仪传感器数据:" + sensorBean.toString());
				gyroscopeArrs.add(sensorBean);
			}
		}
	}
	
	/**
	 * 添加磁阻传感器数据实体类
	 * @param sensorBean
	 */
	public synchronized void addMagneticSensorData(SensorDataBean sensorBean) {
		if (sensorBean != null) {
			synchronized (magneticArrs) {
//				MLog.d("添加的磁阻传感器数据:" + sensorBean.toString());
				magneticArrs.add(sensorBean);
			}
		}
	}
	
	/**
	 * 添加加速度传感器数据实体类
	 * @param sensorBean
	 */
	public synchronized void addAccelerometerSensorData(SensorDataBean sensorBean) {
		if (sensorBean != null) {
			synchronized (accelerometerArrs) {
//				MLog.d("添加的速度传感器数据:" + sensorBean.toString());
				accelerometerArrs.add(sensorBean);
			}
		}
	}
	
	/**
	 * 添加传感器数据
	 */
	public void addSensorData(SensorDataBean sensorBean) {
		int type = sensorBean.getType();
		switch (type) {
		case Sensor.TYPE_GYROSCOPE:
			addGyroscopeSensorData(sensorBean);
			break;
		case Sensor.TYPE_ACCELEROMETER:
			addAccelerometerSensorData(sensorBean);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			addMagneticSensorData(sensorBean);
			break;
		}
	}
	
	/**
	 * 调用C++定位库初始化函数
	 * @param fileName			地图文件名称
	 * @param isVehicleMode		是否是车载模式
	 * @param arr				周边wifi ap信号源数据数组
	 */
	public native void initLoc(String fileName, boolean isVehicleMode, WifiDataBean[] arr);
	
	/**
	 * 调用C++用户行为轨迹定位函数
	 * @param wifiArr				wifi数据数组
	 * @param wifiStart				wifi数据读取起始位置
	 * @param wifiEnd				wifi数据读取结束位置
	 * @param gyroscopeArr			陀螺仪数据数组
	 * @param gyroscopeStart		陀螺仪数据读取起始位置
	 * @param gyroscopeEnd			陀螺仪数据读取结束位置
	 * @param magneticArr			磁阻传感器数据数组
	 * @param magneticStart			磁阻传感器数据读取起始位置
	 * @param magneticEnd			磁阻传感器数据读取结束位置
	 * @param accelerometerArr		加速度传感器数据数组
	 * @param accelerometerStart	加速度传感器数据读取起始位置
	 * @param accelerometerEnd		加速度传感器数据读取结束位置
	 */
	public native Point[] getUserTrack(WifiDataBean[] wifiArr, int wifiStart, int wifiEnd, 
									SensorDataBean[] gyroscopeArr, int gyroscopeStart, int gyroscopeEnd,
									SensorDataBean[] magneticArr, int magneticStart, int magneticEnd,
									SensorDataBean[] accelerometerArr, int accelerometerStart, int accelerometerEnd);
	
	public static DataTransferMgr2 getInstance() {
		if (instance == null) {
			instance = new DataTransferMgr2();
		}
		return instance;
	}

	static {
		System.loadLibrary("loc");
	}
	
	private void gc() {
		wifiDataArrs = null;
		gyroscopeArrs = null;
		magneticArrs = null;
		accelerometerArrs = null;
		
		instance = null;
	}
	
	public void setCarModeOpen(boolean flag) {
		isCarModeOpen = flag;
	}
}
