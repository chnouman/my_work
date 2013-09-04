package com.ssac.expro.kewen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class Activity_VIP extends BaseActivity implements OnClickListener{

	private LinearLayout lin_home, lin_yetai, lin_vip, lin_search, lin_more;
	private WebView webview;
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
		lin_yetai	= 	(LinearLayout) findViewById(R.id.linearYetai);
		lin_vip	=	(LinearLayout) findViewById(R.id.linearVip);
		lin_search	=	(LinearLayout) findViewById(R.id.linearSearch);
		lin_more=	(LinearLayout) findViewById(R.id.linearMore);
		
		lin_home.setOnClickListener(this);
		lin_yetai.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_search.setOnClickListener(this);
		lin_more.setOnClickListener(this);
		
		webview = (WebView) findViewById(R.id.webviewOfVip);
		
		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setUseWideViewPort(true);  
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.setBackgroundColor(0X000000);
		webview.setVisibility(View.VISIBLE);
		webview.loadUrl("file:///android_asset/vip.html");
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
			intent.setClass(this, Activity_Yetai.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearVip:
			break;
		case R.id.linearSearch:
			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
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
		webview.getSettings().setBuiltInZoomControls(true);
		webview.setVisibility(View.GONE);
		webview.destroy();
		super.onDestroy();
	}
	
	
}
