package com.itheima.mobilesafe.activity.antithief;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ContactInfo;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class InitConfigThirdActivity extends BaseInitConfigActivity{
	
	private static final String TAG = "InitConfigThirdActivity";

	private static final int REQUEST_CODE_CHOOSE_CONTACT = 1;
	
	private SharedPreferences pref;
	
	private EditText etAlertPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_third);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);
		
		String alertPhoneNumber = pref.getString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, "");
		
		etAlertPhoneNumber = (EditText) findViewById(R.id.et_alert_phone_number);
		if(!TextUtils.isEmpty(alertPhoneNumber)){
			etAlertPhoneNumber.setText(alertPhoneNumber);
		}
		etAlertPhoneNumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String phoneNumber = s.toString();
				phoneNumber = phoneNumber.replaceAll("\\-", "").replaceAll(" ", "");
				
				SharedPreferences.Editor editor = pref.edit();
				editor.putString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, phoneNumber);
				editor.commit();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		Button buttonChooseContact = 
				(Button)findViewById(R.id.button_choose_contact);
		buttonChooseContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(InitConfigThirdActivity.this,ContactsLoadActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CHOOSE_CONTACT);
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

	@Override
	protected void showNextConfig(){

		//业务验证
		//如果绑定SIM卡，必须设置安全号码
		boolean isBindSIMCard = pref.getBoolean(ConfigInfo.TEMP_IS_BIND_SIM_CARD_KEY, false);
		String alertPhoneNumber = pref.getString(ConfigInfo.TEMP_ALERT_PHONE_NUMBER_KEY, "");
		if(isBindSIMCard == true && TextUtils.isEmpty(alertPhoneNumber)){
			Toast.makeText(this, "你已经绑定SIM卡，需要设置安全号码", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent intent = new Intent(this,InitConfigFourthActivity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigSecondActivity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case REQUEST_CODE_CHOOSE_CONTACT:
			
			if(resultCode == Activity.RESULT_OK){
				ContactInfo contactInfo = 
						(ContactInfo) data.getSerializableExtra(
								ContactsLoadActivity.EXTRA_CONTACT_INFO);
				Log.d(TAG, "get contact:"+contactInfo.getDisplayName());
				
				List<String> phoneNumberList = contactInfo.getPhoneNumberList();
				if(phoneNumberList.size() == 0){
					Toast.makeText(InitConfigThirdActivity.this
							, "你选择的联系人没有手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String phoneNumber = phoneNumberList.get(0);
				phoneNumber = phoneNumber.replaceAll("\\-", "").replaceAll(" ", "");
				etAlertPhoneNumber.setText(phoneNumber);
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
}
