package com.psb.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.psb.R;
import com.psb.ui.util.ImageUtil;

public class RoundImageView extends ImageView {
    private int mBorderThickness = 0;
    private Context mContext;
    private int defaultColor = 0;
    private int mBorderOutsideColor = 0;
    private int mBorderInsideColor = 0;
    private int defaultWidth = 0;
    private int defaultHeight = 0;
    private int mRound = 0;

    private boolean mBdrawRound = true;

    public RoundImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.roundedimageview);
        mBorderThickness = a.getDimensionPixelSize(
                R.styleable.roundedimageview_border_thickness, 0);
        mBorderOutsideColor = a
                .getColor(R.styleable.roundedimageview_border_outside_color,
                        defaultColor);
        mBorderInsideColor = a.getColor(
                R.styleable.roundedimageview_border_inside_color, defaultColor);
        mRound = a.getInt(R.styleable.roundedimageview_round, 0);
        a.recycle();
    }

    public void setDrawRound(boolean isDrawRound) {
        this.mBdrawRound = isDrawRound;
    }

    public void setRoundRadius(int radius) {
        this.mRound = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!this.mBdrawRound) {
            super.onDraw(canvas);
            return;
        }
        try {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            if (getWidth() == 0 || getHeight() == 0) {
                return;
            }
            this.measure(0, 0);
            if (drawable.getClass() == NinePatchDrawable.class)
                return;
            Bitmap b = null;
            if (drawable.getClass() == BitmapDrawable.class) {
                b = ((BitmapDrawable) drawable).getBitmap();
            }
            if (null == b) {
                b = ImageUtil.drawableToBitmap(drawable);
            }
            Bitmap bitmap = b.copy(Config.ARGB_8888, true);
            if (defaultWidth == 0) {
                defaultWidth = getWidth();
            }
            if (defaultHeight == 0) {
                defaultHeight = getHeight();
            }
            int radius = 0;
            if (mBorderInsideColor != defaultColor
                    && mBorderOutsideColor != defaultColor) {
                radius = (defaultWidth < defaultHeight ? defaultWidth
                        : defaultHeight) / 2 - 2 * mBorderThickness;
                drawCircleBorder(canvas, radius + mBorderThickness / 2,
                        mBorderInsideColor);
                drawCircleBorder(canvas, radius + mBorderThickness
                        + mBorderThickness / 2, mBorderOutsideColor);
            } else if (mBorderInsideColor != defaultColor
                    && mBorderOutsideColor == defaultColor) {
                radius = (defaultWidth < defaultHeight ? defaultWidth
                        : defaultHeight) / 2 - mBorderThickness;
                drawCircleBorder(canvas, radius + mBorderThickness / 2,
                        mBorderInsideColor);
            } else if (mBorderInsideColor == defaultColor
                    && mBorderOutsideColor != defaultColor) {
                radius = (defaultWidth < defaultHeight ? defaultWidth
                        : defaultHeight) / 2 - mBorderThickness;
                drawCircleBorder(canvas, radius + mBorderThickness / 2,
                        mBorderOutsideColor);
            } else {
                radius = (defaultWidth < defaultHeight ? defaultWidth
                        : defaultHeight) / 2;
            }
            Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
            canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius,
                    defaultHeight / 2 - radius, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;

        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        int squareWidth = 0, squareHeight = 0;
        int x = 0, y = 0;
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {
            squareWidth = squareHeight = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else if (bmpHeight < bmpWidth) {
            squareWidth = squareHeight = bmpHeight;
            x = (bmpWidth - bmpHeight) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else {
            squareBitmap = bmp;
        }
        if (squareBitmap.getWidth() != diameter
                || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
                    diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }
        if (mRound > 0) {
            if (scaledSrcBmp.getWidth() < this.getLayoutParams().width) {
                scaledSrcBmp = ImageUtil.zoomBitmap(scaledSrcBmp, this.getLayoutParams().width, this.getLayoutParams().height);
            }
        }

        Bitmap output = null;
        Rect rect = null;
        if (this.getLayoutParams().width > 0
                && this.getLayoutParams().height > 0) {
            output = Bitmap.createBitmap(this.getLayoutParams().width,
                    this.getLayoutParams().height, Config.ARGB_8888);
            rect = new Rect(0, 0, this.getLayoutParams().width,
                    this.getLayoutParams().height);
        } else {
            output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                    scaledSrcBmp.getHeight(), Config.ARGB_8888);
            rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
                    scaledSrcBmp.getHeight());
        }
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        if (mRound > 0) {
            RectF rectf = null;
            if (this.getLayoutParams().width > 0
                    && this.getLayoutParams().height > 0) {
                rectf = new RectF(0, 0, this.getLayoutParams().width,
                        this.getLayoutParams().height);
            } else {
                rectf = new RectF(0, 0, scaledSrcBmp.getWidth(),
                        scaledSrcBmp.getHeight());
            }
            canvas.drawRoundRect(rectf, mRound, mRound, paint);
        } else {
            canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                    scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
                    paint);
        }
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        //bmp = null;
        squareBitmap.recycle();
        scaledSrcBmp.recycle();
        return output;
    }

    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
    }

}