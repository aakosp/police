package com.psb.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.psb.core.AppContext;

public class DisplayUtil {

    private static DisplayMetrics displaysMetrics = null;
    private static float scale = -1.0f;

    public static DisplayMetrics getDisplayMetrics() {
        if (null == displaysMetrics) {
            displaysMetrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) AppContext.getInstance()
                    .getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(displaysMetrics);
        }
        return displaysMetrics;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getHeight() {
        if (null == displaysMetrics) {
            getDisplayMetrics();
        }
        return displaysMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getWidth() {
        if (null == displaysMetrics) {
            getDisplayMetrics();
        }
        return displaysMetrics.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (scale < 0) {
            if (null == displaysMetrics) {
                getDisplayMetrics();
            }
            scale = displaysMetrics.density;
        }

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        if (scale < 0) {
            if (null == displaysMetrics) {
                getDisplayMetrics();
            }
            scale = displaysMetrics.density;
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从sp 的单位 转成为 dp
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppContext.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
