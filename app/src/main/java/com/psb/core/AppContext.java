package com.psb.core;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.RoundImageDecoder;
import com.psb.protocol.Api;
import com.util.LocationUtils;

/**
 * Created by zl on 2015/1/26.
 */
public class AppContext extends Application {

    public static final long auto_request_time_lag = 10 * 60 * 1000;
    public static final long request_time_lag = 5 * 1000;
    private static AppContext appContext;

    public static Application getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);
        LocationUtils.getInstance().initLocation(this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this.getApplicationContext()).memoryCacheSizePercentage(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .imageDecoder(new RoundImageDecoder(false)).build();
        ImageLoader.getInstance().init(config);
        Api.getInstance().getAddrs();
        appContext = this;
    }


}
