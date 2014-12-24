package com.modou.loc;

import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;
import com.modou.utils.StringUtils;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-6-14 下午5:12:58
 * 类说明:
 * 应用设置页面
 */
public class FeedBackActivity extends BaseActivity {

	private Button btnSubmit;
	private EditText edttxtSuggest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		initView();
		initData();
	}

	private void initView() {
		setTxtTitle(R.string.suggest_page_title);
		setLeftBtn(R.drawable.btn_back_bg, R.string.back);
		
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnSubmit.setEnabled(false);
		btnSubmit.setOnClickListener(this);
		
		edttxtSuggest = (EditText) findViewById(R.id.edttxt_suggest);
		edttxtSuggest.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String res = s.toString();
				if (StringUtils.isEmpty(res)) {
					btnSubmit.setEnabled(false);
				} else {
					btnSubmit.setEnabled(true);
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		int viewId = v.getId();
		if (viewId == R.id.btn_submit) {
			showToast("执行提交评论操作，发送信息到server");
		} else if (viewId == R.id.btn_left) {
			finish();
		}
	}
	
}
