package com.oblivion.mvp_fragment.holder;

import android.view.View;
import android.widget.TextView;

import com.oblivion.mvp_fragment.R;
import com.oblivion.mvp_fragment.application.MyApplication;

import java.util.List;

/**
 * Created by oblivion on 2016/11/18.
 */
public abstract class BasicHolder<T> {
    public View holderView;
    public List<T> mLists;

    public BasicHolder(List<T> mLists) {
        this.mLists = mLists;
        holderView = getInflateView();
    }

    public abstract View getInflateView();

    public abstract void bindData(int position);

}
