float LPF(float *a, int NUM, int CUR, int num1)//a 为存储传感器元素的数组， NUM为a的容量， CUR为当前所在的位置，num1为需要求和的范围
{
	float sum=0;
	for (int i=0;i<num1;i++)
	{
		sum+=a[(CUR-i+NUM)%NUM];
	}
	return sum/num1;

}