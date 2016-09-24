package com.itheima.mobilesafe.activity.antithief;

import java.util.List;

import android.app.Activity;
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
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		String alertPhoneNumber = pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		
		etAlertPhoneNumber = (EditText) findViewById(R.id.et_alert_phone_number);
		if(!TextUtils.isEmpty(alertPhoneNumber)){
			etAlertPhoneNumber.setText(alertPhoneNumber);
		}
		
		Button buttonChooseContact = 
				(Button)findViewById(R.id.button_choose_contact);
		buttonChooseContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(InitConfigThirdActivity.this,ContactsLoadActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CHOOSE_CONTACT);
			}
		});
	}

	@Override
	protected Class<?> getNextConfigActivity() {
		return InitConfigFourthActivity.class;
	}

	@Override
	protected Class<?> getPreviousConfigActivity() {
		return InitConfigSecondActivity.class;
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

	protected void showNextConfig(){
		
		String phoneNumber = etAlertPhoneNumber.getText().toString();
		phoneNumber = phoneNumber.replaceAll("\\-", "").replaceAll(" ", "");
		
		if(TextUtils.isEmpty(phoneNumber)){
			Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}else{
			saveAlertPhoneNumber(phoneNumber);
		}

		Intent intent = new Intent(this,getNextConfigActivity());
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}
	
	private void saveAlertPhoneNumber(String phoneNumber){
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, phoneNumber);
		editor.commit();
	}
	
}
