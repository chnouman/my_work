package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.List;

import com.ssac.expro.kewen.adapter.Adapter4YingyuanSchedule;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class YingyuanDetail extends Activity implements OnClickListener{

	private ImageView back;
	private ListView listView;
	private List<Film> contentList;
	private TextView text1,text2,text3,title;//今天、明天、后天
	private ProgressBar progressbar;
	private String from,code;
	private BaseAdapter adapter;
	
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
		from = getIntent().getStringExtra("from");
		code = getIntent().getStringExtra("code");
		progressbar = 	(ProgressBar) findViewById(R.id.progressBarOfYingyuanDetail);
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadYingyuanDetail);
		title		=	(TextView) findViewById(R.id.textNameOfHeadYingyuanDetail);
		listView=	(ListView) findViewById(R.id.listviewOfLayoutYingyuanDetail);
		
		title.setText(from);
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
	}
	
	private void setDate() {
		// TODO Auto-generated method stub
		text1.setText(TimeUtil.getDate(0));
		text2.setText(TimeUtil.getDate(1));
		text3.setText(TimeUtil.getDate(2));
		
		new task4Detail().execute(text1.getText().toString());
	}

	private void updateTextBg(int pos){
		
		switch (pos) {
		case 0:
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			new task4Detail().execute(text1.getText().toString());
			break;
		case 1:
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			new task4Detail().execute(text2.getText().toString());
			break;
		case 2:
			text3.setBackgroundResource(R.drawable.tab_film_yingyuan_date1);
			text2.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			text1.setBackgroundResource(R.drawable.tab_film_yingyuan_date2);
			new task4Detail().execute(text3.getText().toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageLeftOfHeadYingyuanDetail:
			finish();
			break;
		case	R.id.txtTodayOfHeadYingyuanDetail:
			updateTextBg(0);
			break;
		case R.id.txtNextdayOfHeadYingyuanDetail:
			updateTextBg(1);
			break;
		case R.id.txtHoutianOfHeadYingyuanDetail:
			updateTextBg(2);
			break;
		default:
			break;
		}
	}
	
	//去获取coverflow所需要得广告图片
	private class task4Detail extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			String date=params[0];
			try {
				contentList =XmlToListService.GetFilms4Schedule(HttpUtil.sendGetRequest(
						Constants.YINGYUAN_PAIPIAN_DETAIL+"/"+code+"/"+date));
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
			if(contentList!=null&&contentList.size()>0)	{
					adapter=new Adapter4YingyuanSchedule(YingyuanDetail.this.getApplicationContext(), contentList);
					listView.setAdapter(adapter);
			}
			
			progressbar.setVisibility(View.GONE);
		}
	}

}
