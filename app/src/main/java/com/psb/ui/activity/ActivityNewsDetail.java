package com.psb.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2015/1/30.
 */
public class ActivityNewsDetail extends BaseActivity {

    private TopNavigationBar topbar;
    private TextView title, detail;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        title = (TextView) findViewById(R.id.title);
        detail = (TextView) findViewById(R.id.detail);
        img = (ImageView) findViewById(R.id.img);

        topbar.setActivity(this);
        title.setText("测试新闻标题");
        detail.setText("北京时间1月30日，2015年全明星替补阵容出炉，目前在养伤的活塞后卫布兰登-詹宁斯谈到了全明星阵容。\n" +
                "\n" +
                "詹宁斯表示，他一直认为全明星首发应该由球员投票，替补名单则由教练决定。\n" +
                "\n" +
                "“贾马尔-克劳福德和蒙塔-埃利斯从来没进过全明星，这太疯狂了，”詹宁斯写到，“像詹姆斯-哈登应该首发，他随时一场比赛能拿30分的，大伙儿！”\n" +
                "\n" +
                "詹宁斯表示，如果让他投票，他会投给胜率超过50%球队里面的球员。他还表示，明年需要一些名人来给自己拉票。\n" +
                "\n" +
                "“那么，我猜明年我要说唱歌手，演员等等来为我全明星拉票，这就是你唯一需要做的事情。”詹宁斯写到，“我唯一需要的就是Nicki和碧昂斯为我拉票，然后我就能入选了。”\n" +
                "\n" +
                "“我没有酸，我就是感觉很多其他在赢球的人需要获得一次机会，像迈克-康利和布兰登-奈特。”詹宁斯写道。\n" +
                "\n" +
                "被问到是否在暗示猛龙后卫凯尔-洛瑞，詹宁斯说：“不，我就是广而言之。”\n" +
                "\n" +
                "詹宁斯还表示，活塞进入状态太晚了，以至于球队没人入选，称自己早说过马刺老将蒂姆-邓肯会入选。");
        img.setImageResource(R.drawable.test_one);
    }
}
