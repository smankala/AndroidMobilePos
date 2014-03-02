package com.android.mobilepos.parse.data;

public class Order {
	
	private int itemQuantity;
	private MenuItem item;
	
	public MenuItem getItem() {
		return item;
	}
	public void setItem(MenuItem item) {
		this.item = item;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	

}
