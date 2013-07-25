package com.ssac.expro.kewen.util;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.ssac.expro.kewen.R;

public class ImageCacheUtil {
	
	static DisplayImageOptions options=null;
	static DisplayImageOptions options2=null;
	static DisplayImageOptions options3=null;
	
	static{
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder)
		.showImageForEmptyUri(R.drawable.placeholder)
		.showImageOnFail(R.drawable.placeholder)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		options2 = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder_long)
		.showImageForEmptyUri(R.drawable.placeholder_long)
		.showImageOnFail(R.drawable.placeholder_long)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		options3 = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder_high)
		.showImageForEmptyUri(R.drawable.placeholder_high)
		.showImageOnFail(R.drawable.placeholder_high)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	
	
	public void init(){
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder)
		.showImageForEmptyUri(R.color.white)
		.showImageOnFail(R.drawable.placeholder)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		options2 = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder)
		.showImageForEmptyUri(R.color.white)
		.showImageOnFail(R.drawable.placeholder)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		options3 = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.placeholder_high)
		.showImageForEmptyUri(R.drawable.placeholder_high)
		.showImageOnFail(R.drawable.placeholder_high)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	/**
	 * 
	 * @param iv
	 * @param fileName 图片路径
	 */
	public  void loadImageList(ImageLoader imageLoader,ImageView iv,String fileName){
		if(null!=imageLoader){
			if(options==null){
				init();
			}
			imageLoader.displayImage(fileName, iv, options);
		}
	}
	
	public  void  loadImageGallery(ImageLoader imageLoader,ImageView iv,String fileName,ImageLoadingListener lin ){
		if(null!=imageLoader){
			if(options==null){
				init();
			}
//			imageLoader.displayImage(fileName, iv, options2);
			imageLoader.displayImage(fileName, iv, options2, lin);
		}
	}
	
	public  void  loadImageGallery2(ImageLoader imageLoader,ImageView iv,String fileName,ImageLoadingListener lin ){
		if(null!=imageLoader){
			if(options==null){
				init();
			}
//			imageLoader.displayImage(fileName, iv, options2);
			imageLoader.displayImage(fileName, iv, options3, lin);
		}
	}
	
	public static void ClearCache(ImageLoader imageLoader){
		imageLoader.clearMemoryCache();
		imageLoader.clearDiscCache();
	}
}
