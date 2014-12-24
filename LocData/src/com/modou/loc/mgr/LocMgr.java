package com.modou.loc.mgr;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-11 上午7:52:58
 * 类说明:
 * 定位逻辑数据处理类
 * 该类用于定位数据的集中处理，jni交互等操作。
 */
public class LocMgr {

	private static LocMgr instance = null;
	
	private LocMgr(){}
	
	/**
	 * 定位前的初始化操作
	 */
	public void init() {
		//TODO 1. 调用JNI，将map文件地址引用传递给JNI。
		// 	   2.  开启计时器任务，检测当前数据队列是否已满（假设定义容量为100的容器），如果已满，
		//     		则生成副本并传递给JNI,并重新保存数据(数组索引从1开始)。
		// 	   3. 如果调用第2步的jni方法有返回值，则应更新地图数据和保存，并同步到地图展示页面上。
		
		
	}
	
	public static LocMgr getIntance() {
		if (instance == null) {
			instance = new LocMgr();
		}
		return instance;
	}
	
	public native int NativeFileOpen(String filename, int flags);

	public native int NativeFileRead(int fd, byte[] buf, int sizes);

	public native int NativeFileWrite(int fd, byte[] buf, int sizes);

	public native long NativeFileSeek(int fd, long Offset, int whence);
	//Offset：偏移量，每一读写操作所需要移动的距离，单位是字节的数量，可正可负（向前移，向后移）。

	public native int NativeFileClose(int fd);
	
	static {
		System.loadLibrary("fs");
	}
	
}
