package com.android.mobilepos.util;

import com.android.mobilepos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class LoadingView extends ViewFlipper{
	private TextView mTextView;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(getContext(), R.layout.loading_view, this);
		mTextView = (TextView)findViewById(R.id.loadingmessage);
	}
	
	public void success(){
		setDisplayedChild(2);
	}
	
	public void setLoading(){
		setDisplayedChild(0);
	}
	
	public void setError(String message){
		mTextView.setText(message);
		setDisplayedChild(1);
	}

}
