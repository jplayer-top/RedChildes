package com.oblivion.googleplay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oblivion.googleplay.base.BaseFragment;
import com.oblivion.googleplay.base.MyApplication;
import com.oblivion.googleplay.utils.LogUtils;

import java.util.Random;

/**
 * Created by oblivion on 2016/11/14.
 */
public class SortFragment extends BaseFragment {


    /**
     * 子类返回更新成功且新结果不空的布局
     *
     * @return
     */
    @Override
    protected View initView() {
        TextView tv = new TextView(MyApplication.getContext());
        tv.setText(getClass().getSimpleName());
        tv.setTextColor(Color.BLUE);        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    /**
     * 触摸加载后的效果---->加载失败，加载成功，加载数据为空）
     *
     * @return
     */
    private static final int STATE_EMPTY = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_SUCCESS = 3;

    /**
     * 子线程用来获取数据
     *
     * @return
     */
    @Override
    public int initData() {
        Random random = new Random();
        //数据获取一个状态
        int index = random.nextInt(3);
        int[] states = new int[]{STATE_EMPTY, STATE_ERROR, STATE_SUCCESS};
        SystemClock.sleep(2000);//模拟耗时操作
        return states[index];
    }
}
