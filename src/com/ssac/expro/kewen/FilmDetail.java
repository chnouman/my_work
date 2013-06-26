package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.List;

import com.ssac.expro.kewen.FragementFilm.ImageAdapter;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.FilmSchedule;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;
import android.app.Activity;
import android.content.Context;
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

public class FilmDetail extends Activity {

	private ImageView back, img_film;
	private TextView title;
	private TextView txt_daoy, txt_zhuy, txt_type, txt_shic, txt_released;
	private String filmID;// 当前电影标示
	private WebView mWebView;// 电影介绍

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_film_detail);

		init();
	}

	// 初始化
	void init() {
		filmID = getIntent().getStringExtra("filmID");

		back = (ImageView) findViewById(R.id.imageLeftOfHeadFilmDetail);
		title = (TextView) findViewById(R.id.textNameOfHeadFilmDetail);
		img_film = (ImageView) findViewById(R.id.imageOfFileDetail);
		// txt_tjzs = findViewById(R.id.text)
		txt_daoy = (TextView) findViewById(R.id.textdaoyanOfFilmDetail);
		txt_zhuy = (TextView) findViewById(R.id.textzhuyanOfFilmDetail);
		txt_type = (TextView) findViewById(R.id.textTypeOfFilmDetail);
		txt_shic = (TextView) findViewById(R.id.textPianChangOfFilmDetail);
		txt_released = (TextView) findViewById(R.id.textReleaseDateOfFilmDetail);
		mWebView = (WebView) findViewById(R.id.webviewOfFilmDetail);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// get date
		String img = getIntent().getStringExtra("img");
		String titlename = getIntent().getStringExtra("filmName");
		String daoyan = getIntent().getStringExtra("daoyan");
		String zhuyan = getIntent().getStringExtra("zhuyan");
		// String type =getIntent().getStringExtra("type");
		// String pianchang =getIntent().getStringExtra("painchang");
		String releaseDate = getIntent().getStringExtra("realeaseDate");

		title.setText(titlename);
		txt_daoy.setText(daoyan);
		txt_zhuy.setText(zhuyan);
		txt_released.setText(releaseDate);

		AsyncImageLoader async = new AsyncImageLoader();
		async.loadDrawable(img, new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				img_film.setImageBitmap(imageDrawable);
			}
		}, "internet", FromType.home);

		// 获取更详细的数据
		task4FilmDetail ts = new task4FilmDetail();
		ts.execute();
	}

	// 去获取coverflow所需要得广告图片
	private class task4FilmDetail extends AsyncTask<String, String, String> {

		private Film film;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载演出资讯...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				film = XmlToListService.GetFilmDetail(HttpUtil.sendGetRequest(null, Constants.DIANYING_LIST + filmID));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			if (film != null) {
				txt_type.setText(film.getType());
				txt_shic.setText(film.getTotalTime() + " 分钟");
				mWebView.loadDataWithBaseURL(null, film.getFilmDesc(), "text/html", "utf-8", null);
			}
		}
	}

	// 去获取coverflow所需要得广告图片
	private class task4FilmSchedule extends AsyncTask<String, String, String> {
		
		private List<FilmSchedule> sList;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载演出资讯...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				sList = XmlToListService.GetFilmScheduleList(HttpUtil.sendGetRequest(null, Constants.DIANYING_SCHEDUL + filmID));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			if (sList != null&&sList.size()>0) {
				//生成可以使用的linearlayout
				
				//check the device's width
				int width=ExproApplication.metrics.widthPixels;
				if(width>=800){
					
					
				}else{//<800
					
					
				}
			
			}
		}
	}
}
