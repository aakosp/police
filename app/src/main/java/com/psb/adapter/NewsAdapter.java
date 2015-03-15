package com.psb.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.entity.Article;
import com.psb.entity.NewsInfo;
import com.psb.ui.util.ImageUtil;
import com.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/27.
 */
public class NewsAdapter extends BaseAdapter {

    private List<NewsInfo> news;

    public NewsAdapter(List<NewsInfo> titles) {
        news = new ArrayList<>();
        news.addAll(titles);
    }

    public void setArticle(Article articles) {
        if (articles.getCurrent_page() == 1) {
            this.setNews(articles.getData());
        } else {
            this.addNews(articles.getData());
        }
    }

    public void setNews(List<NewsInfo> titles) {
        news.clear();
        news.addAll(titles);
    }

    public void addNews(List<NewsInfo> titles) {
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
        NewsInfoHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_news_title, null);
            holder = new NewsInfoHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (NewsInfoHolder) convertView.getTag();
        }
        NewsInfo item = news.get(position);
        if (StringUtils.isEmpty(item.getThumb())) {
            holder.img.setVisibility(View.GONE);
        } else {
            holder.img.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(item.getThumb() + ImageUtil.LIST, holder.img, ImageUtil.options);
        }
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
        return convertView;
    }

    private static class NewsInfoHolder {
        public ImageView img;
        public TextView title;
        public TextView time;
    }
}
