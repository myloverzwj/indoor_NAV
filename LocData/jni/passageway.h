#ifndef PASSAGEWAY_H
#define PASSAGEWAY_H 
#include "rect.h"
#include "sector.h"
#include "door.h"
#include "lines.h"
#include <jni.h>
using namespace std;

struct rects
{
	rect *the;
	rects *next;

};
struct sectors
{
	sector *the;
	sectors *next;
};
struct liness
{
	lines *the;
	liness *next;
};
class passageway
{
public:
	passageway(char* name1);
	~passageway();
	void set_rect(rect &rect1);
	void set_sector(sector &sector1);
	void set_lines(lines &lines1);
	bool if_in(float x1,float y1);
	bool operator != (passageway &passageway1);
	void set_door(door &door1,room *room1);
	void set_door(door &door1,passageway *passageway1);
	door* find_door(room & b);
	door* find_door(passageway & b);
	char* get_name();
//	void display();
private:
    rects *rect_head, *rect_end;
    sectors *sectors_head, *sectors_end;
    liness *liness_head,*liness_end;
    door_passageway *door_passageway_head,*door_passageway_end;
    door_room *door_room_head, *door_room_end;
    char* name;
};

passageway::passageway(char* name1)
{
	name = name1;
	rect_head = 0;
	door_passageway_head = 0;
	sectors_head = 0;
    door_room_head = 0;
    liness_end=0;
    liness_head=0;
}

passageway::~passageway()
{
	while(door_room_head)
	{
		door_room_end = door_room_end->next;
//		delete door_room_head->the;
		delete door_room_head;
		door_room_head = door_room_end;
	}
	while(door_passageway_head)
	{
		door_passageway_end = door_passageway_end->next;
//		delete door_passageway_head->the;
		delete door_passageway_head;
		door_passageway_head = door_passageway_end;
	}
    while(rect_head)
    {
    	rect_end = rect_head->next;
		delete rect_head->the;
    	delete rect_head;
    	rect_head = rect_end;
    }
    while(sectors_head)
    {
    	sectors_end = sectors_head->next;
		delete sectors_head->the;
    	delete sectors_head;
    	sectors_head = sectors_end;
    }
}

void passageway::set_rect(rect &r1)
{
	if(!rect_head)
	{
		rect_head = new rects;
		rect_head->the = new rect(r1);
		rect_head->next=0;
		rect_end = rect_head;
	}
	else
	{
		rect_end->next = new rects;
		rect_end = rect_end->next;
		rect_end->the = new rect(r1);
		rect_end->next=0;
	}
}

char* passageway::get_name()
{
	return name;
}

void passageway::set_sector(sector &s1)
{

	if (!sectors_head)
	{
		sectors_head = new sectors;
		sectors_head->the = new sector(s1);
		sectors_head->next = 0;
		sectors_end = sectors_head;
	}
	else
	{
		sectors_end->next = new sectors;
		sectors_end = sectors_end->next;
		sectors_end->the = new sector(s1);
		sectors_end->next = 0;
	}
}

void passageway::set_lines(lines &l1)
{

	if (!sectors_head)
	{
		liness_head = new liness;
		liness_head->the = new lines(l1);
		liness_head->next = 0;
		liness_end = liness_head;
	}
	else
	{
		liness_end->next = new liness;
		liness_end = liness_end->next;
		liness_end->the = new lines(l1);
		liness_end->next = 0;
	}
}

bool passageway::if_in(float x1,float y1)
{
	rects *find1 = rect_head;
	sectors *find2 = sectors_head;
	liness *find3 = liness_head;
	while(find1)
	{
		if(find1->the->if_in(x1,y1))return 1;
		find1 = find1->next;
	}
	while(find2)
	{
		if (find2->the->if_in(x1,y1)) return 1;
		find2 = find2->next;
	}
	while(find3)
	{
		if (find3->the->if_in(x1,y1)) return 1;
		find3 = find3->next;
	}
	return 0;
}


void passageway::set_door(door &door1,room *room1)
{
	if(!door_room_head)
	{
		door_room_head = new door_room;
		door_room_head->the=&(door1);
		door_room_head->next=0;
		door_room_head->to_room = room1;
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

void passageway::set_door(door &door1,passageway *passageway1)
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
door * passageway::find_door(room & b)
{
	door_room *d1=door_room_head;
	while(d1)
	{
		if(!(d1->to_room!= &b)) return d1->the;
		d1 = d1->next;
	}
	return 0;

}

door * passageway::find_door(passageway &b)
{
	door_passageway *d1=door_passageway_head;
	while(d1)
	{
		if(!(d1->to_passageway!= &b)) return d1->the;
		d1 = d1->next;
	}
	return 0;

}

bool passageway::operator !=(passageway &passageway1)
{
	if(name != passageway1.get_name())return true;
    return false;
}
//void passageway::display()
//{
//	rects *rect_s = rect_head;
//	sectors *sector_s = sectors_head;
//	while(rect_s) rect_s->the->display(),rect_s = rect_s->next;
//	while(sector_s) sector_s->the->display(), sector_s = sector_s->next;
//
//}
#endif
