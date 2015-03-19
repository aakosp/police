package com.psb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Opinion;
import com.psb.entity.Opinions;
import com.psb.ui.activity.ActivityOpinionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/3/3.
 */
public class OpinionsAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Opinion> list;
    private Activity activity;
    private Intent intent;

    public OpinionsAdapter(Activity activity) {
        this.activity = activity;
        list = new ArrayList<>();
        intent = new Intent();
    }

    public void setOpinions(Opinions opinions) {
        if (opinions.getCurrent_page() == 1) {
            this.setOpinions(opinions.getData());
        } else {
            this.addOpinions(opinions.getData());
        }
        this.notifyDataSetChanged();
    }

    public void setOpinions(List<Opinion> opinions) {
        list.clear();
        list.addAll(opinions);
    }

    public void addOpinions(List<Opinion> opinions) {
        list.addAll(opinions);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsInfoHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_opinion, null);
            holder = new NewsInfoHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
            convertView.setOnClickListener(this);
        } else {
            holder = (NewsInfoHolder) convertView.getTag();
        }
        Opinion item = list.get(position);
        holder.id = item.getId();
        holder.title.setText(item.getTitle());
        if (Opinion.ANONYMOUS.equals(item.getType())) {
            holder.name.setText("匿名");
        } else {
            holder.name.setText(item.getUser().getName());
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        NewsInfoHolder holder = (NewsInfoHolder) v.getTag();
        intent.setClass(activity, ActivityOpinionInfo.class);
        intent.putExtra("id", holder.id);
        this.activity.startActivity(intent);
    }

    private static class NewsInfoHolder {
        public int id;
        public TextView title;
        public TextView name;
    }
}