package com.modou.loc.module.map;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.modou.utils.MLog;

public class Renderer implements GLSurfaceView.Renderer {

	private float posX = 75;
	private float posY = 100;
	private float posZ = 0;
	private float xOffset = 0;
	private float yOffset = 0;
	private float zoom = -200;
	
	private Activity aty;
	private Map mMap;
	public Renderer(Activity aty) {
		this.aty = aty;
		mMap = new Map(aty);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glEnable(GL10.GL_TEXTURE_2D);
		mMap.loadTexture(gl, aty);
    	// Set the background color to black ( rgba ).
		gl.glClearColor(58.0f, 55.0f, 55.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		Log.d("mylog", "Renderer............onSurfaceCreated.....");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, w, h);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) w / (float) h, 0.1f,
				400.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		// Reset the modelview matrix
		gl.glLoadIdentity();
		
		MLog.d("onSurfaceChanged======================");
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		//draw frame at right zoom and temporary translation
		gl.glTranslatef(-(posX+xOffset), -(posY+yOffset), zoom); 

		mMap.Draw(gl);
//		mTriangle.drawSelf(gl);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity(); // OpenGL docs     
	}
	
	public void MoveCamera(float x, float y) {
		xOffset = -x*zoom/-350f;
		yOffset = y*zoom/-350f;
	}
	
	public void CenterCamera()
	{
		xOffset = yOffset = 0;
	}
	
	/**
	 * Update the stored location of the user
	 * @param x - the user's x location
	 * @param y - the user's y location
	 * @param z - the user's z location
	 */
	public void UpdateLocation(float x, float y, float z) {
		posX = x;
		posY = y;
		posZ = z;
		
		mMap.UpdateLoction(x, y, z);
	}
	
	public void zoomOut() {
		zoom -= 4;
		if(zoom <= -350)
			zoom = -350;
	}

	public void zoomIn() {
		zoom += 4;
		if(zoom >= -50)
			zoom = -50;
	}

	public void changeMap(int mapId) {
		mMap.changeMap(mapId);
	}
	
}
