package com.ssac.expro.kewen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.ssac.expro.kewen.adapter.Adapter4Yingyuan;
import com.ssac.expro.kewen.adapter.Adapter4YingyuanActivities;
import com.ssac.expro.kewen.adapter.Adapter4YingyuanActivities.lastIndexLoad;
import com.ssac.expro.kewen.adapter.PaperAdapter;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.ShowInfo;
import com.ssac.expro.kewen.service.XmlToListService;
import com.ssac.expro.kewen.util.ImageCacheUtil;
import com.ssac.expro.kewen.util.HttpUtil;
import com.ssac.expro.kewen.view.MirrorView;
import com.ssac.expro.kewen.view.ReflectionImage;
import com.ssac.expro.kewen.view.SlideHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 演出
 * 
 * @author poe
 */
@SuppressLint("ValidFragment")
public class FragementFilm extends Fragment implements OnClickListener {

	private List<Film> filmList = new ArrayList<Film>();
	private ViewPager mViewPager;
	private LinearLayout lin_yingpian, lin_yingyuan, lin_huodong;
	private LayoutInflater lin;
	// private LinearLayout progressbar;
	private PagerAdapter adapter;
	private int toId = 0;
	private int PageSize = 10;
	private int PageIndex = 1, PageIndex2 = 1, PageIndex3 = 1;// 从1开始
	private Context mContext;
	// 影片
	// private CoverFlow mCoverFlow;
	private MirrorView mirrorGallery;
	private BaseAdapter filmAdapter;
	private TextView title, date, daoyan, zhuyan, desc;
	private int position = 0;// coverflow的滑动位置
	private SlideHolder mSlideHoler;
	// 影院
	private ListView listview;
	private BaseAdapter listAdapter;
	private List<String> yingyuanList = new ArrayList<String>();
	// 影院活动
	private ListView listview2;
	private Adapter4YingyuanActivities listAdapter2;
	private List<ArtLesson> artList = new ArrayList<ArtLesson>();

