package com.psb.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Work;
import com.psb.protocol.Api;
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
    private AlertDialog operation_dialog = null;
    private TextView operation_del = null;
    private DelClick delClick = new DelClick();
    private int pos = -1;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        NewsInfoHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_opinion, null);
            holder = new NewsInfoHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
            convertView.setOnClickListener(this);
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    pos = position;
                    showAlertMenu();
                    return false;
                }
            });
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

    public void showAlertMenu() {
        if (null == operation_dialog) {
            operation_dialog = new AlertDialog.Builder(this.activity)
                    .create();
            View menuView = LayoutInflater.from(this.activity).inflate(
                    R.layout.alert_msg_menu, null);
            operation_del = (TextView) menuView.findViewById(R.id.del);
            operation_dialog.setView(menuView);
            operation_del.setOnClickListener(delClick);
        }
        operation_dialog.show();
    }

    public class DelClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.del){
                Api.getInstance().delWork(list.get(pos).getId());
                list.remove(pos);
                WorkAdapter.this.notifyDataSetChanged();
                operation_dialog.dismiss();
            }
        }
    }
}