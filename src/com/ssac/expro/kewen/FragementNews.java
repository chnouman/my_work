package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList.lastIndexLoad;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.GetPicFromURL;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.util.MulitPointTouchListener;
import com.ssac.expro.kewen.view.SlideHolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * month art info
 * 
 * @author poe
 */
public class FragementNews extends Fragment implements OnClickListener {

	Art art = null;
	private ImageView imageLeft, imageView;
	private LinearLayout progressbar;
	private Context mContext;
	// 影片
	private SlideHolder mSlideHoler;

	public FragementNews(Context mContext, SlideHolder mSlideHoler) {
		super();
		this.mContext = mContext;
		this.mSlideHoler = mSlideHoler;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null) {
			return null;
		}
		container = (ViewGroup) inflater.inflate(R.layout.layout_art, null);

		progressbar = (LinearLayout) container.findViewById(R.id.progressOfArt);
		imageLeft = (ImageView) container.findViewById(R.id.imageLeftOfHeadArt);
		imageLeft.setOnClickListener(this);

		imageView = (ImageView) container.findViewById(R.id.imageOfArt);
		imageView.setOnTouchListener(new MulitPointTouchListener());
		// 通知服务获取ad数据
		task4Art ts = new task4Art();
		ts.execute();

		return container;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageLeftOfHeadArt:
			mSlideHoler.toggle();
			break;
		default:
			break;
		}
	}

	private class task4Art extends AsyncTask<String, String, String> {

		private List<Huizhan> hlist = new ArrayList<Huizhan>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				art = XmlToListService.GetArt(HttpUtil.sendGetRequest(Constants.MONTH_ART));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			progressbar.setVisibility(View.GONE);
			if (art != null && null != art.getImgPath()) {
				ImageCacheUtil ic = new ImageCacheUtil();
				ic.loadImageList(ExproApplication.imageLoader, imageView, art.getImgPath());
			}
			// progressbar.setVisibility(View.VISIBLE);
			if (mSlideHoler.isOpened()) {
				mSlideHoler.toggle();
			}
		}
	}
}
