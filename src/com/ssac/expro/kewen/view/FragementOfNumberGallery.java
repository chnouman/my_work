package com.ssac.expro.kewen.view;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.adapter.ImageAdapter4NumberGallery;
import com.ssac.expro.kewen.service.MainService;

public class FragementOfNumberGallery extends Fragment{

//	private BaseActivity mparent;
	  private int flag;
	  private GestureDetector gestureDetector;
	  private NumberDotImageView mDotImageView;
	  private SlowFlipGallery mGallery;
	  private ImageAdapter4NumberGallery mImageAdapter;
	  private int postion = 0;
	  
	  private Timer timer ;
	  
   
	 /**
	  * 全局数据有变化、更新gallery
	  */
	public void NotifyDataSetChanged(){
		if(null!=mImageAdapter){
			mImageAdapter.notifyDataSetChanged();
		}
	}
	  
	  
	public FragementOfNumberGallery() {
		 super();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(container==null){
			return null;
		}
		
		container = (ViewGroup) inflater.inflate(R.layout.help, null);
		//设置图片资源对应填充
		this.gestureDetector = new GestureDetector(new DefaultGestureDetector());
	    this.mDotImageView = ((NumberDotImageView)container.findViewById(R.id.dot_imageview));
	    this.mGallery = ((SlowFlipGallery)container.findViewById(R.id.gallery));
	    
	    //填充数据
	    fillGallery();
	    
		return container;
	}

	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			mGallery.setSelection(postion	, true);
		}
		
		
	};
	
	 //给 gallery 放数据。
	  public void fillGallery()
	  {
		  
	    this.mImageAdapter = new ImageAdapter4NumberGallery(getActivity());
	    this.mGallery.setAdapter(this.mImageAdapter);
	    final int i = MainService.adList.size();
	    
	    timer = new Timer();
	    timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				postion++;
				if(postion==(MainService.adList.size()-1)){
					postion=0;
				}
				handler.sendEmptyMessage(0);
				
			}
		},  1000, 2000);
	    
	    mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	      public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
	      {
	    	  FragementOfNumberGallery.this.mDotImageView.refresh(i, paramInt);
//	        if (paramInt < HelpShowImageActivity.this.arrays.length)
//	          HelpShowImageActivity.this.mTitle.setText(HelpShowImageActivity.this.arrays[paramInt]);
//	        if (paramInt == -1 + FragementOfNumberGallery.this.arrays.length)
//	        {
//	        }
	      }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	    });
	    this.mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
	      {
	        if (paramInt == -1 + MainService.adList.size()){
	        	
	        	// HelpShowImageActivity.this.jumpActivity();
	        	Toast.makeText(getActivity(), "over", Toast.LENGTH_LONG).show(); 
//	        	jumpActivity();
	        }
	         
	      }
	    });
	    
	    this.mGallery.setOnTouchListener(new View.OnTouchListener()
	    {
	    	
	      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	      {
	        return FragementOfNumberGallery.this.gestureDetector.onTouchEvent(paramMotionEvent);
	      }
	      
	    });
	  }
	  
	 class DefaultGestureDetector extends GestureDetector.SimpleOnGestureListener
	  {
	    int mFlag;

	    DefaultGestureDetector()
	    {
	    }

	    public boolean onDown(MotionEvent paramMotionEvent)
	    {
	      this.mFlag = FragementOfNumberGallery.this.flag;
	      return false;
	    }

	    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	    {
	      Log.e("mGallery.getSelectedItemId()", FragementOfNumberGallery.this.mGallery.getSelectedItemId() + "");
	      
	      Log.e("mGallery.getSelected-----position", postion+"");
	      
	      if(postion==2){
	    	  if( (paramMotionEvent1.getX() - paramMotionEvent2.getX() > 20.0F)
	    		  && (Math.abs(paramFloat1) > 20.0F)){
	    		  
	    		  Log.e("mGallery.getSelected----end", "over now goto next activity");
//	    		  jumpActivity();
	    	  }
	      }
	      postion = (int) FragementOfNumberGallery.this.mGallery.getSelectedItemId();
	      
//	      }
	      return false;
	    }
	  }
	 
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
