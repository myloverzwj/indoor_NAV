package com.modou.loc;

import java.util.ArrayList;

import com.modou.loc.adapter.FloorAdapter;
import com.modou.loc.db.DBDao;
import com.modou.loc.entity.Floor;
import com.modou.loc.module.map.LocationNormalizer;
import com.modou.loc.module.map.Renderer;
import com.modou.utils.MathUtils;
import com.modou.utils.VectorF;
import com.modou.widget.BuildSelPopupWin;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class IndoorMapActivity extends BaseActivity implements OnTouchListener {

	// 标题组件
	private TextView txtTitle;
	private BuildSelPopupWin buildSelPopWin;
	
	private GLSurfaceView mGLView;
	private Renderer mRenderer;
	
	//locations
	float posX=20;
	float posY=-20;
	float posZ=0;
	float xStart=0;
	float yStart=0;
	
	float sx = 5.0f, sy = 5.0f;
	float moveX, moveY;
	boolean xChanged, yChanged;
	
	private boolean multiTouch = false;
	private boolean touched = false;
	private float initialDistance;
	private final VectorF pinchVector = new VectorF();
	private float lastScale = 1.0f;
	private float maxScale = 5.0f;
	private float preScale = -1;
	
	private static boolean mShowRoomNums = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_category);
		
		initView();
		initData();
	}

	private void initView() {
		setLeftBtn(R.drawable.btn_back_bg, R.string.back);
		setRightBtn(R.drawable.btn_confirm_bg, R.string.locationing);
		txtTitle = setTxtTitle(R.string.loc_navigation);
		txtTitle.setOnClickListener(this);
		buildSelPopWin = new BuildSelPopupWin(this, DBDao.getInstance().getShops());
		
		mGLView = new GLSurfaceView(this);
		mRenderer = new Renderer(this);
		mGLView.setRenderer(mRenderer);
		mGLView.setKeepScreenOn(true);
		
		mGLView.setOnTouchListener(this);
		
		RelativeLayout panelView = (RelativeLayout) findViewById(R.id.layer_room_map);
		panelView.addView(mGLView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		initFloorView();
	}

    private void initFloorView() {
    	//TODO 测试数据
    	final ArrayList<Floor> datas  = new ArrayList<Floor>();
    	Floor fl = null;
		for (int i = 0; i < 5; i++) {
			fl = new Floor(i, "F" + (i+1));
			datas.add(fl);
		}
		
    	final Button btnSel = (Button) findViewById(R.id.btn_sel);
		ListView listview = (ListView) findViewById(R.id.listview_floor);
		final FloorAdapter adapter = new FloorAdapter(this, datas);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Floor fl = datas.get(position);
				String text = fl.getName();
				btnSel.setText(text);
				
				// 更改选中颜色
				adapter.setSelectItem(position);
				mRenderer.changeMap(position);
			}
			
		});
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
			if (xStart <= 0)
				xStart=event.getX();
			if (yStart <= 0)
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
//				mRenderer.MoveCamera(event.getX()-xStart, event.getY()-yStart);
				if (Math.abs(event.getX() - xStart) > 2) {
					xChanged = true;
					
					if (event.getX() - xStart < 0) {
						moveX -= sx;
					} else {
						moveX += sx;
					}
				}
				
				if (Math.abs(event.getY() - yStart) > 2) {
					yChanged = true;
					
					if (event.getY() - yStart < 0) {
						moveY += sy;
					} else {
						moveY -= sy;
					}
				}
				
				xStart = event.getX();
				yStart = event.getY();
				
				if(xChanged || yChanged) {
					mRenderer.MoveCamera(moveX, moveY);
					
					xChanged = false;
					yChanged = false;
				}
			}
			return true;
		}
		else if(event.getAction()==MotionEvent.ACTION_UP)
		{
//			mRenderer.CenterCamera();//当触摸手势离开时，重置位置到中心点
			xStart = yStart = 0;
			
			multiTouch = false;
			preScale = -1;
			initialDistance = 0;
			return true;
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		int viewId = v.getId();
		if (viewId == R.id.btn_left) {
			finish();
		} else if (viewId == R.id.btn_right) {
			showToast("开始定位操作");
		} else if (viewId == R.id.txtview_title) {
			buildSelPopWin.show(txtTitle);
		}
	}
	
	/**
	 * 加载建筑物地图
	 * @param buildId	建筑物ID
	 */
	public void loadBuildMap(String buildId) {
		showToast("加载建筑物地图:buildId=" + buildId);
	}
	
}
