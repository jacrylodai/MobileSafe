package com.itheima.mobilesafe.domain;

/**
 * ������-����ѡ��
 * @author jacrylodai
 *
 */
public class FunctionItem {
	
	/**
	 * �ֻ�����
	 */
	public static final int ID_ANTI_THIEF = 1;

	/**
	 * ͨѶ��ʿ
	 */
	public static final int ID_CONTACTS_GUARD = 2;

	/**
	 * �������
	 */
	public static final int ID_SOFTWARE_MANAGE = 3;

	/**
	 * ���̹���
	 */
	public static final int ID_PROCESS_MANAGE = 4;

	/**
	 * ����ͳ��
	 */
	public static final int ID_INTERNET_TRAFFIC_MANAGE = 5;

	/**
	 * �ֻ�ɱ��
	 */
	public static final int ID_ANTI_VIRUS = 6;

	/**
	 * ��������
	 */
	public static final int ID_TEMP_FILES_DELETE = 7;

	/**
	 * �߼�����
	 */
	public static final int ID_OTHER_FUNCTION = 8;

	/**
	 * ��������
	 */
	public static final int ID_CONFIG = 9;
	
	//����id
	private int functionId;

	//��������
	private String name;

	//����ͼ��id
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
