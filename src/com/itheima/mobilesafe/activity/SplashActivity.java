package com.itheima.mobilesafe.activity;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ServerAPKInfo;
import com.itheima.mobilesafe.utils.http.HttpCallbackListener;
import com.itheima.mobilesafe.utils.http.HttpConnectionUtil;
import com.itheima.mobilesafe.utils.log.LogUtil;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

/**
 * 初始化界面，显示程序logo，联网取得最新版本信息，提示升级，下载最新版本并升级
 * @author jacrylodai
 *
 */
public class SplashActivity extends ActionBarActivity {
	
	private static final String TAG = "SplashActivity";

	protected static final int NETWORK_ERROR = 1;

	protected static final int JSON_ERROR = 2;

	protected static final int ENTER_HOME = 3;

	protected static final int UPDATE_DIALOG = 4;
	
	private SharedPreferences pref;
	
	private TextView tvSplashVersion;
	
	private ProgressBar pbSplashLoading;
	
	private ProgressBar pbSplashDownload;
	
	private ServerAPKInfo serverAPKInfo;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
			case NETWORK_ERROR:

				LogUtil.d(TAG, "网络异常");		
				Toast.makeText(getApplicationContext()
						, "网络异常", Toast.LENGTH_SHORT).show();
				enterHome();
				break;

			case JSON_ERROR:

				LogUtil.d(TAG, "JSON解析异常");		
				Toast.makeText(SplashActivity.this
						, "JSON解析异常", Toast.LENGTH_SHORT).show();
				enterHome();
				break;

			case ENTER_HOME:
				
				LogUtil.d(TAG, "进入主界面");				
				enterHome();
				break;

			case UPDATE_DIALOG:

				LogUtil.d(TAG, "弹出升级对话框");
				showUpdateDialog();
				break;
				
			default:
				break;
			}
		};
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        
        tvSplashVersion = (TextView) findViewById(R.id.tv_splash_version);
        tvSplashVersion.setText("版本号：" + getVersionName());
        
        pbSplashLoading = (ProgressBar) findViewById(R.id.pb_splash_loading);
        
        pbSplashDownload = (ProgressBar) findViewById(R.id.pb_splash_download);
		pbSplashDownload.setProgress(0);
        pbSplashDownload.setVisibility(View.INVISIBLE);
        
        //检测是否提示用户程序升级
        if(ConfigInfo.getIsShowUpdateKey(pref)){
        	checkVersion();
        }else{
        	handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					enterHome();
				}
			}, 2000);
        }
        
        AlphaAnimation alphA = new AlphaAnimation(0.2f, 1.0f);
        alphA.setDuration(1000);
        
        View layoutSplashRoot = findViewById(R.id.layout_splash_root);
        layoutSplashRoot.setAnimation(alphA);
        
    }
    
    /**
     * 弹出升级对话框
     */
    private void showUpdateDialog() {

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("发现新版本，是否升级");
    	builder.setMessage(serverAPKInfo.getDescription());
    	builder.setCancelable(true);
    	builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				updateCurrentProgram();
			}
		});
    	
    	builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				enterHome();
			}
		});
    	
    	//当用户取消时进入主页面
    	builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {

				dialog.dismiss();
				enterHome();
			}
		});
    	
    	builder.show();
	}

    /**
     * 升级当前应用
     */
	private void updateCurrentProgram() {

		//取得外置SD卡的状态
		String externalStorageState = Environment.getExternalStorageState();
		if(externalStorageState.equals(Environment.MEDIA_MOUNTED)){
			
			String externalStoragePath = 
					Environment.getExternalStorageDirectory().getAbsolutePath();
			String newApkPath = externalStoragePath + "/download/SafeGuard.apk";
			File newApkFile = new File(newApkPath);
			newApkFile.delete();
			
			pbSplashDownload.setVisibility(View.VISIBLE);
			
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.download(
					serverAPKInfo.getApkUrl()
					, newApkPath
					, new AjaxCallBack<File>() {

						@Override
						public void onLoading(long count, long current) {

							super.onLoading(count, current);
							int progress = (int) (current*100/count);
							pbSplashDownload.setProgress(progress);
						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							
							super.onFailure(t, errorNo, strMsg);
							LogUtil.e(TAG, "", t);
							Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
							
							enterHome();
						}

						@Override
						public void onSuccess(File t) {
							
							super.onSuccess(t);
							
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(t)
									, "application/vnd.android.package-archive");     
							startActivity(intent);
							SplashActivity.this.finish();
						}
						
			});
			
		}else{
			//sd存储卡不可用
			Toast.makeText(this, "SD卡无法使用，无法升级", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 进入主界面
	 */
	private void enterHome() {

    	Intent intent = new Intent(this,HomeActivity.class);
    	startActivity(intent);
    	finish();
	}

	/**
	 * 取得服务器的版本名称，并和服务器的版本名称进行比对
	 */
	private void checkVersion() {

		final long startTime = System.currentTimeMillis();
		
    	String infoUrl = getString(R.string.serverApkInfoUrl);
    	HttpConnectionUtil.connect(infoUrl,"UTF-8",2000,3000, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {

				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime - startTime;
				//展示2秒钟的logo
				if(elapsedTime<2000){
					try {
						Thread.sleep(2000-elapsedTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				LogUtil.d(TAG, response);
				try {
					serverAPKInfo = ServerAPKInfo.buildFromJson(response);
				} catch (JSONException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(JSON_ERROR);
					return;
				}
				
				String currVersionName = getVersionName();
				if(currVersionName.equals(serverAPKInfo.getVersionName())){
					
					handler.sendEmptyMessage(ENTER_HOME);
				}else{
					
					handler.sendEmptyMessage(UPDATE_DIALOG);
				}
			}
			
			@Override
			public void onError(Exception ex) {
				
				LogUtil.e(TAG, "", ex);
				
				handler.sendEmptyMessage(NETWORK_ERROR);
			}
		});
	}

	/**
	 * 取得版本名称
	 * @return
	 */
	public String getVersionName(){
    	
    	PackageManager pm = getPackageManager();
    	try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
