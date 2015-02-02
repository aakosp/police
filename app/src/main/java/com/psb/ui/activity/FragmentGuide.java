package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.adapter.GuideAdapter;
import com.psb.core.AppContext;
import com.psb.entity.NewsInfo;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ViewPagerWithTitle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentGuide extends BaseFragment implements AdapterView.OnItemClickListener{

    private View mView;
    private EditText query;
    private ViewPagerWithTitle viewPager;

    private String guideColumns[] = AppContext.getInstance().getResources().getStringArray(R.array.guide_columns);
    private List<View> pageViews = new ArrayList<>();

    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_guide, container, false);
        query = (EditText) mView.findViewById(R.id.query);
        viewPager = (ViewPagerWithTitle) mView.findViewById(R.id.vp);
        this.initView();
        intent = new Intent();
        return mView;
    }

    private void initView() {
        for(int i=0; i<guideColumns.length; i++){
            PullToRefreshListView list = new PullToRefreshListView(this.getActivity());
//            list.setVerticalScrollBarEnabled(false);
            list.getRefreshableView().setVerticalScrollBarEnabled(false);
            GuideAdapter adapter = new GuideAdapter(this.getActivity());
            adapter.setList(getTest(i));
            list.setAdapter(adapter);
            list.setOnItemClickListener(this);
            pageViews.add(list);
        }
        viewPager.setTabs(guideColumns);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);
    }

    public List<NewsInfo> getTest(int index){
        String strTitle = "";
        String strInfo = "";
        switch (index){
            case 0:
                strTitle = "户政新闻";
                strInfo = "户政新闻内容1234567890 户政新闻内容1234567890 户政新闻内容1234567890 户政新闻内容1234567890 户政新闻内容1234567890";
                break;
            case 1:
                strTitle = "治安新闻";
                strInfo = "治安新闻内容1234567890 治安新闻内容1234567890 治安新闻内容1234567890 治安新闻内容1234567890 治安新闻内容1234567890";
                break;
            case 2:
                strTitle = "交通新闻";
                strInfo = "交通新闻内容1234567890 交通新闻内容1234567890 交通新闻内容1234567890 交通新闻内容1234567890 交通新闻内容1234567890";
                break;
            case 3:
                strTitle = "消防新闻";
                strInfo = "消防新闻内容1234567890 消防新闻内容1234567890 消防新闻内容1234567890 消防新闻内容1234567890 消防新闻内容1234567890";
                break;
            case 4:
                strTitle = "出入境新闻";
                strInfo = "出入境新闻内容1234567890 出入境新闻内容1234567890 出入境新闻内容1234567890 出入境新闻内容1234567890 出入境新闻内容1234567890";
                break;
        }
        List<NewsInfo> list = new ArrayList<>();
        for(int i=0; i<7; i++){
            NewsInfo title = new NewsInfo();
            title.setTitle(strTitle);
            title.setId(i);
            list.add(title);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsInfo title = (NewsInfo) parent.getAdapter().getItem(position);
        Log.d("onItemClick", title.getTitle());
        switch (title.getId()){
            case 99:
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }
}
