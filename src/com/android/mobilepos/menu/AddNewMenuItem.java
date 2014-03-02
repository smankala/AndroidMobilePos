package com.android.mobilepos.menu;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.mobilepos.MobilePOSApp;
import com.android.mobilepos.R;
import com.android.mobilepos.backend.GsonRequest;
import com.android.mobilepos.data.Item;
import com.android.mobilepos.data.MenuItems;
import com.android.mobilepos.data.POSResponse;
import com.android.mobilepos.parse.data.MenuItem;
import com.android.mobilepos.util.ImageUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class AddNewMenuItem extends DialogFragment {
	private EditText itemName;
	private EditText itemDesc;
	private EditText itemPrice;
	private Button takePicture;
	private Button choosePicture;
	private Button submitMenu;
	private Activity actv;
	private Uri imageUri;
	public static int GETIMAGEURI = 8080;
	public static int GETIMAGEFROMCAMERA = 9090;
	private String encodedImage = "";
	private ImageView iv;
	private Spinner itemSpinner;
	private Listener<POSResponse> mMenuListener;
	private ErrorListener mMenuErrorListener;
	private MenuItemAddListener mMenuItemAddListener;
	public static String ARG_MENU_ID = "argMenuId";
	public interface MenuItemAddListener{
		public void onMenuAddSuccess();
	}

	
	public void setMenuItemAddListener(MenuItemAddListener menuItemAddListener){
		mMenuItemAddListener = menuItemAddListener;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	actv = getActivity();
    	initListeners();
        this.getDialog().setTitle("Add Menu Item:");
        View v = inflater.inflate(R.layout.newmenuitem_dialog, container, false);
        itemName = (EditText)v.findViewById(R.id.item_name);
        itemDesc = (EditText)v.findViewById(R.id.item_desc);
        itemPrice = (EditText)v.findViewById(R.id.item_price);
        takePicture = (Button)v.findViewById(R.id.takepicture);
        iv = (ImageView)v.findViewById(R.id.menuImage);
        itemSpinner = (Spinner)v.findViewById(R.id.item_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.item_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        
        takePicture.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				PackageManager pm = actv.getPackageManager();
				if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
				ContentValues values = new ContentValues();
				if(!externalStorageAvailable()){
					Toast.makeText(actv,"SD Card is unmounted or unavailable", Toast.LENGTH_LONG).show();
					return;
				}
				values.put(MediaColumns.TITLE,"bestpic.png");
				imageUri = actv.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				//create new Intent
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,GETIMAGEFROMCAMERA);
				}
				else
					Toast.makeText(actv, "Camera not Supported",Toast.LENGTH_SHORT).show();
			}
        	
        });
        choosePicture = (Button)v.findViewById(R.id.choosepicture);
        choosePicture.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent,GETIMAGEURI);
			}
        });
        submitMenu = (Button)v.findViewById(R.id.submitmenu);
        submitMenu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MenuItem menu = new MenuItem();
				menu.setItemName(itemName.getText().toString());
				menu.setItemDesc(itemDesc.getText().toString());
				menu.setItemCategory(itemSpinner.getSelectedItem().toString());
				menu.setItemPrice(itemPrice.getText().toString());
				menu.setItemImage(encodedImage);
				menu.saveInBackground(new SaveCallback(){
					@Override
					public void done(ParseException e) {
						if (e == null){
							mMenuItemAddListener.onMenuAddSuccess();
							dismiss();
						}
						else
							Toast.makeText(actv, "Failed to add menuItem. Please try later", Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub
					}
					
				});

				/*
				Item item = new Item();
				item.itemname =  itemName.getText().toString();
				item.itemdesc = itemDesc.getText().toString();
				item.itemprice = itemPrice.getText().toString();
				item.itemimage = encodedImage;
				item.itemcategory = itemSpinner.getSelectedItem().toString();
				MenuItems menuItem = new MenuItems();
				List<Item> itemList  = new ArrayList<Item>();
				itemList.add(item);
				menuItem.menuitems = itemList;
				String requestJson = new Gson().toJson(menuItem,MenuItems.class);
				insertMenuData(requestJson);
				*/
		}
        });
        return v;
    
    }
    
    
    private void initListeners(){
		mMenuListener = new Listener<POSResponse>(){
			@Override
			public void onResponse(POSResponse response) {
				if (response.status == 1){
					mMenuItemAddListener.onMenuAddSuccess();
					dismiss();
				}
				else
					Toast.makeText(actv, "Failed to add menuItem. Please try later", Toast.LENGTH_SHORT).show();
			}
			
		};
		mMenuErrorListener = new ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(actv, "Failed to add menuItem. Please try later", Toast.LENGTH_SHORT).show();
			}
		};
	}
    
    private void insertMenuData(String requestString){
		GsonRequest<POSResponse> request = new GsonRequest<POSResponse>(Method.POST,getUrl(), POSResponse.class,requestString, null, mMenuListener, mMenuErrorListener);
		MobilePOSApp.getInstance().getVolleyQueue().add(request);
	}
    
    
    
    private String getUrl(){
    	return "http://mobile-pos-1.herokuapp.com/insertmenuitem";
	}
    
    @Override
	public void onActivityResult(int requestCode,int resultCode,Intent it){
    	super.onActivityResult(requestCode, resultCode, it);
    	if(requestCode == GETIMAGEURI )
		{
			if(resultCode ==Activity.RESULT_OK) 
    		{
				Uri imageUri = it.getData();
    			this.imageUri = null;
    			iv.setImageBitmap(null);
    			System.gc();
    			this.imageUri = imageUri;
    			if( imageUri != null){
    				Bitmap bm = null;
    				bm = ImageUtils.ShrinkBitmap( ImageUtils.getRealPathFromURI(this.imageUri,actv),64,64);
    				iv.setImageBitmap(bm);
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
   			        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
    			    byte[] b = baos.toByteArray();
    			    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    			}
    		}
		}
		else if(requestCode == GETIMAGEFROMCAMERA){
			if(resultCode ==Activity.RESULT_OK) 
    		{
				if( this.imageUri != null){
    				Bitmap bm = ImageUtils.ShrinkBitmap( ImageUtils.getRealPathFromURI(this.imageUri,actv),64,64);
    				iv.setImageBitmap(bm);
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
  			         bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
   			     	byte[] b = baos.toByteArray();
   			     	encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    				
    			}
    		}
		}
		
	}
    private boolean externalStorageAvailable()
	{
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		if(mExternalStorageAvailable && mExternalStorageWriteable) return true;
		else return false;
	}
    
   
    
}
