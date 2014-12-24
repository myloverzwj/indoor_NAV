#ifndef PARTICLE_H
#define  PARTICLE_H
#include <math.h>

struct weizhi{
	int x;
	int y;
	weizhi* next;
};
class particle
{
public:
	particle(float x1,float y1,float width1,particle *fa);
	~particle();
	void change_xy(float l,float d);
	void chang_width(float factor);
	float get_width();
	float get_x();
	float get_y();
	float get_xf();
	float get_yf();
	particle *get_next();
	float width;
	float x,y,x_f,y_f;
	float det_l_f,det_d_f;
	particle *next,*pre;
	particle *farther;
	int an_num;
	weizhi *head;
	weizhi *cur;

};

particle::particle(float x1,float y1,float width1,particle *fa)
{
	an_num=0;

	x = x1;
	y = y1;
	width = width1;
	next = 0;
	pre=0;
	det_l_f=0;
	det_d_f=0;
	head=0;
    if(fa==0)
    {
    	cur =head =new weizhi;
	cur->x=x;
	cur->y=y;
	head ->next=0;
	 an_num++;
	 return;

    }
	farther =fa;
	weizhi *p=farther->head;

	while(p!=0)
	{
		if(head==0)
		{
			cur =head =new weizhi;
			cur->x=p->x;
			cur->y=p->y;
			head ->next=0;
			// an_num++;
			continue;
		}
	    weizhi *q=new weizhi;
	    cur->next=q;
	    q->x=p->x;
	    q->y=p->y;
	    q->next=0;
	    cur=q;
	   // an_num++;
	    p=p->next;
	}
	an_num=fa->an_num;
	cur=head;
}

float particle::get_width()
{
	return width;
}

float particle::get_x()
{
	return x;
}
float particle::get_y()
{
	return y;
}
float particle::get_xf()
{
	return x_f;
}
float particle::get_yf()
{
	return y_f;
}

void particle::change_xy(float l,float d)
{
	x_f = x;
	y_f = y;
	x = x+l*sin(d);
	y = y-l*cos(d);
    weizhi *p=new weizhi;
    p->next=head;
    p->x=x;
    p->y=y;
    head=p;
    cur=head;
    an_num++;
}

void particle::chang_width(float factor)
{
	width = width*factor;

}

particle *particle::get_next()
{
    return next;
}
particle::~particle()
{
	for(int i=0;i<an_num;i++)
	{
		weizhi *p=head;
		head =head->next;
		delete p;
	}
}
#endif
