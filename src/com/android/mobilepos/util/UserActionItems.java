package com.android.mobilepos.util;

public enum UserActionItems {
	MENU("Menu","User Menu"),
	CHEF_VIEW("Chefs View"," Chef View"),
	ADMIN("Admin","Admin Usage");
	
	private String mDisplayName;
	private String mDescription;
	private UserActionItems(String displayName,String description){
		mDisplayName = displayName;
		mDescription = description;
	}
	
	public String getDisplayName(){
		return mDisplayName;
	}
	
	public String getDescription(){
		return mDescription;
	}
	
	public static String[] getActionItems(){
		return new String[]{
			MENU.mDisplayName,
			CHEF_VIEW.mDisplayName,
			ADMIN.mDisplayName
		};
	}

}
