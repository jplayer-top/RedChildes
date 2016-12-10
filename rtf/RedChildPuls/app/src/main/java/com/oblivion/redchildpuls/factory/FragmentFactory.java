package com.oblivion.redchildpuls.factory;

import android.support.v4.app.Fragment;

import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.fragment.HomeFragment;
import com.oblivion.redchildpuls.fragment.LogoFragment;
import com.oblivion.redchildpuls.fragment.CategoryFragment;
import com.oblivion.redchildpuls.fragment.PayFragment;
import com.oblivion.redchildpuls.fragment.SearchFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class FragmentFactory {
    public static Map<Integer, BaseFragment> map = new HashMap<>();
    private static final int HOME_FRAGMENT = 0;
    private static final int SEARCH_FRAGMENT = 1;
    private static final int LOGO_FRAGMENT = 2;
    private static final int PAY_FRAGMENT = 3;
    private static final int MORE_FRAGMENT = 4;

    public static Fragment getFragment(int position) {
        BaseFragment fragment;
        if (map.containsKey(position)) {
            fragment = map.get(position);
            return fragment;
        }
        switch (position) {
            case HOME_FRAGMENT:
                fragment = new HomeFragment();
                break;
            case SEARCH_FRAGMENT:
                fragment = new SearchFragment();
                break;
            case LOGO_FRAGMENT:
                fragment = new LogoFragment();
                break;
            case PAY_FRAGMENT  :
                fragment = new PayFragment();
                break;
            default:
                fragment = new CategoryFragment();
                break;
        }
        map.put(position,fragment);
        return fragment;
    }

}
