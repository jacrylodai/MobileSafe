package com.itheima.mobilesafe.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.activity.antithief.InitConfigFirstActivity;
import com.itheima.mobilesafe.activity.antithief.LostFindActivity;
import com.itheima.mobilesafe.adapter.FunctionItemAdapter;
import com.itheima.mobilesafe.domain.ConfigInfo;
import com.itheima.mobilesafe.domain.FunctionItem;
import com.itheima.mobilesafe.utils.encrypt.ByteUtils;
import com.itheima.mobilesafe.utils.encrypt.EncryptUtils;

/**
 * 主界面
 * @author jacrylodai
 *
 */
public class HomeActivity extends ActionBarActivity {
	
	private static final String TAG = "HomeActivity";
	
	private GridView gvHomeFunction;
	
	//功能选项列表
	private List<FunctionItem> functionItemList;
	
	private SharedPreferences pref;
	
	private AlertDialog setProtectPasswordDialog;
	
	private AlertDialog checkProtectPasswordDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, Activity.MODE_PRIVATE);
		
		initFunctionItemList();
		gvHomeFunction = (GridView) findViewById(R.id.gv_home_function);
		gvHomeFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FunctionItem functionItem = functionItemList.get(position);
				Log.d(TAG, functionItem.getName());
				
				int functionId = functionItem.getFunctionId();
				switch (functionId) {
				
				case FunctionItem.ID_ANTI_THIEF:
					checkIsAntiTheifProtectPasswordSet();
					break;
				
				case FunctionItem.ID_CONFIG:
					Intent intent = new Intent(HomeActivity.this,ConfigActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
		
		FunctionItemAdapter adapter = new FunctionItemAdapter(
				this, R.layout.list_item_function_item, functionItemList);
		gvHomeFunction.setAdapter(adapter);
		
	}

	private void initFunctionItemList() {

		functionItemList = new ArrayList<FunctionItem>();
		FunctionItem functionItem;
		
		functionItem = new FunctionItem(FunctionItem.ID_ANTI_THIEF, "手机防盗", R.drawable.anti_theif);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_CONTACTS_GUARD, "通讯卫士", R.drawable.contacts_guard);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_SOFTWARE_MANAGE, "软件管理", R.drawable.software_manage);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_PROCESS_MANAGE, "进程管理", R.drawable.process_manage);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_INTERNET_TRAFFIC_MANAGE, "流量统计", R.drawable.internet_traffic_manage);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_ANTI_VIRUS, "手机杀毒", R.drawable.anti_virus);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_TEMP_FILES_DELETE, "缓存清理", R.drawable.temp_files_delete);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_OTHER_FUNCTION, "高级工具", R.drawable.other_function);
		functionItemList.add(functionItem);

		functionItem = new FunctionItem(FunctionItem.ID_CONFIG, "设置中心", R.drawable.config);
		functionItemList.add(functionItem);
		
	}
	
	/**
	 * 检查手机防盗保护密码是否设置
	 */
	private void checkIsAntiTheifProtectPasswordSet(){
		
		boolean isProtectPasswordSet = 
				ConfigInfo.getIsAntiThiefProtectPasswordSetKey(pref);
		if(isProtectPasswordSet){
			showCheckProtectPasswordDialog();
		}else{
			showSetProtectPasswordDialog();
		}
	}

	private void showCheckProtectPasswordDialog() {

		View view = LayoutInflater.from(this).inflate(R.layout.dialog_check_protect_password, null);
		
		final EditText etProtectPassword = (EditText) view.findViewById(R.id.et_protect_password);
		
		Button buttonCommit = (Button) view.findViewById(R.id.button_commit);
		Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
		
		buttonCommit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String protectPassword = etProtectPassword.getText().toString();
				
				if(TextUtils.isEmpty(protectPassword)){
					Toast.makeText(HomeActivity.this, "保护密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!Pattern.matches("\\w{6,30}", protectPassword)){
					Toast.makeText(HomeActivity.this, "保护密码只能为字母或数字，并且至少6位"
							, Toast.LENGTH_SHORT).show();
					return;
				}

				String protectPasswordEnc = 
						ByteUtils.byteArrayToHexString(EncryptUtils.encryptByMD5SHA(
								protectPassword.getBytes()));
				
				String realProtectPasswordEnc = 
						pref.getString(ConfigInfo.ANTI_THIEF_PROTECT_PASSWORD_KEY, "");
				
				if(realProtectPasswordEnc.equals(protectPasswordEnc)){
					
					Log.d(TAG, "你输入的保护密码正确");
					
					startAntiThief();
					checkProtectPasswordDialog.dismiss();
				}else{
					Toast.makeText(HomeActivity.this, "你输入的保护密码错误，请重新输入"
							, Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				checkProtectPasswordDialog.dismiss();
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		builder.setView(view);
		checkProtectPasswordDialog = builder.create();
		checkProtectPasswordDialog.show();		
	}

	private void showSetProtectPasswordDialog() {
		
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_set_protect_password, null);
		
		final EditText etProtectPassword = (EditText) view.findViewById(R.id.et_protect_password);
		final EditText etConfirmProtectPassword = (EditText) view.findViewById(
				R.id.et_confirm_protect_password);
		
		Button buttonCommit = (Button) view.findViewById(R.id.button_commit);
		Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
		
		buttonCommit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String protectPassword = etProtectPassword.getText().toString();
				String confirmProtectPassword = etConfirmProtectPassword.getText().toString();
				
				if(TextUtils.isEmpty(protectPassword)){
					Toast.makeText(HomeActivity.this, "保护密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!Pattern.matches("\\w{6,30}", protectPassword)){
					Toast.makeText(HomeActivity.this, "保护密码只能为字母或数字，并且至少6位"
							, Toast.LENGTH_SHORT).show();
					return;
				}

				if(TextUtils.isEmpty(confirmProtectPassword)){
					Toast.makeText(HomeActivity.this, "确认保护密码不能为空"
							, Toast.LENGTH_SHORT).show();
					return;
				}

				
				if(!Pattern.matches("\\w{6,30}", confirmProtectPassword)){
					Toast.makeText(HomeActivity.this, "确认保护密码只能为字母或数字，并且至少6位"
							, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!protectPassword.equals(confirmProtectPassword)){
					Toast.makeText(HomeActivity.this, "你2次输入的保护密码不一致，请重新输入"
							, Toast.LENGTH_SHORT).show();
					return;
				}else{
					
					String protectPasswordEnc = 
							ByteUtils.byteArrayToHexString(
									EncryptUtils.encryptByMD5SHA(protectPassword.getBytes()));
					
					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean(ConfigInfo.IS_ANTI_THIEF_PROTECT_PASSWORD_SET_KEY, true);
					editor.putString(ConfigInfo.ANTI_THIEF_PROTECT_PASSWORD_KEY, protectPasswordEnc);
					editor.commit();

					Toast.makeText(HomeActivity.this, "你已经成功设置手机防盗保护密码"
							, Toast.LENGTH_SHORT).show();
					
					startAntiThief();
					
					setProtectPasswordDialog.dismiss();
				}				
								
			}
		});
		
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				setProtectPasswordDialog.dismiss();
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		builder.setView(view);
		setProtectPasswordDialog = builder.create();
		setProtectPasswordDialog.show();
	}
	
	/**
	 * 启动手机防盗功能
	 */
	private void startAntiThief(){
		
		boolean isInitConfig = 
				ConfigInfo.getIsAntiThiefInitConfig(pref);
		if(isInitConfig){
			Intent intent = new Intent(this,LostFindActivity.class);
			startActivity(intent);
		}else{
			Intent intent = new Intent(this,InitConfigFirstActivity.class);
			startActivity(intent);
		}
	}
	
}
