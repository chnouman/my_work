package com.ssac.expro.kewen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Activity_More extends BaseActivity implements OnClickListener{

	private LinearLayout linearAbount,linearMap,linearContact,linearVersion,linearSystem;
	private ImageView home;
	private String point ="31.321087, 120.701845";//地图定位中心点 文化博览中心
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_more);
		
		
		init();
		
	}

	@Override
	public void init() {
		
		home = (ImageView) findViewById(R.id.imageLeftOfHeadMore);
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Activity_More.this,Activity_Home.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				startActivity(intent);
			}
		});
		
		 linearAbount	=	(LinearLayout) findViewById(R.id.linearAbountOfMore);
		 linearMap		=	(LinearLayout) findViewById(R.id.linearMapOfMore);
		 linearContact	=	(LinearLayout) findViewById(R.id.linearContactOfMore);
		 linearVersion	=	(LinearLayout) findViewById(R.id.linearVersionOfMore);
		 linearSystem	=	(LinearLayout) findViewById(R.id.linearSystemSetOfMore);
		 
		 linearAbount.setOnClickListener(this);
		 linearMap.setOnClickListener(this);
		 linearContact.setOnClickListener(this);
		 linearVersion.setOnClickListener(this);
		 linearSystem.setOnClickListener(this);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearAbountOfMore:
			startActivity(new Intent(Activity_More.this,MoreAbountDetail.class));
			break;
		case R.id.linearMapOfMore:
			Intent i = new Intent(Intent.ACTION_VIEW, Uri .parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q="+point));   
			startActivity(i);   
//			Uri uri = Uri.parse("geo:"+point);    
//			Intent it = new Intent(Intent.ACTION_VIEW,uri);    
//			startActivity(it);    
			break;
		case R.id.linearContactOfMore:
		case R.id.linearVersionOfMore:
		case R.id.linearSystemSetOfMore:
		default:
			//弹出提示框
			new AlertDialog.Builder(Activity_More.this)
						.setTitle("提示")
						.setMessage("正在建设中...")
						.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						}).show();
			break;
		}
	}
	
}
