#include <math.h>


float cal_de(float c_x ,float c_y,float c_z,float a_x,float a_y,float a_z,float G,float C)
{
	float a1,a2,a1x,a2x;
	a1=sqrt(c_x*c_x+c_y*c_y+c_z*c_z);
	a2=sqrt(a_x*a_x+a_y*a_y+a_z*a_z);
	a1x=sqrt(abs(a1*a1-C*C));
	a2x=sqrt(abs(a2*a2-G*G));
	return (c_x*a_x+c_y*a_y+c_z*a_z-G*C);
}
