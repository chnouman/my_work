package com.ssac.expro.kewen;

import java.io.IOException;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.bean.Theatre;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class TheatreActivitiesDetail extends Activity {

	private ImageView back,img;
	private TextView title;
	private TextView txt_released,txt_title;
	private String contentID;//当前电影标示
	private WebView mWebView;//电影介绍
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_theatre_activities_detail);
		
		init();
	}
	
	//初始化
	void init(){
		
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadTheatreDetail);
		title		=	(TextView) findViewById(R.id.textOfHeadTheatreDetail);
		txt_title=(TextView) findViewById(R.id.textTitleOfTheatreActivitiesDetail);
		img	=	(ImageView) findViewById(R.id.imageOfTheatreActivitiesDetail);
		txt_released=	(TextView) findViewById(R.id.textTimeOfTheatreActivitiesDetail);	
		mWebView=	(WebView) findViewById(R.id.webviewOfTheatreActivitiesDetail);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//get date
		contentID	= getIntent().getStringExtra("contentID");
		String str_img=getIntent().getStringExtra("img");
		String titlename=getIntent().getStringExtra("title");
		String releaseDate=getIntent().getStringExtra("time");
		title.setText("剧院活动详情");
		txt_title.setText(titlename);
		txt_released.setText(releaseDate);
		
		ImageCacheUtil ic =new ImageCacheUtil();
		ic.loadImageList(ExproApplication.imageLoader, img, str_img);
		/*
		AsyncImageLoader async =new AsyncImageLoader();
		async.loadDrawable(str_img, new ImageCallback() {
			
			@Override
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				img.setImageBitmap(imageDrawable);
			}
		}, "internet", FromType.home);*/
		
		//获取更详细的数据
		task4FilmDetail ts =new task4FilmDetail();
		ts.execute();
	}
	
	//去获取coverflow所需要得广告图片
		private class task4FilmDetail extends AsyncTask<String, String, String> {

			private Theatre  film;
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
					film = XmlToListService.GetTheatreDetail(HttpUtil.sendGetRequest(null,
							Constants.JUYUAN_ACTIVITIES_DETAIL+contentID));

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
				// 处理结果
				if(film!=null){
						mWebView.loadDataWithBaseURL(null, film.getDesc(), "text/html", "utf-8", null);
				}
			}
		}
}
