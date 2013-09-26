package com.ssac.expro.kewen;

import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ssac.expro.kewen.adapter.Adapter4Search;
import com.ssac.expro.kewen.adapter.Adapter4Search.lastIndexLoaded;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.SearchBean;
import com.ssac.expro.kewen.collapsiblesearchmenu.CollapsibleMenuUtils;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Activity_Search extends SherlockListActivity implements OnClickListener {

	private LinearLayout lin_home, lin_yetai, lin_vip, lin_search, lin_more;
	private Context c;
	private MenuItem mSearchMenuItem;
	private int PageIndex=1,PageSize=10;
	private ListView listview;
	private BaseAdapter adapter;
	private List<SearchBean> dataList=new ArrayList<SearchBean>();
	private String keyword="苏艺";
	private ProgressBar progressbar;
	
	private CollapsibleMenuUtils.OnQueryTextListener mOnQueryTextListener = new CollapsibleMenuUtils.OnQueryTextListener() {

		@Override
		public void onQueryTextSubmit(String query) {
//			ExproApplication.throwTipLong("Search key word:"+query);
			keyword = query;
			PageIndex=1;
			dataList.clear();
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
			task4Search task =new task4Search();
			task.execute();
		}

		@Override
		public void onQueryTextChange(String newText) {
//			mArrayAdapter.getFilter().filter(newText);
		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_search);
		c = this;
		
		Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_titlebar);
		BitmapDrawable bd = new BitmapDrawable(bmap);
		getSupportActionBar().setBackgroundDrawable(bd.getCurrent());
		
		init();
		
		
//		task4Search task =new task4Search();
//		task.execute();
	}

	public void init() {
		if(ExproApplication.metrics==null){
			ExproApplication.metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(ExproApplication.metrics);
		}
		
		listview = getListView();
		lin_home = (LinearLayout) findViewById(R.id.linearHOme);
		lin_yetai = (LinearLayout) findViewById(R.id.linearYetai);
		lin_vip = (LinearLayout) findViewById(R.id.linearVip);
		lin_search = (LinearLayout) findViewById(R.id.linearSearch);
		lin_more = (LinearLayout) findViewById(R.id.linearMore);
		progressbar	=	(ProgressBar) findViewById(R.id.progressBar1);
		
		
		lin_home.setOnClickListener(this);
		lin_yetai.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_search.setOnClickListener(this);
		lin_more.setOnClickListener(this);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				if(arg3>-1){
					
					SearchBean sb =dataList.get(arg2);
					
					goNext(sb);
				}
			}
		});
	}
	
	/**
	 * go to the next page content/daram/film
	 * content:文章
	 * film:电影
	 * drama:演出
	 * @param sb
	 */
	private void goNext(SearchBean sb) {
		// TODO Auto-generated method stub
		if("drama".equals(sb.getType())){
			Intent intent =new Intent(c,TheatreYanchuDetail.class);
			intent.putExtra("filmID", sb.getId());
			startActivity(intent);
		}else if("content".equals(sb.getType())){//
			Intent intent =new Intent(c,YitanDetail.class);
			intent.putExtra("contentID", sb.getId());
			startActivity(intent);
		}else if("film".equals(sb.getType())){//
			Intent intent =new Intent(c,FilmDetail.class);
			intent.putExtra("filmID", sb.getId());
			startActivity(intent);
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		// ATTENTION: need to do to closure of the keyboard when you click on
		// home
		if (mSearchMenuItem.isActionViewExpanded()) {
			mSearchMenuItem.collapseActionView();
			// reset filter
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
			startActivity(intent);// 通知服务获取ad数据.
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			finish();
			break;
		case R.id.linearYetai:
			intent.setClass(this, Activity_Yetai.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			finish();
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			finish();
			break;
		case R.id.linearSearch:
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			finish();
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

	
	//load more...
	private class task4Search extends AsyncTask<String, String, String> {

		private List<SearchBean> sList;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				sList = XmlToListService.GetSearchDataList(HttpUtil
						.sendGetRequest( Constants.SEARCH_BASE_URL +keyword+"/"+ PageSize
								+ "/" + PageIndex));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
				if(PageIndex>0)
					PageIndex--;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			if(sList!=null&&sList.size()>0){
				dataList.addAll(sList);
				adapter=new Adapter4Search(c, dataList,new lastIndexLoaded() {
					
					@Override
					public void loadData() {
						// TODO Auto-generated method stub
						PageIndex++;
						task4Search ts = new task4Search();
						ts.execute();
					}
				});
				listview.setAdapter(adapter);
			}
			
			progressbar.setVisibility(View.GONE);
		}
	}
}
