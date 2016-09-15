package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.itheima.mobilesafe.R;

public class LostFindActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
	}
	
	public void reconfig(View view){
		
		Intent intent = new Intent(this,InitConfigFirstActivity.class);
		startActivity(intent);
		finish();
	}
	
}
