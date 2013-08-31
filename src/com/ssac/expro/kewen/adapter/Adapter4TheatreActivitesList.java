package com.ssac.expro.kewen.adapter;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.Theatre;
import com.ssac.expro.kewen.util.ImageCacheUtil;

public class Adapter4TheatreActivitesList extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<Theatre> list;
	 private lastIndexLoad4Activities mLastLoad;
	 private Context c;
	    public Adapter4TheatreActivitesList(Context mContext,List<Theatre> list,lastIndexLoad4Activities mLastLoad)
	    {
	    	c=mContext;
	      lin = LayoutInflater.from(mContext);
	      this.list=list;
	      this.mLastLoad=mLastLoad;
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
	    	if(paramInt==getCount()-1){
	    		if(list.size()%10==0){
		    		paramView = lin.inflate(R.layout.load_more, null	);
		    		mLastLoad.loadData();
		    		}else{
		    			TextView text=new TextView(c);
		    			text.setText("没有更多的数据了...");
		    			return text;
		    		}
	    	}else{
	    	if(paramView!=null&&paramView.findViewById(R.id.imageOfTheatreActivitiesItem)!=null){
				Log.d("getview-----saved", "doGetView-------get TextView-----------"+paramInt);
			}else{
				Log.d("getview", "doGetView-------new TextView-----------"+paramInt);
				//把xml布局文件变成View对象
				paramView=lin.inflate(R.layout.item_theatre_activies, null);
			}
	    	final ViewHolder vh = new ViewHolder();
	    	vh.image = (ImageView) paramView.findViewById(R.id.imageOfTheatreActivitiesItem);
	    	vh.title		=	(TextView) paramView.findViewById(R.id.titleOfTheatreActivitiesItem);
	    	vh.content	=	(TextView) paramView.findViewById(R.id.contentOfTheatreActivitiesItem);
			// 设置数据
			Theatre Ninfo = list.get(paramInt);
			// 取图片
			String imageUrl = Ninfo.getTitleImage();
			ImageCacheUtil ic =new ImageCacheUtil();
			ic.loadImageList(ExproApplication.imageLoader, vh.image, imageUrl);
	      
				vh.title.setText(Ninfo.getContentTitle());
				vh.content.setText(Ninfo.getSummary());
				
	    	}
	      return paramView;
	    }

	    private static class ViewHolder {
		 ImageView image;
		 TextView title;
		 TextView content;
		}
	    
	    public interface lastIndexLoad4Activities{
	    	
	    	void loadData();
	    }
}
