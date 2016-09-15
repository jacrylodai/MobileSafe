package com.itheima.mobilesafe.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.FunctionItem;

/**
 * 功能选项九宫格适配器
 * @author jacrylodai
 *
 */
public class FunctionItemAdapter extends ArrayAdapter<FunctionItem>{
	
	private int resourceId;

	public FunctionItemAdapter(Context context, int resource,
			List<FunctionItem> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		FunctionItem functionItem = getItem(position);
		
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
		}
		ImageView ivFunctionItemIcon = 
				(ImageView) convertView.findViewById(R.id.iv_function_item_icon);
		ivFunctionItemIcon.setImageResource(functionItem.getIconId());
		
		TextView tvFunctionItemName = 
				(TextView) convertView.findViewById(R.id.tv_function_item_name);
		tvFunctionItemName.setText(functionItem.getName());
		
		return convertView;
	}

}
