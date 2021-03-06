package com.modou.widget;

import java.util.ArrayList;
import java.util.List;

import com.modou.loc.IndoorMapActivity;
import com.modou.loc.MapActivity;
import com.modou.loc.R;
import com.modou.loc.db.DBDao;
import com.modou.loc.entity.Building;
import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-13 上午8:40:51
 * 类说明:
 * 建筑物选项选择页面
 */
public class BuildSelPopupWin {

	private Context mContext;
	private PopupWindow popupWindow;
	private ListView lv_group;
	private View viewPanel;
	private List<Building> datas;
	
	public BuildSelPopupWin(Context ctx, List<Building> datas) {
		this.mContext = ctx;
		this.datas = datas;
		initData();
	}

	public void show(View parent) {
		if (popupWindow == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewPanel = inflater.inflate(R.layout.list_build_sel_panel, null);
			lv_group = (ListView) viewPanel.findViewById(R.id.lvGroup);
			lv_group.setOnItemClickListener(itemClickListener);
			
			DataAdapter adapter = new DataAdapter(datas);
			lv_group.setAdapter(adapter);
			// 创建一个PopupWindow对象
			int popW = mContext.getResources().getDimensionPixelSize(R.dimen.build_popupwin_width);
			int popH = mContext.getResources().getDimensionPixelSize(R.dimen.build_popupwin_height);
			popupWindow = new PopupWindow(viewPanel, popW, popH);
		}
		
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;

		Log.i("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
				+ windowManager.getDefaultDisplay().getWidth() / 2);
		//
		Log.i("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);

		Log.i("coder", "xPos:" + xPos);

//		popupWindow.showAsDropDown(parent, xPos, 0);
		int[] pos = MethodUtils.getViewCenterOnScreen(parent, popupWindow.getWidth());
		popupWindow.showAsDropDown(parent, -((popupWindow.getWidth() / 2) - parent.getWidth()/ 2), 0);
	}

	private void initData() {
//		datas = new ArrayList<Building>();
//		//TODO 测试数据，正常流程串通后需从DB库中读取
//		Building build = null;
//		for (int i = 0; i < 5; i++) {
//			build = new Building((i+1)+"", "建筑物" + (i+1), "", "");
//			datas.add(build);
//		}
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Building building = datas.get(position);
			Toast.makeText(mContext, "切换为[" + building.getName() + "]地图", Toast.LENGTH_SHORT).show();
			//TODO 加载地图操作
			String buildId = building.getId();
			((MapActivity)mContext).loadBuildMap(buildId);
			
			popupWindow.dismiss();
		}
	};

	/**数据适配器*/
	class DataAdapter extends BaseAdapter {

		private List<Building> buildList;
		
		public DataAdapter (List<Building> datas) {
			this.buildList = datas;
		}
		
		@Override
		public int getCount() {
			return buildList.size();
		}

		@Override
		public Object getItem(int position) {
			return buildList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView==null) {
				convertView=LayoutInflater.from(mContext).inflate(R.layout.listitem_build_sel, null);
				holder=new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem);
			} else {
				holder=(ViewHolder) convertView.getTag();
			}
			
			Building build = buildList.get(position);
			holder.groupItem.setText(build.getName());
			
			return convertView;
		}
		
		class ViewHolder {
			TextView groupItem;
		}
		
	}
	
}
