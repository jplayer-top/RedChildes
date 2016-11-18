package com.oblivion.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.oblivion.googleplay.adapter.BasicAdapter;
import com.oblivion.googleplay.adapter.HomeAdapter;
import com.oblivion.googleplay.base.BaseFragment;
import com.oblivion.googleplay.base.LoadingPager;
import com.oblivion.googleplay.bean.HomeBean;
import com.oblivion.googleplay.config.Constants;
import com.oblivion.googleplay.presenter.HomePresenter;
import com.oblivion.googleplay.utils.HttpUtils;
import com.oblivion.googleplay.utils.LogUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oblivion on 2016/11/14.
 */
public class HomeFragment extends BaseFragment {


    private ListView mListView;
    private List<String> mLists;
    private List<HomeBean.ListBean> homeList;

    /**
     * 子类返回更新成功且新结果不空的布局
     *
     * @return
     */
    @Override
    protected View initView() {
//        view = View.inflate(UIUtils.getContext(), R.layout.listview_virtual, null);
        mListView = new ListView(getContext());
        mListView.setAdapter(new HomeAdapter(homeList));//数据需要写在这儿
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
            mLists.add("撩了" + i + "个小妹妹");
        }
        HomePresenter presenter = new HomePresenter();
        presenter.doUILogic();
        HomeBean homeBean = presenter.homeBean;//需要做非空判断，不然会挂掉。。不会null空指针报错
        if(null==homeBean){
            return STATE_EMPTY;
        }
        homeList=homeBean.list;
        if (null==homeList){
            return STATE_EMPTY;
        }
        SystemClock.sleep(2000);//模拟耗时操作
        int state = presenter.getState();
        System.out.println(state + "state");
        return state;
    }
}
