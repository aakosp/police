package com.psb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Article;
import com.psb.entity.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/2.
 */
public class GuideAdapter extends BaseAdapter {

    private List<NewsInfo> list = new ArrayList<>();
    private Context context;

    public GuideAdapter(Context context) {
        this.context = context;
    }

    public void setArticle(Article articles){
        if(articles.getPer_page() == 1){
            this.setList(articles.getData());
        } else{
            this.addList(articles.getData());
        }
    }

    public void setList(List<NewsInfo> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public void addList(List<NewsInfo> list) {
        this.list.addAll(list);
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
        TextView title = null;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_news_noimg, null);
        }
        title = (TextView) convertView.findViewById(R.id.title);
        NewsInfo item = list.get(position);
        title.setText(item.getTitle());
        return convertView;
    }

}
