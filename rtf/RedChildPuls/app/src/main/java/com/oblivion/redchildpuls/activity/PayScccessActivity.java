package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.event.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class PayScccessActivity extends AppCompatActivity implements HttpLoader.HttpListener {
    @Bind(R.id.btn_gotohome)
    Button btnGotohome;
    @Bind(R.id.btn_lookpay)
    Button btnLookpay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paysuccess);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.btn_gotohome, R.id.btn_lookpay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gotohome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_lookpay:
                startActivity(new Intent(this, OrderActivity.class));
/**
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
                HttpParams httpParams = new HttpParams();
                httpParams.addHeader("userid", MyApplication.userId + "");
                httpParams.put("addressId",5+"");
                httpParams.put("sku",1+":"+1+":"+3+"");
                MyApplication.HL.post(RBConstants.URL_DETAIL_ORDERSUMBIT, httpParams, null, RBConstants.REQUEST_CODE_DETAIL_ORDERSUMBIT, this);
                finish();
                break;
        }
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    private EventMessage message;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(EventMessage eventMessage) {
        this.message = eventMessage;
    }
}
