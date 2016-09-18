package com.itheima.mobilesafe.activity.antithief;

import android.os.Bundle;

import com.itheima.mobilesafe.R;

public class InitConfigSecondActivity extends BaseInitConfigActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_second);
	}

	@Override
	protected Class<?> getNextConfigActivity() {
		return InitConfigThirdActivity.class;
	}

	@Override
	protected Class<?> getPreviousConfigActivity() {
		return InitConfigFirstActivity.class;
	}
	
}
