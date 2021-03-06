package com.ssac.expro.kewen.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpUtil {

	private static String TAG = "HttpUtil";
	public static boolean isWap = false;
	private static int ONE_SECOND = 1000;

	public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static String sendGetRequest(String strUrl) throws IOException {
		String result = null;
		BufferedReader br = null;
		
		InputStream instream = getInputsteamByAppache(strUrl);
		
		if (instream == null) {
			instream = httpgetInputsteam(strUrl);// (InputStream)
		}
		
		if (instream == null) {
			instream = httpgetInputsteam(strUrl);// (InputStream)
		}
		// http://58.210.186.110/fyservice.svc/fy/notice/1/1/xml
		if (null != instream) {
			br = new BufferedReader(new InputStreamReader(instream));
			StringBuilder sBuilder = new StringBuilder();
			String line = "";
			while ((line = br.readLine()) != null) {
				sBuilder.append(line);
			}
			instream = null;
			result = sBuilder.toString();
			sBuilder = null;
			Log.d(TAG, "response=" + result);
		}
		return result;
	}

	public static String sendPostRequest(HashMap<String, Object> params, String strUrl) {
		String result = null;
		BufferedReader br = null;
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		InputStream instream = null;

		try {
			StringBuilder paraUrl = new StringBuilder();
			int index = 0;
			if (params != null && !params.isEmpty()) {
				for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
					Map.Entry e = (Map.Entry) it.next();
					if (index > 0)
						paraUrl.append("&");
					paraUrl.append(e.getKey().toString());
					paraUrl.append("=");
					paraUrl.append(URLEncoder.encode(e.getValue().toString(), "utf-8"));
					index++;
				}
			}
			URL url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(paraUrl.toString());
			out.flush();

			StringBuilder sBuilder = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				instream = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(instream, "utf-8");
				int ichar;
				while ((ichar = isr.read()) != 0) {
					sBuilder.append((char) ichar);
				}
				result = sBuilder.toString();
			}
		} catch (Exception e) {
			Log.d("HTTP", e.getMessage());
		} finally {
			if (null != connection)
				connection.disconnect();
			try {
				if (null != out)
					out.close();
			} catch (IOException ioe) {
				Log.d("HTTP", ioe.getMessage());
			}
		}
		return result;
	}

	/**
	 * 获取输入流
	 * 
	 * @param address
	 * @return
	 */
	public static InputStream httpgetInputsteam(String address) {
		InputStream inStream = null;

		HttpURLConnection conn = null;
		try {

			// URL url=new
			// URL("http://10.0.44.56:8002/SyncService/Request.aspx?account_no=april&acc_pwd=123456&data_type=SynBusinessAll&last_sync=2011-01-01%200:0:0");
			URL url = new URL(address.trim());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			connectServer(conn);
			inStream = conn.getInputStream();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inStream;
	}

	
	public static void connectServer(HttpURLConnection conn) {
		boolean success = false;
		int count = 0;
		while (!success && count < 3) {
			success = true;
			try {
				conn.connect();
			} catch (Exception e) {
				count++;
				success = false;
			}
		}

	}
	/**
	 * 以后所有的 输入流都采用这个 方法来获取 ，也可以 获取 字符串。
	 * 
	 * @param address
	 * @return
	 */
	public static InputStream getInputsteamByAppache(String address) {
		
		InputStream inStream = null;

		// 使用get请求
		HttpGet getRequest = new HttpGet(address);

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(),
				3 * ONE_SECOND);
		HttpConnectionParams.setSoTimeout(client.getParams(), 5 * ONE_SECOND);
		try {
			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inStream = response.getEntity().getContent();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // 221.224.52.112:80
		}
		return inStream;
	}
}
