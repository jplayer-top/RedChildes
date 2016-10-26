package com.oblivion.slideviewdrag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by oblivion on 2016/10/26.
 */
public class IntentceptLineaLayout extends LinearLayout {

    private SlideViewDrag mslSlideViewDrag;

    public IntentceptLineaLayout(Context context) {
        this(context, null);
    }

    public IntentceptLineaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发机制，询问是否拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println(mslSlideViewDrag.getStatus() + "----");
        if (mslSlideViewDrag.getStatus() != SlideViewDrag.Status.Close) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    /**
     * 对事件进行处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mslSlideViewDrag.getStatus() != SlideViewDrag.Status.Close) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * 获取主MainActivity中获取到的SlideViewDrag,,,不是重新创建一个。。。。
     * 重新创建的话，不会有滑动监听效果
     *
     * @param ssd_drag
     */
    public void setSlideViewDrag(SlideViewDrag ssd_drag) {
        this.mslSlideViewDrag = ssd_drag;
    }
}
