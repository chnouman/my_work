package com.ssac.expro.kewen;

import java.io.IOException;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class YingyuanActivitiesDetail extends Activity {

	private ImageView back,img_film;
	private TextView txt_title,txt_title2,txt_effect_time;
	private String filmID;//当前电影标示
	private WebView mWebView;//电影介绍
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_yingyuan_activities_detail);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadYingyuanActivitiesDetail);
		img_film	=	(ImageView) findViewById(R.id.imageOfLayoutYingyuanActivitiesDetail);
//		txt_tjzs	=	findViewById(R.id.text)
		txt_title	=	(TextView) findViewById(R.id.textTitleOfLayoutYingyuanActivitiesDetail);
		txt_title2	=	(TextView) findViewById(R.id.textTitle2OfLayoutYingyuanActivitiesDetail);
		txt_effect_time	=	(TextView) findViewById(R.id.textTimeOfLayoutYingyuanActivitiesDetail);
		
		mWebView=	(WebView) findViewById(R.id.webviewOfLayoutYingyuanActivitiesDetail);
		
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
		
		ImageCacheUtil ic =new ImageCacheUtil();
		ic.loadImageList(ExproApplication.imageLoader, img_film, img);
		
		//获取更详细的数据
		task4HuodongDetail ts =new task4HuodongDetail();
		ts.execute();
	}
	
	//去获取coverflow所需要得广告图片
		private class task4HuodongDetail extends AsyncTask<String, String, String> {

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
					film = XmlToListService.GetYiwenKetangDetail(HttpUtil.sendGetRequest(null,
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
