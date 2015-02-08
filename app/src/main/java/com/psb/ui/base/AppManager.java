package com.psb.ui.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addContext(BaseFragmentActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void addContext(BaseActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        try {
            if (null == activityStack) {
                return;
            }
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    try {
                        activityStack.get(i).finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishOtherActivity(String className) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (null != activity
                    && !activity.getClass().getName().equals(className)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT <= 8)
                activityMgr.restartPackage(context.getPackageName());
            else
                activityMgr.killBackgroundProcesses(context.getPackageName());
            // System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}