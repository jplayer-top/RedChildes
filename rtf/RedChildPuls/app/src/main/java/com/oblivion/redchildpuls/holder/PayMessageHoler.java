package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.SelectLocalActivity_;
import com.oblivion.redchildpuls.bean.CheckoutResponse;
import com.oblivion.redchildpuls.event.EventSelectLocal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/7.
 */
public class PayMessageHoler extends BaseHolder<CheckoutResponse> {
    @Bind(R.id.pay_name)
    TextView payName;
    @Bind(R.id.pay_call)
    TextView payCall;
    @Bind(R.id.tv_pay_local)
    TextView tvPayLocal;
    @Bind(R.id.iv_local)
    ImageView ivLocal;
    @Bind(R.id.tv_pay_pay)
    TextView tvPayPay;
    @Bind(R.id.iv_pay_pay)
    ImageView ivPayPay;
    @Bind(R.id.tv_pay_style)
    TextView tvPayStyle;
    @Bind(R.id.iv_pay_style)
    ImageView ivPayStyle;
    @Bind(R.id.tv_pay_fast)
    TextView tvPayFast;
    @Bind(R.id.tv_pay_paick)
    TextView tvPayPaick;

    public PayMessageHoler(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.pay_detial, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void bindData(CheckoutResponse data) {
        super.bindData(data);
        tvPayStyle.setText(data.deliveryList.get(0).des);
        tvPayPay.setText(data.paymentList.get(0).des);
        tvPayLocal.setText("北京市朝阳区银座大厦220号楼");
    }

    @OnClick({R.id.iv_local, R.id.iv_pay_pay, R.id.iv_pay_style})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_local:
                Toast.makeText(MyApplication.mContext, "修改地址", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SelectLocalActivity_.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.iv_pay_pay:
                Toast.makeText(MyApplication.mContext, "支付方式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_pay_style:
                Toast.makeText(MyApplication.mContext, "送货方式", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocalData(EventSelectLocal message) {
        payName.setText(message.name);
        payCall.setText(message.call);
        tvPayLocal.setText(message.address + message.detail);
    }

}
