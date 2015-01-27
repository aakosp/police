package com.psb.core;

import android.app.Application;

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
        appContext = this;
    }
}
