#ifndef FLOOR_H
#define  FLOOR_H
#include "room.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include "passageway.h"
#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)

struct rooms
{
	room *the;
	rooms *next;
};

struct  passageways
{

	passageway *the;
	passageways *next;
};
struct doors
{
	door *the;
	doors *next;
};
class Floor
{
public:
	Floor(char* name1);
	void set_room(room &room1);
	~Floor();
	void set_passageway(passageway &passageway1);
	void set_door(door &door1);
	void set_door(room &room1,room &room2,door &door1);
	void set_door(room &room1,passageway &passageway1,door &door1);
	void set_door(passageway &passageway1,passageway &passageway2,door &door1);
	char* get_name();
	room * findroom(char* name);
    passageway *findpassageway(char* name);
	room * findroom(float x1,float y1);
	passageway *findpassageway(float x1,float y1);
	bool check(float x1,float y1,float x2,float y2);

//	void display();
private:

	rooms *room_end;
	rooms *room_head;
	passageways *passageway_head;
	passageways *passageway_end;
	doors *door_head, *door_end;
	char* name;
};

Floor::Floor(char* name1)
{	
	name = name1;
    room_head = 0;
    room_end = 0;
    passageway_head = 0;
    door_head = 0;
}
Floor::~Floor()
{
	while(room_head)
	{
	    room_end = room_head->next;
		//delete room_head->the;
	    delete room_head;
	    room_head = room_end;
	}
	while(passageway_head)
	{
	 passageway_end = passageway_head->next;
	 //delete passageway_head->the;
	 delete passageway_head;
	 passageway_head = passageway_end;
	}
	while(door_head)
	{
		door_end = door_head->next;
		//delete door_head->the;
		delete door_head;
		door_head = door_end;
	}
}

void Floor::set_room(room &room1)
{
	if(room_head==0)
	{
		room_head = new rooms;
		room_head->the=&(room1);
		room_head->next=0;
		room_end = room_head;
	//	LOGI("---------------initLoc--------%s %d  ",room_head->the->get_name(),room_head );

	}
	else
	{
		//   LOGI("---------------initLoc--------%s   %d",room_head->the->get_name(),room_head );


			room_end->next = new rooms;
			 room_end =room_end->next;
		    room_end->the=&(room1);
		    room_end->next=0;
		//   LOGI("---------------initLoc--------%s   ",room_head->next->the->get_name() );

	}

}

void Floor::set_passageway(passageway &passageway1)
{
	if(passageway_head==0)
	{
		passageway_head = new passageways;
		passageway_head->the=&(passageway1);
		passageway_head->next=0;
		passageway_end = passageway_head;
	}
	else
	{
		passageway_end->next = new passageways;
		passageway_end =passageway_end->next;
		passageway_end->the=&(passageway1);
		passageway_end->next=0;
	}

}

room *Floor::findroom(char* name)
{
	rooms *find;
	find = room_head;
	//LOGI("---------------initLoc--------%s   %s",find->the->get_name(),name );

	while(find&&strcmp(find->the->get_name(),name)!=0)
		{
	//	LOGI("---------------initLoc--------%s   %s",find->the->get_name(),name );
		find=find->next;
		}
	if(find!=room_end->next)
		{
		return find->the;
    }
	else return 0;
}
passageway *Floor::findpassageway(char* name)
{
	passageways *find;
	find = passageway_head;
	//LOGI("---------------initLoc--------%s   %s",room_head->the->get_name(),name );

	while(find&&strcmp(find->the->get_name(),name)!=0)
		{
		//LOGI("---------------initLoc--------%s   %s",find->the->get_name(),name );

		find=find->next;
		}
	if(find!=passageway_end->next)return find->the;
	else return 0;
}

void Floor::set_door(door &door1)
{
	if(door_head==0)
		{
			door_head = new doors;
			door_head->the = &(door1);
			door_head->next = 0;
			door_end =door_head;
		}
		else
		{
			door_end->next = new doors;
			door_end = door_end->next;
			door_end->the = &(door1);
			door_end->next = 0;
		}
}
void Floor::set_door(room &room1,room &room2,door &door1)
{
	if(door_head==0)
	{
		door_head = new doors;
		door_head->the = &(door1);
		door_head->next = 0;
		door_end =door_head;
	}
	else
	{
		door_end->next = new doors;
		door_end = door_end->next;
		door_end->the = &(door1);
		door_end->next = 0;
	}
	rooms *room_1,*room_2;
	room_1 = room_head;
    while((*(room_1->the)!=room1)&&room_1)room_1 = room_1->next;
    room_2 = room_head;
    while((*(room_2->the)!=room2)&&room_2)room_2 = room_2->next;
    if(room_1)room_1->the->set_door(door1,room_2->the);
    if(room_2)room_2->the->set_door(door1,room_1->the);
}

