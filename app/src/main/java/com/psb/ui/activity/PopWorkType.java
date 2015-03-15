package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.psb.R;
import com.psb.event.Event;
import com.psb.ui.base.BaseActivity;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by aako on 2015/3/15.
 */
public class PopWorkType extends BaseActivity implements OnWheelChangedListener {

    private Button ok;
    private WheelView type;
    private WheelView wvCun;
    private WorkTypeItem[] typeData;
    private String strType;
    private String strId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_work_type);
        type = (WheelView) findViewById(R.id.type);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", strType);
                intent.putExtra("id", strId);
                setResult(Event.RESULT_WORK, intent);
                finish();
            }
        });
        WorkTypeItem item1 = new WorkTypeItem("OPINION", "社情民意");
        WorkTypeItem item2 = new WorkTypeItem("DISPUTE", "矛盾纠纷");
        WorkTypeItem item3 = new WorkTypeItem("SECURITY", "治安防范");
        WorkTypeItem item4 = new WorkTypeItem("EDUCATION", "宣传教育");
        WorkTypeItem item5 = new WorkTypeItem("SERVING", "服务群众");

        typeData = new WorkTypeItem[]{item1, item2, item3, item4, item5};
        type.setViewAdapter(new ArrayWheelAdapter<WorkTypeItem>(this, typeData));
        type.addChangingListener(this);
        type.setVisibleItems(3);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        strId = typeData[newValue].id;
        strType = typeData[newValue].type;
    }

    private class WorkTypeItem {

        public WorkTypeItem(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String id;
        public String type;

        @Override
        public String toString() {
            return type;
        }
    }
}
