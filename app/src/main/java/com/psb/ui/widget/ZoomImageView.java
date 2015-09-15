package com.psb.ui.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by aako on 2015/8/31.
 */
public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    //最大缩放比例
    public static final float SCALE_MAX = 4.0f;

    //初始化缩放比例
    private static float initScale = 1.0f;

    //存放矩阵的9个值
    private final float[] matrixValues = new float[9];
    private final Matrix mScaleMatrix = new Matrix();
    //
    private boolean bOnce = true;
    //手势检测
    private ScaleGestureDetector mScaleGestureDetector = null;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }


    /**
     * OnScaleGestureListener implements
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if(null == getDrawable())
            return true;
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)){

        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * OnTouchListener implement
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale()
    {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
}
