package com.ssac.expro.kewen.util;

import com.ssac.expro.kewen.ExproApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

public class BitMapUtil {

	//缩放对应的bitmap到适应当前设备的顶部位置
	public static BitmapDrawable createBitMapDrawable(int height,Bitmap bitmap){
		BitmapDrawable bd=null;
		
		int screenW=ExproApplication.metrics.widthPixels;
		
		  Matrix matrix = new Matrix();
	        float w = (float)screenW / bitmap.getWidth();
	        float h = (float)height / bitmap.getHeight();
	        matrix.postScale(w, h);// 获取缩放比例
	                    // 根据缩放比例获取新的位图
	        Bitmap newbmp =Bitmap.createScaledBitmap(bitmap, screenW, height, true);
	        // Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix, true);
	        bd = new BitmapDrawable(bitmap);
		
	        return bd;
	}
}
