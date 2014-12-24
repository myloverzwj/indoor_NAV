#ifndef CIRCLE_H
#define CIRCLE_H
#include <math.h>
//#include <glut.h>
using namespace std;
class circle
{
public:
    circle(float x1,float y1,float radius1);
    float get_radius();
    bool if_in(float x1,float y1);
    float get_x();
    float get_y();
//    void display();
private:
	float x,y,radius;
};

circle::circle(float x1,float y1,float radius1)
{
	x=x1;
	y=y1;
	radius=radius1;
}
float circle::get_radius()
{
	return radius;
}

bool circle::if_in(float x1,float y1)
{
	if((x-x1)*(x-x1)+(y-y1)*(y-y1)>radius*radius)return false;
	return true;
}

float circle::get_x()
{
	return x;
}
float circle::get_y()
{
	return y;
}
//void circle::display()
//{
//	  int n=10;
//     float R =radius/30;
//     float Pi=3.14;
//     glBegin(GL_LINE_LOOP);
//     for(int i=0; i<n; ++i)
//      glVertex2f(x/30+R*cos(2*Pi/n*i), y/30+R*sin(2*Pi/n*i));
//     glEnd();
//}
#endif
