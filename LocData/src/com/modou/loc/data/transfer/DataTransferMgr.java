//package com.modou.loc.data.transfer;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.hardware.Sensor;
//
//import com.modou.loc.module.map2.Map;
//import com.modou.loc.module.map2.Point;
//import com.modou.loc.module.map2.ShowSurfaceView;
//import com.modou.loc.task.SensorDataTask;
//import com.modou.utils.MLog;
//
///**
// * @author 作者 E-mail: xyylchq@163.com
// * @version 创建时间: 2014-8-22 下午9:19:51
// * 类说明:
// * java层和C++层数据交换管理类
// * 该类存储定位所包含的数据数组
// */
//public class DataTransferMgr {
//	
//	private Timer timer;
//	
//	/**调用C++类库频率*/
//	public static final int DELAY_PER = 1000;
//	
//	/**WIFI数组最大容量*/
//	private int WIFI_ARR_SIZE = 1000;//TODO 和定时器成比例
//	/**传感器数组最大容量*/
//	private int SENSOR_ARR_SIZE = 2000;
//	/**WIFI区间范围(区间范围等于读数据频率和写数据频率的比值)*/
//	private int wifiRegionSize = DELAY_PER / SensorDataTask.DELAY_PER;
//	/**Sensor区间范围(区间范围等于读数据频率和写数据频率的比值)*/
//	private int sensorRegionSize = DELAY_PER / SensorDataTask.DELAY_PER;
//	/**Wifi存储区域大小*/
//	private int wifiBlockSize = 1000;
//	/**Sensor存储区域大小*/
//	private int sensorBlockSize = 1000;
//	/**wifi当前存储位置索引*/
//	private int wifiCurIndex = 0;
//	/**陀螺仪传感器存储位置索引*/
//	private int gyroscopeSensorCurIndex = 0;
//	/**磁阻传感器存储位置索引*/
//	private int magneticSensorCurIndex = 0;
//	/**加速度传感器存储位置索引*/
//	private int accelerometerSensorCurIndex = 0;
//	/**wifi读取位置索引*/
//	private int wifiReadStart = 0;
//	/**Sensor读取位置索引*/
//	private int sensorReadStart = 0;
//	/**wifi读取结束位置索引*/
//	private int wifiReadEnd = wifiRegionSize;
//	/**Sensor读取结束位置索引*/
//	private int sensorReadEnd = sensorRegionSize;
//	
//	/**wifi数据数组*/
//	private WifiDataBean[] wifiDataArrs = new WifiDataBean[WIFI_ARR_SIZE];
//	/**陀螺仪传感器数据数组*/
//	private SensorDataBean[] gyroscopeArrs = new SensorDataBean[SENSOR_ARR_SIZE];
//	/**磁阻传感器数据数组*/
//	private SensorDataBean[] magneticArrs = new SensorDataBean[SENSOR_ARR_SIZE];
//	/**加速度传感器数据数组*/
//	private SensorDataBean[] accelerometerArrs = new SensorDataBean[SENSOR_ARR_SIZE];
//
//	
//	private static DataTransferMgr instance;
//	/**地图场景显示类*/
//	private ShowSurfaceView showSurfaceView;
//	
//	private DataTransferMgr(){}
//	
//	/**
//	 * 初始化函数
//	 * @param mMap	地图场景显示类
//	 */
//	public void init(ShowSurfaceView surfaceView) {
//		this.showSurfaceView = surfaceView;
//		
//		String fileName = "filename";
//		boolean isVehicleMode = false;
//		WifiDataBean[] arr = new WifiDataBean[2];
//		initLoc(fileName, isVehicleMode, arr);
//	}
//	
//	/**开启用户行走路线轨迹定位任务*/
//	public void start() {
//		timer = new Timer();
//		timer.schedule(task, 0, DELAY_PER);
//	}
//	
//	/**关闭用户行走路线轨迹定位任务*/
//	public void cancel() {
//		timer.cancel();
//		task.cancel();
//	}
//	
//	final TimerTask task = new TimerTask() {
//		@Override
//		public void run() {
//			// 用户行为轨迹点
//			dataAnalysis();
//			
//			// 计算读取的游标位置
//			calculateWifiCursor();
//			calculateSensorCursor();
//		}
//		
//	};
//	
//	/**
//	 * 计算wifi读取的游标位置
//	 */
//	private void calculateWifiCursor() {
//		wifiReadStart = wifiReadEnd;
//		if (wifiBlockSize - wifiReadEnd <= wifiRegionSize) {
//			wifiReadEnd = wifiRegionSize - (wifiBlockSize - wifiReadEnd);
//		} else {
//			wifiReadEnd += wifiRegionSize;
//		}
//	}
//	
//	/**
//	 * 计算Sensor读取的游标位置
//	 */
//	private void calculateSensorCursor() {
//		sensorReadStart = sensorReadEnd;
//		if (sensorBlockSize - sensorReadEnd <= sensorRegionSize) {
//			sensorReadEnd = sensorRegionSize - (sensorBlockSize - sensorReadEnd);
//		} else {
//			sensorReadEnd += sensorRegionSize;
//		}
//	}
//	
//	/**
//	 * 定位数据分析
//	 */
//	private void dataAnalysis() {
//		WifiDataBean[] wifiTmpArr = /*getWifiCacheData()*/ new WifiDataBean[1];
//		SensorDataBean[] gyroscopeTmpArr = /*getGyroscopeCacheData()*/ new SensorDataBean[1];
//		SensorDataBean[] magneticTmpArr = /*getMagneticCacheData()*/ new SensorDataBean[1];
//		SensorDataBean[] accelerometerTmpArr = /*getAccelerometerCacheData()*/ new SensorDataBean[1];
//		Point[] points = getUserTrack(wifiTmpArr, wifiReadStart, wifiReadEnd, gyroscopeTmpArr, sensorReadStart, sensorReadEnd, 
//				magneticTmpArr, sensorReadStart, sensorReadEnd, accelerometerTmpArr, sensorReadStart, sensorReadEnd);
//		if (points != null) {
//			int len = points.length;
//			Point p = null;
//			for (int i = 0;  i< len; i++) {
//				p = points[i];
////				MLog.d("C层返回的定位点P================.toString=" + p.toString());
//				showSurfaceView.addUserTrack(p);
//			}
//		}
//	}
//	
//	/**
//	 * 获取当前时钟频率下采集到的wifi数据
//	 */
//	private WifiDataBean[] getWifiCacheData() {
//		WifiDataBean[] tempArr = new WifiDataBean[wifiRegionSize];
//		int index = 0;
//		
//		if (wifiReadStart > wifiReadEnd) {//表明到达了队列尾部
//			
//			if (wifiReadEnd == 0) {
//				// 正好到达队尾的情况下
//				int end = wifiBlockSize;
//				for (int i = wifiReadStart; i < end; i++) {
//					tempArr[index++] = wifiDataArrs[i];
//				}
//			} else {
//				// 队尾
//				for (int i = wifiReadStart; i < wifiBlockSize; i++) {
//					tempArr[index++] = wifiDataArrs[i];
//				}
//				// 队头
//				for (int i = 0; i < wifiReadEnd; i++) {
//					tempArr[index++] = wifiDataArrs[i];
//				}
//			}
//		} else {
//			for (int i = wifiReadStart; i < wifiReadEnd; i++) {
//				tempArr[index++] = wifiDataArrs[i];
//			}
//		}
//		
//		return tempArr;
//	}
//	
//	/**
//	 * 获取当前时钟频率下采集到的陀螺仪传感器数据
//	 */
//	private SensorDataBean[] getGyroscopeCacheData() {
//		SensorDataBean[] tempArr = new SensorDataBean[sensorRegionSize];
//		int index = 0;
//		
//		if (sensorReadStart > sensorReadEnd) {//表明到达了队列尾部
//			
//			if (sensorReadEnd == 0) {
//				// 正好到达队尾的情况下
//				int end = sensorBlockSize;
//				for (int i = sensorReadStart; i < end; i++) {
//					if (gyroscopeArrs[i] == null) {
////						MLog.e("=============1111111111===========" + gyroscopeArrs[i]);
//					} else
//						tempArr[index++] = gyroscopeArrs[i];
//				}
//			} else {
//				// 队尾
//				for (int i = sensorReadStart; i < sensorBlockSize; i++) {
//					if (gyroscopeArrs[i] == null) {
////						MLog.e("=============22222222===========" + gyroscopeArrs[i]);
//					} else
//						tempArr[index++] = gyroscopeArrs[i];
//				}
//				// 队头
//				for (int i = 0; i < sensorReadEnd; i++) {
//					if (gyroscopeArrs[i] == null) {
////						MLog.e("=============3333333===========" + gyroscopeArrs[i]);
//					}else
//						tempArr[index++] = gyroscopeArrs[i];
//				}
//			}
//		} else {
//			for (int i = sensorReadStart; i < sensorReadEnd; i++) {
//				if (gyroscopeArrs[i] == null) {
////					MLog.e("=============44444444===========" + gyroscopeArrs[i] + " i=" + i + " ,sensorStart=" + sensorReadStart + " ,sensorReadEnd=" + sensorReadEnd);
//				}else
//					tempArr[index++] = gyroscopeArrs[i];
//			}
//		}
//		
//		return tempArr;
//	}
//	
//	
//	/**
//	 * 获取当前时钟频率下采集到的磁阻传感器数据
//	 */
//	private SensorDataBean[] getMagneticCacheData() {
//		SensorDataBean[] tempArr = new SensorDataBean[sensorRegionSize];
//		int index = 0;
//		
//		if (sensorReadStart > sensorReadEnd) {//表明到达了队列尾部
//			
//			if (sensorReadEnd == 0) {
//				// 正好到达队尾的情况下
//				int end = sensorBlockSize;
//				for (int i = sensorReadStart; i < end; i++) {
//					if (magneticArrs[i] == null) {
////						MLog.e("========getMagneticCacheData=====11111===========" + magneticArrs[i]);
//					} else {
//						tempArr[index++] = magneticArrs[i];
//					}
//				}
//			} else {
//				// 队尾
//				for (int i = sensorReadStart; i < sensorBlockSize; i++) {
//					if (magneticArrs[i] == null) {
////						MLog.e("========getMagneticCacheData=====2222===========" + magneticArrs[i]);
//					}else
//						tempArr[index++] = magneticArrs[i];
//				}
//				// 队头
//				for (int i = 0; i < sensorReadEnd; i++) {
//					if (magneticArrs[i] == null) {
////						MLog.e("========getMagneticCacheData=====3333===========" + magneticArrs[i]);
//					}else
//						tempArr[index++] = magneticArrs[i];
//				}
//			}
//		} else {
//			for (int i = sensorReadStart; i < sensorReadEnd; i++) {
//				if (magneticArrs[i] == null){
////					MLog.e("========getMagneticCacheData=====44444===========" + magneticArrs[i]);
//				}else
//					tempArr[index++] = magneticArrs[i];
//			}
//		}
//		
//		return tempArr;
//	}
//	
//	/**
//	 * 获取当前时钟频率下采集到的加速度传感器数据
//	 */
//	private SensorDataBean[] getAccelerometerCacheData() {
//		SensorDataBean[] tempArr = new SensorDataBean[sensorRegionSize];
//		int index = 0;
//		
//		if (sensorReadStart > sensorReadEnd) {//表明到达了队列尾部
//			
//			if (sensorReadEnd == 0) {
//				// 正好到达队尾的情况下
//				int end = sensorBlockSize;
//				for (int i = sensorReadStart; i < end; i++) {
//					if (accelerometerArrs[i] == null) {
////						MLog.e("========getAccelerometerCacheData=====11111===========" + accelerometerArrs[i]);
//					}else
//						tempArr[index++] = accelerometerArrs[i];
//				}
//			} else {
//				// 队尾
//				for (int i = sensorReadStart; i < sensorBlockSize; i++) {
//					if (accelerometerArrs[i] == null) {
////						MLog.e("========getAccelerometerCacheData=====22222===========" + accelerometerArrs[i]);
//					}else
//						tempArr[index++] = accelerometerArrs[i];
//				}
//				// 队头
//				for (int i = 0; i < sensorReadEnd; i++) {
//					if (accelerometerArrs[i] == null) {
////						MLog.e("========getAccelerometerCacheData=====3333333===========" + accelerometerArrs[i]);
//					}else
//						tempArr[index++] = accelerometerArrs[i];
//				}
//			}
//		} else {
//			for (int i = sensorReadStart; i < sensorReadEnd; i++) {
//				if (accelerometerArrs[i] == null){
////					MLog.e("========getAccelerometerCacheData=====444444===========" + accelerometerArrs[i]);
//				}else
//					tempArr[index++] = accelerometerArrs[i];
//			}
//		}
//		
//		return tempArr;
//	}
//	
//	/**
//	 * 添加wifi数据实体类
//	 * @param wifiBean
//	 */
//	public synchronized void addWifiData(WifiDataBean wifiBean) {
//		if (wifiCurIndex >= wifiBlockSize) {
//			wifiCurIndex = 0;
//		}
////		MLog.d("添加的wifi数据:" + wifiBean.toString());
//		wifiDataArrs[wifiCurIndex++] = wifiBean;
//	}
//	
//	/**
//	 * 添加陀螺仪sensor数据实体类
//	 * @param sensorBean
//	 */
//	public synchronized void addGyroscopeSensorData(SensorDataBean sensorBean) {
//		if (gyroscopeSensorCurIndex >= sensorBlockSize) {
//			gyroscopeSensorCurIndex = 0;
//		}
////		MLog.d("添加的陀螺仪传感器数据:" + sensorBean.toString());
//		gyroscopeArrs[gyroscopeSensorCurIndex++] = sensorBean;
//	}
//	
//	/**
//	 * 添加磁阻传感器数据实体类
//	 * @param sensorBean
//	 */
//	public synchronized void addMagneticSensorData(SensorDataBean sensorBean) {
//		if (magneticSensorCurIndex >= sensorBlockSize) {
//			magneticSensorCurIndex = 0;
//		}
////		MLog.d("添加的磁阻传感器数据:" + sensorBean.toString());
//		magneticArrs[magneticSensorCurIndex++] = sensorBean;
//	}
//	
//	/**
//	 * 添加加速度传感器数据实体类
//	 * @param sensorBean
//	 */
//	public synchronized void addAccelerometerSensorData(SensorDataBean sensorBean) {
//		if (accelerometerSensorCurIndex >= sensorBlockSize) {
//			accelerometerSensorCurIndex = 0;
//		}
////		MLog.d("添加的速度传感器数据:" + sensorBean.toString());
//		accelerometerArrs[accelerometerSensorCurIndex++] = sensorBean;
//	}
//	
//	/**
//	 * 添加传感器数据
//	 */
//	public void addSensorData(SensorDataBean sensorBean) {
//		int type = sensorBean.getType();
//		switch (type) {
//		case Sensor.TYPE_GYROSCOPE:
//			addGyroscopeSensorData(sensorBean);
//			break;
//		case Sensor.TYPE_ACCELEROMETER:
//			addAccelerometerSensorData(sensorBean);
//			break;
//		case Sensor.TYPE_MAGNETIC_FIELD:
//			addMagneticSensorData(sensorBean);
//			break;
//		}
//	}
//	
//	/**
//	 * 调用C++定位库初始化函数
//	 * @param fileName			地图文件名称
//	 * @param isVehicleMode		是否是车载模式
//	 * @param arr				周边wifi ap信号源数据数组
//	 */
//	public native void initLoc(String fileName, boolean isVehicleMode, WifiDataBean[] arr);
//	
//	/**
//	 * 调用C++用户行为轨迹定位函数
//	 * @param wifiArr				wifi数据数组
//	 * @param wifiStart				wifi数据读取起始位置
//	 * @param wifiEnd				wifi数据读取结束位置
//	 * @param gyroscopeArr			陀螺仪数据数组
//	 * @param gyroscopeStart		陀螺仪数据读取起始位置
//	 * @param gyroscopeEnd			陀螺仪数据读取结束位置
//	 * @param magneticArr			磁阻传感器数据数组
//	 * @param magneticStart			磁阻传感器数据读取起始位置
//	 * @param magneticEnd			磁阻传感器数据读取结束位置
//	 * @param accelerometerArr		加速度传感器数据数组
//	 * @param accelerometerStart	加速度传感器数据读取起始位置
//	 * @param accelerometerEnd		加速度传感器数据读取结束位置
//	 */
//	public native Point[] getUserTrack(WifiDataBean[] wifiArr, int wifiStart, int wifiEnd, 
//									SensorDataBean[] gyroscopeArr, int gyroscopeStart, int gyroscopeEnd,
//									SensorDataBean[] magneticArr, int magneticStart, int magneticEnd,
//									SensorDataBean[] accelerometerArr, int accelerometerStart, int accelerometerEnd);
//	
//	public static DataTransferMgr getInstance() {
//		if (instance == null) {
//			instance = new DataTransferMgr();
//		}
//		return instance;
//	}
//
//	static {
//		System.loadLibrary("loc");
//	}
//	
//	private void gc() {
//		wifiDataArrs = null;
//		gyroscopeArrs = null;
//		magneticArrs = null;
//		accelerometerArrs = null;
//		
//		instance = null;
//	}
//	
//}
