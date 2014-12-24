package com.modou.loc.module.map2;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.loc.module.map2.config.Cell;
import com.modou.loc.module.map2.config.Floor;
import com.modou.loc.module.map2.config.MapConfig;
import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-7-30 上午8:08:42
 * 类说明:
 * 在该类上显示所有的房间、走廊、设备等物体
 */
public class Map extends GraphicsObject {

	/**用户行走轨迹绘制类*/
	private WalkPath walkPath;
	/**当前场景物体集合*/
	private List<GraphicsObject> drawObjList;
	/**显示的楼层ID*/
	private int floorIndex = 0;
	/**屏幕的宽度高度*/
	private int screenWidth, screenHeight;
	/**地图是否加载完毕*/
	private boolean isLoadOver;
	
	private SceneRenderer sceneRenderer;
	
	public Map(Context ctx, SceneRenderer sr) {
		this(ctx, 1); // 默认加载第一张地图
		this.sceneRenderer = sr;
	}
	
	public Map(Context ctx, int floorIndex) {
		super(ctx);
		this.floorIndex = floorIndex;
		
	}
	
	/**
	 * 加载相应楼层地图信息
	 * @param floorIndex	楼层地图索引
	 */
	public void reloadMap(int floorIndex) {
		MLog.d("===========开始加载数据=============");
		setLoadOver(false);
		this.floorIndex = floorIndex;
		initData();
	}
	
	ShapeWalkHead shapeHead;
	/**初始化地图信息数据*/
	public void initData() {
		shapeHead = new ShapeWalkHead(mContext);
		walkPath = new WalkPath(mContext, shapeHead);
		
		if (drawObjList == null)
			drawObjList = new ArrayList<GraphicsObject>();
		
//		List<GraphicsObject> tmpObjList = new ArrayList<GraphicsObject>();
		synchronized (drawObjList) {
			drawObjList.clear();
			//TODO 解析地图xml，将其中的每一个对象添加到drawObjList集合中。
			if (MapConfig.getInstance().getFloorList() == null) {
				DataTransferMgr2.getInstance().cancel();
				sceneRenderer.showMsg("加载地图时，没有取到相应楼层信息");
				MLog.d("加载地图时，没有取到相应楼层信息");
				return;
			}
			Floor floor = MapConfig.getInstance().getFloorList().get(floorIndex);
			if (floor == null || floor.getCellList() == null || floor.getCellList().size() == 0) {
				if (MLog.DEBUG)
					throw new RuntimeException("加载地图时，没有找到对应的楼层信息 floorIndex=" + floorIndex);
				else {
					DataTransferMgr2.getInstance().cancel();
					sceneRenderer.showMsg("没有找到对应的楼层信息");
					((Activity)mContext).finish();
					return;
				}
			}
			else
				MLog.d("加载当前的楼层信息:" + floorIndex);
			List<Cell> cellList = floor.getCellList();
			int size = cellList == null ? 0 : cellList.size();
			Cell cell = null;
			for (int i = 0; i < size; i++) {
				cell = cellList.get(i);
				if ("矩形".equals(cell.getShape()) ||
					"rect".equals(cell.getShape())) {
					ShapeRect rect = new ShapeRect(mContext);
					List<Point> points = cell.getPoints();
					int pSize = points == null ? 0 : points.size();
					if (pSize != 2)
						throw new RuntimeException("获取矩形数据时，数据不合法 pointSize=" + pSize + " ,cell=" + cell.toString());
					Point startP = points.get(0);
					Point endP = points.get(1);
					rect.addPoint(startP, endP);
					
					rect.setColor(cell.getColor());
					rect.initVertexData();
					drawObjList.add(rect);
				} else if ("椭圆".equals(cell.getShape()) || 
							"圆形".equals(cell.getShape()) ||
							"circle".equals(cell.getShape()) ||
							"ellipse".equals(cell.getShape())) {
					ShapeCircle circle = new ShapeCircle(mContext);
					List<Point> points = cell.getPoints();
					int pSize = points == null ? 0 : points.size();
					if (pSize != 2)
						throw new RuntimeException("获取圆形数据时，数据不合法 pointSize=" + pSize + " ,cell=" + cell.toString());
					Point startP = points.get(0);
					Point endP = points.get(1);
					circle.addPoint(startP, endP);
					
					circle.setColor(cell.getColor());
					circle.initVertexData();
					drawObjList.add(circle);
				} else if ("直线".equals(cell.getShape()) ||
						"折线".equals(cell.getShape()) ||
						"multline".equals(cell.getShape()) ||
						"line".equals(cell.getShape())) {
					ShapeFoldLine foldLine = new ShapeFoldLine(mContext);
					List<Point> points = cell.getPoints();
					foldLine.addPoints(points);
					
					foldLine.setColor(cell.getColor());
					foldLine.initVertexData();
					drawObjList.add(foldLine);
				} else if ("扇形".equals(cell.getShape()) ||
						"圆弧".equals(cell.getShape()) ||
						"arc".equals(cell.getShape()) ||
						"arcArea".equals(cell.getShape())) {
					ShapeArc shape = null;
					if ("扇形".equals(cell.getShape()) || "arcArea".equals(cell.getShape())) {
						shape = new ShapeSector(mContext);
					} else if ("圆弧".equals(cell.getShape()) || "arc".equals(cell.getShape())) {
						shape = new ShapeArc(mContext);
					}
					shape.setColor(cell.getColor());
					shape.initShapeDatas(cell.getCenterPoint(), cell.getPoints().get(0), cell.getPoints().get(1), cell.getPoints().get(2));
					shape.initVertexData();
					drawObjList.add(shape);
				} else if ("扇环".equals(cell.getShape()) ||
						"annulus".equals(cell.getShape())) {
					ShapeAnnulus annulus = new ShapeAnnulus(mContext);
					List<Point> points = cell.getPoints();
					annulus.addPoints(points);
					annulus.setColor(cell.getColor());
					annulus.initVertexData();
					drawObjList.add(annulus);
				}
			}
			
			//TODO 添加一个测试矩形
			/*ShapeRect rect = new ShapeRect(mContext);
			rect.addPoint(-1f, 0.5f);
			rect.addPoint(-1f, -0.5f);
			rect.addPoint(0f, -0.5f);
			rect.addPoint(0f, 0.5f);
			rect.initVertexData();
			drawObjList.add(rect);*/
			
			//TODO 测试圆形
			/*ShapeCircle circle = new ShapeCircle(mContext);
			Point startP = null;
			Point endP = null;
			circle.addPoint(startP, endP);
			circle.initVertexData();
			drawObjList.add(circle);*/
			
			//TODO 测试扇形
			/*ShapeArc arc = new ShapeArc(mContext);
			float p1X = CoordUtil.toGLX(120);
			float p1Y = CoordUtil.toGLY(360);
			Point startP = new Point(p1X, p1Y);
			
			float p2X = CoordUtil.toGLX(309);
			float p2Y = CoordUtil.toGLY(646);
			Point endP = new Point(p2X, p2Y);
			float arcDeg = 50;
			float startDeg = 93;
			arc.initShapeDatas(startP, endP, arcDeg, startDeg);
			arc.initVertexData();
			drawObjList.add(arc);*/
		}
		
		MLog.d("===========加载数据完毕=============");
		setLoadOver(true);
	}

