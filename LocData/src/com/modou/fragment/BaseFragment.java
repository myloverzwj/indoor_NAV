package com.modou.fragment;

import com.modou.loc.R;
import com.modou.utils.ToastUtils;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BaseFragment extends Fragment implements OnClickListener {

	protected Button setLeftBtnImg(int imgId) {
		Button btn = (Button) getView().findViewById(R.id.btn_left);
		if (btn != null) {
			btn.setVisibility(View.VISIBLE);
			btn.setBackgroundResource(imgId);
			btn.setOnClickListener(this);
		}
		return btn;
	}
	
	protected Button setRightBtnImg(int imgId) {
		Button btn = (Button) getView().findViewById(R.id.btn_right);
		if (btn != null) {
			btn.setVisibility(View.VISIBLE);
			btn.setBackgroundResource(imgId);
			btn.setOnClickListener(this);
		}
		return btn;
	}
	
	protected void setTxtTitle(int resId) {
		TextView txt = (TextView) getView().findViewById(R.id.txtview_title);
		if (txt != null) {
			txt.setText(resId);
		}
	}
	
	protected void showToast(String res) {
		ToastUtils.show(getActivity(), res);
	}
	
	protected void showToast(int resId) {
		ToastUtils.show(getActivity(), resId);
	}

	protected void showAutoToast(String resId) {
		ToastUtils.showAutoToast(getActivity(), resId, 0);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
