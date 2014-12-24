#ifndef PARTICLE_FILTER_H
#define PARTICLE_FILTER_H


#include "particle_chain.h"
#include "floor.h"
#include "gauss.h"
#include "WIFI.h"


#define LOG_TAG "debug log"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)


struct POINT{
	int x;
	int y;
	POINT *next;
	POINT *pre;
};
class points{
public:
	points();
	POINT* get_CUR();
	void  add(int x,int y);
	void reset();
	int get_NUM();
	void next();
private:
	POINT* head;
	POINT* CUR;
	POINT* end;
	int NUM;
};
points::points()
{
	NUM=0;
}
int points::get_NUM()
{
	return NUM;
}
void points::next(){
	CUR=CUR->pre;
}
void points::add(int x, int y)
{
	if(head==0)
	{
		head =new POINT;
		head->next=0;
		head->pre=0;
		head->x=x;
		head->y=y;
		CUR=head;
		end=head;
	}
	else
	{
		POINT *p=end;
		p=new POINT;
		end->next=p;
		p->pre=end;
		end=p;
		end->x=x;
		end->y=y;
		p->next=0;

	}
	NUM++;
}
POINT *points::get_CUR()
{
	return CUR;
}

void points::reset()
{
	CUR = end;
}
class particle_filter
{
public:
	particle_filter(int num);
	~particle_filter();
	void initialization (float x,float y,float det_x1, float det_y1);
	void prediction (float l,float det_l,float d,float det_d,Floor *floor,Locating *wifi);
	void delete_particle(Floor *floor);
	void caldet();
	void calave();
	float get_x();
	int get_num();
	float get_y();
	float getx();
	float gety();
	void next();
	void reset();
	void calweizhi(int *x,int *y);
//private:
	int max_num;
	particle_chain particles;
	float x_,y_;
	float lanmuda,sita;
	float det_x,det_y;
	bool end;


};

particle_filter::particle_filter(int num)
{
	max_num = num;
	end=false;
}

particle_filter::~particle_filter()
{
}
void particle_filter::calweizhi(int *x,int *y)
{
	particles.calweizhi(x,y);

}
int particle_filter::get_num()
{
	return particles.get_num();
}

float particle_filter::getx()
{
	return particles.get_current()->x;
}
float particle_filter::gety()
{
	return particles.get_current()->y;
}

float particle_filter::get_x()
{
	return x_;
}

void particle_filter::next()
{
	particles.next();
}

void particle_filter::reset()
{
	particles.reset();
}
float particle_filter::get_y()
{
	return y_;
}
void particle_filter::delete_particle(Floor *floor)
{

	particles.reset();
	while (particles.get_current()!=0){
		//if(particles.get_current()==particles.end)break;
	   // LOGI("-----delete---%f %f %f %f",particles.current->x,particles.current->y,particles.current->x_f,particles.current->y_f );
		if(!floor->check(particles.current->x,particles.current->y,particles.current->x_f,particles.current->y_f)){
			 //   LOGI("-----before delete---%f %f %f %f",particles.current->x,particles.current->y,particles.current->x_f,particles.current->y_f );

			particles.delete_node();
			   //LOGI("-----after delete---%f %f %f %f",particles.current->x,particles.current->y,particles.current->x_f,particles.current->y_f );
			if(particles.get_current()==particles.end)break;
		}
		//if(particles.get_current()==particles.end)break;
		else  particles.next();
	}
	//particles.end=particles.end->pre;
	//particles.Num--;
	LOGI("-----------------------point---------%d",particles.Num);
if(particles.Num==0)return ;
	particles.reset();
	while (particles.get_current()!=0)
	{
		if (particles.current->width<1/(float(max_num)*float(max_num)))
		{
			particles.delete_node();
		}
		else particles.next();
	}

	LOGI("-----------------------point---------%d",particles.Num);
}
void particle_filter::initialization(float x,float y,float det_x1, float det_y1)
{
	end=false;
	float x1,y1;
	particles.reset();
	for (int i=0;i<max_num;i++)
	{

		srand(time(NULL)+i+1);
		if(i%2==0)x1=gaussrand()*det_x1+x;
		else x1=-gaussrand()*det_x1+x;
		srand(time(NULL)-i-1);
		if(i%2==0)y1=gaussrand()*det_y1+y;
		else y1=-gaussrand()*det_y1+y;;
		particles.insert_node(x1,y1,1/float(max_num),0);
	}
	calave();
	end=true;
}

