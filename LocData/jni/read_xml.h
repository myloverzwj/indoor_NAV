#ifndef READ_XML_H
#define READ_XML_H
#define TAG "fs_jni"

#include <android/log.h>
#include <string.h>
#include "jniUtils.h"
//jni.c
#define TAG "fs_jni"

#include <android/log.h>
#include <string.h>
#include "jniUtils.h"
#include <jni.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <stdlib.h>
#include <fcntl.h>
#include <stdio.h>
#include "floor.h"
#include "tinyxml.h"

#include "WIFI.h"



#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)

void read_xml(Floor *floor1,Locating *wifi,const char* filename)
{

	JNIEnv env;
	TiXmlDocument* myDocument = new TiXmlDocument();
	myDocument->LoadFile(filename);
	LOGI("---------111------initLoc--------%s", filename);
	TiXmlElement *rootElement = myDocument->RootElement();
	TiXmlElement *studentsElement = rootElement->FirstChildElement();
	TiXmlElement *p;
	TiXmlElement *q;
	TiXmlElement *r;
    p=rootElement->FirstChildElement("Cells");
    q=p->FirstChildElement();
	r=q->FirstChildElement();
	wifi->InitialAddress();
   while(q)
	{

		char *ax=new char[10],*bx=new char[20],*cx=new char[20];

		strcpy(ax,q->FirstChildElement("Type")->GetText());
		strcpy(bx,q->FirstChildElement("Shape")->GetText());
		//LOGI("---------111------initLoc--------%s",ax);
		if(strcmp(ax,"Room")==0&&strcmp(bx,"rect")==0){
			TiXmlElement *point1;
			TiXmlElement *point2;
			point1=q->FirstChildElement("Points")->FirstChildElement();
			point2=point1->NextSiblingElement();
			int pp1x,pp1y,pp2x,pp2y;

			pp1x=atoi(point1->FirstChildElement("X")->GetText());
			pp1y=atoi(point1->FirstChildElement("Y")->GetText());
			pp2x=atoi(point2->FirstChildElement("X")->GetText());
			pp2y=atoi(point2->FirstChildElement("Y")->GetText());
			strcpy(cx,q->FirstChildElement("Name")->GetText());
			room *room1=new room(pp1x,pp1y,pp2x,pp2y,0,cx);
			floor1->set_room(*room1);
			//char* na = env->GetStringUTFChars(room1->get_name(), NULL);
			//LOGI("------room---111------initLoc--------%s", na);
		//	strcpy(ax,q->FirstChildElement("Type")->GetText());

			LOGI("------room---11rect------initLoc--------%s  %d",room1->get_name(),room1);

		}
		if(strcmp(ax,"Room")==0&&strcmp(bx,"multline")==0)
		{

			TiXmlElement *point1;
		    point1=q->FirstChildElement("Points")->FirstChildElement();

		    float point[20][2];
		    int num1;
		    int i=0;
		    while(point1)
		    {
		     	point[i][0]=(float)atoi(point1->FirstChildElement("X")->GetText());
			    point[i][1]=(float)atoi(point1->FirstChildElement("Y")->GetText());
			    point1=point1->NextSiblingElement();
			    i++;
		    }
             lines *lines1=new lines(point,i);
             strcpy(cx,q->FirstChildElement("Name")->GetText());
		     room *p1=new room(lines1,cx);
            floor1->set_room(*p1);
		    LOGI("------room---111------initLoc--------%s  %d",p1->get_name(),p1);
		   //const char* na = env.GetStringUTFChars(p1->get_name(), NULL);
		   //LOGI("------room---111------initLoc--------%s", na);

		}
		if(strcmp(ax,"Passage")==0)
		{

			if(strcmp(bx,"rect")==0)
			{
				TiXmlElement *point1;
				TiXmlElement *point2;
				point1=q->FirstChildElement("Points")->FirstChildElement();
				point2=point1->NextSiblingElement();
				int pp1x,pp1y,pp2x,pp2y;

				pp1x=atoi(point1->FirstChildElement("X")->GetText());
				pp1y=atoi(point1->FirstChildElement("Y")->GetText());
				pp2x=atoi(point2->FirstChildElement("X")->GetText());
				pp2y=atoi(point2->FirstChildElement("Y")->GetText());
				strcpy(cx,q->FirstChildElement("Name")->GetText());
				passageway *p1=new passageway(cx);
                rect *rect1=new rect(pp1x,pp1y,pp2x,pp2y,0);
				p1->set_rect(*rect1);
				floor1->set_passageway(*p1);
				LOGI("------passage---111------initLoc--------%s", p1->get_name());

			}
			if(strcmp(bx,"sector")==0)
			{
				TiXmlElement *point1;

			}
			if(strcmp(bx,"multline")==0)

			{
				TiXmlElement *point1;
				point1=q->FirstChildElement("Points")->FirstChildElement();

				float point[20][2];
				int num1;
				int i=0;
				while(point1)
				{
					point[i][0]=(float)atoi(point1->FirstChildElement("X")->GetText());
					point[i][1]=(float)atoi(point1->FirstChildElement("Y")->GetText());
					point1=point1->NextSiblingElement();
					i++;
				}
                lines *lines1=new lines(point,i);
                strcpy(cx,q->FirstChildElement("Name")->GetText());
				passageway *p1=new passageway(cx);
                p1->set_lines(*lines1);
                floor1->set_passageway(*p1);
    			LOGI("------passage---111------initLoc--------%s", p1->get_name());

			}

		}
		if (strcmp(ax,"Door")==0)
		{
			TiXmlElement *point1;
			TiXmlElement *point2;
			point1=q->FirstChildElement("Points")->FirstChildElement();
			point2=point1->NextSiblingElement();
			int pp1x,pp1y,pp2x,pp2y;
			pp1x=atoi(point1->FirstChildElement("X")->GetText());
			pp1y=atoi(point1->FirstChildElement("Y")->GetText());
			pp2x=atoi(point2->FirstChildElement("X")->GetText());
			pp2y=atoi(point2->FirstChildElement("Y")->GetText());
			door *door1=new door(pp1x,pp1y,pp2x,pp2y);
			TiXmlElement *name1, *name2;
			name1=q->FirstChildElement("Connections")->FirstChildElement();
			name2=name1->NextSiblingElement();
			char *dx=new char[20],*ex=new char[20];
			 strcpy(dx,name1->GetText());
			 strcpy(ex,name2->GetText());
				LOGI("---------door------initLoc--------%s %s",dx,ex);

			if(floor1->findroom(dx)!=0&&floor1->findroom(ex)!=0)
			{
				LOGI("---------door--rr----initLoc--------%s %s",floor1->findroom(dx)->get_name(),name2->GetText() );
				floor1->set_door(*(floor1->findroom(dx)),*(floor1->findroom(ex)),*door1);


			}
			if(floor1->findroom(dx)!=0&&floor1->findpassageway(ex)!=0)
			{
				LOGI("---------door----rp--initLoc--------%s %s",floor1->findroom(dx)->get_name(),floor1->findpassageway(ex)->get_name() );
				floor1->set_door(*(floor1->findroom(dx)),*(floor1->findpassageway(ex)),*door1);


			}
			if(floor1->findpassageway(dx)!=0&&floor1->findpassageway(ex)!=0)
			{
				LOGI("---------door------pr--initLoc--------%s %s",floor1->findpassageway(dx)->get_name(),floor1->findpassageway(ex)->get_name() );
				floor1->set_door(*(floor1->findpassageway(dx)),*(floor1->findpassageway(ex)),*door1);


			}
			if(floor1->findpassageway(dx)!=0&&floor1->findroom(ex)!=0)
			{
				LOGI("---------door---pp---initLoc--------%s %s",floor1->findpassageway(dx)->get_name(),floor1->findroom(ex)->get_name()  );
				floor1->set_door(*(floor1->findroom(ex)),*(floor1->findpassageway(dx)),*door1);
			}
		}
		if (strcmp(ax,"Wifi")==0)
		{
			TiXmlElement *point1;
			TiXmlElement *point2;
			point1=q->FirstChildElement("Points")->FirstChildElement();
			point2=point1->NextSiblingElement();
			char name2[30];
			strcpy(name2 ,q->FirstChildElement("Attribute")->FirstChildElement("AP")->GetText());
			int pp1x,pp1y,pp2x,pp2y;
			pp1x=atoi(point1->FirstChildElement("X")->GetText());
			pp1y=atoi(point1->FirstChildElement("Y")->GetText());
		    pp2x=atoi(point2->FirstChildElement("X")->GetText());
			pp2y=atoi(point2->FirstChildElement("Y")->GetText());

            wifi->GetMacAddress(name2,(pp1x+pp2x)/50,(pp1y+pp2y)/50);
            LOGI("---------wifi111------initLoc--------%d %d",(pp1x+pp2x)/2,(pp1y+pp2y)/2 );

		}

		q=q->NextSiblingElement();
	}

//  LOGI("---------------initLoc-----200--100---%s",floor1->findroom(250,100)->get_name());
// LOGI("---------------initLoc-----200--100---%d",floor1->findroom(1000,0));
// LOGI("---------------initLoc-----200--100---%d",floor1->findpassageway(0,1000));
//  LOGI("---------------initLoc-----200--100---%s",floor1->findroom(400,300)->get_name());
//
//  //floor1->findroom(100,200);
// // floor1->findroom(100,300);
// // floor1->findroom(200,200);
//  LOGI("---------------initLoc-----100--200---%s",floor1->findroom(100,200)->get_name());
//   LOGI("---------------initLoc-----150--350---%s",floor1->findpassageway(300,600)->get_name());
//   //LOGI("---------------initLoc-----100--100---%s",floor1->findroom("")->get_name());
//  // LOGI("---------------initLoc-----0--0---%d",floor1->findroom("room1")->find_door(*(floor1->findroom("room2"))));

   wifi->InitialValue();
}


#endif
