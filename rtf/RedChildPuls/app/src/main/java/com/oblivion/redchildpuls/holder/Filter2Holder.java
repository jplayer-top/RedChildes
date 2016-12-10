package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.CategoryFilterBean;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 周乐 on 2016/12/8.
 */
public class Filter2Holder extends BaseHolder <CategoryFilterBean.ListFilterBean.ValueListBean>{
    @Bind(R.id.tv_filter2_value)
    TextView tvFilter2Value;

    public Filter2Holder(Context mContext) {
        super(mContext);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.item_filter2, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(CategoryFilterBean.ListFilterBean.ValueListBean data) {
        tvFilter2Value.setText(data.name);
    }
}
