package com.oblivion.googleplay.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by oblivion on 2016/11/15.
 */
public abstract class BaseFragment extends Fragment {
    private MyLoadingPager mLoadingPager;

    /**
     * 提供获取到mLoadingPager
     *
     * @return
     */
    public MyLoadingPager getmLoadingPager() {
        return mLoadingPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPager = new MyLoadingPager(MyApplication.getContext());
       // mLoadingPager.changeState();
        return mLoadingPager;
    }

    /**
     * 抽象类不能直接new
     */
    public class MyLoadingPager extends LoadingPager {

        public MyLoadingPager(Context context) {
            super(context);
        }

        @Override
        public int initData() {
            return BaseFragment.this.initData();
        }

        @Override
        public View initView() {
            return BaseFragment.this.initView();
        }
    }

    /**
     * 同样基类不知道获取到什么样的View，需要子类必须实现
     * so --定义为抽象类
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 同样基类不知道获取到什么样的数据，需要子类必须实现
     * so --定义为抽象类
     *
     * @return
     */
    public abstract int initData();
}
