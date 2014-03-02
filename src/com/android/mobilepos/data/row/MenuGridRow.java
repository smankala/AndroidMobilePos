package com.android.mobilepos.data.row;

import java.util.List;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.mobilepos.R;
import com.android.mobilepos.adapter.MenuAdapter;
import com.android.mobilepos.data.IDataItem;
import com.android.mobilepos.data.MenuGridItem;
import com.android.mobilepos.menu.MenuOrderDialog;
import com.android.mobilepos.parse.data.MenuItem;

public class MenuGridRow extends LinearLayout implements IDataRow{

	private GridView mGridView;
	private MenuAdapter mMenuAdapter;
	private MenuItemClickListener mMenuItemClickListener;
	public MenuGridRow(Context context) {
		super(context);
	}
	
	public MenuGridRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
   
	public void setMenuClickListener(MenuItemClickListener menuItemClickListener){
		mMenuItemClickListener= menuItemClickListener;
	}
	
	@Override
	public void bindData(IDataItem data) {
		if( data instanceof MenuGridItem){
			MenuGridItem dataItem = (MenuGridItem)data;
			final List<MenuItem> itemList = dataItem.getItemList();
			mGridView = (GridView) findViewById(R.id.gridview);
			mMenuAdapter = new MenuAdapter();
			mGridView.setAdapter(mMenuAdapter);
			mGridView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						mMenuItemClickListener.onClick(itemList.get(position));
					}
		        });
			mMenuAdapter.onMenuDataAvailable(dataItem.getItemList());
		}
	}
	
	
	public interface MenuItemClickListener{
		public void onClick(MenuItem menuItem);
	}
	

}
