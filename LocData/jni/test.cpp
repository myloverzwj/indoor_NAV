//jni.c
#define TAG "fs_jni"

#include <android/log.h>
#include <string.h>
#include "jniUtils.h"
//jni.c
#define TAG "fs_jni"

#include <android/log.h>
#include <string.h>
#include "jniUtils.h"
#include <jni.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <stdlib.h>
#include <fcntl.h>
#include <stdio.h>
#include "read_xml.h"
#include "testH1.h"
#include "floor.h"
#include "particle_filter.h"
#include "sensor_data.h"
#include "WIFI.h"
using namespace std;
char axx[20]="floor1";
static particle_filter particle_filter1(400);
static Floor floor1(axx);
static sensor_data GyroscopeData;
static sensor_data AccelerometerData;
static sensor_data MagneticData;
static points trail;
static float length = 25;
static float det_length = 10;
static float det_de = 0.8;
static float de;
static float de_de=-1.57;
static int point_x;
static int point_y;
static Locating wifi;
static bool begin=0;
static bool check;
static int C_low;
static int C_high;
#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)
static int testnum=0;
/**
 * 添加wifi数据
 */

void reset() {

	//wifi.InitialValue();
	wifi.Calculate();
    LOGI("---------111------wifi--------%f  %f",wifi.x*5,wifi.y*5 );
	//particle_filter1.initialization(wifi.x*25,wifi.y*25 , 50, 50);
	particle_filter1.initialization(100,300 , 50, 50);

	point_x = particle_filter1.get_x();
	point_y = particle_filter1.get_y();
}
void addWifiData(char const* macAddress, long curTime, int signalStrength) {

	//LOGI("--------------addWifiData------------%s, %ld, %d", macAddress, curTime, signalStrength);
	wifi.GetValue(macAddress, signalStrength);
	//wifi.Calculate();
    //LOGI("---------111------wifi--------%f  %f",wifi.x*10,wifi.y*10 );
	//point_x =wifi.x*10;
   // point_y = wifi.y*10;
	if(!begin)testnum++;
	if(testnum>30&&!begin)
	{
		C_low=0;
		C_high=0;
		begin = 1;
		reset();
		check=1;

	}
}

/**
 * 添加陀螺仪传感器数据
 */
void addGyroscopeData(char const* name, char const* vendor, int accuracy,
		long timestamp, float xAxis, float yAxis, float zAxis, int type,
		int version, float maximumRange, float power, float resolution) {
	GyroscopeData.setdata(xAxis, yAxis, zAxis);

}

/**
 * 添加磁阻传感器数据
 */
void addMagneticData(char const* name, char const* vendor, int accuracy,
		long timestamp, float xAxis, float yAxis, float zAxis, int type,
		int version, float maximumRange, float power, float resolution) {
	MagneticData.setdata(xAxis, yAxis, zAxis);
  //  LOGI("---------111------fff--------    %f %f  %f",xAxis, yAxis, zAxis);

}

/**
 * 添加加速度传感器
 */
