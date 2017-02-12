package com.itheima.mobilesafe.ui;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.log.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �Զ�����Ͽؼ�����������-����ѡ�ֻ��true false���������ѡ��
 * @author jacrylodai
 *
 */
public class ConfigItemTrueFalseView extends LinearLayout{
	
	private static final String TAG = "ConfigItemTrueFalseView";

	//����ѡ�����
	private TextView tvConfigItemTitle;

	//����ѡ��������Ϣ
	private TextView tvConfigItemDescription;
	
	//����ѡ��ֵ
	private CheckBox cbConfigItemValue;
	
	private void initView(Context context,AttributeSet attrs){

		LayoutInflater.from(context).inflate(R.layout.config_item_true_false, this);
		
		tvConfigItemTitle = (TextView) findViewById(R.id.tv_config_item_title);
		
		tvConfigItemDescription = (TextView) findViewById(R.id.tv_config_item_description);
		
		cbConfigItemValue = (CheckBox) findViewById(R.id.cb_config_item_value);
		
		//ȡ������ѡ�������ֵ
		String configItemTitle = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.mobilesafe"
				, "config_item_title");

		String configItemDescription = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.mobilesafe"
				, "config_item_description");
		
		if(configItemTitle != null){
			setConfigItemTitle(configItemTitle);
		}
		
		if(configItemDescription != null){
			setConfigItemDescription(configItemDescription);
		}
		
	}

	public ConfigItemTrueFalseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LogUtil.d(TAG, "default Cons 2");
		initView(context,attrs);
	}
	
	@SuppressLint("NewApi")
	public ConfigItemTrueFalseView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		LogUtil.d(TAG, "default Cons 3");
		initView(context,attrs);
	}

	public void setConfigItemTitle(String title){
		tvConfigItemTitle.setText(title);
	}
	
	public void setConfigItemDescription(String description){
		tvConfigItemDescription.setText(description);
	}
	
	public void setConfigItemValue(boolean isTrue){
		cbConfigItemValue.setChecked(isTrue);
	}
	
	/**
	 * ��������ѡ��ֵ��������Ļص�����
	 * @param onCheckedChangeListener
	 */
	public void setConfigItemValueCheckBoxListener(
			CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
		cbConfigItemValue.setOnCheckedChangeListener(onCheckedChangeListener);
	}

}
