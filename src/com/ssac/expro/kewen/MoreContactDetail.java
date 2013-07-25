package com.ssac.expro.kewen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreContactDetail extends Activity {

	private ImageView back;
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_more_contact);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadMoreDetail);
		title		=	(TextView) findViewById(R.id.textOfHeadMoreDetail);
		title.setText(getResources().getString(R.string.str_more_contact));
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
