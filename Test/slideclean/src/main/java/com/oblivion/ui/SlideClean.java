package com.oblivion.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.oblivion.slideclean.R;

/**
 * Created by oblivion on 2016/10/28.
 */
public class SlideClean extends FrameLayout {
    /**
     * 拖拽工具类
     */
    private ViewDragHelper mViewDragHelper;
    /**
     * 获取主控件测量宽度
     */
    private int mainWidht;
    /**
     * 获取主控件View对象
     */
    private View mainView;
    /**
     * 获取右控件View对象
     */
    private View rightView;
    /**
     * 获取主控件高度
     */
    private int mainHeight;
    /**
     * 要显示的控件大小的View对象
     */
    private View mRangeView;
    /**
     * 测量出的显示的控件大小的测量宽度
     */
    private int meaWidth;
    private onCleanState moOnCleanState;
    /**
     * 最终状态，默认为0，0是关，1是开，2是滑动状态
     */
    private int finalState = 2;

    public boolean getOpenState() {
        return openState;
    }

    public void setOpenState(boolean openState) {
        this.openState = openState;
    }

    private boolean openState;

    public SlideClean(Context context) {
        this(context, null);
    }

    public SlideClean(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideClean(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, mcallback);
    }

    private ViewDragHelper.Callback mcallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 大于0,拦截可以
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mainWidht;
        }

        //重写之后测能滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            finalState = 2;//每次开始滑动之前设定为2；
            if (child == mainView) {

                //点击的是主控件，left是这个控件的左边距
                if (left > 0) {
                    left = 0;
                } else if (left < -meaWidth) {
                    left = -meaWidth;
                }
            } else if (child == rightView) {
                //点击的是右控件，left是这个控件的左边距
                if (left > mainWidht) {
                    left = mainWidht;
                } else if (left < mainWidht - meaWidth) {
                    left = mainWidht - meaWidth;
                }
            }
           // System.out.println(left);
            return left;
        }

        /**
         * 条目被状态被改变
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //System.out.println(dx + "---" + left);
            if (changedView == mainView) {
                //将mainView的偏移量传递给rightview
                rightView.offsetLeftAndRight(dx);

            } else if (changedView == rightView) {
                //将rightview的偏移量传递给mainView
                mainView.offsetLeftAndRight(dx);
            }
            //requestLayout();//请求重新布局
            dispatchDragEvent();
            invalidate();
        }

        /**
         * 滑动事件被释放
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
          //  System.out.println(xvel);
            int releaseLeft = mainView.getLeft();
            if (releaseLeft < -30) {
                //平滑开启
                mViewDragHelper.smoothSlideViewTo(mainView, -meaWidth, 0);
               // System.out.println(mainView.getLeft()+"-----------------shifangdian");
                finalState = 1;
                if(releaseLeft==-60 &&moOnCleanState!=null){//判断特殊情况
                    moOnCleanState.onOpen(true);
                }
            } else if (releaseLeft >= -meaWidth / 2) {
                //关闭
                mViewDragHelper.smoothSlideViewTo(mainView, 0, 0);
                finalState = 0;
                if(releaseLeft==0 &&moOnCleanState!=null){//判断特殊情况
                    moOnCleanState.onColse(false);
                }
            }
        }
    };
    /**
     * 更新状态
     */
    private void dispatchDragEvent() {
        int currentLeft = mainView.getLeft();
       // System.out.println(currentLeft+"--------"+finalState);
        if(moOnCleanState != null){//如果设定了监听
            if(finalState==2){
                moOnCleanState.onDrag();
            }else  if(finalState==1 && currentLeft==-60){
                moOnCleanState.onOpen(true);
            }else if(finalState==0&& currentLeft==0){
                moOnCleanState.onColse(false);
            }
        }

    }

    /**
     * 是否完成动画
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            //如果没有画完。继续作画
            ViewCompat.postInvalidateOnAnimation(this);
        }
        invalidate();
    }

    /**
     * 转发事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("ssint");
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 请求拦截
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_UP) {
        System.out.println("sstouup");
        }if (event.getAction()== MotionEvent.ACTION_DOWN) {
        System.out.println("sstoudown");
        }
        try {
            //抓取多点触摸的异常
            mViewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("ssdis");
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 初始化导入Inflate完成
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rightView = getChildAt(0);
        mainView = getChildAt(1);
        mRangeView = rightView.findViewById(R.id.tv);
    }

    /**
     * get measue size
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取测量的数值
        mainHeight = mainView.getMeasuredHeight();
        mainWidht = mainView.getMeasuredWidth();
        meaWidth = mRangeView.getMeasuredWidth();
    }

    /**
     * override Layout
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setLayout();
    }

    /**
     * 根据矩形设定布局
     */
    private void setLayout() {
        Rect mainRect = getMainRect();
        mainView.layout(mainRect.left, mainRect.top, mainRect.right, mainRect.bottom);
        Rect rightRect = getRightRect(mainRect);
        rightView.layout(rightRect.left, rightRect.top, rightRect.right, rightRect.bottom);
    }

    /**
     * 根据主背景矩形获取右背景矩形
     *
     * @param mainRect
     * @return
     */
    private Rect getRightRect(Rect mainRect) {
        return new Rect(mainRect.right, mainRect.top, mainRect.right + mainWidht, mainRect.bottom);
    }

    /**
     * 获取主背景矩形
     *
     * @return
     */
    private Rect getMainRect() {
        Rect mainRect = new Rect(0, 0, mainWidht, mainHeight);
        return mainRect;
    }

    /**
     * 删除状态监听
     */
    public void setOnCleanStateListener(onCleanState listener) {
        moOnCleanState = listener;
    }

    /**
     * 删除状态监听回调
     */
    public interface onCleanState {
        void onOpen(boolean flag);

        void onColse(boolean flag);

        void onDrag();
    }
}
