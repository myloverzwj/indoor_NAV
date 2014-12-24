#ifndef SECTOR_H
#define SECTOR_H 
#include <math.h>
//#include <glut.h>
using namespace std;


class sector
{
public:
	sector(float x1,float y1,float r1,float r2,float arg1,float arg2);
	sector();
	bool if_in(float x1,float y1);
	//void display();
private:
    float x, y,r1,r2;
    float arg_start,arg_end;
};


sector::sector()
{

}
sector::sector(float x1,float y1,float r_1,float r_2,float arg1,float arg2)
{
	x = x1;
	y = y1;
	r1 = r_1;
	r2 = r_2;
	arg_start = arg1;
	arg_end = arg2;
}


bool sector::if_in(float x1,float y1)
{
	if(sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y))>r2&&
        sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y))<r1&&
       atan2(x1-x,y1-y)>arg_start&&
        atan2(x1-x,y1-y)<arg_end)return 1;
	return 0;
}
//
//void sector::display()
//{
//	int n=20;
//     float Pi=3.14;
//     glBegin(GL_LINE_LOOP);
//     for(int i=0; i<n; ++i)glVertex2f(x/30+r1/30*sin(arg_start+(arg_end-arg_start)*i/n), y/30+r1/30*cos(arg_start+(arg_end-arg_start)*i/n));
//     glVertex2f(x/30+r1/30*sin(arg_end),y/30+r1/30*cos(arg_end));
//    glVertex2f(x/30+r2/30*sin(arg_end),y/30+r2/30*cos(arg_end));
//     for(int i=0; i<n; ++i)glVertex2f(x/30+r2/30*sin(arg_end-(arg_end-arg_start)*i/n), y/30+r2/30*cos(arg_end-(arg_end-arg_start)*i/n));
//     glVertex2f(x/30+r2/30*sin(arg_start),y/30+r2/30*cos(arg_start));
//     glEnd();
//}
#endif
