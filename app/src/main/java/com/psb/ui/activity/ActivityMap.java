package com.psb.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;


/**
 * 演示覆盖物的用法
 */
public class ActivityMap extends BaseActivity implements OnClickListener {

    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;
    private InfoWindow mInfoWindow;
    private View officeInfo;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markb);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markc);
    BitmapDescriptor bdD = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markd);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        TopNavigationBar topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        initOverlay();
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {

                OnInfoWindowClickListener listener = null;
                if (marker == mMarkerA || marker == mMarkerD) {
                    PositionViewHolder positionViewHolder = (PositionViewHolder) officeInfo.getTag();
                    positionViewHolder.name.setText("汤阴大付庄警务室");
                    positionViewHolder.tel.setText("电话：32323553");
                    positionViewHolder.addr.setText("汤阴大付庄村");
                    positionViewHolder.call.setTag("32323553");
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(officeInfo, ll, 240);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                /*else if (marker == mMarkerC) {
                    button.setText("删除");
                    button.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            marker.remove();
                            mBaiduMap.hideInfoWindow();
                        }
                    });
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(button, ll, -47);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }*/
                return true;
            }
        });
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
        LatLng llA = new LatLng(34.736352, 113.606382);
        LatLng llB = new LatLng(34.732673, 113.608322);
        LatLng llC = new LatLng(34.738221, 113.600381);
        LatLng llD = new LatLng(34.732703, 113.616586);

        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdB).zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
        OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdC).perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
        OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD).perspective(false).zIndex(7);
        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        // add ground overlay
        LatLng southwest = new LatLng(34.738562, 113.620144);
        LatLng northeast = new LatLng(34.738562, 113.620144);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);

        mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(
                        ActivityMap.this,
                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
                                + marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }

    /**
     * 重新添加Overlay
     *
     * @param view
     */
    public void resetOverlay(View view) {
        clearOverlay(null);
        initOverlay();
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
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "" + (v.getId() == R.id.call));
        switch (v.getId()) {
            case R.id.call:
                Uri telUri = Uri.parse("tel:" + v.getTag());
                Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                ActivityMap.this.startActivity(intent);
                break;
            default:
                mBaiduMap.hideInfoWindow();
                return;
        }
    }

    private static class PositionViewHolder {
        public TextView name, tel, addr;
        public View call, navigation;
    }

}
