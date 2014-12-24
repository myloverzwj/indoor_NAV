package com.modou.loc.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Building implements Parcelable {
	
	/**商城ID*/
	private String id;
	/**商城名称*/
	private String name;
	/**商城缩略图URL*/
	private String iconUrl;
	/**下载地图地址URL*/
	private String dlMapUrl;
	/**是否已下载*/
	private boolean isDownload;
	
	public Building() {
		
	}
	
	public Building(String id, String name, String iconUrl, String dlMapUrl) {
		this.id = id;
		this.name = name;
		this.iconUrl = iconUrl;
		this.dlMapUrl = dlMapUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public boolean isDownload() {
		return isDownload;
	}
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	public String getDlMapUrl() {
		return dlMapUrl;
	}

	public void setDlMapUrl(String dlMapUrl) {
		this.dlMapUrl = dlMapUrl;
	}

	private Building (Parcel in) {
		id = in.readString();
		name = in.readString();
		iconUrl = in.readString();
		dlMapUrl = in.readString();
		boolean[] flag = new boolean[1];
		in.readBooleanArray(flag);
		isDownload = flag[0];
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(iconUrl);
		out.writeString(dlMapUrl);
		out.writeBooleanArray(new boolean[]{isDownload});
	}
	
	public static final Parcelable.Creator<Building> CREATOR = new Parcelable.Creator<Building>() {

		@Override
		public Building createFromParcel(Parcel in) {
			return new Building(in);
		}

		@Override
		public Building[] newArray(int size) {
			return new Building[size];
		}
	};
}
