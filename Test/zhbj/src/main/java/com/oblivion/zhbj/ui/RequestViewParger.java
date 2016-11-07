package com.oblivion.zhbj.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by oblivion on 2016/11/2.
 */
public class RequestViewParger extends ViewPager {

    private float down_x;
    private float down_y;

    public RequestViewParger(Context context) {
        super(context);
    }

    public RequestViewParger(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父类呀买拦截
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_x = ev.getX();
                down_y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float move_x = ev.getX();
                float move_y = ev.getY();
                float disX = Math.abs(move_x - down_x);
                float disY = Math.abs(move_y - down_y);
                if (disX > disY) {//判断是否哪个方向滑动？--
                    //左右滑动不要拦截
                    if ((move_x - down_x) > 0) {
                        if (getCurrentItem() == 0) {//如果条目位置第一位，设置请求拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        if (getCurrentItem() == getAdapter().getCount()-1) {//如果条目位置最后一位，设置请求拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    //上下滑动，，拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
