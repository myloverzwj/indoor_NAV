package com.modou.loc.module.map2.config;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-3 下午12:04:18 类说明: 坐标转换常量类
 */
public class CoordUtil {

	private final static float ratio = 1f;
	private static int screenWidth, screenHeight;
	
	public static void init(int screenW, int screenH) {
		screenWidth = screenW;
		screenHeight = screenH;
	}

	/**
	 * Convert x to openGL
	 * 
	 * @param x
	 *            Screen x offset top left
	 * @return Screen x offset top left in OpenGL
	 */
	public static float toGLX(float x) {
		return -1.0f * ratio + toGLWidth(x);
	}

	/**
	 * Convert y to openGL y
	 * 
	 * @param y
	 *            Screen y offset top left
	 * @return Screen y offset top left in OpenGL
	 */
	public static float toGLY(float y) {
		return 1.0f - toGLHeight(y);
	}

	/**
	 * Convert width to openGL width
	 * 
	 * @param width
	 * @return Width in openGL
	 */
	public static float toGLWidth(float width) {
		return 2.0f * (width / screenWidth) * ratio;
	}

	/**
	 * Convert height to openGL height
	 * 
	 * @param height
	 * @return Height in openGL
	 */
	public static float toGLHeight(float height) {
		return 2.0f * (height / screenHeight);
	}

	/**
	 * Convert x to screen x
	 * 
	 * @param glX
	 *            openGL x
	 * @return screen x
	 */
	public static float toScreenX(float glX) {
		return toScreenWidth(glX - (-1 * ratio));
	}

	/**
	 * Convert y to screent y
	 * 
	 * @param glY
	 *            openGL y
	 * @return screen y
	 */
	public static float toScreenY(float glY) {
		return toScreenHeight(1.0f - glY);
	}

	/**
	 * Convert glWidth to screen width
	 * 
	 * @param glWidth
	 * @return Width in screen
	 */
	public static float toScreenWidth(float glWidth) {
		return (glWidth * screenWidth) / (2.0f * ratio);
	}

	/**
	 * Convert height to screen height
	 * 
	 * @param glHeight
	 * @return Height in screen
	 */
	public static float toScreenHeight(float glHeight) {
		return (glHeight * screenHeight) / 2.0f;
	}

}
