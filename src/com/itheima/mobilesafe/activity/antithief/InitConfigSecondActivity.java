package com.itheima.mobilesafe.activity.antithief;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.AntiThiefInitConfigParams;
import com.itheima.mobilesafe.ui.ConfigItemTrueFalseView;
import com.itheima.mobilesafe.utils.log.LogUtil;

public class InitConfigSecondActivity extends BaseInitConfigActivity{
	
	private static final String TAG = "InitConfigSecondActivity";
	
	private ConfigItemTrueFalseView ciInitConfigBindSIMCard;
	
	private AntiThiefInitConfigParams antiThiefParams;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_second);
		
		antiThiefParams = (AntiThiefInitConfigParams) getIntent().getSerializableExtra(
				InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS);
		
		ciInitConfigBindSIMCard = 
				(ConfigItemTrueFalseView) findViewById(R.id.ci_init_config_bind_sim_card);
		
		boolean isBindSIMCard = antiThiefParams.getIsBindSIMCard();
		ciInitConfigBindSIMCard.setConfigItemValue(isBindSIMCard);
		
		ciInitConfigBindSIMCard.setConfigItemValueCheckBoxListener(
				new CompoundButton.OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if(isChecked){
							
							TelephonyManager tm = (TelephonyManager) getSystemService(
									Context.TELEPHONY_SERVICE);
							String simSerialNumber = tm.getSimSerialNumber();
							if(TextUtils.isEmpty(simSerialNumber)){
								
								Toast.makeText(InitConfigSecondActivity.this, 
										"绑定SIM卡失败，手机上没有SIM卡", Toast.LENGTH_SHORT).show();
								
								antiThiefParams.setIsBindSIMCard(false);
								antiThiefParams.setSimCardSerialNumber("");
								
								ciInitConfigBindSIMCard.setConfigItemValue(false);
							}else{
								
								LogUtil.d(TAG, "bind SIM card success,SIM card serial number:"
										+simSerialNumber);

								Toast.makeText(InitConfigSecondActivity.this, 
										"绑定SIM卡成功", Toast.LENGTH_SHORT).show();
								
								antiThiefParams.setIsBindSIMCard(true);
								antiThiefParams.setSimCardSerialNumber(simSerialNumber);
							}
							
						}else{

							antiThiefParams.setIsBindSIMCard(false);
							antiThiefParams.setSimCardSerialNumber("");
						}
						
					}});
		

		Button buttonNextConfig = (Button) findViewById(R.id.button_next_config);
		buttonNextConfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				showNextConfig();
			}
		});
		
		Button buttonPreviousConfig = (Button)findViewById(R.id.button_previous_config);
		buttonPreviousConfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				showPreviousConfig();
			}
		});
		
	}

	@Override
	protected void showNextConfig(){

		Intent intent = new Intent(this,InitConfigThirdActivity.class);

		intent.putExtra(InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigFirstActivity.class);
		
		intent.putExtra(InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
		
}
