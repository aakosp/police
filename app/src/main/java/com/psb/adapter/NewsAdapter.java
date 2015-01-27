package com.psb.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psb.entity.NewsTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/27.
 */
public class NewsAdapter extends BaseAdapter{

    private List<NewsTitle> news;

    public NewsAdapter(List<NewsTitle> titles){
        news = new ArrayList<>();
        news.addAll(titles);
    }

    public void addNews(List<NewsTitle> titles){
        news.addAll(titles);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private static class NewsTitleHolder {
        public ImageView img;
        public TextView title;
    }
}
