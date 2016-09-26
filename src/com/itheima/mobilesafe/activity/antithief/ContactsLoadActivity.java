package com.itheima.mobilesafe.activity.antithief;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.domain.ContactInfo;
import com.itheima.mobilesafe.utils.other.ContactsUtils;

public class ContactsLoadActivity extends ActionBarActivity {

	private static final String TAG = "ContactsLoadActivity";
	
	private List<ContactInfo> contactInfoList;
	
	private ListView lvContactInfoList;
	
	public static final String EXTRA_CONTACT_INFO = 
			"com.itheima.mobilesafe.extra_contact_info";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_load);
		
		readContacts();
		
		lvContactInfoList = (ListView) findViewById(R.id.lv_contact_info_list);
		
		ArrayAdapter<ContactInfo> adapter = 
				new ArrayAdapter<ContactInfo>(this
						, android.R.layout.simple_list_item_1, contactInfoList);
		
		lvContactInfoList.setAdapter(adapter);
		lvContactInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ContactInfo contactInfo = (ContactInfo) parent.getItemAtPosition(position);
				Log.d(TAG, "you click contact:"+contactInfo.getDisplayName());
				
				readContactInfoFromSystem(contactInfo);
				
				Intent data = new Intent();
				data.putExtra(EXTRA_CONTACT_INFO, contactInfo);
				ContactsLoadActivity.this.setResult(RESULT_OK, data);
				ContactsLoadActivity.this.finish();
			}
		});
	}
	
	/**
	 * 读取联系人电话号码及其他信息
	 * @param contactInfo
	 */
	private void readContactInfoFromSystem(ContactInfo contactInfo){
		
		Uri dataUri = Uri.parse(ContactsUtils.Data.CONTENT_URI);
		long contactId = contactInfo.getContactId();

		Cursor dataCursor = 
				getContentResolver().query(
						dataUri
						, ContactsUtils.Data.PROJECTION
						, ContactsUtils.Data.SELECTION
						, new String[]{ String.valueOf(contactId) }
						, null);
		
		if(dataCursor == null){
			Toast.makeText(this, "无法取得联系人详细信息", Toast.LENGTH_SHORT).show();
			return;
		}
		
		while(dataCursor.moveToNext()){
			String mimeType = 
					dataCursor.getString(
							dataCursor.getColumnIndex(ContactsUtils.Data.COL_MINE_TYPE));
			String data1 = 
					dataCursor.getString(
							dataCursor.getColumnIndex(ContactsUtils.Data.COL_DATA1));
			Log.d(TAG, mimeType+"--"+data1);
			if(mimeType.equals(ContactsUtils.MimeType.MIME_TYPE_PHONE)){
				contactInfo.addPhoneNumber(data1);
			}
				
		}
		dataCursor.close();
		
	}

	/**
	 * 仅读取手机中所有的联系人
	 */
	private void readContacts() {
		
		Uri rawContactsUri = Uri.parse(ContactsUtils.RawContact.CONTENT_URI);
		
		contactInfoList = new ArrayList<ContactInfo>();
		
		Cursor cursor = 
				getContentResolver().query(
						rawContactsUri
						, ContactsUtils.RawContact.PROJECTION
						, ContactsUtils.RawContact.SELECTION
						, null
						, ContactsUtils.RawContact.COL_SORT_KEY);
		
		if(cursor == null){
			Toast.makeText(this, "无法取得联系人", Toast.LENGTH_SHORT).show();
			return;
		}
		
		while(cursor.moveToNext()){
			
			ContactInfo contactInfo = new ContactInfo();
			
			long contactId = cursor.getLong(
					cursor.getColumnIndex(ContactsUtils.RawContact.COL_CONTACT_ID));
			String displayName = cursor.getString(
					cursor.getColumnIndex(ContactsUtils.RawContact.COL_DISPLAY_NAME));
			Log.d(TAG, "contactId:"+contactId+"  displayName:"+displayName);
			contactInfo.setContactId(contactId);
			contactInfo.setDisplayName(displayName);
			
			contactInfoList.add(contactInfo);
		}
		cursor.close();
		
	}
	
}
