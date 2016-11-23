package com.itheima.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.itheima.mobilesafe.utils.log.LogUtil;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class BootCompleteReceiver extends BroadcastReceiver {

	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		LogUtil.d(TAG, "launch BootCompleteReceiver");
		
		SharedPreferences pref = context.getSharedPreferences(
				ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);
		
		boolean isAntiThiefProtectOpen = pref.getBoolean(
				ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		
		if(isAntiThiefProtectOpen){
			
			boolean isBindSIMCard = pref.getBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
			if(isBindSIMCard){
				String SIMCardNum = pref.getString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY, "");
				String alertPhoneNumber = pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
				
				TelephonyManager tm = 
						(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				//�޸ĵ�ǰSIM�����ڲ���
				String currSIMCardNum = tm.getSimSerialNumber()+"111";
				
				if(TextUtils.isEmpty(currSIMCardNum)){
					LogUtil.d(TAG, "��ǰ�ֻ���û��SIM��");
					return;
				}
				
				if(SIMCardNum.equals(currSIMCardNum)){
					LogUtil.d(TAG, "SIM��û�б��");
				}else{
					LogUtil.d(TAG, "Σ�գ�SIM���������");
					
					String message = 
							"Warning,the SIM card in your cellphone has changed.";
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(
							alertPhoneNumber, null, message
							, null,null);
				}
			}
		}
	}

}
