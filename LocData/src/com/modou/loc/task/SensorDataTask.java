package com.modou.loc.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import com.modou.loc.module.sensor.SensorMgr;
import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;
import com.modou.utils.StorageUtil;

import android.content.Context;

public class SensorDataTask {

	private Context mContext;
	
	private String fileName = "sensor_loc_data_";
	private File file;
	private BufferedWriter bw = null;
	private BufferedReader br = null;

	private Timer timer;
	private TimerTask task;
	public static final int DELAY_PER = 10;// 100Hz发送一次
	private boolean isCancel;
	
	public SensorDataTask (Context ctx) {
		this.mContext = ctx;
		/*try {
			initFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	}
	
	public void start() {
		isCancel = false;
		SensorMgr.getInstance().init(mContext);
		
		timer = new Timer();
		if (task != null) {
			task.cancel();
		}
		task = new SensorTimerTask();
		timer.schedule(task, 0, DELAY_PER);
	}

	public void cancel() {
		isCancel = true;
		SensorMgr.getInstance().gc();
		
		if (timer != null) {
			timer.cancel();
		}
		if (task != null) {
			task.cancel();
		}
		
		gc();
	}

	class SensorTimerTask extends TimerTask {
		@Override
		public void run() {
			if (isCancel)
				return;
			SensorMgr.getInstance().addSensorData();
		}
	}
	
//	final TimerTask task = new TimerTask() {
//		@Override
//		public void run() {
//			SensorMgr.getInstance().addSensorData();
//			
//			/*String res = SensorMgr.getInstance().getSensorData();
//			if (res != null) {
//				try {
//					addRecord(res);
//					MLog.d("res====" + res);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}*/
//		}
//
//	};
	
	/**
	 * 文件读写操作初始化
	 * @throws FileNotFoundException
	 */
	private void initFile() throws FileNotFoundException {
		fileName += MethodUtils.parseDate(System.currentTimeMillis());
		file = StorageUtil.createFile(fileName, mContext);
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}
	
	/**
	 * 将扫描到的Sensor信息追加到文本文件中
	 * @param res	Sensor信息
	 * @throws IOException 
	 */
	private void addRecord(String res) throws IOException {
		if (res == null || res.length() <= 0)
			return;
			
		if(br.readLine() != null) {
			bw.newLine();
		}
		bw.append(res);
		bw.flush();
	}
	
	/**
	 * 关闭文件流
	 */
	private void closeFile() {
		try {
			if (br != null) {
				br.close();
			}
			
			if (bw != null) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void gc() {
		closeFile();
	}
}
