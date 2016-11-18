package com.oblivion.mvp_fragment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.oblivion.mvp_fragment.holder.BasicHolder;
import com.oblivion.mvp_fragment.holder.ViewHolder;

import java.util.List;

/**
 * Created by oblivion on 2016/11/18.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    private List<T> mList;

    public BasicAdapter(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BasicHolder holder;
        if (convertView == null) {
            holder = initHolder();
            convertView = holder.holderView;
            convertView.setTag(holder);
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        holder.bindData(position);
        return convertView;
    }

    public abstract BasicHolder initHolder();
}
