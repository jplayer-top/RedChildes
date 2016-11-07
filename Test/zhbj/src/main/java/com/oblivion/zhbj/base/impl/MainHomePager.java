package com.oblivion.zhbj.base.impl;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.oblivion.zhbj.base.BaseMenuPager;

/**
 * Created by oblivion on 2016/10/31.
 */
public class MainHomePager extends BaseMenuPager {
    public MainHomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("home加载");
        TextView textView = new TextView(mActivity);
        textView.setText("主界面-home界面");
        textView.setGravity(Gravity.CENTER);
        iv_slide2left.setVisibility(View.GONE);
        tv_title.setText("Home界面");
        fl_main_parger.addView(textView);
    }
}
