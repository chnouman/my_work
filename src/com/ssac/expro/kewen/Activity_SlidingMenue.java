package com.ssac.expro.kewen;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import com.ssac.expro.kewen.bean.Task;
import com.ssac.expro.kewen.bean.TaskType;
import com.ssac.expro.kewen.service.MainService;
import com.ssac.expro.kewen.view.SlideHolder;

/**
 * 演出、电影、会展的载体 
 * 
 * @author poe
 * 
 */ 
public class Activity_SlidingMenue extends FragmentActivity implements
		OnClickListener {

	private SlideHolder mSlideHolder;
	private LinearLayout lin_home, lin_art, lin_vip, lin_youhui, lin_more;
	private LinearLayout lin_yc, lin_dy, lin_hz, lin_px, lin_msg, lin_yt;
	private String from = "yanchu";
	private Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_sliding_menu);
		c = this;

		if (!MainService.isrun) {
			Intent it = new Intent(this, MainService.class);
			this.startService(it);
		}

		init();
	}

	public void init() {
		// TODO Auto-generated method stub
		from = getIntent().getStringExtra("tag");
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		mSlideHolder.setAllowInterceptTouch(false);
		
		lin_home = (LinearLayout) findViewById(R.id.linearHOme);
		lin_art = (LinearLayout) findViewById(R.id.linearNews);
		lin_vip = (LinearLayout) findViewById(R.id.linearVip);
		lin_youhui = (LinearLayout) findViewById(R.id.linearYouHui);
		lin_more = (LinearLayout) findViewById(R.id.linearMore);

		lin_home.setOnClickListener(this);
		lin_art.setOnClickListener(this);
		lin_vip.setOnClickListener(this);
//		lin_map.setOnClickListener(this);
		lin_more.setOnClickListener(this);

		// 下面点击button的效果
		lin_yc = (LinearLayout) findViewById(R.id.linearYanchuOfSlidingMenue);
		lin_dy = (LinearLayout) findViewById(R.id.linearDianYingOfSlidingMenue);
		lin_hz = (LinearLayout) findViewById(R.id.linearHuiZhanOfSlidingMenue);
		lin_px = (LinearLayout) findViewById(R.id.linearPeiXunOfSlidingMenue);
		lin_msg = (LinearLayout) findViewById(R.id.linearMeiShuOfSlidingMenue);
		lin_yt = (LinearLayout) findViewById(R.id.linearYiTanOfSlidingMenue);

		lin_hz.setOnClickListener(this);
		lin_px.setOnClickListener(this);
		lin_msg.setOnClickListener(this);
		lin_yt.setOnClickListener(this);
		lin_yc.setOnClickListener(this);
		lin_dy.setOnClickListener(this);
		// 默认去加载 演出
		// 通知服务获取ad数据
		loadFragment(from);
	}

	/**
	 * 根据 from加载不同的随便布局
	 * 
	 * @param from
	 */
	private void loadFragment(final String from) {
//		MainService.newTask(ts);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HashMap<String, Object> hm = new HashMap<String, Object>();
				Task ts = null;
				// 1.演出
				if ("yanchu".equals(from)) {
					ts = new Task(TaskType.GET_YANCHU, hm);
				} else
				// 2.电影
				if ("dianying".equals(from)) {
					ts = new Task(TaskType.GET_DIANYING, hm);
				} else
				// 3.会展
				if ("huizhan".equals(from)) {
					ts = new Task(TaskType.GET_HUIZHAN, hm);
				} else
				// 4.培训
				if ("peixun".equals(from)) {
					ts = new Task(TaskType.GET_PEIXUN, hm);
				} else
				// 5.金鸡湖美术馆
				if ("meishuguan".equals(from)) {
					ts = new Task(TaskType.GET_MEISHUGUAN, hm);
				} else
				// 6.金鸡湖艺坛
				if ("yitan".equals(from)) {
					ts = new Task(TaskType.GET_YITAN, hm);
				}
				
				if(ts!=null)
				refresh(ts.getTaskID());
			}
		}, 100);
		
	}

	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		int type = (Integer) param[0];
		FragmentTransaction transaction = this.getSupportFragmentManager()
				.beginTransaction();
		loadFram(type, transaction);
	}

	private void loadFram(int key, FragmentTransaction transaction) {
		Fragment frag;
		switch (key) {
		case TaskType.GET_YANCHU:
			frag = new FragementYanChu(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		case TaskType.GET_DIANYING:
			frag = new FragementFilm(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		case TaskType.GET_HUIZHAN:
			frag = new FragementHuiZhan(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		case TaskType.GET_PEIXUN:
			frag = new FragementPeiXun(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		case TaskType.GET_MEISHUGUAN:
			frag = new FragementMeiShuGuan(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		case TaskType.GET_YITAN:
			frag = new FragementYiTan(c,mSlideHolder);
			transaction.replace(R.id.linearcontent, frag);
			transaction.commit();
			break;
		default:
			break;
		}
		
//		mSlideHolder.toggle();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		switch (v.getId()) {
		case R.id.linearHOme:
			intent.setClass(this, Activity_Home.class);
			startActivity(intent);
			finish();
			break;
		case R.id.linearNews:
			intent.setClass(this, Activity_Art.class);
			startActivity(intent);
			finish();
			break;
		case R.id.linearVip:
			intent.setClass(this, Activity_VIP.class);
			startActivity(intent);
			finish();
			break;
		case R.id.linearYouHui:
//			intent.setClass(this, this);
//			startActivity(intent);
			ExproApplication.showBuildTip(c);
			break;
		case R.id.linearMore:
			intent.setClass(this, Activity_More.class);
			startActivity(intent);
			finish();
			break;
		// 子选项的跳转
		case R.id.linearYanchuOfSlidingMenue:
			from="yanchu";
			loadFragment(from);
			break;
		case R.id.linearDianYingOfSlidingMenue:
			from="dianying";
			loadFragment(from);
			break;
		case R.id.linearHuiZhanOfSlidingMenue:
			from= "huizhan";
			loadFragment(from);
			break;
		case R.id.linearPeiXunOfSlidingMenue:
			from="peixun";
			loadFragment(from);
			break;
		case R.id.linearMeiShuOfSlidingMenue:
			from="meishu";
			loadFragment(from);
			break;
		case R.id.linearYiTanOfSlidingMenue:
			from="yitan";
			loadFragment(from);
			break;
		default:
			break;
		}
		
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

}
