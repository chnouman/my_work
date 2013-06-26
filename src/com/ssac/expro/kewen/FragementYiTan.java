package com.ssac.expro.kewen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ssac.expro.kewen.adapter.PaperAdapter;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.FromType;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.AsyncImageLoader;
import com.ssac.expro.kewen.util.AsyncImageLoader.ImageCallback;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.view.MyViewPager;
import com.ssac.expro.kewen.view.SlideHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 演出
 * 
 * @author poe
 */
@SuppressLint("ValidFragment")
public class FragementYiTan extends Fragment implements OnClickListener {

	private SlideHolder mSlideHolder;// control menue
	private List<ArtLesson> artList = new ArrayList<ArtLesson>();
	private List<ArtLesson> artList2 = new ArrayList<ArtLesson>();
	private List<ArtLesson> artList3 = new ArrayList<ArtLesson>();

	private List<ArtLesson> artList4 = new ArrayList<ArtLesson>();// 聚焦名家
	private List<ArtLesson> artList5 = new ArrayList<ArtLesson>();// Sunday艺术汇
	private List<ArtLesson> artList6 = new ArrayList<ArtLesson>();// 特别活动

	private MyViewPager mViewPager;
	private LinearLayout lin_ketang, lin_huodong;
	private LayoutInflater lin;
	// private LinearLayout progressbar;
	private PagerAdapter adapter;
	private int toId = 0, fromId = 0;
	private int PageSize = 10;
	private int PageIndex = 1, PageIndex2 = 1;// 从1开始
	private Context mContext;
	// expandlistview
	private ExpandableListView listview, listview2;
	private expandableadapter listAdapter;
	private expandableadapter2 listAdapter2;

	public FragementYiTan(Context mContext, SlideHolder mSlideHolder) {
		super();
		this.mContext = mContext;
		this.mSlideHolder = mSlideHolder;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lin = inflater;
		if (container == null) {
			return null;
		}
		container = (ViewGroup) inflater.inflate(R.layout.layout_yitan, null);
		lin_ketang = (LinearLayout) container
				.findViewById(R.id.linear1OfHeadYitan);
		lin_huodong = (LinearLayout) container
				.findViewById(R.id.linear2OfHeadYitan);
		lin_ketang.setOnClickListener(this);
		lin_huodong.setOnClickListener(this);
		ImageView img_back = (ImageView) container
				.findViewById(R.id.imageLeftOfHeadYitan);
		img_back.setOnClickListener(this);
		// progressbar = (LinearLayout) container
		// .findViewById(R.id.progressOfTheatre);
		// viewflipper
		mViewPager = (MyViewPager) container
				.findViewById(R.id.viewpagerOfYitan);

		initVP();

		return container;
	}

