/*
 * $Id$
 */

package com.ssac.expro.kewen.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
//import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class ErrorLog {
	private String mPath; // log的当前 路径
//	private Writer mWriter; // 打印机
	private FileOutputStream fos;
	private BufferedOutputStream bout;
	private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat(
			"[HH:mm:ss] "); // 时间戳

	public ErrorLog() throws IOException {
		File sdcard = Environment.getExternalStorageDirectory();
		File logDir = new File(sdcard, "fy_crm/log/");

		if (!logDir.exists()) {
			logDir.mkdirs();
			// do not allow media scan
			new File(logDir, ".nomedia").createNewFile();
		}
		open(logDir.getAbsolutePath() + "/error.log" + "-" + getTodayString());
	}

	public ErrorLog(String basePath) throws IOException {
		open(basePath);
	}

	/**
	 * 
	 * @param basePath
	 * @throws IOException
	 */
	protected void open(String basePath) throws IOException {

		fos = new FileOutputStream(basePath, true);
		bout = new BufferedOutputStream(fos);

		mPath = basePath;
		// bout.write("Opened log.".getBytes());
		// bout.flush();
		// bout.close();

		// File f = new File(basePath + "-" + getTodayString());
		// File f = new File(basePath);
		// mPath = f.getAbsolutePath();
		// mWriter = new BufferedWriter(new FileWriter(mPath), 10*1024*8);
		// println("Opened log.");
	}

	/**
	 * 获取当前时间年月日-时分秒
	 * 
	 * @return
	 */
	private static String getTodayString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}

	/**
	 * 获取当前打开的log路径
	 * 
	 * @return
	 */
	public String getPath() {
		return mPath;
	}

	/**
	 * 向文件里面写数据 log日志
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void println(String message) throws IOException {
		// mWriter.write(TIMESTAMP_FMT.format(new Date()));
		// mWriter.write(message);
		// mWriter.write('\n');
		// mWriter.flush();
		if(message!=null){
			bout.write(TIMESTAMP_FMT.format(new Date()).getBytes());
			bout.write(message.getBytes());
			bout.write('\n');
			bout.flush();
		}

	}


	public void close() throws IOException {
//		mWriter.close();
		bout.close();
	}
}
