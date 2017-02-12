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
				//ҵ����֤ʧ�ܣ��޷������������������÷�����������״̬Ϊfalse
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
						//������������ʧ�ܣ����õ�ѡ��״̬Ϊfalse
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
	 * ҵ����֤
	 * ���������������������벢���ð�ȫ����
	 * ���û�п��������������Ƿ����ð�ȫ���붼û�й�ϵ
	 * @return
	 */
	private boolean validataIsOpenAntiThiefAvailable(boolean isShowMessage){

		String alertPhoneNumber = 
				pref.getString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, "");
		
		if(TextUtils.isEmpty(alertPhoneNumber)){
			if(isShowMessage){
				Toast.makeText(this,
						"����ʧ�ܣ��������������������ð�ȫ���룬" +
						"��ص���һ����������", Toast.LENGTH_LONG).show();
			}
			return false;
		}
		
		return true;
	}

	/**
	 * ����ʱtemp�����ҵ����������ı�������
	 * ��Ϊ�Ѿ�ͨ��ҵ����֤
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
