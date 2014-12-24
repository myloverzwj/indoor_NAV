#ifndef WIFI_H
#define WIFI_H
#include <android/log.h>
#include <math.h>
#include<jni.h>
#include <stdlib.h>
#include<string.h>
#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)

//class AP{
//public:
//	char *MacAddress;
//	int mac_x,max_y;
//	float strength_1m;
//	float strength;
//	float dis(int x,int y);
//	AP(char  * MacAddress,int mac_x,int mac_y,int stength);
//
//};
//AP::AP(char  * MacAddress1,int mac_x1,int mac_y1,int stength1)
//{
//	MacAddress=new char[50];
//	strcpy(MacAddress,MacAddress1);
//	mac_x=mac_x1;
//	mac_y=mac_y1;
//	strength_1m=strength1;
//}
//float AP::dis(int x,int y)
//{
//	return sqrt((x-mac_x)*(x-mac_x)+(y-mac_y)*(y-mac_y));
//}
//class Wifi{
//public:
//	Wifi(int T=20);
//	void set_info(char  * MacAddress,int mac_x,int mac_y,flaot strength=-24);
//	void set_data(char const* MacAddress,int value);
//	float get_position();
//private:
//	float x_2,y_2,
//	int Max_Num;
//	Ap (*APs)[];
//	int CUR;
//	int SIZE;
//};
//
//
//Wifi::Wifi(int T)
//{
//	SIZE=0;
//	CUR =0;
//	Max_Num=T;
//	APs=new (Ap *)[T];
//}
//
//void Wifi::set_info(char * macaddress, int mac_x, int mac_y,float strength)
//{
//	if(CUR<Max_Num)
//	{
//		APs[CUR]=new Ap(macaddress,mac_x,mac_y,strength);
//		CUR++;
//		SIZE++;
//	}
//}
//
//void Wifi::set_data(char const* MacAddress,int value)
//{
//	for(int i=0; i<SIZE; i++)
//	{
//		if(strcmp(APs[i]->MacAddress,MacAddress)==0)
//		{
//			APs[i]->strength=(float)value;
//		}
//	}
//}
//
//float Wifi::get_position()
//{
//
//}


class Locating{
public:
	Locating(){number;avx=0;avy=0;avg=0;l0=-24;mac_num=0;}
    int number;
    double coordinate[100][2];
    double value[100];
    void Input();
    void Output();
	void GetMacAddress(char  * MacAddress,int mac_x,int mac_y);
    void gx();
    double g[100][5];
    void fx();
    double x;
    double y;
    double a;
    double b[100];
    double c[100];
    double avx;
    double avy;
    double avg;
    double l0;
    void analyse();
    int d0;
	int mac_num;
	char Mac_name[100][30];
	void InitialAddress();
	void InitialValue();
	void GetValue(char const* MacAddress,int value);
	int mac[100][4];     //  0:x  1:y
	void Calculate();
};
void Locating::InitialValue()
{
	int i=0;
	for(int i=0;i<100;i++)
		mac[i][3]=0;
}

void Locating::GetValue(char const * MacAddress,int value)
{
	int i=0;
	for(i=0;i<50;i++)
	{
		if(strcmp(MacAddress,Mac_name[i])==0)
		{
			//LOGI("---------111------initLoc--------%s,%d,%d", MacAddress,i,value);
			break;
		}
	}
	if (i==50)return;
	mac[i][2]=value;
	mac[i][3]=1;

}

void Locating::GetMacAddress(char* MacAddress, int mac_x,int mac_y)
{
	strcpy(Mac_name[mac_num],MacAddress);
	mac[mac_num][0]=mac_x;
	mac[mac_num][1]=mac_y;
	mac_num++;
}
void Locating::InitialAddress()
{
	mac_num=0;
}

void Locating::fx()
{
    a=2;
    d0=1;
    for(int i=0;i<number;i++)
    {
       g[i][0]=d0*pow(10, (l0-value[i])/(10*a));
    }
}

