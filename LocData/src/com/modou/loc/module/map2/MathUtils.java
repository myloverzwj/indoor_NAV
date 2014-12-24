/*
 * Copyright (c) 2012 Jason Polites
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.modou.loc.module.map2;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;

public class MathUtils {
	
	public static float distance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
	
	public static float distance(PointF p1, PointF p2) {
		float x = p1.x - p2.x;
		float y = p1.y - p2.y;
		return FloatMath.sqrt(x * x + y * y);
	}
	
	public static float distance(Point p1, Point p2) {
		float x = p1.getX() - p2.getX();
		float y = p1.getY() - p2.getY();
		return FloatMath.sqrt(x * x + y * y);
	}
	
	public static float distance(float x1, float y1, float x2, float y2) {
		float x = x1 - x2;
		float y = y1 - y2;
		return FloatMath.sqrt(x * x + y * y);
	}

	public static void midpoint(MotionEvent event, PointF point) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		float x2 = event.getX(1);
		float y2 = event.getY(1);
		midpoint(x1, y1, x2, y2, point);
	}

	public static void midpoint(float x1, float y1, float x2, float y2, PointF point) {
		point.x = (x1 + x2) / 2.0f;
		point.y = (y1 + y2) / 2.0f;
	}
	/**
	 * Rotates p1 around p2 by angle degrees.
	 * @param p1
	 * @param p2
	 * @param angle
	 */
	public void rotate(PointF p1, PointF p2, float angle) {
		float px = p1.x;
		float py = p1.y;
		float ox = p2.x;
		float oy = p2.y;
		p1.x = (FloatMath.cos(angle) * (px-ox) - FloatMath.sin(angle) * (py-oy) + ox);
		p1.y = (FloatMath.sin(angle) * (px-ox) + FloatMath.cos(angle) * (py-oy) + oy);
	}
	
	public static float angle(PointF p1, PointF p2) {
		return angle(p1.x, p1.y, p2.x, p2.y);
	}	
	
	public static float angle(Point p1, Point p2) {
		return angle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	public static float angle(float x1, float y1, float x2, float y2) {
		return (float) Math.atan2(x2 - x1, y2 - y1);
	}
	
	/**根据三点确定圆心*/
	public static Point computeCirclePoint(Point p1, Point p2, Point p3) {
		float x1 = p1.getX();
		float x2 = p2.getX();
		float x3 = p3.getX();
		float y1 = p1.getY();
		float y2 = p2.getY();
		float y3 = p3.getY();
		
		// 圆心X坐标点
		float x = ((y3-y1)*(y2-y1)*(y3-y2) - (x2+x3)*(x2-x3)*(y2-y1) + (x1+x2)*(x1-x2)*(y3-y2))/
				((y3-y2)*(x1-x2) - (y2-y1)*(x2-x3)) / 2;
		
		// 圆心Y坐标点
		float y = (x2-x2)/(y3-y2)*(x - (x2+x3)/2) + (y2+y3)/2;
		
		return new Point(x, y);
	}
	
	/**
	 * 根据圆心和弧线的起始点来计算弧线上的其他点数据
	 * @return
	 */
	public static List<Point> computeCirlePoint(Point centerPoint, Point startPoint, Point middlePoint, Point endPoint) {
		List<Point> points = new ArrayList<Point>();
		float argStart = MathUtils.angle(centerPoint, startPoint);
		float argEnd = MathUtils.angle(centerPoint, endPoint);
		float radius = MathUtils.distance(centerPoint, startPoint);
		float x = centerPoint.getX();
		float y = centerPoint.getY();
		int n=20;
		float PI = 3.1416f;
		if(argStart<argEnd)
			argStart+=2*PI;
	    for(int i=0; i<n+1; i++) {
	    	float x1 = x+radius*(float)Math.sin(argStart+(argEnd-argStart)*i/n);
	    	float y1 = y+radius*(float)Math.cos(argStart+(argEnd-argStart)*i/n);
//	    	MLog.d("计算出的弧形值: x1=" + x1 + " ,y1=" + y1 );
	    	points.add(new Point(x1, y1));
	    }
	    
	    return points;
	}
	
}
