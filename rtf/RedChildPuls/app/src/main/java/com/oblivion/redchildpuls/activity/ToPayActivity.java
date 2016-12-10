package com.oblivion.redchildpuls.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.oblivapi.PayApi;
import com.oblivion.redchildpuls.adapter.ToPayAdapter;
import com.oblivion.redchildpuls.bean.CheckoutResponse;
import com.oblivion.redchildpuls.holder.PayAllMessageHolder;
import com.oblivion.redchildpuls.holder.PayMessageHoler;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/7.
 */
public class ToPayActivity extends AppCompatActivity implements HttpLoader.HttpListener {

    @Bind(R.id.iv_back_cart)
    ImageView ivBackCart;
    @Bind(R.id.fl_pay_detail)
    FrameLayout flPaydetail;
    private ListView lvPaydetail;
    private PayMessageHoler payMessageHoler;
    private PayAllMessageHolder payAllMessageHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartedit);
        ButterKnife.bind(this);
        payAllMessageHolder = new PayAllMessageHolder(MyApplication.mContext);
        payMessageHoler = new PayMessageHoler(MyApplication.mContext);
        lvPaydetail = new ListView(MyApplication.mContext);
        flPaydetail.addView(lvPaydetail);
        lvPaydetail.addHeaderView(payMessageHoler.rootView);
        lvPaydetail.addFooterView(payAllMessageHolder.rootView);
        //请求网络
        PayApi.getCheckoutData(this, MyApplication.stringBuffer.toString());
    }


    /**
     * 请求网路返回数据
     *
     * @param requestCode response对应的requestCode
     * @param response    返回的response
     */
    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        CheckoutResponse checkoutResponse = (CheckoutResponse) response;
        String response1 = checkoutResponse.response;
        if (response1.equals("error")) {
            Toast.makeText(ToPayActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            System.out.println(response1);
            lvPaydetail.setAdapter(new ToPayAdapter(checkoutResponse.productList));
            payMessageHoler.bindData(checkoutResponse);
            payAllMessageHolder.bindData(checkoutResponse);
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(ToPayActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_back_cart)
    public void onClick() {
        finish();
    }

}
