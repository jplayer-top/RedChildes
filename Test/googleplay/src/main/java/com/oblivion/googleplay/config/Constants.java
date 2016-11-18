package com.oblivion.googleplay.config;

import com.oblivion.googleplay.utils.LogUtils;

/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class Constants {
    /*
    LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
    LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final String URL = "http://10.0.3.2:8080/GooglePlayServer/";
    //http://localhost:8080/GooglePlayServer/image?name=app/com.itheima.www/icon.jpg
    public static final String ICONURL = URL + "/image?name=";
    private static final String HOME = "首页";
    private static final String APP = "应用";
    private static final String GAME = "游戏";
    private static final String SUBJECT = "专题";
    private static final String SHARE = "推荐";
    private static final String SORT = "分类";
    private static final String ORDER = "排行";
    //缓存过期时间
    public static final long DEFAULTCACHETIME = 5 * 60 * 1000;
}
