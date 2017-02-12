package com.itheima.mobilesafe.activity.antithief;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.receiver.MyDeviceAdminReceiver;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class LostFindActivity extends ActionBarActivity {
	
	private static final int REQUEST_CODE_RUN_CONFIG = 1;
	
	private SharedPreferences pref;
	
	private TextView tvBindSIMCardState;
	
	private TextView tvAlertPhoneNumber;
	
	private TextView tvAntiThiefProtectState;
	
	private TextView tvDeviceAdminState;

	private Boolean isBindSIMCard;
	
	private String simCardSerialNumber;
	
	private String alertPhoneNumber;
	
	private Boolean isAntiThiefProtectOpen;

	private DevicePolicyManager mDPM;
	
	private ComponentName mDeviceAdmin;
	
	private boolean mActiveAdmin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);

		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdmin = new ComponentName(this, MyDeviceAdminReceiver.class);
		
		tvBindSIMCardState = (TextView) findViewById(R.id.tv_bind_sim_card_state);
		tvAlertPhoneNumber = (TextView) findViewById(R.id.tv_alert_phone_number);				
		tvAntiThiefProtectState = (TextView) findViewById(R.id.tv_anti_thief_protect_state);
		tvDeviceAdminState = (TextView) findViewById(R.id.tv_device_admin_state);
		
		TextView tvLostFindReconfig = (TextView) findViewById(R.id.tv_lost_find_reconfig);
		tvLostFindReconfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				reconfigAntiThiefInitConfig();
			}
		});
		
		refreshConfig();
		
		updateUI();
		
		boolean isAntiThiefInitConfig = 
				pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_INIT_CONFIG_KEY, false);
		//û�н��г�ʼ�����ã����г�ʼ������
		if(isAntiThiefInitConfig == false){
			Intent intent = new Intent(this,InitConfigFirstActivity.class);
			startActivityForResult(intent, REQUEST_CODE_RUN_CONFIG);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case REQUEST_CODE_RUN_CONFIG:
			
			refreshConfig();
			updateUI();
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
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
		startActivityForResult(intent, REQUEST_CODE_RUN_CONFIG);
	}
	
	private void refreshConfig(){
		
		isBindSIMCard = pref.getBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
		simCardSerialNumber = pref.getString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY, "");
		alertPhoneNumber = pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		isAntiThiefProtectOpen = pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		
		mActiveAdmin = mDPM.isAdminActive(mDeviceAdmin);
	}
	
	private void updateUI(){
		
		if(isBindSIMCard){
			tvBindSIMCardState.setText("��");
		}else{
			tvBindSIMCardState.setText("δ��");
		}

		if(TextUtils.isEmpty(alertPhoneNumber)){
			tvAlertPhoneNumber.setText("û�����ð�ȫ����");
		}else{
			tvAlertPhoneNumber.setText(alertPhoneNumber);
		}
		
		if(mActiveAdmin){
			tvDeviceAdminState.setText("����");
		}else{
			tvDeviceAdminState.setText("�ر�");
			
		}
		
		if(isAntiThiefProtectOpen){
			tvAntiThiefProtectState.setText("����");
		}else{
			tvAntiThiefProtectState.setText("�ر�");
		}
	}
	
}