	@Override
	public void Draw(GL10 gl) {
		if (isLoadOver()) {
			synchronized (drawObjList) {
				for (GraphicsObject gb : drawObjList) {
					gb.Draw(gl);
				}
			}
			
			walkPath.Draw(gl);
//			shapeHead.Draw(gl);
		}
	}
	
	public void move(float dx, float dy) {
		walkPath.move(dx, dy);
	}

	public void setScreenSize(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	long preTime = System.currentTimeMillis();
	/**
	 * 添加用户行为轨迹点
	 * @param p	用户行为轨迹点
	 */
	public void addUserTrack(Point p) {
	
		//TODO 测试数据
		if (System.currentTimeMillis() - preTime > 1500) {
			preTime = System.currentTimeMillis();
			float x = (float)Math.random() * 0.5f;
			float y = (float)Math.random() * 0.5f;
			
			walkPath.addPoint(new Point(x, y));
		}
		
//		walkPath.addPoint(p);
	}
	
	public void updateUserTrack(Point[] pts) {
		//TODO 测试数据
//		if (System.currentTimeMillis() - preTime > 1500) {
//			preTime = System.currentTimeMillis();
//			Point[] par = new Point[20];
//			for (int i = 0; i < 20; i++) {
//				float x = (float)Math.random() * 0.5f;
//				float y = (float)Math.random() * 0.5f;
//				
//				par[i] = new Point(x, y);
//			}
//			walkPath.updateUserTrack(par);
//		}
		
		
		walkPath.updateUserTrack(pts);
	}

	/**
	 * 获取当前地图文件名称
	 * @return	当前地图文件名称
	 */
	public String getCurMapFileName() {
		if (MapConfig.getInstance().getFloorList() == null)
			return null;
		Floor floor = MapConfig.getInstance().getFloorList().get(floorIndex);
		if (floor == null)
			return null;
		String path = floor.getMapFilePath();
		return path;
	}

	public boolean isLoadOver() {
		return isLoadOver;
	}

	public void setLoadOver(boolean isLoadOver) {
		this.isLoadOver = isLoadOver;
	}
	
}
