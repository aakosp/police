package com.psb.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.psb.R;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsSecurity extends LinearLayout {
    public NewsSecurity(Context context) {
        super(context);
    }

    public NewsSecurity(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.news_security, this, true);
    }
}
