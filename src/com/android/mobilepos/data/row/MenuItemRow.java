package com.android.mobilepos.data.row;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilepos.R;
import com.android.mobilepos.data.IDataItem;
import com.android.mobilepos.data.Item;

public class MenuItemRow extends FrameLayout implements IDataRow {


	public MenuItemRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void bindData(IDataItem data) {
		if (data instanceof Item) {
			Item it = (Item) data;
			ImageView imageView = (ImageView) findViewById(R.id.item_image);
			byte[] decodedString;
			if(it.itemimage != null){
				decodedString = Base64.decode(it.itemimage, Base64.DEFAULT);
				Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
						decodedString.length);
				imageView.setImageBitmap(bitmap);
				imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,150));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}
			TextView itemName = (TextView) findViewById(R.id.item_name);
			itemName.setText(it.itemname);
			TextView itemPrice = (TextView) findViewById(R.id.item_price);
			itemPrice.setText("$" + it.itemprice + "");
		}
	}
}
