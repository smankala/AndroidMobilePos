package com.android.mobilepos.menu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.mobilepos.R;
import com.android.mobilepos.parse.data.MenuItem;

public class MenuOrderDialog extends DialogFragment {
	private MenuItem mMenuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	   View v = inflater.inflate(R.layout.menu_select_dialog, container, false);
    
		if (mMenuItem != null) {
			getDialog().setTitle("Select Quantity:");
			final NumberPicker np = (NumberPicker) v
					.findViewById(R.id.quantity_picker);
			np.setMinValue(1);
			np.setMaxValue(20);
			np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			TextView itemDesc = (TextView) v.findViewById(R.id.item_description);
			itemDesc.setText(mMenuItem.getItemDesc());
			Button b = (Button) v.findViewById(R.id.add_to_order);
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*
					 * Order order = new Order(); Item it = new Item();
					 * it.setItemDesc(it_desc); it.setItemCategory(it_category);
					 * it.setItemName(it_name); it.setItemPrice(it_price);
					 * order.setItem(it); order.setItemQuantity(np.getValue());
					 * ((ManageMultiClientActivity)
					 * getActivity()).refreshOrders(order);
					 * MenuOrderDialog.this.dismiss();
					 */
				}
			});
			Button cancel = (Button) v.findViewById(R.id.cancel_order);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					MenuOrderDialog.this.dismiss();
				}
			});
		}
    return v;
    }

	public void setMenuItem(com.android.mobilepos.parse.data.MenuItem menuItem) {
		this.mMenuItem = menuItem;
	}
}
