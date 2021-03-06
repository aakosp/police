package com.psb.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.psb.R;
import com.psb.ui.util.ToastUtil;

public class BaseFragmentActivity extends FragmentActivity {

    protected WeakReferenceHandler handler = null;
    private boolean allowBreakDestroy = true;
    private int enterAnim = 0, exitAnim = R.anim.slide_out_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new WeakReferenceHandler(this);
        AppManager.getAppManager().addContext(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive() && getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    0);
        }
        if (allowBreakDestroy) {
            this.finish();
        }
        overridePendingTransition(enterAnim, exitAnim);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setAnim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
    }


    public void setAllowBreakDestroy(boolean allowDestroy) {
        this.allowBreakDestroy = allowDestroy;
    }

    public Handler getHandler() {
        return this.handler;
    }

    protected void handlerPacketMsg(Message msg) {
    }
}
