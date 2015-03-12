package com.psb.ui.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.psb.R;
import com.psb.ui.activity.ActivityLogin;
import com.psb.ui.activity.ActivityWork;

/**
 * Created by zl on 2015/3/12.
 */
public class TipsLogin implements View.OnClickListener {

    public TipsLogin(Context context){
        this.context = context;
    }

    private Context context;
    private PopupWindow popupWindow;
    private Button login;

    public void initPopuptWindow(View parent) {
        if(null != popupWindow){
            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
            return;
        }
        View popupWindow_view = View.inflate(context, R.layout.tips, null);
        login = (Button) popupWindow_view.findViewById(R.id.login);
        login.setOnClickListener(this);
        popupWindow = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popupWindow_view);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Intent intent = new Intent();
                intent.setClass(context, ActivityLogin.class);
                this.context.startActivity(intent);
                popupWindow.dismiss();
                popupWindow = null;
                break;
        }
    }
}
