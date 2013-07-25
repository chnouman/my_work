package com.ssac.expro.kewen.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.ssac.expro.kewen.bean.FromType;

public class AsyncImageLoader {

	public static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>();
	public static List<String> path_list = new ArrayList<String>();

	public String durl = null, dfrom = null;
	public InputStream dinStream = null;

	public void loadDrawable(final String imageUrl,
			final ImageCallback imageCallback, final String tag, final int from) {

		synchronized (AsyncImageLoader.this) {
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
				}
			};

		// 如果已经有了这个下载的线程则不启动
		if(imageCache.get(imageUrl)!=null
				&&!imageCache.get(imageUrl).isRecycled()) {
				Bitmap bitmap = imageCache.get(imageUrl);
				
					Message message = handler.obtainMessage(0, bitmap);
					handler.sendMessage(message);
					
		}else  {
			//图片呗系统回收掉了，则取出此标记 从新区获取新的图片信息
			if (path_list.contains(imageUrl)){
				path_list.remove(imageUrl);
			}
				new Thread() {
					@Override
					public void run() {
						Bitmap drawable = null;
						// 先证明自己抢到了优先下载的权利
						try {
							drawable = loadImageFromUrl(imageUrl, tag, from);
						} catch (Exception e) {

						}
						// store the image url
						if (drawable != null) {
							imageCache.put(imageUrl,drawable);
							path_list.add(imageUrl);
						}
						// remove the top from stack
						if (path_list.size() > 20) {
							removeImageCache();
						}

						Message message = handler.obtainMessage(0, drawable);
						handler.sendMessage(message);
					}
				}.start();
			}
		}
	}

	/**
	 * 删除很久不用的数据
	 */
	public void removeImageCache() {
		synchronized (AsyncImageLoader.this) {
		if(null!=imageCache.get(path_list.get(0))
				&&!imageCache.get(path_list.get(0)).isRecycled()){
			imageCache.get(path_list.get(0)).recycle();
		}
		
		imageCache.remove(path_list.get(0));
		path_list.remove(0);

		}
	}

	/**
	 * 使用本方法时，一般在adapter之前
	 * 
	 * @param url
	 *            本地地址 或者 网络地址
	 * @param tag
	 *            判断是 本的地址 还是 网络地址
	 * @return
	 */
	public  Bitmap loadImageFromUrl(final String url, String tag, final int from) {
		URL m;
		Bitmap d = null;
		if (tag.equals("internet")) {
			try {
				m = new URL(url);
				dinStream = (InputStream) m.getContent();
				if (FromType.home == from) {
					d = GetPicFromURL.savePicture(url, dinStream, from);
				}
			} catch (IOException e) {
				// e.printStackTrace();
			}
		} else {
		}

		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}
}
