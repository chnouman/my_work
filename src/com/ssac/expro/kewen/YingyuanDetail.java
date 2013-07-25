package com.ssac.expro.kewen;

import java.io.IOException;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

public class YingyuanDetail extends Activity {

	private ImageView back;
	private WebView mWebView;//电影介绍
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_yingyuan_detail);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadYingyuanDetail);
		
		mWebView=	(WebView) findViewById(R.id.webviewOfLayoutYingyuanDetail);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//获取更详细的数据
		task4Detail ts =new task4Detail();
		ts.execute();
	}
	
	//去获取coverflow所需要得广告图片
		private class task4Detail extends AsyncTask<String, String, String> {

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
					content =XmlToListService.getString(HttpUtil.sendGetRequest(null,
							Constants.YINGCHENG_DESCRIPTION));

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
				Log.i("poe", content);
				// 处理结果
				if(content!=null){
						mWebView.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
				}
			}
		}
}
