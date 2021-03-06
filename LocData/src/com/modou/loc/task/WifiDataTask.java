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

import com.modou.loc.module.wifi.WifiMgr;
import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;
import com.modou.utils.StorageUtil;

import android.content.Context;

public class WifiDataTask {

	private Context mContext;

	private String fileName = "wifi_loc_data_";
	private File file;
	private BufferedWriter bw = null;
	private BufferedReader br = null;

	private Timer timer;
	private TimerTask task;
	private boolean isCancel;

	public WifiDataTask(Context ctx) {
		this.mContext = ctx;

		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		isCancel = false;
		WifiMgr.getInstance().init(mContext);
		timer = new Timer();
		if (task != null) {
			task.cancel();
		}
		task = new WifiTimerTask();
		timer.schedule(task, 0, 2 * 1000); // 0.5Hz发送一次
	}

	public void cancel() {
		isCancel = true;
		if (timer != null) {
			timer.cancel();
		}
		if (task != null) {
			task.cancel();
		}
		WifiMgr.getInstance().gc();
		gc();
	}
	
//	private TimerTask getTimerTask() {
//		TimerTask task = new TimerTask() {
//			@Override
//			public void run() {
//				WifiMgr.getInstance().addLocData();
//				
//				// 以下是之前将数据写入到文件中
//				/*String res = WifiMgr.getInstance().getLocData();
//				if (res != null) {
//					try {
//						addRecord(res);
//						MLog.d("res====" + res);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}*/
//			}
//
//		};
//		return task;
//	}

//	final TimerTask task = new TimerTask() {
//		@Override
//		public void run() {
//			WifiMgr.getInstance().addLocData();
//			
//			// 以下是之前将数据写入到文件中
//			/*String res = WifiMgr.getInstance().getLocData();
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
	
	class WifiTimerTask extends TimerTask {

		@Override
		public void run() {
			if (isCancel)
				return;
			WifiMgr.getInstance().addLocData();
		}
		
	}

	private void init() throws FileNotFoundException {
		fileName += MethodUtils.parseDate(System.currentTimeMillis());
		file = StorageUtil.createFile(fileName, mContext);
		MLog.d("file===" + file + " ,fileName==" + fileName);
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				file, true)));
		br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
	}

	/**
	 * 将扫描到的wifi信息追加到文本文件中
	 * 
	 * @param res
	 *            wifi信息
	 * @throws IOException
	 */
	private void addRecord(String res) throws IOException {
		if (br.readLine() != null) {
			bw.newLine();
		}
		bw.append(res);
		bw.flush();
	}

	private void gc() {
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

}
