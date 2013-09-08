package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.ssac.expro.kewen.adapter.Adapter4ShowinfoList;
import com.ssac.expro.kewen.adapter.Adapter4ShowinfoList.lastIndexLoad;
import com.ssac.expro.kewen.adapter.Adapter4TheatreActivitesList;
import com.ssac.expro.kewen.adapter.Adapter4TheatreActivitesList.lastIndexLoad4Activities;
import com.ssac.expro.kewen.adapter.ImageAdapter4GalleryTheatre;
import com.ssac.expro.kewen.adapter.PaperAdapter;
import com.ssac.expro.kewen.bean.AD;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.ShowInfo;
import com.ssac.expro.kewen.bean.Theatre;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.view.MyViewPager;
import com.ssac.expro.kewen.view.NumberDotImageView;
import com.ssac.expro.kewen.view.SlideHolder;
import com.ssac.expro.kewen.view.SlowFlipGallery;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 演出
 * @author poe
 */
@SuppressLint("ValidFragment")
public class FragementYanChu extends Fragment implements OnClickListener {

	private SlideHolder mSlideHolder;//control menue
	private List<AD> adList = new ArrayList<AD>();
	private List<ShowInfo> showList = new ArrayList<ShowInfo>();
	private List<Theatre> theatreList =new ArrayList<Theatre>();
	private MyViewPager mViewPager;
	private LinearLayout lin_ycxy, lin_jyhd;
	private TextView txt_show_info,txt_show_activities;
	private LayoutInflater lin;
//	private LinearLayout progressbar;
	private PagerAdapter adapter;
	private int toId = 0;
	private int PageSize = 10,PageSize2=10;
	private int PageIndex = 1,PageIndex2=1;// 从1开始
	// gallery
	private int flag;
	private GestureDetector gestureDetector;
	private NumberDotImageView mDotImageView;
	private SlowFlipGallery mGallery;
	private ImageAdapter4GalleryTheatre mImageAdapter;
	private int postion = 0;
	private Context mContext;
	private Timer timer;
	//listview
	private ListView listview;
	private BaseAdapter listAdapter;
	//剧院活动
	private ListView listView2;
	private BaseAdapter listAdapter2;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(postion>=(adList.size())){
				postion=0;
			}
			mGallery.setSelection(postion, true);
		}
	};
	

	public FragementYanChu(Context mContext,SlideHolder mSlideHolder) {
		super();
		this.mContext = mContext;
		this.mSlideHolder=mSlideHolder;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}

	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ImageCacheUtil.ClearCache(ExproApplication.imageLoader);
		super.onDestroy();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lin = inflater;
		if (container == null) {
			return null;
		}
		container = (ViewGroup) inflater.inflate(R.layout.layout_theatre, null);
		lin_ycxy = (LinearLayout) container
				.findViewById(R.id.linearYanchuXYOfTheatre);
		lin_jyhd = (LinearLayout) container
				.findViewById(R.id.linearJuyuanHDOfTheatre);
		txt_show_info	=	(TextView) container.findViewById(R.id.txtShowInfoOfTheatre);
		txt_show_activities	=	(TextView) container.findViewById(R.id.txtShowActiveOfTheatre);
		lin_jyhd.setOnClickListener(this);
		lin_ycxy.setOnClickListener(this);
		ImageView img_back=(ImageView) container.findViewById(R.id.imageLeftOfHeadTheatre);
		img_back.setOnClickListener(this);
