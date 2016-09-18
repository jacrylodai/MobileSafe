package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ConfigInfo;

public class InitConfigFourthActivity extends BaseInitConfigActivity{
	
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_fourth);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
	}

	protected void showNextConfig(){
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_INIT_CONFIG_KEY, true);
		editor.commit();
		
		Intent intent = new Intent(this,LostFindActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected Class<?> getPreviousConfigActivity() {
		return InitConfigThirdActivity.class;		
	}	
	
}