void particle_filter::prediction(float l,float det_l,float d,float det_d,Floor *floor,Locating *wifi)
{
	end=false;
	particle *p= particles.get_head();
	float det_f_l=particles.get_det_l();
	float det_f_d=particles.get_det_d();
	for (int i=0;i<particles.get_num();i++)
	{
		srand((time(NULL))+i+2);
		float gauss1=gaussrand();
		srand((time(NULL))-i+2);
		float gauss2=gaussrand();
		if(gauss1>0&&gauss1*gauss1>1)gauss1=0.5;
		if(gauss1<0&&gauss1*gauss1>1)gauss1=-0.5;
		if(gauss2>0&&gauss2*gauss2>1)gauss2=0.5;
		if(gauss2<0&&gauss2*gauss2>1)gauss2=-0.5;
		if(i%2==0)gauss1=-gauss1;
		if(i%2==0)gauss2=-gauss2;
		p->det_l_f=gauss1+det_f_l;
		p->det_d_f=gauss2+det_f_d;
		p->change_xy(l+gauss1*det_l/*+det_f_l*/,d+gauss2*det_d+det_f_d);
		p=p->next;
	}
	calave();
	x_+=l*cos(d);
	y_+=l*sin(d);
	delete_particle(floor);
	if(particles.get_num()<3){
		wifi->Calculate();
	 LOGI("---------new------wifi--------%f  %f",wifi->x*5,wifi->y*5);
	particles.delet_all();
	initialization(wifi->x*5,wifi->y*5,50,50);
	x_=wifi->x*5;
	y_=wifi->y*5;
			return ;
	}
	caldet();
	calave();
	p=particles.get_head();
//	x_=x_*0.9+wifi->x*0.8;
//	y_=y_*0.9+wifi->y*0.8;
	for (int i=0;i<particles.get_num();i++)
	{

	//	p->chang_width(guassvalue((p->get_x()-x_)/det_x, (p->get_y()-y_)/det_y));
		p=p->next;
	}

	particles.reset_w();
	calave();
	//delete_particle(floor);
	for(int j=0;j<10;j++){
	particles.reset_w();
	p=particles.get_head();
	calave();
	particles.reset_w();
	for (int i=0;i<particles.get_num()-2;i++)
	{
		if(p->width>3/float(max_num))
		{
			LOGI("---------add--------------point---------%f",p->width);
			p->chang_width(0.5);
			particles.insert_node(p->get_x()/*+gauss1*det_l/2*/,p->get_y()/*+gauss2*det_l/2*/,p->width,p);
				particles.end->y_f=p->get_yf();
				particles.end->x_f=p->get_xf();
           i++;
//			while(p->width>2/float(max_num)){
//				srand((time(NULL))+i+2);
//						float gauss1=gaussrand();
//						srand((time(NULL))-i+2);
//						float gauss2=gaussrand();
//						if(gauss1>0&&gauss1*gauss1>1)gauss1=1;
//						if(gauss1<0&&gauss1*gauss1>1)gauss1=-1;
//						if(gauss2>0&&gauss2*gauss2>1)gauss2=1;
//						if(gauss2<0&&gauss2*gauss2>1)gauss2=-1;
//						if(i%2==0)gauss1=-gauss1;
//						if(i%2==0)gauss2=-gauss2;
//				p->width-=1/float(max_num);
//			particles.insert_node(p->get_x()/*+gauss1*det_l/2*/,p->get_y()/*+gauss2*det_l/2*/,1/float(max_num));
//			particles.end->y_f=p->get_yf();
//			particles.end->x_f=p->get_xf();
//			//particles.end->width=p->width;
//			i++;
//			}

		}
		p=p->next;
	}
	}
	particles.reset_w();
	p=particles.get_head();
	calave();
	particles.reset_w();

	LOGI("-----------------------point---------%d",particles.Num);
	particles.reset_w();
	calave();
	end=true;
}

void particle_filter::caldet()
{
	particle *p = particles.get_head();
	det_x = det_y = 0;
	for (int i=0; i<particles.get_num(); i++)
	{
		det_x+=(p->get_x()-x_)*(p->get_x()-x_)*p->get_width();
		det_y+=(p->get_y()-y_)*(p->get_y()-y_)*p->get_width();
		p=p->next;


		//p = p->next;
	}
	det_x = sqrt(det_x);
    det_y = sqrt(det_y);
}
void particle_filter::calave()
{
	particle *p = particles.get_head();
	x_ = y_ =0;
	for (int i=0;i<particles.get_num(); i++)
	{
		x_+=p->get_x()*p->get_width();
		y_+=p->get_y()*p->get_width();
	}

}

#endif
