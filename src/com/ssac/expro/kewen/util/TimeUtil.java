package com.ssac.expro.kewen.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	
	
	/**
	 * 
	 * @return 11:00:00
	 */
	public static String getNow() {
		String nowTime = null;
		Date rightNow = new Date();
		DateFormat format1 = new SimpleDateFormat("HH:mm:ss");
		nowTime = format1.format(rightNow);
		return nowTime;
	}

	/**
	 * 
	 * @return 2013-02-01 11:00:00
	 */
	public static String getNow1() {
		String nowTime = null;
		Date rightNow = new Date();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nowTime = format1.format(rightNow);
		return nowTime;
	}

	/**
	 * 
	 * 获取今、明、后的 日期
	 * 
	 * @param str
	 * @return 2013-02-01
	 */
	public static String getDate(int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar strDate = Calendar.getInstance();
		strDate.add(strDate.DATE, count);
		return sdf.format(strDate.getTime());
	}
	
	/**
	 * 返回 9月6
	 * @param Gmt
	 * @return
	 * @throws ParseException 
	 */
	public static String parseGMT(String Gmt) throws ParseException{
		
		if(Gmt!=null){
		 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); 
		 
		 Date date =sf.parse(Gmt);
		 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd"); 
	     String result = sdf.format(date); 
	     
	     result=result.replace("-", "月");
	     return result;
		}
		return null;
	}
}
