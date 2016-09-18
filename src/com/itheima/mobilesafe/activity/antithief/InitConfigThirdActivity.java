package com.itheima.mobilesafe.activity.antithief;

import android.os.Bundle;

import com.itheima.mobilesafe.R;

public class InitConfigThirdActivity extends BaseInitConfigActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_third);
	}

	@Override
	protected Class<?> getNextConfigActivity() {
		return InitConfigFourthActivity.class;
	}

	@Override
	protected Class<?> getPreviousConfigActivity() {
		return InitConfigSecondActivity.class;
	}
	
	
}
