package com.ssac.expro.kewen;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ssac.expro.kewen.collapsiblesearchmenu.CollapsibleMenuUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class Activity_Search extends SherlockListActivity implements OnClickListener{

	private LinearLayout lin_home, lin_yetai, lin_vip, lin_search, lin_more;
	private Context c;
	  private static final String[] ITEMS = new String[]{
          "China",
          "India",
          "United States",
          "Indonesia",
          "Brazil",
          "Pakistan",
          "Nigeria",
          "Bangladesh",
          "Russia",
          "Japan",
          "Mexico",
          "Philippines",
          "Vietnam",
          "Ethiopia",
          "Egypt",
          "Germany",
          "Iran",
          "Turkey",
          "Democratic Republic of the Congo",
          "Thailand",
          "France",
          "United Kingdom",
          "Italy",
          "South Africa",
          "Myanmar",
          "South Korea",
          "Colombia",
          "Spain",
          "Ukraine",
          "Tanzania",
          "Kenya",
          "Argentina",
          "Poland",
          "Algeria",
          "Canada"
  };
  private MenuItem mSearchMenuItem;
  private ArrayAdapter<String> mArrayAdapter;
  private CollapsibleMenuUtils.OnQueryTextListener mOnQueryTextListener = new CollapsibleMenuUtils.OnQueryTextListener() {

      @Override
      public void onQueryTextSubmit(String query) {
      }

      @Override
      public void onQueryTextChange(String newText) {
          mArrayAdapter.getFilter().filter(newText);
      }
  };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_search);
		c=this;
		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS);
        setListAdapter(mArrayAdapter);
		init();
		
		if(getVersionName()>11){
			Bitmap bmap =BitmapFactory.decodeResource(getResources(), R.drawable.home_titlebar);
			BitmapDrawable bd=new BitmapDrawable(bmap);
			getActionBar().setBackgroundDrawable(bd.getCurrent());
		}
	}
	private int getVersionName() 
	   {
	           int v = Build.VERSION.SDK_INT;
				ExproApplication.throwTips("version:"+v);
	           return v;
	   }
	public void init() {
		// TODO Auto-generated method stub
		lin_home=	(LinearLayout) findViewById(R.id.linearHOme);
		lin_yetai	= 	(LinearLayout) findViewById(R.id.linearYetai);
		lin_vip	=	(LinearLayout) findViewById(R.id.linearVip);
		lin_search	=	(LinearLayout) findViewById(R.id.linearSearch);
		lin_more=	(LinearLayout) findViewById(R.id.linearMore);
		
		lin_home.setOnClickListener(this);
		lin_yetai.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_search.setOnClickListener(this);
		lin_more.setOnClickListener(this);
	}

	@Override
    protected void onPause() {
        super.onPause();
        // ATTENTION: need to do to closure of the keyboard when you click on home
        if (mSearchMenuItem.isActionViewExpanded()) {
            mSearchMenuItem.collapseActionView();
            // reset filter
            mArrayAdapter.getFilter().filter(null);
            ExproApplication.throwTips("home key is clicked!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mSearchMenuItem = CollapsibleMenuUtils.addSearchMenuItem(menu, true, mOnQueryTextListener);
        return true;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);//通知服务获取ad数据.
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		case R.id.linearYetai:
			intent.setClass(this, Activity_Yetai.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.linearVip:
			break;
		case R.id.linearSearch:
			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
