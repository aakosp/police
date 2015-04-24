package com.psb.core;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.RoundImageDecoder;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.util.LocationUtils;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class AppContext extends Application {

    public static final long auto_request_time_lag = 10 * 60 * 1000;
    public static final long request_time_lag = 5 * 1000;
    private static AppContext appContext;

    // user your appid the key.
    public static final String APP_ID = "1000270";
    // user your appid the key.
    public static final String APP_KEY = "670100056270";
    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    // com.xiaomi.mipushdemo
    public static final String TAG = "com.xiaomi.mipush";

    private static MiHandler handler = null;

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

        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
        if (handler == null)
            handler = new MiHandler(getApplicationContext());

        appContext = this;

        if (Cache.getInstance().isLogin()){
            Api.getInstance().getUser(Cache.getInstance().getId());
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static MiHandler getHandler() {
        return handler;
    }

}
