package com.oblivion.redchildpuls.activity.oblivapi;

import com.android.volley.Request;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CheckoutResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;

/**
 * Created by yanjingpan on 2016/8/5.
 */
public class PayApi {

    public static Request getCheckoutData(HttpLoader.HttpListener listener,String value) {
        String url = RBConstants.URL_CHECKOUT;
        HttpParams params = new HttpParams();
        params.addHeader("userid", MyApplication.userId+"");
       // String value = "1:3:4|2:2:2";
        params.put("sku", value);
        Class<CheckoutResponse> clazz = CheckoutResponse.class;
        int requestcode = RBConstants.REQUEST_CODE_CHECKOUT;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }

}
