package com.ssac.expro.kewen.util;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.webkit.CacheManager;

public class GenericUtil {

	/**
	 * 空值判断
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0||"".equals(s);
	}
	
	/**
	 * 获取当前的 日期  2012-1-1
	 * @return
	 */
	public static String getCurrentDate() {
		String result = null;
		result += Calendar.YEAR + "-" +Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH;
		return result;
	}
	
	/**
	 * 正则表达式 ，规范 （null)替换掉
	 * @param data
	 * @return
	 */
	public static String DectorData(String data){
		
		String d="";
		//判断 data是否包含< 如果包含 就 先找到 < 和  >的 位置 
//		d = data.replaceAll("<[.[^<]]*>|&\\w+;", "");
		d = data.replaceAll("\\((null)\\)", "");
		
		return d;
		
	}
	
	
	/**
	 * 验证电话号 
	 * --正确格式为：XXXX-XXXXXXX，XXXX-XXXXXXXX，XXX-XXXXXXX，XXX-XXXXXXXX，XXXXXXX，XXXXXXXX。
	 * @param mobiles
	 * ^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$
	 * @return
	 */
	public static boolean isPhoneNO(String mobiles){     
        Pattern p = Pattern.compile("^(\\d{3,4}|\\d{3,4}\\s*-\\s*)?\\d{7,8}$");     
        Matcher m = p.matcher(mobiles);     
        return m.matches();     
    } 
	/**
	 * 验证手机号 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);     
        return m.matches();     
    } 
   
	/**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
    public static boolean isEmail(String email){     
     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);     
        return m.matches();     
    } 
    
	/**
	 * 根据 need_find 在total 字符串里面搜寻 所在位置
	 * 
	 * @param need_find
	 *            搜寻的字符串
	 * @param total
	 *            本搜索的范围
	 * @return	查询不到返回 -1
	 */
	public static int findIndex(String need_find, String[] total) {
		
		int index = -1;
		
		if (null!=total&&total.length > 0) {
			
			for (int i = 0; i < total.length; i++) {
				
				if (need_find.equals(total[i])) {// 只有第一次发现的有效
					
					index = i;
					
					break;
					
					}
				}
			
			}
		
		return index;
		
	}
	
	/**
	 * 应用退出前，清楚缓存的方法
	 */
	public static void clearnCache() {
		
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
		
	}
	
	/**
	 * 处理日期 只取到 天
	 * @param date 2012-08-25 00:00:00.0
	 * 
	 * @return 2012-08-25
	 */
	public static String DateFormat(String date){
		
		String result =null;
		
		if(date!=null){
			
			if(date.length()>11){
				result = date.split(" ")[0];
			}else{
				result =date;
			}
		}
		
		return result;
		
	}
	
	/**
	 * 判断手机号码是否合法，并返回true or false 的判断值 
	 * @param phoneNumber
	 * @return
	 */
	 public static boolean isPhoneNumberValid(String phoneNumber)
	 {
	  boolean isValid = false;
	  
	  String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";
	  
	  String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
	  CharSequence inputStr = phoneNumber;
	  
	  Pattern pattern = Pattern.compile(expression);
	  
	  Matcher matcher = pattern.matcher(inputStr);
	
	  Pattern pattern2 = Pattern.compile(expression2);
	  
	  Matcher matcher2 = pattern2.matcher(inputStr);
	  if (matcher.matches()|| matcher2.matches())
	  {
	   isValid = true;
	  }
	  return isValid;
	 }
	 
//	 /**
//	  * 还有一种更简洁地判断手机号码的方法，如下：
//	  * @param mobiles
//	  * @return
//	  */
//	  public static boolean isMobileNum(String mobiles)
//	  {
//	   Pattern p = Pattern
//	     .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
//	   Matcher m = p.matcher(mobiles); 
//	   return m.matches();
//	  }
//	  
//	  /**
//	   * 邮箱判断格式是否正确
//	   * @param eMAIL1
//	   * @return
//	   */
//	  public static boolean isEmailFormat(String eMAIL1) {//邮箱判断正则表达式
//			 Pattern pattern = Pattern
//			 .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//			 Matcher mc = pattern.matcher(eMAIL1);
//			 return mc.matches();
//	  }
}
