package com.itheima.mobilesafe.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactInfo implements Serializable{
	
	private long contactId;
	
	private String displayName;
	
	private List<String> phoneNumberList;
	
	public ContactInfo(){
		phoneNumberList = new ArrayList<String>();
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getPhoneNumberList() {
		return phoneNumberList;
	}
	
	public void addPhoneNumber(String phoneNumber){
		phoneNumberList.add(phoneNumber);
	}
	
	public String getAllPhoneNumber(){
		
		StringBuffer sb = new StringBuffer();
		for(String phoneNumber:phoneNumberList){
			if(sb.length() == 0){
				sb.append(phoneNumber);
			}else{
				sb.append(" , "+phoneNumber);
			}
		}
		return sb.toString();
	}
	
}
