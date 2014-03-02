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
import com.android.mobilepos.data.MenuGridItem;
import com.android.mobilepos.data.MenuHeader;
import com.android.mobilepos.data.row.IDataRow;
import com.android.mobilepos.data.row.MenuGridRow;
import com.android.mobilepos.data.row.MenuGridRow.MenuItemClickListener;
import com.android.mobilepos.parse.data.MenuItem;


public class MenuTopAdapter extends BaseAdapter {
	
	private enum RowType{HEADER_ROW,MENU_GRID_ROW};

	private List<MenuItem> appetizerList;
	private List<MenuItem> entreeList;
	private List<MenuItem> dessertList;
	private List<MenuItem> otherList;
	private final String APPETIZER = "Appetizer";
	private final String ENTREE = "Entree";
	private final String DESSERT = "Dessert";
	private final String SIDE_DISH = "Side Dish";
	private final String OTHER = "Other";
	private MenuItemClickListener mMenuItemClickListener;
	
	private ArrayList<IDataItem> mData = new ArrayList<IDataItem>();
	public MenuTopAdapter(MenuItemClickListener listener){
		mData.clear();
		mMenuItemClickListener = listener;
		appetizerList = new ArrayList<MenuItem>();
		entreeList = new ArrayList<MenuItem>();
		dessertList = new ArrayList<MenuItem>();
		otherList = new ArrayList<MenuItem>();
		
		/*
		mData.add(new MenuHeader("Appetizer"));
		mData.add(new MenuGridItem("Appetizer"));
		
		mData.add(new MenuHeader("Entree"));
		mData.add(new MenuGridItem("Entree"));
		
		mData.add(new MenuHeader("Dessert"));
		mData.add(new MenuGridItem("Dessert"));
		
		mData.add(new MenuHeader("Side Dish"));
		mData.add(new MenuGridItem("Side Dish"));
		*/
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
		return  10;
	}
	
	
	public RowType getItemView(int position){
		IDataItem item = mData.get(position);
		if( item instanceof MenuGridItem)
			return RowType.MENU_GRID_ROW;
		else
			return RowType.HEADER_ROW;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (getItemView(position) == RowType.MENU_GRID_ROW) {
				v = inflater.inflate(R.layout.menu_grid_row, null);
			} else {
				v = inflater.inflate(R.layout.menu_header_row, null);
			}
		} else
			v = convertView;
		
		((IDataRow)v).bindData(mData.get(position));
		if (getItemView(position) == RowType.MENU_GRID_ROW){
			IDataRow dataRow = (IDataRow)v;
			if( dataRow instanceof MenuGridRow){
				((MenuGridRow)dataRow).setMenuClickListener(mMenuItemClickListener);
			}
			
		}
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

	public void onItemClick(int position) {
		IDataItem it =  getItem(position);
		/*if (it instanceof Item) {
			Item item = (Item)it;
			Bundle data = new Bundle();
			MenuOrderDialog mi = new MenuOrderDialog();
			data.putSerializable(MenuOrderDialog.ARG_DATA_ID, item);
			mi.setArguments(data);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Fragment prev = getFragmentManager()
					.findFragmentByTag("menuDialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			mi.show(ft, "menuDialog");
			*/
		}

	public void onMenuDataAvailable(
			List<com.android.mobilepos.parse.data.MenuItem> menuItems) {
		if (menuItems == null || menuItems.size() < 0)
			return;

		mData.clear();
		List<Item> items = new ArrayList<Item>();
		for (MenuItem m : menuItems) {
			if (APPETIZER.equals(m.getItemCategory())) {
				appetizerList.add(m);
			} else if (this.ENTREE.equals(m.getItemCategory())) {
				entreeList.add(m);
			} else if (this.DESSERT.equals(m.getItemCategory())) {
				dessertList.add(m);
			} else {
				otherList.add(m);
			}
		}
		
		if (appetizerList.size() > 0) {
			mData.add(new MenuHeader(APPETIZER));
			mData.add(new MenuGridItem(appetizerList));
		}
		if (entreeList.size() > 0) {
			mData.add(new MenuHeader(ENTREE));
			mData.add(new MenuGridItem(entreeList));
		}
		if (dessertList.size() > 0) {
			mData.add(new MenuHeader(DESSERT));
			mData.add(new MenuGridItem(dessertList));
		}
		if (otherList.size() > 0) {
			mData.add(new MenuHeader(OTHER));
			mData.add(new MenuGridItem(otherList));
		}
		mData.addAll(items);
	}

}




