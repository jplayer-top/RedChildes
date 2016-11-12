package com.oblivion.qqxmpp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oblivion.qqxmpp.R;

/**
 * Created by oblivion on 2016/11/11.
 */
public class TakeFragment extends BaseFragment {
    @Override
    public View createViewSaveData(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_take, null);
        return view;
    }
}
