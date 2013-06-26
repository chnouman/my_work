package com.ssac.expro.kewen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class Activity_VIP extends BaseActivity implements OnClickListener{

	private LinearLayout lin_home,lin_art,lin_vip,lin_youhui,lin_more;
	private LinearLayout lin_shenqing,lin_chongzhi,lin_center,lin_zongze,lin_vip_youhui;
	private Context c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_vip);
		c=this;
		
		init();
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
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
		
		lin_shenqing		=	(LinearLayout) findViewById(R.id.linearVipShenqingOfMore);
		lin_chongzhi		=	(LinearLayout) findViewById(R.id.linearVipChongzhiOfMore);
		lin_center			=	(LinearLayout) findViewById(R.id.linearVipCenterOfMore);
		lin_zongze			=	(LinearLayout) findViewById(R.id.linearVipZongzeOfMore);
		lin_vip_youhui		=	(LinearLayout) findViewById(R.id.linearVipYouhuiOfMore);
		
		lin_shenqing.setOnClickListener(this);
		lin_chongzhi.setOnClickListener(this);
		lin_center.setOnClickListener(this);
		lin_zongze.setOnClickListener(this);
		lin_vip_youhui.setOnClickListener(this);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);//通知服务获取ad数据
			break;
		case R.id.linearNews:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_Art.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		case R.id.linearVip:
			break;
		case R.id.linearYouHui:
			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearMore:
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		case R.id.linearVipShenqingOfMore:
		case R.id.linearVipChongzhiOfMore:
		case R.id.linearVipCenterOfMore:
		case R.id.linearVipZongzeOfMore:
		case R.id.linearVipYouhuiOfMore:
			ExproApplication.showBuildTip(c);
			break;
		default:
			break;
		}
		
	}
	
}
