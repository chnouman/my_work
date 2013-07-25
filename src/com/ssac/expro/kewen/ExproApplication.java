package com.ssac.expro.kewen;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ssac.expro.kewen.util.ErrorLog;

public class ExproApplication extends Application {

	public static SharedPreferences sp = null;
	public static ExproApplication crmApp;
	public static String version = "";
	public static DisplayMetrics metrics =null;
	public static ErrorLog mLog = null;
	public static ImageLoader imageLoader=ImageLoader.getInstance();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		crmApp = this;
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		try {
			//检查sd卡
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				mLog = new ErrorLog();
			} else {

				Toast.makeText(crmApp, "sd卡不可用、日志系统自动关闭！", Toast.LENGTH_LONG)
						.show();
			}
		} catch (IOException e) {
		}
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		//检测 当前版本 ，并生成当前版本的V+版本号
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			 version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 没有网络的提示
	public static void NoNetWorkTips() {
		Toast.makeText(crmApp, "您的网络不可用,请设置！", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 提示正在建设中
	 */
	public static void showBuildTip(Context c){
		//弹出提示框
		new AlertDialog.Builder(c)
					.setTitle("提示")
					.setMessage("正在建设中...")
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).show();
	}

	// 剪贴板复制提示
	public static void throwTips(String str) {
		
		Toast.makeText(crmApp, str, 2200).show();
		
	}

	// 长提示，短提示
	public static void throwTipLong(String str) {
		Toast.makeText(crmApp, str, Toast.LENGTH_LONG).show();
	}
	

	/**
	 * 存放token的方法
	 * 
	 * @param tag
	 * @param data
	 */
	public static void pushPreferenceData(String tag, String data) {
		Editor editor = sp.edit();
		editor.putString(tag, data);
		editor.commit();
	}

	/**
	 * 取出参数token
	 * 
	 * @param tag
	 *            标记
	 * @return
	 */
	public static String getPreferenceData(String tag) {

		return sp.getString(tag, null);
	}
}
