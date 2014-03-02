package com.android.mobilepos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.mobilepos.adapter.MenuOrderAdapter;
import com.android.mobilepos.util.LoadingView;

public class MenuOrderFragment extends Fragment {

	public static MenuOrderFragment getInstance(){
		MenuOrderFragment fragment = new MenuOrderFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	private View mView;
	private ListView mListView;
	private LoadingView mLoadingView;
	private MenuOrderAdapter mMenuOrderAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.std_listview_screen, null);
		mListView = (ListView) mView.findViewById(R.id.list);
		mListView.setDivider(null);
		mLoadingView = (LoadingView)mView.findViewById(R.id.loadingview);
		mLoadingView.success();
		mMenuOrderAdapter = new MenuOrderAdapter();
		mListView.setAdapter(mMenuOrderAdapter);
		return mView;
	}

	
}
