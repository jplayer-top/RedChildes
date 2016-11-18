package com.oblivion.mvp_fragment.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by oblivion on 2016/11/18.
 */
public class MyApplication extends Application {
    public static Context MainContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MainContext = getApplicationContext();
    }
}
