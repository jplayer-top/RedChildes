package com.oblivion.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oblivion.pull_push_listview.R;

/**
 * Created by oblivion on 2016/10/23.
 */
public class PullPushListView extends ListView {
    /**
     * 下拉状态
     */
    private static final int STATE_PULL = 0;
    /**
     * 松开刷新状态
     */
    private static final int STATE_PUSH = 1;
    /**
     * 正在刷新状态
     */
    private static final int STATE_REFRESH = 2;
    /**
     * 当前状态
     */
    private int STATE_CURRENT;
    private static final String TAG = "PullPushListView";
    /**
     * 第一次落下的点
     */
    private float down_dot;
    /**
     * 要添加的头部条目
     */
    private View headerView;
    /**
     * 获取测量headerView 高度的数据
     */
    private int meatureHeight;
    /**
     * 文本显示是否刷新
     */
    private TextView tv_loadtext;
    /**
     * 旋转动画
     */
    private RotateAnimation mRotateAnimation;
    private ImageView iv_arrow;
    private ProgressBar pb_circle;
    private float fromDegrees;
    private float toDegrees;
    /**
     * 回调的接口
     */
    private OnRefreshStateListener mOnRefreshStateListener;
    /**
     * 尾部的条目
     */
    private View footerView;
    private int footerViewHeight;
    /**
     * 是否已经正在刷新
     */
    private boolean refreshingState;
    private onLoadStateListener mOnLoadStateListener;
    private boolean loadState;


    /**
     * 重写创建时用的构造方法
     *
     * @param context
     */
    public PullPushListView(Context context) {
        super(context);
    }

