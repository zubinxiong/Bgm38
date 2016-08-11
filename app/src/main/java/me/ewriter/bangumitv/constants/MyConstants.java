package me.ewriter.bangumitv.constants;

import java.io.File;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.utils.Md5Util;

/**
 * Created by Zubin on 2016/7/26.
 */
public class MyConstants {
    // App  缓存目录
    public static final String RootCacheDir = BangumiApp.sAppCtx.getBangumiDirPath() + File.separator;

    // token 缓存目录/文件名
    public static final String TOKEN_PATH = RootCacheDir + Md5Util.md5("bangumi_token");

    // fragment 的 tag，用来处理点击navigation时的跳转
    public static final String TAG_CALENDER_FRAGMENT = "CalenderFragment";
    public static final String TAG_MY_COLLECTION = "MyCollectionFragment";

    // 登录状态的spf key 和 value
    public static final String LOGIN_MANAGER_NAME = Md5Util.md5("LOGIN_MANAGER_NAME");
    public static final String LOGIN_MANAGER_AUTH_KEY = Md5Util.md5("LOGIN_MANAGER_AUTH_KEY");
    public static final String LOGIN_MANAGER_USER_ID_KEY = Md5Util.md5("LOGIN_MANAGER_USER_ID_KEY");
    public static final String LOGIN_MANAGER_HOME_URL_KEY = Md5Util.md5("LOGIN_MANAGER_HOME_URL_KEY");
    public static final String LOGIN_MANAGER_NICKNAME_KEY = Md5Util.md5("LOGIN_MANAGER_NICKNAME_KEY");
    public static final String LOGIN_MANAGER_AVATAR_KEY = Md5Util.md5("LOGIN_MANAGER_AVATAR_KEY");
    public static final String LOGIN_MANAGER_SIGN_KEY = Md5Util.md5("LOGIN_MANAGER_SIGN_KEY");
    public static final String LOGIN_MANAGER_AUTH_ENCODE_KEY = Md5Util.md5("LOGIN_MANAGER_AUTH_ENCODE_KEY");

    /** 每日放送刷新时间，超过6小时刷新一次，使用默认的Name*/
    public static final String CALENDAR_REFRESH_KEY = Md5Util.md5("CALENDAR_REFRESH_KEY");
    public static final long CALENDAR_REFRESH_TIME = 21600000;

    public static final String BUGLY_APPID = "900046055";
}
