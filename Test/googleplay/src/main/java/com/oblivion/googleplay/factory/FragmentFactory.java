package com.oblivion.googleplay.factory;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.oblivion.googleplay.R;
import com.oblivion.googleplay.base.BaseFragment;
import com.oblivion.googleplay.fragment.AppFragment;
import com.oblivion.googleplay.fragment.GameFragment;
import com.oblivion.googleplay.fragment.HomeFragment;
import com.oblivion.googleplay.fragment.OrderFragment;
import com.oblivion.googleplay.fragment.ShareFragment;
import com.oblivion.googleplay.fragment.SortFragment;
import com.oblivion.googleplay.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oblivion on 2016/11/14.
 */
public class FragmentFactory {

    /**
     * 在外部就初始化好-----------如果写在内部，就会每次都初始化，然后空指针
     */
    public static Map<Integer, BaseFragment> map = new HashMap<>();

    public static BaseFragment create(int position) {
        BaseFragment fragment = null;
        if (map.containsKey(position)) {
            fragment = map.get(position);
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AppFragment();
                break;
            case 2:
                fragment = new GameFragment();
                break;
            case 3:
                fragment = new SubjectFragment();
                break;
            case 4:
                fragment = new ShareFragment();
                break;
            case 5:
                fragment = new SortFragment();
                break;
            case 6:
                fragment = new OrderFragment();
                break;
        }
        map.put(position, fragment);//添加到Map集合中---复用Fragment
        return fragment;
    }
}