	private Handler handler_yingpian = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			// 更新textview的内容
			if (position >0) {
				title.setText(filmList.get(position%filmList.size()).getFilmName());
				date.setText(filmList.get(position%filmList.size()).getReleaseDte());
				daoyan.setText(filmList.get(position%filmList.size()).getProperty1());
				zhuyan.setText(filmList.get(position%filmList.size()).getProperty2());
			}
		}

	};

	public FragementFilm(Context mContext, SlideHolder mSlideHoler) {
		super();
		this.mContext = mContext;
		this.mSlideHoler = mSlideHoler;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ImageCacheUtil.ClearCache(ExproApplication.imageLoader);
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lin = inflater;
		if (container == null) {
			return null;
		}
		container = (ViewGroup) inflater.inflate(R.layout.layout_film, null);

		lin_yingyuan = (LinearLayout) container
				.findViewById(R.id.linearYingYuanOfFilm);
		lin_yingpian = (LinearLayout) container
				.findViewById(R.id.linearYingPianOfFilm);
		lin_huodong = (LinearLayout) container
				.findViewById(R.id.linearHuoDongOfFilm);
		ImageView img_back_film = (ImageView) container
				.findViewById(R.id.imageLeftOfHeadFilm);
		lin_yingyuan.setOnClickListener(this);
		lin_yingpian.setOnClickListener(this);
		lin_huodong.setOnClickListener(this);
		img_back_film.setOnClickListener(this);

		// progressbar = (LinearLayout) container
		// .findViewById(R.id.progressOfTheatre);
		// viewflipper
		mViewPager = (ViewPager) container.findViewById(R.id.viewpagerOfFilm);

		initVP();

		return container;
	}

	private void initVP() {

		List<View> views = new ArrayList<View>();
		views.add(lin.inflate(R.layout.layout_yingpian, null));
		views.add(lin.inflate(R.layout.layout_yingyuan, null));
		views.add(lin.inflate(R.layout.layout_yingyuan_activies, null));

		adapter = new PaperAdapter(views);

		mViewPager.setAdapter(adapter);

		// 给viewpager增加监听器
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// 1.设置背景颜色
//				updateTextColorBefore(toId);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		// 影片初始化
		mirrorGallery = (MirrorView) views.get(0).findViewById(
				R.id.mirrorviewOfYingPian);

		mirrorGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				position = arg2;
				handler_yingpian.sendMessage(handler_yingpian.obtainMessage(0, arg2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		mirrorGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Film f = filmList.get(arg2%filmList.size());
				Intent intent = new Intent(mContext, FilmDetail.class);
				intent.putExtra("filmName", f.getFilmName());
				intent.putExtra("img", f.getTitleImageName());
				intent.putExtra("daoyan", f.getProperty1());
				intent.putExtra("zhuyan", f.getProperty2());
				intent.putExtra("realeaseDate", f.getReleaseDte());
				intent.putExtra("filmID", f.getFilmID());

				startActivity(intent);
			}
		});

		title = (TextView) views.get(0).findViewById(R.id.textTitleOfYingPian);
		date = (TextView) views.get(0).findViewById(R.id.textDateOfYingPian);
		daoyan = (TextView) views.get(0).findViewById(
				R.id.textProperty1OfYingPian);
		zhuyan = (TextView) views.get(0).findViewById(
				R.id.textProperty2OfYingPian);
		desc = (TextView) views.get(0).findViewById(R.id.textDescOfYingPian);

		task4YingPian ts1 = new task4YingPian();
		ts1.execute();
		// 影院
		listview = (ListView) views.get(1)
				.findViewById(R.id.listviewOfYingyuan);
		yingyuanList.add("苏州文化艺术中心电影城");

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 跳转到详细页面
				if (arg3 > -1) {
					Intent intent = new Intent(mContext, YingyuanDetail.class);
					startActivity(intent);
				}
			}
		});

		// 影院活动
		listview2 = (ListView) views.get(2).findViewById(
				R.id.listviewOfYingyuanActivities);

		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg3 > -1) {
					ArtLesson al = artList.get(arg2);
					// 跳转到详细页面
					Intent intent = new Intent(mContext,
							YingyuanActivitiesDetail.class);
					intent.putExtra("contentID", al.getContentID());
					intent.putExtra("img", al.getTitleImageName());
					intent.putExtra("title", al.getContentTitle());

					startActivity(intent);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void updateTextColorBefore(int position) {

		switch (position) {

		case 0:
			lin_yingyuan.setBackgroundDrawable(null);
			lin_yingpian.setBackgroundResource(R.drawable.film_backtop);
			lin_huodong.setBackgroundDrawable(null);
			break;
		case 1:
			lin_yingyuan.setBackgroundResource(R.drawable.film_backtop);
			lin_yingpian.setBackgroundDrawable(null);
			lin_huodong.setBackgroundDrawable(null);
			if (listAdapter == null) {
				listAdapter = new Adapter4Yingyuan(mContext, yingyuanList);
				listview.setAdapter(listAdapter);
			}

			break;
		case 2:
			lin_yingyuan.setBackgroundDrawable(null);
			lin_yingpian.setBackgroundDrawable(null);
			lin_huodong.setBackgroundResource(R.drawable.film_backtop);
			if (listAdapter2 == null) {
				task1 ts = new task1();
				ts.execute();
			}
			break;
		}

		mViewPager.setCurrentItem(position);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearYingPianOfFilm://
			toId = 0;
			break;
		case R.id.linearYingYuanOfFilm://
			toId = 1;
			break;
		case R.id.linearHuoDongOfFilm://
			toId = 2;
			break;
		case R.id.imageLeftOfHeadFilm:
			mSlideHoler.toggle();
			break;
		default:
			break;
		}
		updateTextColorBefore(toId);
	}

	// 去获取coverflow所需要得广告图片
	private class task4YingPian extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// progressbar.setVisibility(View.VISIBLE);
			ExproApplication.throwTips("加载演出资讯...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				filmList = XmlToListService.GetFilms(HttpUtil.sendGetRequest(
						 Constants.DIANYING_LIST + PageSize + "/"
								+ PageIndex));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 处理结果
			if (filmList != null && filmList.size() > 0) {
				filmAdapter = new ImageAdapter(mContext);
				mirrorGallery.setAdapter(filmAdapter);
				mirrorGallery.setSelection(filmList.size()*30);
				// 提一条影片数据
				title.setText(filmList.get(0).getFilmName());
				date.setText(filmList.get(0).getReleaseDte());
				daoyan.setText(filmList.get(0).getProperty1());
				zhuyan.setText(filmList.get(0).getProperty2());
				// desc.setText(filmList.get(0).get);
			} else {
				Log.i("poe", "获取广告数据失败！");
			}
			// progressbar.setVisibility(View.VISIBLE);
			if(mSlideHoler.isOpened()){
				mSlideHoler.toggle();
			}
		}
	}

	public Bitmap createReflectedImages(Bitmap originalImage) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 4;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, 0,
				width, height, matrix, false);

		// Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.RGB_565);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	// coverflower图像的适配器
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private LayoutInflater lin;

		public ImageAdapter(Context c) {
			mContext = c;
			lin = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;//filmList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return filmList.get(position%filmList.size());
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Film ad = filmList.get(position%filmList.size());
			final ReflectionImage image = new ReflectionImage(mContext);
			image.setImageResource(R.drawable.placeholder_high);
			ImageCacheUtil ic =new ImageCacheUtil();
			ic.loadImageGallery2(ExproApplication.imageLoader, image, ad.getTitleImageName(),new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					//加载reflect图片
					ReflectionImage image2 = new ReflectionImage(mContext);
					image2.setImageBitmap(loadedImage);
					image2.setLayoutParams(new MirrorView.LayoutParams(200,400 ));
					image2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
					// Make sure we set anti-aliasing otherwise we get jaggies
					BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
					drawable.setAntiAlias(true);
					
					image.setImageDrawable(drawable);
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
			});
			image.setLayoutParams(new MirrorView.LayoutParams(200,400 ));
			image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
			drawable.setAntiAlias(true);

			return image;
		}
		
		
	}

	/**
	 * Returns the size (0.0f to 1.0f) of the views depending on the 'offset' to
	 * the center.
	 */
	public float getScale(boolean focused, int offset) {
		/* Formula: 1 / (2 ^ offset) */
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

	// 1.影院活动
	private class task1 extends AsyncTask<String, String, String> {
		private List<ArtLesson> sList;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ExproApplication.throwTips("加载活动数据...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				sList  = XmlToListService.GetYiwenKetang(HttpUtil
						.sendGetRequest( Constants.YINGCHENG_ACTIVITIES
								+ PageSize + "/" + PageIndex3));

				Log.i("poe", "artList.size()" + artList.size());

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("poe", "sax解析出错！" + e.getMessage());
				if (PageIndex3 > 1) {
					PageIndex3--;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (sList != null && sList.size() > 0) {

				artList.addAll(sList);
				if (listAdapter2 == null) {
					listAdapter2 = new Adapter4YingyuanActivities(mContext,
							artList, new lastIndexLoad() {
								@Override
								public void loadData() {
									// TODO Auto-generated method stub
									PageIndex3++;
									task1 ts = new task1();
									ts.execute();
								}
							});

					listview2.setAdapter(listAdapter2);
					
				} else {
					listAdapter2.notifyDataSetChanged();
				}
			}
			
		}
	}
}
