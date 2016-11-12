package com.oblivion.qqxmpp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by oblivion on 2016/11/11.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //5.0一下的Fragment页面更新后数据不会保存的bug修复了
        View view = null;
        if (view == null) {
            view = createViewSaveData(inflater, container, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    /**
     * 将View封装，，但是要不要更改成protect----
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public  abstract View createViewSaveData(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
