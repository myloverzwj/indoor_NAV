/*
 * ECE 480 Spring 2011
 * Team 2 Design Project
 * Matt Gottshall
 * Jake D'Onofrio
 * Gordie Stein
 * Andrew Kling
 */
package com.modou.loc.module.map;

import java.util.HashMap;
import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;

/** The map with all rooms and halls for all floors */
public class Map extends GraphicsObject {

	// 用户行走轨迹
	private Vector<GraphicsObject> walkPath;
	// 房间地图加载类
	private IndoorMap indoorMap;
	
	private Vector<GraphicsObject> Children;
	private Vector<GraphicsObject> FirstFloor;
	private Vector<GraphicsObject> SecondFloor;
	private Vector<GraphicsObject> ThirdFloor;

	private HashMap<Integer, Integer> roomSquarePos = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> roomSquarePos2 = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> roomSquarePos3 = new HashMap<Integer, Integer>();

	private float posX;
	private float posY;
	private float posZ;

	private Dot mDot;

	public Map(Activity miD) {
		walkPath = initWalkPathData();
		
		indoorMap = new IndoorMap(-110.0f, 130.0f, 61.4f, -95.4f, 0, miD);
		
		Children = new Vector<GraphicsObject>();
		FirstFloor = new Vector<GraphicsObject>();

		mDot = new Dot(20, -20, 0);
		mDot.setColor(1, 0, 0, 0);

		int pos1 = 0;
		FirstFloor.add(new Line(-120, -16.4f, 12, -16.4f));
		pos1++;
		FirstFloor.add(new Line(24.5f, -16.4f, 143.5f, -16.4f));
		pos1++;
		FirstFloor.add(new Line(-108, -28.4f, 120, -28.4f));
		pos1++;
		FirstFloor.add(new Line(143.5f, -16.4f, 143.5f, -28.4f));
		pos1++;
		FirstFloor.add(new Line(132, -28.4f, 143.5f, -28.4f));
		pos1++;
		FirstFloor.add(new Line(120, -28.4f, 120, -468.4f));
		pos1++;
		FirstFloor.add(new Line(132, -28.4f, 132, -468.4f));
		pos1++;
		FirstFloor.add(new Line(120, -468.4f, 132, -468.4f));
		pos1++;
		// FirstFloor.add(new Line(132, -153.4f, 142, -153.4f));

		// Previous halls are shared in all floors
		SecondFloor = new Vector<GraphicsObject>(FirstFloor);
		int pos2 = pos1;

		FirstFloor.add(new Line(-120, -16.4f, -120, -64.4f));
		pos1++;
		FirstFloor.add(new Line(-108, -28.4f, -108, -91.4f));
		pos1++;
		FirstFloor.add(new Line(-120, -64.4f, -165, -64.4f));
		pos1++;
		FirstFloor.add(new Line(12, 0, 12, -16.4f));
		pos1++;
		FirstFloor.add(new Line(24.5f, 0, 24.5f, -16.4f));
		pos1++;
		FirstFloor.add(new Line(-188, -61.4f, -188, -95.4f));
		pos1++;
		FirstFloor.add(new Line(-188, -61.4f, -165, -59.4f));
		pos1++;
		FirstFloor.add(new Line(-188, -95.4f, -165, -97.4f));
		pos1++;
		FirstFloor.add(new Line(-165, -59.4f, -165, -64.4f));
		pos1++;
		FirstFloor.add(new Line(-165, -97.4f, -165, -91.4f));
		pos1++;
		FirstFloor.add(new Line(-165, -91.4f, -108, -91.4f));
		pos1++;

		SecondFloor.add(new Line(12, -16.4f, 24.5f, -16.4f));
		pos2++;

		ThirdFloor = new Vector<GraphicsObject>(SecondFloor);

		Children = FirstFloor;

		// add rooms
		String classroom = "blue", office = "green", restroom = "white", lab = "yellow", stair = "white", unknown = "gray", spartys = "white", thecenter = "white";

		FirstFloor.add(new RoomSquare(-188f, -61.4f, "left", "top", 70f, 34f,
				classroom, miD, 1345));// 1345
		roomSquarePos.put(1345, pos1++);
		FirstFloor.add(new RoomSquare(-165f, -91.4f, "top", "left", 28.6f, 5f,
				spartys, miD, 6000));// 6000
		roomSquarePos.put(6000, pos1++);
		FirstFloor.add(new RoomSquare(-165f, -64.4f, "top", "left", 45f, 30f,
				thecenter, miD, 1340));// 1340
		roomSquarePos.put(1340, pos1++);
		
		FirstFloor.add(new RoomSquare(120, -40f, "left", "bottom", 23, 11.6f, restroom, miD, 1203));//1203
		roomSquarePos.put(1203, pos1++);
		FirstFloor.add(new RoomSquare(120, -71f, "left", "bottom", 23, 31, stair, miD, 8000));//8000
		roomSquarePos.put(8000, pos1++);
		FirstFloor.add(new RoomSquare(120, -71f, "left", "top", 23, 10, office, miD, 1210));//1210
		roomSquarePos.put(1210, pos1++);
		FirstFloor.add(new RoomSquare(120, -91f, "left", "bottom", 19, 10, office, miD, 1211));//1211
		roomSquarePos.put(1211, pos1++);
		FirstFloor.add(new RoomSquare(120, -105f, "left", "bottom", 19, 14, office, miD, 1213));//1213
		roomSquarePos.put(1213, pos1++);
		FirstFloor.add(new RoomSquare(120, -111f, "left", "bottom", 19, 6, office, miD, 1216));//1216
		roomSquarePos.put(1216, pos1++);
		FirstFloor.add(new RoomSquare(120, -121f, "left", "bottom", 19, 10, office, miD, 1217));//1217
		roomSquarePos.put(1217, pos1++);
		FirstFloor.add(new RoomSquare(120, -131f, "left", "bottom", 19, 10, office, miD, 1218));//1218
		roomSquarePos.put(1218, pos1++);
		FirstFloor.add(new RoomSquare(120, -139f, "left", "bottom", 19, 8, office, miD, 1219));//1219
		roomSquarePos.put(1219, pos1++);
		FirstFloor.add(new RoomSquare(120, -151f, "left", "bottom", 22, 12, office, miD, 1226));//1226
		roomSquarePos.put(1226, pos1++);
		FirstFloor.add(new RoomSquare(120, -178f, "left", "bottom", 22, 27, unknown, miD, 1228));//1228
		roomSquarePos.put(1228, pos1++);
		FirstFloor.add(new RoomSquare(120, -197f, "left", "bottom", 22, 19, unknown, miD, 1231));//1231
		roomSquarePos.put(1231, pos1++);
		FirstFloor.add(new RoomSquare(120, -220f, "left", "bottom", 22, 23, unknown, miD, 1232));//1232
		roomSquarePos.put(1232, pos1++);
		FirstFloor.add(new RoomSquare(120, -241f, "left", "bottom", 27, 21, unknown, miD, 1235));//1235
		roomSquarePos.put(1235, pos1++);
		FirstFloor.add(new RoomSquare(120, -265f, "left", "bottom", 27, 24, stair, miD, 8000));//stair
		roomSquarePos.put(-2, pos1++);
		FirstFloor.add(new RoomSquare(120, -288f, "left", "bottom", 27, 23, unknown, miD, 1237));//1237
		roomSquarePos.put(1237, pos1++);
		FirstFloor.add(new RoomSquare(120, -311f, "left", "bottom", 27, 23, unknown, miD, 1242));//1242
		roomSquarePos.put(1242, pos1++);
		FirstFloor.add(new RoomSquare(120, -322f, "left", "bottom", 27, 11, unknown, miD, 1243));//1243
		roomSquarePos.put(1243, pos1++);
		FirstFloor.add(new RoomSquare(120, -330f, "left", "bottom", 27, 8, unknown, miD, 1245));//1245
		roomSquarePos.put(1245, pos1++);
		FirstFloor.add(new RoomSquare(120, -342f, "left", "bottom", 27, 12, unknown, miD, 1248));//1248
		roomSquarePos.put(1248, pos1++);
		FirstFloor.add(new RoomSquare(120, -357f, "left", "bottom", 27, 15, unknown, miD, 1250));//1250
		roomSquarePos.put(1250, pos1++);
		FirstFloor.add(new RoomSquare(120, -371f, "left", "bottom", 27, 14, stair, miD, 8000));//stair
		roomSquarePos.put(-1, pos1++);
		FirstFloor.add(new RoomSquare(120, -468.4f, "left", "bottom", 22, 97, unknown, miD, -1));//group
		roomSquarePos.put(-10, pos1++);
		
		FirstFloor.add(new RoomSquare(132, -28.4f, "right", "top", 30, 30.6f, unknown, miD, 1204));//1204
		roomSquarePos.put(1204, pos1++);
		FirstFloor.add(new RoomSquare(132, -59f, "right", "top", 30, 11f, unknown, miD, 1206));//1206
		roomSquarePos.put(1206, pos1++);
		FirstFloor.add(new RoomSquare(132, -70f, "right", "top", 30, 35f, unknown, miD, 1208));//1208
		roomSquarePos.put(1208, pos1++);
		FirstFloor.add(new RoomSquare(132, -105f, "right", "top", 30, 12f, unknown, miD, 1215));//1215
		roomSquarePos.put(1215, pos1++);
		FirstFloor.add(new RoomSquare(132, -117f, "right", "top", 30, 32f, unknown, miD, 1220));//1220
		roomSquarePos.put(1220, pos1++);
		FirstFloor.add(new RoomSquare(132, -149f, "right", "top", 30, 36.5f, classroom, miD, 1225));//1225
		roomSquarePos.put(1225, pos1++);
		FirstFloor.add(new RoomSquare(132, -219f, "right", "bottom", 30, 33.5f, classroom, miD, 1230));//1230
		roomSquarePos.put(1230, pos1++);
		FirstFloor.add(new RoomSquare(132, -219f, "right", "top", 30, 38f, classroom, miD, 1234));//1234
		roomSquarePos.put(1234, pos1++);
		FirstFloor.add(new RoomSquare(132, -468.4f, "right", "bottom", 30, 199f, unknown, miD, 10000));//group
		roomSquarePos.put(-3, pos1++);
		
		FirstFloor.add(new RoomSquare(97, -28.4f, "bottom", "right", 22, 30f, unknown, miD, 8000));//stair/1306?
		roomSquarePos.put(-4, pos1++);
		FirstFloor.add(new RoomSquare(27f, -28.4f, "bottom", "left", 48f, 30f, lab, miD, 1307));//1307
		roomSquarePos.put(1307, pos1++);
		FirstFloor.add(new RoomSquare(27f, -28.4f, "bottom", "right", 12f, 30f, stair, miD, 8000));//stair
		roomSquarePos.put(-6, pos1++);
		FirstFloor.add(new RoomSquare(-16f, -28.4f, "bottom", "left", 31f, 30f, lab, miD, 1320));//1320
		roomSquarePos.put(1320, pos1++);
		
		FirstFloor.add(new RoomSquare(130f, -16.4f, "top", "right", 26f, 30f, classroom, miD, 1202));//1202
		roomSquarePos.put(1202, pos1++);
		FirstFloor.add(new RoomSquare(104f, -16.4f, "top", "right", 30f, 30f, classroom, miD, 1300));//1300
		roomSquarePos.put(1300, pos1++);
		FirstFloor.add(new RoomSquare(74f, -16.4f, "top", "right", 9f, 30f, unknown, miD, 1303));//1303
		roomSquarePos.put(1303, pos1++);
		FirstFloor.add(new RoomSquare(65f, -16.4f, "top", "right", 22f, 30f, classroom, miD, 1312));//1312
		roomSquarePos.put(1312, pos1++);
		FirstFloor.add(new RoomSquare(43f, -16.4f, "top", "right", 18.5f, 30f, lab, miD, 1314));//1314
		roomSquarePos.put(1314, pos1++);
		FirstFloor.add(new RoomSquare(12f, -16.4f, "top", "right", 32f, 30f, lab, miD, 1318));//1318
		roomSquarePos.put(1318, pos1++);
		
		FirstFloor.add(new RoomSquare(-20f, -16.4f, "top", "right", 100f, 30f, unknown, miD, -1));//junk
		roomSquarePos.put(-20, pos1++); 
		FirstFloor.add(new RoomSquare(-16f, -28.4f, "bottom", "right", 92f, 30f, unknown, miD, -1));//junk
		roomSquarePos.put(-21, pos1++); 
	}

