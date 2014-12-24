float cal_DC(float *a,int NUM,int CUR,int num)
{

	float sum=0;
	for (int i=0;i<num;i++)
	{
		sum+=a[(CUR-i+NUM)%NUM];
	}
	return sum/num;
}