package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.CartResponse;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/6.
 */
public class NumberDetailHolder extends BaseHolder<CartResponse> {
    @Bind(R.id.tv_number_counts)
    TextView tvNumberCounts;
    @Bind(R.id.tv_number_point)
    TextView tvNumberPoint;
    @Bind(R.id.tv_number_money)
    TextView tvNumberMoney;

    public NumberDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.number_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(CartResponse data) {
        super.bindData(data);
        tvNumberCounts.setText(data.totalCount+"");
        tvNumberPoint.setText(data.totalPoint+"");
        tvNumberMoney.setText(data.totalPrice+"");
    }
}
