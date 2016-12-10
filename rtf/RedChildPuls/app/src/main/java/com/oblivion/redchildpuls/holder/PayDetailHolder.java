package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CheckoutResponse;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/7.
 */
public class PayDetailHolder extends BaseHolder<CheckoutResponse.ProductListBean> {
    @Bind(R.id.iv_prodpic_pay)
    ImageView ivProdpicPay;
    @Bind(R.id.tv_prod_name_pay)
    TextView tvProdNamePay;
    @Bind(R.id.tv_prod_conts_pay)
    TextView tvProdContsPay;
    @Bind(R.id.tv_prod_size_pay)
    TextView tvProdSizePay;
    @Bind(R.id.tv_prod_color_pay)
    TextView tvProdColorPay;
    @Bind(R.id.tv_prod_money_pay)
    TextView tvProdMoneyPay;
    @Bind(R.id.tv_item_money_pay)
    TextView tvItemMoneyPay;

    public PayDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        View view = View.inflate(MyApplication.mContext, R.layout.paydetailholder, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(CheckoutResponse.ProductListBean data) {
        // super.bindData(data);
        tvProdMoneyPay.setText(data.product.price + "");
        tvProdNamePay.setText(data.product.name);
        tvItemMoneyPay.setText(data.product.price + "");
        for (int i = 0; i < data.product.productProperty.size(); i++) {
            if (i == 3 || i == 4) {
                tvProdSizePay.setText(data.product.productProperty.get(i).v);
            } else {
                tvProdColorPay.setText(data.product.productProperty.get(i).v);
            }
        }
        String url = RBConstants.URL_SERVER + data.product.pic;
        // System.out.println("test"+RBConstants.URL_SERVER + data.product.pic);
        MyApplication.IL.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                ivProdpicPay.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MyApplication.mContext, "图片加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
