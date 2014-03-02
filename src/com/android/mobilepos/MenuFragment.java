package com.android.mobilepos;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.mobilepos.adapter.MenuAdapter;
import com.android.mobilepos.data.MenuItems;
import com.android.mobilepos.menu.AddNewMenuItem;
import com.android.mobilepos.util.LoadingView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class MenuFragment extends Fragment implements AddNewMenuItem.MenuItemAddListener{
	
	private static String ARG_ITEM_TYPE="argItemType";
	private View mView;
	private GridView mGridView;
	private Listener<MenuItems> mMenuListener;
	private ErrorListener mMenuErrorListener;
	private MenuAdapter mMenuAdapter;
	private LoadingView mLoadingView;
	private String mItemType;
	public static MenuFragment getInstance(String itemType){
		MenuFragment fragment = new MenuFragment();
		Bundle args = new Bundle();
		args.putString(ARG_ITEM_TYPE, itemType);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mItemType = getArguments().getString(ARG_ITEM_TYPE);
		mView = inflater.inflate(R.layout.menu_layout, null);
		mGridView = (GridView) mView.findViewById(R.id.gridview);
		mLoadingView = (LoadingView)mView.findViewById(R.id.loadingview);
		mMenuAdapter = new MenuAdapter();
		mGridView.setAdapter(mMenuAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
						//mMenuAdapter.onItemClick(position);
				}
	        });
		initListeners();
		getLatestData();
		return mView;
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_items, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.addmenu:
			AddNewMenuItem newMenuItemDialog = new AddNewMenuItem();
			newMenuItemDialog.setMenuItemAddListener(this);
			FragmentManager manager = this.getFragmentManager();
			newMenuItemDialog.show(manager, "menuItemAddtag");
	        return false;
	    default:
	        break;
	    }
	    return false;
	}
	
	private void initListeners(){
		/*mMenuListener = new Listener<MenuItems>(){
			@Override
			public void onResponse(MenuItems response) {
				mMenuAdapter.onMenuDataAvailable(response);
				mMenuAdapter.notifyDataSetChanged();
			}
			
		};
		mMenuErrorListener = new ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		};
		*/
	}
	
	private void getLatestData(){
	
		ParseQuery<com.android.mobilepos.parse.data.MenuItem> parseQuery = ParseQuery.getQuery(com.android.mobilepos.parse.data.MenuItem.class);
		parseQuery.whereEqualTo("itemCategory", mItemType);
		parseQuery.findInBackground(new FindCallback<com.android.mobilepos.parse.data.MenuItem>(){
			@Override
			public void done(
					List<com.android.mobilepos.parse.data.MenuItem> menuItems,
					ParseException e) {
				if( e ==  null ){
					mMenuAdapter.onMenuDataAvailable(menuItems);
					mMenuAdapter.notifyDataSetChanged();
					mLoadingView.success();
				}
				else{
					mLoadingView.setError("Failed to load menu. Please try later");
				}
			}
			
		});
		
		/*
		GsonRequest<MenuItems> request = new GsonRequest<MenuItems>(getUrl(), MenuItems.class, null, mMenuListener, mMenuErrorListener);
		MobilePOSApp.getInstance().getVolleyQueue().add(request);
		*/
	}
	
	private String getUrl(){
		return "http://mobile-pos-1.herokuapp.com/getmenu";
	}

	@Override
	public void onMenuAddSuccess() {
		getLatestData();
	}
}
