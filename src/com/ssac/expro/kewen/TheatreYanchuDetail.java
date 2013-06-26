package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.List;

import com.ssac.expro.kewen.FragementFilm.ImageAdapter;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.bean.ShowInfo;
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

public class TheatreYanchuDetail extends Activity {

	private ImageView back,img_theatre;
	private TextView title,txt_type,txt_time,txt_price,txt_company;
	private String filmID;//当前电影标示
	private WebView mWebView;//电影介绍
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_theatre_detail);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadTheatreDetail);
		title		=	(TextView) findViewById(R.id.textTitleOfTheatreDetail);
		img_theatre	=	(ImageView) findViewById(R.id.imageOfTheatreDetail);
		txt_type	=	(TextView) findViewById(R.id.textTypeOfTheatreDetail);
		txt_time	=	(TextView) findViewById(R.id.textTimeOfTheatreDetail);
		txt_price	=	(TextView) findViewById(R.id.textPriceOfTheatreDetail);
		txt_company	=	(TextView) findViewById(R.id.textCompanyOfTheatreDetail);
		mWebView=	(WebView) findViewById(R.id.webviewOfTheatreDetail);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//get date
		filmID	= getIntent().getStringExtra("filmID");
		String img=getIntent().getStringExtra("img");
		String titlename=getIntent().getStringExtra("titleName");
		String type=getIntent().getStringExtra("type");
		String time=getIntent().getStringExtra("time");
		String price=getIntent().getStringExtra("price");
		
		title.setText(titlename);
		txt_type.setText(type);
		txt_time.setText(time);
		txt_price.setText(price);
		
		AsyncImageLoader async =new AsyncImageLoader();
		async.loadDrawable(img, new ImageCallback() {
			
			@Override
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				img_theatre.setImageBitmap(imageDrawable);
			}
		}, "internet", FromType.home);
		
		//获取更详细的数据
		task4FilmDetail ts =new task4FilmDetail();
		ts.execute();
	}
	
	//去获取coverflow所需要得广告图片
		private class task4FilmDetail extends AsyncTask<String, String, String> {

			private ShowInfo  showinfo;
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
					showinfo = XmlToListService.GetShowInfoDetail(HttpUtil.sendGetRequest(null,
							Constants.YANCHU_ZIXUN+filmID));
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
				if(showinfo!=null){
						txt_company.setText(showinfo.getPublishCompany());
						mWebView.loadDataWithBaseURL(null, 
								showinfo.getDesc(), "text/html", "utf-8", null);
				}
			}
		}
}