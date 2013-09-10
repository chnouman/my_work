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
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.util.ImageCacheUtil;

public class Adapter4YingyuanSchedule extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<Film> list;
	 private Context c;

		public Adapter4YingyuanSchedule(Context mContext,List<Film> list) {
			c = mContext;
			lin = LayoutInflater.from(c);
			this.list = list;
		}

		public void addNewData() {

			this.notifyDataSetChanged();

		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int paramInt) {
			return list.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return list.indexOf(getItem(paramInt));
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			if (paramView != null && paramView.findViewById(R.id.imageOfYingyuanDetailItem) != null) {
				Log.d("getview-----saved", "doGetView-------get TextView-----------" + paramInt);
			} else {
				Log.d("getview", "doGetView-------new TextView-----------" + paramInt);
				// 把xml布局文件变成View对象
				paramView = lin.inflate(R.layout.item_yingyuan_detai, null);
			}
			final ViewHolder vh = new ViewHolder();
			vh.image 			=	 	(ImageView) paramView.findViewById(R.id.imageOfYingyuanDetailItem);
			vh.title			 	= 		(TextView) paramView.findViewById(R.id.textTitleOfYingyuanDetailItem);
			vh.content		=		(TextView) paramView.findViewById(R.id.textContentOfYingyuanDetailItem);
			// 设置数据
			Film Ninfo = list.get(paramInt);
			// 取图片
			String imageUrl = Ninfo.getTitleImageName();
			ImageCacheUtil ic = new ImageCacheUtil();
			ic.loadImageList(ExproApplication.imageLoader, vh.image, imageUrl);
			vh.title.setText(Ninfo.getFilmName());
			String contents=Ninfo.getSchedule().replaceAll(","," | ");
			vh.content.setText(contents);
			return paramView;
		}

		private static class ViewHolder {
			ImageView image;
			TextView title;
			TextView content;
		}
}
