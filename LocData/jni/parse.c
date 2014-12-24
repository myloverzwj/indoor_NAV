#include <unistd.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <stdlib.h>
#include <fcntl.h>
#include <stdio.h>


/**
 * 添加wifi数据
 */
void addWifiData(char macAddress, long curTime, int signalStrength)
{
//    point a;
	printf("%c", macAddress);
}

/**
 * 添加陀螺仪传感器数据
 */
void addGyroscopeData(long timestamp, float xAxis, float yAxis, float zAxis,
		int type, int version, float maximumRange, char name, float power,
		int accuracy, float resolution, char vendor)
{

}

/**
 * 添加磁阻传感器数据
 */
void addMagneticData(long timestamp, float xAxis, float yAxis, float zAxis,
		int type, int version, float maximumRange, char name, float power,
		int accuracy, float resolution, char vendor)
{

}

/**
 * 添加加速度传感器
 */
void addAccelerometerData(long timestamp, float xAxis, float yAxis, float zAxis,
		int type, int version, float maximumRange, char name, float power,
		int accuracy, float resolution, char vendor)
{

}

/**
 * 获取定位点数据
 */
//Point getLocDatas()
//{
//	return NULL;
//}

