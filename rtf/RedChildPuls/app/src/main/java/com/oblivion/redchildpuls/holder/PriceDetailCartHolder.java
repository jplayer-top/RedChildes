package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.CartResponse;
import com.oblivion.redchildpuls.utils.FontDisplayUtil;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/6.
 */
public class PriceDetailCartHolder extends BaseHolder<CartResponse> {
    @Bind(R.id.lv_detail_pay)
    LinearLayout lvDetailPay;

    public PriceDetailCartHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(getContext(), R.layout.pricedetia, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(CartResponse data) {

        for (int i = 0; i < data.prom.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(data.prom.get(i).toString());
            textView.setTextColor(Color.RED);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = FontDisplayUtil.dip2px(MyApplication.mContext, 10f);
            params.topMargin = FontDisplayUtil.dip2px(MyApplication.mContext, 3f);
            System.out.println(data.prom.get(i).toString());
            lvDetailPay.addView(textView, params);
        }
    }
}
