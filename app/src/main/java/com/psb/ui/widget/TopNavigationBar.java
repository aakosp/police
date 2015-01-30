package com.psb.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psb.R;

/**
 * Created by Orgtec on 2014/12/30.
 */
public class TopNavigationBar extends RelativeLayout implements View.OnClickListener{

    private ImageView btnLeft, imgBtnRight;
    private Button buttonLeft, btnRight = null;
    private TextView tvTitle = null;
    private ImageView arrow;
    private RelativeLayout mtopbar;

    private Activity activity;

    public TopNavigationBar(Context context) {
        this(context, null);
    }

    public TopNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 读取布局
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_top_navigation_bar, this, true);
        this.mtopbar = (RelativeLayout) findViewById(R.id.topbar_rly);
        this.btnLeft = (ImageView) findViewById(R.id.btnTopBarLeft);
        this.buttonLeft = (Button) findViewById(R.id.buttonTopBarLeft);
        this.btnRight = (Button) findViewById(R.id.btnTopBarRight);
        this.imgBtnRight = (ImageView) findViewById(R.id.imgBtnTopBarRight);
        this.tvTitle = (TextView) findViewById(R.id.tvTopBarTitle);
        this.arrow = (ImageView) findViewById(R.id.type_top_arrow);
        this.btnLeft.setOnClickListener(this);
        this.buttonLeft.setOnClickListener(this);
        // 初始化属性
        this.initializeAttributes(context, attrs);
    }

    public ImageView getButtonLeft() {
        return this.btnLeft;
    }

    public Button getLeftButton() {
        return this.buttonLeft;
    }

    public TextView getTitleTextView() {
        return this.tvTitle;
    }

    public Button getRightButton() {
        return this.btnRight;
    }

    public ImageView getRightImageButton() {
        return this.imgBtnRight;
    }

    public void setTitleText(CharSequence text) {
        if (this.tvTitle != null) this.tvTitle.setText(text);
    }

    public void setTitleBackgroundResource(int resId) {
        if (this.tvTitle != null) this.tvTitle.setBackgroundResource(resId);
    }

    public ImageView getArrow() {
        return this.arrow;
    }

    public void setButtonLeftText(String text) {
        if (this.buttonLeft != null) this.buttonLeft.setText(text);
    }

    public void setLeftButtonBackgroundResource(int resId) {
        if (this.btnLeft != null) this.btnLeft.setBackgroundResource(resId);
    }

    public void setLeftButtonBackgroundVisibility(int visibility) {
        if (this.btnLeft != null) this.btnLeft.setVisibility(visibility);
    }

    public void setRightButtonText(CharSequence text) {
        if (this.btnRight != null) this.btnRight.setText(text);
    }

    public void setRightButtonBackgroundResource(int resId) {
        if (this.btnRight != null) this.btnRight.setBackgroundResource(resId);
    }

    public void setRightButtonBackgroundVisibility(int visibility) {
        if (this.btnRight != null) this.btnRight.setVisibility(visibility);
    }

    public void setRightImageButtonResource(int resId) {
        if (this.imgBtnRight != null) {
            this.imgBtnRight.setImageResource(resId);
        }
    }

    private void initializeAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopNavigationBar);
        boolean bVisible;
        int resId;

        if (this.btnLeft != null) {
            bVisible = typedArray.getBoolean(R.styleable.TopNavigationBar_leftButtonVisible, true);
            if (!bVisible) {
                this.btnLeft.setVisibility(View.GONE);
            }

            resId = typedArray.getResourceId(R.styleable.TopNavigationBar_leftButtonBackground, 0);
            if (resId != 0) {
                this.btnLeft.setBackgroundResource(resId);
            }
        }

        if (this.btnRight != null) {
            bVisible = typedArray.getBoolean(R.styleable.TopNavigationBar_rightButtonVisible, true);
            if (!bVisible) {
                this.btnRight.setVisibility(View.GONE);
            }

            resId = typedArray.getResourceId(R.styleable.TopNavigationBar_rightButtonBackground, 0);
            if (resId != 0) {
                this.btnRight.setBackgroundResource(resId);
            }

            this.btnRight.setText(typedArray.getText(R.styleable.TopNavigationBar_rightButtonText));
        }

        if (this.tvTitle != null) {
            CharSequence cs = typedArray.getText(R.styleable.TopNavigationBar_titleText);
            this.tvTitle.setText(cs);
            resId = typedArray.getResourceId(R.styleable.TopNavigationBar_titleBackground, 0);
            if (resId != 0) {
                this.tvTitle.setBackgroundResource(resId);
            }
        }

        if (this.mtopbar != null) {
            resId = typedArray.getResourceId(R.styleable.TopNavigationBar_barTitleBackground, -1);
            if (resId != -1) {
                this.mtopbar.setBackgroundResource(resId);
            }
        }
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTopBarLeft:
            case R.id.btnTopBarLeft:
                this.activity.finish();
                break;
        }
    }
}
