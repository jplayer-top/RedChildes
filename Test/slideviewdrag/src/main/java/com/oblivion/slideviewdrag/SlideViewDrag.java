package com.oblivion.slideviewdrag;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by oblivion on 2016/10/25.
 */
public class SlideViewDrag extends FrameLayout {

    private float fraction;

    public static enum Status {
        Close, Open, Dragging
    }

    private Status status = Status.Close;

    private ViewDragHelper mViewDragHelper;
    private int mRange;
    private View mMainContent;
    private View mLeftContent;
    private int mHeight;
    private int mWidth;
    private int currentPosition;
    private OnDragStateListener mOnDragStateListener;


    /**
     * 三个构造方法都需要创建，这里需要将三个构造串联
     *
     * @param context
     */
    public SlideViewDrag(Context context) {
        this(context, null);
    }

    public SlideViewDrag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViewDrag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建ViewDragHelper类
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    /**
     * 生成 当前状态的get方法
     *
     * @return
     */
    public Status getStatus() {
       // System.out.println(status);
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * ViewDragHelper.Callback帮助类
     */
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        //决定哪个子类可以拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }


        /**
         * 在拖拽之前，用于修正《状态还未发生改变》
         * @param child  子控件
         * @param left 将要移动到的位置
         * @param dx  距离当前位置的left偏移量
         * @return 返回一个要移动到的距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //当前位置
            if (child == mMainContent) {//如果移动的控件是主界面
                left = fixRange(left);
            }
            return left;
        }

        /**
         * 一般返回大于0的数值，用于进行拖拽，默认值是0,原因是OnTouchEvent()事件处理机制
         * @param child 移动的哪个子控件
         * @return 需要返回一个大于0的数值，用于处理事件机制
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        /**
         * View 的位置状态发生改变----发生在状态改变之后
         * @param changedView 改变的哪一个View
         * @param left 当前移动的控件的左边距
         * @param top 上边距
         * @param dx X轴偏移量
         * @param dy  Y轴偏移量
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mLeftContent) {
                mLeftContent.layout(0, 0, mWidth, mHeight);//固定mLeftContent
                int newLeft = fixRange(left + mMainContent.getLeft());//传入新的修正值
                mMainContent.layout(newLeft, 0, newLeft + mWidth, mHeight);//动态设定MainContent
            }
            // System.out.println(left + "---" + top + "---" + dx + "---" + dy);
            AllAnimations();
            invalidate();//刷新界面
        }

        /**
         * 释放控件后要做的操作
         * @param releasedChild   释放的控件
         * @param xvel      X轴移动的速度
         * @param yvel     Y轴移动的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            int releaseLeft = mMainContent.getLeft();//获取释放控件后mMainContent的左边距
            if (xvel == 0 && releaseLeft > mRange / 2) {
                open();
            } else if (xvel > 0) {
                open();
            } else {
                close();
            }
            // System.out.println(xvel);
        }
    };

    /**
     * 要加载的动画
     */
    private void AllAnimations() {
        currentPosition = mMainContent.getLeft();
        //必须是小数啊，，不然给四舍五入了
        fraction = currentPosition * 1.0f / mRange;
        ViewHelper.setScaleX(mMainContent, evaluate(fraction, 1.0f, 0.8f));
        ViewHelper.setScaleY(mMainContent, evaluate(fraction, 1.0f, 0.8f));
        ViewHelper.setAlpha(mLeftContent, evaluate(fraction, 0.3f, 1.0f));
        //动画开始前对布局进行调整呢
        mLeftContent.layout(-mRange + currentPosition, 0, mWidth - mRange + currentPosition, mHeight);
        ViewHelper.setScaleX(mLeftContent, evaluate(fraction, 0.5f, 1.0f));
        ViewHelper.setScaleY(mLeftContent, evaluate(fraction, 0.5f, 1.0f));
        //设定背景有黑蛇=色到透明
        getBackground().setColorFilter(evaluateColorChange(fraction, Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
        //System.out.println(currentPosition);
        getCurrentStatus();
    }

    /**
     * 动态获取当前的状态，根据动画中的currentPosition的动态改变获取；
     */
    private void getCurrentStatus() {
        if (currentPosition <= 0+0.05) {
            if (getStatus() == Status.Dragging) {
                mOnDragStateListener.OnCloseState();
                System.out.println("clos");
                status = Status.Close;
            }

        } else if (currentPosition >= mRange*0.95) {
            if (getStatus() == Status.Dragging) {
                System.out.println("open");
                mOnDragStateListener.OnOpenState();
                status = Status.Open;
            }
        } else {
            System.out.println("drag");
            mOnDragStateListener.OnDragState(fraction);
            status = Status.Dragging;
        }
    }

    /**
     * 颜色的估值器。用来设定背景颜色的修改
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    private int evaluateColorChange(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }


    /**
     * 关闭侧边栏
     */
    private void close() {
        if (mViewDragHelper.smoothSlideViewTo(mMainContent, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            status = Status.Close;
            //System.out.println("end");
        } else {
            mMainContent.layout(0, 0, mWidth, mHeight);
            status = Status.Close;
            // System.out.println("end");
        }
        getCurrentStatus();
    }

    /**
     * 打开侧边栏
     */
    private void open() {

        if (mViewDragHelper.smoothSlideViewTo(mMainContent, mRange, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            status = Status.Open;
            //System.out.println("state");
        } else {
            mMainContent.layout(mRange, 0, mRange + mWidth, mHeight);
            status = Status.Open;
           // System.out.println(status + "--------");
            //System.out.println("state");
        }
        getCurrentStatus();
    }

    /**
     * 判断是否滑动到设定距离，记住用if
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {//while---会死循环
            ViewCompat.postInvalidateOnAnimation(this);
            //  System.out.println("ing");
            status = Status.Dragging;
        }

    }

    /**
     * 释放手指后的View所在状态
     *
     * @param releaseLeft
     */
    private void refreshViewState(int releaseLeft) {
        if (releaseLeft < mRange / 2) {
            mMainContent.layout(0, 0, mWidth, mHeight);//动态设定MainContent

        } else {
            mMainContent.layout(mRange, 0, mRange + mWidth, mHeight);//动态设定MainContent
        }
    }

    /**
     * 修正MainContent的滑动范围
     *
     * @param left
     * @return
     */
    private int fixRange(int left) {
        //移动到了最左端
        if (left < 0) {
            left = 0;
        } else if (left > mRange) {
            left = mRange;
        }
        return left;
    }

    /**
     * 转交拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 提交给mViewDraghelper去处理事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {//捕捉多点异常
            mViewDragHelper.processTouchEvent(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 布局文件导入完毕，在OnMeasure之前，获取子控件，用于健壮性判断
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();//空实现，可以干掉
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            System.out.println("子控件必须为容器");
        }
        if (getChildCount() < 2) {
            System.out.println("子控件大于两个");
        }
        mLeftContent = getChildAt(0);//由于是Framlayout 所以在调用的时候被MainCountent覆盖在其上边
        mMainContent = getChildAt(1);

    }

    /**
     * 在测量值之后
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);//空实现，可以搞掉
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRange = (int) (getMeasuredWidth() * 0.6);
    }

    /**
     * 估值计算，在接口FloatEvluator中
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * 设置是否正在滑动监听
     *
     * @param listener
     */
    public void setOnDragStateListener(OnDragStateListener listener) {
        mOnDragStateListener = listener;
    }

    /**
     * 回调接口
     */
    public interface OnDragStateListener {
        void OnDragState(float fraction);

        void OnOpenState();

        void OnCloseState();
    }
}
