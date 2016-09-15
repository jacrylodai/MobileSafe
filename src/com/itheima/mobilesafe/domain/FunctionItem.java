package com.itheima.mobilesafe.domain;

/**
 * 主界面-功能选项
 * @author jacrylodai
 *
 */
public class FunctionItem {
	
	/**
	 * 手机防盗
	 */
	public static final int ID_ANTI_THIEF = 1;

	/**
	 * 通讯卫士
	 */
	public static final int ID_CONTACTS_GUARD = 2;

	/**
	 * 软件管理
	 */
	public static final int ID_SOFTWARE_MANAGE = 3;

	/**
	 * 进程管理
	 */
	public static final int ID_PROCESS_MANAGE = 4;

	/**
	 * 流量统计
	 */
	public static final int ID_INTERNET_TRAFFIC_MANAGE = 5;

	/**
	 * 手机杀毒
	 */
	public static final int ID_ANTI_VIRUS = 6;

	/**
	 * 缓存清理
	 */
	public static final int ID_TEMP_FILES_DELETE = 7;

	/**
	 * 高级工具
	 */
	public static final int ID_OTHER_FUNCTION = 8;

	/**
	 * 设置中心
	 */
	public static final int ID_CONFIG = 9;
	
	//功能id
	private int functionId;

	//功能名称
	private String name;

	//功能图标id
	private int iconId;

	public FunctionItem(int functionId, String name, int iconId) {
		super();
		this.functionId = functionId;
		this.name = name;
		this.iconId = iconId;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	
}
