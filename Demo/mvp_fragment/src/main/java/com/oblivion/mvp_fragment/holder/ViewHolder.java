package com.oblivion.mvp_fragment.holder;

import android.view.View;
import android.widget.TextView;

import com.oblivion.mvp_fragment.R;
import com.oblivion.mvp_fragment.application.MyApplication;

import java.util.List;

/**
 * Created by oblivion on 2016/11/18.
 */
public class ViewHolder extends BasicHolder<Integer> {

    public TextView tv;

    public ViewHolder(List<Integer> mLists) {
        super(mLists);
    }

    @Override
    public View getInflateView() {
        View view = View.inflate(MyApplication.MainContext, R.layout.item_firstfragment, null);
        tv = (TextView) view.findViewById(R.id.tv_item);
        return view;
    }

    @Override
    public void bindData(int position) {
        tv.setText(mLists.get(position) + "");
    }
}
