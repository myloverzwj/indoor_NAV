#ifndef RECT_H
#define RECT_H
//#include <glut.h>
#include <math.h>
#include <string.h>
#include <stdio.h>
class rect
{
public:
	rect(float x_1,float y_1,float length_1,float width_1,float arg_0=0);
	float get_length();
	float get_width();
	bool if_in(float x1,float x2);
	bool operator == (rect &rect1);
	//void display();
private:
	float x,y,length,width;
	float arg;
};

rect::rect(float x_1,float y_1,float length_1,float width_1,float arg_0)
{
	x=x_1;
		y=y_1;
		length=length_1-x_1;
		width=width_1-y_1;
		arg=arg_0;
}

float rect::get_length()
{
	return length;
}

float rect::get_width()
{
	return width;
}

bool rect::if_in(float x1,float y1)
{
	/*float arg1=atan2(x1-x,y1-y);
    float dis=sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
    float x2,y2;
    x2=dis*cos(arg1-arg);
    y2=dis*sin(arg1-arg);*/
	float len,wid;
	len=2*x1-length-2*x;
	wid=2*y1-width-2*y;
	if(len*len>length*length||wid*wid>width*width)return 0;
    else return 1;
}

bool rect::operator ==(rect &rect1)
{

	if(rect1.x==x&&rect1.y==y&&rect1.width==width&&rect1.length==length&&rect1.arg==arg)return true;
	else return false;
}
//
//void rect::display()
//{
//   glBegin(GL_LINE_LOOP);
//     glVertex3f((x)/30,(y)/30,0.00);
//     glVertex3f((x+width*sin(arg))/30,(y+width*cos(arg))/30,0.00);
//     glVertex3f((x+width*sin(arg)+length*cos(arg))/30,(y+width*cos(arg)-length*sin(arg))/30,0.00);
//      glVertex3f((x+length*cos(arg))/30,(y-length*sin(arg))/30,0.00);
//     glEnd();
//}
#endif
