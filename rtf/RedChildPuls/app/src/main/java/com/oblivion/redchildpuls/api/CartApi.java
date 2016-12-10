package com.oblivion.redchildpuls.api;

import com.android.volley.Request;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CartResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;

/**
 * Created by yanjingpan on 2016/8/5.
 */
public class CartApi {

    public static Request getCheckoutData(HttpLoader.HttpListener listener,String value) {
        String url = RBConstants.URL_CART;
        HttpParams params = new HttpParams();
        params.addHeader("userid", "20428");
       // String value = "1:3:4|2:2:2";
        params.put("sku", value);
        Class<CartResponse> clazz = CartResponse.class;
        int requestcode = RBConstants.REQUEST_CODE_CART;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

}
