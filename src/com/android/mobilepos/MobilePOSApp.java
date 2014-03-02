package com.android.mobilepos;

import com.android.mobilepos.parse.data.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class MobilePOSApp extends Application{

	public static MobilePOSApp mobilePosApp;
	private RequestQueue mRequestQueue;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initializeVolley();
		mobilePosApp = this;
		ParseObject.registerSubclass(MenuItem.class);
		Parse.initialize(this, "MBhsAtY6pEQ722w2UgoM0lzxVU4xoq6qBQP86q6j", "BTXTbgHRT0Dk70sjE71ill4dtRK6Xaza5dq3grVI");
	}
	
	public static MobilePOSApp getInstance(){
		return mobilePosApp;
	}
	
	private void initializeVolley(){
		mRequestQueue = Volley.newRequestQueue(this);
	}
	
	public RequestQueue getVolleyQueue(){
		return mRequestQueue;
	}

}
