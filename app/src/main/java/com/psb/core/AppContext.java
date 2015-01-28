package com.psb.core;

import android.app.Application;
import com.baidu.mapapi.SDKInitializer;
import com.util.LocationUtils;

/**
 * Created by zl on 2015/1/26.
 */
public class AppContext extends Application {

    private static AppContext appContext;

    public static Application getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        LocationUtils.getInstance().initLocation(this);
        appContext = this;
    }


}
