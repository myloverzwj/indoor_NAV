package com.modou.fragment;

import com.modou.loc.R;
import com.modou.loc.module.map.LocationNormalizer;
import com.modou.loc.module.map.Renderer;
import com.modou.utils.MLog;
import com.modou.utils.MathUtils;
import com.modou.utils.VectorF;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CategoryFragment extends BaseFragment implements OnTouchListener {

	private GLSurfaceView mGLView;
	private Renderer mRenderer;
	
	//locations
	float posX=20;
	float posY=-20;
	float posZ=0;
	float xStart=0;
	float yStart=0;
	
	private boolean multiTouch = false;
	private boolean touched = false;
	private float initialDistance;
	private final VectorF pinchVector = new VectorF();
	private float lastScale = 1.0f;
	private float maxScale = 5.0f;
	private float preScale = -1;
	
	private static boolean mShowRoomNums = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_category, container, false);
		
		mGLView = new GLSurfaceView(getActivity());
		mRenderer = new Renderer(getActivity());
		mGLView.setRenderer(mRenderer);
		mGLView.setKeepScreenOn(true);
		mGLView.setZOrderOnTop(true);
		
		mGLView.setOnTouchListener(this);
		
		RelativeLayout panelView = (RelativeLayout) v.findViewById(R.id.layer_room_map);
		panelView.addView(mGLView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView();
		initData();
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		
		UpdateLocation(posX, posY, posZ);
	}
	
	/**
	 * Updates the location of the user in iDocent and in the renderer
	                          
	{@link UpdateLocation}. 
	 *
	                         
	@param  x - the location in the x direction
			y - the location in the y direction
	 *   
	 */
    public void UpdateLocation(float x, float y, float z)
    {
    	float[] normalLoc = LocationNormalizer.Normalize(x, y, z);
    	posX = normalLoc[0];
    	posY = normalLoc[1];
    	posZ = normalLoc[2];
    	
    	mRenderer.UpdateLocation(posX, posY, posZ);
    }
    
	public static boolean ShowRoomNums()
	{
		return mShowRoomNums;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//Handle touch events to move the map around
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			xStart=event.getX();
			yStart=event.getY();
			
			touched = true;
			return true;
		}
		else if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			if (event.getPointerCount() > 1) {
				multiTouch = true;
				if(initialDistance > 0) {
					pinchVector.set(event);
					pinchVector.calculateLength();
					
					float distance = pinchVector.length;
					
					if(initialDistance != distance) {
						float newScale = (distance / initialDistance) * lastScale;
						if(newScale <= maxScale) {
							if (preScale != -1) {
								if (newScale - preScale > 0) {
									mRenderer.zoomIn();
								} else {
									mRenderer.zoomOut();
								}
							}
							preScale = newScale;
						}
					}
				} else {
					initialDistance = MathUtils.distance(event);
				}
			} else {
				mRenderer.MoveCamera(event.getX()-xStart, event.getY()-yStart);
			}
			return true;
		}
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			mRenderer.CenterCamera();
			xStart = yStart = 0;
			
			multiTouch = false;
			preScale = -1;
			initialDistance = 0;
			return true;
		}
		return false;
	}
	
}
