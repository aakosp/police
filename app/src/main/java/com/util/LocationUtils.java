package com.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by zl on 2015/1/28.
 */
public class LocationUtils {

    private static LocationUtils mLocationUtils;
    public Vibrator mVibrator;
    private LocationClient mLocationClient;
    private BDLocation mBDLocation;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    private int span = 30000;
    private LocationUtils() {
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

    public void initLocation(Context context) {
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                mBDLocation = location;
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\ndirection : ");
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append(location.getDirection());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                }
                Log.i("BaiduLocationApiDem", sb.toString());
            }
        });
        mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);
        option.setCoorType(tempcoor);
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);// 打开gps
        mLocationClient.setLocOption(option);
//        if (!mLocationClient.isStarted()) {
//            mLocationClient.start();
//        }
    }

    public void getLoc() {
        if (null != mLocationClient) {
            int res = mLocationClient.requestLocation();
            Log.d("getLoc", "" + res);
        }
    }
}
