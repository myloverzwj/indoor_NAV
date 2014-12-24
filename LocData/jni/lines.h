#ifndef LINES_H
#define LINES_H
//#include <glut.h>
#include <jni.h>

#include <math.h>
#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)

class lines
{
public:
	lines(float points1[][2],int num1);
	//float get_length();
	//float get_width();
	int if_com(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4);

	bool if_in(float x1,float x2);
	//bool operator == (lines &lines1);
	//void display();
private:
	float points[20][2];
	int cur;
	int num;
};

lines::lines(float points1[][2],int num1)
{
	for(int i=0;i<num1;i++)
	{
		points[i][0]=points1[i][0];
		points[i][1]=points1[i][1];
		// LOGI("---------------initLoc-----if_in--%f-%f",points[i][0],points[i][1]);
	}
	num=num1;

}

bool lines::if_in(float x1,float y1)
{
int arg1=0;

   for(int i=0;i<num-1;i++)
   {
	   /*if(i==0)arg11=atan2(points[num-2][0]-x1,points[num-2][1]-y1);
	   else arg11=atan2(points[i-1][0]-x1,points[i-1][1]-y1);
	   arg12=atan2(points[i][0]-x1,points[i][1]-y1);
	   //if(arg11<0)arg11+=2*3.14159;
	  // if(arg12<0)arg12+=2*3.14159;
	   float tt=arg11-arg12;*/
	 // LOGI("---------------initLoc-----if_in--%f--%f-%d",x1,y1,if_com((float)points[i][0],(float)points[i][1],(float)points[(i+1)%num][0],(float)points[(i+1)%num][1],x1,y1,(float)-100,(float)-100));
	   if(if_com((float)points[i][0],(float)points[i][1],(float)points[(i+1)][0],(float)points[(i+1)][1],x1,y1,(float)-100,(float)-100)==1)arg1++;
   }

	if(arg1%2==0)return 0;
    else return 1;
}

int lines::if_com(
    float v1x1, float v1y1, float v1x2, float v1y2,
    float v2x1, float v2y1, float v2x2, float v2y2
) {
    //LOGI("---------------initLoc-----if_in %f %f %f %f",v1x1,v1y1,v1x2,v1y2);
    float a1, a2, a3,a4;
    a1=(v2x1-v1x1)*(v1y1-v1y2)-(v2y1-v1y1)*(v1x1-v1x2);
    a2=(v2x2-v1x1)*(v1y1-v1y2)-(v2y2-v1y1)*(v1x1-v1x2);
    a3=(v1x1-v2x1)*(v2y1-v2y2)-(v1y1-v2y1)*(v2x1-v2x2);
    a4=(v1x2-v2x1)*(v2y1-v2y2)-(v1y2-v2y1)*(v2x1-v2x2);
 //   LOGI("---------------initLoc-----if_in %f %f %f %f",a1,a2,a3,a4);
    if(a1*a2<0&&a3*a4<0)return 1;
    return 0;
}
#endif
