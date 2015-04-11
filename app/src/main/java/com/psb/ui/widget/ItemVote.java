package com.psb.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.psb.R;
import com.psb.entity.Vote;
import com.util.StringUtils;

/**
 * Created by zl on 2015/4/11.
 */
public class ItemVote extends LinearLayout {
    private TextView textView;
    private RadioGroup rg;

    public ItemVote(Context context) {
        this(context, null);
    }

    public ItemVote(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_vote, this, true);
        textView = (TextView) findViewById(R.id.minyi);
        rg = (RadioGroup) findViewById(R.id.rg);
    }

    public void setVote(Vote vote){
        if(null != vote){
            textView.setText(vote.getVote_title());
            Vote.Opt opt = vote.getOption();
            if(null == opt){
                return;
            }
            if(!StringUtils.isEmpty(opt.getA())){
                RadioButton rb = new RadioButton(this.getContext());
                rb.setText("A. "+opt.getA());
                rb.setTag("A");
                rg.addView(rb);
            }
            if(!StringUtils.isEmpty(opt.getB())){
                RadioButton rb = new RadioButton(this.getContext());
                rb.setText("B. "+opt.getB());
                rb.setTag("B");
                rg.addView(rb);
            }
            if(!StringUtils.isEmpty(opt.getC())){
                RadioButton rb = new RadioButton(this.getContext());
                rb.setText("C. "+opt.getC());
                rb.setTag("C");
                rg.addView(rb);
            }
            if(!StringUtils.isEmpty(opt.getD())){
                RadioButton rb = new RadioButton(this.getContext());
                rb.setText("D. "+opt.getD());
                rb.setTag("D");
                rg.addView(rb);
            }
        }
    }

    public String getChecked(){
        for(int i=0; i<rg.getChildCount(); i++){
            RadioButton rb = (RadioButton) rg.getChildAt(i);
            if(rb.isChecked()){
                return (String)rb.getTag();
            }
        }
        return "";
    }

}
