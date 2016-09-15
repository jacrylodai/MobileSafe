package com.itheima.mobilesafe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ConfigInfo;
import com.itheima.mobilesafe.ui.ConfigItemTrueFalseView;
import com.itheima.mobilesafe.utils.log.LogUtil;

/**
 * …Ë÷√÷––ƒ
 * @author jacrylodai
 *
 */
public class ConfigActivity extends ActionBarActivity {
	
	private static final String TAG = "ConfigActivity";
		
	private ConfigItemTrueFalseView configItemConfigShowUpdate;
	
	private SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);
		
		configItemConfigShowUpdate = (ConfigItemTrueFalseView) findViewById(
				R.id.config_item_config_show_update);
		
		boolean showUpdateValue = ConfigInfo.getIsShowUpdateKey(pref);
		configItemConfigShowUpdate.setConfigItemValue(showUpdateValue);
		
		CompoundButton.OnCheckedChangeListener showUpdateListener = 
				new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LogUtil.d(TAG, "check state is:"+isChecked);
				
				boolean showUpdateValue = isChecked;
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(ConfigInfo.IS_SHOW_UPDATE_KEY, showUpdateValue);
				editor.commit();
			}
		};
		configItemConfigShowUpdate.setConfigItemValueCheckBoxListener(showUpdateListener);
	}
	
}
