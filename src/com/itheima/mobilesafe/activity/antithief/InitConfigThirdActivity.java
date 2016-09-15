package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.itheima.mobilesafe.R;

public class InitConfigThirdActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_third);
	}
	
	public void nextConfig(View view){
		Intent intent = new Intent(this,InitConfigFourthActivity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}
	
	public void previousConfig(View view){
		Intent intent = new Intent(this,InitConfigSecondActivity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
}
