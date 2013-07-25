package com.ssac.expro.kewen;

import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.VersionUpdateManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreVersionUpdateDetail extends Activity {

	private ImageView back;
	private TextView title,txtVersion;
	private Button btnUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_more_version);
		
		init();
	}
	
	//初始化
	void init(){
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadMoreDetail);
		title		=	(TextView) findViewById(R.id.textOfHeadMoreDetail);
		title.setText(getResources().getString(R.string.str_about));
		
		txtVersion = (TextView) findViewById(R.id.txtVersion);
		btnUpdate = (Button) findViewById(R.id.btnCheckVerison);
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(HttpUtil.checkNet(MoreVersionUpdateDetail.this.getApplicationContext())){
					VersionUpdateManager vm=new VersionUpdateManager();
					vm.CheckVersion(MoreVersionUpdateDetail.this);
				}	else{
					ExproApplication.throwTips("网络环境有问题，请设置网络后重试！");
				}
			}
		});
		
		txtVersion.setText("V"+ExproApplication.version);
	}
}
