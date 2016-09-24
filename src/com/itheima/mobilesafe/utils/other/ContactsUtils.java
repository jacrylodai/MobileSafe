package com.itheima.mobilesafe.utils.other;

public class ContactsUtils {
	
	public static class RawContact{
		
		public static final String CONTENT_URI = "content://com.android.contacts/raw_contacts";

		public static final String COL_CONTACT_ID = "contact_id";
		
		public static final String COL_DISPLAY_NAME = "display_name";
		
		public static final String COL_SORT_KEY = "sort_key";
		
		public static final String[] PROJECTION = 
				new String[]{ COL_CONTACT_ID,COL_DISPLAY_NAME };

		public static final String SELECTION = 
				"(" + "(" + COL_DISPLAY_NAME+ " is not null )"
						+"and ("+COL_DISPLAY_NAME+ " != '' )" + ")";
		
	}

	public static class Data{
		
		public static final String CONTENT_URI = "content://com.android.contacts/data";

		public static final String COL_CONTACT_ID = "contact_id";
		
		public static final String COL_MINE_TYPE = "mimetype";
		
		public static final String COL_DATA1 = "data1";
		
		public static final String [] PROJECTION = 
				new String[]{ COL_MINE_TYPE,COL_DATA1 };
		
		public static final String SELECTION = COL_CONTACT_ID + " =? ";
		
	}
	
	public static class MimeType{
		
		public static final String MIME_TYPE_PHONE = "vnd.android.cursor.item/phone_v2";

		public static final String MIME_TYPE_NAME = "vnd.android.cursor.item/name";
		
	}
	
}
