#ifndef DOOR_H
#define DOOR_H value
#include "rect.h"
#include "circle.h"

class room;
class passageway;
class door;
struct door_room
{
    door* the;
    door_room* next;
    room *to_room;
};
struct door_passageway
{
    door* the;
    door_passageway* next;
    passageway *to_passageway;
};
class door
{
public:
	door(float x_1,float y_1,float x_2,float y_2);
	~door();
	float get_width();
	bool if_in(float x,float y);
//	void display();
private:
	float width;
	float x1,y1,x2,y2;
	circle *a;
};

door::door(float x_1,float y_1,float x_2,float y_2)
{
	x1=x_1;
	x2=x_2;
	y1=y_1;
	y2=y_2;
	a=new circle((x1+x2)/2,(y1+y2)/2,(sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)))/2);
}
door::~door()
{
	delete a;
}
float door::get_width()
{
	return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}

bool door::if_in(float x,float y)
{
	return a->if_in(x,y);
}

//void door::display()
//{
//	a->display();
//}
#endif
