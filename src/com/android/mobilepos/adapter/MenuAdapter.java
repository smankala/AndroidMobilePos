package com.android.mobilepos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.mobilepos.R;
import com.android.mobilepos.data.IDataItem;
import com.android.mobilepos.data.Item;
import com.android.mobilepos.data.row.IDataRow;
import com.android.mobilepos.parse.data.MenuItem;


public class MenuAdapter extends BaseAdapter {
	
	private enum RowType{HEADER_ROW,MENU_ITEM_ROW};
	private ArrayList<IDataItem> mData = new ArrayList<IDataItem>();;
	
	public MenuAdapter(){
		mData.clear();
	}

	@Override
	public int getCount() {
		return mData.size();
	}
	
	@Override
	public IDataItem getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	@Override
	public int getItemViewType(int position) {
		return getItemView(position).ordinal();
	}
	
	@Override
	public int getViewTypeCount() {
		return RowType.values().length;
	}
	
	
	public RowType getItemView(int position){
		IDataItem item = mData.get(position);
		if( item instanceof Item)
			return RowType.MENU_ITEM_ROW;
		else
			return RowType.HEADER_ROW;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (getItemView(position) == RowType.MENU_ITEM_ROW) {
				v = inflater.inflate(R.layout.menu_item_layout, null);
			} else {
				v = inflater.inflate(R.layout.menu_item_layout, null);
			}
		} else
			v = convertView;
		
		((IDataRow)v).bindData(mData.get(position));
		return v;
	}

	/*
	public void onMenuDataAvailable(MenuItems response) {
		if( response == null || response.menuitems == null || response.menuitems.size() == 0)
			return;
		
		mData.clear();
		List<Item> menuItems = response.menuitems;
		mData.addAll(menuItems);
	}
	*/

	

	public void onMenuDataAvailable(
			List<com.android.mobilepos.parse.data.MenuItem> menuItems) {
		if( menuItems == null || menuItems.size() <0)
			return;
		
		mData.clear();
		//TODO: We dont need this conversion once we move to parse completely
		List<Item> items = new ArrayList<Item>();
		for( MenuItem m: menuItems){
			Item it = new Item();
			it.itemcategory = m.getItemCategory();
			it.itemdesc = m.getItemDesc();
			it.itemimage = m.getItemImage();
			it.itemname = m.getItemName();
			it.itemprice = m.getItemPrice();
			items.add(it);
		}
		
		mData.addAll(items);
	}

}




