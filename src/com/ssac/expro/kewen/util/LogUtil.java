/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssac.expro.kewen.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class for LogCat. 返回： FYCRM_+类的名称
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class LogUtil {

	private static boolean ISOPEN = true;

	@SuppressWarnings("unchecked")
	public static String makeLogTag(Class cls) {
		return "FYCRM_" + cls.getSimpleName();
	}

	/**
	 * 打印log
	 * 
	 * @param message
	 * @param e
	 */
	public static void log(ErrorLog mLog, Throwable e) {
		if (ISOPEN) {
			if (e != null) {
				e.printStackTrace();
				if (mLog != null) {
					try {
						mLog.println("Case By :" + e.toString());
					} catch (IOException ex) {
					}
				}
				if (e != null) {
					StackTraceElement[] stacks = e.getStackTrace();
					if (stacks.length > 0) {
						for (int i = 0; i < stacks.length; i++) {
							String tip = stacks[i].toString();
							if (mLog != null) {
								try {
									mLog.println(tip);
								} catch (IOException ex) {
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 打印一段文字
	 * 
	 * @param mLog
	 * @param e
	 */
	public static void log(ErrorLog mLog, String e) {
		if (e != null) {
			try {
				mLog.println(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void logOut2Sdcard(String data, String outputfile) {
		try {
			FileOutputStream out = new FileOutputStream(new File(outputfile));
			ByteArrayInputStream bis = new ByteArrayInputStream(data.getBytes());
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
