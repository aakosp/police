package com.psb.ui.activity;

import android.os.Bundle;
import android.widget.ListView;
import com.psb.R;
import com.psb.adapter.OfficeAdapter;
import com.psb.entity.OfficeInfo;
import com.psb.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/1.
 */
public class ActivityOffice extends BaseActivity{

    private ListView mList;
    private OfficeAdapter officeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        this.mList = (ListView) findViewById(R.id.list);

        officeAdapter = new OfficeAdapter(this);
        List<OfficeInfo> list = new ArrayList<>();
        for(int i=0; i<6; i++){
            OfficeInfo info = new OfficeInfo();
            info.setId(""+i);
            info.setAddr("汤阴大付庄村");
            info.setTel("32323553");
            info.setName("汤阴大付庄警务室");
            info.setLatitude(34.736352f);
            info.setLontitude(113.606382f);
            list.add(info);
        }
        officeAdapter.setOfficeInfoList(list);
        mList.setAdapter(officeAdapter);
    }
}
