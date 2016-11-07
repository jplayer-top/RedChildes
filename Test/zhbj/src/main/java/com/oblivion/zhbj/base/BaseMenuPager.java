package com.oblivion.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oblivion.zhbj.MainActivity;
import com.oblivion.zhbj.R;

/**
 * Created by oblivion on 2016/10/31.
 */
public class BaseMenuPager {
    public Activity mActivity;
    public View mRootView;
    public ImageView iv_slide2left;
    public TextView tv_title;
    public FrameLayout fl_main_parger;
    public  ImageView iv_layoutmanager;

    public BaseMenuPager(Activity activity) {
        mActivity = activity;
        //提前初始化，子类继承之后并没有实现会空指针。
        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_menu_parger, null);
        iv_layoutmanager = (ImageView) view.findViewById(R.id.iv_layoutmanager);
        iv_slide2left = (ImageView) view.findViewById(R.id.iv_slideleft_menu);
        iv_slide2left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mActivity;
                SlidingMenu slidingMenu = mainActivity.sendSlidingMenu();
                //设置开关
                slidingMenu.toggle();
            }
        });
        fl_main_parger = (FrameLayout) view.findViewById(R.id.fl_main_parger);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData() {
    }
}
