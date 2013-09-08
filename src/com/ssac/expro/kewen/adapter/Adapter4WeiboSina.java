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
import com.ssac.expro.kewen.bean.WeiboSina;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.ImageCacheUtil;

public class Adapter4WeiboSina extends BaseAdapter {

	 private LayoutInflater lin;
	 private List<WeiboSina> list;
	 private Context c;

		public Adapter4WeiboSina(Context mContext, List<WeiboSina> list) {
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
			return list.indexOf(getItem(paramInt));// >getCount()?paramInt%getCount():paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			if (paramView != null && paramView.findViewById(R.id.imgHeaderIconOfSinaWeiboItem) != null) {
				Log.d("getview-----saved", "doGetView-------get TextView-----------" + paramInt);
			} else {
				Log.d("getview", "doGetView-------new TextView-----------" + paramInt);
				// 把xml布局文件变成View对象
				paramView = lin.inflate(R.layout.item_sina_weibo, null);
			}
			final ViewHolder vh = new ViewHolder();
			vh.image 			= (ImageView) paramView.findViewById(R.id.imgHeaderIconOfSinaWeiboItem);
			vh.title 			= (TextView) paramView.findViewById(R.id.textTitleOfSinaWeiboItem);
			vh.content		= (TextView) paramView.findViewById(R.id.textContentOfSinaWeiboItem);
			vh.imageDes 	= (ImageView) paramView.findViewById(R.id.imgDescriptionOfSinaWeiboItem);
			// 设置数据
			WeiboSina Ninfo = list.get(paramInt);
			ImageCacheUtil ic = new ImageCacheUtil();
			// 头像
			String imageUrl = Ninfo.getUser().getProfile_image_url();
			ic.loadImageList(ExproApplication.imageLoader, vh.image, imageUrl);
			//内容
			vh.title.setText(Ninfo.getUser().getScreen_name());
			vh.content.setText(Ninfo.getText());
			//内容图片
			String imageurl2=Ninfo.getThumbnail_pic();
			ic.loadImageList(ExproApplication.imageLoader, vh.imageDes, imageurl2);
			
			return paramView;
		}

		private static class ViewHolder {
			ImageView image;
			ImageView imageDes;
			TextView title;
			TextView content;
		}
}
