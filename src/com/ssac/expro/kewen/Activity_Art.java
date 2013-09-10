package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.HashMap;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.GetPicFromURL;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.util.MulitPointTouchListener;

public class Activity_Art extends BaseActivity implements OnClickListener {

	private ImageView imageView,imageBack;
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
		imageBack = (ImageView) findViewById(R.id.imageLeftOfHeadArt);
		imageView	=	(ImageView) findViewById(R.id.imageOfArt);
		imageBack.setOnClickListener(this);
		imageView.setOnTouchListener(new MulitPointTouchListener ());
		
		// 通知服务获取ad数据
		HashMap<String, Object> hm = new HashMap<String, Object>();
		Task ts = new Task(TaskType.GET_ART, hm);
		MainService.newTask(ts);
	}

	protected void onResume() {
		super.onResume();
		Log.i("poe", "onResume");
	}

	protected void onDestroy() {
		Log.i("poe", "onDestroy()");
		super.onDestroy();
	}


	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		if (MainService.artList != null && MainService.artList.size() > 0) {
			progressbar.setVisibility(View.GONE);
			final Art art = MainService.artList.get(0);

			new Thread( new Runnable() {
				public void run() {
					if (null != art.getImgPath()) {
							ImageCacheUtil ic =new ImageCacheUtil();
							ic.loadImageList(ExproApplication.imageLoader, imageView, art.getImgPath());
					}
				}
			}).start();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}

}
