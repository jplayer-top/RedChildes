package com.oblivion.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by oblivion on 2016/11/1.
 */
public abstract class BaseNewsCenterParger {
    public Activity mActivity;

    public BaseNewsCenterParger(Activity activity) {
        this.mActivity = activity;
    }

    public abstract View initView();
    public void initData(){};




}
