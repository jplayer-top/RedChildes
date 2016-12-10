package com.oblivion.redchildpuls.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.DetailActivity_;
import com.oblivion.redchildpuls.bean.CartResponse;

import org.senydevpkg.base.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/6.
 */
public class CartDetailCartHolder extends BaseHolder<CartResponse.CartBean> {

    @Bind(R.id.iv_prodpic)
    ImageView ivProdpic;
    @Bind(R.id.tv_prod_name)
    TextView tvProdName;
    @Bind(R.id.tv_prod_conts)
    TextView tvProdConts;
    @Bind(R.id.tv_prod_size)
    TextView tvProdSize;
    @Bind(R.id.tv_prod_color)
    TextView tvProdColor;
    @Bind(R.id.tv_prod_money)
    TextView tvProdMoney;
    @Bind(R.id.tv_item_money)
    TextView tvItemMoney;
    @Bind(R.id.iv_prod_arrow)
    ImageView ivProdArrow;
    private View view;

    public CartDetailCartHolder(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
        view = View.inflate(MyApplication.mContext, R.layout.cartdetail, null);
        ButterKnife.bind(this, view);
        return view;
    }
private CartResponse.CartBean data;
    @Override
    public void bindData(CartResponse.CartBean data) {
        super.bindData(data);
        this.data = data;
        tvProdName.setText(data.product.name);
        tvProdConts.setText(data.prodNum + "");
        tvItemMoney.setText(data.product.price*data.prodNum + "");
        tvProdMoney.setText(data.product.price + "");
        int index;
        for (int i = 0; i < data.product.productProperty.size(); i++) {
            if (data.product.productProperty.get(i).id == 4) {
                index = i;
                tvProdSize.setText(data.product.productProperty.get(index).v);
            }
        }
        String url = RBConstants.URL_SERVER + data.product.pic;
        MyApplication.IL.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
                ivProdpic.setScaleType(ImageView.ScaleType.FIT_XY);
                ivProdpic.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ivProdpic.setImageResource(R.drawable.perple);
            }
        });
    }

    @OnClick(R.id.iv_prod_arrow)
    public void onClick() {
        Toast.makeText(MyApplication.mContext, "跳转到详情", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MyApplication.mContext, DetailActivity_.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        MyApplication.mContext.startActivity(intent);
        DetailActivity_.activitystart(MyApplication.mContext,data.product.id+"");
    }
}
