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
import android.widget.ListView;
import android.widget.TextView;

import com.oblivion.googleplay.adapter.AppAdapter;
import com.oblivion.googleplay.adapter.HomeAdapter;
import com.oblivion.googleplay.base.BaseFragment;
import com.oblivion.googleplay.base.MyApplication;
import com.oblivion.googleplay.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by oblivion on 2016/11/14.
 */
public class AppFragment extends BaseFragment {


    private ListView mListView;
    private List<String> mLists;

    /**
     * 子类返回更新成功且新结果不空的布局
     *
     * @return
     */
    @Override
    protected View initView() {
//        view = View.inflate(UIUtils.getContext(), R.layout.listview_virtual, null);
        mListView = new ListView(getContext());
        mListView.setAdapter(new AppAdapter(mLists));//数据需要写在这儿
        return mListView;
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
        mLists = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            mLists.add("撩了" + (99 - i) + "个小妹妹");
        }
        SystemClock.sleep(2000);//模拟耗时操作
        System.out.println(STATE_SUCCESS);
        return STATE_SUCCESS;
    }
}

