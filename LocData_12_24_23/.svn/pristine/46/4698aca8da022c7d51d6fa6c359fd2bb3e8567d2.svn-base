package com.modou.loc.adapter;

import java.util.List;

import com.modou.loc.R;
import com.modou.loc.entity.Building;
import com.modou.utils.LoadImageAsyncTask;
import com.modou.utils.LoadImageAsyncTask.ImageCallback;
import com.modou.utils.MLog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdapter extends BaseAdapter {

	private Context mContext;
	private List<Building> datas;
	
	public ShopAdapter(Context mContext, List<Building> datas) {
		this.mContext = mContext;
		this.datas = datas;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	/**
	 * 获取建筑物对象
	 * @param buildId	建筑物ID
	 * @return
	 */
	public Building getItem(String buildId) {
		int size = getCount();
		Building building = null;
		for (int i = 0; i < size; i++) {
			building = datas.get(i);
			if (buildId.equals(building.getId()))
				return building;
		}
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_listitem, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Building shop = datas.get(position);
		viewHolder.txtviewShopName.setText(shop.getName());
		viewHolder.imgShop.setTag(shop.getIconUrl());
		Drawable cachedImage = LoadImageAsyncTask.getInstance(mContext).loadDrawableOtherPath(shop.getIconUrl(),
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (imageDrawable != null) {
							ImageView imgView = (ImageView) parent.findViewWithTag(imageUrl);
							if (imgView != null) {
								imgView.setImageDrawable(imageDrawable);
								MLog.d("从网络中拿取图片");
							}
						}
					}
				});
		if (cachedImage != null) {
			MLog.d("从本地缓存中读取图片");
			viewHolder.imgShop.setImageDrawable(cachedImage);
		}
		
		int cornerVis = shop.isDownload() ? View.VISIBLE : View.GONE;
		viewHolder.imgviewCorner.setVisibility(cornerVis);
		
		return convertView;
	}
	
	static class ViewHolder {
		protected ImageView imgShop;
		protected TextView txtviewShopName;
		protected ImageView imgviewCorner;
		
		public ViewHolder(View panel) {
			imgShop = (ImageView) panel.findViewById(R.id.img_shop);
			txtviewShopName = (TextView) panel.findViewById(R.id.txtview_shopname);
			imgviewCorner = (ImageView) panel.findViewById(R.id.img_dl_over_corner);
		}
	}

}
