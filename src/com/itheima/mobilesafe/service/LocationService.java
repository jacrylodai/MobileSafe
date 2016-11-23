package com.itheima.mobilesafe.service;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.itheima.mobilesafe.utils.log.LogUtil;
import com.itheima.mobilesafe.utils.other.ConfigInfo;

public class LocationService extends Service {

	private static final String TAG = "LocationService";
	
	private LocationManager locationManager = null;
	
	private SharedPreferences pref;
	
	private String alertPhoneNumber;
	
	private LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

			LogUtil.d(TAG, "onStatusChanged");
		}
		
		@Override
		public void onProviderEnabled(String provider) {

			LogUtil.d(TAG, "onProviderEnabled");
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			LogUtil.d(TAG, "onProviderDisabled");
		}
		
		@Override
		public void onLocationChanged(Location location) {
			LogUtil.d(TAG, "onLocationChanged");
			showLocation(location);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		pref = getSharedPreferences(ConfigInfo.CONFIG_FILE_NAME, Context.MODE_PRIVATE);

		alertPhoneNumber = 
				pref.getString(ConfigInfo.ALERT_PHONE_NUMBER_KEY, "");
		
		locationManager = 
				(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		List<String> providerList = locationManager.getProviders(criteria, true);
		
		LogUtil.i(TAG, providerList.toString());
		
		String provider = null;
		if(providerList.contains(LocationManager.GPS_PROVIDER)){
			provider = LocationManager.GPS_PROVIDER;
		}else
			if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
				provider = LocationManager.NETWORK_PROVIDER;
			}else
				if(providerList.size()>0){
					provider = providerList.get(0);				
				}else{
					LogUtil.i(TAG,"There is no avalible location provider");
					Toast.makeText(this, "没有可用的位置提供者", Toast.LENGTH_SHORT).show();
				}
		LogUtil.i(TAG, "You are using "+provider);
		
//		List<String> providerList = locationManager.getProviders(true);
//		LogUtil.i(TAG, providerList.toString());
//		
//		String provider = null;
//		if(providerList.contains(LocationManager.GPS_PROVIDER)){
//			provider = LocationManager.GPS_PROVIDER;
//		}else
//			if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
//				provider = LocationManager.NETWORK_PROVIDER;
//			}else{
//				LogUtil.i(TAG,"There is no avalible location provider");
//				Toast.makeText(this, "没有可用的位置提供者", Toast.LENGTH_SHORT).show();
//			}
//		LogUtil.i(TAG, "You are using "+provider);
		
		if(provider != null){
			Location location = locationManager.getLastKnownLocation(provider);
			showLocation(location);
			locationManager.requestLocationUpdates(provider, 3000, 5, locationListener);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(locationManager != null){
			locationManager.removeUpdates(locationListener);
		}
	}
	
	private void showLocation(Location location){

		if(location == null){
			Toast.makeText(this, "location is null", Toast.LENGTH_SHORT).show();
			LogUtil.i(TAG, "location is null");
			return;
		}
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		float accuracy = location.getAccuracy();
		double altitude = location.getAltitude();
		
		//经度
		String longitudeStr = "longitude:"+longitude;
		//纬度
		String latitudeStr = "latitude:"+latitude;
		
		StringBuffer sb = new StringBuffer();
		sb.append(longitudeStr);
		sb.append('\n');
		sb.append(latitudeStr);
//		sb.append('\n');
//		sb.append("精度:"+accuracy);
//		sb.append('\n');
//		sb.append("海拔:"+altitude);
		
		String msg = sb.toString();
		LogUtil.i(TAG, msg);
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(ConfigInfo.LOCATION_LONGITUDE_KEY, String.valueOf(longitude));
		editor.putString(ConfigInfo.LOCATION_LATITUDE_KEY, String.valueOf(latitude));
		editor.commit();
		
		if(!TextUtils.isEmpty(alertPhoneNumber)){

			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(
					alertPhoneNumber, null, msg, null, null);
		}
		
		stopSelf();
	}
}
