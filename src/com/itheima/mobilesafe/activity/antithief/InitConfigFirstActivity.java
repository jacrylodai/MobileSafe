package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itheima.mobilesafe.R;

public class InitConfigFirstActivity extends BaseInitConfigActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_first);

		Button buttonNextConfig = (Button) findViewById(R.id.button_next_config);
		buttonNextConfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				showNextConfig();
			}
		});
		
	}

	@Override
	protected void showNextConfig(){

		Intent intent = new Intent(this,InitConfigSecondActivity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		//do nothing
	}
	
}
