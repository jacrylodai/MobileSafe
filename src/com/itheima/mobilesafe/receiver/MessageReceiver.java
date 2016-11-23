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
				//�Ƚϰ�ȫ����ͷ��Ͷ��ŵĺ����Ƿ�һ��
				if(alertPhoneNumber.equals(address)){
					LogUtil.d(TAG, "���ͺ���Ͱ�ȫ����һ��");
					
					if(content.equals("#*alarm*#")){
						LogUtil.d(TAG, "���ű�������");
						
						AudioPlayer audioPlayer = new AudioPlayer();
						audioPlayer.play(context, R.raw.ylzs,4);
						
						abortBroadcast();
					}else
						if(content.equals("#*location*#")){
							LogUtil.d(TAG, "��ʾλ��");
							
							Intent locationIntent = new Intent(context,LocationService.class);
							context.startService(locationIntent);
							
							abortBroadcast();
						}
				}
			}
		}
	}

}
