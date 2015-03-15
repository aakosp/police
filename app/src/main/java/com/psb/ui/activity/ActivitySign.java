package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.psb.R;
import com.psb.entity.ID;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.LocationUtils;
import com.util.StringUtils;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class ActivitySign extends BaseActivity implements View.OnClickListener {

    public MyLocationListenner myListener = new MyLocationListenner();
    // 定位相关
    LocationClient mLocClient;
    BitmapDescriptor mCurrentMarker;
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    private TopNavigationBar topbar;
    private LocationMode mCurrentMode = LocationMode.FOLLOWING;
    private EditText content;
    private Button sign;
    BDLocation mLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
//        LatLng southwest = new LatLng(34.738562, 113.620144);
//        LatLng northeast = new LatLng(34.738562, 113.620144);
//        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
        MapStatusUpdate zoom = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(zoom);
//        mBaiduMap.setMapStatus(msu);
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.mark);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, false, mCurrentMarker));
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        content = (EditText) findViewById(R.id.work);
        sign = (Button) findViewById(R.id.sign);
        sign.setOnClickListener(this);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.SGIN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign:
                String work = content.getText().toString();
//                Log.d("sign", "" + LocationUtils.getInstance().getmBDLocation().getLatitude() + " " + LocationUtils.getInstance().getmBDLocation().getLongitude());
                if(null != mLocation){
                    Api.getInstance().sgin(work, mLocation.getLongitude(), mLocation.getLatitude());
                }
                else{
                    ToastUtil.showLongToast(this, "无法获取当前位置", 0);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        EventNotifyCenter.getInstance().unregister(this.getHandler(), Event.SGIN);
        super.onDestroy();
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.SGIN:
                ID res = Cache.getInstance().getSign();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showLongToast(this, res.getError(), 0);
                } else if (res.getId() > -1) {
                    EventNotifyCenter.getInstance().doNotify(Event.REFRESH_OPINION);
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("sign", true);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            mLocation = location;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
