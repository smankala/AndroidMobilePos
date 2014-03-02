package com.android.mobilepos;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.mobilepos.adapter.MenuTopAdapter;
import com.android.mobilepos.data.row.MenuGridRow.MenuItemClickListener;
import com.android.mobilepos.menu.AddNewMenuItem;
import com.android.mobilepos.menu.MenuOrderDialog;
import com.android.mobilepos.util.LoadingView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class MenuTopFragment extends Fragment implements AddNewMenuItem.MenuItemAddListener,MenuItemClickListener{
	
	private View mView;
	private ListView mListView;
	private MenuTopAdapter mMenuTopAdapter;
	private LoadingView mLoadingView;
	private static final String ARG_CATEGORY_NAME = "argCategoryName";
	String mCategoryName;
	
	
	public static MenuTopFragment getInstance(String categoryName){
		MenuTopFragment fragment = new MenuTopFragment();
		Bundle args = new Bundle();
		args.putString(ARG_CATEGORY_NAME, categoryName);
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
		mCategoryName = getArguments().getString(ARG_CATEGORY_NAME);
		if (mCategoryName == null)
			mCategoryName = "Other";
		mView = inflater.inflate(R.layout.std_listview_screen, null);
		mListView = (ListView) mView.findViewById(R.id.list);
		mListView.setDivider(null);
		mLoadingView = (LoadingView)mView.findViewById(R.id.loadingview);
		mMenuTopAdapter = new MenuTopAdapter(this);
		mListView.setAdapter(mMenuTopAdapter);
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
	
	private void getLatestData(){
		
		ParseQuery<com.android.mobilepos.parse.data.MenuItem> parseQuery = ParseQuery.getQuery(com.android.mobilepos.parse.data.MenuItem.class);
		parseQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		parseQuery.whereEqualTo("itemCategory", mCategoryName);
		parseQuery.findInBackground(new FindCallback<com.android.mobilepos.parse.data.MenuItem>(){
			@Override
			public void done(
					List<com.android.mobilepos.parse.data.MenuItem> menuItems,
					ParseException e) {
				if( e ==  null ){
					mMenuTopAdapter.onMenuDataAvailable(menuItems);
					mMenuTopAdapter.notifyDataSetChanged();
					mLoadingView.success();
				}
				else{
					mLoadingView.setError("Failed to load menu. Please try later");
				}
			}
			
		});
	}

	@Override
	public void onMenuAddSuccess() {
		//TODO:   
		
	}

	@Override
	public void onClick(com.android.mobilepos.parse.data.MenuItem menuItem) {
		MenuOrderDialog mi = new MenuOrderDialog();
		mi.setMenuItem(menuItem);
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("menuDialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);
	    mi.show(ft, "menuDialog");
	}
}
