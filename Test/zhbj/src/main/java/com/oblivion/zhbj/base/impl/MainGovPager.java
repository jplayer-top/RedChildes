package com.oblivion.zhbj.base.impl;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.oblivion.zhbj.base.BaseMenuPager;

/**
 * Created by oblivion on 2016/10/31.
 */
public class MainGovPager extends BaseMenuPager {
    public MainGovPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        System.out.println("gov加载");
        //添加数据
        TextView textView = new TextView(mActivity);
        textView.setText("主界面-gov界面");
        textView.setGravity(Gravity.CENTER);
        iv_slide2left.setVisibility(View.VISIBLE);
        tv_title.setText("政务处理");
        fl_main_parger.addView(textView);
    }
}
