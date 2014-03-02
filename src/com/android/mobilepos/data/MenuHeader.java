package com.android.mobilepos.data;

import java.io.Serializable;

public class MenuHeader implements IDataItem, Serializable{
	private String mDisplayText;
	
	public MenuHeader(String displayText){
		mDisplayText = displayText;
	}
	
	public String getDisplayText(){
		return mDisplayText;
	}

}
