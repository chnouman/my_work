package com.ssac.expro.kewen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class SlowFlipGallery extends Gallery {
	
	 public SlowFlipGallery(Context paramContext)
	  {
	    super(paramContext);
	  } 

	  public SlowFlipGallery(Context paramContext, AttributeSet paramAttributeSet)
	  {
	    super(paramContext, paramAttributeSet);
	  }

	  public SlowFlipGallery(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
	  {
	    super(paramContext, paramAttributeSet, paramInt);
	  }

	  /**
	   * 判断是否向右滑动
	   * @param paramMotionEvent1
	   * @param paramMotionEvent2
	   * @return
	   */
	  private boolean isScrollingLeft(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2)
	  {
	    if (paramMotionEvent2.getX() > paramMotionEvent1.getX())
	    {
	    	return true;
	    }
	    
	    return false;
	  }

	  @Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			 if (isScrollingLeft(e1, e2)){//如果时间是 想做华东的 
			    	onKeyDown(21,null);
			    }
			 
			return super.onFling(e1, e2, velocityX, velocityY);
		}
}
