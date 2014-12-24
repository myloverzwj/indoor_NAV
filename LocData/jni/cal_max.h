#include <math.h>

float cal_max(float *a,float DC,int NUM,int CUR,int num)
{
	float SUM=0;
	for (int i=0;i<num;i++)
	{
		SUM+=abs(a[(CUR-i)%NUM]-DC);
	}
	return SUM/num;

}