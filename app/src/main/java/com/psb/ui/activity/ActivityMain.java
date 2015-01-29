package com.psb.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.psb.R;
import com.psb.ui.base.BaseFragmentActivity;
import com.psb.ui.widget.NaviTabButton;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2014/11/21.
 */
public class ActivityMain extends BaseFragmentActivity {

    private TopNavigationBar topbar;
    private NaviTabButton mTabButtons[];
    private Fragment mFragments[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        initTab();
        initFragment();
        setFragmentIndicator(0);
    }

    private void initTab() {
        mTabButtons = new NaviTabButton[4];

        mTabButtons[0] = (NaviTabButton) findViewById(R.id.tabbutton_news);
        mTabButtons[1] = (NaviTabButton) findViewById(R.id.tabbutton_navigation);
        mTabButtons[2] = (NaviTabButton) findViewById(R.id.tabbutton_gruid);
        mTabButtons[3] = (NaviTabButton) findViewById(R.id.tabbutton_profile);

        mTabButtons[0].setTitle(getString(R.string.news));
        mTabButtons[0].setIndex(0);
//        mTabButtons[0].setSelectedImage(getResources().getDrawable(R.drawable.tab_broadcast_press));
//        mTabButtons[0].setUnselectedImage(getResources().getDrawable(R.drawable.tab_broadcast));

        mTabButtons[1].setTitle(getString(R.string.navigation));
        mTabButtons[1].setIndex(1);
//        mTabButtons[1].setSelectedImage(getResources().getDrawable(R.drawable.informationicons3_33));
//        mTabButtons[1].setUnselectedImage(getResources().getDrawable(R.drawable.informationicon3_3));

        mTabButtons[2].setTitle(getString(R.string.guide));
        mTabButtons[2].setIndex(2);
//        mTabButtons[2].setSelectedImage(getResources().getDrawable(R.drawable.wishtalk));
//        mTabButtons[2].setUnselectedImage(getResources().getDrawable(R.drawable.wishtalk));

        mTabButtons[3].setTitle(getString(R.string.profile));
        mTabButtons[3].setIndex(3);
//        mTabButtons[3].setSelectedImage(getResources().getDrawable(R.drawable.found3_3));
//        mTabButtons[3].setUnselectedImage(getResources().getDrawable(R.drawable.found3));
    }

    private void initFragment() {
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_news);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_navigation);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_gruid);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_profile);
    }

    /**
     *
     * @param which
     */
    public void setFragmentIndicator(int which) {
        switch (which){
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
}
