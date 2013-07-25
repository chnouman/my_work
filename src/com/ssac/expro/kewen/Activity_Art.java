package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.HashMap;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.util.GetPicFromURL;

public class Activity_Art extends BaseActivity implements OnClickListener {

	private ImageView imageView;
	private LinearLayout lin_home, lin_art, lin_vip, lin_youhui, lin_more;
	private WebView webView=null;
	private LinearLayout progressbar;
	final static int PROGRESS_DIALOG_CONNECTING = 1000;
	ProgressDialog loadingProgressDialog = null;
	boolean blockLoadingNetworkImage = false;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String path =(String) msg.obj;
			if(null!=path&&webView!=null){
			webView.loadUrl(path);
			showDialog(PROGRESS_DIALOG_CONNECTING);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_art);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		progressbar = (LinearLayout) findViewById(R.id.progressOfArt);
		imageView = (ImageView) findViewById(R.id.imageLeftOfHeadArt);
		webView = (WebView) findViewById(R.id.webviewOfArt);
		lin_home = (LinearLayout) findViewById(R.id.linearHOme);
		lin_art = (LinearLayout) findViewById(R.id.linearNews);
		lin_vip = (LinearLayout) findViewById(R.id.linearVip);
		lin_youhui = (LinearLayout) findViewById(R.id.linearYouHui);
		lin_more = (LinearLayout) findViewById(R.id.linearMore);

		lin_home.setOnClickListener(this);
		lin_art.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
		lin_youhui.setOnClickListener(this);
		lin_more.setOnClickListener(this);

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
						dismissDialog(PROGRESS_DIALOG_CONNECTING);
				}
			}
		});

		// 通知服务获取ad数据
		HashMap<String, Object> hm = new HashMap<String, Object>();
		Task ts = new Task(TaskType.GET_ART, hm);
		MainService.newTask(ts);
	}

	protected void onResume() {
		super.onResume();
		Log.i("poe", "onResume");
		if (webView.getProgress() < 100)
			showDialog(PROGRESS_DIALOG_CONNECTING);
	}

	protected void onDestroy() {
		Log.i("poe", "onDestroy()");
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setVisibility(View.GONE);
		webView.destroy();
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG_CONNECTING: {
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage(getResources().getString(R.string.loading));
			loadingProgressDialog = progressDialog;
			return progressDialog;
		}
		default:
			break;
		}
		return null;
	}

	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch (id) {
		case PROGRESS_DIALOG_CONNECTING: {
			loadingProgressDialog.setMax(100);
			dialog.show();
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		if (MainService.artList != null && MainService.artList.size() > 0) {
			progressbar.setVisibility(View.GONE);
			final Art art = MainService.artList.get(0);

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
//			handler.postDelayed(r, 200);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);
//			MainService.removeActivity(Activity_Art.this);
//			finish();
			break;
		case R.id.linearNews:
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
//			MainService.removeActivity(Activity_Art.this);
//			finish();
			break;
		case R.id.linearYouHui:
			ExproApplication.showBuildTip(Activity_Art.this);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
//			MainService.removeActivity(Activity_Art.this);
//			finish();
			break;
		default:
			break;
		}
		
	}

}
