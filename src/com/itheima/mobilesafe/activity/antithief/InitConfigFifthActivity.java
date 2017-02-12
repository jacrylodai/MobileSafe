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
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class InitConfigFifthActivity extends BaseInitConfigActivity{
	
	private CheckBox cbSetAntiThiefProtect;
	
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_fifth);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
				
		boolean isAntiThiefProtectOpen = 
				pref.getBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		if(isAntiThiefProtectOpen){
			if(validataIsOpenAntiThiefAvailable(false) == false){
				//业务验证失败，无法开启防盗保护，设置防盗保护开启状态为false
				isAntiThiefProtectOpen = false;
				
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
				editor.commit();
			}
		}
		
		cbSetAntiThiefProtect = (CheckBox) findViewById(R.id.cb_set_anti_thief_protect);
		cbSetAntiThiefProtect.setChecked(isAntiThiefProtectOpen);
		
		cbSetAntiThiefProtect.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked){
					
					if(validataIsOpenAntiThiefAvailable(true)){
						
						SharedPreferences.Editor editor = pref.edit();
						editor.putBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, true);
						editor.commit();
						
					}else{
						//开启防盗保护失败，回置单选框状态为false
						cbSetAntiThiefProtect.setChecked(false);
					}
				}else{

					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
					editor.commit();
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
	 * 如果开启防盗保护，则必须并设置安全号码
	 * 如果没有开启防盗保护，是否设置安全号码都没有关系
	 * @return
	 */
	private boolean validataIsOpenAntiThiefAvailable(boolean isShowMessage){

		String alertPhoneNumber = 
				pref.getString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, "");
		
		if(TextUtils.isEmpty(alertPhoneNumber)){
			if(isShowMessage){
				Toast.makeText(this,
						"开启失败，开启防盗保护必须设置安全号码，" +
						"请回到上一步进行设置", Toast.LENGTH_LONG).show();
			}
			return false;
		}
		
		return true;
	}

	/**
	 * 把临时temp保存的业务变量真正的保存起来
	 * 因为已经通过业务验证
	 */
	@Override
	protected void showNextConfig(){
		
		
		boolean isBindSIMCard = 
				pref.getBoolean(ConfigInfo.TEMP_IS_BIND_SIM_CARD_KEY,false);
		String simCardSerialNumber = 
				pref.getString(ConfigInfo.TEMP_SIM_CARD_SERIAL_NUMBER_KEY,"");
		String alertPhoneNumber = 
				pref.getString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, "");
		boolean isAntiThiefProtectOpen = 
				pref.getBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_INIT_CONFIG_KEY, true);
		
		editor.putBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, isBindSIMCard);
		editor.putString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY, simCardSerialNumber);
		editor.putString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, alertPhoneNumber);
		editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, isAntiThiefProtectOpen);
		
		editor.commit();
		
		finish();
		
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigFourthActivity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
}
