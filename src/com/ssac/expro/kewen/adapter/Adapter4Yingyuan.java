package com.ssac.expro.kewen.adapter;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.Yingyuan;

public class Adapter4Yingyuan extends BaseAdapter {

	private LayoutInflater lin;
	private List<Yingyuan> list;
	private Context c;
	public Adapter4Yingyuan(Context mContext, List<Yingyuan> list
			) {
		c = mContext;
		lin = LayoutInflater.from(mContext);
		this.list = list;
	}

	public void addNewData() {

		this.notifyDataSetChanged();

	}

	public int getCount() {
		return list.size() ;
	}

	public Object getItem(int paramInt) {
			return list.get(paramInt);
	}

	public long getItemId(int paramInt) {
			return list.indexOf(getItem(paramInt));// >getCount()?paramInt%getCount():paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		// if load the last index
			if (paramView != null
					&& paramView.findViewById(R.id.texttitleOfYingyuanItem) != null) {
				Log.d("getview-----saved",
						"doGetView-------get TextView-----------" + paramInt);
			} else {
				Log.d("getview", "doGetView-------new TextView-----------"
						+ paramInt);
				// 把xml布局文件变成View对象
				paramView = lin.inflate(R.layout.item_yingyuan, null);
			}
			
			Yingyuan yingyuan =list.get(paramInt);
			final ViewHolder vh = new ViewHolder();
			vh.title = (TextView) paramView
					.findViewById(R.id.texttitleOfYingyuanItem);
			vh.title.setText(yingyuan.getName());
			
		return paramView;
	}

	private static class ViewHolder {
		TextView title;
	}

}
