package com.psb.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.psb.R;
import com.psb.adapter.NewsAdapter;
import com.psb.entity.NewsTitle;
import com.psb.ui.widget.ViewPagerWithPoint;

import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsPolice extends LinearLayout{

    private ViewPagerWithPoint banner;
    private ListView listView;
    private NewsAdapter adapter;

    public NewsPolice(Context context) {
        this(context, null);
    }

    public NewsPolice(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.news_police, this, true);
        banner = (ViewPagerWithPoint) findViewById(R.id.banner);
        listView = (ListView) findViewById(R.id.list);
    }

    public void setBanner(List<View> banners){
        banner.setPagerViews(banners);
    }

    public void addNews(List<NewsTitle> news){
        adapter = new NewsAdapter(news);
        listView.setAdapter(adapter);
    }
}
