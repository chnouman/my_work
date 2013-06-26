package com.ssac.expro.kewen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.view.NumberDotImageView;
import com.ssac.expro.kewen.view.SlowFlipGallery;

public class HelpShowImageActivity extends Activity {
	
	 public static final String HELP_EXHIBITION_VIEW = "exhibition";
	  public static final String HELP_VIEW_KEY = "help_view_key";
	  private String[] arrays;
	  private int flag;
	  private GestureDetector gestureDetector;
	  private int[] images;
	  private List<Bitmap> mBmpList;
	  private NumberDotImageView mDotImageView;
	  private SlowFlipGallery mGallery;
	  private ImageAdapter mImageAdapter;
	  private TextView mTitle;
	  private LinearLayout mdotlayout;
	  private int postion = 0;

	  public HelpShowImageActivity()
	  {
	    int[] arrayOfInt = new int[4];
	    arrayOfInt[0] = R.drawable.help01;
	    arrayOfInt[1] = R.drawable.help02;
	    arrayOfInt[2] = R.drawable.help03;
	    arrayOfInt[3] = R.drawable.help04;
	    this.images = arrayOfInt;
	    this.flag = 5;
	  }

	  //给 gallery 放数据。
	  private void fillGallery(List<Bitmap> paramList)
	  {
	    this.mImageAdapter = new ImageAdapter(this, paramList);
	    this.mGallery.setAdapter(this.mImageAdapter);
	    final int i = paramList.size();
	    
	    mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	    	
	      public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
	      {
	    	  
	        HelpShowImageActivity.this.mDotImageView.refresh(i, paramInt);
	        
//	        if (paramInt < HelpShowImageActivity.this.arrays.length)
//	          HelpShowImageActivity.this.mTitle.setText(HelpShowImageActivity.this.arrays[paramInt]);
	        
	        if (paramInt == -1 + HelpShowImageActivity.this.arrays.length)
	        {
	        	
	        }
	        
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
	        if (paramInt == -1 + HelpShowImageActivity.this.images.length){
	        	
	        	// HelpShowImageActivity.this.jumpActivity();
//	        	Toast.makeText(HelpShowImageActivity.this, "over", Toast.LENGTH_LONG).show();
	        	jumpActivity();
	        }
	         
	      }
	    });
	    
	    this.mGallery.setOnTouchListener(new View.OnTouchListener()
	    {
	      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	      {
	        return HelpShowImageActivity.this.gestureDetector.onTouchEvent(paramMotionEvent);
	      }
	    });
	    
	  }

	  private void jumpActivity()
	  {
	    startActivity(new Intent(this, Activity_Home.class));
	    overridePendingTransition(2130968579, 2130968581);
	    finish();
	  }

	  public int getScreenScale()
	  {
	    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
	    int i;
	    switch (localDisplayMetrics.widthPixels)
	    {
	    default:  i = 2;
	    case 240: i = 1;
	    case 320: i = 2;
	    case 480:i = 3;
	    }
		return i;
	  }

	  protected void onCreate(Bundle paramBundle)
	  {
	   //enableMobClick(true);
	    super.onCreate(paramBundle);
	    requestWindowFeature(1);
	    getWindow().setFlags(1024, 1024);
	    getWindow().clearFlags(2048);
	    setContentView(R.layout.help);
	    
	    
	    //启动服务
	    if(MainService.isrun){
	    	Intent stop_intnent=new Intent("com.ssac.expro.service.MainService");
	    	stopService(stop_intnent);
	    }
	    
	    Intent intent = new Intent(HelpShowImageActivity.this,MainService.class);
	    startService(intent);
	    
	    
	    this.gestureDetector = new GestureDetector(new DefaultGestureDetector());
	    this.arrays = getResources().getStringArray(R.array.help_tips);
	    this.mTitle = ((TextView)findViewById(R.id.help_title));
	    this.mdotlayout = ((LinearLayout)findViewById(R.id.dot_linearlayout));
	    this.mDotImageView = ((NumberDotImageView)findViewById(R.id.dot_imageview));
	    this.mGallery = ((SlowFlipGallery)findViewById(R.id.gallery));
	    this.mBmpList = new ArrayList();
	    for (int i = 0; i < this.images.length; i++)
	    {
	      Drawable localDrawable = getResources().getDrawable(this.images[i]);
	      this.mBmpList.add(((BitmapDrawable)localDrawable).getBitmap());
	      localDrawable = null;
	    }
	    fillGallery(this.mBmpList);
	    if (getScreenScale() == 3)
	    {
	      this.mTitle.setPadding(0, 80, 0, 0);
	      this.mdotlayout.setPadding(0, 20, 0, 20);
	    }
	  }

	  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
	  {
	    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
//	    	Toast.makeText(HelpShowImageActivity.this, "over", Toast.LENGTH_LONG).show();
 	      jumpActivity();
	    return super.onKeyDown(paramInt, paramKeyEvent);
	}

	  class DefaultGestureDetector extends GestureDetector.SimpleOnGestureListener
	  {
	    int mFlag;

	    DefaultGestureDetector()
	    {
	    }

	    public boolean onDown(MotionEvent paramMotionEvent)
	    {
	      this.mFlag = HelpShowImageActivity.this.flag;
	      return false;
	    }

	    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	    {
	      Log.e("mGallery.getSelectedItemId()", HelpShowImageActivity.this.mGallery.getSelectedItemId() + "");
	      
	      Log.e("mGallery.getSelected-----position", postion+"");
	      
	      if(postion==2){
	    	  if( (paramMotionEvent1.getX() - paramMotionEvent2.getX() > 20.0F)
	    		  && (Math.abs(paramFloat1) > 20.0F)){
	    		  
	    		  Log.e("mGallery.getSelected----end", "over now goto next activity");
	    		  jumpActivity();
	    	  }
	      }
	      postion = (int) HelpShowImageActivity.this.mGallery.getSelectedItemId();
	      
//	      if ((this.mFlag == -1 + HelpShowImageActivity.this.arrays.length)
//	    		  && (paramMotionEvent1.getX() - paramMotionEvent2.getX() > 20.0F)
//	    		  && (Math.abs(paramFloat1) > 20.0F)){
//	     //   HelpShowImageActivity.this.jumpActivity();
//	    	  
//	      }
	      return false;
	    }
	  }

	  class ImageAdapter extends BaseAdapter
	  {
	    private final Context mContext;
	    private final List<Bitmap> mImagesByteList;

	    public ImageAdapter(Context mContext,List<Bitmap> arg2)
	    {
	      this.mContext = mContext;
	      this.mImagesByteList = arg2;
	    }

	    public int getCount()
	    {
	      return this.mImagesByteList.size();
	    }

	    public Object getItem(int paramInt)
	    {
	      return this.mImagesByteList.get(paramInt);
	    }

	    public long getItemId(int paramInt)
	    {
	      return paramInt;
	    }

	    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	    {
	      ImageView localImageView = new ImageView(this.mContext);
	      localImageView.setImageBitmap((Bitmap)this.mImagesByteList.get(paramInt));
	      localImageView.setLayoutParams(new Gallery.LayoutParams(-1, -2));
	      localImageView.setPadding(10, 10, 10, 10);
	      localImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	      localImageView.setBackgroundColor(0);
	      return localImageView;
	    }
	  }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		for(int i=0;i<mBmpList.size();i++){
			if(null!=mBmpList.get(i)){
				mBmpList.get(i).recycle();
			}
		}
	}
	  
	  
}
