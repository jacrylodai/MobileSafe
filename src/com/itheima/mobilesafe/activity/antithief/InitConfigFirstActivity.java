package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.itheima.mobilesafe.R;

public class InitConfigFirstActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_first);
	}
	
	public void nextConfig(View view){
		Intent intent = new Intent(this,InitConfigSecondActivity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}
	
}