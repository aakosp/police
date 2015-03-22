package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.psb.R;
import com.psb.entity.Addr;
import com.psb.event.Event;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by zl on 2015/3/13.
 */
public class ActivityArea extends BaseActivity implements OnWheelChangedListener {

    private Button ok;
    private WheelView wvXiang;
    private WheelView wvCun;
    private AddrItem[] mXiangDatas;
    private Map<Integer, AddrItem[]> mCunDatasMap = new HashMap<>();
    private int mCurrentXiangName, mCurrentCunName;
    private String strCun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        wvXiang = (WheelView) findViewById(R.id.xiang);
        wvCun = (WheelView) findViewById(R.id.cun);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("areaid", mCurrentCunName);
                intent.putExtra("areastr", strCun);
                setResult(Event.RESULT_AREA, intent);
                finish();
            }
        });
        initDatas();
        wvXiang.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, mXiangDatas));
        wvXiang.addChangingListener(this);
        wvCun.addChangingListener(this);
        wvXiang.setVisibleItems(5);
        wvCun.setVisibleItems(5);
        updateAreas();
    }

    private void initDatas() {
        if (null == Cache.getInstance().getAddr()) {
            return;
        }
        mXiangDatas = new AddrItem[Cache.getInstance().getAddr().size()];
        for (int i = 0; i < Cache.getInstance().getAddr().size(); i++) {
            Addr addrxiang = Cache.getInstance().getAddr().get(i);
            AddrItem xiangItem = new AddrItem(addrxiang.getId(), addrxiang.getName());
            mXiangDatas[i] = xiangItem;
            AddrItem mCunDatas[] = new AddrItem[addrxiang.getChild().size()];
            for (int j = 0; j < addrxiang.getChild().size(); j++) {
                mCunDatas[j] = new AddrItem(addrxiang.getChild().get(j).getId(), addrxiang.getChild().get(j).getName());
            }
            mCunDatasMap.put(addrxiang.getId(), mCunDatas);
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wvXiang) {
            updateAreas();
        } else if (wheel == wvCun) {
            mCurrentCunName = mCunDatasMap.get(mCurrentXiangName)[newValue].id;
            strCun = mCunDatasMap.get(mCurrentXiangName)[newValue].addr;
        }
    }

    private void updateAreas() {
        int pCurrent = wvXiang.getCurrentItem();
        mCurrentXiangName = mXiangDatas[pCurrent].id;
        AddrItem[] areas = mCunDatasMap.get(mCurrentXiangName);

        if (areas == null) {
            return;
        }
        wvCun.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, areas));
        wvCun.setCurrentItem(0);
        mCurrentCunName = mCunDatasMap.get(mCurrentXiangName)[0].id;
        strCun = mCunDatasMap.get(mCurrentXiangName)[0].addr;
    }


    private class AddrItem {

        public AddrItem(int id, String addr) {
            this.id = id;
            this.addr = addr;
        }

        public String addr;
        public int id;

        @Override
        public String toString() {
            return addr;
        }
    }
}
