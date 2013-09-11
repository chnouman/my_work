package com.ssac.expro.kewen;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ssac.expro.kewen.adapter.Adapter4HomeFilm;
import com.ssac.expro.kewen.adapter.Adapter4HomeShow;
import com.ssac.expro.kewen.adapter.ImageAdapter4NumberGallery;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.ShowInfo;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.exception.DefaultExceptionHandler;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.VersionUpdateManager;
import com.ssac.expro.kewen.view.NumberDotImageView;
import com.ssac.expro.kewen.view.SlowFlipGallery;

public class Activity_Home extends BaseActivity implements OnClickListener {

	private LinearLayout lin_home, lin_yetai, lin_vip, lin_search, lin_more;
	private LinearLayout progressbar;
	private GestureDetector gestureDetector;
	private NumberDotImageView mDotImageView;
	private SlowFlipGallery mGallery;
	// private Gallery mGallery;
	private ImageAdapter4NumberGallery mImageAdapter;
	private BaseAdapter adapterShow,adapterFilm;
	private int postion = 0;
	private Context mContext;
	private Timer timer;
	//new add
	private TextView more1,more2;
	private Gallery galleryShow,galleryFilm;
	

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (postion >= MainService.adList.size()) {
				postion = 0;
			}
			mGallery.setSelection(postion, true);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(  
			       this.getApplicationContext()));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_home);
		mContext = this;
		if (!MainService.isrun) {
			Intent it = new Intent(Activity_Home.this, MainService.class);
			Activity_Home.this.startService(it);
		}

		// 版本升级检测
		if (HttpUtil.checkNet(this.getApplicationContext())) {
			VersionUpdateManager vm = new VersionUpdateManager();
			vm.CheckVersion(mContext);
		} else {
			ExproApplication.throwTips("网络环境有问题，请设置网络后重试！");
		}

		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (MainService.adList.size() > 0) {
			fillGallery();
		}
		
		if(MainService.showList.size()>0){
			fillGalleryShow();
		}
		
		if(MainService.filmList.size()>0){
			fillGalleryFilm();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (null != timer) {
			timer.cancel();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		ExproApplication.metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(ExproApplication.metrics);

		progressbar = (LinearLayout) findViewById(R.id.progressbarOfHome);
		lin_home = (LinearLayout) findViewById(R.id.linearHOme);
		lin_yetai = (LinearLayout) findViewById(R.id.linearYetai);
		lin_vip = (LinearLayout) findViewById(R.id.linearVip);
		lin_search = (LinearLayout) findViewById(R.id.linearSearch);
		lin_more = (LinearLayout) findViewById(R.id.linearMore);

		lin_home.setOnClickListener(this);
		lin_yetai.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_search.setOnClickListener(this);
		lin_more.setOnClickListener(this);

		// 下面点击button的效果
		more1 = (TextView) findViewById(R.id.txtMore1);
		more2 = (TextView) findViewById(R.id.txtMore2);
		more1.setOnClickListener(this);
		more2.setOnClickListener(this);

		this.gestureDetector = new GestureDetector(new DefaultGestureDetector());
		this.mDotImageView = ((NumberDotImageView) findViewById(R.id.dot_imageview));
		// this.mGallery = ((SlowFlipGallery)findViewById(R.id.gallery));
		this.mGallery = (SlowFlipGallery) findViewById(R.id.gallery);
		mGallery.setVisibility(View.VISIBLE);
		this.mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				if (!TextUtils.isEmpty(MainService.adList.get(arg2).getLinkAddress())) {
					Uri uri = Uri.parse(MainService.adList.get(arg2).getLinkAddress());
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(it);
				}
			}
		});
		this.mGallery.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				return Activity_Home.this.gestureDetector.onTouchEvent(paramMotionEvent);
			}
		});

		
		galleryShow = (Gallery) findViewById(R.id.galleryYanchuOfHome);
		galleryFilm 	= (Gallery) findViewById(R.id.galleryDianYingOfHome);
		
		galleryFilm.scrollBy(100, 0);
		galleryShow.scrollBy(100, 0);
		
		galleryShow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ShowInfo sinfo=MainService.showList.get(arg2);
				Intent intent =new Intent(mContext,TheatreYanchuDetail.class);
				intent.putExtra("filmID",sinfo.getDramaID() );
				intent.putExtra("img", sinfo.getTitleImage());
				intent.putExtra("title", sinfo.getDramaName());
				intent.putExtra("type", sinfo.getDramaType());
				intent.putExtra("time", sinfo.getShowTime());
				intent.putExtra("price", sinfo.getPrice());
				
				startActivity(intent);
			}
		});
		
		galleryFilm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Film f = MainService.filmList.get(arg2);
				Intent intent = new Intent(mContext, FilmDetail.class);
				intent.putExtra("filmName", f.getFilmName());
				intent.putExtra("img", f.getTitleImageName());
				intent.putExtra("daoyan", f.getProperty1());
				intent.putExtra("zhuyan", f.getProperty2());
				intent.putExtra("realeaseDate", f.getReleaseDte());
				intent.putExtra("filmID", f.getFilmID());

				startActivity(intent);
			}
		});
		// 通知服务获取ad数据
		HashMap<String, Object> hm = new HashMap<String, Object>();
		Task ts = new Task(TaskType.GET_HOME, hm);
		MainService.newTask(ts);
		progressbar.setVisibility(View.VISIBLE);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		//ad
		if (MainService.adList.size() > 0) {
			fillGallery();
		}
		
		//showinfo
		if(MainService.showList.size()>0){
			fillGalleryShow();
		}
		//film
		if(MainService.filmList.size()>0){
			fillGalleryFilm();
		}

		progressbar.setVisibility(View.GONE);
	}

	private void fillGalleryFilm() {
		if(null==adapterFilm){
			adapterFilm = new Adapter4HomeFilm(mContext);
			galleryFilm.setAdapter(adapterFilm);
		}else{
			adapterFilm.notifyDataSetChanged();
		}
	}

	private void fillGalleryShow() {
		if(null==adapterShow){
			adapterShow = new Adapter4HomeShow(mContext);
			galleryShow.setAdapter(adapterShow);
		}else{
			adapterShow.notifyDataSetChanged();
		}
		
	}

	// 给 gallery 放数据。
	public void fillGallery() {
		this.mImageAdapter = new ImageAdapter4NumberGallery(mContext);
		this.mGallery.setAdapter(this.mImageAdapter);
		final int i = MainService.adList.size();

		timer = new Timer();
		mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
				Activity_Home.this.mDotImageView.refresh(i, paramInt);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				postion++;
				handler.sendEmptyMessage(0);
			}
		}, 10 * 1000, 3000);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			break;
		case R.id.linearYetai:
			intent.setClass(this, Activity_Yetai.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearSearch:
			intent.setClass(this, Activity_Search.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		// 子选项的跳转
		case R.id.txtMore1:
			intent.putExtra("tag", "yanchu");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.txtMore2:
			intent.putExtra("tag", "dianying");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		default:
			break;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	class DefaultGestureDetector extends GestureDetector.SimpleOnGestureListener {
		int mFlag;

		DefaultGestureDetector() {
		}

		public boolean onDown(MotionEvent paramMotionEvent) {
			return false;
		}

		public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
			Log.e("mGallery.getSelectedItemId()", Activity_Home.this.mGallery.getSelectedItemId() + "");
			
			if(MainService.adList.size()>0){
				if ((paramMotionEvent1.getX() - paramMotionEvent2.getX() > 20.0F) && (Math.abs(paramFloat1) > 20.0F)) {
					Log.e("mGallery.getSelected----end", "over now goto next activity");
					postion--;
					mGallery.setSelection((postion + MainService.adList.size()) % MainService.adList.size());
				} else if ((paramMotionEvent2.getX() - paramMotionEvent1.getX() > 20.0F) && (Math.abs(paramFloat1) > 20.0F)) {
					Log.e("mGallery.getSelected----end", "over now goto next activity");
					postion++;
					mGallery.setSelection(postion % MainService.adList.size());
				}
				postion = (int) Activity_Home.this.mGallery.getSelectedItemId();
			}
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		MainService.promptExit(this);
		return super.onKeyDown(keyCode, event);
	}
}
