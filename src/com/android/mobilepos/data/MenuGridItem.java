package com.android.mobilepos.data;

import java.io.Serializable;
import java.util.List;

import com.android.mobilepos.parse.data.MenuItem;

public class MenuGridItem implements IDataItem, Serializable{
	
	private List<MenuItem> mItemList;
	
	public MenuGridItem(List<MenuItem> itemList){
		mItemList = itemList;
	}
	
	public List<MenuItem> getItemList(){
		return mItemList;
	}

}
