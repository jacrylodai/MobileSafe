package com.itheima.mobilesafe.activity.antithief;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.itheima.mobilesafe.R;

public abstract class BaseInitConfigActivity extends ActionBarActivity{

	private static final String TAG = "BaseInitConfigActivity";
	
	private GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				
				Log.d(TAG, "e1-rawX:"+e1.getRawX()+"  rawY:"+e1.getRawY());
				Log.d(TAG, "e2-rawX:"+e2.getRawX()+"  rawY:"+e2.getRawY());
				
				if(Math.abs(e2.getRawY() - e1.getRawY()) >300){
					Toast.makeText(BaseInitConfigActivity.this, "纵向滑动幅度过大"
							, Toast.LENGTH_SHORT).show();
					return true;
				}
				
				if(e2.getRawX() - e1.getRawX() >200){
					showPreviousConfig();
					return true;
				}
				
				if(e2.getRawX() - e1.getRawX() < -200){
					showNextConfig();
					return true;
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
		});
	}
	
	public void nextConfig(View view){
		
		showNextConfig();
	}
	
	protected Class<?> getNextConfigActivity(){
		return null;
	}
	
	protected void showNextConfig(){

		Intent intent = new Intent(this,getNextConfigActivity());
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.next_enter_anim, R.anim.next_exit_anim);
	}
	
	public void previousConfig(View view){
		
		showPreviousConfig();
	}

	protected Class<?> getPreviousConfigActivity(){
		return null;
	}
	
	protected void showPreviousConfig(){
		
		Intent intent = new Intent(this,getPreviousConfigActivity());
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.previous_enter_anim, R.anim.previous_exit_anim);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
}
