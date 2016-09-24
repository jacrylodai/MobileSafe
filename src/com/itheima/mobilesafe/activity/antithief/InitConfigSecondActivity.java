package com.itheima.mobilesafe.activity.antithief;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.ui.ConfigItemTrueFalseView;
import com.itheima.mobilesafe.utils.log.LogUtil;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class InitConfigSecondActivity extends BaseInitConfigActivity{
	
	private static final String TAG = "InitConfigSecondActivity";
	
	private SharedPreferences pref;
	
	private ConfigItemTrueFalseView ciInitConfigBindSIMCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_second);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		
		ciInitConfigBindSIMCard = 
				(ConfigItemTrueFalseView) findViewById(R.id.ci_init_config_bind_sim_card);
		
		boolean isBindSIMCard = pref.getBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
		ciInitConfigBindSIMCard.setConfigItemValue(isBindSIMCard);
		
		ciInitConfigBindSIMCard.setConfigItemValueCheckBoxListener(
				new CompoundButton.OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						SharedPreferences.Editor editor = pref.edit();
						if(isChecked){
							
							TelephonyManager tm = (TelephonyManager) getSystemService(
									Context.TELEPHONY_SERVICE);
							String simSerialNumber = tm.getSimSerialNumber();
							if(TextUtils.isEmpty(simSerialNumber)){
								
								Toast.makeText(InitConfigSecondActivity.this, 
										"绑定SIM卡失败，手机上没有SIM卡", Toast.LENGTH_SHORT).show();
								editor.putBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
								ciInitConfigBindSIMCard.setConfigItemValue(false);
							}else{
								
								LogUtil.d(TAG, "bind SIM card success,SIM card serial number:"
										+simSerialNumber);

								Toast.makeText(InitConfigSecondActivity.this, 
										"绑定SIM卡成功", Toast.LENGTH_SHORT).show();
								editor.putBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, true);
								editor.putString(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY,simSerialNumber);
							}
							
						}else{
							
							editor.remove(ConfigInfo.SIM_CARD_SERIAL_NUMBER_KEY);
							editor.putBoolean(ConfigInfo.IS_BIND_SIM_CARD_KEY, false);
						}
						editor.commit();
						
					}});
	}

	@Override
	protected Class<?> getNextConfigActivity() {
		return InitConfigThirdActivity.class;
	}

	@Override
	protected Class<?> getPreviousConfigActivity() {
		return InitConfigFirstActivity.class;
	}
	
}
