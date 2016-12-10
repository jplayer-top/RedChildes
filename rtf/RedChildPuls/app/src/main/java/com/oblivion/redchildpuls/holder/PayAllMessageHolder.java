package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.PayScccessActivity;
import com.oblivion.redchildpuls.bean.CheckoutResponse;
import com.oblivion.redchildpuls.event.EventMessage;
import com.oblivion.redchildpuls.utils.FontDisplayUtil;

import org.greenrobot.eventbus.EventBus;
import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class PayAllMessageHolder extends BaseHolder<CheckoutResponse> {
    @Bind(R.id.tvpayallconts)
    TextView tvpayallconts;
    @Bind(R.id.tvrawmoney)
    TextView tvrawmoney;
    @Bind(R.id.tvpayallpast)
    TextView tvpayallpast;
    @Bind(R.id.tv_deletemoney)
    TextView tvDeletemoney;
    @Bind(R.id.tv_allmoney)
    TextView tvAllmoney;
    @Bind(R.id.ll_payall)
    LinearLayout llPayall;
    @Bind(R.id.btn_payall)
    Button btnPayall;

    public PayAllMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.payallmessage, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private CheckoutResponse data;

    @Override
    public void bindData(CheckoutResponse data) {
        super.bindData(data);
        this.data = data;
        tvpayallpast.setText(data.checkoutAddup.freight + "");
        tvrawmoney.setText(data.checkoutAddup.totalPrice + "");
        tvDeletemoney.setText(data.checkoutAddup.totalPoint + "");
        tvpayallconts.setText(data.checkoutAddup.totalCount + "");
        tvAllmoney.setText(data.checkoutAddup.totalPrice - data.checkoutAddup.totalPoint + "");
        for (int i = 0; i < data.checkoutProm.size(); i++) {
            TextView textView = new TextView(MyApplication.mContext);
            textView.setText(data.checkoutProm.get(i));
            textView.setTextColor(Color.RED);
            llPayall.setPadding(FontDisplayUtil.dip2px(MyApplication.mContext, 10f), 0, 0, 0);
            llPayall.addView(textView);
        }
    }

    @OnClick(R.id.btn_payall)
    public void onClick() {
        Toast.makeText(MyApplication.mContext, "提交订单", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), PayScccessActivity.class);
        /*
         * POST	/ordersumbit
         参数名称	描述
         sku	商品ID:数量:属性ID|商品ID:数量:属性ID
         addressId	地址簿ID
         paymentType	支付方式
         deliveryType	送货时间
         invoiceType	发票类型
         invoiceTitle	发票抬头
         invoiceContent	发票内容

         */
        String addressArea = data.addressInfo.addressArea;
        String addressDetail = data.addressInfo.addressDetail;
        int id = data.productList.get(0).product.id;
        String name = data.productList.get(0).product.name;
        int price = data.productList.get(0).product.price;
        int stype = data.productList.get(0).product.productProperty.get(0).id;
        EventBus.getDefault().post(new EventMessage(addressArea, addressDetail, id, name, price, stype));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
