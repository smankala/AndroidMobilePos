package com.android.mobilepos.data.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mobilepos.R;
import com.android.mobilepos.data.IDataItem;
import com.android.mobilepos.data.MenuHeader;

public class MenuHeaderRow extends LinearLayout implements IDataRow{
	
	TextView mTextView;
	public MenuHeaderRow(Context context) {
		super(context);
	}
	
	public MenuHeaderRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void bindData(IDataItem data) {
		mTextView = (TextView)findViewById(R.id.typename);
		if( data instanceof MenuHeader){
			MenuHeader row = (MenuHeader)data;
			mTextView.setText(row.getDisplayText());
		}
	}
	

}
