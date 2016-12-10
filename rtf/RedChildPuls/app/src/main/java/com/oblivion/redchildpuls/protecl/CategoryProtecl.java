package com.oblivion.redchildpuls.protecl;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CatetoryResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by qinyin on 2016/12/6.
 */
public class   CategoryProtecl implements HttpLoader.HttpListener {
    IResponse x;
    private List<CatetoryResponse.CategoryBean> categoryDataBeanList;

    public List<CatetoryResponse.CategoryBean>  getData() {
        if(x != null){
            System.out.println("缓存复用，防止空指针");
            categoryDataBeanList =((CatetoryResponse) x).category;
            return categoryDataBeanList;
        }
        //请求数据
        String url = RBConstants.URL_CATEGORY_LIST;
        HttpParams params = new HttpParams();
        MyApplication.HL.get(url, params, CatetoryResponse.class, RBConstants.REQUEST_CODE_CATEGORY_LIST, this);
        return categoryDataBeanList;
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == RBConstants.REQUEST_CODE_CATEGORY_LIST && response instanceof CatetoryResponse) {
            categoryDataBeanList =((CatetoryResponse) response).category;
            x = response;
            System.out.println("----请求成功-----");

        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        System.out.println("错了   ");
    }
}
