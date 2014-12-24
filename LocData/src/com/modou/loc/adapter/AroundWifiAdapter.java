package com.modou.loc.adapter;

import java.util.List;

import com.modou.loc.R;
import com.modou.loc.entity.WifiEntity;
import com.modou.utils.MethodUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-8 下午3:38:50
 * 类说明:
 * 附近Wifi数据适配器
 */
public class AroundWifiAdapter extends BaseAdapter {

	private Context mContext;
	private List<WifiEntity> mDatas;
	
	public AroundWifiAdapter(Context ctx, List<WifiEntity> datas) {
		mContext = ctx;
		mDatas = datas;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.act_wifilist_child_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		WifiEntity entity = mDatas.get(position);
		viewHolder.txtViewTitle.setText(entity.getScanResult().SSID);
		int levelIndex = MethodUtils.parseWifiLevel(entity.getScanResult().level);
		viewHolder.imgViewSignal.setImageLevel(levelIndex);
		
		return convertView;
	}
	
	static class ViewHolder {
		protected ImageView imgViewSignal;
		protected TextView txtViewTitle;
		
		public ViewHolder(View view) {
			imgViewSignal = (ImageView) view.findViewById(R.id.imgview_signal);
			txtViewTitle = (TextView) view.findViewById(R.id.txtview_title);
		}
	}

	public void resetData(List<WifiEntity> result) {
		mDatas = result;
		notifyDataSetChanged();
	}

}
