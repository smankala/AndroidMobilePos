package com.android.mobilepos.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageUtils {
	public static  Bitmap ShrinkBitmap(String file, int width, int height){
		   
	     BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
	        bmpFactoryOptions.inJustDecodeBounds = true;
	        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
	         
	        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
	        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);
	         
	        if (heightRatio > 1 || widthRatio > 1)
	        {
	         if (heightRatio > widthRatio)
	         {
	          bmpFactoryOptions.inSampleSize = heightRatio;
	         } else {
	          bmpFactoryOptions.inSampleSize = widthRatio; 
	         }
	        }
	         
	        bmpFactoryOptions.inJustDecodeBounds = false;
	        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
	     return bitmap;
	    }
	
	public static String getRealPathFromURI(Uri contentUri,Activity ctx) {
	       String[] proj = { MediaStore.Images.Media.DATA };
	       Cursor cursor = ctx.getContentResolver().query(contentUri, proj, null, null, null);
	       int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	       String realPathFromURI = cursor.getString(column_index);
	       cursor.close();
	       return realPathFromURI;
	   }
	
	public static final Bitmap downloadFile(String fileUrl){
		URL myFileUrl =null;
		Bitmap bmImg = null;
		HttpURLConnection conn = null;
		try {
			myFileUrl= new URL(fileUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			conn = (HttpURLConnection)myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.setUseCaches(true);
			conn.setAllowUserInteraction(false);
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			bmImg = BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			if(conn != null )
				conn.disconnect();
		}
		
		return bmImg;
	}
}