//		progressbar = (LinearLayout) container
//				.findViewById(R.id.progressOfTheatre);
		// viewflipper
		mViewPager = (MyViewPager) container
				.findViewById(R.id.viewpagerOfTheatre);

		initVP();

		
		return container;
	}

	private void initVP() {
		List<View> views = new ArrayList<View>();
		views.add(lin.inflate(R.layout.layout_show_info, null));
		views.add(lin.inflate(R.layout.layout_theatre_activities, null));
		views.add(lin.inflate(R.layout.layout_yiwen_zhoukan, null));

		adapter = new PaperAdapter(views);

		mViewPager.setAdapter(adapter);

		// 给viewpager增加监听器
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// 1.设置背景颜色
//				updateTextColorBefore(toId);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

		// gallery
		this.gestureDetector = new GestureDetector(new DefaultGestureDetector());
		this.mDotImageView = ((NumberDotImageView) views.get(0)
				.findViewById(R.id.dot_imageviewOfShowInfo));
		this.mGallery = ((SlowFlipGallery) views.get(0).findViewById(R.id.galleryOfShowInfo));
		listview = (ListView) views.get(0).findViewById(R.id.listviewOfShowInfo);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ShowInfo sinfo=showList.get(arg2);
				Intent intent =new Intent(mContext,TheatreYanchuDetail.class);
				intent.putExtra("filmID",sinfo.getDramaID() );
				intent.putExtra("img", sinfo.getTitleImage());
				intent.putExtra("title", sinfo.getDramaName());
				intent.putExtra("type", sinfo.getDramaType());
				intent.putExtra("time", sinfo.getShowTime());
				intent.putExtra("price", sinfo.getPrice());
				
				startActivity(intent);
				}
		});
		//listview2
		listView2= (ListView) views.get(1).findViewById(R.id.listviewOfTheatreActivies);
		
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Theatre  sinfo=theatreList.get(arg2);
				Intent intent =new Intent(mContext,TheatreActivitiesDetail.class);
				intent.putExtra("contentID",sinfo.getContentID() );
				intent.putExtra("img", sinfo.getTitleImage());
				intent.putExtra("title", sinfo.getContentTitle());
				intent.putExtra("time", sinfo.getBeginTime());
				
				startActivity(intent);
				}
		});
		task4YanchuZX ts1 = new task4YanchuZX();
		ts1.execute();
	}
	 //给 gallery 放数据。
	  public void fillGallery() {
		  
	    this.mImageAdapter = new ImageAdapter4GalleryTheatre(mContext,adList);
	    this.mGallery.setAdapter(this.mImageAdapter);
	    final int i = adList.size();
	    
	    timer = new Timer();
	    mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	      public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
	      {
	    	  FragementYanChu.this.mDotImageView.refresh(i, paramInt);
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
//	        	jumpActivity();
	        	ExproApplication.throwTips("go over!");
	        }
	      }
	    });
	    
	    this.mGallery.setOnTouchListener(new View.OnTouchListener()
	    {
	      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	      {
	        return FragementYanChu.this.gestureDetector.onTouchEvent(paramMotionEvent);
	      }
	    });
	    
	    timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				postion++;
				handler.sendEmptyMessage(0);
			}
		},  10*1000, 2500);
	 }
	  
	 /**
	  * tabbar choose then change the textcolor
	  * @param position
	  */
	@SuppressWarnings("deprecation")
	private void updateTextColorBefore(int position) {

		switch (position) {

		case 0:
			txt_show_info.setBackgroundResource(R.drawable.yanchu_backtop);
			txt_show_activities.setBackgroundDrawable(null);
			break;
		case 1:
			txt_show_info.setBackgroundDrawable(null);
			txt_show_activities.setBackgroundResource(R.drawable.yanchu_backtop);
			break;
		}
		if(listAdapter2==null){
			task4TheatreActivities ts=new task4TheatreActivities();
			ts.execute();
		}
		mViewPager.setCurrentItem(position);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearYanchuXYOfTheatre:// 演出资讯
			toId = 0;
			break;
		case R.id.linearJuyuanHDOfTheatre:// 剧院活动
			toId = 1;
			break;
		case R.id.imageLeftOfHeadTheatre://show menue
			mSlideHolder.toggle();
			break;
		default:
			break;
		}
		updateTextColorBefore(toId);
	}

	private class task4YanchuZX extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载演出资讯...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				adList = XmlToListService.GetAD(HttpUtil.sendGetRequest(
						Constants.YANCHU_AD));
				showList = XmlToListService.GetShowInfo(HttpUtil
						.sendGetRequest( Constants.YANCHU_ZIXUN + PageSize
								+ "/" + PageIndex));

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			mSlideHolder.toggle();
			// 处理结果
			if(adList!=null&&adList.size()>0){
				fillGallery();
			}else{
				Log.i("poe", "获取广告数据失败！");
			}
			
			if(showList!=null&&showList.size()>0){
					listAdapter=new Adapter4ShowinfoList(mContext, showList,new lastIndexLoad() {
						
						@Override
						public void loadData() {
							// TODO Auto-generated method stub
							PageIndex++;
							task4YanchuZX2 ts = new task4YanchuZX2();
							ts.execute();
						}
					});
					listview.setAdapter(listAdapter);
			}
			
			if(mSlideHolder.isOpened()){
				mSlideHolder.toggle();
			}
		}
	}
	
	//load more...
	private class task4YanchuZX2 extends AsyncTask<String, String, String> {

		private List<ShowInfo> sList;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				sList = XmlToListService.GetShowInfo(HttpUtil
						.sendGetRequest( Constants.YANCHU_ZIXUN + PageSize
								+ "/" + PageIndex));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
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
				showList.addAll(sList);
				listAdapter=new Adapter4ShowinfoList(mContext, showList,new lastIndexLoad() {
					
					@Override
					public void loadData() {
						// TODO Auto-generated method stub
						PageIndex++;
						task4YanchuZX2 ts = new task4YanchuZX2();
						ts.execute();
					}
				});
				listview.setAdapter(listAdapter);
			}
		}
	}

	
	private class task4TheatreActivities extends AsyncTask<String, String, String> {
		
		private List<Theatre> tlist=new ArrayList<Theatre>();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ExproApplication.throwTipLong("正在努力的为您加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				tlist = XmlToListService.GetTheatre(HttpUtil
						.sendGetRequest( Constants.JUYUAN_ACTIVITIES + PageSize2
								+ "/" + PageIndex2));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
				if(PageIndex2>1){
					PageIndex2--;
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			if(tlist!=null&&tlist.size()>0){
				 theatreList.addAll(tlist);
				 
					listAdapter2=new Adapter4TheatreActivitesList(mContext, theatreList,new lastIndexLoad4Activities() {
						
						@Override
						public void loadData() {
							// TODO Auto-generated method stub
							
							PageIndex2++;
							task4TheatreActivities ts =new task4TheatreActivities();
							ts.execute();
						}
					});
					listView2.setAdapter(listAdapter2);
			}
		}
	}
	class DefaultGestureDetector extends
			GestureDetector.SimpleOnGestureListener {
		int mFlag;
		DefaultGestureDetector() {
		}
		
		public boolean onDown(MotionEvent paramMotionEvent) {
			this.mFlag = FragementYanChu.this.flag;
			return false;
		}
		public boolean onFling(MotionEvent paramMotionEvent1,
				MotionEvent paramMotionEvent2, float paramFloat1,
				float paramFloat2) {
			postion = (int) FragementYanChu.this.mGallery.getSelectedItemId();
			return false;
		}
	}
}
