package com.android.mobilepos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.mobilepos.R;
import com.android.mobilepos.parse.data.MenuItem;
import com.android.mobilepos.parse.data.Order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	
	private List<Order> mData = new ArrayList<Order>();
	public OrderListAdapter(List<Order> orderItems){
		mData = orderItems;
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
			v = inflater.inflate(R.layout.order_item_layout, null);
		}
		Order actionItem = mData.get(position);
	    MenuItem it= actionItem.getItem();
        
        TextView itemName = (TextView)v.findViewById(R.id.item_name);
        itemName.setText(it.getItemName());
        TextView itemPrice = (TextView)v.findViewById(R.id.item_price);
        itemPrice.setText("Price: $"+it.getItemPrice());
        TextView itemQuantity = (TextView)v.findViewById(R.id.item_quantity);
        itemQuantity.setText("Quantity: "+actionItem.getItemQuantity());
        
        TextView totalPrice = (TextView)v.findViewById(R.id.item_price_total);
        int quant = actionItem.getItemQuantity();
        totalPrice.setText(""+quant*Integer.parseInt(it.getItemPrice()));
        
     
		return v;
	}
	

}
