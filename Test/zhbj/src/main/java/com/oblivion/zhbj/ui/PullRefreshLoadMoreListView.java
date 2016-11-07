package com.oblivion.zhbj.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oblivion.zhbj.R;
import com.oblivion.zhbj.utils.ToastUtils;

/**
 * Created by oblivion on 2016/11/3.
 */
public class PullRefreshLoadMoreListView extends ListView {

    private ImageView arrow;
    private TextView refresh;
    private TextView date;
    private View headView;
    private int measureHeight;
    //三种状态
    private static final int STATE_PULL = 0;
    private static final int STATE_PUSH = 1;
    private static final int STATE_REFRESH = 2;
    //当前状态
    private int currentState = STATE_PULL;//默认状态是初始下拉状态
    private RotateAnimation mPushRotateAnimation;
    private RotateAnimation mPullRotateAnimation;
    private ProgressBar progressBar;
    private View footerView;
    private int footerViewMeasureHeight;
    private boolean isLoading = false;

    public PullRefreshLoadMoreListView(Context context) {
        this(context, null);
    }

    public PullRefreshLoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PullRefreshLoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
        initFooterView();
        initAnimation();
    }

    /**
     * 初始化尾部信息
     */
    public void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.pull_refresh_footer, null);
        footerView.measure(0, 0);
        footerViewMeasureHeight = footerView.getMeasuredHeight();//获取到底部信息栏的高度
        footerView.setPadding(0, -footerViewMeasureHeight, 0, 0);
        System.out.println(footerViewMeasureHeight);
        addFooterView(footerView);
        //设置滑动监听，监听尾部信息
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //滑动状态变为空闲状态，并且当前所见的条目为最有一条,并且不是正在加载状态
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getCount() - 1 == getLastVisiblePosition() && !isLoading) {
                    footerView.setPadding(0, 0, 0, 0);
                    setSelection(getCount() - 1);//设定选择状态为最后一条，即使选不上，也这样设定
                    isLoading = true;//设定状态为正在加载
                    onRefreshStateListener.loadMore();//设定监听
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    /**
     * 初始化头部信息
     */
    public void initHeadView() {
        headView = View.inflate(getContext(), R.layout.pull_refresh_header, null);
        arrow = (ImageView) headView.findViewById(R.id.iv_pull_image);
        refresh = (TextView) headView.findViewById(R.id.tv_pull_refresh);
        date = (TextView) headView.findViewById(R.id.tv_pull_date);
        progressBar = (ProgressBar) headView.findViewById(R.id.pb_progress);
        //将布局添加到ListView中
        addHeaderView(headView);
        headView.measure(0, 0);//都写0,是告诉程序只测量不参与计算
        //获取到测量高度
        measureHeight = headView.getMeasuredHeight();
        System.out.println(measureHeight);
        headView.setPadding(0, -measureHeight, 0, 0);//讲Padding设定为-数,目的隐藏该条目
    }

    private int startY = -1;//由于在ViewPager中已经对事件的点击移动做过处理，所以这里需要对这种情况做出判断，

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentState == STATE_REFRESH) {
                    break;//如果状态已经是状态刷新状态，不做处理
                }
                if (getFirstVisiblePosition() == 0) {//判断是否当前条目是否为第一个，不然就保留点击点
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == STATE_REFRESH) {
                    break;//如果状态已经是状态刷新状态，不做处理
                }
                if (startY == -1 && getFirstVisiblePosition() == 0) {//事件消耗掉，但是条目不是从0开始记录
                    startY = (int) ev.getY();//重新获取一次按下的点
                }
                int moveY = (int) ev.getY();
                int dis = moveY - startY;
                if (dis > 0 && getFirstVisiblePosition() == 0) {//说明是向下滑动,并且第一个条目时ListView中最上边
                    headView.setPadding(0, dis - measureHeight, 0, 0);//根据移动的距离动态设定headView显示的高度
                }
                //System.out.println(headView.getHeight()+"height");//得到动态的高度
                if (headView.getHeight() > measureHeight) {//如果超过了初始高度
                    if (currentState != STATE_PUSH) {//目的只让他进入一次就好
                        currentState = STATE_PUSH;
                        System.out.println("松开刷新");
                        updateState();//更新上边的图标变换
                    }
                } else {
                    if (currentState != STATE_PULL) {//目的只让他进入一次就好
                        currentState = STATE_PULL;
                        System.out.println("下拉加载");
                        updateState();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//回滚操作，将按下值重新设定为-1；
                if (currentState == STATE_PULL) {//松开之前的状态是下拉状态，那就回到隐藏点
                    headView.setPadding(0, -measureHeight, 0, 0);
                    progressBar.setVisibility(INVISIBLE);//隐藏进度圆圈
                    arrow.setVisibility(VISIBLE);//显示箭头图片
                } else if (currentState == STATE_PUSH) {//如果松开状态是松开刷新状态，那就设定显示头信息
                    headView.setPadding(0, 0, 0, 0);
                    currentState = STATE_REFRESH;//更新状态为刷新状态
                    updateState();
                    if (onRefreshStateListener != null) {
                        onRefreshStateListener.refreshRunning();//回调接口在这里作用，记得判断监听是否为空
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据动态的下拉高度，更新图标状态
     */
    private void updateState() {
        if (currentState == STATE_PUSH) {//松开刷新状态
            arrow.startAnimation(mPullRotateAnimation);
            refresh.setText("松开刷新");
        } else if (currentState == STATE_PULL) {
            arrow.startAnimation(mPushRotateAnimation);
            refresh.setText("下拉加载");
        } else if (currentState == STATE_REFRESH) {//状态为刷新状态
            arrow.clearAnimation();//先清除所有动画，不然，箭头图片还会显示
            arrow.setVisibility(INVISIBLE);
            refresh.setText("正在刷新");
            progressBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        //下转上动画
        mPullRotateAnimation = new RotateAnimation
                (0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mPullRotateAnimation.setDuration(500);
        mPullRotateAnimation.setFillAfter(true);
        //上转下动画
        mPushRotateAnimation = new RotateAnimation
                (-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mPushRotateAnimation.setDuration(500);
        mPushRotateAnimation.setFillAfter(true);
    }

    private OnRefreshStateListener onRefreshStateListener;

    /**
     * 完成刷新，但是有成功失败这一说
     *
     * @param isRefresh 是否更新成功
     */
    public void complementRefresh(boolean isRefresh) {
        if (isRefresh) {//如果是下拉刷新
            headView.setPadding(0, -measureHeight, 0, 0);//返回到初始状态
            currentState = STATE_PULL;//更新状态为下拉刷新
            arrow.setVisibility(VISIBLE);
            progressBar.setVisibility(INVISIBLE);
        } else {//若果是加载更多
            footerView.setPadding(0, -footerViewMeasureHeight, 0, 0);//隐藏
            isLoading = false;//回滚初始状态
        }

    }

    /**
     * 回调接口，正在刷新
     */
    public interface OnRefreshStateListener {
        void refreshRunning();

        void loadMore();
    }

    /**
     * 设置下拉刷新，加载更多的监听
     */
    public void setOnRefreshStateListener(OnRefreshStateListener listener) {
        onRefreshStateListener = listener;
    }
}