void Locating::gx()
{
    for(int i=0;i<number;i++)
    {
        g[i][1]=g[i][0]*g[i][0]-coordinate[i][0]*coordinate[i][0]-coordinate[i][1]*coordinate[i][1];
        avg=avg+g[i][1];
    }
    avg=avg/number;
    for(int i=0;i<number;i++)
    {
        avx=avx+coordinate[i][0];
    }
    avx=avx/number;
    for(int i=0;i<number;i++)
    {
        avy=avy+coordinate[i][1];
    }
    avy=avy/number;
    for(int i=0;i<number;i++)
    {
        b[i]=(g[i][1]-avg)/(2*(avx-coordinate[i][0]));
        c[i]=(avy-coordinate[i][1])/(avx-coordinate[i][0]);
    }
}

void Locating::analyse()
{
    double sum1=0;
    double sum2=0;
    double sum3=0;
    double sum4=0;
    if(number==2)
    {
        y=(b[0]-b[1])/(c[0]-c[1]);
        x=(c[1]*b[0]-c[0]*b[1])/(c[1]-c[0]);
    }
    else
    {
    for(int i=0;i<number;i++)
    {
        sum1=sum1+b[i]*c[i];
        sum2=sum2+b[i];
        sum3=sum3+c[i];
        sum4=sum4+c[i]*c[i];
    }
    y=(number*sum1-sum2*sum3)/(number*sum4-sum3*sum3);
    x=sum2/number-y*(sum3/number);
    }
}

/*void Locating::Input()
{
    for(int i=0;i<number;i++)
    {
        for(int j=0;j<2;j++)
            cin>>coordinate[i][j];
        cin>>value[i];
    }
	number=GetWifiNumber();
	for(int i=0;i<number;i++)
	{
		coordinate[i][0]=GetX(i);
		coordinate[i][1]=GetY(i);
		value[i]=GetValue(i);
	}
}*/

/*void Locating::Output()
{
    cout<<x<<endl;
    cout<<y<<endl;
}*/

void Locating::Calculate()
{
    x=0;
    y=0;
    a=0;
    avx=0;
    avy=0;
    avg=0;
	number=0;
	for(int i=0;i<50;i++)
	{

		if(mac[i][3]==1)
		{
			coordinate[number][0]=mac[i][0];
			coordinate[number][1]=mac[i][1];
			value[i]=mac[i][2];
			number++;
		}
	}
    fx();
    gx();
    analyse();
 //   myLocating.Output();
}
//
//int main(){
//	double x;
//	double y;
//	Locating myLocating;
//	myLocating.InitialAddress();
//	myLocating.GetMacAddress("ha",60,80);
//	myLocating.GetMacAddress("haha",0,100);
//	myLocating.GetMacAddress("hahaha",100,0);
//	myLocating.InitialValue();
//	myLocating.GetValue("ha",-80);
//	myLocating.GetValue("haha",-80);
//	myLocating.GetValue("hahaha",-70);
//	myLocating.Calculate();
//	x=myLocating.x;
//	y=myLocating.y;
//	myLocating.InitialAddress();
//	myLocating.GetMacAddress("ha",60,80);
//	myLocating.GetMacAddress("haha",0,100);
//	myLocating.GetMacAddress("hahaha",100,0);
//	myLocating.InitialValue();
//	myLocating.GetValue("ha",-80);
//	myLocating.GetValue("haha",-80);
//	myLocating.GetValue("hahaha",-80);
//	myLocating.Calculate();
//	x=myLocating.x;
//	y=myLocating.y;
//    /*int num;
//    cout<<"输入AP个数n"<<endl;
//    cin>>num;
//    cout<<"输入n个x,y,信号强度"<<endl;*/
//   /* Locating myLocating;
//    myLocating.Input();
//    myLocating.fx();
//    myLocating.gx();
//    myLocating.analyse();
//    cout<<"坐标为:"<<endl;
//    myLocating.Output();*/
//    return 0;
//}



#endif
