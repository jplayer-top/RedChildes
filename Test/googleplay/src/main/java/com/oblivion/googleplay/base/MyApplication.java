package com.oblivion.googleplay.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by oblivion on 2016/11/14.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler mMainHandler;
    private static int mMainThreadId;

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取主线程Handler
     *
     * @return
     */
    public static Handler getmMainHandler() {
        return mMainHandler;
    }

    /**
     * 获取主线程Handler
     *
     * @return
     */
    public static int getmMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mMainHandler = new Handler();
        mMainThreadId = Process.myTid();

    }
}
