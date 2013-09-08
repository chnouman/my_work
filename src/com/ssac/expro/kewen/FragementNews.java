package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList;
import com.ssac.expro.kewen.adapter.Adapter4HuiZhanList.lastIndexLoad;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.GetPicFromURL;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.view.SlideHolder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
/**
 *  month art info
 * @author poe
 */
public class FragementNews extends Fragment implements OnClickListener {

	Art art=null;
	private ImageView imageView;
	private WebView webView=null;
	private LinearLayout progressbar;
	final static int PROGRESS_DIALOG_CONNECTING = 1000;
	ProgressDialog loadingProgressDialog = null;
	boolean blockLoadingNetworkImage = false;
	private Context mContext;
	//影片
	private SlideHolder mSlideHoler;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String path =(String) msg.obj;
			if(null!=path&&webView!=null){
			webView.loadUrl(path);
			((Activity) mContext).showDialog(PROGRESS_DIALOG_CONNECTING);
			}
		}
	};
	
	public FragementNews(Context mContext, SlideHolder mSlideHoler) {
		super();
		this.mContext = mContext;
		this.mSlideHoler = mSlideHoler;
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
		container = (ViewGroup) inflater.inflate(R.layout.layout_art, null);
		
		progressbar = (LinearLayout)container.findViewById(R.id.progressOfArt);
		imageView = (ImageView) container.findViewById(R.id.imageLeftOfHeadArt);
		webView = (WebView) container.findViewById(R.id.webviewOfArt);

		imageView.setOnClickListener(this);
		// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				Log.i("poe", "onPageFinished");
				progressbar.setVisibility(view.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
				Log.i("poe", "onReceivedError:" + errorCode + description);
//				refresh(null);

			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				progressbar.setVisibility(view.VISIBLE);
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

		});

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (loadingProgressDialog != null && loadingProgressDialog.isShowing())
					loadingProgressDialog.setProgress(progress);
				if (progress >= 100) {
					if (blockLoadingNetworkImage) {
						webView.getSettings().setBlockNetworkImage(false);
						blockLoadingNetworkImage = false;
					}
					if (loadingProgressDialog != null && loadingProgressDialog.isShowing())
						((Activity) mContext).showDialog(PROGRESS_DIALOG_CONNECTING);
				}
			}
		});

		// 通知服务获取ad数据
		task4Art ts =new task4Art();
		ts.execute();
		
		return container;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageLeftOfHeadArt:
			mSlideHoler.toggle(); 
			break;
		default:
			break;
		}
	}

	private class task4Art extends AsyncTask<String, String, String> {

		private List<Huizhan> hlist=new ArrayList<Huizhan>();
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				 art=XmlToListService.GetArt(HttpUtil.sendGetRequest(Constants.MONTH_ART));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！"+e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			progressbar.setVisibility(View.GONE);
			if(art!=null){
				new Thread( new Runnable() {
					public void run() {
						if (null != art.getImgPath()) {
							try {
								final String path = GetPicFromURL.savePicture(art.getImgPath());
								handler.sendMessage(handler.obtainMessage(0, path));
								Log.i("poe", "本地缓存图片" + path);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								ExproApplication.throwTips("网络异常！");
							}
						}
					}
				}).start();
		}
	}
	}
}
