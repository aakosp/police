package com.psb.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psb.R;
import com.psb.ui.activity.ActivityMain;

/**
 * Created by zl on 2014/11/21.
 */
public class NaviTabButton extends FrameLayout implements View.OnClickListener{

    private int mIndex;
    private ImageView mImage;
    private TextView mTitle;
    private TextView mNotify;
    private Drawable mSelectedImg;
    private Drawable mUnselectedImg;
    private Context mContext;

    public NaviTabButton(Context context) {
        this(context, null);
    }

    public NaviTabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NaviTabButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_navi_tab_button, this, true);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.tab_btn_container);

        mImage = (ImageView) findViewById(R.id.tab_btn_default);
        mTitle = (TextView) findViewById(R.id.tab_btn_title);
        mNotify = (TextView) findViewById(R.id.tab_unread_notify);

        container.setOnClickListener(this);
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public void setUnselectedImage(Drawable img) {
        this.mUnselectedImg = img;
    }

    public void setSelectedImage(Drawable img) {
        this.mSelectedImg = img;
    }

    private void setSelectedColor(Boolean selected) {
        if (selected) {
            mTitle.setTextColor(getResources().getColor(
                    R.color.selected_red));
        } else {
            mTitle.setTextColor(getResources().getColor(
                    R.color.news_time));
        }
    }

    public void setSelectedButton(Boolean selected) {
        setSelectedColor(selected);
        if (selected) {
            mImage.setImageDrawable(mSelectedImg);
        } else {
            mImage.setImageDrawable(mUnselectedImg);
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setUnreadNotify(int unreadNum) {
        if (0 == unreadNum) {
            mNotify.setVisibility(View.INVISIBLE);
            return;
        }

        String notify;
        if (unreadNum > 99) {
            notify = "99+";
        } else {
            notify = Integer.toString(unreadNum);
        }

        mNotify.setText(notify);
        mNotify.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        ((ActivityMain) mContext).setFragmentIndicator(mIndex);
    }
}
