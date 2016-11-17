package com.oblivion.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.oblivion.googleplay.R;
import com.oblivion.googleplay.utils.LogUtils;

/**
 * Created by oblivion on 2016/11/15.
 */
public abstract class LoadingPager extends FrameLayout {
    /*----------三种静态布局-------------*/
    private View pager_empty;
    private View pager_error;
    private View pager_loading;

    /* -------------- 需要显示的布局的状态以及，当前的状态  ----------*/
    private static final int STATE_LOADING = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_SUCCESS = 3;
    /**
     * 当前状态
     */
    private int currentState = 0;
    private Button errorRetry;
    private LoadingTask loadingTask;

    public LoadingPager(Context context) {
        super(context);
        initCommnetView();
    }

    /**
     * 初始化添加子View
     */
    private void initCommnetView() {
        //添加三种静态布局-->包括（加载失败，加载中，加载数据为空）
        pager_empty = View.inflate(MyApplication.getContext(), R.layout.pager_empty, null);
        this.addView(pager_empty);
        pager_empty.setVisibility(GONE);
        pager_error = View.inflate(MyApplication.getContext(), R.layout.pager_error, null);
        errorRetry = (Button) pager_error.findViewById(R.id.error_btn_retry);
        errorRetry.setOnClickListener(new MyOnClickListener());
        this.addView(pager_error);
        pager_loading = View.inflate(MyApplication.getContext(), R.layout.pager_loading, null);
        this.addView(pager_loading);
        //更新状态---》子线程更新UI
        refreshState();
    }

    private class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            currentState = STATE_LOADING;
            refreshState();
            changeState();
        }
    }

    /**
     * 根据currentState更新状态
     */
    private void refreshState() {
        if (currentState == STATE_LOADING) {
            pager_loading.setVisibility(VISIBLE);
        } else {
            pager_loading.setVisibility(GONE);
        }
        if (currentState == STATE_EMPTY) {
            pager_empty.setVisibility(VISIBLE);
        } else {
            pager_empty.setVisibility(GONE);
        }
        if (currentState == STATE_ERROR) {
            pager_error.setVisibility(VISIBLE);
        } else {
            pager_error.setVisibility(GONE);
        }
        //状态是成功状态的话，就更新
        stateSuccess();
    }

    /**
     * 获取到返回的状态成功的View
     */
    private View stateSuccess() {
        if (currentState == STATE_SUCCESS) {
            View successView = initView();
            addView(successView);
        }
        return initView();
    }

    /**
     * 外界调用---触摸加载
     */
    public void changeState() {
        if (loadingTask == null) {//当前没有在获取数据
            if (currentState != STATE_SUCCESS) {//当前状态不是成功状态
                currentState = STATE_LOADING;
                refreshState();
                //获取数据是一个耗时操作
                loadingTask = new LoadingTask();
                new Thread(loadingTask).start();
            }
        } else {
            LogUtils.s("不处理");
            return;
        }
    }

    class LoadingTask implements Runnable {
        @Override
        public void run() {
            int tempState = initData();
            currentState = tempState;
            MyApplication.getmMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    //更新状态---
                    refreshState();
                }
            });

            loadingTask = null;//线程致为空
        }

    }

    /**
     * 外界返回数据的状态得到返回值，通过这个返回值，赋值给changeState()方法中的tempState更新状态
     * 修改修饰符为public
     * 外界不知道更新的数据状态
     *
     * @return 返回一个状态用于更新数据
     */
    public abstract int initData();

    /**
     * 外界返回的View,更新success状态的View布局
     *
     * @return
     */
    public abstract View initView();
}
