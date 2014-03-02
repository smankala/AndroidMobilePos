package com.android.mobilepos.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class MyTabFactory implements TabContentFactory {

    private final Context mContext;

    public MyTabFactory(Context context) {
        mContext = context;
    }

	@Override
	public View createTabContent(String tag) {
	        final TextView tv = new TextView(mContext);
	        tv.setText("Content for tab with tag " + tag);
	        return tv;
	}
    
}