void addAccelerometerData(char const* name, char const* vendor, int accuracy,
		long timestamp, float xAxis, float yAxis, float zAxis, int type,
		int version, float maximumRange, float power, float resolution) {
	AccelerometerData.setdata(xAxis, yAxis, zAxis);
	//LOGI("---------1a--------%f",AccelerometerData.cal_Ci(20));
	if(begin&&C_high<8&&AccelerometerData.cal_Ci(20)>1){
		C_high++;
	}
	if(C_high<8&&AccelerometerData.cal_Ci(20)<1){
		C_high=0;
	}
/*	if(C_high>=15)
	{
		if(AccelerometerData.cal_Ci(20)<3){
					C_high=0;
			LOGI("---------1a--------%f",AccelerometerData.cal_Ci(20));

				}
	}
*/
	if(C_high>=5){
		if(C_low>0&&AccelerometerData.cal_Ci(20)>1){
			C_high=0;
			C_low=0;
		}
		if(AccelerometerData.cal_Ci(20)<1){
			//C_high=0;
			C_low++;
		}
		if(C_low>2){
			LOGI("---------1a--------%f",AccelerometerData.cal_Ci(20));
			de = AccelerometerData.cal_de(MagneticData);
			de+=de_de;
			de=de+6.28;
			LOGI("---------111------aaaa--------%f %f %f %f",AccelerometerData.cal_max(100),AccelerometerData.cal_DC(20), AccelerometerData.cal_DC(200),de+de_de);

		    particle_filter1.prediction(length, det_length, de, det_de, &floor1,&wifi);
					point_x = particle_filter1.get_x();
					point_y = particle_filter1.get_y();
					C_low=0;
					C_high=0;
		}
	}

//	//LOGI("------de----------    %f",AccelerometerData.cal_de(MagneticData)*180/3.1415);
	/*if (check&&begin&&(AccelerometerData.cal_DC(10)- AccelerometerData.cal_DC(200))>0.1&&(AccelerometerData.cal_DC(10)- AccelerometerData.cal_DC(200))> 0.1*AccelerometerData.cal_max(100)) {
		de = AccelerometerData.cal_de(MagneticData);
		LOGI("---------111------aaaa--------%f %f %f %f",AccelerometerData.cal_max(100),AccelerometerData.cal_DC(20), AccelerometerData.cal_DC(200),de);


	    particle_filter1.prediction(length, det_length, de+de_de, det_de, &floor1);
		point_x = particle_filter1.get_x();
		point_y = particle_filter1.get_y();
		check =0;
	}
	if((AccelerometerData.cal_DC(10)- AccelerometerData.cal_DC(200))<-0.1&&AccelerometerData.cal_DC(10)- AccelerometerData.cal_DC(200)<-0.1*AccelerometerData.cal_max(100))
		check=1;*/
}

