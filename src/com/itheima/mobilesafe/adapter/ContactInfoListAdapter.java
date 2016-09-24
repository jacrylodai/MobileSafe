package com.itheima.mobilesafe.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ContactInfo;

public class ContactInfoListAdapter extends ArrayAdapter<ContactInfo>{

	private int resourceId;
	
	public ContactInfoListAdapter(Context context, int resource,
			List<ContactInfo> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ContactInfo contactInfo = getItem(position);
		
		View view = convertView;
		if(view == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
		}
		
		TextView tvDisplayName = 
				(TextView)view.findViewById(R.id.tv_contact_info_display_name);
		TextView tvPhoneNumber = 
				(TextView)view.findViewById(R.id.tv_contact_info_phone_number);
		
		tvDisplayName.setText(contactInfo.getDisplayName());
		tvPhoneNumber.setText(contactInfo.getAllPhoneNumber());
		
		return view;
	}

}
