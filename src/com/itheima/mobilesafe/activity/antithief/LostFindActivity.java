package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class LostFindActivity extends ActionBarActivity {
	
	private SharedPreferences pref;
	
	private TextView tvBindSIMCardState;
	
	private TextView tvAlertPhoneNumber;
	
	private TextView tvAntiThiefProtectState;

	private Boolean isBindSIMCard;
	
	private String simCardSerialNumber;
	
	private String alertPhoneNumber;
	
	private Boolean isAntiThiefProtectOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		
		tvBindSIMCardState = (TextView) findViewById(R.id.tv_bind_sim_card_state);
		tvAlertPhoneNumber = (TextView) findViewById(R.id.tv_alert_phone_number);				
		tvAntiThiefProtectState = (TextView) findViewById(R.id.tv_anti_thief_protect_state);
		
		TextView tvLostFindReconfig = (TextView) findViewById(R.id.tv_lost_find_reconfig);
		tvLostFindReconfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				reconfigAntiThiefInitConfig();
			}
		});
		
		readConfigFromSharedPref();
		
		updateUI();
		
		boolean isAntiThiefInitConfig = 
				pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_INIT_CONFIG_KEY, false);
		//û�н��г�ʼ�����ã����г�ʼ������
		if(isAntiThiefInitConfig == false){
			Intent intent = new Intent(this,InitConfigFirstActivity.class);
			startActivity(intent);
		}
	}
	
	private void reconfigAntiThiefInitConfig(){

		//��������ҵ��������浽��ʱtemp�������Ϊ�û����õ�;�п��ܻ��Ƴ�������ж�����
		//ֻ�н��������������̲�ͨ��ҵ����֤��������Ч��ֵ
		boolean isBindSIMCard = 
				pref.getBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY,false);
		String simCardSerialNumber = 
				pref.getString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY,"");
		String alertPhoneNumber = 
				pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		boolean isAntiThiefProtectOpen = 
				pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putBoolean(ConfigInfo.TEMP_IS_BIND_SIM_CARD_KEY, isBindSIMCard);
		editor.putString(ConfigInfo.TEMP_SIM_CARD_SERIAL_NUMBER_KEY, simCardSerialNumber);
		editor.putString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, alertPhoneNumber);
		editor.putBoolean(ConfigInfo.TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY, isAntiThiefProtectOpen);
		
		editor.commit();
		
		Intent intent = new Intent(this,InitConfigFirstActivity.class);
		startActivity(intent);
	}
	
	private void readConfigFromSharedPref(){
		
		isBindSIMCard = pref.getBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
		simCardSerialNumber = pref.getString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY, "");
		alertPhoneNumber = pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		isAntiThiefProtectOpen = pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
	}
	
	private void updateUI(){
		
		if(isBindSIMCard){
			tvBindSIMCardState.setText("�Ѱ�");
		}else{
			tvBindSIMCardState.setText("δ��");
		}

		if(TextUtils.isEmpty(alertPhoneNumber)){
			tvAlertPhoneNumber.setText("û�����ð�ȫ����");
		}else{
			tvAlertPhoneNumber.setText(alertPhoneNumber);
		}
		
		if(isAntiThiefProtectOpen){
			tvAntiThiefProtectState.setText("�ѿ���");
		}else{
			tvAntiThiefProtectState.setText("δ����");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		readConfigFromSharedPref();
		updateUI();
	}
	
}
