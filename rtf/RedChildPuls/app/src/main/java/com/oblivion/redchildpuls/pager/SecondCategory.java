/*
package com.oblivion.redchildpuls.pager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.CatetoryResponse;
import com.oblivion.redchildpuls.factory.FragmentFactory;
import com.oblivion.redchildpuls.fragment.CategoryFragment;

import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * 二级分类页
 * Created by qinyin on 2016/12/4.
 *//*

public class SecondCategory extends BaseCategoryPager {
    private static final String TAG = "SecondCategory";
    */
/**
     * 获取的数据
     *//*

    private List<CatetoryResponse.CategoryBean> mDatas;
    */
/**
     * ParentId
     *//*

    private int currItem;

    */
/**
     * 当前页数据
     *//*

    private List<CatetoryResponse.CategoryBean> mCurrData;
    public SecondCategory(Activity mActivity) {
        super(mActivity);
        getData();
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
    @Override
    public void initData() {
        mCurrData=new ArrayList<>();
        mDatas=categoryDataBeanList;
        System.out.println("seconddata--------------------------"+mDatas.size());
       CategoryFragment categoryFragment = (CategoryFragment) FragmentFactory.getFragment(4);
        ArrayList<BaseCategoryPager> mpages = categoryFragment.getmPagers();
        FirstCategory firstCategory= (FirstCategory) mpages.get(0);


       currItem = firstCategory.getCurrItem();
        Log.i(TAG, "initData: 0000000000000000000000"+mDatas.size());
        for (int i=0;i<mDatas.size();i++){
            if (mDatas.get(i).parentId==currItem){
                mCurrData.add(mDatas.get(i));

            }
        }
        category_list_view.setAdapter(new FirstCategoryAdapter());
    }
*/
/**
 * 二级分类适配器
 *//*

    class FirstCategoryAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return mCurrData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view=View.inflate(parent.getContext(), R.layout.item_list_category2,null);
        TextView category_title = (TextView) view.findViewById(R.id.category_title);
       category_title.setText(mCurrData.get(position).name+"");
        return view;
    }
}
}
*/
