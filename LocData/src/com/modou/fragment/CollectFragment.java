package com.modou.fragment;

import java.util.ArrayList;
import java.util.List;

import com.modou.loc.R;
import com.modou.loc.adapter.ShopAdapter;
import com.modou.loc.db.DBMgr;
import com.modou.loc.download.DlMapMgr;
import com.modou.loc.entity.Building;
import com.modou.loc.receiver.DlMapReceiver;
import com.modou.widget.OptionDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CollectFragment extends BaseFragment {

	private ShopAdapter adatper;
	private DlMapReceiver dlMapReceiver;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_collect, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		addReceiver();
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		setTxtTitle(R.string.shop_map_list);
		
		String[] urls = new String[] {
				"http://imgsrc.baidu.com/baike/pic/item/cc506c8be01cb25cc8fc7aff.jpg",
				"http://pic4.nipic.com/20091107/3664641_000146285763_2.jpg",
				"http://imgsrc.baidu.com/baike/pic/item/9304c8887b338cdaa4c272ff.jpg",
				"http://www.kansai-airport.or.jp/resshop/shop/199/image/main_yorozu.jpg",
				"http://img4.imgtn.bdimg.com/it/u=2704490036,279906104&fm=21&gp=0.jpg",
				"http://pic25.nipic.com/20121207/11547983_111418572000_2.jpg",
				"http://img5.imgtn.bdimg.com/it/u=777011768,2254612365&fm=21&gp=0.jpg",
				"http://img1.soufunimg.com/viewimage/zxb/2013_01/04/54/36/pic/407079499300/770x1500.jpg",
				"http://www.cnga.org.cn/news/text/201282411533652.jpg",
				"http://image.photophoto.cn/m-3/Marketplace/ShopSpace/0070050001.jpg",
				"http://img0.imgtn.bdimg.com/it/u=4096515241,1578078927&fm=21&gp=0.jpg" };
		final List<Building> datas = new ArrayList<Building>();
		Building shop = null;
		for (int i = 0; i < 11; i++) {
			shop = new Building((i+1) + "", "商店" + (i+1), urls[i], "地图下载地址");
			boolean isExist = DBMgr.getInstance().isExistInCache((i+1) + "");
			shop.setDownload(isExist);
			datas.add(shop);
		}
		GridView gridView = (GridView) getView().findViewById(
				R.id.gridview_shop);
		adatper = new ShopAdapter(getActivity(), datas);
		gridView.setAdapter(adatper);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Building shop = datas.get(position);
				boolean isDled = shop.isDownload();
				if (isDled) {
					deleteDialog(shop);
				} else if (DlMapMgr.getInstance().isDownloading(shop.getId())) {
					showToast(R.string.downloading);
				} else {
					downloadDialog(shop);
				}
			}
		});
	}
	
	
	/**
	 * 下载确认对话框
	 * @param shop	商城对象
	 */
	private void downloadDialog(final Building shop) {
		OptionDialog.Builder builder = new OptionDialog.Builder(getActivity());
		OptionDialog dialog = builder
				.setTitle(R.string.confirm_download_img_str)
				.setFristButton(R.string.confirm,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showToast("下载文件操作");
								downloadMapFile(shop);
							}
						})
				.setSecondButton(R.string.cancel, null)
				.create();
		dialog.show();
	}
	
	/**
	 * 删除本地商城文件对话框
	 * @param shop	商城对象
	 */
	private void deleteDialog(final Building shop) {
		OptionDialog.Builder builder = new OptionDialog.Builder(getActivity());
		OptionDialog dialog = builder
				.setTitle(R.string.confirm_del_img_str)
				.setFristButton(R.string.confirm,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showToast("删除文件操作");
								deleteFileMap(shop);
							}
						})
				.setSecondButton(R.string.cancel, null)
				.create();
		dialog.show();
	}

	
	/**
	 * 删除本地地图文件
	 * @param shop
	 */
	private void deleteFileMap(Building shop) {
		String shopId = shop.getId();
		DBMgr.getInstance().delShopRecord(shopId);
		
		// 更新适配器中对应的下载状态
		shop.setDownload(false);
		adatper.notifyDataSetChanged();
	}
	
	/**下载地图文件*/
	private void downloadMapFile(Building build) {
		
		DlMapMgr.getInstance().addTask(build);
		
		/*String shopId = shop.getId();
		String shopName = shop.getName();
		String shopImgUrl = shop.getIconUrl();
		DBMgr.getInstance().addShop(shopId, shopName, shopImgUrl);
		
		// 更新适配器中对应的下载状态
		shop.setDownload(true);
		adatper.notifyDataSetChanged();*/
	}
	
	@Override
	public void onDestroy() {
		if (getActivity() != null) {
			if (dlMapReceiver != null)
				getActivity().unregisterReceiver(dlMapReceiver);
		}
		super.onDestroy();
	}

	private void addReceiver() {
		dlMapReceiver = new DlMapReceiver() {
			
			@Override
			protected void successDlMap(String mapId) {
				Building build = adatper.getItem(mapId);
				build.setDownload(true);
				adatper.notifyDataSetChanged();
			}
			
			@Override
			protected void startDlMap(String mapId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void failDlMap(String mapId) {
				// TODO Auto-generated method stub
				
			}
		};
		
		dlMapReceiver.register(getActivity());
	}
	
}
