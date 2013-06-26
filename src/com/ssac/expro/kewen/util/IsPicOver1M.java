package com.ssac.expro.kewen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class IsPicOver1M {
	private static boolean over=true;
	
	public static boolean Over1M(String absolutePath) throws IOException{
		
		
		File file=new File(absolutePath);
		FileInputStream fis=new FileInputStream(file);
		byte[] buffer=new byte[1024*1024];
			int read=fis.read(buffer);
			if (read < buffer.length) { 
			over=false;
			}
		return over;
	}

}
