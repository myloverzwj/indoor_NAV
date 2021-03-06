//jni.c
#define TAG "fs_jni"

#include <android/log.h>
#include <string.h>
#include "jniUtils.h"
#include <jni.h>


#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)


static const char* const kClassPathName = "com/modou/loc/data/transfer/DataTransferMgr2";


Java_com_modou_loc_data_transfer_DataTransferMgr2_initLoc(JNIEnv* env, jobject thiz, jstring fileName, jboolean flag, jobjectArray arr) {
	write_info(1);
}

jobjectArray
Java_com_modou_loc_data_transfer_DataTransferMgr2_getUserTrack(JNIEnv* env, jobject thiz, jobjectArray wifiArr, jint wifiStart, jint wifiEnd,
															  jobjectArray gyroscopeArr, jint gyroscopeStart, jint gyroscopeEnd,
															  jobjectArray magneticArr, jint magneticStart, jint magneticEnd,
															  jobjectArray accelerometerArr, jint accelerometerStart, jint accelerometerEnd) {

	LOGI("----------getUserTrack method-------------");
//	testMt();//TODO 调用当前类中的方法会报错，。。。。。。。
	//=================解析java层传递的数据======================
	jobject obj;
	jclass objCls;
	jmethodID methodId;
	int i;
	jsize size;
	if (wifiArr) {
		size = (*env)->GetArrayLength(env, wifiArr);
		//=============================遍历wifi数组数据==================================
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = (*env)->GetObjectArrayElement(env, wifiArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			objCls = (*env)->GetObjectClass(env, obj);
			if (!objCls)
				continue;
			// 获得对象中的getMacAddress方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getMacAddress", "()Ljava/lang/String;");
			jstring macAddress =(jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char* macAds_char = (*env)->GetStringUTFChars(env, macAddress, NULL);
			//TODO macAddress传递给c的时候估计应该还需转换成char类型
			// 获得对象中的getCurTime方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getCurTime", "()J");
			jlong curTime = (jlong) (*env)->CallLongMethod(env, obj, methodId, NULL);
			// 获得对象中的getSignalStrength方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getSignalStrength", "()I");
			jint signalStrength =(jint) (*env)->CallIntMethod(env, obj, methodId, NULL);

			// 调用添加wifi数据函数
			addWifiData(macAds_char, curTime, signalStrength);

			(*env)->DeleteLocalRef(env, objCls);
			(*env)->DeleteLocalRef(env, obj);
			(*env)->ReleaseStringUTFChars(env, macAddress, macAds_char);
			(*env)->DeleteLocalRef(env, macAddress);
		}
		(*env)->DeleteLocalRef(env, wifiArr);
	}

	//=============================遍历wifi数组数据结束==================================

	// ==============================遍历陀螺仪数据数组===================================
	if (gyroscopeArr) {
		size = (*env)->GetArrayLength(env, gyroscopeArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = (*env)->GetObjectArrayElement(env, gyroscopeArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = (*env)->GetObjectClass(env, obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getAccuracy", "()I");
			jint accuracy = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) (*env)->CallLongMethod(env, obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getType", "()I");
			jint type = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVersion", "()I");
			jint version = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getName方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getName", "()Ljava/lang/String;");
			jstring name = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *name_char = (*env)->GetStringUTFChars(env, name, NULL);
			// 获得对象中的getPower方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getPower", "()F");
			jfloat power = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVendor", "()Ljava/lang/String;");
			jstring vendor = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *vendor_char = (*env)->GetStringUTFChars(env, vendor, NULL);

			addGyroscopeData(accuracy, timestamp, xAxis, yAxis, zAxis, type, version,
					maximumRange, name_char, power, resolution, vendor_char);

			(*env)->ReleaseStringUTFChars(env, name, name_char);
			(*env)->ReleaseStringUTFChars(env, vendor, vendor_char);
			(*env)->DeleteLocalRef(env, objCls);
			(*env)->DeleteLocalRef(env, obj);
			(*env)->DeleteLocalRef(env, name);
			(*env)->DeleteLocalRef(env, vendor);
		}
		(*env)->DeleteLocalRef(env, gyroscopeArr);
	}
	// ==============================遍历陀螺仪数据数组结束===================================


	// ==============================遍历磁阻传感器数据数组===================================
	if (magneticArr) {
		size = (*env)->GetArrayLength(env, magneticArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = (*env)->GetObjectArrayElement(env, magneticArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = (*env)->GetObjectClass(env, obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getAccuracy", "()I");
			jint accuracy = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) (*env)->CallLongMethod(env, obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getType", "()I");
			jint type = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVersion", "()I");
			jint version = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getName方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getName", "()Ljava/lang/String;");
			jstring name = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *name_char = (*env)->GetStringUTFChars(env, name, NULL);
			// 获得对象中的getPower方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getPower", "()F");
			jfloat power = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVendor", "()Ljava/lang/String;");
			jstring vendor = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *vendor_char = (*env)->GetStringUTFChars(env, vendor, NULL);

			addMagneticData(accuracy, timestamp, xAxis, yAxis, zAxis, type, version,
					maximumRange, name_char, power, resolution, vendor_char);

			(*env)->ReleaseStringUTFChars(env, name, name_char);
			(*env)->ReleaseStringUTFChars(env, vendor, vendor_char);
			(*env)->DeleteLocalRef(env, objCls);
			(*env)->DeleteLocalRef(env, name);
			(*env)->DeleteLocalRef(env, vendor);
		}
		(*env)->DeleteLocalRef(env, magneticArr);
	}
	// ==============================遍历陀螺仪数据数组结束===================================

	// ==============================遍历加速度传感器数据数组===================================
	if (accelerometerArr) {
		size = (*env)->GetArrayLength(env, accelerometerArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = (*env)->GetObjectArrayElement(env, accelerometerArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = (*env)->GetObjectClass(env, obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getAccuracy", "()I");
			jint accuracy = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) (*env)->CallLongMethod(env, obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getType", "()I");
			jint type = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVersion", "()I");
			jint version = (jint) (*env)->CallIntMethod(env, obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getName方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getName", "()Ljava/lang/String;");
			jstring name = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *name_char = (*env)->GetStringUTFChars(env, name, NULL);
			// 获得对象中的getPower方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getPower", "()F");
			jfloat power = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) (*env)->CallFloatMethod(env, obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = (*env)->GetMethodID(env, objCls, "getVendor", "()Ljava/lang/String;");
			jstring vendor = (jstring) (*env)->CallObjectMethod(env, obj, methodId, NULL);
			const char *vendor_char = (*env)->GetStringUTFChars(env, vendor, NULL);

			addAccelerometerData(accuracy, timestamp, xAxis, yAxis, zAxis, type, version,
					maximumRange, name_char, power, resolution, vendor_char);

			(*env)->ReleaseStringUTFChars(env, name, name_char);
			(*env)->ReleaseStringUTFChars(env, vendor, vendor_char);
			(*env)->DeleteLocalRef(env, objCls);
			(*env)->DeleteLocalRef(env, obj);
			(*env)->DeleteLocalRef(env, name);
			(*env)->DeleteLocalRef(env, vendor);
		}
		(*env)->DeleteLocalRef(env, accelerometerArr);
	}
	// ==============================遍历加速度传感器数据数组结束===================================

	//================java层数据解析完毕========================



	//==================C++层生成对象数据数组返回给java层====================


	// 定义数组中的元素类型
//	Point[] *pointArr = getLocDatas();
//	jsize pArrSize = pointArr.length; //TODO 对象数组大小，暂定为2
	jsize pArrSize = 2;
	jclass pointCls = (*env)->FindClass(env, "com/modou/loc/module/map2/Point");
	jobjectArray pointArr = (*env)->NewObjectArray(env, pArrSize, pointCls, NULL);
	jobject point;
	jmethodID consID = (*env)->GetMethodID(env, pointCls, "<init>", "()V");
	jfieldID xID = (*env)->GetFieldID(env, pointCls, "x", "F");
	jfieldID yID = (*env)->GetFieldID(env, pointCls, "y", "F");
	for (i = 0; i < pArrSize; i++) {
		point = (jobject) (*env)->NewObject(env, pointCls, consID);
		(*env)->SetObjectArrayElement(env, pointArr, i, point);
		(*env)->SetFloatField(env, point, xID, 1);//TODO 假值
		(*env)->SetFloatField(env, point, yID, 1);
	}

	return pointArr;
	//==================C++层返回数据处理完毕=============================

}

void testMt()
{
}


/******************************JNI registration.************************************/
static JNINativeMethod gMethods[] = {
    {"initLoc",       		"(Ljava/lang/String;Z[Lcom/modou/loc/data/transfer/WifiDataBean;)V",           (void *)Java_com_modou_loc_data_transfer_DataTransferMgr2_initLoc},
    {"getUserTrack",   	 	"([Lcom/modou/loc/data/transfer/WifiDataBean;II[Lcom/modou/loc/data/transfer/SensorDataBean;II[Lcom/modou/loc/data/transfer/SensorDataBean;II[Lcom/modou/loc/data/transfer/SensorDataBean;II)[Lcom/modou/loc/module/map2/Point;",       (void *)Java_com_modou_loc_data_transfer_DataTransferMgr2_getUserTrack},
};

int register_com_modou_loc_data_transfer_DataTransferMgr2(JNIEnv *env) {
	return jniRegisterNativeMethods(env, kClassPathName, gMethods, sizeof(gMethods) / sizeof(gMethods[0]));

}
