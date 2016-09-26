package com.itheima.mobilesafe.utils.other;

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
	
	/**
	 * �ֻ���������
	 */
	public static final String ANTI_THIEF_PROTECT_PASSWORD_KEY = "antiThiefProtectPasswordKey";
	
	/**
	 * �ֻ������Ƿ���й���ʼ������
	 */
	public static final String IS_ANTI_THIEF_INIT_CONFIG_KEY = "isAntiThiefInitConfigKey";
	
	/**
	 * �Ƿ��SIM��
	 */
	public static final String IS_BIND_SIM_CARD_KEY = "isBindSIMCardKey";
	
	/**
	 * �󶨵�SIM�����к�
	 */
	public static final String SIM_CARD_SERIAL_NUMBER_KEY = "SIMCardSerialNumberKey"; 
	
	/**
	 * ��ȫ���룬��������
	 */
	public static final String ALERT_PHONE_NUMBER_KEY = "alertPhoneNumberKey";
	
	/**
	 * �Ƿ�����������
	 */
	public static final String IS_ANTI_THIEF_PROTECT_OPEN_KEY = "isAntiThiefProtectOpenKey";
	
}
