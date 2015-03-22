package com.psb.ui.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.psb.R;

/**
 * Created by aako on 2015/3/22.
 */
public class PhotoUtil implements View.OnClickListener {

    public PhotoUtil(Activity context, View root){
        this.context = context;
        this.root = root;
    }

    private Activity context;
    private PopupWindow popupWindow;
    private Button pai, xiang;
    private View root;

    public void initPopuptWindow() {
        if(null != popupWindow){
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            return;
        }
        View popupWindow_view = View.inflate(context, R.layout.pop_photo, null);
        pai = (Button) popupWindow_view.findViewById(R.id.pai);
        xiang = (Button) popupWindow_view.findViewById(R.id.xiang);
        pai.setOnClickListener(this);
        xiang.setOnClickListener(this);
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
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pai:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                context.startActivityForResult(intent, 1);
                popupWindow.dismiss();
                popupWindow = null;
                break;

            case R.id.xiang:
                Intent xiang = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(xiang, 0);
                popupWindow.dismiss();
                popupWindow = null;
                break;
        }
    }
}
