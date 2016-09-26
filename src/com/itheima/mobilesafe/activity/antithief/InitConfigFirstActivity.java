package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.AntiThiefInitConfigParams;

public class InitConfigFirstActivity extends BaseInitConfigActivity{
	
	private AntiThiefInitConfigParams antiThiefParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_first);
		
		antiThiefParams = (AntiThiefInitConfigParams) getIntent().getSerializableExtra(
				InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS);

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
		
		intent.putExtra(InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		//do nothing
	}
	
}
