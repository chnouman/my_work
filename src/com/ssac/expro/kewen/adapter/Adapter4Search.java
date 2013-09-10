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
import com.ssac.expro.kewen.bean.SearchBean;
import com.ssac.expro.kewen.bean.Theatre;
import com.ssac.expro.kewen.util.ImageCacheUtil;

public class Adapter4Search extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<SearchBean> list;
	 private lastIndexLoaded mLastLoad;
	 private Context c;
	    public Adapter4Search(Context mContext,List<SearchBean> list,lastIndexLoaded mLastLoad)
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
	    	if(paramView!=null&&paramView.findViewById(R.id.textTitleOfSearchItem)!=null){
				Log.d("getview-----saved", "doGetView-------get TextView-----------"+paramInt);
			}else{
				Log.d("getview", "doGetView-------new TextView-----------"+paramInt);
				//把xml布局文件变成View对象
				paramView=lin.inflate(R.layout.item_search, null);
			}
	    	final ViewHolder vh = new ViewHolder();
	    	vh.title		=	(TextView) paramView.findViewById(R.id.textTitleOfSearchItem);
			// 设置数据
	    	SearchBean Ninfo = list.get(paramInt);
			vh.title.setText(Ninfo.getTitle());
				
	    	}
	      return paramView;
	    }

	    private static class ViewHolder {
		 TextView title;
		}
	    
	    public interface lastIndexLoaded{
	    	void loadData();
	    }
}
