package com.android.mobilepos.adapter;

import com.android.mobilepos.MenuTopFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MenuFragmentPagerAdapter extends FragmentPagerAdapter {

	private String[] mCategoryStrings;
	public MenuFragmentPagerAdapter(FragmentManager fm,String[] categoryStrings) {
		super(fm);
		mCategoryStrings = categoryStrings;
	}

	@Override
	public Fragment getItem(int position) {
		MenuTopFragment fragment = MenuTopFragment.getInstance(mCategoryStrings[position]);
		return fragment;
	}

	@Override
	public int getCount() {
		return mCategoryStrings.length;
	}


}
