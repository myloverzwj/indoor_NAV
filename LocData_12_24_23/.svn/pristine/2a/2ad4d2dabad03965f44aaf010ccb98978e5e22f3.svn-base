package com.modou.widget;

import com.modou.loc.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义dialog类
 */
public class OptionDialog extends Dialog {

	public OptionDialog(Context context, int theme) {
		super(context, theme);
	}

	public OptionDialog(Context context) {
		super(context);
	}

	public static class Builder {
		LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		private Context context;
		private String titleText,message;
		private View layout;
		private View content = null;
		private String fristText, secondText;
		private boolean autoCancle = true;
		private Drawable res;

		private DialogInterface.OnClickListener fristTextListener,
				secondTextListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * 设置标题
		 * @param resid
		 * @return
		 */
		public Builder setTitle(int resid) {
			this.titleText = (String) context.getText(resid);
			return this;
		}
		
		private float titleTxtSize = -1;
		/**设置标题字号*/
		public Builder setTitleTxtSize(float size) {
			this.titleTxtSize = size;
			return this;
		}
		
		private float msgTxtSize = -1;
		/**设置Message字号*/
		public Builder setMsgTxtSize(float size) {
			this.msgTxtSize = size;
			return this;
		}
		
		/**
		 * 设置标题
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.titleText = title;
			return this;
		}
		
		/**
		 * 设置信息体
		 * @param resid
		 * @return
		 */
		public Builder setMessage(int resid) {
			this.message = (String) context.getText(resid);
			return this;
		}
		
		/**
		 * 设置信息体
		 * @param msg
		 * @return
		 */
		public Builder setMessage(String msg) {
			this.message = msg;
			return this;
		}
		
		public Builder setAutoCancle(boolean flag){
			this.autoCancle = flag;
			return this;
		}

	
		/**
		 * 设置button监听
		 * @param resid
		 * @param listener
		 * @return
		 */
		public Builder setFristButton(int resid,
				DialogInterface.OnClickListener listener) {
			this.fristText = (String) context.getText(resid);
			this.fristTextListener = listener;
			return this;
		}

		/**
		 * 设置button监听
		 * @param resid
		 * @param listener
		 * @return
		 */
		public Builder setFristButton(String text,
				DialogInterface.OnClickListener listener) {
			this.fristText = text;
			this.fristTextListener = listener;
			return this;
		}

		public Builder setSecondButton(int resid,
				DialogInterface.OnClickListener listener) {
			this.secondText = (String) context.getText(resid);
			this.secondTextListener = listener;
			return this;
		}

		public Builder setSecondButton(String text,
				DialogInterface.OnClickListener listener) {
			this.secondText = text;
			this.secondTextListener = listener;
			return this;
		}

		/**
		 * 设置dialog内容
		 * @param content
		 * @return
		 */
		public Builder setContentView(View content){
			this.content = content;
			return this;
		}
		
		private int btnOrientaion = LinearLayout.HORIZONTAL;
		public Builder setBtnOrientation(int orientation) {
			btnOrientaion = orientation;
			return this;
		}
		
		private int bgId = -1;
		public Builder setFirstButtonBG(int resId){
			this.bgId = resId;
			return this;
		}
		
		private int secondBtnBg = -1;
		public Builder setSecondBtnBG(int resId) {
			this.secondBtnBg = resId;
			return this;
		}
		public void setDrawable(int resId){
			res = context.getResources().getDrawable(resId);
		}
		public Drawable secondBg(){
			return res;
		}
		private int firstBtnTxtColor = -1;
		public Builder setFisrtBtnTxtColor(int color) {
			this.firstBtnTxtColor = color;
			return this;
		}
		
		private int secondBtnVisible = View.VISIBLE;
		/**设置取消按钮的显示隐藏状态*/
		public void setSecondBtnVisible(int visible) {
			secondBtnVisible = visible;
		}
		
		
		
		private int secondBtnTxtColor = -1;
		public Builder setSecondBtnTxtColor(int color) {
			this.secondBtnTxtColor = color;
			return this;
		}

		/**
		 * 创建一个自定义的对话框
		 */
		public OptionDialog create() {
			LayoutInflater inflater = LayoutInflater.from(context);
			if (btnOrientaion == LinearLayout.HORIZONTAL) {
				layout = inflater.inflate(R.layout.widget_dialog_defalut_model, null);
			} else {
				layout = inflater.inflate(R.layout.widget_dialog_model_btn_vertical, null);
			}
			final OptionDialog dialog = new OptionDialog(context, R.style.Dialog);
			dialog.setCanceledOnTouchOutside(autoCancle);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			TextView title = (TextView) layout.findViewById(R.id.dialog_title);
			View second_bg = layout.findViewById(R.id.second_bg);
			View button_content = layout.findViewById(R.id.content);
			if(titleText != null){
				if(title != null){
					title.setVisibility(View.VISIBLE);
					title.setText(titleText);
				}
				if (titleTxtSize > 0) {
					title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTxtSize);
				}
			} else {
				title.setVisibility(View.GONE);
			}
			if(message != null){
				TextView msg = (TextView) layout.findViewById(R.id.dialog_message);
				if(msg != null){
					msg.setVisibility(View.VISIBLE);
					msg.setText(message+"\n");
					if(secondBg()!=null){
						second_bg.setBackgroundDrawable(res);
					}
					
				}
				if (msgTxtSize > 0) {
					msg.setTextSize(TypedValue.COMPLEX_UNIT_PX, msgTxtSize);
				}
			}
			if(content != null){
				LinearLayout parent = (LinearLayout) layout.findViewById(R.id.dialog_content);
				parent.setVisibility(View.VISIBLE);
				parent.addView(content);
				
			}
//			if(secondBg()==null){
//				button_content.setBackgroundResource(R.drawable.alert_bottom);
//			}
			if (fristText != null) {
				TextView tv = ((TextView) layout.findViewById(R.id.txt_frist));
				if(tv != null){
					tv.setVisibility(View.VISIBLE);
					tv.setText(fristText);
					if(bgId != -1)
						tv.setBackgroundResource(bgId);
					if (firstBtnTxtColor != -1)
						tv.setTextColor(context.getResources().getColor(firstBtnTxtColor));
					tv.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
							if (fristTextListener != null)fristTextListener.onClick(dialog, DialogInterface.BUTTON1);
						}
					});
				}
			}

			if (secondText != null) {
				TextView tv = ((TextView) layout.findViewById(R.id.txt_second));
				if(tv != null){
					tv.setVisibility(View.VISIBLE);
					tv.setText(secondText);
					if (secondBtnBg != -1)
						tv.setBackgroundResource(secondBtnBg);
					if (secondBtnTxtColor != -1)
						tv.setTextColor(context.getResources().getColor(secondBtnTxtColor));
					tv.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
							if (secondTextListener != null)secondTextListener.onClick(dialog,
									DialogInterface.BUTTON2);
						}
					});
				}
			} 
			dialog.setContentView(layout);
			return dialog;
		}

	}

}