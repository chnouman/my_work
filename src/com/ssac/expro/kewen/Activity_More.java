package com.ssac.expro.kewen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Activity_More extends BaseActivity implements OnClickListener{

	private LinearLayout linearAbount,linearMap,linearContact,linearVersion;
	private ImageView home;
	
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
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
		
		 linearAbount	=	(LinearLayout) findViewById(R.id.linearAbountOfMore);
		 linearMap		=	(LinearLayout) findViewById(R.id.linearMapOfMore);
		 linearContact	=	(LinearLayout) findViewById(R.id.linearContactOfMore);
		 linearVersion	=	(LinearLayout) findViewById(R.id.linearVersionOfMore);
		 
		 linearAbount.setOnClickListener(this);
		 linearMap.setOnClickListener(this);
		 linearContact.setOnClickListener(this);
		 linearVersion.setOnClickListener(this);
		 findViewById(R.id.linearZhoubianOfMore).setOnClickListener(this);
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
			startActivity(new Intent(Activity_More.this,Client_Map.class));
			break;
		case R.id.linearContactOfMore:
			startActivity(new Intent(Activity_More.this,MoreContactDetail.class));
			break;
		case R.id.linearVersionOfMore:
			startActivity(new Intent(Activity_More.this,MoreVersionUpdateDetail.class));
			break;
		case R.id.linearZhoubianOfMore:
			startActivity(new Intent(Activity_More.this,MoreZhoubianDetail.class));
			break;
		default:
			break;
		}
	}
	
}
