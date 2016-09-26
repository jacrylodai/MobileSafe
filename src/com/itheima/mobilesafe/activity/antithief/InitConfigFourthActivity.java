package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.AntiThiefInitConfigParams;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class InitConfigFourthActivity extends BaseInitConfigActivity{
	
	public static final String EXTRA_ANTI_THIEF_PARAMS = 
			"com.itheima.mobilesafe.extra_anti_thief_params";
	
	private AntiThiefInitConfigParams antiThiefParams;
	
	private CheckBox cbSetAntiThiefProtect;
	
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_fourth);

		antiThiefParams = (AntiThiefInitConfigParams) getIntent().getSerializableExtra(
				InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		
		boolean isAntiThiefProtectOpen = antiThiefParams.getIsAntiThiefProtectOpen();
		
		cbSetAntiThiefProtect = (CheckBox) findViewById(R.id.cb_set_anti_thief_protect);
		cbSetAntiThiefProtect.setChecked(isAntiThiefProtectOpen);
		
		cbSetAntiThiefProtect.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				boolean isAntiThiefProtectOpen = isChecked;
				antiThiefParams.setIsAntiThiefProtectOpen(isAntiThiefProtectOpen);
				
				if(isAntiThiefProtectOpen){
					
					if(validataAntiThiefParams() == false){
						
						antiThiefParams.setIsAntiThiefProtectOpen(false);
						cbSetAntiThiefProtect.setChecked(false);
					}
				}			
			}
		});

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
	
	/**
	 * 业务验证
	 * 如果开启防盗保护，则必须绑定SIM卡，并设置安全号码
	 * 绑定SIM卡时，手机上必须有SIM卡
	 * 如果没有开启防盗保护，是否绑定SIM卡，是否设置安全号码都没有关系
	 * @return
	 */
	private boolean validataAntiThiefParams(){

		boolean isBindSIMCard = antiThiefParams.getIsBindSIMCard();
		String simCardSerialNumber = antiThiefParams.getSimCardSerialNumber();
		String alertPhoneNumber = antiThiefParams.getAlertPhoneNumber();
		boolean isAntiThiefProtectOpen = antiThiefParams.getIsAntiThiefProtectOpen();
		
		if(isAntiThiefProtectOpen){
			
			if(isBindSIMCard == false){
				Toast.makeText(this,
						"开启失败，开启防盗保护必须绑定SIM卡，" +
						"请回到之前的步骤进行设置", Toast.LENGTH_LONG).show();
				return false;
			}
			if(TextUtils.isEmpty(alertPhoneNumber)){
				Toast.makeText(this,
						"开启失败，开启防盗保护必须设置安全号码，" +
						"请回到上一步进行设置", Toast.LENGTH_LONG).show();
				return false;
			}
			
			return true;
		}else{
			
			return true;
		}
	}

	@Override
	protected void showNextConfig(){
		
		if(validataAntiThiefParams()){
			
			boolean isBindSIMCard = antiThiefParams.getIsBindSIMCard();
			String simCardSerialNumber = antiThiefParams.getSimCardSerialNumber();
			String alertPhoneNumber = antiThiefParams.getAlertPhoneNumber();
			boolean isAntiThiefProtectOpen = antiThiefParams.getIsAntiThiefProtectOpen();
			
			SharedPreferences.Editor editor = pref.edit();
			
			editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_INIT_CONFIG_KEY, true);
			
			editor.putBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, isBindSIMCard);
			editor.putString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY, simCardSerialNumber);
			editor.putString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, alertPhoneNumber);
			editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, isAntiThiefProtectOpen);
			
			editor.commit();
			
			finish();
		}else{
			
			//do nothing
		}
		
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigThirdActivity.class);
		
		intent.putExtra(EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
}