extern "C" void Java_com_modou_loc_data_transfer_DataTransferMgr2_initLoc(
		JNIEnv* env, jobject thiz, jstring fileName, jboolean flag,
		jobjectArray arr) {

	//地图文件名
	const char* filename_char = env->GetStringUTFChars(fileName, NULL);
	read_xml(&floor1, &wifi, filename_char);
	//LOGI("---------------initLoc--------%s, %d", filename_char, flag);
	env->ReleaseStringUTFChars(fileName, filename_char);
	env->DeleteLocalRef(fileName);
    begin=0;
	if (arr) {
		jobject obj;
		jclass objCls;
		jmethodID methodId;
		jsize size = env->GetArrayLength(arr);
		for (int i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = env->GetObjectArrayElement(arr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			objCls = env->GetObjectClass(obj);
			if (!objCls)
				continue;
			// 获得对象中的getMacAddress方法的id
			methodId = env->GetMethodID(objCls, "getMacAddress",
					"()Ljava/lang/String;");
			jstring macAddress = (jstring) env->CallObjectMethod(obj, methodId,
					NULL);
			const char* macAds_char = env->GetStringUTFChars(macAddress, NULL);
			// 获得对象中的getCurTime方法的id
			methodId = env->GetMethodID(objCls, "getCurTime", "()J");
			jlong curTime = (jlong) env->CallLongMethod(obj, methodId, NULL);
			// 获得对象中的getSignalStrength方法的id
			methodId = env->GetMethodID(objCls, "getSignalStrength", "()I");
			jint signalStrength = (jint) env->CallIntMethod(obj, methodId,
					NULL);

			wifi.GetValue(macAds_char, (int) signalStrength);

			//particle_filter1.initialization();
		//	LOGI("---------------initLoc----------%s, %ld, %d", macAds_char,
		//			(long )curTime, signalStrength);

			env->DeleteLocalRef(objCls);
			env->DeleteLocalRef(obj);
			env->ReleaseStringUTFChars(macAddress, macAds_char);
			env->DeleteLocalRef(macAddress);
		}
		env->DeleteLocalRef(arr);
	}
//reset();


}

extern "C" jobjectArray Java_com_modou_loc_data_transfer_DataTransferMgr2_getUserTrack(
		JNIEnv* env, jobject thiz, jobjectArray wifiArr, jint wifiStart,
		jint wifiEnd, jobjectArray gyroscopeArr, jint gyroscopeStart,
		jint gyroscopeEnd, jobjectArray magneticArr, jint magneticStart,
		jint magneticEnd, jobjectArray accelerometerArr,
		jint accelerometerStart, jint accelerometerEnd) {

//	LOGI("----------getUserTrack method-------------");

//=================解析java层传递的数据======================
	jobject obj;
	jclass objCls;
	jmethodID methodId;
	int i;
	jsize size;
	if (wifiArr) {
		size = env->GetArrayLength(wifiArr);
		//=============================遍历wifi数组数据==================================
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = env->GetObjectArrayElement(wifiArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			objCls = env->GetObjectClass(obj);
			if (!objCls)
				continue;
			// 获得对象中的getMacAddress方法的id
			methodId = env->GetMethodID(objCls, "getMacAddress",
					"()Ljava/lang/String;");
			jstring macAddress = (jstring) env->CallObjectMethod(obj, methodId,
					NULL);
			const char* macAds_char = env->GetStringUTFChars(macAddress, NULL);
			//TODO macAddress传递给c的时候估计应该还需转换成char类型
			// 获得对象中的getCurTime方法的id
			methodId = env->GetMethodID(objCls, "getCurTime", "()J");
			jlong curTime = (jlong) env->CallLongMethod(obj, methodId, NULL);
			// 获得对象中的getSignalStrength方法的id
			methodId = env->GetMethodID(objCls, "getSignalStrength", "()I");
			jint signalStrength = (jint) env->CallIntMethod(obj, methodId,
					NULL);

			// 调用添加wifi数据函数
			addWifiData(macAds_char, curTime, signalStrength);
//			LOGI("---------------getUserTrack-----wifiData-----%s, %ld, %d", macAds_char, (long)curTime, signalStrength);

			env->DeleteLocalRef(objCls);
			env->DeleteLocalRef(obj);
			env->ReleaseStringUTFChars(macAddress, macAds_char);
			env->DeleteLocalRef(macAddress);
		}
		env->DeleteLocalRef(wifiArr);
	}

	//=============================遍历wifi数组数据结束==================================

	// ==============================遍历陀螺仪数据数组===================================
	if (gyroscopeArr) {
		size = env->GetArrayLength(gyroscopeArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = env->GetObjectArrayElement(gyroscopeArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = env->GetObjectClass(obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = env->GetMethodID(objCls, "getAccuracy", "()I");
			jint accuracy = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) env->CallLongMethod(obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = env->GetMethodID(objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = env->GetMethodID(objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = env->GetMethodID(objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = env->GetMethodID(objCls, "getType", "()I");
			jint type = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getVersion", "()I");
			jint version = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = env->GetMethodID(objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getName方法的id
			methodId = env->GetMethodID(objCls, "getName",
					"()Ljava/lang/String;");
			jstring name = (jstring) env->CallObjectMethod(obj, methodId, NULL);
			const char *name_char = env->GetStringUTFChars(name, NULL);
			// 获得对象中的getPower方法的id
			methodId = env->GetMethodID(objCls, "getPower", "()F");
			jfloat power = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getVendor",
					"()Ljava/lang/String;");
			jstring vendor = (jstring) env->CallObjectMethod(obj, methodId,
					NULL);
			const char *vendor_char = env->GetStringUTFChars(vendor, NULL);

			addGyroscopeData(name_char, vendor_char, accuracy, timestamp, xAxis,
					yAxis, zAxis, type, version, maximumRange, power,
					resolution);
//			LOGI("---------------getUserTrack-----陀螺仪数据-----%f, %f, %f", xAxis, yAxis, zAxis);

			env->ReleaseStringUTFChars(name, name_char);
			env->ReleaseStringUTFChars(vendor, vendor_char);
			env->DeleteLocalRef(objCls);
			env->DeleteLocalRef(obj);
			env->DeleteLocalRef(name);
			env->DeleteLocalRef(vendor);
		}
		env->DeleteLocalRef(gyroscopeArr);
	}
	// ==============================遍历陀螺仪数据数组结束===================================

	// ==============================遍历磁阻传感器数据数组===================================
	if (magneticArr) {
		size = env->GetArrayLength(magneticArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = env->GetObjectArrayElement(magneticArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = env->GetObjectClass(obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = env->GetMethodID(objCls, "getAccuracy", "()I");
			jint accuracy = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) env->CallLongMethod(obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = env->GetMethodID(objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = env->GetMethodID(objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = env->GetMethodID(objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = env->GetMethodID(objCls, "getType", "()I");
			jint type = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getVersion", "()I");
			jint version = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = env->GetMethodID(objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getName方法的id
			methodId = env->GetMethodID(objCls, "getName",
					"()Ljava/lang/String;");
			jstring name = (jstring) env->CallObjectMethod(obj, methodId, NULL);
			const char *name_char = env->GetStringUTFChars(name, NULL);
			// 获得对象中的getPower方法的id
			methodId = env->GetMethodID(objCls, "getPower", "()F");
			jfloat power = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getVendor",
					"()Ljava/lang/String;");
			jstring vendor = (jstring) env->CallObjectMethod(obj, methodId,
					NULL);
			const char *vendor_char = env->GetStringUTFChars(vendor, NULL);

			addMagneticData(name_char, vendor_char, accuracy, timestamp, xAxis,
					yAxis, zAxis, type, version, maximumRange, power,
					resolution);
//			LOGI("---------------getUserTrack-----磁阻传感器数据-----%f, %f, %f", xAxis, yAxis, zAxis);

			env->ReleaseStringUTFChars(name, name_char);
			env->ReleaseStringUTFChars(vendor, vendor_char);
			env->DeleteLocalRef(objCls);
			env->DeleteLocalRef(name);
			env->DeleteLocalRef(vendor);
		}
		env->DeleteLocalRef(magneticArr);
	}
	// ==============================遍历陀螺仪数据数组结束===================================

	// ==============================遍历加速度传感器数据数组===================================
	if (accelerometerArr) {
		size = env->GetArrayLength(accelerometerArr);
		for (i = 0; i < size; i++) {
			// 从数组中获得对象
			obj = env->GetObjectArrayElement(accelerometerArr, i);
			if (!obj)
				continue;
			// 获得对象的句柄
			jclass objCls = env->GetObjectClass(obj);
			if (!objCls)
				continue;
			// 获得对象中的getAccuracy方法的id
			methodId = env->GetMethodID(objCls, "getAccuracy", "()I");
			jint accuracy = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getTimestamp", "()J");
			jlong timestamp = (jlong) env->CallLongMethod(obj, methodId, NULL);
			// 获得对象中的getxAxis方法的id
			methodId = env->GetMethodID(objCls, "getxAxis", "()F");
			jfloat xAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getyAxis方法的id
			methodId = env->GetMethodID(objCls, "getyAxis", "()F");
			jfloat yAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getzAxis方法的id
			methodId = env->GetMethodID(objCls, "getzAxis", "()F");
			jfloat zAxis = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getType方法的id
			methodId = env->GetMethodID(objCls, "getType", "()I");
			jint type = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getTimestamp方法的id
			methodId = env->GetMethodID(objCls, "getVersion", "()I");
			jint version = (jint) env->CallIntMethod(obj, methodId, NULL);
			// 获得对象中的getMaximumRange方法的id
			methodId = env->GetMethodID(objCls, "getMaximumRange", "()F");
			jfloat maximumRange = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getName方法的id
			methodId = env->GetMethodID(objCls, "getName",
					"()Ljava/lang/String;");
			jstring name = (jstring) env->CallObjectMethod(obj, methodId, NULL);
			const char *name_char = env->GetStringUTFChars(name, NULL);
			// 获得对象中的getPower方法的id
			methodId = env->GetMethodID(objCls, "getPower", "()F");
			jfloat power = (jfloat) env->CallFloatMethod(obj, methodId, NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getResolution", "()F");
			jfloat resolution = (jfloat) env->CallFloatMethod(obj, methodId,
					NULL);
			// 获得对象中的getResolution方法的id
			methodId = env->GetMethodID(objCls, "getVendor",
					"()Ljava/lang/String;");
			jstring vendor = (jstring) env->CallObjectMethod(obj, methodId,
					NULL);
			const char *vendor_char = env->GetStringUTFChars(vendor, NULL);

			addAccelerometerData(name_char, vendor_char, accuracy, timestamp,
					xAxis, yAxis, zAxis, type, version, maximumRange, power,
					resolution);
//			LOGI("---------------getUserTrack-----加速度传感器数据-----%f, %f, %f", xAxis, yAxis, zAxis);

			env->ReleaseStringUTFChars(name, name_char);
			env->ReleaseStringUTFChars(vendor, vendor_char);
			env->DeleteLocalRef(objCls);
			env->DeleteLocalRef(obj);
			env->DeleteLocalRef(name);
			env->DeleteLocalRef(vendor);
		}
		env->DeleteLocalRef(accelerometerArr);
	}
	// ==============================遍历加速度传感器数据数组结束===================================

	//================java层数据解析完毕========================

	//==================C++层生成对象数据数组返回给java层====================

	// 定义数组中的元素类型

    int a1=particle_filter1.get_num();
    LOGI("--------------%d,   ",a1);
    int a2=0;
    if(a1!=0&&particle_filter1.end==true)a2=particle_filter1.particles.head->an_num;
    LOGI("--------------%d,  %d ",a1,a2);
	jsize pArrSize = a1+a2;			// 目前定义对象数组大小就为1，每次返回1个点
	jclass pointCls = env->FindClass("com/modou/loc/module/map2/Point");
	jobjectArray pointArr = env->NewObjectArray(pArrSize, pointCls, NULL);
	jobject point;
	jmethodID consID = env->GetMethodID(pointCls, "<init>", "()V");
	jfieldID xID = env->GetFieldID(pointCls, "x", "F");
	jfieldID yID = env->GetFieldID(pointCls, "y", "F");
	particle_filter1.reset();
	for (i = 0; i < a1; i++) {
		point = (jobject) env->NewObject(pointCls, consID);
		env->SetObjectArrayElement(pointArr, i, point);
		env->SetFloatField(point, xID, particle_filter1.getx());
		env->SetFloatField(point, yID, particle_filter1.gety());
		particle_filter1.next();
	}
	LOGI("--------------%d,   ",a1);
	particle_filter1.reset();
	LOGI("--------------%d,   ",a1);
	trail.reset();
	for(;i<pArrSize&&particle_filter1.end==true;i++)
	{
		point = (jobject) env->NewObject(pointCls, consID);
				env->SetObjectArrayElement(pointArr, i, point);
				int xx,yy;
				particle_filter1.calweizhi(&xx,&yy);
                if(trail.get_NUM()<a2)
                {
                	trail.add(xx,yy);
                }
                else
                {
                	if(i-a1>20)
                	{
                		xx=trail.get_CUR()->x;
                		yy=(trail.get_CUR())->y;
                	}
                	else
                	{
                		trail.get_CUR()->x=xx;
                		(trail.get_CUR())->y=yy;
                	}
                     trail.next();

                }
				env->SetFloatField(point, xID, xx);
				env->SetFloatField(point, yID, yy);
				//particle_filter1.next();
	}

	return pointArr;
	//==================C++层返回数据处理完毕=============================
}

