package com.oblivion.redchildpuls.pager;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CatetoryResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类页的基类页
 * Created by qinyin on 2016/12/4.
 */
public class BaseCategoryPager implements HttpLoader.HttpListener {
    /**
     * 传递的activity
     */
    public Activity mActivity;

    public Activity getmActivity() {
        return mActivity;
    }

    /**
     * 当前页面的布局对象
     */

    public View mRootView;

    public ListView category_list_view;
    public List<CatetoryResponse.CategoryBean> categoryDataBeanList;

    public BaseCategoryPager(Activity mActivity) {
        this.mActivity = mActivity;

        mRootView = initView();
    }

    private View initView() {
        View view = View.inflate(mActivity, R.layout.category_list_view, null);
        category_list_view = (ListView) view.findViewById(R.id.category_list_view);
        getData();

        return view;
    }


    public void initData() {

    }

    public void getData() {
        //请求数据
        String url = RBConstants.URL_CATEGORY_LIST;
        HttpParams params = new HttpParams();
        MyApplication.HL.get(url, params, CatetoryResponse.class, 7, this);

    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == RBConstants.REQUEST_CODE_CATEGORY_LIST && response instanceof CatetoryResponse) {
            categoryDataBeanList = ((CatetoryResponse) response).category;
            System.out.println(categoryDataBeanList.get(0).name);
            System.out.println("----请求成功-----");
            initData();
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        System.out.println("错了   ");
    }

}
