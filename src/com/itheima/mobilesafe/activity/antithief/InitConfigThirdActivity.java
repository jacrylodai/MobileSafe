package com.itheima.mobilesafe.activity.antithief;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
import com.itheima.mobilesafe.domain.AntiThiefInitConfigParams;
import com.itheima.mobilesafe.domain.ContactInfo;

public class InitConfigThirdActivity extends BaseInitConfigActivity{
	
	private static final String TAG = "InitConfigThirdActivity";

	private static final int REQUEST_CODE_CHOOSE_CONTACT = 1;
	
	private EditText etAlertPhoneNumber;
	
	private AntiThiefInitConfigParams antiThiefParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_third);
		
		antiThiefParams = (AntiThiefInitConfigParams) getIntent().getSerializableExtra(
				InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS);
		
		String alertPhoneNumber = antiThiefParams.getAlertPhoneNumber();
		
		etAlertPhoneNumber = (EditText) findViewById(R.id.et_alert_phone_number);
		if(!TextUtils.isEmpty(alertPhoneNumber)){
			etAlertPhoneNumber.setText(alertPhoneNumber);
		}
		etAlertPhoneNumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String phoneNumber = s.toString();
				phoneNumber = phoneNumber.replaceAll("\\-", "").replaceAll(" ", "");
				antiThiefParams.setAlertPhoneNumber(phoneNumber);
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

		Intent intent = new Intent(this,InitConfigFourthActivity.class);

		intent.putExtra(InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigSecondActivity.class);

		intent.putExtra(InitConfigFourthActivity.EXTRA_ANTI_THIEF_PARAMS, antiThiefParams);
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
