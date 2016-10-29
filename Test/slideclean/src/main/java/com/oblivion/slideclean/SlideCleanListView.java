package com.oblivion.slideclean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by oblivion on 2016/10/29.
 */
public class SlideCleanListView extends ListView {

    private int downX;
    private int downY;
    private boolean flag;

    public SlideCleanListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideCleanListView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("int");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("dis");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("tou");
        return super.onTouchEvent(ev);
    }
}
