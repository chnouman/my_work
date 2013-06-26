package com.ssac.expro.kewen;

import java.util.ArrayList;
import java.util.List;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList.lastIndexLoad;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.view.SlideHolder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
/**
 * 演出
 * @author poe
 */
public class FragementPeiXun extends Fragment implements OnClickListener {

	private List<Huizhan> filmList= new ArrayList<Huizhan>();
	private LinearLayout progressbar;
	private int PageSize = 10;
	private int PageIndex = 1;// 从1开始
	private Context mContext;
	//影片
	private BaseAdapter filmAdapter;
	private SlideHolder mSlideHoler;
	private ListView listView;
	
	public FragementPeiXun(Context mContext,SlideHolder mSlideHoler) {
		super();
		this.mContext = mContext;
		this.mSlideHoler=mSlideHoler;
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
		if (container == null) {
			return null;
		}
		container = (ViewGroup) inflater.inflate(R.layout.layout_huizhan, null);
		
		ImageView img_back_film=(ImageView) container.findViewById(R.id.imageLeftOfHeadShow);
		img_back_film.setOnClickListener(this);

		progressbar = (LinearLayout) container	.findViewById(R.id.progressOfHuiZhan);
		listView		=	(ListView) container.findViewById(R.id.listviewofHuiZhan);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if(arg3!=-1){
						Huizhan hz=filmList.get(arg2);
				
						Intent intent=new Intent(mContext,HuizhanDetail.class);
						intent.putExtra("showID", hz.getShowID());
						intent.putExtra("title", hz.getShowTitle());
						intent.putExtra("time", hz.getBeginTime());
						intent.putExtra("address", hz.getAddress());
						intent.putExtra("img", hz.getShowImg());
						
						startActivity(intent);
				}
			}
		});
		
		task4HuiZhan ts1 = new task4HuiZhan();
		ts1.execute();
		
		return container;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageLeftOfHeadShow:
			mSlideHoler.toggle(); 
			break;
		default:
			break;
		}
	}

	//去获取coverflow所需要得广告图片
	private class task4HuiZhan extends AsyncTask<String, String, String> {

		private List<Huizhan> hlist=new ArrayList<Huizhan>();
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(PageIndex<2)
			progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载演出资讯...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				hlist = XmlToListService.GetHuiZhan(HttpUtil.sendGetRequest(null,
						Constants.HUIZHAN_LIST+PageSize+"/"+PageIndex));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
				if(PageIndex>1){
					PageIndex--;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			if(hlist!=null&&hlist.size()>0){
				filmList.addAll(hlist);
				filmAdapter = new Adapter4HuiZhanList(mContext,filmList,new lastIndexLoad() {
					
					@Override
					public void loadData() {
						// TODO Auto-generated method stub
						PageIndex++;
						task4HuiZhan ts=new task4HuiZhan();
						ts.execute();
					}
				});
				
				listView.setAdapter(filmAdapter);
				
				if(PageIndex==1){
					mSlideHoler.toggle();
				}
				//提一条影片数据
			}else{
				Log.i("poe", "获取广告数据失败！");
			}
			progressbar.setVisibility(View.GONE);
		}
	}
}
