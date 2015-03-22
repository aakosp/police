package com.psb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Work;
import com.psb.ui.activity.ActivityOpinionInfo;
import com.psb.ui.activity.ActivityWork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/3/3.
 */
public class WorkAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Work> list;
    private Activity activity;
    private Intent intent;

    public WorkAdapter(Activity activity) {
        this.activity = activity;
        list = new ArrayList<>();
        intent = new Intent();
    }

    public void setWorks(List<Work> work) {
        this.addWorks(work);
        this.notifyDataSetChanged();
    }

    public void addWorks(List<Work> work) {
        list.addAll(work);
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
            holder.time = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
            convertView.setOnClickListener(this);
        } else {
            holder = (NewsInfoHolder) convertView.getTag();
        }
        Work item = list.get(position);
        holder.id = item.getId();
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        NewsInfoHolder holder = (NewsInfoHolder) v.getTag();
        intent.setClass(activity, ActivityWork.class);
        intent.putExtra("id", holder.id);
        this.activity.startActivity(intent);
    }

    private static class NewsInfoHolder {
        public int id;
        public TextView title;
        public TextView time;
    }
}