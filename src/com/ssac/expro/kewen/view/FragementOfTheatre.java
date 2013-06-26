package com.ssac.expro.kewen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import com.ssac.expro.kewen.Activity_Home;
import com.ssac.expro.kewen.R;

public class FragementOfTheatre extends Fragment implements OnClickListener{

	private ViewFlipper mViewFlipper;
	private ImageView imgBack;
	private Context c;
	  

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		c=getActivity().getApplicationContext();
		container = (ViewGroup) inflater.inflate(R.layout.layout_theatre, null);
		
//		mViewFlipper = (ViewFlipper) container.findViewById(R.id.viewflipperOfTheatre);
		
		imgBack		=	(ImageView) container.findViewById(R.id.imageLeftOfHeadTheatre);
		
		imgBack.setOnClickListener(this);
	     return container;
	     
	  }
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.imageLeftOfHeadTheatre:
			Intent intent =new Intent(c,Activity_Home.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
