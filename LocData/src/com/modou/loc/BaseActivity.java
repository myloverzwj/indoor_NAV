package com.modou.loc;

import com.modou.utils.ToastUtils;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends Activity implements OnClickListener {

	protected Button setLeftBtn(int imgId) {
		Button btn = setLeftBtn(imgId, 0);
		return btn;
	}
	
	protected Button setLeftBtn(int imgId, int strId) {
		Button btn = (Button) findViewById(R.id.btn_left);
		if (btn != null) {
			btn.setVisibility(View.VISIBLE);
			if (imgId != 0)
				btn.setBackgroundResource(imgId);
			if (strId != 0)
				btn.setText(strId);
			btn.setOnClickListener(this);
		}
		return btn;
	}
	
	protected Button setRightBtnImg(int imgId) {
		Button btn = (Button) findViewById(R.id.btn_right);
		if (btn != null) {
			btn.setVisibility(View.VISIBLE);
			btn.setBackgroundResource(imgId);
			btn.setOnClickListener(this);
		}
		return btn;
	}
	
	protected Button setRightBtn(int imgId, int strId) {
		Button btn = (Button) findViewById(R.id.btn_right);
		if (btn != null) {
			btn.setVisibility(View.VISIBLE);
			if (imgId > 0)
				btn.setBackgroundResource(imgId);
			if (strId > 0)
				btn.setText(strId);
			btn.setOnClickListener(this);
		}
		return btn;
	}
	
	protected TextView setTxtTitle(int resId) {
		TextView txt = (TextView) findViewById(R.id.txtview_title);
		if (txt != null) {
			txt.setText(resId);
		}
		return txt;
	}
	
	protected TextView setTxtTitle(String msg) {
		TextView txt = (TextView) findViewById(R.id.txtview_title);
		if (txt != null) {
			txt.setText(msg);
		}
		return txt;
	}
	
	protected void showToast(String res) {
		ToastUtils.show(this, res);
	}
	
	protected void showToast(int resId) {
		ToastUtils.show(this, resId);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
