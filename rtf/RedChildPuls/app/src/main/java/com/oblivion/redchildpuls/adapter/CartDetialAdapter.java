package com.oblivion.redchildpuls.adapter;

import android.view.ViewGroup;

import com.oblivion.redchildpuls.bean.CartResponse;
import com.oblivion.redchildpuls.holder.CartDetailCartHolder;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/6.
 */
public class CartDetialAdapter extends AbsBaseAdapter<CartResponse.CartBean> {

    /**
     * 接收AbsListView要显示的数据
     *
     * @param data 要显示的数据
     */
    public CartDetialAdapter(List<CartResponse.CartBean> data) {
        super(data);
    }

    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartDetailCartHolder(parent.getContext());
    }
}
