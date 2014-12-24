package com.modou.loc.adapter;

import com.modou.loc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuAdapter extends BaseAdapter {

	private Context mContext;
	
	private final int[] iconArr = new int[]{ R.drawable.ic_portable_ap, R.drawable.ic_net_speed_test,
										  R.drawable.ic_power_saving, R.drawable.ic_traffic, 
										  R.drawable.ic_id };
	private final int[] strArr = new int[]{ R.string.wifi_portable, R.string.location_record, 
											R.string.power_saving_mode, R.string.flow_info,
											R.string.personal };
	
	public LeftMenuAdapter(Context ctx) {
		mContext = ctx;
	}
	
	@Override
	public int getCount() {
		return iconArr.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_left_menu, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imgViewIcon.setImageResource(iconArr[position]);
		viewHolder.txtViewStr.setText(strArr[position]);
		return convertView;
	}
	
	static class ViewHolder {
		
		protected ImageView imgViewIcon;
		protected TextView txtViewStr;
		
		public ViewHolder(View view) {
			imgViewIcon = (ImageView) view.findViewById(R.id.imgview_icon);
			txtViewStr = (TextView) view.findViewById(R.id.txtview_str);
		}
	}

}
