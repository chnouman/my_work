package com.ssac.expro.kewen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Activity_Yetai extends BaseActivity implements OnClickListener{

	private LinearLayout lin_home, lin_yetai, lin_vip, lin_search, lin_more;
	private Context c;
	private ImageView img_show,img_film,img_yitan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_yetai);
		c=this;
		
		init();
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		lin_home=	(LinearLayout) findViewById(R.id.linearHOme);
		lin_yetai	= 	(LinearLayout) findViewById(R.id.linearYetai);
		lin_vip	=	(LinearLayout) findViewById(R.id.linearVip);
		lin_search	=	(LinearLayout) findViewById(R.id.linearSearch);
		lin_more=	(LinearLayout) findViewById(R.id.linearMore);
		
		
		img_show		=	(ImageView) findViewById(R.id.imgShowOfYetai);
		img_film		=	(ImageView) findViewById(R.id.imgFilmOfYetai);
		img_yitan		=	(ImageView) findViewById(R.id.imgYitanOfYetai);
		
		lin_home.setOnClickListener(this);
		lin_yetai.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_search.setOnClickListener(this);
		lin_more.setOnClickListener(this);
		img_show.setOnClickListener(this);
		img_film.setOnClickListener(this);
		img_yitan.setOnClickListener(this);
		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);//通知服务获取ad数据.
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		case R.id.linearYetai:
//			intent.setClass(this, Activity_Art.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
//			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearSearch:
			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		case R.id.imgShowOfYetai:
			intent.putExtra("tag", "yanchu");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.imgFilmOfYetai:
			intent.putExtra("tag", "dianying");
			intent.setClass(this, Activity_SlidingMenue.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.imgYitanOfYetai:
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
}
