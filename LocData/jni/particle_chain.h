
#ifndef PARTICLE_CHAIN_H
#define  PARTICLE_CHAIN_H
#include "particle.h"

#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)


class particle_chain
{
public:
	particle_chain();
	~particle_chain();
	void insert_node (float x,float y,float width,particle *fa);
	void delete_node();
	int get_num();
	particle *get_head();
	particle *get_current();
	void reset();
	float get_det_l();
	float get_det_d();
	void reset_w();
	void next();
	void delet_all();
	void calweizhi(int *x,int *y);
	particle *head,*end,*current;
	int Num;
};

particle_chain::particle_chain()
{
	head = 0;
	end = 0;
	Num=0;
	current = 0;
}

void particle_chain::calweizhi(int *x,int *y){

	float xx=0,yy=0;
	particle *p=head;
	for(int i=0;i<Num;i++)
	{
		xx+=p->cur->x*p->width;
		yy+=p->cur->y*p->width;
		p->cur=p->cur->next;
		p=p->next;
	}
	//xx=xx/Num;
	//yy=yy/Num;
	*x=xx;
	*y=yy;
//	if(head->cur->next==0)
//	{
//	        p=head;
//			for(int i=0;i<Num;i++)
//			{
//
//				p->cur=p->head;
//				p=p->next;
//			}
//	}

}
void particle_chain::delet_all()
{
//	while(head)
//	{
//		particle *p=head;
//		head=head->next;
//		delete p;
//	}
	head=end=0;
}
void particle_chain::insert_node(float x,float y,float width,particle *fa)
{
	if (head ==0)
	{
		head =new particle(x,y,width,fa);
		end = head;
		head->pre=0;
		head->next=0;
	}
	else
	{
		end->next=new particle(x,y,width,fa);
		end->next->pre=end;
		end = end->next;
		end->next=0;

	}
	Num++;
}
particle *particle_chain::get_head()
{
	return head;
}
void particle_chain::delete_node()
{
	//LOGI("-------------dele-------%d",current);
	Num=Num-1;
	if(!head)return;
	if(current==head){
		//LOGI("---------------head--------");

		head=current->next;
		 current->~particle();
		head->pre=0;
		current=head;
		return;
	}
	if(current==end){
		//LOGI("--------------end-------");

		end=current->pre;
		if(current!=0) current->~particle();
		current=0;
		end->next=0;
		return;
	}

	particle *p=current;
	p->pre->next=p->next;
	p->next->pre=p->pre;
    	current=p->next;
	if(p!=0)p->~particle();;
	return;
}
int particle_chain::get_num()
{

	return Num;
}
void particle_chain::reset()
{
    particle *p=head;

	for(int i=0;i<Num;i++)
	{
		p->cur=p->head;
		p=p->next;
	}
	current =head;
}
particle *particle_chain::get_current()
{

	return current;
} 
particle_chain::~particle_chain()
{
}

void particle_chain::next()
{
	current=current->next;
}
void particle_chain::reset_w()
{
	float w=0;
	particle *p=head;
	while (p!=end)
	{
		w+=p->width;
		p=p->next;
	}
	p=head;
	while (p!=end)
	{
		p->width=p->width*1/w;
		p=p->next;
	}
}

float particle_chain::get_det_l()
{
	particle *p=head;
	int i=0;
	float det_l=0;
		while (p!=0)
		{
			det_l+=p->det_l_f;
			p=p->next;
			i++;
		}
	det_l=det_l/i;
	return det_l;
}


float particle_chain::get_det_d()
{
	particle *p=head;
	int i=0;
	float det_d=0;
		while (p!=0)
		{
			det_d+=p->det_d_f;
			p=p->next;
			i++;
		}
	det_d=det_d/i;
	return det_d;
}
#endif
