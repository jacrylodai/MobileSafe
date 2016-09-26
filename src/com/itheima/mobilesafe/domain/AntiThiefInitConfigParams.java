package com.itheima.mobilesafe.domain;

import java.io.Serializable;

/**
 * 防盗保护设置参数类
 * @author jacrylodai
 *
 */
public class AntiThiefInitConfigParams implements Serializable{

	private Boolean isBindSIMCard;
	
	private String simCardSerialNumber;
	
	private String alertPhoneNumber;
	
	private Boolean isAntiThiefProtectOpen;
	
	public AntiThiefInitConfigParams(){
		
		isBindSIMCard = false;
		simCardSerialNumber = "";
		alertPhoneNumber = "";
		isAntiThiefProtectOpen = false;
	}

	public Boolean getIsBindSIMCard() {
		return isBindSIMCard;
	}

	public void setIsBindSIMCard(Boolean isBindSIMCard) {
		this.isBindSIMCard = isBindSIMCard;
	}

	public String getSimCardSerialNumber() {
		return simCardSerialNumber;
	}

	public void setSimCardSerialNumber(String simCardSerialNumber) {
		this.simCardSerialNumber = simCardSerialNumber;
	}

	public String getAlertPhoneNumber() {
		return alertPhoneNumber;
	}

	public void setAlertPhoneNumber(String alertPhoneNumber) {
		this.alertPhoneNumber = alertPhoneNumber;
	}

	public Boolean getIsAntiThiefProtectOpen() {
		return isAntiThiefProtectOpen;
	}

	public void setIsAntiThiefProtectOpen(Boolean isAntiThiefProtectOpen) {
		this.isAntiThiefProtectOpen = isAntiThiefProtectOpen;
	}
	
}
