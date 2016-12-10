package com.oblivion.redchildpuls.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.factory.FragmentFactory;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        initData();
    }

    private void initData() {
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = (BaseFragment) FragmentFactory.getFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        //五个界面直接返回Fragment的个数吧，不做其他处理了；
        return 5;
    }

}