	private Vector<GraphicsObject> initWalkPathData() {
		int[][] lines = new int[][]{{-10, 5}, {-9, -4}, {-8, 0}, {-7, 4}, {-6, 3}, {-5, 3},
									{-4, -3}, {-3, -2}, {0, -3}, {1, -2}, {2, -2}, {3, -4},
									{5, -5}, {6, -8}, {7, -4}, {8, -1}, {10, 5}, {12, 8}, 
									{13, 9}, {13, 7}, {15, 5}, {17, 3}, {20, -5}, {22, -7},
									{25, 0}, {27, 2}, {29, 5}, {35, 8}, {40, 15}, {50, -9}, 
									{60, -5}, {70, 8}, {65, -8}, {80, -5}, {80, -16}, {70, -20}, 
									{75, -25}, {78, -26}, {80, -30}, {83, -27}, {88, -30}, {90, -31},
									{91, -29}, {93, -26}, {95, -20}, {99, -25}, {100, -23}, {105, -20}, 
									{110, -15}};
		int len = lines.length;
		Vector<GraphicsObject> arrs = new Vector<GraphicsObject>(len);
		Line line = null;
		for (int i = 0; i < len-1; i++) {
			int x1 = lines[i][0];
			int y1 = lines[i][1];
			int x2 = lines[i+1][0];
			int y2 = lines[i+1][1];
			line = new Line(x1, y1, x2, y2);
			
			arrs.add(line);
		}
		return arrs;
	}

