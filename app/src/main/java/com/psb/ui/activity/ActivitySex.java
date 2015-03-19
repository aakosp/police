package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.psb.R;
import com.psb.event.Event;
import com.psb.ui.base.BaseActivity;

/**
 * Created by zl on 2015/3/13.
 */
public class ActivitySex extends BaseActivity implements View.OnClickListener {

    private RadioButton male = null;
    private RadioButton female = null;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        male.setChecked(true);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                Intent intent = new Intent();
                int isMale = 1;
                if (!male.isChecked()) {
                    isMale = 0;
                }
                intent.putExtra("sex", isMale);
                this.setResult(Event.RESULT_SEX, intent);
                finish();
                break;
        }
    }
}
