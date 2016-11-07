package com.oblivion.zhbj.utils;

import android.content.Context;

/**
 * Created by oblivion on 2016/11/2.
 */
public class CacheUtils {
    /**
     * 设定缓存
     *
     * @param json
     * @param context
     */
    public static void setCache(String json, Context context) {
        PrefUtils.putString(context, "url", json);
    }

    /**
     * 获取缓存
     *
     * @param context
     */
    public static String  getCache(Context context) {
        return PrefUtils.getString(context, "url", null);
    }
}
