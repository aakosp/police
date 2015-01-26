package com.psb.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.psb.R;

public class TextViewTag extends TextView {
    Context mcontext;

    public TextViewTag(Context context) {
        super(context);
        mcontext = context;
        initColor();
    }

    public TextViewTag(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        initColor();
    }

    public TextViewTag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mcontext = context;
        initColor();
    }

    public void setText(String text) {
        super.setText(text);
        initColor();
    }

    private void initColor() {
        try {
            if (Integer.valueOf(this.getText().toString()) > 0)
                this.setTextColor(mcontext.getResources().getColor(
                        R.color.topbar));
            else
                this.setTextColor(mcontext.getResources().getColor(
                        R.color.linecolor));
            if (Integer.valueOf(this.getText().toString()) < 0)
                this.setText("0");
        } catch (Exception e) {
            this.setText("0");
        }
    }

}
