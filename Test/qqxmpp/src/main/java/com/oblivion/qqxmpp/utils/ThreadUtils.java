package com.oblivion.qqxmpp.utils;

import android.os.Handler;

/**
 * Created by oblivion on 2016/11/9.
 */
public class ThreadUtils {
    private static Handler handler = new Handler();

    public static void runInThread(Runnable runnable) {
        new Thread(runnable).start();//别忘记开启子线程
    }

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
