package com.oblivion.zhbj.base.impl.newsdetial;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.oblivion.zhbj.base.BaseNewsCenterParger;

/**
 * Created by oblivion on 2016/11/1.
 */
public class MenuUnitl extends BaseNewsCenterParger {
    public MenuUnitl(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        //添加数据
        TextView textView = new TextView(mActivity);
        textView.setText("News界面-unitl界面");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
