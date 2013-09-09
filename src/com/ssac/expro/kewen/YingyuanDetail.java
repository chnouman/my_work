package com.ssac.expro.kewen;

import java.io.IOException;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.TimeUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class YingyuanDetail extends Activity implements OnClickListener{

	private ImageView back;
	private ListView listView;
	private String content;
	private TextView text1,text2,text3,title;//今天、明天、后天
	
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
		title		=	(TextView) findViewById(R.id.textNameOfHeadYingyuanDetail);
		listView=	(ListView) findViewById(R.id.listviewOfLayoutYingyuanDetail);
		//今明后
		text1	=	(TextView) findViewById(R.id.txtTodayOfHeadYingyuanDetail);
		text2	=	(TextView) findViewById(R.id.txtNextdayOfHeadYingyuanDetail);
		text3	=	(TextView) findViewById(R.id.txtHoutianOfHeadYingyuanDetail);
		
		back.setOnClickListener(this);
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		
		//获取当前日期
		setDate();
		//获取更详细的数据
//		task4Detail ts =new task4Detail();
//		ts.execute();
	}
	
	private void setDate() {
		// TODO Auto-generated method stub
		text1.setText(TimeUtil.getDate(0));
		text2.setText(TimeUtil.getDate(1));
		text3.setText(TimeUtil.getDate(2));
	}

	private void updateTextBg(int pos){
		
		switch (pos) {
		case 0:
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			break;
		case 1:
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			break;
		case 2:
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			break;
		default:
			break;
		}
	}
	//去获取coverflow所需要得广告图片
//		private class task4Detail extends AsyncTask<String, String, String> {
//
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
////				progressbar.setVisibility(View.VISIBLE);
//				ExproApplication.throwTips("加载演出资讯...");
//			}
//
//			@Override
//			protected String doInBackground(String... params) {
//				// TODO Auto-generated method stub
//				try {
//					content =XmlToListService.getString(HttpUtil.sendGetRequest(
//							Constants.YINGCHENG_DESCRIPTION));
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Log.e("poe", "sax解析出错！"+e.getMessage());
//				}
//
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(result);
//				Log.i("poe", content);
//				// 处理结果
//				if(content!=null){
//						mWebView.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//				}
//			}
//		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageLeftOfHeadYingyuanDetail:
			finish();
			break;
		case	R.id.txtTodayOfHeadYingyuanDetail:
			
			break;
		case R.id.txtNextdayOfHeadYingyuanDetail:
			
			break;
		case R.id.txtHoutianOfHeadYingyuanDetail:
			
			break;
		default:
			break;
		}
	}
}
