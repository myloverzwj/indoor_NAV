#ifndef SENSOR_DATA_H
#define SENSOR_DATA_H

#define  MAX 1000
#include <math.h>

class sensor_data
{
public:
	sensor_data();
	~sensor_data();
	void setdata(float x1,float x2,float x3);
	float LPF(int T);
	float cal_max(int T);
	float cal_DC(int T);
	float cal_de(sensor_data &a);
	float cal_Ci(int T);
	float cal_length();
	float cal_de_PSP(sensor_data &a,sensor_data &b);
private:
	float x[MAX],y[MAX],z[MAX],m[MAX];
	float xl,yl,zl,ml;
	int CUR;
	float *amend;

};

sensor_data::sensor_data()
{
	CUR =0;
	amend = new float[72]{
		0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0,
	    0,0,0,0,0,0,0,0};


}

sensor_data::~sensor_data()
{
}

float sensor_data::cal_Ci(int T)
{
	float as=0;
	for(int i=0;i<=2*T;i++){
		as+=m[(CUR-2*T-1+i)%MAX]/(1+2*T);
	}
	float sitas=0;
	for (int i = 0; i <=2*T; i++) {
		sitas+=(m[(CUR-2*T-1+i)%MAX]-as)*(m[(CUR-2*T-1+i)%MAX]-as)/(1+2*T);
	}
	return sitas;

}
void sensor_data::setdata(float x1,float x2,float x3)
{
	x[CUR]=x1;
	y[CUR]=x2;
	z[CUR]=x3;
	m[CUR]=sqrt(x1*x1+x2*x2+x3*x3);
	CUR++;
	CUR=CUR%MAX;
}
float sensor_data::LPF(int T)
{
	xl=yl=zl=0;
	ml=0;
	for (int i=0;i<T;i++)
	{
		xl+=x[(CUR-i+MAX)%MAX];
		yl+=y[(CUR-i+MAX)%MAX];
		zl+=z[(CUR-i+MAX)%MAX];
		ml+=m[(CUR-i+MAX)%MAX];
	}
	xl=xl/T;
	yl=yl/T;
	zl=zl/T;
	ml=ml/T;
	return ml;
}

float sensor_data::cal_DC(int T)
{
	ml=0;
	for (int i=0;i<T;i++)
	{
		ml+=m[(CUR-i+MAX)%MAX];
	}
	ml=ml/T;
	return ml;
}

float sensor_data::cal_max(int T)
{
	float max=0;
	float DC=cal_DC(300);
	for (int i=0;i<T;i++)
	{
		max+=abs(m[(CUR-i+MAX)%MAX]-DC);
	}
	return max/T;
}

float sensor_data::cal_de(sensor_data &a)
{

	LPF(50);
	a.LPF(20);
	float dede=atan2((a.xl-6),a.yl-2.5);
	return -dede;
	float x_dc,y_dc,z_dc;
	float gx,gy,gz;
	gx=0;
	gy=1;
	gz=0;

		x_dc=xl;
		y_dc=yl;
		z_dc=zl;
		float m_dc=sqrt(x_dc*x_dc+y_dc*y_dc+z_dc*z_dc);
		float sita=a.xl*x_dc+a.yl*y_dc+a.zl*z_dc;
		float gama=gx*x_dc+gy*y_dc+gz*z_dc;
		gx=gx-gama*x_dc/m_dc;
		gy=gy-gama*y_dc/m_dc;
		gz=gz-gama*z_dc/m_dc;
		a.xl=a.xl-sita*x_dc/m_dc;
		a.yl=a.yl-sita*y_dc/m_dc;
		a.zl=a.zl-sita*z_dc/m_dc;
		a.zl=0;
		float mll=sqrt(gx*gx+gy*gy+gz*gz);
		float mml=sqrt(a.xl*a.xl+a.yl*a.yl+a.zl*a.zl);
    	float arg=acos((gx*a.xl+gy*a.yl+gz*a.zl)/(mll*mml));
	//	float arg=acos(0.5);
		if(x_dc*(-gy*a.zl+a.yl*gz)+y_dc*(-gz*a.xl+a.zl*gx)+z_dc*(-gx*a.yl+a.xl*gy)>=0)return arg;
		else
		{
			 return -arg;
		}
	/*LPF(200);
	//CUR-=495;
	CUR=CUR%MAX;
	a.LPF(100);
	float x_dc,y_dc,z_dc;
	x_dc=xl;
	y_dc=yl;
	z_dc=zl;
	float m_dc=sqrt(xl*xl+yl*yl+zl*zl);
	LPF(100);
	//CUR+=495;
	CUR=CUR%MAX;

	float sita=a.xl*x_dc+a.yl*y_dc+a.zl*z_dc,gama=x_dc*xl+y_dc*yl+z_dc*zl;
	xl=xl-gama*x_dc/m_dc;
	yl=yl-gama*y_dc/m_dc;
	zl=zl-gama*z_dc/m_dc;
	a.xl=a.xl-sita*x_dc/m_dc;
	a.yl=a.yl-sita*y_dc/m_dc;
	a.zl=a.zl-sita*z_dc/m_dc;
	ml=sqrt(xl*xl+yl*yl+zl*zl);
	a.ml=sqrt(a.xl*a.xl+a.yl*a.yl+a.zl*a.zl);
	if (ml==0||a.ml==0)
	{
		return 0;
	}
	float arg=acos((xl*a.xl+yl*a.yl+zl*a.zl)/2/(ml*a.ml));
	if(x_dc*(-yl*a.zl+a.yl*zl)+y_dc*(-zl*a.xl+a.zl*xl)+z_dc*(-xl*a.yl+a.xl*yl)>=0)return arg;
	else
	{
		 return -arg;
	}
*/
}

#endif
