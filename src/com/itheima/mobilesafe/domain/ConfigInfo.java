package com.itheima.mobilesafe.domain;

import android.content.SharedPreferences;

public class ConfigInfo {

	/**
	 * �����ļ�����
	 */
	public static final String CONFIG_FILE_NAME = "config";
	
	/**
	 * �Ƿ���ʾ������������Key
	 */
	public static final String IS_SHOW_UPDATE_KEY = "isShowUpdateKey";

	public static boolean getIsShowUpdateKey(SharedPreferences pref){
		return pref.getBoolean(IS_SHOW_UPDATE_KEY, true);
	}
	
	/**
	 * �Ƿ������ֻ���������
	 */
	public static final String IS_ANTI_THIEF_PROTECT_PASSWORD_SET_KEY = 
			"isAntiThiefProtectPasswordSetKey";

	public static boolean getIsAntiThiefProtectPasswordSetKey(
			SharedPreferences pref){
		return pref.getBoolean(IS_ANTI_THIEF_PROTECT_PASSWORD_SET_KEY, false);
	}
	
	/**
	 * �ֻ���������
	 */
	public static final String ANTI_THIEF_PROTECT_PASSWORD_KEY = "antiThiefProtectPasswordKey";
	
	/**
	 * �ֻ������Ƿ���г�ʼ������
	 */
	public static final String IS_ANTI_THIEF_INIT_CONFIG_KEY = "isAntiThiefInitConfigKey";
	
	public static boolean getIsAntiThiefInitConfig(SharedPreferences pref){
		return pref.getBoolean(IS_ANTI_THIEF_INIT_CONFIG_KEY, false);
	}
	
}