void Floor::set_door(room &room1,passageway &passageway1,door &door1)
{
	if(door_head==0)
	{
		door_head = new doors;
		door_head->the = &(door1);
		door_head->next = 0;
		door_end =door_head;
	}
	else
	{
		door_end->next = new doors;
		door_end = door_end->next;
		door_end->the =&(door1);
		door_end->next = 0;
	}
	rooms *room_1;
	passageways *passageway_1;
	room_1 = room_head;
    while((*(room_1->the)!=room1)&&room_1)room_1 = room_1->next;
    passageway_1 = passageway_head;
    while((*(passageway_1->the)!=passageway1)&&passageway_1)passageway_1 = passageway_1->next;
    if(room_1)room_1->the->set_door(door1,passageway_1->the);
    if(passageway_1)passageway_1->the->set_door(door1,room_1->the);
}
void Floor::set_door(passageway &passageway1,passageway &passageway2,door &door1)
{
	if(door_head==0)
	{
		door_head = new doors;
		door_head->the = &(door1);
		door_head->next = 0;
		door_end =door_head;
	}
	else
	{
		door_end->next = new doors;
		door_end = door_end->next;
		door_end->the =&(door1);
		door_end->next = 0;
	}
	passageways *passageway_1,*passageway_2;
	passageway_1 = passageway_head;
    while(*(passageway_1->the)!=passageway1&&passageway_1)passageway_1 = passageway_1->next;
    passageway_2 = passageway_head;
    while(*(passageway_2->the)!=passageway2&&passageway_2)passageway_2 = passageway_2->next;
    if(passageway_1)passageway_1->the->set_door(door1,passageway_2->the);
    if(passageway_2)passageway_2->the->set_door(door1,passageway_1->the);
}

char* Floor::get_name()
{
	return name;
}

room *Floor::findroom(float x1,float y1)
{
	rooms *find;
	find = room_head;
	while(find!=0&&(find->the->if_in(x1,y1))==0){
	//	LOGI("---------------initLoc--------%s",find->the->get_name());

		find=find->next;
	}
	if(find!=room_end->next)return find->the;
	else return 0;
} 

passageway *Floor::findpassageway(float x1,float y1)
{
	passageways *find;
	find = passageway_head;
	while(find!=0&&(find->the->if_in(x1,y1))==0)find=find->next;
	if(find!= passageway_end->next)return find->the;
	else return 0;
} 

//void Floor::display()
//{
//	rooms *room_s = room_head;
//	passageways *passageway_s = passageway_head;
//	doors *door_s = door_head;
//	glColor4f(0.0,1.0,0.0,1.0);
//	while(room_s) room_s->the->display(), room_s = room_s->next;
//	glColor4f(0.0,0.0,1.0,0.5);
//	while(passageway_s) passageway_s->the->display(), passageway_s  = passageway_s->next;
//	glColor4f(1.0,0.0,0.0,0.5);
//	while(door_s) door_s->the->display(), door_s = door_s->next;
//
//}

bool Floor::check(float x1,float y1,float x2,float y2)
{
	//LOGI("-----------------------point---------%f---%f---%f---%f",x1,y1,x2,y2);
   //  if(findroom(x1,y1)!=0findroom(x2,y2)!=0)return 0;
	if(findroom(x2,y2)==0&&findpassageway(x2,y2)==0)
		{
	//	LOGI("---------------out------s");

		return 0;
		}
	if(findroom(x1,y1)!=0&&findroom(x2,y2)!=0&&findroom(x1,y1)==findroom(x2,y2))
		{
		//LOGI("-----------------------point---------%s---%s",findroom(x1,y1)->get_name(),findroom(x2,y2)->get_name());
		return 1;
		}
	//LOGI("-------------------rr----point---------%f---%f---%f---%f",x1,y1,x2,y2);

	if(findpassageway(x1,y1)!=0&&findpassageway(x2,y2)!=0&&findpassageway(x1,y1)==findpassageway(x2,y2)){
		//LOGI("---------------pp--------point---------%f---%f---%f---%f",x1,y1,x2,y2);
		return 1;
	}
	//LOGI("-------------------rr----point---------%f---%f---%f---%f",x1,y1,x2,y2);


	if(findroom(x1,y1)!=0&&findroom(x2,y2)!=0&&(findroom(x1,y1)->find_door(*findroom(x2,y2)))!=0)
	{
		LOGI("-------------drr--------point---------%f---%f---%f---%f",x1,y1,x2,y2);

		if((findroom(x1,y1)->find_door(*findroom(x2,y2))->if_in((x1+x2)/2,(y1+y2)/2))!=0)return 1;

	}
	if(findroom(x1,y1)!=0&&findpassageway(x2,y2)!=0&&(findroom(x1,y1)->find_door(*findpassageway(x2,y2)))!=0)
		{
		LOGI("----------dpr-----------point---------%f---%f---%f---%f",x1,y1,x2,y2);

			if((findroom(x1,y1)->find_door(*findpassageway(x2,y2))->if_in((x1+x2)/2,(y1+y2)/2))!=0)return 1;

		}
	if(findpassageway(x1,y1)!=0&&findroom(x2,y2)!=0&&(findpassageway(x1,y1)->find_door(*findroom(x2,y2)))!=0)
		{
		LOGI("-------------drp----------point---------%f---%f---%f---%f",x1,y1,x2,y2);

			if((findpassageway(x1,y1)->find_door(*findroom(x2,y2))->if_in((x1+x2)/2,(y1+y2)/2))!=0)return 1;

		}
	if(findpassageway(x1,y1)!=0&&findpassageway(x2,y2)!=0&&(findpassageway(x1,y1)->find_door(*findpassageway(x2,y2)))!=0)
		{
		LOGI("-----------dpp------------point---------%f---%f---%f---%f",x1,y1,x2,y2);

			if((findpassageway(x1,y1)->find_door(*findpassageway(x2,y2))->if_in((x1+x2)/2,(y1+y2)/2))!=0)return 1;

		}
	return 0;
}
#endif
