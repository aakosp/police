package com.psb.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.psb.R;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsWarning extends LinearLayout {
    public NewsWarning(Context context) {
        super(context);
    }

    public NewsWarning(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.news_warning, this, true);
    }
}
