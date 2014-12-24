package com.modou.loc.adapter;

import com.modou.loc.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideAdapter extends PagerAdapter {

	private Context mContext;
	private int[] mDatas;
	private SparseArray<ViewPagerItemView> mViews;
	private GuideClickListener guideClickListener;
	
	public GuideAdapter(Context ctx, int[] datas) {
		this.mContext = ctx;
		this.mDatas = datas;
		mViews = new SparseArray<ViewPagerItemView>();
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		int resImgId = mDatas[position];
		ViewPagerItemView itemView = mViews.get(resImgId);
		if (itemView != null) {
			itemView.reload();
		} else {
			itemView = new ViewPagerItemView(mContext, position);
			itemView.setData(resImgId);
			itemView.setClickListener(guideClickListener);
			mViews.put(resImgId, itemView);
			
			((ViewPager) container).addView(itemView);
		}
		return itemView;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		ViewPagerItemView itemView = (ViewPagerItemView) object;
		itemView.recyle();
	}
	
	@Override
	public int getCount() {
		return mDatas.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	public interface GuideClickListener {
		void onClick(View view);
	}
	
	public void setGuidItemClickListener(GuideClickListener listener) {
		guideClickListener = listener;
	}
	
	class ViewPagerItemView extends FrameLayout implements OnClickListener {

		// 图片组件
		private ImageView showImgView;
		// 分享按钮
		private Button shareBtn;
		// 开始体验按钮
		private Button startBtn;
		// 标题
		private TextView titleTxtView;
		
		// 当前显示页索引
		private int pageIndex;

		private int mImgId;
		private GuideClickListener clickListener;
		
		public ViewPagerItemView(Context context) {
			super(context);
			initViews();
		}
		
		public ViewPagerItemView(Context context, int position) {
			super(context);
			this.pageIndex = position;
			initViews();
		}
		
		public void setClickListener(GuideClickListener listener) {
			clickListener = listener;
		}

		public ViewPagerItemView(Context context, AttributeSet attrs) {
			super(context, attrs);
			initViews();
		}
		
		public void initViews() {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View panel = null;
			if (pageIndex == mDatas.length - 1) {
				// 当前要显示的是最后一页
				panel = inflater.inflate(R.layout.viewpager_last_itemview, null);
				
//				shareBtn = (Button) panel.findViewById(R.id.btn_share);
//				shareBtn.setOnClickListener(this);
				startBtn = (Button) panel.findViewById(R.id.btn_start);
				startBtn.setOnClickListener(this);
				titleTxtView = (TextView) panel.findViewById(R.id.txtview_title);
				titleTxtView.setOnClickListener(this);
			} else {
				panel = inflater.inflate(R.layout.viewpager_itemview, null);
			}
			showImgView = (ImageView) panel.findViewById(R.id.imgview_show);
			
			addView(panel);
		}
		
		public void setData(int resId) {
			this.mImgId = resId;
			showImgView.setImageResource(resId);
		}
		
		public void recyle() {
			showImgView.setImageBitmap(null);
		}
		
		public void reload() {
			showImgView.setImageResource(mImgId);
		}

		@Override
		public void onClick(View v) {
			clickListener.onClick(v);
		}
		
	}
	
}
