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

    public static final int NOTICE = 10;                 //通知通报

    public static final int GET_USER = 26;              //获取用户资料
    public static final int SGIN = 11;                  //签到
    public static final int GET_FEEDBACK = 12;          //获取单个意见回复
    public static final int GET_OPINION = 13;           //获取意见
    public static final int OPINION_FEEDBACK = 14;      //意见处理
    public static final int GET_OPINION_LIST_OK = 15;      //获取民众意见列表(已处理)
    public static final int GET_OPINION_LIST_UNDO = 16;      //获取民众意见列表(未处理)

    public static final int GET_ADDRS = 17;             //获取乡镇列表
    public static final int GET_OFFICE_LIST = 18;       //获取警务室列表
    public static final int GET_POLICE_LIST = 19;       //获取民警信息
    public static final int REGISTER = 20;              //注册
    public static final int CHANGE_PWD = 21;            //修改密码

    public static final int COMMIT_WORK = 22;            //提交工作记录
    public static final int COMMIT_OPINION = 23;         //提交意见
    public static final int GET_WORK = 24;                 //获取工作记录
    public static final int CHULI = 25;                 //处理意见



    public static final int REFRESH_OPINION = 26;          //刷新意见列表

    public static final int GET_VOTE = 27;              //获取投票选项
    public static final int SET_VOTE = 28;              //投票
    public static final int CHECK_VOTE = 29;              //判断是否投票

    public static final int RESULT_SEX = 100;           //获取性别
    public static final int RESULT_AREA = 101;          //获取地区
    public static final int RESULT_WORK = 102;          //获取地区
    public static final int NOTICE_SIGN_BEGIN = 103;          //签到广播
    public static final int NOTICE_SIGN_END = 104;          //签到广播
}
