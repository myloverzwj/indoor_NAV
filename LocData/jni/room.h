#ifndef ROOM_H
#define ROOM_H
#include "door.h"
#include <jni.h>

#include "lines.h"
using namespace std;
class room
{
public:

	room(float x_1,float y_1,float length_1,float width_1,float arg_0,char* name1);
    room(lines *l1,char* name1);
	~room();
	bool operator != (room &room1);
	float get_length();
	float get_width();
	bool if_in(float x1,float x2);
	void set_door(door &door1,room *room1);
	void set_door(door &door1,passageway *passageway1);
	door* find_door(room & b);
	door* find_door(passageway & b);
	door_room* get_head(int a=0);
	door_passageway* get_head();
	char* get_name();
//	void display();
private:
	rect *a;
	lines *b;
    door_passageway *door_passageway_head,*door_passageway_end;
    door_room *door_room_head, *door_room_end;
    char* name;
};


room::~room(){
	while(door_room_head)
	{
		door_room_end=door_room_head->next;
//		delete door_room_head->the;
		delete door_room_head;
		door_room_head=door_room_end;
	}
	while(door_passageway_head)
	{
		door_passageway_end=door_passageway_head->next;
//		delete door_passageway_head->the;
		delete door_passageway_head;
		door_passageway_head=door_passageway_end;
	}
	delete a;
}
room::room(float x_1,float y_1,float length_1,float width_1,float arg_0,char* name1)
{
	a=new rect(x_1,y_1,length_1,width_1,arg_0);
	door_room_head = 0;
	door_passageway_head = 0;
	name =name1;
	b=0;
}
room::room(lines *l1,char* name1)
{
	a=0;
	b=l1;
	name=name1;
}

door_room *room::get_head(int a)
{
	return door_room_head;
}

door_passageway *room::get_head()
{
	return door_passageway_head;
}
char* room::get_name()
{
	return name;
}
float room::get_length()
{
	return a->get_length();
}

float room::get_width()
{
	return a->get_width();
}

bool room::if_in(float x1,float x2)
{

	if((a&&a->if_in(x1,x2)))return 1;
	if(b&&b->if_in(x1,x2))return 1;
	else return 0;
}

void room::set_door(door &door1,room *room1)
{
	if(!door_room_head)
	{
		door_room_head = new door_room;
		door_room_head->the=&(door1);
		door_room_head->next=0;
		door_room_head->to_room =room1;
		door_room_end = door_room_head;

	}
	else
	{
		door_room_end->next = new door_room;
		door_room_end = door_room_end->next;
		door_room_end->the=&(door1);
		door_room_end->next=0;
		door_room_end->to_room = room1;

	}

}

void room::set_door(door &door1,passageway *passageway1)
{
	if(!door_passageway_head)
	{
		door_passageway_head = new door_passageway;
		door_passageway_head->the=&(door1);
		door_passageway_head->next=0;
		door_passageway_head->to_passageway = passageway1;
		door_passageway_end = door_passageway_head;

	}
	else
	{
		door_passageway_end->next = new door_passageway;
		door_passageway_end = door_passageway_end->next;
		door_passageway_end->the=&(door1);
		door_passageway_end->next=0;
		door_passageway_end->to_passageway = passageway1;

	}
}
bool room::operator !=(room &room1)
{
	//if(!(*(room1.a) == *a))return true;
	if(name != room1.get_name())return true;
    else return false;
}

door * room::find_door(room & b)
{
	door_room *d1=door_room_head;
	while(d1)
	{
		if(!(d1->to_room!= &b)) return d1->the;
		d1 = d1->next;
	}
	return 0;

}

door * room::find_door(passageway &b)
{
	door_passageway *d1=door_passageway_head;
	while(d1)
	{
		if(!(d1->to_passageway!= &b)) return d1->the;
		d1 = d1->next;
	}
	return 0;

}

//void room::display()
//{
//	a->display();
//}
#endif
