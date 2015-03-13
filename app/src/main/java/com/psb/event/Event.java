package com.psb.event;

/**
 * Created by zl on 2014/12/3.
 */

public class Event {

    public static final int NEWS_1 = 1;                 //警务新闻
    public static final int NEWS_2 = 2;                 //警队风采
    public static final int NEWS_3 = 3;                 //预警信息
    public static final int NEWS_4 = 4;                 //安防

    public static final int NEWS_5 = 5;                 //户政
    public static final int NEWS_6 = 6;                 //治安
    public static final int NEWS_7 = 7;                 //交通
    public static final int NEWS_8 = 8;                 //消防
    public static final int NEWS_9 = 9;                 //出入境

    public static final int GET_USER = 10;              //获取用户资料
    public static final int SGIN = 11;                  //签到
    public static final int GET_FEEDBACK = 12;          //获取单个意见回复
    public static final int GET_OPINION = 13;           //获取意见
    public static final int OPINION_FEEDBACK = 14;      //意见处理
    public static final int GET_OPINION_LIST = 15;      //获取民众意见列表

    public static final int GET_ADDRS = 16;             //获取乡镇列表
    public static final int GET_OFFICE_LIST = 17;       //获取警务室列表
    public static final int GET_POLICE_LIST = 18;       //获取民警信息
    public static final int REGISTER = 19;              //注册
    public static final int CHANGE_PWD = 20;            //修改密码

    public static final int COMMIT_WORK = 21;            //提交工作记录


    public static final int RESULT_SEX = 100;           //获取性别
    public static final int RESULT_AREA = 101;          //获取地区
}
