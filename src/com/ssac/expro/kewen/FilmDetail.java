package com.ssac.expro.kewen;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.FilmSchedule;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.AppUtil;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class FilmDetail extends Activity {

	private ImageView back, img_film;
	private TextView title;
	private TextView txt_daoy, txt_zhuy, txt_type, txt_shic, txt_released;
	private String filmID;// 当前电影标示
	private String filmName;//电影名字
	private WebView mWebView;// 电影介绍.
	private LinearLayout linear_Schedule,linear_Schedule2;
	private Context c;
	private RatingBar ratingbar;
	private Button btn_buy;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_film_detail);
		c=this;
		
		init();
	}

	// 初始化
	void init() {
		filmID = getIntent().getStringExtra("filmID");
		filmName = getIntent().getStringExtra("filmName");

		btn_buy	= (Button) findViewById(R.id.btnBuyOfFilmDetail);
		back = (ImageView) findViewById(R.id.imageLeftOfHeadFilmDetail);
		title = (TextView) findViewById(R.id.textNameOfHeadFilmDetail);
		img_film = (ImageView) findViewById(R.id.imageOfFileDetail);
		// txt_tjzs = findViewById(R.id.text)
		ratingbar	=	(RatingBar) findViewById(R.id.ratingBar1);
		txt_daoy = (TextView) findViewById(R.id.textdaoyanOfFilmDetail);
		txt_zhuy = (TextView) findViewById(R.id.textzhuyanOfFilmDetail);
		txt_type = (TextView) findViewById(R.id.textTypeOfFilmDetail);
		txt_shic = (TextView) findViewById(R.id.textPianChangOfFilmDetail);
		txt_released = (TextView) findViewById(R.id.textReleaseDateOfFilmDetail);
		mWebView = (WebView) findViewById(R.id.webviewOfFilmDetail);
		linear_Schedule = (LinearLayout) findViewById(R.id.linearFilmSchedule);
		linear_Schedule2 = (LinearLayout) findViewById(R.id.linearFilmSchedule2);
		
		btn_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//1.检测是否存在 苏艺影城app
				if(!AppUtil.checkInstall(FilmDetail.this.getApplicationContext())){
					
					new AlertDialog.Builder(FilmDetail.this)
					.setTitle("友情提示")
					.setMessage("购票需要下载苏艺影城客户端，是否前往下载？")
					.setPositiveButton("前往下载",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Uri uri = Uri.parse("http://apk.hiapk.com/html/2013/10/1889277.html?module=256&info=z4J6gnFfzlc%3D");    
							Intent it = new Intent(Intent.ACTION_VIEW, uri);      
							startActivity(it);    
						}
					}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).show();
				}else{
						PackageInfo pi =null;
						try {
							pi = getPackageManager().getPackageInfo("com.ykse.ticket.kewen", 0);
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
						resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
						resolveIntent.setPackage(pi.packageName);
						List<ResolveInfo> apps =  getPackageManager().queryIntentActivities(resolveIntent, 0);
						 
						ResolveInfo ri = apps.iterator().next();
						if (ri != null ) {
							String pName = ri.activityInfo.packageName;
							String className = ri.activityInfo.name;
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_LAUNCHER);
						ComponentName cn = new ComponentName(pName, className);
						intent.setComponent(cn);
						startActivity(intent);
						}
				}
		}
			}
			);
		
		
		
		
	//share
		findViewById(R.id.imageRightOfHeadFilmDetail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str=title.getText().toString()+"！导演："+txt_daoy.getText().toString()+" 主演："
				+txt_zhuy.getText().toString()+" 发布日期："+txt_released.getText().toString();
				Intent intent_share=new Intent(Intent.ACTION_SEND);
				intent_share.setType("text/plain");
				intent_share.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent_share.putExtra(Intent.EXTRA_TEXT, str+",详见苏州文化艺术中心:http://www.sscac.com.cn(分享自苏州文化艺术中心安卓客户端)");
				startActivity(Intent.createChooser(intent_share, getTitle()));
			}
		});
		
		// 获取更详细的数据
		task4FilmDetail ts = new task4FilmDetail();
		ts.execute();
		
		//拍片信息
		task4FilmSchedule tfs=new task4FilmSchedule();
		tfs.execute();
		
		task4FilmSchedule2 tfs2=new task4FilmSchedule2();
		tfs2.execute();
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
				film = XmlToListService.GetFilmDetail(HttpUtil.sendGetRequest( Constants.DIANYING_LIST + filmID));

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
				// get date
				title.setText(film.getFilmName());
				txt_daoy.setText(film.getProperty1());
				txt_zhuy.setText(film.getProperty2());
				txt_released.setText(film.getReleaseDte());
				txt_type.setText(film.getType());
				txt_shic.setText(film.getTotalTime() + " 分钟");
				
				ImageCacheUtil ic =new ImageCacheUtil();
				ic.loadImageList(ExproApplication.imageLoader, img_film, film.getTitleImageName());
				
				mWebView.loadDataWithBaseURL(null, film.getFilmDesc(), "text/html", "utf-8", null);
				if(film.getStar()!=null&&Float.parseFloat(film.getStar())>0){
					
					ratingbar.setRating(Float.parseFloat(film.getStar()));
					
				}
			}
		}
	}

	// 苏艺电影院排片信息
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
				sList = XmlToListService.GetFilmScheduleList(HttpUtil.sendGetRequest( 
						Constants.DIANYING_SCHEDUL +"0/"+ URLEncoder.encode(filmName)));
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
				int lineNumber=0;
				if(width>=800){//一行放六个
					lineNumber=sList.size()%6+1;
				}else{//<800 一行放4个
					lineNumber=sList.size()%4+1;
				}
				LinearLayout [] linears=new LinearLayout[lineNumber];
				for(int i=0;i<lineNumber;i++){
					linears[i] = new LinearLayout(c);
					linears[i].setOrientation(LinearLayout.HORIZONTAL);
					linears[i].setPadding(10,10,10,10);
					
					for(int j=0;j<((sList.size()-6*i)>6?6:(sList.size()-6*i));j++){
						
						final FilmSchedule fs=sList.get(i*6+j);
						TextView tv=new TextView(c);
						tv.setBackgroundResource(R.drawable.dy_schedul);
						tv.setText(fs.getTime());
//						tv.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Uri uri = Uri.parse(fs.getLink());    
//								Intent it   = new Intent(Intent.ACTION_VIEW,uri);    
//								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								c.startActivity(it);   
//							}
//						});
						
						linears[i].addView(tv);
					}
				}
				//添加到linearschedule
				for(int t=0;t<lineNumber;t++){
					linear_Schedule.addView(linears[t], t);
				}
			}
		}
	}
	
