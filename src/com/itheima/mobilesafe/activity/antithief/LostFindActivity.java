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
	
	private TextView tvAlertPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_find);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		
		tvAlertPhoneNumber = (TextView) findViewById(R.id.tv_alert_phone_number);
		
		String alertPhoneNumber = pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		if(TextUtils.isEmpty(alertPhoneNumber)){
			tvAlertPhoneNumber.setText("没有设置安全号码");
		}else{
			tvAlertPhoneNumber.setText(alertPhoneNumber);
		}
	}
	
	public void reconfig(View view){
		
		Intent intent = new Intent(this,InitConfigFirstActivity.class);
		startActivity(intent);
		finish();
	}
	
}
