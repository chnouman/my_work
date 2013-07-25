package com.ssac.expro.kewen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.AD;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.BitMapUtil;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;
import com.ssac.expro.kewen.util.ImageCacheUtil;

public class ImageAdapter4NumberGallery extends BaseAdapter {

	 private final Context mContext;
	 private LayoutInflater lin;

	    public ImageAdapter4NumberGallery(Context mContext)
	    {
	      this.mContext = mContext;
	      lin = LayoutInflater.from(mContext);
	    }
	    
	    public void addNewData(){
	    	
	    	this.notifyDataSetChanged();
	    	
	    }

	    public int getCount()
	    {
	      return MainService.adList.size();
	    }

	    public Object getItem(int paramInt)
	    {
	      return  MainService.adList.get(paramInt);
	    }

	    public long getItemId(int paramInt)
	    {
	      return paramInt;//>getCount()?paramInt%getCount():paramInt;
	    }

	    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	    {
	    	if(paramView!=null&&paramView.findViewById(R.id.imgOfHomeItem)!=null){
				Log.d("getview-----saved", "doGetView-------get TextView-----------"+paramInt);
			}else{
				Log.d("getview", "doGetView-------new TextView-----------"+paramInt);
				//把xml布局文件变成View对象
				paramView=lin.inflate(R.layout.item_home, null);
			}
	    	final ViewHolder vh = new ViewHolder();
	    	vh.image = (ImageView) paramView.findViewById(R.id.imgOfHomeItem);
	    	vh.image.setScaleType(ImageView.ScaleType.FIT_XY);
	    	vh.image.setLayoutParams(new LinearLayout.LayoutParams(ExproApplication.metrics.widthPixels,LayoutParams.FILL_PARENT));
			// 设置数据
			final AD Ninfo = MainService.adList.get(paramInt);
			// 取图片
			String imageUrl = Ninfo.getImgPath();
		/*	
			String tag="file";
			if(imageUrl!=null&&imageUrl.indexOf("http")!=-1){
				tag="internet";
			}
				AsyncImageLoader asyncloder = new AsyncImageLoader();
				asyncloder.loadDrawable(imageUrl, new ImageCallback() {
					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
						
//						BitmapDrawable imageDrawable =BitMapUtil.createBitMapDrawable(150, bitmap);
						if(null!=bitmap&&!bitmap.isRecycled())
						vh.image.setImageBitmap(bitmap);
						
					}
				}, tag, FromType.home);
//				vh.image.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						
//						//url跳转
//						Uri uri = Uri.parse(Ninfo.getLinkAddress());    
//						Intent it   = new Intent(Intent.ACTION_VIEW,uri);    
//						mContext.startActivity(it);   
//					}
//				});
*/	      
			ImageCacheUtil ic =new ImageCacheUtil();
			ic.loadImageGallery(ExproApplication.imageLoader, vh.image, imageUrl,null);
			Log.i("poe", imageUrl);
			return paramView;
	    }

	    private static class ViewHolder {
		 ImageView image;
		}
}
