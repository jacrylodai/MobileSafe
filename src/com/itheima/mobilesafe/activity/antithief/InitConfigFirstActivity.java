package com.itheima.mobilesafe.activity.antithief;

import android.os.Bundle;

import com.itheima.mobilesafe.R;

public class InitConfigFirstActivity extends BaseInitConfigActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_first);
	}

	@Override
	protected Class<?> getNextConfigActivity() {
		return InitConfigSecondActivity.class;
	}

	@Override
	protected void showPreviousConfig() {
		//do nothing
	}
	
}
