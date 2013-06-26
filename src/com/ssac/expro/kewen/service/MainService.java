package com.ssac.expro.kewen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import com.ssac.expro.kewen.BaseActivity;
import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.R;
import com.ssac.expro.kewen.bean.AD;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.util.GenericUtil;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.util.LogUtil;

public class MainService extends Service implements Runnable {

	private String TAG = LogUtil.makeLogTag(MainService.this.getClass());
	public static boolean isrun = false;
	private static Queue<Task> allTask = new LinkedList<Task>();
	private static ArrayList<BaseActivity> allActivity = new ArrayList<BaseActivity>();
	
	public static List<AD> adList = new ArrayList<AD>();
	public static List<Art> artList= new ArrayList<Art>();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		isrun = true;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void onDestroy() {


		isrun = false;

		super.onDestroy();

		allTask = null;
		allActivity = null;
	}

	@Override
	public void run() {
		while (isrun) {
			Task task = null;
			if (!allTask.isEmpty()) {
				task = allTask.poll();
				if (null != task) {
					try {
						doTask(task);
					} catch (Exception e) {
						LogUtil.log(ExproApplication.mLog, e);
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void addActivity(BaseActivity wa) {
		allActivity.add(wa);
	}

	public static void removeActivity(BaseActivity wa) {

		allActivity.remove(wa);
	}

	public static BaseActivity getActivityByName(String aname) {
		for (BaseActivity wa : allActivity) {
			if (wa.getClass().getName().indexOf(aname) >= 0) {
				return wa;
			}
		}
		return null;
	}

	
	private Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			
			case TaskType.GET_HOME: {
				if (MainService.getActivityByName("Activity_Home") != null)
					MainService.getActivityByName("Activity_Home").refresh(msg.what);
				break;
			}
			case TaskType.GET_YANCHU:
			case TaskType.GET_YANCHU_ZX: 
			case TaskType.GET_DIANYING://首页 广告信息获取
			case TaskType.GET_HUIZHAN://首页 广告信息获取
			case TaskType.GET_PEIXUN://首页 广告信息获取
			case TaskType.GET_MEISHUGUAN://首页 广告信息获取
			case TaskType.GET_YITAN://首页 广告信息获取
			if (MainService.getActivityByName("Activity_SlidingMenue") != null){
				MainService.getActivityByName("Activity_SlidingMenue").refresh(msg.what);
			}
			break;
			case TaskType.GET_ART:
				if (MainService.getActivityByName("Activity_Art") != null){
					MainService.getActivityByName("Activity_Art").refresh(msg.what);
				}
				break;
		}
	 }
	};

	public static void newTask(Task ts) {
		
		allTask.add(ts);
		
	}

	private void doTask(Task ts) throws Exception {
		Message message = hand.obtainMessage();
		message.what = ts.getTaskID();
		// 获取参数
		HashMap<String, Object> hashParam = (HashMap<String, Object>) ts.getTaskParam();

		switch (ts.getTaskID()) {

		case TaskType.GET_HOME://首页 广告信息获取
			
			List<AD> list_ad = XmlToListService.GetAD(
								HttpUtil.sendGetRequest(null,Constants.HOME_AD)
								);
			
			if(null!=list_ad){
				
				MainService.adList.clear();
				
				MainService.adList.addAll(list_ad);
				
			}
			break;
		case TaskType.GET_YANCHU://首页 广告信息获取
				break;
		case TaskType.GET_DIANYING://首页 广告信息获取
			break;
		case TaskType.GET_HUIZHAN://首页 广告信息获取
			break;
		case TaskType.GET_PEIXUN://首页 广告信息获取
			break;
		case TaskType.GET_MEISHUGUAN://首页 广告信息获取
			break;
		case TaskType.GET_YITAN://首页 广告信息获取
			break;
		case TaskType.GET_ART://每月易迅
			Art art=XmlToListService.GetArt(HttpUtil.sendGetRequest(null,Constants.MONTH_ART));
			artList.add(art);
			break;
		}
		allTask.remove(ts);
		hand.sendMessage(message);
	}

	public static void alertNetError(final Context context) {
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		ab.setTitle("网络异常");
		ab.setMessage("网络连接异常，请设置网络或退出程序");
		ab.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				context.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				dialog.cancel();
			}
		});
		ab.setNegativeButton("退出程序", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(context, MainService.class);
				context.stopService(it);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		ab.create().show();
	}

	/**
	 * 关闭管理器里面的所有 activity
	 * 
	 * @param con
	 */
	public static void exitApp(Context con) {

		for (Activity ac : allActivity) {
			ac.finish();
		}

		allTask = null;
		allActivity = null;

		Intent it = new Intent("com.cnsaas.fycrm.service.MainService");
		con.stopService(it);
		// // 先清楚缓存
		GenericUtil.clearnCache();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 程序退出
	 * 
	 * @param con
	 */
	public static void promptExit(final Context con) {
		LayoutInflater li = LayoutInflater.from(con);
		View exitV = li.inflate(R.layout.exitdialog, null);
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setView(exitV);
		ab.setPositiveButton(R.string.confirm, new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
//				// 记录本次退出的tag
//				String favorite_page = con.getClass().getName();
//				CRMApplication.pushPreferenceData("favorite_page",
//						favorite_page);

				// 把缓存的数据都释放掉 节省资源

				exitApp(con);
			}
		});

		ab.setNegativeButton(R.string.cancel, null);
		ab.show();
	}
}
