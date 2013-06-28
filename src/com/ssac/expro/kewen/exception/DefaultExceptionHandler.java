package com.ssac.expro.kewen.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.StringTokenizer;

import com.ssac.expro.kewen.util.ErrorLog;
import com.ssac.expro.kewen.util.LogUtil;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * Default exception handler for all activities.
 * 
 * 
 * 
 * @author http://blog.csdn.net/arui319
 * 
 * @version 2011/12/01
 * 
 * 
 */

public class DefaultExceptionHandler implements UncaughtExceptionHandler {

	private Context act = null;

	private ErrorLog mLog = null;

	public DefaultExceptionHandler(Context act) {

		this.act = act;

		try {
			// 检查sd卡
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				mLog = new ErrorLog();
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		// 收集异常信息 并且发送到服务器

		sendCrashReport(ex);

		// 等待半秒

		try {

			Thread.sleep(500);

		} catch (InterruptedException e) {

			//

		}

		// 处理异常

		handleException();

	}

	/**
	 * 收集 crash的信息发送到服务器
	 * 
	 * @param ex
	 */
	private void sendCrashReport(Throwable ex) {

		
		
		StringBuffer exceptionStr = new StringBuffer();

		exceptionStr.append(ex.getMessage());

		StackTraceElement[] elements = ex.getStackTrace();

		for (int i = 0; i < elements.length; i++) {

			exceptionStr.append(elements[i].toString());

		}

		if (null != mLog) {
			LogUtil.log(mLog, ex);
		}

		// TODO
		// 发送收集到的Crash信息到服务器

		// CRMApplication.throwTips("应用崩溃了！正在想服务器发送信息...");
		Log.d("UncathException()", "应用崩溃了");
		Log.d("UncathException()", exceptionStr.toString());
		
	}

	private void handleException() {

		// TODO
		// MainService.exitApp(act);
		final ActivityManager am = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
		am.restartPackage(act.getPackageName());
		
		
		File f = new File(Environment.getExternalStorageDirectory(),"oom-error.hprof");
		String path = f.getAbsolutePath();
		// force a few GC before dumping stuff
		System.gc();
		System.gc();    
		try {
			Debug.dumpHprofData(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 关闭应用
		killProcess(act.getPackageName());
	}

	/**
	 * kill 应用
	 * @param packageName
	 */
	public static void killProcess(String packageName) {
		// TODO Auto-generated method stub

		// String packageName = mAct.getPackageName();
		String processId = "";
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("ps");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null) {
				if (inline.contains(packageName)) {
					break;
				}
			}
			br.close();
			StringTokenizer processInfoTokenizer = new StringTokenizer(inline);
			int count = 0;
			while (processInfoTokenizer.hasMoreTokens()) {
				count++;
				processId = processInfoTokenizer.nextToken();
				if (count == 2) {
					break;
				}
			}
			r.exec("kill -15 " + processId);
		} catch (IOException ex) {
		}
	}

}