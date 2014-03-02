package com.android.mobilepos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.mobilepos.adapter.OrderListAdapter;
import com.android.mobilepos.parse.data.MenuItem;
import com.android.mobilepos.parse.data.Order;

public class OrderListFragment extends Fragment {
		private View mContentView;
		private ListView orderList;
		private Button sendOrder;
		private Button newOrder;
		 @Override
		  public void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		 }
		 
	 	@Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	    }
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        mContentView = inflater.inflate(R.layout.device_list, null);
	        newOrder = (Button)mContentView.findViewById(R.id.newOrder);
	        newOrder.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

				}
	        });
	        sendOrder = (Button)mContentView.findViewById(R.id.sendOrder);
	        sendOrder.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
		
				}
				});
	        orderList = (ListView)mContentView.findViewById(R.id.order_list);
	        List<Order> orderListItems = new ArrayList<Order>();
	        Order order = new Order();
	        order.setItemQuantity(10);
	        MenuItem menuItem = new MenuItem();
	        menuItem.setItemCategory("appet");
	        menuItem.setItemDesc("tasty");
	        menuItem.setItemName("tasty1");
	        menuItem.setItemPrice("23");
	        order.setItem(menuItem);
	        orderListItems.add(order);
	        
	        orderList.setAdapter(new OrderListAdapter(orderListItems));
	        return mContentView;
	    }
	    
		
		
		
		public void refresh(Order ord){
		}
		
		 public void hideDetails() {
		        this.getView().setVisibility(View.GONE);
		    }
			public void show() {
				this.getView().setVisibility(View.VISIBLE);
		
			}
	}
