package com.psb.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psb.R;

/**
 * Created by zl on 2015/1/29.
 */
public class ItemHorizontal extends RelativeLayout {

    private ImageView img, arrow;
    private TextView textView;

    public ItemHorizontal(Context context) {
        this(context, null);
    }

    public ItemHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_horizontal, this, true);
        img = (ImageView) findViewById(R.id.img);
        textView = (TextView) findViewById(R.id.text);
        arrow = (ImageView) findViewById(R.id.arrow);
        this.initializeAttributes(context, attrs);
    }

    private void initializeAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemHorizontal);

        if (this.img != null) {
            int resId = typedArray.getResourceId(R.styleable.ItemHorizontal_ItemHorizontal_img_src, 0);
            if (resId != 0) {
                this.img.setImageResource(resId);
            }
            int width = typedArray.getDimensionPixelOffset(R.styleable.ItemHorizontal_ItemHorizontal_img_width, 48);
            int height = typedArray.getDimensionPixelOffset(R.styleable.ItemHorizontal_ItemHorizontal_img_height, 48);
            LayoutParams params = (LayoutParams) img.getLayoutParams();
            params.width = width;
            params.height = height;
            img.setLayoutParams(params);
        }

        if (this.arrow != null) {
            boolean bVisible = typedArray.getBoolean(R.styleable.ItemHorizontal_ItemHorizontal_arrow_visible, true);
            if (!bVisible) {
                this.arrow.setVisibility(View.GONE);
            }
        }

        if (this.textView != null) {
            CharSequence cs = typedArray.getText(R.styleable.ItemHorizontal_ItemHorizontal_text);
            if(null != cs){
                this.textView.setText(cs);
            }
            int color = typedArray.getColor(R.styleable.ItemHorizontal_ItemHorizontal_text_color, 0XFFFFFFFF);
            this.textView.setTextColor(color);
            float s = typedArray.getDimension(R.styleable.ItemHorizontal_ItemHorizontal_text_size, 12);
            Log.d("size", ""+s);
            this.textView.setTextSize(18);
        }
    }

    public void setText(String text){
        this.textView.setText(text);
    }

}
