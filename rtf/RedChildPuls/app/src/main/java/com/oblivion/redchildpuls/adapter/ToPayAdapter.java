package com.oblivion.redchildpuls.adapter;

import android.view.ViewGroup;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.bean.CheckoutResponse;
import com.oblivion.redchildpuls.holder.PayDetailHolder;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/7.
 */
public class ToPayAdapter extends AbsBaseAdapter<CheckoutResponse.ProductListBean> {


    /**
     * 接收AbsListView要显示的数据
     *
     * @param data 要显示的数据
     */
    public ToPayAdapter(List<CheckoutResponse.ProductListBean> data) {
        super(data);
    }

    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PayDetailHolder(MyApplication.mContext);
    }
}
