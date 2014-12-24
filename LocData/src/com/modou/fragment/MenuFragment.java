package com.modou.fragment;

import com.modou.loc.R;
import com.modou.loc.adapter.LeftMenuAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_left, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		initView();
	}

	private void initView() {
		LeftMenuAdapter adapter = new LeftMenuAdapter(getActivity());
		ListView listView = (ListView) getView().findViewById(R.id.listview_left_menu);
		listView.setAdapter(adapter);
	}
	
}
