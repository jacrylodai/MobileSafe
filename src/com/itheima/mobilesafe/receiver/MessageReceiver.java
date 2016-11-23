package com.itheima.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.service.LocationService;
import com.itheima.mobilesafe.utils.log.LogUtil;
import com.itheima.mobilesafe.utils.other.ConfigInfo;
import com.itheima.mobilesafe.utils.player.AudioPlayer;

public class MessageReceiver extends BroadcastReceiver{
	
	private static final String TAG = "MessageReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get("pdus");
		SmsMessage[] messageArr = new SmsMessage[pdus.length];
		for(int i=0;i<pdus.length;i++){
			messageArr[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
		}
		String address = messageArr[0].getOriginatingAddress();
		
		StringBuffer sb = new StringBuffer();
		for(SmsMessage message:messageArr){
			sb.append(message.getMessageBody());
		}
		
		String content = sb.toString();
		LogUtil.d(TAG, "address:"+address);
		LogUtil.d(TAG, "content:"+content);
		
		SharedPreferences pref = context.getSharedPreferences(
				ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);
		boolean isAntiThiefProtectOpen = 
				pref.getBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_OPEN_KEY, false);
		String alertPhoneNumber = 
				pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		
		if(isAntiThiefProtectOpen){
			if(!TextUtils.isEmpty(alertPhoneNumber)){
				//比较安全号码和发送短信的号码是否一致
				if(alertPhoneNumber.equals(address)){
					LogUtil.d(TAG, "发送号码和安全号码一致");
					
					if(content.equals("#*alarm*#")){
						LogUtil.d(TAG, "播放报警音乐");
						
						AudioPlayer audioPlayer = new AudioPlayer();
						audioPlayer.play(context, R.raw.ylzs,4);
						
						abortBroadcast();
					}else
						if(content.equals("#*location*#")){
							LogUtil.d(TAG, "显示位置");
							
							Intent locationIntent = new Intent(context,LocationService.class);
							context.startService(locationIntent);
							
							abortBroadcast();
						}
				}
			}
		}
	}

}