//东沙湖电影院排片信息
private class task4FilmSchedule2 extends AsyncTask<String, String, String> {
		
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
				sList = XmlToListService.GetFilmScheduleList(HttpUtil.sendGetRequest( 
						Constants.DIANYING_SCHEDUL +"1/"+ URLEncoder.encode(filmName)));
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
				int lineNumber=0;
				if(width>=800){//一行放六个
					lineNumber=sList.size()%6+1;
				}else{//<800 一行放4个
					lineNumber=sList.size()%4+1;
				}
				LinearLayout [] linears=new LinearLayout[lineNumber];
				for(int i=0;i<lineNumber;i++){
					linears[i] = new LinearLayout(c);
					linears[i].setOrientation(LinearLayout.HORIZONTAL);
					linears[i].setPadding(10,10,10,10);
					
					for(int j=0;j<((sList.size()-6*i)>6?6:(sList.size()-6*i));j++){
						
						final FilmSchedule fs=sList.get(i*6+j);
						TextView tv=new TextView(c);
						tv.setBackgroundResource(R.drawable.dy_schedul);
						tv.setText(fs.getTime());
//						tv.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Uri uri = Uri.parse(fs.getLink());    
//								Intent it   = new Intent(Intent.ACTION_VIEW,uri);    
//								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								c.startActivity(it);   
//							}
//						});
						
						linears[i].addView(tv);
					}
				}
				//添加到linearschedule
				for(int t=0;t<lineNumber;t++){
					linear_Schedule2.addView(linears[t], t);
				}
			}
		}
	}
}
