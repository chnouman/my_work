package com.ssac.expro.kewen.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * 苏艺影城购票app的工具类
 * @author poe.Cai
 *
 */
public class AppUtil {

	private static String package_buy ="com.ykse.ticket.kewen";
	
	
	/**
	 * 苏艺影城 是否安装
	 * @return true :已安装 false：未安装
	 */
	public static boolean checkInstall(Context mContext){
		
		PackageInfo packageInfo;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(
            		package_buy, 0);

        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        
        List<PackageInfo> list =getAllApps(mContext);
        if(list!=null){
        	for(PackageInfo p:list){
        		Log.i("2222", p.packageName);
        	}
        }
        if(packageInfo ==null){
        	return false;
        }else{
        	return true;
        }
	}
	
	
	/** 
	 * 查询手机内非系统应用 
	 * @param context 
	 * @return 
	 */  
	public static List<PackageInfo> getAllApps(Context context) {  
	    List<PackageInfo> apps = new ArrayList<PackageInfo>();  
	    PackageManager pManager = context.getPackageManager();  
	    //获取手机内所有应用  
	    List<PackageInfo> paklist = pManager.getInstalledPackages(0);  
	    for (int i = 0; i < paklist.size(); i++) {  
	        PackageInfo pak = (PackageInfo) paklist.get(i);  
	        //判断是否为非系统预装的应用程序  
	        if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {  
	            // customs applications  
	            apps.add(pak);  
	        }  
	    }  
	    return apps;  
	}  
}
