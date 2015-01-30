package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.psb.R;
import com.psb.core.AppContext;
import com.psb.entity.NewsTitle;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ViewPagerWithTitle;
import com.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentNews extends BaseFragment {

    private View mView;
    private ViewPagerWithTitle viewPager;
    private List<View> pageViews = new ArrayList<>();
    private String newsColumns[] = AppContext.getInstance().getResources().getStringArray(R.array.news_columns);
    private NewsPolice mNewsPolice;
    private NewsPresence mNewsPresence;
    private NewsWarning mNewsWarning;
    private NewsSecurity mNewsSecurity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_news, container, false);
        viewPager = (ViewPagerWithTitle) mView.findViewById(R.id.vp);
        this.initView();
        return mView;
    }

    private void initView() {
        mNewsPolice = new NewsPolice(this.getActivity());
        mNewsPresence = new NewsPresence(this.getActivity());
        mNewsWarning = new NewsWarning(this.getActivity());
        mNewsSecurity = new NewsSecurity(this.getActivity());

        pageViews.add(mNewsPolice);
        pageViews.add(mNewsPresence);
        pageViews.add(mNewsWarning);
        pageViews.add(mNewsSecurity);
        viewPager.setTabs(newsColumns);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);

        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        List<View> banner = new ArrayList<>();
        ImageView iv1 = new ImageView(this.getActivity());
        iv1.setImageResource(R.drawable.test_one);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView iv2 = new ImageView(this.getActivity());
        iv2.setImageResource(R.drawable.test_two);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView iv3 = new ImageView(this.getActivity());
        iv3.setImageResource(R.drawable.test_three);
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        banner.add(iv1);
        banner.add(iv2);
        banner.add(iv3);
        mNewsPolice.setBanner(banner);

        List<NewsTitle> news = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsTitle item = new NewsTitle();
            item.setId("" + i);
            item.setImg("http://imgsrc.baidu.com/forum/pic/item/7e3e6709c93d70cf97cf8c93f8dcd100baa12b72.jpg");
            item.setTitle("第" + i + "条新闻");
            item.setTime(TimeUtil.getYYYYMMDDDate());
            news.add(item);
        }

        mNewsPolice.addNews(news);

        mNewsPolice.scrollTop();

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent();
                t.setClass(FragmentNews.this.getActivity(), ActivityRegister.class);
                FragmentNews.this.getActivity().startActivity(t);
            }
        });
    }
}
