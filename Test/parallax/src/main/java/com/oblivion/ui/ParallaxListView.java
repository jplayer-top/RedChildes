package com.oblivion.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;

import com.oblivion.parallax.R;

/**
 * Created by oblivion on 2016/10/27.
 */
public class ParallaxListView extends ListView {
    /**
     * init()方法获取到的View
     */
    private ImageView view;
    /**
     * 初始高度
     */
    private int globalHeight;
    /**
     * 图片原始高度
     */
    private int intriHeight;
    /**
     * 初始触摸点
     */
    private int down_dot;
    /**
     * 第二种方法显示采用flag判断
     */
    private boolean flag;


    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context) {
        super(context);
    }

    /**
     * 获取图片初始和原始高度
     *
     * @param view
     */
    public void init(final View view) {
        this.view = (ImageView) view;
        globalHeight = view.getHeight();
        intriHeight = ((ImageView) view).getDrawable().getIntrinsicHeight();
        System.out.println(globalHeight);
    }

    /**
     * 边界滑动事件
     *
     * @param deltaX         拖拽的力道 向下为-
     * @param deltaY
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX   超出边界X范围
     * @param scrollRangeY   超出边界Y范围
     * @param maxOverScrollX 超出边界最大X
     * @param maxOverScrollY 超出边界最大Y
     * @param isTouchEvent   是否是手指触摸
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        System.out.println(deltaX + "--" + deltaY + "--" + scrollY + "--" + scrollRangeY + "--" + maxOverScrollY);
        if (isTouchEvent && deltaY < 0) {
            flag = true;
            //获取新的距离,每次都是加上上一个位置
            int newHeight = -deltaY + view.getHeight();
            //更新位置
            System.out.println("newHeight: " + newHeight);
            //设置位置
            view.getLayoutParams().height = newHeight;
            //请求更新
            view.requestLayout();
        } else {//如果不是下拉状态，就返回false
            flag = false;
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    /**
     * 手指抬起后动态的点
     */
    private float setHeight;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指刚抬起的图片高度
                setHeight = view.getHeight();
                //抬起手指后设定falg = false;
                flag = false;
                //设定值动画
                ValueAnimator va = ValueAnimator.ofFloat(setHeight, globalHeight);
                va.setDuration(2000);
                va.start();

                //动画监听，为了获取动态的数值
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //获取动画设定的分数值
                        float fraction = animation.getAnimatedFraction();
                        //获取动画设定的间值
                        setHeight = (float) animation.getAnimatedValue();
                        System.out.println(setHeight);
                        System.out.println(fraction);
                        //动态shedding高度，并请求动画更新
                        view.getLayoutParams().height = (int) setHeight;
                        view.requestLayout();
                    }
                });
                break;
            case MotionEvent.ACTION_DOWN:
                //记录按下的点
                down_dot = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否可以滑动
                if (flag) {
                    //设定滑动
                    int move2down = (int) (ev.getY() - down_dot);
                    int newHeight = view.getHeight() + move2down / 15;
                    System.out.println(newHeight + "-----");
                    if (newHeight > intriHeight) {//设定图片可以放置的最大值
                        newHeight = intriHeight;
                    }
                    //请求刷新Layout
                    // view.getLayoutParams().height = newHeight;
                    //  view.requestLayout();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
