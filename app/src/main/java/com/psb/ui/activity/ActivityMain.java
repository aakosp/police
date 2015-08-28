package com.psb.ui.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.psb.R;
import com.psb.core.AppContext;
import com.psb.entity.Version;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragmentActivity;
import com.psb.ui.widget.NaviTabButton;
import com.psb.ui.widget.TopNavigationBar;
import com.util.DialogHelper;
import com.util.UpdateUtil;

/**
 * Created by zl on 2014/11/21.
 */
public class ActivityMain extends BaseFragmentActivity {

    private TopNavigationBar topbar;
    private NaviTabButton mTabButtons[];
    private Fragment mFragments[];
    private ProgressDialog updateProgressDialog;
    private UpdateUtil updateUtil;
    private  UpdateUtil.UpdateCallback appUpdateCb;
    private int versionCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        initTab();
        initFragment();
        setFragmentIndicator(0);

        Api.getInstance().checkVersion();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.CHECK_VERSION);
    }

    private void initTab() {
        mTabButtons = new NaviTabButton[4];

        mTabButtons[0] = (NaviTabButton) findViewById(R.id.tabbutton_news);
        mTabButtons[1] = (NaviTabButton) findViewById(R.id.tabbutton_navigation);
        mTabButtons[2] = (NaviTabButton) findViewById(R.id.tabbutton_gruid);
        mTabButtons[3] = (NaviTabButton) findViewById(R.id.tabbutton_profile);

        //mTabButtons[0].setTitle(getString(R.string.news));
        mTabButtons[0].setIndex(0);
        mTabButtons[0].setSelectedImage(getResources().getDrawable(R.drawable.tab_news_press));
        mTabButtons[0].setUnselectedImage(getResources().getDrawable(R.drawable.tab_news));

        //mTabButtons[1].setTitle(getString(R.string.navigation));
        mTabButtons[1].setIndex(1);
        mTabButtons[1].setSelectedImage(getResources().getDrawable(R.drawable.tab_navigation_press));
        mTabButtons[1].setUnselectedImage(getResources().getDrawable(R.drawable.tab_navigation));

        //mTabButtons[2].setTitle(getString(R.string.guide));
        mTabButtons[2].setIndex(2);
        mTabButtons[2].setSelectedImage(getResources().getDrawable(R.drawable.tab_guide_press));
        mTabButtons[2].setUnselectedImage(getResources().getDrawable(R.drawable.tab_guide));

        //mTabButtons[3].setTitle(getString(R.string.profile));
        mTabButtons[3].setIndex(3);
        mTabButtons[3].setSelectedImage(getResources().getDrawable(R.drawable.tab_profile_press));
        mTabButtons[3].setUnselectedImage(getResources().getDrawable(R.drawable.tab_profile));
    }

    private void initFragment() {
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_news);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_navigation);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_gruid);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_profile);
    }

    /**
     * @param which
     */
    public void setFragmentIndicator(int which) {
        switch (which) {
            case 0:
                topbar.setTitleText(this.getResources().getText(R.string.news));
                break;
            case 1:
                topbar.setTitleText(this.getResources().getText(R.string.navigation));
                break;
            case 2:
                topbar.setTitleText(this.getResources().getText(R.string.guide));
                break;
            case 3:
                topbar.setTitleText(this.getResources().getText(R.string.profile));
                break;
        }
        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
        mTabButtons[0].setSelectedButton(false);
        mTabButtons[1].setSelectedButton(false);
        mTabButtons[2].setSelectedButton(false);
        mTabButtons[3].setSelectedButton(false);
        mTabButtons[which].setSelectedButton(true);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what){
            case Event.CHECK_VERSION:
                update();
                break;
        }
    }

    private void update(){
        Version v = Cache.getInstance().getVersion();
        if(null == v){
            return;
        }
        int n = 0;
        try{
            n = Integer.valueOf(v.getVersion());
        } catch (Exception e){
            return;
        }

        try {
            PackageInfo pInfo = AppContext.getInstance().getPackageManager().getPackageInfo(AppContext.getInstance().getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(n <= versionCode){
            return;
        }
//        Log.d("update", v.getUrl());
        if(null == appUpdateCb){
            appUpdateCb = new UpdateUtil.UpdateCallback() {

                Version ver = Cache.getInstance().getVersion();

                public void downloadProgressChanged(int progress) {
                    if (updateProgressDialog != null
                            && updateProgressDialog.isShowing()) {
                        updateProgressDialog.setProgress(progress);
                    }

                }

                public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
                    if (updateProgressDialog != null
                            && updateProgressDialog.isShowing()) {
                        updateProgressDialog.dismiss();
                    }
                    if (sucess) {
                        updateUtil.update();
                    } else {
                        DialogHelper.Confirm(ActivityMain.this,
                                R.string.dialog_error_title,
                                R.string.dialog_downfailed_msg,
                                R.string.dialog_downfailed_btnnext,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        updateUtil.downloadPackage(ver.getUrl());
                                    }
                                }, R.string.dialog_cancel, null);
                    }
                }

                public void downloadCanceled() {
                    // TODO Auto-generated method stub

                }

                public void checkUpdateCompleted(Boolean hasUpdate,
                                                 CharSequence updateInfo) {
                    if (hasUpdate) {
                        DialogHelper.Confirm(ActivityMain.this,
                                getText(R.string.dialog_update_title),
                                getText(R.string.dialog_update_msg).toString(),
                                getText(R.string.dialog_update_btnupdate),
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        updateProgressDialog = new ProgressDialog(
                                                ActivityMain.this);
                                        updateProgressDialog
                                                .setMessage(getText(R.string.dialog_downloading_msg));
                                        updateProgressDialog.setIndeterminate(false);
                                        updateProgressDialog
                                                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                        updateProgressDialog.setMax(100);
                                        updateProgressDialog.setProgress(0);
                                        updateProgressDialog.show();

                                        updateUtil.downloadPackage(ver.getUrl());
                                    }
                                }, getText(R.string.dialog_cancel), null);
                    }
                }
            };
        }
        if(null == updateUtil){
            updateUtil = new UpdateUtil(this, appUpdateCb);
        }
//        if(null == updateOkListener){
//            updateOkListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    updateUtil.downloadPackage(v.getUrl());
//                }
//            };
//        }
//        if(null == updateListener){
//            updateListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            };
//        }
//        DialogHelper.Confirm(this, "更新提示", "发现新版本", "确定", updateOkListener, "取消", updateListener);
        DialogHelper.Confirm(ActivityMain.this,
                getText(R.string.dialog_update_title),
                getText(R.string.dialog_update_msg).toString(),
                getText(R.string.dialog_update_btnupdate),
                new DialogInterface.OnClickListener() {
                    Version ver = Cache.getInstance().getVersion();
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        updateProgressDialog = new ProgressDialog(
                                ActivityMain.this);
                        updateProgressDialog
                                .setMessage(getText(R.string.dialog_downloading_msg));
                        updateProgressDialog.setIndeterminate(false);
                        updateProgressDialog
                                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        updateProgressDialog.setMax(100);
                        updateProgressDialog.setProgress(0);
                        updateProgressDialog.show();

                        updateUtil.downloadPackage(ver.getUrl());
                    }
                }, getText(R.string.dialog_cancel), null);
    }
}
