package com.psb.core;

import android.app.Application;

/**
 * Created by zl on 2015/1/26.
 */
public class AppContext extends Application {

    private static AppContext appContext;

    private AppContext(){}

    public synchronized static AppContext getInstance(){
        if(null == appContext){
            appContext = new AppContext();
        }
        return appContext;
    }

}
