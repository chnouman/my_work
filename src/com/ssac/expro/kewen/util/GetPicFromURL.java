package com.ssac.expro.kewen.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;

import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.FromType;

public class GetPicFromURL {
	public static BitmapDrawable getPic(URL url) {
		BitmapDrawable bd = null;
		try {
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			InputStream is = hc.getInputStream();
			bd = new BitmapDrawable(is);
			hc.disconnect();

		} catch (Exception e) {
		}
		return bd;
	}

	/**
	 * 存放图片于本地,and return the Drawable object
	 * 
	 * @param path
	 *            图片的网络 地址
	 * @throws IOException
	 */
	public static Bitmap savePicture(String path, InputStream inStream, int from) throws IOException {
		if (path != null) {
			String state = Environment.getExternalStorageState();

			Bitmap d = null;
			// 存在sd卡
			if (state.equals(Environment.MEDIA_MOUNTED)) {

				String locapic_path = null;
				switch (from) {
				case FromType.home:
					locapic_path = Constants.LOCAPIC_NEWS_PATH;
					break;
				}

				String pic_name = path.substring((path.lastIndexOf("/") + 1), path.length());
				String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + locapic_path;
				// 如果第一次加载创建文件夹
				File f = new File(file_path);
				f.mkdirs();

				// 拼凑绝对路径
				String pic_path = file_path + pic_name;
				File file = new File(pic_path);
				// 本地没有则下载保存到本地
				if (!file.exists()) {
					FileOutputStream outStream = new FileOutputStream(file);
					// 最大存储一兆的图片
					byte[] buffer = new byte[1024 * 1024];
					int len = 0;
					while ((len = inStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					inStream.close();
					outStream.flush();
					outStream.close();
				}
				d = BitMapUtil.getimage(pic_path);
			} else {
				d = BitmapFactory.decodeStream(inStream);
			}

			return d;
		} else {
			return null;
		}
	}

	/**
	 * 返回本地的存储路径
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String savePicture(String path) throws IOException {
		String pic_name = path.substring((path.lastIndexOf("/") + 1), path.length());
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.LOCAPIC_NEWS_PATH+"/";
		// 如果第一次加载创建文件夹
		File f = new File(file_path);
		f.mkdirs();

		// 拼凑绝对路径
		String pic_path = file_path + pic_name;
		File file = new File(pic_path);
		// 本地没有则下载保存到本地
		if (!file.exists()) {
			URL m = new URL(path);
			InputStream inStream = (InputStream) m.getContent();
			if (inStream != null) {

				FileOutputStream outStream = new FileOutputStream(file);
				// 最大存储一兆的图片
				byte[] buffer = new byte[1024 * 1024];
				int len = 0;
				while ((len = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				inStream.close();
				outStream.flush();
				outStream.close();
			}
		}
		
		return "file://"+pic_path;
	}

	// 把bitmap转换成String
	public static Bitmap bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		String str_bitmap = Base64.encodeToString(b, Base64.DEFAULT);
		ByteArrayInputStream stringInputStream = new ByteArrayInputStream(str_bitmap.getBytes());

		Bitmap result = BitmapFactory.decodeStream(stringInputStream);

		return result;
	}

	/**
	 * 简单缩放到480*800
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 单个图片语序的最大值放大到500kB
	 * 
	 * @param Size
	 * @return
	 */
	private static BitmapFactory.Options getSampleOption(long Size) {

		BitmapFactory.Options option = new Options();

		long fileSize = Size / 1024;// kB

		int scaleParam = 1;

		// 200kB以下
		if (fileSize < MAX_SINGLE_PICK_SIZE) {
			scaleParam = 1;
		} else {
			scaleParam = (int) (fileSize / MAX_SINGLE_PICK_SIZE);
		}

		option.inSampleSize = scaleParam;

		return option;
	}

	public static final int MAX_SINGLE_PICK_SIZE = 50;
}
