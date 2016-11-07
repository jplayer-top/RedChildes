package com.oblivion.zhbj.base.impl;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.oblivion.zhbj.base.BaseMenuPager;

/**
 * Created by oblivion on 2016/10/31.
 */
public class MainSmartServerPager extends BaseMenuPager {
    public MainSmartServerPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("server加载");
        TextView textView = new TextView(mActivity);
        textView.setText("主界面-server界面");
        textView.setGravity(Gravity.CENTER);
        iv_slide2left.setVisibility(View.VISIBLE);
        tv_title.setText("智能服务");
        fl_main_parger.addView(textView);
    }
}
