package com.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.psb.protocol.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/28.
 */
public class LocationUtils {

    private static LocationUtils mLocationUtils;
    public Vibrator mVibrator;
    private LocationClient mLocationClient;
    private BDLocation mBDLocation;
    private BDLocationListener mBDLocationListener;
    private List<List<LatLng>> allPoints = new ArrayList<>();
    private List<LatLng> points = new ArrayList<LatLng>();
    private boolean sign = false;
    private String session;

    private LocationUtils() {
        allPoints.add(points);
    }

    public synchronized static LocationUtils getInstance() {
        if (null == mLocationUtils) {
            mLocationUtils = new LocationUtils();
        }
        return mLocationUtils;
    }

    public BDLocation getmBDLocation() {
        return mBDLocation;
    }

    public LocationClient getLocationClient() {
        return mLocationClient;
    }

    public void start() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    public void stop() {
        mLocationClient.stop();
    }

    public void setmBDLocationListener(BDLocationListener mBDLocationListener) {
        this.mBDLocationListener = mBDLocationListener;
    }

    public void initLocation(Context context) {
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(new BDLocationListener() {


            @Override
            public void onReceiveLocation(BDLocation location) {
                mBDLocation = location;
                if (null != mBDLocationListener) {
                    mBDLocationListener.onReceiveLocation(location);
                }
                if (sign) {
                    if (location.getLocType() == BDLocation.TypeNetWorkException) {
                        return;
                    }
                    if (!StringUtils.isEmpty(session)) {
                        Api.getInstance().sign_up_session(session, location.getLongitude(), location.getLatitude());
                    }
                    LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());
                    points.add(lat);
                    if (points.size() == 500) {
                        points = new ArrayList<LatLng>();
                        allPoints.add(points);
                    }
                }

            }
        });
        mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000);
        mLocationClient.setLocOption(option);
    }

    public List<List<LatLng>> getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(List<List<LatLng>> allPoints) {
        this.allPoints = allPoints;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
        if (!sign) {
            allPoints.clear();
        }
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void getLoc() {
        if (null != mLocationClient) {
            int res = mLocationClient.requestLocation();
            Log.d("getLoc", "" + res);
        }
    }
}
