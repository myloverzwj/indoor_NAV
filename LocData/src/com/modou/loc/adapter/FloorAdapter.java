package com.modou.loc.adapter;

import java.util.ArrayList;

import com.modou.loc.R;
import com.modou.loc.entity.Floor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FloorAdapter extends BaseAdapter {

	private ArrayList<Floor> datas;
	private Context mContext;
	
	public FloorAdapter(Context ctx, ArrayList<Floor> datas) {
		this.datas = datas;
		this.mContext = ctx;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setSelectItem(int position) {
		final int count = getCount();
		Floor floor = null;
		for (int i = 0; i < count; i++) {
			floor = (Floor) getItem(i);
			if (i == position)
				floor.setSelected(true);
			else
				floor.setSelected(false);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.floor_listitem_layout, null);
			vHolder = new ViewHolder(convertView);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		
		Floor fl = datas.get(position);
		int itemBg = fl.isSelected() ? R.color.blue1 : R.color.white1;
		vHolder.btn.setBackgroundColor(mContext.getResources().getColor(itemBg));
		vHolder.btn.setText(fl.getName());
		
		return convertView;
	}
	
	static class ViewHolder {
		private TextView btn;
		public ViewHolder(View v) {
			btn = (TextView) v.findViewById(R.id.btn_f);
		}
	}

}
