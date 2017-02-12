package com.itheima.mobilesafe.activity.antithief;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.receiver.MyDeviceAdminReceiver;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class InitConfigFourthActivity extends BaseInitConfigActivity{
	
	private static final String TAG = InitConfigFourthActivity.class.getSimpleName();
	
	private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
	
	private CheckBox cbSetDeviceAdmin;
	
	private SharedPreferences pref;

	private DevicePolicyManager mDPM;
	
	private ComponentName mDeviceAdmin;
	
	private boolean mAdminActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_config_fourth);
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, MODE_PRIVATE);
		
		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdmin = new ComponentName(this, MyDeviceAdminReceiver.class);
		
		mAdminActive = isActiveAdmin();
				
		cbSetDeviceAdmin = (CheckBox) findViewById(R.id.cb_set_device_admin);

		cbSetDeviceAdmin.setChecked(mAdminActive);
		
		cbSetDeviceAdmin.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				Log.i(TAG, "onCheckedChanged");
				
				boolean value = isChecked;
                if (value) {
                    // Launch the activity to have the user enable our admin.
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            getString(R.string.add_admin_extra_app_text));
                    startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
                } else {
                    mDPM.removeActiveAdmin(mDeviceAdmin);
                    mAdminActive = false;
                }
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Log.i(TAG, "resultCode:"+resultCode);
		
		switch (requestCode) {
		case REQUEST_CODE_ENABLE_ADMIN:
			
			mAdminActive = isActiveAdmin();
			if(mAdminActive == false){
				cbSetDeviceAdmin.setChecked(false);
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
	private boolean isActiveAdmin() {
	    return mDPM.isAdminActive(mDeviceAdmin);
	}
	
	@Override
	protected void showNextConfig(){

		Intent intent = new Intent(this,InitConfigFifthActivity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}

	@Override
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,InitConfigThirdActivity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
}
