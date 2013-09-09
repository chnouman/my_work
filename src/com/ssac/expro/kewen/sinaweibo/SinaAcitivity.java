package com.ssac.expro.kewen.sinaweibo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.adapter.Adapter4WeiboSina;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.WeiboSina;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.HttpUtil;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.HttpManager;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;

/**
 * 
 * @author liyan (liyan9@staff.sina.com.cn)
 */
public class SinaAcitivity extends Activity {

	private Weibo mWeibo;
	public static Oauth2AccessToken accessToken;
	public static final String TAG = "sinasdk";
	private List<WeiboSina> listSina;
	private String from="theatre",weibo_id=Constants.WEIBO_SINA_THEATRE_ID;
	private ListView listView;
	private BaseAdapter adapter;
	private ProgressBar progressbarTitle;
	private LinearLayout progressbar;
	private ImageView refresh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_sina_weibo);
		mWeibo = Weibo.getInstance(Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		listView	=	(ListView) findViewById(R.id.listviewOfSinaWeibo);
		progressbar = (LinearLayout) findViewById(R.id.progressBarOfSinaWeibo);
		
		//refrsh
		refresh		=	(ImageView) findViewById(R.id.imageRightOfHeadSinaWeibo);
		progressbarTitle	=	(ProgressBar) findViewById(R.id.progressBarTitleOfHeadSinaWeibo);
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh.setVisibility(View.GONE);
				progressbarTitle.setVisibility(View.VISIBLE);
				loadWeiboTask task=new loadWeiboTask();
				task.execute();
			}
		});
		findViewById(R.id.imageLeftOfHeadSinaWeibo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initData();
	}

	//get the weibo data
	private void initData(){
		from = getIntent().getStringExtra("from");
		//get the local token
		SinaAcitivity.accessToken = AccessTokenKeeper.readAccessToken(this);
		
		if(from!=null){
			if("film".equals(from)){
				weibo_id = Constants.WEIBO_SINA_FILM_ID;
			}else{
				weibo_id = Constants.WEIBO_SINA_THEATRE_ID;
			}
		}
		
		if(checkToken()){
			//get the data
			loadWeiboTask task=new loadWeiboTask();
			task.execute();
		}else{
			mWeibo.anthorize(SinaAcitivity.this, new AuthDialogListener());
		}
	}
	// if the token has expired reAnthorize
	private boolean  checkToken() {
		// TODO Auto-generated method stub
		if (SinaAcitivity.accessToken.isSessionValid()) {
			String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new java.util.Date(SinaAcitivity.accessToken.getExpiresTime()));
			Log.i("poe", date);
			return true;
		} 
		
		return false;
	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {

			String code = values.getString("code");

			String expires_in = values.getString("expires_in");

			Log.i("poe", "code:" + code);
			Log.i("poe", "expires_in:" + expires_in);

			if (code != null) {
				mytask mt = new mytask();
				mt.execute(code);
				return;
			}
			String token = values.getString("access_token");
			Log.i("poe", "token:" + token);
			// 保存accessdtoken
			SinaAcitivity.accessToken = new Oauth2AccessToken(token, expires_in);
			if (SinaAcitivity.accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(SinaAcitivity.accessToken.getExpiresTime()));
				AccessTokenKeeper.keepAccessToken(SinaAcitivity.this, accessToken);
				Toast.makeText(SinaAcitivity.this, "认证成功", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// sso 授权回调
	}

	//access the token by code task
	private class mytask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//show the progress dialog
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String code = params[0];
			String result = request(code);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONObject json = new JSONObject(result);
				String token = json.getString("access_token");
				String expires_in2 = json.getString("expires_in");
				String uid = json.getString("uid");
				// 保存accessdtoken
				SinaAcitivity.accessToken = new Oauth2AccessToken(token, expires_in2);
				AccessTokenKeeper.keepAccessToken(SinaAcitivity.this, accessToken);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//get data
			loadWeiboTask task=new loadWeiboTask();
			task.execute();
		}

		public String request(String code) {
			WeiboParameters params = new WeiboParameters();
			params.add(Constants.CLIENT_ID, Constants.APP_KEY);
			params.add(Constants.CLIENT_SECRET, Constants.APP_SECRET);
			params.add(Constants.GRANT_TYPE, Constants.AUTHORIZATION_CODE);
			params.add(Constants.USER_REDIRECT_URL, Constants.REDIRECT_URL);
			params.add(Constants.CODE, code);
			String result = "";
			try {
				result = HttpManager.openUrl(Constants.API_SERVER, "POST", params, null);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	//after get the token then get the weibo info
	private class loadWeiboTask extends AsyncTask<String, Integer, String> {

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//show the progress bar
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			String strUrl=Constants.URL_WEIBO_TIME_BACH
					+"?"+Constants.ACCESS_TOKEN
					+"="+accessToken.getToken()
					+"&&"+Constants.UIDS+"="+weibo_id;
			 try {
				 if(from.equals("film"))	{
					 listSina =XmlToListService.GetWeiboList(HttpUtil.sendGetRequest( strUrl));
				 }else{
					 listSina =XmlToListService.GetWeiboTheatreList(HttpUtil.sendGetRequest( strUrl));
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}
			return "true";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result!=null&&"true".equals(result)){
				//dismiss the dialog
				if(listSina!=null&&listSina.size()>0){
					//set the list adapter
					if(adapter!=null){
						adapter.notifyDataSetChanged();
					}else{
						adapter	=	new Adapter4WeiboSina(SinaAcitivity.this.getApplicationContext(), listSina);
						listView.setAdapter(adapter);
					}
				}
			}else{
				//tell the user get network fialure
				ExproApplication.throwTipLong("获取数据失败！");
			}
			
			progressbar.setVisibility(View.GONE);
			progressbarTitle.setVisibility(View.GONE);
			refresh.setVisibility(View.VISIBLE);
		}
	}
}
