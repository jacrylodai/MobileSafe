package com.itheima.mobilesafe.activity.antithief;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.log.LogUtil;

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
				
				LogUtil.d(TAG, "e1-rawX:"+e1.getRawX()+"  rawY:"+e1.getRawY());
				LogUtil.d(TAG, "e2-rawX:"+e2.getRawX()+"  rawY:"+e2.getRawY());
				
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
	
	protected abstract void showNextConfig();
	
	protected abstract void showPreviousConfig();
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
}
