package com.oblivion.mvp_fragment.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.oblivion.mvp_fragment.R;
import com.oblivion.mvp_fragment.holder.BasicHolder;
import com.oblivion.mvp_fragment.holder.ViewHolder;

import java.util.List;

/**
 * Created by oblivion on 2016/11/18.
 */
public class FirstFragmentAdapter extends BasicAdapter {
    protected List<Integer> mLists;

    public FirstFragmentAdapter(List<Integer> mLists) {
        super(mLists);
        this.mLists = mLists;
    }

    @Override
    public BasicHolder initHolder() {
        return new ViewHolder(mLists);
    }
}
