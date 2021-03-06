package com.psb.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.psb.R;
import com.psb.entity.OfficeInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

import java.util.List;


/**
 * 演示覆盖物的用法
 */
public class ActivityMap extends BaseActivity implements OnClickListener, OnMarkerClickListener {

    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Button gensui;
    private InfoWindow mInfoWindow;
    private View officeInfo;
    private BitmapDescriptor mark = BitmapDescriptorFactory.fromResource(R.drawable.mark);
    private List<OfficeInfo> officeInfos;
    private int focId = -1;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    LatLng southwest, northeast;

    //    boolean isFirstLoc = true;// 是否首次定位
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        gensui = (Button) findViewById(R.id.back);
        gensui.setOnClickListener(this);
        Bundle b = this.getIntent().getExtras();
        if (null != b) {
            focId = b.getInt("id");
        }

        TopNavigationBar topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();


        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
        mBaiduMap.setMapStatus(msu);
        initOverlay();
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                mBaiduMap.hideInfoWindow();
                return false;
            }
        });

        Api.getInstance().getOffice();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_OFFICE_LIST);
    }

    public void initOverlay() {
        officeInfo = View.inflate(ActivityMap.this, R.layout.item_map_position, null);
        PositionViewHolder positionViewHolder = new PositionViewHolder();
        positionViewHolder.name = (TextView) officeInfo.findViewById(R.id.name);
        positionViewHolder.tel = (TextView) officeInfo.findViewById(R.id.tel);
        positionViewHolder.addr = (TextView) officeInfo.findViewById(R.id.addr);
        positionViewHolder.call = officeInfo.findViewById(R.id.call);
        positionViewHolder.navigation = officeInfo.findViewById(R.id.navigation);
        officeInfo.setTag(positionViewHolder);
        positionViewHolder.call.setOnClickListener(this);
        positionViewHolder.navigation.setOnClickListener(this);

        // add marker overlay
//        LatLng llA = new LatLng(34.736352, 113.606382);
//        LatLng llB = new LatLng(34.732673, 113.608322);
//        LatLng llC = new LatLng(34.738221, 113.600381);
//        LatLng llD = new LatLng(34.732703, 113.616586);
//
//        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//        OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdB).zIndex(5);
//        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
//        OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdC).perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
//        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//        OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD).perspective(false).zIndex(7);
//        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        // add ground overlay
        southwest = new LatLng(35.758397, 114.148159);
        northeast = new LatLng(36.068486, 114.877727);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);

//        mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
//            public void onMarkerDrag(Marker marker) {
//            }
//
//            public void onMarkerDragEnd(Marker marker) {
//                Toast.makeText(
//                        ActivityMap.this,
//                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
//                                + marker.getPosition().longitude,
//                        Toast.LENGTH_LONG).show();
//            }
//
//            public void onMarkerDragStart(Marker marker) {
//            }
//        });
    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        // 回收 bitmap 资源
        mark.recycle();
        EventNotifyCenter.getInstance().unregister(this.getHandler(), Event.GET_OFFICE_LIST);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
                Uri telUri = Uri.parse("tel:" + v.getTag());
                Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                ActivityMap.this.startActivity(intent);
                break;
            case R.id.back:
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.NORMAL, true, null));

//                if(mCurrentMode == MyLocationConfiguration.LocationMode.NORMAL){
//                    gensui.setText("跟随");
//                    mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
//                    mBaiduMap
//                            .setMyLocationConfigeration(new MyLocationConfiguration(
//                                    mCurrentMode, true, null));
//                }
//                else if(mCurrentMode == MyLocationConfiguration.LocationMode.FOLLOWING){
//                    gensui.setText("普通");
//                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//                    mBaiduMap
//                            .setMyLocationConfigeration(new MyLocationConfiguration(
//                                    mCurrentMode, true, null));
//                }
            default:
                mBaiduMap.hideInfoWindow();
                return;
        }
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_OFFICE_LIST:
                this.init(Cache.getInstance().getOffice());
                break;
        }
    }

    private void init(List<OfficeInfo> offices) {
        if (null == offices) {
            return;
        }
        officeInfos = offices;
        for (int i = 0; i < offices.size(); i++) {
            OfficeInfo info = offices.get(i);
            LatLng ll = new LatLng(info.getLocation_y(), info.getLocation_x());
            OverlayOptions ooA = new MarkerOptions().position(ll).icon(mark).zIndex(i).draggable(true);
            Marker item = (Marker) (mBaiduMap.addOverlay(ooA));
            if (focId == info.getId()) {
                PositionViewHolder positionViewHolder = (PositionViewHolder) officeInfo.getTag();
                positionViewHolder.name.setText(info.getName());
                positionViewHolder.tel.setText(info.getPhone());
                positionViewHolder.addr.setText(info.getAddress());
                positionViewHolder.call.setTag(info.getPhone());
                LatLng at = item.getPosition();
                mInfoWindow = new InfoWindow(officeInfo, at, 235);
                mBaiduMap.showInfoWindow(mInfoWindow);

                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(at);
                mBaiduMap.setMapStatus(u);
                MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
                mBaiduMap.setMapStatus(msu);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (null == officeInfos) {
            return true;
        }
        PositionViewHolder positionViewHolder = (PositionViewHolder) officeInfo.getTag();
        OfficeInfo info = officeInfos.get(marker.getZIndex());
        positionViewHolder.name.setText(info.getName());
        positionViewHolder.tel.setText(info.getPhone());
        positionViewHolder.addr.setText(info.getAddress());
        positionViewHolder.call.setTag(info.getPhone());
        LatLng ll = marker.getPosition();
        mInfoWindow = new InfoWindow(officeInfo, ll, 235);
        mBaiduMap.showInfoWindow(mInfoWindow);
        return true;
    }

    private static class PositionViewHolder {
        public TextView name, tel, addr;
        public View call, navigation;
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
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//                mBaiduMap.animateMapStatus(u);
//            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
