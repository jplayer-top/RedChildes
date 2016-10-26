package com.oblivion.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by oblivion on 2016/10/24.
 */
public class SlideMenu extends ViewGroup {
    /**
     * 得到Menu界面
     */
    private View menuView;
    /**
     * 得到Main界面
     */
    private View mainView;
    /**
     * 获取menuView 的布局宽
     */
    private int menuViewWidth;
    /**
     * 触目事件落下点
     */
    private int down_dot;
    /**
     * 手指抬起点
     */
    private int up_dot;
    /**
     * 移动点与按下点的距离
     */
    private int move2down_distance;
    /**
     * 当前menuView 滑动到的位置
     */
    private int currrentScroll;
    private Scroller msScroller;
    private onDragStateListener mOnDragStateListener;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        msScroller = new Scroller(getContext());
    }

    /**
     * 测量布局中的控件
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//测量自身宽高
        //得到menu界面
        menuView = getChildAt(0);
        menuViewWidth = menuView.getLayoutParams().width;
        //得到main界面
        mainView = getChildAt(1);
        //设定menuView的宽------不用setMeasure...
        menuView.measure(MeasureSpec.makeMeasureSpec(menuViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
        //设定mainView的宽
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int menuleft = -menuViewWidth;//menu设定的left
        int menuright = 0;//menu设定的right
        int menutop = 0;//menu设定的top
        int menubottom = b - t;//menu设定的bottom
        //布局menuView 控件xx2
        menuView.layout(menuleft, menutop, menuright, menubottom);
        int mainleft = 0;//main设定的left
        int mainright = r - l;//main设定的right
        int maintop = 0;//main设定的top
        int mainbottom = b - t;//main设定的bottom
        //布局mainView ---------------控件.....你妈逼方向---------方向是left,top,right,bottom
        mainView.layout(mainleft, maintop, mainright, mainbottom);
        System.out.println(menuViewWidth--);//原来这这里减减了；

    }

    /**
     * 由于系统scrollTo是取反操作，所以需要封装一下
     *
     * @param distance
     */
    private void scrollTo(int distance) {
        super.scrollTo(-distance, 0);
    }

    public int getMyScrollX() {
        return -getScrollX();
    }

    /**
     * 控件触莫状态
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下点
                down_dot = (int) event.getX();
                System.out.println(down_dot);
                break;
            case MotionEvent.ACTION_MOVE:
                int move_dot = (int) event.getX();
                //动态的移动点和按下点的间距绝对值
                move2down_distance = move_dot - down_dot;
                //移动间距与上次抬起的点
                int lastUp_dot = move2down_distance + up_dot;
                // 控制边界
                if (lastUp_dot >= menuViewWidth) {
                    lastUp_dot = menuViewWidth;
                } else if (lastUp_dot <= 0) {
                    lastUp_dot = 0;
                }
                //设定移动后的距离
                scrollTo(lastUp_dot);
                break;
            case MotionEvent.ACTION_UP:
                //当前抬起手指点
                up_dot = (int) event.getX();
                //设定最终的状态
                setFinalScroll();
                break;
        }
        return true;//将事件消费掉
    }

    /**
     * 当手指抬起后，记录最终的状态；
     */
    private void setFinalScroll() {
        //得到当前距离左边界的距离
        currrentScroll = getMyScrollX();
        if (currrentScroll >= menuViewWidth / 2) {
            //scrollTo(menuViewWidth);
            rightAnimation();
        } else {
            leftAnimation();

        }
    }

    /**
     * 平滑向左的移动动画
     */
    private void leftAnimation() {
        //scrollTo(0);
        int dx = 0 - currrentScroll;//要移动的距离
        //设定平滑动画
        msScroller.startScroll(currrentScroll, 0, dx, 0, 300);
        invalidate();
        //设定一下抬起点
        up_dot = 0;
        //调用接口的关闭状态
        mOnDragStateListener.onDragClose();
    }
    /**
     * 平滑向右移动的动画
     */
    private void rightAnimation() {
        int dx = menuViewWidth - currrentScroll;//要移动的距离
        //设定平滑动画
        msScroller.startScroll(currrentScroll, 0, dx, 0, 300);
        invalidate();
        //设定一下抬起点
        up_dot = menuViewWidth;
        //调用接口的开启状态
        mOnDragStateListener.onDragOpen();
    }

    /**
     * 平滑移动动画是否结束
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (msScroller.computeScrollOffset()) {
            int currX = msScroller.getCurrX();//模拟出来的数值
            scrollTo(currX);
            invalidate();
        }
    }

    /**
     * 拖拽的监听
     */
    public void setOnDragStateListener(onDragStateListener listener) {
        mOnDragStateListener = listener;
    }

    /**
     * 回调修改状态
     *
     * @param dragState
     */
    public void setStateChange(boolean dragState) {
        if (dragState) {
            currrentScroll = 0;
            rightAnimation();
        } else {
            currrentScroll = menuViewWidth;
            leftAnimation();
        }
    }


    /**
     * 创建拖拽的回调接口
     */
    public interface onDragStateListener {
        /**
         * 被拖拽开
         */
        void onDragOpen();

        /**
         * 被关闭
         */
        void onDragClose();
    }
}
