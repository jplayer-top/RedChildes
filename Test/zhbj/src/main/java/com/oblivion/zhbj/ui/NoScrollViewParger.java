package com.oblivion.zhbj.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by oblivion on 2016/10/31.
 */
public class NoScrollViewParger extends ViewPager {
    public NoScrollViewParger(Context context) {
        super(context);
    }

    public NoScrollViewParger(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 禁止滑动事件的传递
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 让其子View的事件不会被拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

}
