package com.oblivion.redchildpuls.api;

import com.android.volley.Request;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.AddressResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */

public class LocalApi {
    public static Request getCheckoutData(HttpLoader.HttpListener listener, String value) {
        String url = RBConstants.URL_ADDRESS_LIST;   //dfdfs
        HttpParams params = new HttpParams();
        params.addHeader("userid", "20428");
        // String value = "1:3:4|2:2:2";

        //d
        Class<AddressResponse> clazz = AddressResponse.class;
        int requestcode = RBConstants.REQUEST_CODE_ADDRESS_LIST;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

}


