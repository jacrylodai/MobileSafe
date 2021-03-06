package com.itheima.mobilesafe.utils.other;

import android.content.SharedPreferences;

public class ConfigInfo {

	/**
	 * 配置文件名称
	 */
	public static final String CONFIG_FILE_NAME = "config";
	
	/**
	 * 是否提示程序升级，的Key
	 */
	public static final String IS_SHOW_UPDATE_KEY = "isShowUpdateKey";

	public static boolean getIsShowUpdateKey(SharedPreferences pref){
		return pref.getBoolean(IS_SHOW_UPDATE_KEY, true);
	}
	
	/**
	 * 是否设置手机防盗密码
	 */
	public static final String IS_ANTI_THIEF_PROTECT_PASSWORD_SET_KEY = 
			"isAntiThiefProtectPasswordSetKey";
	
	/**
	 * 手机防盗密码
	 */
	public static final String ANTI_THIEF_PROTECT_PASSWORD_KEY = "antiThiefProtectPasswordKey";
	
	/**
	 * 手机防盗是否进行过初始化配置
	 */
	public static final String IS_ANTI_THIEF_INIT_CONFIG_KEY = "isAntiThiefInitConfigKey";
	
	/**
	 * 是否绑定SIM卡
	 */
	public static final String IS_BIND_SIM_CARD_KEY = "isBindSIMCardKey";

	/**
	 * Temp是否绑定SIM卡
	 */
	public static final String TEMP_IS_BIND_SIM_CARD_KEY = "tempIsBindSIMCardKey";
	
	/**
	 * 绑定的SIM卡序列号
	 */
	public static final String SIM_CARD_SERIAL_NUMBER_KEY = "SIMCardSerialNumberKey"; 

	/**
	 * Temp绑定的SIM卡序列号
	 */
	public static final String TEMP_SIM_CARD_SERIAL_NUMBER_KEY = "TempSIMCardSerialNumberKey"; 
	
	/**
	 * 安全号码，报警号码
	 */
	public static final String ALERT_PHONE_NUMBER_KEY = "alertPhoneNumberKey";

	/**
	 * TEMP安全号码，报警号码
	 */
	public static final String TEMP_ALERT_PHONE_NUMBER_KEY = "tempAlertPhoneNumberKey";
	
	/**
	 * 是否开启防盗保护
	 */
	public static final String IS_ANTI_THIEF_PROTECT_OPEN_KEY = "isAntiThiefProtectOpenKey";

	/**
	 * TEMP是否开启防盗保护
	 */
	public static final String TEMP_IS_ANTI_THIEF_PROTECT_OPEN_KEY = "tempIsAntiThiefProtectOpenKey";
	
	/**
	 * 经度
	 */
	public static final String LOCATION_LONGITUDE_KEY = "locationLongitudeKey";

	/**
	 * 纬度
	 */
	public static final String LOCATION_LATITUDE_KEY = "locationLatitudeKey";
	
}
