package com.oblivion.tablayout.fragmentfactory;

import android.support.v4.app.Fragment;

import com.oblivion.tablayout.fragment.AppFragment;
import com.oblivion.tablayout.fragment.HomeFragment;
import com.oblivion.tablayout.fragment.PlayFragment;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/11/22.
 */
public class FragmentFactory {
    public static Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AppFragment();
                break;
            case 2:
                fragment = new PlayFragment();
                break;
        }
        return fragment;
    }
}
