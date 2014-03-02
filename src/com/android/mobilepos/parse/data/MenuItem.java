package com.android.mobilepos.parse.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("MenuItem")
public class MenuItem extends ParseObject{
	public String getItemName() {
		return getString("itemName");
	}
	public void setItemName(String itemName) {
		put("itemName", itemName);
	}
	public String getItemDesc() {
		return getString("itemDesc");
	}
	public void setItemDesc(String itemDesc) {
		put("itemDesc", itemDesc);
	}
	public String getItemPrice() {
		return getString("itemPrice");
	}
	public void setItemPrice(String itemPrice) {
		put("itemPrice", itemPrice);
	}
	public String getItemCategory() {
		return getString("itemCategory");
	}
	public void setItemCategory(String itemCategory) {
		put("itemCategory", itemCategory);
	}
	public String getItemImage() {
		return getString("itemImage");
	}
	public void setItemImage(String itemImage) {
		put("itemImage", itemImage);
	}
}

