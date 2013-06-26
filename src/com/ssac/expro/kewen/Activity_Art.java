package com.ssac.expro.kewen;

import java.util.HashMap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;

public class Activity_Art extends BaseActivity implements OnClickListener{

	private ImageView imageView;
	private LinearLayout lin_home,lin_art,lin_vip,lin_youhui,lin_more;
	private ImageView image;
	private LinearLayout progressbar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_art);
		
		
		init();
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		progressbar = (LinearLayout) findViewById(R.id.progressOfArt);
		imageView = (ImageView) findViewById(R.id.imageLeftOfHeadArt);
		image			=	(ImageView) findViewById(R.id.imageOfArt);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mSlideHolder.toggle();
			}
		});
		
		
		lin_home=	(LinearLayout) findViewById(R.id.linearHOme);
		lin_art	= 	(LinearLayout) findViewById(R.id.linearNews);
		lin_vip	=	(LinearLayout) findViewById(R.id.linearVip);
		lin_youhui	=	(LinearLayout) findViewById(R.id.linearYouHui);
		lin_more=	(LinearLayout) findViewById(R.id.linearMore);
		
		lin_home.setOnClickListener(this);
		lin_art.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_youhui.setOnClickListener(this);
		lin_more.setOnClickListener(this);
		
		// 通知服务获取ad数据
		HashMap<String, Object> hm = new HashMap<String, Object>();
		Task ts = new Task(TaskType.GET_ART, hm);
		MainService.newTask(ts);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		Art art=MainService.artList.get(0);
		
		AsyncImageLoader asyn = new AsyncImageLoader();
		asyn.loadDrawable(art.getImgPath(), new ImageCallback() {
			@Override
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				image.setImageBitmap(imageDrawable);
				progressbar.setVisibility(View.GONE);
			}
		}, "internet", FromType.home);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);
			break;
		case R.id.linearNews:
			break;
		case R.id.linearVip:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			break;
		case R.id.linearYouHui:
			ExproApplication.showBuildTip(Activity_Art.this);
			break;
		case R.id.linearMore:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
	}
	
}
