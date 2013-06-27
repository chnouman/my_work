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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import com.ssac.expro.kewen.adapter.ImageAdapter4NumberGallery;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.view.NumberDotImageView;
import com.ssac.expro.kewen.view.SlowFlipGallery;

public class Activity_Home extends BaseActivity implements OnClickListener {

	private LinearLayout lin_home, lin_art, lin_vip, lin_youhui, lin_more;
	private LinearLayout lin_yc, lin_dy, lin_hz, lin_px, lin_msg, lin_yt;
	private LinearLayout progressbar;
	private GestureDetector gestureDetector;
	private NumberDotImageView mDotImageView;
//	private SlowFlipGallery mGallery;
	private SlowFlipGallery mGallery;
	private ImageAdapter4NumberGallery mImageAdapter;
	private int postion = 0;
	private Context mContext;

	private Timer timer;

	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(postion>=MainService.adList.size()){
				postion=0;
			}
			mGallery.setSelection(postion, true);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_home);

		mContext = this;
		if (!MainService.isrun) {
			Intent it = new Intent(Activity_Home.this, MainService.class);
			Activity_Home.this.startService(it);
		}
		init();
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mImageAdapter!=null){
			mGallery.setAdapter(mImageAdapter);
			mImageAdapter.addNewData();
		}else{
			init();
		}
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		ExproApplication.metrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(ExproApplication.metrics);
		
		progressbar = (LinearLayout) findViewById(R.id.progressbarOfHome);
		lin_home = (LinearLayout) findViewById(R.id.linearHOme);
		lin_art = (LinearLayout) findViewById(R.id.linearNews);
		lin_vip = (LinearLayout) findViewById(R.id.linearVip);
		lin_youhui = (LinearLayout) findViewById(R.id.linearYouHui);
		lin_more = (LinearLayout) findViewById(R.id.linearMore);

		lin_home.setOnClickListener(this);
		lin_art.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_youhui.setOnClickListener(this);
		lin_more.setOnClickListener(this);

		// 下面点击button的效果
		lin_yc = (LinearLayout) findViewById(R.id.linearYanChuOfHome);
		lin_dy = (LinearLayout) findViewById(R.id.linearDianYingOfHome);
		lin_hz = (LinearLayout) findViewById(R.id.linearHuiZhanOfHome);
		lin_px = (LinearLayout) findViewById(R.id.linearPeiXunOfHome);
		lin_msg = (LinearLayout) findViewById(R.id.linearMeiShuOfHome);
		lin_yt = (LinearLayout) findViewById(R.id.linearYiTanOfHome);

		lin_hz.setOnClickListener(this);
		lin_px.setOnClickListener(this);
		lin_msg.setOnClickListener(this);
		lin_yt.setOnClickListener(this);
		lin_yc.setOnClickListener(this);
		lin_dy.setOnClickListener(this);
		
		this.gestureDetector = new GestureDetector(new DefaultGestureDetector());
	    this.mDotImageView = ((NumberDotImageView)findViewById(R.id.dot_imageview));
	    this.mGallery = ((SlowFlipGallery)findViewById(R.id.gallery));
		
	    this.mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(MainService.adList.get(arg2).getLinkAddress());    
				Intent it   = new Intent(Intent.ACTION_VIEW,uri);    
				mContext.startActivity(it);   	
			}
		});
		  this.mGallery.setOnTouchListener(new View.OnTouchListener()
		    {
		      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
		      {
		        return Activity_Home.this.gestureDetector.onTouchEvent(paramMotionEvent);
		      }
		    });
		  
		  
		  
		// 通知服务获取ad数据
			HashMap<String, Object> hm = new HashMap<String, Object>();
			Task ts = new Task(TaskType.GET_HOME, hm);
			MainService.newTask(ts);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		if (MainService.adList.size() > 0) {
			fillGallery();
		}
		
		progressbar.setVisibility(View.GONE);
	}

	

	 //给 gallery 放数据。
	  public void fillGallery() {
	    this.mImageAdapter = new ImageAdapter4NumberGallery(mContext);
	    this.mGallery.setAdapter(this.mImageAdapter);
	    final int i = MainService.adList.size();
	    
	    timer = new Timer();
	    mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	      public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
	      {
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
		},  10*1000, 3000);
	    
	  }
	  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
//			intent.setClass(this, Activity_Home.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearNews:
			intent.setClass(this, Activity_Art.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearYouHui:
//			intent.setClass(this, Activity_Map.class);
			ExproApplication.showBuildTip(mContext);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		// 子选项的跳转
		case R.id.linearYanChuOfHome:
			intent.putExtra("tag", "yanchu");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearDianYingOfHome:
			intent.putExtra("tag", "dianying");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearHuiZhanOfHome:
			intent.putExtra("tag", "huizhan");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearPeiXunOfHome:
		case R.id.linearMeiShuOfHome:
			//弹出提示框
			ExproApplication.showBuildTip(mContext);
			break;
		case R.id.linearYiTanOfHome:
			intent.putExtra("tag", "yitan");
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
	
	class DefaultGestureDetector extends GestureDetector.SimpleOnGestureListener
	  {
	    int mFlag;

	    DefaultGestureDetector()
	    {
	    }

	    public boolean onDown(MotionEvent paramMotionEvent)
	    {
	      return false;
	    }
	    
	    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	    {
	      Log.e("mGallery.getSelectedItemId()", Activity_Home.this.mGallery.getSelectedItemId() + "");
	      
	      Log.e("mGallery.getSelected-----position", postion+"");
	    	  if( (paramMotionEvent1.getX() - paramMotionEvent2.getX() > 20.0F)
	    		  && (Math.abs(paramFloat1) > 20.0F)){
	    		  Log.e("mGallery.getSelected----end", "over now goto next activity");
	    		  postion--;
	    		  mGallery.setSelection((postion+MainService.adList.size())%MainService.adList.size());
	    	  }else  if( (paramMotionEvent2.getX() - paramMotionEvent1.getX() > 20.0F)
		    		  && (Math.abs(paramFloat1) > 20.0F)){
		    		  Log.e("mGallery.getSelected----end", "over now goto next activity");
		    		  postion++;
		    		  mGallery.setSelection(postion%MainService.adList.size());
		    	  }
	      postion = (int) Activity_Home.this.mGallery.getSelectedItemId();
	      return true;
	    }
	  }
	

}
