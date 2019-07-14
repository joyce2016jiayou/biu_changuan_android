package com.noplugins.keepfit.android.util.net.entity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18030150 on 2018/3/22.
 */

public class Contacts {
    //举报组合
    public static ArrayList<String> typeMsg = new ArrayList<>();
    //列表个数
    public static int itemNub = 0;
    //更新名
    public static String upDataName = null;
    //版本号
    public static String version = null;
    //本地版本号
    public static String VersionName = null;
    //版本下载地址
    public static String appAddress = null;
    //记录是否第一次启动
    public static boolean isStart = false;
    //选择页面
    public static String selectTab = "0";
    //跳转页面
    public static String isPage = "1";
    //手机厂商和型号
    public static String brand;
    //是否注册
    public static String typeMeg = "0";
    //标签选中状态
    public static boolean selsctType = false;
    //标签选中状态
    public static int deleteSelsctType = -1;
    //关闭所有Activity
    public static List<Activity> activities = new ArrayList<>();
    //关注快速置顶按钮
    public static String attentionType;
    //精选快速置顶按钮
    public static String hotType;
    //最新快速置顶按钮
    public static String newType;
    //推荐用户滑动数
    public static int scrollPosition = 0;
    //判断是否隐藏
    public static boolean isGone = false;
    //第三方登录id
    public static String invitationCode;
    //微信是否绑定
    public static String weixin_login;
    //微信昵称
    public static String weixin_name;
    //微博是否绑定
    public static String weibo_login;
    //微博昵称
    public static String weibo_name;
    //首页图片集合
    public static List<String> imageList = new ArrayList<>();
    //消息数
    public static String mesgNumber;
    //记录视频详情播放位置
    public static long currPosition = 0;
    //记录列表位置
    public static int currItem = 0;
    //是否确定播放
    public static boolean isPlay = false;
    //记录首页滑动位置
    public static int currenItem = 0;
    //记录首页滑动位置
    public static int firstVisibleItem = 0;
    //判断视频是否播放
    public static String isVideoPlay = "0";


    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String son_count;
    public static String answers_id;
    public static String Comment_id;
    public static String Users_avatar;
    public static String Users_name;
    public static String Comment_content;
    public static String Created_at;
    public static String Comment_thumbs;
    public static String Is_thumbs;
    public static String Users_id;

}
