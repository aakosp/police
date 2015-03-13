package com.psb.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.entity.NewsInfo;
import com.psb.entity.Opinion;
import com.psb.entity.Opinions;
import com.psb.ui.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/3/3.
 */
public class OpinionsAdapter extends BaseAdapter {

    private List<Opinion> list;

    public OpinionsAdapter(List<Opinion> opinions) {
        list = new ArrayList<>();
        list.addAll(opinions);
    }

    public void setOpinions(Opinions opinions) {
        if (opinions.getCurrent_page() == 1) {
            this.setOpinions(opinions.getData());
        } else {
            this.addOpinions(opinions.getData());
        }
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
        } else {
            holder = (NewsInfoHolder) convertView.getTag();
        }
        Opinion item = list.get(position);
        holder.title.setText(item.getTitle());
        if (Opinion.ANONYMOUS.equals(item.getType())) {
            holder.name.setText("匿名");
        } else {
            holder.name.setText(item.getUser().getName());
        }
        return convertView;
    }

    private static class NewsInfoHolder {
        public TextView title;
        public TextView name;
    }
}