package com.android.mobilepos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.mobilepos.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActionListAdapter extends BaseAdapter {
	
	private List<String> mData = new ArrayList<String>();
	public ActionListAdapter(List<String> actionItems){
		mData = actionItems;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View v ;
		if( convertView != null ){
			v= convertView;
		}else
		{
			v = inflater.inflate(R.layout.action_list_item, null);
			
		}
		String actionItem = mData.get(position);
		TextView actionText = (TextView) v.findViewById(R.id.actionitemtext);
		actionText.setText(actionItem);
		return v;
	}
	

}
