package com.ssac.expro.kewen.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;

import com.ssac.expro.kewen.bean.UpdataInfo;

import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Xml;

/**
 * 版本更新使用，如有新的版本了给予通知。
 * 
 * @author caixm
 * 
 */
public class DownLoadManager {
	// 获取服务器端的版本号：
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");// 设置解析的数据源
		int type = parser.getEventType();
		UpdataInfo info = new UpdataInfo();// 实体
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("Versioncode".equals(parser.getName())) {
					info.setVersion(parser.getText()); // 获取版本号
				} else if ("Url".equals(parser.getName())) {
					// info.setUrl(parser.nextText()); //获取要升级的APK文件
					info.setUrl(parser.getText()); // 获取要升级的APK文件
				} else if ("Message".equals(parser.getName())) {
					info.setDescription(parser.getText()); // 获取该文件的信息
				}
				break;
			}
			type = parser.next();
		}
		return info;
	}
	// 从服务器下载apk:
	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					"updata.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}
}
