uniform mat4 uMVPMatrix; //总变换矩阵
attribute vec3 aPosition;  //顶点位置
attribute vec4 aColor;
varying vec4 vColor;

void main()     
{                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //根据总变换矩阵计算此次绘制此顶点位置
   gl_PointSize=10.0;
   vColor = aColor;
}


//attribute vec4 vPosition;
//void main()
//{
//	gl_Position = vPosition;
//}