package com.ssac.expro.kewen;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ssac.expro.kewen.service.MainService;

public abstract  class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MainService.addActivity(this); 
	}

	public abstract void init();
	public abstract void refresh(Object ...param);

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		init(); 在这会有个问题 每次 回到当前页面都会重新家在一次布局造成资源浪费 耗时很多所以 这个手动来添加最好
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
//		MainService.promptExit(this);
		
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}