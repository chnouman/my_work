package com.ssac.expro.kewen.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;

public class Adapter4YingyuanActivities extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<ArtLesson> list;
	 private lastIndexLoad mLastIndexLoad;
	 private Context c;

	    public Adapter4YingyuanActivities(Context mContext,List<ArtLesson> list,lastIndexLoad mLastIndexLoad)
	    {
	    	c=mContext;
	      lin = LayoutInflater.from(mContext);
	      this.list=list;
	      this.mLastIndexLoad=mLastIndexLoad;
	    }
	    
	    public void addNewData(){
	    	
	    	this.notifyDataSetChanged();
	    	
	    }

	    public int getCount()
	    {
	      return list.size()+1;
	    }

	    public Object getItem(int paramInt)
	    {
	    	if(paramInt==getCount()-1){
	    		return null;
	    	}else{
	    		return  list.get(paramInt);
	    	}
	    }

	    public long getItemId(int paramInt)
	    {
	    	if(paramInt==getCount()-1){
	    		
	    		return -1;
	    	}else{
	    		return list.indexOf(getItem(paramInt));//>getCount()?paramInt%getCount():paramInt;
	    	}
	    }

	    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	    {
	    	//if load the last index 
	    	if(paramInt==getCount()-1){
	    		if(list.size()%10==0){
	    		paramView = lin.inflate(R.layout.load_more, null	);
	    		mLastIndexLoad.loadData();
	    		}else{
	    			TextView text=new TextView(c);
	    			text.setText("没有更多的数据了...");
	    			return text;
	    		}
	    	}else{
	    	
	    	if(paramView!=null&&paramView.findViewById(R.id.imageOfYingyuanActivitiesItem)!=null){
				Log.d("getview-----saved", "doGetView-------get TextView-----------"+paramInt);
			}else{
				Log.d("getview", "doGetView-------new TextView-----------"+paramInt);
				//把xml布局文件变成View对象
				paramView=lin.inflate(R.layout.item_yingyuan_activities, null);
			}
	    	final ViewHolder vh = new ViewHolder();
	    	vh.image = (ImageView) paramView.findViewById(R.id.imageOfYingyuanActivitiesItem);
	    	vh.title		=	(TextView) paramView.findViewById(R.id.texttitleOfYingyuanActivitiesItem);
	    	vh.content	=	(TextView) paramView.findViewById(R.id.textContentOfYingyuanActivitiesItem);
			// 设置数据
	    	ArtLesson Ninfo = list.get(paramInt);
	    	if(null!=Ninfo){
			// 取图片
			String imageUrl = Ninfo.getTitleImageName();
			
			String tag="file";
			if(imageUrl!=null&&imageUrl.indexOf("http")!=-1){
				tag="internet";
			}
				AsyncImageLoader asyncloder = new AsyncImageLoader();
				asyncloder.loadDrawable(imageUrl, new ImageCallback() {
					@Override
					public void imageLoaded(Bitmap bitmap, String imageUrl) {
						
						vh.image.setImageBitmap(bitmap);
						
					}
				}, tag, FromType.home);
	      
				vh.title.setText(Ninfo.getContentTitle());
				vh.content.setText(Ninfo.getSummary());
	    	}
	    	}
	      return paramView;
	    }

	    private static class ViewHolder {
		 ImageView image;
		 TextView title;
		 TextView content;
		}
	    
	    public interface lastIndexLoad{
	    	/**
	    	 * 
	    	 * 获取下一页的数据
	    	 */
	    	void loadData();
	    }
}