	@Override
	public void Draw(GL10 GL) {
		indoorMap.Draw(GL);
		
		if (posZ == LocationNormalizer.ffz)
			Children = FirstFloor;
		else if (posZ == LocationNormalizer.sfz)
			Children = SecondFloor;
		else
			Children = ThirdFloor;

//		for (GraphicsObject i : Children) {
//			if(i instanceof RoomSquare)
//			{
//				((RoomSquare)i).DrawTexture(GL);
//			}
//			i.Draw(GL);
//		}
		
		for (GraphicsObject i : walkPath) {
			i.Draw(GL);
		}
		
		mDot.Draw(GL);
	}

	/**
	 * Update the stored location of the user
	 * 
	 * @param x
	 *            - the user's x location
	 * @param y
	 *            - the user's y location
	 * @param z
	 *            - the user's z location
	 */
	public void UpdateLoction(float x, float y, float z) {
		float[] f = LocationNormalizer.Normalize(x, y, z);
		posX = f[0];
		posY = f[1];
		posZ = f[2];
		mDot.UpdateLocation(posX, posY, posZ);
	}

	public void loadTexture(GL10 gl, Activity miD) {
		for(GraphicsObject r : FirstFloor)
		{
			if(r instanceof RoomSquare)
				((RoomSquare)r).loadGLTexture(gl, miD);
		}
		
		for(GraphicsObject r : SecondFloor)
		{
			if(r instanceof RoomSquare)
				((RoomSquare)r).loadGLTexture(gl, miD);
		}
		
		for(GraphicsObject r : ThirdFloor)
		{
			if(r instanceof RoomSquare)
				((RoomSquare)r).loadGLTexture(gl, miD);
		}
		
		// 房间地图加载
		indoorMap.loadGLTexture(gl, miD);
	}
	
	/**
	 * 更新显示地图
	 * @param mapNum	对应地图ID
	 */
	public void changeMap(int mapNum) {
		indoorMap.updateMap(mapNum);
	}
	
}
