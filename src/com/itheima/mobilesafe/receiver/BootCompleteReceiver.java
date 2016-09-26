package com.itheima.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

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
				
				TelephonyManager tm = 
						(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currSIMCardNum = tm.getSimSerialNumber();
				
				if(SIMCardNum.equals(currSIMCardNum)){
					LogUtil.d(TAG, "SIM卡没有变更");
				}else{
					LogUtil.d(TAG, "危险，SIM卡发生变更");
				}
			}
		}
	}

}
