package com.ssac.expro.kewen.adapter;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.AD;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;

public class ImageAdapter4GalleryTheatre extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<AD> list;
	 private Context mContext;

	    public ImageAdapter4GalleryTheatre(Context mContext,List<AD> list)
	    {
	      lin = LayoutInflater.from(mContext);
	      this.list=list;
	      this.mContext=mContext;
	    }
	    
	    public void addNewData(){
	    	
	    	this.notifyDataSetChanged();
	    	
	    }

	    public int getCount()
	    {
	      return list.size();
	    }

	    public Object getItem(int paramInt)
	    {
	      return  list.get(paramInt>=getCount()?paramInt%getCount():paramInt);
	    }

	    public long getItemId(int paramInt)
	    {
	      return list.indexOf(paramInt>getCount()?paramInt%getCount():paramInt);//
	    }

	    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	    {
	    	if(paramView!=null&&paramView.findViewById(R.id.imgOfYanChuGalleryItem)!=null){
				Log.d("getview-----saved", "doGetView-------get TextView-----------"+paramInt);
			}else{
				Log.d("getview", "doGetView-------new TextView-----------"+paramInt);
				//把xml布局文件变成View对象
				paramView=lin.inflate(R.layout.item_yanchu_gallery, null);
			}
	    	final ViewHolder vh = new ViewHolder();
	    	vh.image = (ImageView) paramView.findViewById(R.id.imgOfYanChuGalleryItem);
	    	vh.image.setLayoutParams(new LinearLayout.LayoutParams(ExproApplication.metrics.widthPixels,LayoutParams.FILL_PARENT));
	    	vh.image.setScaleType(ImageView.ScaleType.FIT_XY);
			// 设置数据
			final AD Ninfo = list.get(paramInt);
			// 取图片
			String imageUrl = Ninfo.getImgPath();
			ImageCacheUtil ic =new ImageCacheUtil();
			ic.loadImageGallery(ExproApplication.imageLoader, vh.image, imageUrl,null);
		/*	String tag="file";
			if(imageUrl!=null&&imageUrl.indexOf("http")!=-1){
				tag="internet";
			}
				AsyncImageLoader asyncloder = new AsyncImageLoader();
				asyncloder.loadDrawable(imageUrl, new ImageCallback() {
					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
//						vh.image.setImageBitmap(bitmap);
						if(null!=bitmap&&!bitmap.isRecycled())
					vh.image.setBackgroundDrawable(new BitmapDrawable(bitmap));
					}
				}, tag, FromType.home);
	      */

	      return paramView;
	    }

	    private static class ViewHolder {
		 ImageView image;
		}
}
