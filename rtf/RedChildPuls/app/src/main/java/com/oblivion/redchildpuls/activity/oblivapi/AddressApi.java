package com.oblivion.redchildpuls.activity.oblivapi;

import com.android.volley.Request;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.AddressListResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/8.
 */
public class AddressApi  {
    /**
     * id
     name
     phoneNumber
     province
     city
     addressArea
     addressDetail
     zipCode
     isDefault

     * @param listener
     * @param
     * @return
     */
    public static Request getCheckoutData(HttpLoader.HttpListener listener,
                                          String name,String call,
                                          String province,
                                          String  city,String addressArea,
                                          String addressDetail,String isDefault) {
        String url = RBConstants.URL_ADDRESS_AREA;
        HttpParams params = new HttpParams();
        params.addHeader("userid", MyApplication.userId+"");
        // String value = "1:3:4|2:2:2";
        params.put("name", name);
        params.put("phoneNumber", call);
        params.put("province", province);
        params.put("city", city);
        params.put("addressArea", addressArea);
        params.put("addressDetail", addressDetail);
        params.put("zipCode", 430070+"");
        params.put("isDefault", isDefault+"");
        Class<AddressListResponse> clazz = AddressListResponse.class;
        int requestcode = RBConstants.REQUEST_CODE_ADDRESS_area;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }
}
