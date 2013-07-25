package com.ssac.expro.kewen;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class Client_Map extends Activity {
	/** Called when the activity is first created. */
	private MapView mMapView;
	BMapManager mBMapMan = null;  
	ImageView back;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mBMapMan=new BMapManager(getApplication());  
		mBMapMan.init(ExproApplication.mStrKey, null);    
		
		setContentView(R.layout.client_map);
		
		mMapView = (MapView) findViewById(R.id.MapViewOfClientMap);
		back		=	(ImageView) findViewById(R.id.imageLeftOfHeadMoreMap);
		mMapView.setBuiltInZoomControls(true);
		//设置启用内置的缩放控件  
		MapController mMapController=mMapView.getController();  
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放  120.710326,31.329219
		GeoPoint point =new GeoPoint((int)(31.329* 1E6),(int)(120.710* 1E6));  
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
		mMapController.setCenter(point);//设置地图中心点  
		mMapController.setZoom(16);//设置地图zoom级别  
		mMapController.animateTo(point);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		addPoint();
	}

	private void addPoint() {
		// TODO Auto-generated method stub
		//准备要添加的Overlay  120.710326,31.329219
		double mLat1 = 31.329219;  
		double mLon1 = 120.710326;  
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)  
		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));  
		//准备overlay图像数据，根据实情情况修复  
		Drawable mark= getResources().getDrawable(R.drawable.icon_mark);  
		//用OverlayItem准备Overlay数据  
		OverlayItem item1 = new OverlayItem(p1,"item1","item1");  
		   
		//创建IteminizedOverlay  
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);  
		//将IteminizedOverlay添加到MapView中  
		mMapView.getOverlays().clear();  
		mMapView.getOverlays().add(itemOverlay);  
		//现在所有准备工作已准备好，使用以下方法管理overlay.  
		//添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高  
		itemOverlay.addItem(item1);  
		mMapView.refresh();  
	}

	@Override
	protected void onPause() {
		 mMapView.onPause();  
	        if(mBMapMan!=null){  
	               mBMapMan.stop();  
	        }  
	        super.onPause();  
	}
	@Override
	protected void onResume() {
		 mMapView.onResume();  
	        if(mBMapMan!=null){  
	                mBMapMan.start();  
	        }  
	       super.onResume();  
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		  mMapView.destroy();  
	        if(mBMapMan!=null){  
	                mBMapMan.destroy();  
	                mBMapMan=null;  
	        }  
		super.onDestroy();
	}
	
	/* 
	 * 要处理overlay点击事件时需要继承ItemizedOverlay 
	 * 不处理点击事件时可直接生成ItemizedOverlay. 
	 */  
	class OverlayTest extends ItemizedOverlay<OverlayItem> {  
	    //用MapView构造ItemizedOverlay  
	    public OverlayTest(Drawable mark,MapView mapView){  
	            super(mark,mapView);  
	    }  
	    protected boolean onTap(int index) {  
	        //在此处理item点击事件  
	        System.out.println("item onTap: "+index);  
	        return true;  
	    }  
	        public boolean onTap(GeoPoint pt, MapView mapView){  
	                //在此处理MapView的点击事件，当返回 true时  
	                super.onTap(pt,mapView);  
	                return false;  
	        }  
	} 
	
}
