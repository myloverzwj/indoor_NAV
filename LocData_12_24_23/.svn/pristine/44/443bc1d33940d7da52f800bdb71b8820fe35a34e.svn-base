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

import com.modou.loc.module.wifi.WifiMgr;
import com.modou.utils.MLog;
import com.modou.utils.StorageUtil;

import android.content.Context;
import android.os.AsyncTask;

public class WifiDataTask_old extends AsyncTask<Void, Void, Void> {

	private Context mContext;
	
	private final String fileName = "wifi_loc_data";
	private File file;
	private BufferedWriter bw = null;
	private BufferedReader br = null;
	
	public WifiDataTask_old(Context ctx) {
		this.mContext = ctx;
		WifiMgr.getInstance().init(ctx);
		
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws FileNotFoundException {
		file = StorageUtil.createFile(fileName, mContext);
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}

	@Override
	protected Void doInBackground(Void... params) {
		while (true) {
//			String res = WifiMgr.getInstance().getLocData(mContext);
//			MLog.d("res====" + res);
//			try {
//				if (res != null) {
//					addRecord(res);
//				}
//				
//				// 0.5Hz发送一次
//				Thread.sleep(2 * 1000);	
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}
	}

	/**
	 * 将扫描到的wifi信息追加到文本文件中
	 * @param res	wifi信息
	 * @throws IOException 
	 */
	private void addRecord(String res) throws IOException {
		if(br.readLine() != null) {
			bw.newLine();
		}
		bw.append(res);
		bw.flush();
	}
	
	@Override
	protected void onCancelled() {
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
		super.onCancelled();
	}
	
}
