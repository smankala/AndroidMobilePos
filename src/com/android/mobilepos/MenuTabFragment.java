package com.android.mobilepos;

import android.app.ActionBar.TabListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.android.mobilepos.adapter.MenuFragmentPagerAdapter;
import com.android.mobilepos.adapter.MenuTopAdapter;
import com.android.mobilepos.adapter.MyTabFactory;
import com.android.mobilepos.util.LoadingView;

public class MenuTabFragment extends Fragment implements OnTabChangeListener{
	
	private View mView;
	private ListView mListView;
	private MenuTopAdapter mMenuTopAdapter;
	private LoadingView mLoadingView;
	private ViewPager mViewPager;
	private MenuFragmentPagerAdapter mFragmentPagerAdapter;
	private TabListener mTabListener;
	private TabHost mTabHost;
	
	
	public static MenuTabFragment getInstance(){
		MenuTabFragment fragment = new MenuTabFragment();
		Bundle args = new Bundle();
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
		mView = inflater.inflate(R.layout.menu_viewpager, null);
		mViewPager = (ViewPager)mView.findViewById(R.id.pager);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int pagePosition) {
				if( mTabHost.getCurrentTab() != pagePosition)
					mTabHost.setCurrentTab(pagePosition);
			}
		});
		FragmentManager manager = getActivity().getSupportFragmentManager();

		mTabHost = (TabHost)mView.findViewById(R.id.tabhost);
		mTabHost.setup();
		mTabHost.setOnTabChangedListener(this);
		String[] catStrings = getResources().getStringArray(R.array.item_categories);
        for(String tabItem : catStrings){
        	TabSpec spec = mTabHost.newTabSpec(tabItem);
        	spec.setIndicator(tabItem);
        	spec.setContent(new MyTabFactory(getActivity()));
        	mTabHost.addTab(spec);
        }
        
		mFragmentPagerAdapter = new MenuFragmentPagerAdapter(manager,catStrings);
		mViewPager.setAdapter(mFragmentPagerAdapter);
		return mView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_items, menu);
	}

	@Override
	public void onTabChanged(String tabId) {
		for(int i=0;i<mTabHost.getTabWidget().getTabCount();i++){
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		mTabHost.getCurrentTabView().setBackgroundColor(R.string.tab_select_color);
		if( mViewPager.getCurrentItem()!= mTabHost.getCurrentTab())
			mViewPager.setCurrentItem(mTabHost.getCurrentTab());
	}
}