    /**
     * 重写赋值参数时用的构造方法
     *
     * @param context
     * @param attrs
     */
    public PullPushListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    /**
     * 添加尾部头目，加载更多
     */
    private void initFooterView() {
        //打气筒添加布局
        footerView = View.inflate(getContext(), R.layout.item_footerview, null);
        //获取测量的宽高
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        int top = -footerViewHeight;
        //设定初始padding的位置
        initHideFooterView(top);
        //将布局添加到Listview 中
        super.addFooterView(footerView);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果滑动状态为空闲，且滑动到了最后一条条目
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount() - 1 && !loadState) {
                    setSelection(getCount() - 1);//设定选择最后一条条目
                    initHideFooterView(0);
                    loadState = true;
                    mOnLoadStateListener.onLoadState();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 动态设定FooterView的Padding
     *
     * @param top 动态设定
     */
    private void initHideFooterView(int top) {
        footerView.setPadding(0, top, 0, 0);
    }

    /**
     * 创建旋转动画
     */
    private void setRotateAnimation(float fromDegrees, float toDegrees) {
        mRotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(500);
        mRotateAnimation.setFillAfter(true);
    }

    /**
     * 初始化HeaderView
     */
    private void initHeaderView() {
        //为自定义ListView添加头部信息
        headerView = View.inflate(getContext(), R.layout.item_headview, null);
        tv_loadtext = (TextView) headerView.findViewById(R.id.tv_loadtext);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_circle = (ProgressBar) headerView.findViewById(R.id.iv_circle);
        //将progressBar 设定为Gone
        pb_circle.setVisibility(View.GONE);
        //通知进行布局测量<<<<<<卧槽尼玛的，不能测量RelativeLayout布局开头>>>>>>>
        headerView.measure(0, 0);
        //获取测量高度，在之前需要测量一下，否则无法获 取正确数据
        meatureHeight = headerView.getMeasuredHeight();
        //通知进行布局测量
        headerView.measure(0, 0);
        Log.i(TAG, "PullPushListView: " + meatureHeight);
        //添加headerView到页面
        super.addHeaderView(headerView);
        //整体隐藏headerView
        inithideheaderView(-meatureHeight);
        headerView.setClickable(false);
        STATE_CURRENT = STATE_PULL;

    }

    /**
     * headerView 根据闯入的数值动态改变HeaderView据上边界的高度
     */
    private void inithideheaderView(int top) {
        headerView.setPadding(0, top, 0, 0);
    }

    /**
     * 对ListView 添加触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //手指按下事件
            case MotionEvent.ACTION_DOWN:
                if (STATE_CURRENT == STATE_REFRESH) {//如果状态是正在刷新状态，就不执行其他操作
                    iv_arrow.setVisibility(GONE);
                    pb_circle.setVisibility(View.VISIBLE);
                    return super.onTouchEvent(event);
                }
                down_dot = event.getY();//如果不是正在刷新状态记录按下的点
                break;
            //手指移动事件
            case MotionEvent.ACTION_MOVE:
                if (STATE_CURRENT == STATE_REFRESH && getFirstVisiblePosition() == 0) {//如果状态是正在刷新状态，就不执行其他操作
                    iv_arrow.setVisibility(GONE);
                    pb_circle.setVisibility(View.VISIBLE);
                    return super.onTouchEvent(event);
                } else if ((STATE_CURRENT == STATE_PULL || STATE_CURRENT == STATE_PUSH) && getFirstVisiblePosition() == 0) {

                    //手指移动的点
                    float move_dot = event.getY();
                    //距离按下点的相对向下移动的距离
                    float movePullDistance = move_dot - down_dot;
                    //判断是否向下滑动，第一个条目是否是0，
                    if (movePullDistance > 0 && getFirstVisiblePosition() == 0 && movePullDistance < meatureHeight) {
                        inithideheaderView((int) movePullDistance - meatureHeight);
                        //向下滑动，动态改变HeardView距离上边界的距离，但是还没有到达修改箭头的边界
                        if (STATE_CURRENT == STATE_PUSH) {
                            tv_loadtext.setText("下拉刷新");
                            rotateArrow_changeText();
                            STATE_CURRENT = STATE_PULL;//修改状态，在这个范围内不再修改状态
                        }
                    } else if (movePullDistance == meatureHeight) {
                        STATE_CURRENT = STATE_CURRENT == STATE_PUSH ? STATE_PUSH : STATE_PULL;//判断状态
                    } else if (movePullDistance > meatureHeight && movePullDistance < 2 * meatureHeight) {
                        inithideheaderView((int) ((movePullDistance - meatureHeight) / 2 + 0));
                        //旋转箭头，修改文本,下拉幅度到边界值大于一个HeadView高度
                        if (STATE_CURRENT == STATE_PULL) {//如果是下拉状态，改变
                            tv_loadtext.setText("松开刷新");
                            rotateArrow_changeText();
                            STATE_CURRENT = STATE_PUSH;//修改状态，在这个范围内不再修改状态
                        }
                    }
                    return super.onTouchEvent(event);
                }
                break;
            //手指抬起事件
            case MotionEvent.ACTION_UP:
                if (STATE_CURRENT == STATE_PUSH) {//如果是松开刷新状态那就改变状态
                    inithideheaderView(0);
                    //松开手指后，图片修改为旋转progressBar,修改文本
                    pb_circle.setVisibility(View.VISIBLE);
                    //必须将动画清除掉。。不然无法隐藏
                    iv_arrow.clearAnimation();
                    iv_arrow.setVisibility(GONE);
                    tv_loadtext.setText("正在刷新");
                    STATE_CURRENT = STATE_REFRESH;//修改为刷新状态
                    if (mOnRefreshStateListener != null && !refreshingState) {//如果不是正在刷新的状态，而且回调接口非空
                        refreshingState = true;
                        mOnRefreshStateListener.OnRefresh();//执行回调的接口
                    }
                } else if (STATE_CURRENT == STATE_PULL) {
                    inithideheaderView(-meatureHeight);
                    pb_circle.setVisibility(View.GONE);
                    iv_arrow.setVisibility(View.VISIBLE);
                    //因为已经清除了动画，所以这里自动回去了，不必在设定转回原点
                    tv_loadtext.setText("下拉刷新");
                    STATE_CURRENT = STATE_PULL;
                }
                break;
        }
        return super.onTouchEvent(event);//必须有这个，这个是让ListView滑动的
    }

    /**
     * 刷新完成后的状态
     */
    public void refreshComplement() {
        inithideheaderView(-meatureHeight);
        pb_circle.setVisibility(View.GONE);
        iv_arrow.setVisibility(View.VISIBLE);
        //因为已经清除了动画，所以这里自动回去了，不必在设定转回原点
        tv_loadtext.setText("下拉刷新");
        STATE_CURRENT = STATE_PULL;
        refreshingState = false;
    }

    /**
     * 旋转箭头，修改文本
     */
    private void rotateArrow_changeText() {
        if (STATE_CURRENT == STATE_PULL) {//如果状态为下拉状态
            fromDegrees = 0f;
            toDegrees = 180f;
            //iv_arrow.clearAnimation();//先清除一下动画效果
            setRotateAnimation(fromDegrees, toDegrees);
            iv_arrow.startAnimation(mRotateAnimation);
        } else if (STATE_CURRENT == STATE_PUSH) {
            fromDegrees = 180f;
            toDegrees = 360f;
            //iv_arrow.clearAnimation();//先清除一下动画效果
            setRotateAnimation(fromDegrees, toDegrees);
            iv_arrow.startAnimation(mRotateAnimation);
        }
    }

    /**
     * 创建是否刷新的监听
     */
    public void setOnRefreshStateListener(OnRefreshStateListener listener) {
        mOnRefreshStateListener = listener;
    }

    /**
     * 创建是否加载更多的刷新
     */
    public void setOnLoadStateListener(onLoadStateListener listener) {
        mOnLoadStateListener = listener;
    }

    /**
     * 完成加载，修改状态
     */
    public void loadComplement() {
        initHideFooterView(-footerViewHeight);
        loadState = false;
    }

    /**
     * 定义正在加载的回调接口
     */
    public interface onLoadStateListener {
        void onLoadState();
    }

    /**
     * 定义正在刷新回调接口
     */
    public interface OnRefreshStateListener {
        void OnRefresh();
    }
}
