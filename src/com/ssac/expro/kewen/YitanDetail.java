package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.List;

import com.ssac.expro.kewen.FragementFilm.ImageAdapter;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YitanDetail extends Activity {

	private ImageView back,img_film;
	private TextView txt_title,txt_title2,txt_effect_time;
	private String filmID;//当前电影标示
	private WebView mWebView;//电影介绍
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_yitan_detail);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadYitanDetail);
		img_film	=	(ImageView) findViewById(R.id.imageOfLayoutYitanDetail);
//		txt_tjzs	=	findViewById(R.id.text)
		txt_title	=	(TextView) findViewById(R.id.textTitleOfLayoutYitanDetail);
		txt_title2	=	(TextView) findViewById(R.id.textTitle2OfLayoutYitanDetail);
		txt_effect_time	=	(TextView) findViewById(R.id.textTimeOfLayoutYitanDetail);
		
		mWebView=	(WebView) findViewById(R.id.webviewOfLayoutYitanDetail);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//get date
		filmID	= getIntent().getStringExtra("contentID");
		String img=getIntent().getStringExtra("img");
		String titlename=getIntent().getStringExtra("title");
		
		txt_title.setText(titlename);
		
		final String str=titlename;
		//share
			findViewById(R.id.imageRightOfHeadYitanDetail).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent_share=new Intent(Intent.ACTION_SEND);
					intent_share.setType("text/plain");
					intent_share.putExtra(Intent.EXTRA_SUBJECT, "分享");
					intent_share.putExtra(Intent.EXTRA_TEXT, str+",详见苏州文化艺术中心:http://www.sscac.com.cn(分享自苏州文化艺术中心安卓客户端)");
					startActivity(Intent.createChooser(intent_share, getTitle()));
				}
			});
		ImageCacheUtil ic =new ImageCacheUtil();
		ic.loadImageList(ExproApplication.imageLoader, img_film, img);
		//获取更详细的数据
		task4YitanDetail ts =new task4YitanDetail();
		ts.execute();
	}
	
	//去获取coverflow所需要得广告图片
		private class task4YitanDetail extends AsyncTask<String, String, String> {

			private ArtLesson  film;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
//				progressbar.setVisibility(View.VISIBLE);
				ExproApplication.throwTips("加载演出资讯...");
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					film = XmlToListService.GetYiwenKetangDetail(HttpUtil.sendGetRequest(
							Constants.JUYUAN_ACTIVITIES_DETAIL+filmID));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("poe", "sax解析出错！"+e.getMessage());
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.i("poe", "获取的数据如下"+film);
				// 处理结果
				if(film!=null){
						txt_effect_time.setText(film.getEffectTime());
						mWebView.loadDataWithBaseURL(null, film.getDesc(), "text/html", "utf-8", null);
				}
			}
		}
}