	private void initVP() {
		List<View> views = new ArrayList<View>();
		views.add(lin.inflate(R.layout.layout_yishu_ketang, null));
		views.add(lin.inflate(R.layout.layout_yiwen_huodong, null));
		// views.add(lin.inflate(R.layout.layout_yiwen_zhoukan, null));
		adapter = new PaperAdapter(views);
		mViewPager.setAdapter(adapter);

		// 给viewpager增加监听器
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// 1.设置背景颜色
				fromId = arg0;
				// 初始化、获取聚焦名家的数据
				if (arg0 > 0 && listAdapter2 == null) {
					task4 ts = new task4();
					ts.execute();
				}

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

		listview = (ExpandableListView) views.get(0).findViewById(
				R.id.expandListviewOfYiwenketang);
		listview2 = (ExpandableListView) views.get(1).findViewById(
				R.id.expandListviewOfYiwenhuodong);

		listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if (groupPosition == 0) {
					if (artList.size() < 1) {
						task1 ts = new task1();
						ts.execute();
					}
				}

				if (groupPosition == 1) {
					if (artList2.size() < 1) {
						task2 ts = new task2();
						ts.execute();
					}
				}

				if (groupPosition == 2) {
					if (artList3.size() < 1) {
						task3 ts = new task3();
						ts.execute();
					}
				}
				return false;
			}
		});

		listview2.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if (groupPosition == 0) {
					if (artList4.size() < 1) {
						task4 ts = new task4();
						ts.execute();
					}
				}

				if (groupPosition == 1) {
					if (artList5.size() < 1) {
						task5 ts = new task5();
						ts.execute();
					}
				}

				if (groupPosition == 2) {
					if (artList6.size() < 1) {
						task6 ts = new task6();
						ts.execute();
					}
				}
				return false;
			}
		});

		listview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				ArtLesson al = null;
				switch (groupPosition) {
				case 0:
					al = artList.get(childPosition);
					break;
				case 1:
					al = artList2.get(childPosition);
					break;
				case 2:
					al = artList3.get(childPosition);
					break;
				}
				if (al != null) {
					Intent intent = new Intent(mContext, YitanDetail.class);
					intent.putExtra("contentID", al.getContentID());
					intent.putExtra("img", al.getTitleImageName());
					intent.putExtra("title", al.getContentTitle());

					startActivity(intent);
				}
				return false;
			}
		});

		listview2.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				ArtLesson al = null;
				switch (groupPosition) {
				case 0:
					al = artList4.get(childPosition);
					break;
				case 1:
					al = artList5.get(childPosition);
					break;
				case 2:
					al = artList6.get(childPosition);
					break;
				}
				if (al != null) {
					Intent intent = new Intent(mContext, YitanDetail.class);
					intent.putExtra("contentID", al.getContentID());
					intent.putExtra("img", al.getTitleImageName());
					intent.putExtra("title", al.getContentTitle());

					startActivity(intent);
				}
				return false;
			}
		});
		task1 ts = new task1();
		ts.execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linear1OfHeadYitan:// 演出资讯
			toId = 0;
			mViewPager.setCurrentItem(toId);
			break;
		case R.id.linear2OfHeadYitan:// 剧院活动
			toId = 1;
			mViewPager.setCurrentItem(toId);
			break;
		case R.id.imageLeftOfHeadYitan:// show menue
			mSlideHolder.toggle();
			break;
		default:
			break;
		}
	}

	// 1.艺术分享
	private class task1 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_YISHU_SHARE
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList != null && artList.size() > 0) {
				if (listAdapter == null) {
					listAdapter = new expandableadapter(mContext);
					listview.setAdapter(listAdapter);
				} else {
					listAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	// 2.文化视野
	private class task2 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList2 = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_WENHUA_SHIYE
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList2 != null && artList2.size() > 0) {
				if (listAdapter == null) {
					listAdapter = new expandableadapter(mContext);
					listview.setAdapter(listAdapter);
				} else {
					listAdapter.addNewData();
					listAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	// 3.美誉传播
	private class task3 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList3 = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_MEIYU_CHUANBO
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList3 != null && artList3.size() > 0) {
				if (listAdapter == null) {
					listAdapter = new expandableadapter(mContext);
					listview.setAdapter(listAdapter);
				} else {
					listAdapter.addNewData();
					listAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	// 4.聚焦名家
	private class task4 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList4 = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_JUJIAO_MINGJIA
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList4 != null && artList4.size() > 0) {
				if (listAdapter2 == null) {
					listAdapter2 = new expandableadapter2(mContext);
					listview2.setAdapter(listAdapter2);
				} else {
					listAdapter2.addNewData();
					listAdapter2.notifyDataSetChanged();
				}
			}
		}
	}

	// 5.Sunday艺术汇
	private class task5 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList5 = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_YISHUHUI
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList5 != null && artList5.size() > 0) {
				if (listAdapter2 == null) {
					listAdapter2 = new expandableadapter2(mContext);
					listview2.setAdapter(listAdapter2);
				} else {
					listAdapter2.addNewData();
					listAdapter2.notifyDataSetChanged();
				}
			}
		}
	}

	// 6.特别活动
	private class task6 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				artList6 = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest(null, Constants.YITAN_SPECIAL_ACTIVIES
								+ PageSize + "/" + PageIndex));

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (artList6 != null && artList6.size() > 0) {
				if (listAdapter2 == null) {
					listAdapter2 = new expandableadapter2(mContext);
					listview2.setAdapter(listAdapter2);
				} else {
					listAdapter2.addNewData();
					listAdapter2.notifyDataSetChanged();
				}
			}
		}
	}

	// 艺术课堂
	class expandableadapter extends BaseExpandableListAdapter {
		ArrayList<String> groups = new ArrayList<String>();
		HashMap<String, List<ArtLesson>> childrens = new HashMap<String, List<ArtLesson>>();

		private LayoutInflater li;
		private Context mcontext;

		public expandableadapter(Context c) {
			super();
			groups.add("艺术分享");
			groups.add("文化视野");
			groups.add("美育传播");

			childrens.put("艺术分享", artList);
			childrens.put("文化视野", artList2);
			childrens.put("美育传播", artList3);

			this.mcontext = c;
			li = LayoutInflater.from(c);
		}

		public void addNewData() {
			groups.clear();
			childrens.clear();
			groups.add("艺术分享");
			groups.add("文化视野");
			groups.add("美育传播");

			childrens.put("艺术分享", artList);
			childrens.put("文化视野", artList2);
			childrens.put("美育传播", artList3);

			this.notifyDataSetChanged();
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groups.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition));
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition)).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = li.inflate(R.layout.item_yitan_group, null);
			TextView tv = (TextView) convertView
					.findViewById(R.id.textOfYitanGroupItem);
			tv.setText(groups.get(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = li.inflate(R.layout.item_yitan_child, null);
			TextView title = (TextView) convertView
					.findViewById(R.id.textTitleOfYitanChildItem);
			TextView content = (TextView) convertView
					.findViewById(R.id.textContentOfYitanChildItem);
			final ImageView img = (ImageView) convertView
					.findViewById(R.id.imageOfYitanChildItem);

			ArtLesson art = childrens.get(groups.get(groupPosition)).get(
					childPosition);
			// set data
			title.setText(art.getContentTitle());
			content.setText(art.getSummary());
			AsyncImageLoader asyn = new AsyncImageLoader();
			asyn.loadDrawable(art.getTitleImageName(), new ImageCallback() {

				@Override
				public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
					// TODO Auto-generated method stub
					if (null != imageDrawable) {
						img.setImageBitmap(imageDrawable);
					}
				}
			}, "internet", FromType.home);

			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
	}

	// 译文活动
	class expandableadapter2 extends BaseExpandableListAdapter {
		ArrayList<String> groups = new ArrayList<String>();
		HashMap<String, List<ArtLesson>> childrens = new HashMap<String, List<ArtLesson>>();

		private LayoutInflater li;
		private Context mcontext;

		public expandableadapter2(Context c) {
			super();
			groups.add("聚焦名家");
			groups.add("Sunday艺术汇");
			groups.add("特别活动");

			childrens.put("聚焦名家", artList4);
			childrens.put("Sunday艺术汇", artList5);
			childrens.put("特别活动", artList6);

			this.mcontext = c;
			li = LayoutInflater.from(c);
		}

		/**
		 * 更新数据
		 */
		public void addNewData() {
			groups.clear();
			childrens.clear();

			groups.add("聚焦名家");
			groups.add("Sunday艺术汇");
			groups.add("特别活动");

			childrens.put("聚焦名家", artList4);
			childrens.put("Sunday艺术汇", artList5);
			childrens.put("特别活动", artList6);

			this.notifyDataSetChanged();
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groups.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition));
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childrens.get(groups.get(groupPosition)).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = li.inflate(R.layout.item_yitan_group, null);
			TextView tv = (TextView) convertView
					.findViewById(R.id.textOfYitanGroupItem);
			tv.setText(groups.get(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = li.inflate(R.layout.item_yitan_child, null);
			TextView title = (TextView) convertView
					.findViewById(R.id.textTitleOfYitanChildItem);
			TextView content = (TextView) convertView
					.findViewById(R.id.textContentOfYitanChildItem);
			final ImageView img = (ImageView) convertView
					.findViewById(R.id.imageOfYitanChildItem);

			ArtLesson art = childrens.get(groups.get(groupPosition)).get(
					childPosition);
			// set data
			title.setText(art.getContentTitle());
			content.setText(art.getSummary());
			AsyncImageLoader asyn = new AsyncImageLoader();
			asyn.loadDrawable(art.getTitleImageName(), new ImageCallback() {

				@Override
				public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
					// TODO Auto-generated method stub
					if (null != imageDrawable) {
						img.setImageBitmap(imageDrawable);
					}
				}
			}, "internet", FromType.home);

			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
	}
}
