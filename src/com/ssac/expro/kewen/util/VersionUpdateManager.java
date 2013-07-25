package com.ssac.expro.kewen.util;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ssac.expro.kewen.Activity_Home;
import com.ssac.expro.kewen.ExproApplication;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.bean.UpdataInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Log;

/**
 * 管理应用的升级
 * 
 * @author poe.Cai
 * 
 */
public class VersionUpdateManager {

	// private Handler handler;
	//
	// public VersionUpdateManager(Handler handler) {
	// super();
	// this.handler = handler;
	// }
	private Context mContext;
	private UpdataInfo info;
	private Handler handler_update_version = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case TaskType.UPDATA_CLIENT:
				info = (UpdataInfo) msg.obj;
				showUpdataDialog();
				break;
			case TaskType.GET_UNDATAINFO_ERROR:
//				ExproApplication.throwTips("获取服务器更新信息失败");
				break;
			case TaskType.DOWN_ERROR:
				ExproApplication.throwTips("下载新版本失败");
				break;
			case TaskType.NO_UPDATE:
				// 不是更新后第一次登陆
				if (ExproApplication.getPreferenceData(ExproApplication.version) != null) {
					// checkTask ts = new checkTask();
					// ts.execute();
				} else {
					// 首次登陆 给出 提示页面 跳转到 登录页面
					GenericUtil.clearnCache();
					// 2清除launch
				}
				break;

			}
		}
	};

	/**
	 * 一步到位的检测下载新的apk升级包
	 * @param c
	 */
	public void CheckVersion(Context c) {
		mContext =c;
		Thread t = new Thread(new Runnable() {
			private Message msg=handler_update_version.obtainMessage() ;
			@Override
			public void run() {
				  try {      
//			            URL url = new URL(com.ssac.expro.kewen.bean.Constants.UPDATE_VERSION); 
//			            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();    
//			            conn.setConnectTimeout(3000);
			            InputStream is =HttpUtil.httpgetInputsteam(com.ssac.expro.kewen.bean.Constants.UPDATE_VERSION);
//			            conn.getInputStream();    
			            UpdataInfo info =  DownLoadManager.getUpdataInfo(is);   
			               
			            if(info.getVersion().equals(ExproApplication.version)){   
			                Log.i("update","版本号相同无需升级");   
			                msg.what = TaskType.NO_UPDATE; 
			            }else{   
			                Log.i("update","版本号不同 ,提示用户升级 ");   
			                msg.what = TaskType.UPDATA_CLIENT;   
			                msg.obj = info;
			            }   
			        } catch (Exception e) {   
			            msg.what = TaskType.GET_UNDATAINFO_ERROR;   
			        } finally{
			        	handler_update_version.sendMessage(msg);
			        }
			}
		});
		t.start();
	}

	
	
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new AlertDialog.Builder(mContext);
		builer.setTitle("版本升级");
		builer.setMessage(info.getDescription());
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				downLoadApk();
			}
		});
		builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				}
		});
		//禁止 从 dialog的outside消灭对话框
		builer.setCancelable(false);
		builer.show();
	}

	protected void downLoadApk() {
		final ProgressDialog pd;
		pd = new ProgressDialog(mContext);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(
							info.getUrl(), pd);
//					sleep(3000);
					installApk(file);
					pd.dismiss();
				} catch (Exception e) {
					LogUtil.log(ExproApplication.mLog, e);
				}
			}
		}.start();
		pd.setCancelable(false);
	}
	
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
		GenericUtil.clearnCache();
		((Activity_Home) mContext).finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
