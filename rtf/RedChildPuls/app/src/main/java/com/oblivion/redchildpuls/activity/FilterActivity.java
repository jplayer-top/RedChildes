package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.MyAdapter;
import com.oblivion.redchildpuls.bean.CategoryFilterBean;
import com.oblivion.redchildpuls.holder.Filter2Holder;
import com.oblivion.redchildpuls.holder.FilterHolder;
import com.oblivion.redchildpuls.utils.PrefUtils;
import com.oblivion.redchildpuls.view.MyTitleBar;
import com.oblivion.redchildpuls.zxq.view.SearchResultActivity;

import org.senydevpkg.base.BaseHolder;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "FilterActivity";
    private ListView lvfilterall;
    /**
     * 一级筛选的集合
     */
    private List<CategoryFilterBean.ListFilterBean> filterBeanList;
    /**
     * 二级筛选的集合
     */
    private List<CategoryFilterBean.ListFilterBean.ValueListBean> valueListBeens;

    private FilterAdapter<Object> filterAdapter;
    private Filter2Adapter<Object> filter2Adapter;

    String s = "";
    private MyTitleBar myTitleBar;


    @Override
    public void onBackPressed() {
        if (lvfilterall.getAdapter() instanceof FilterAdapter){
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra(SearchResultActivity.FILTER_URL,"");
            //设置返回数据
            setResult(1, intent);
            //关闭Activity
            finish();
        }else {
            lvfilterall.setAdapter(filterAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        lvfilterall=(ListView) findViewById(R.id.lv_filter_all);
        myTitleBar = (MyTitleBar) findViewById(R.id.mtb_filter);

        //回退按钮点击
        myTitleBar.addOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvfilterall.getAdapter() instanceof FilterAdapter){
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra(SearchResultActivity.FILTER_URL,"");
                    //设置返回数据
                    setResult(1, intent);
                    //关闭Activity
                    finish();
                }else {
                    lvfilterall.setAdapter(filterAdapter);
                }

            }
        });

        //筛选按钮点击事件
        myTitleBar.addOnFilterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param = "";
                for (CategoryFilterBean.ListFilterBean bean:filterBeanList){
                    int position = PrefUtils.getInt(MyApplication.mContext, bean.key + "position", -1);
                    if (position==-1){
                        continue;
                    }
                    String id = bean.valueList.get(position).id;
                    param= param +"-"+id;

                }

                if (!TextUtils.isEmpty(param)){
                    param = param.substring(1);
                }

                Log.i(TAG, "onClick: "+param);

                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra(SearchResultActivity.FILTER_URL,param);
                //设置返回数据
                setResult(1, intent);
                //关闭Activity
                finish();

//                int position = PrefUtils.getInt(MyApplication.mContext, filterBeanList.get(0).key + "position", -1);
//                int position1 = PrefUtils.getInt(MyApplication.mContext, filterBeanList.get(1).key + "position", -1);
//                int position2 = PrefUtils.getInt(MyApplication.mContext, filterBeanList.get(2).key + "position", -1);
//                String id = valueListBeens.get(position).id;
//                String id1 = valueListBeens.get(position1).id;
//                String id2 = valueListBeens.get(position2).id;
            }
        });

        /**
         * 给条目设置点击事件
         */
        lvfilterall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getAdapter() instanceof FilterAdapter){
                    CategoryFilterBean.ListFilterBean listFilterBean = filterBeanList.get(position);
                    s=listFilterBean.key;

                    valueListBeens = listFilterBean.valueList;
                    filter2Adapter = new Filter2Adapter<>(valueListBeens);

                    //不是第一次刷新一级筛选
                  //  PrefUtils.putBoolean(MyApplication.mContext,"firstinterfilter",false);
                    lvfilterall.setAdapter(filter2Adapter);
                }else {
                    //Toast.makeText(FilterActivity.this, "666", Toast.LENGTH_SHORT).show();
                    PrefUtils.putInt(MyApplication.mContext,s+"position",position);
                    lvfilterall.setAdapter(filterAdapter);
                }

            }
        });

        //加载数据
        String url = RBConstants.URL_SERVER + "productlist";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "10")
               .put("cId", "111").put("orderby", "priceUp");
        Class<? extends IResponse> clazz = CategoryFilterBean.class;
        int requestCode = 10;
        MyApplication.HL.get(url, params, clazz, requestCode, new HttpLoader.HttpListener() {

            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                Log.e(TAG, "onGetResponseSuccess: "+"hahah" );
                CategoryFilterBean categoryFilterBean = (CategoryFilterBean) response;
                filterBeanList = categoryFilterBean.listFilter;
                filterAdapter = new FilterAdapter<>(filterBeanList);

                //第一次刷新一级筛选
              //  PrefUtils.putBoolean(MyApplication.mContext,"firstinterfilter",true);
                lvfilterall.setAdapter(filterAdapter);

            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Log.e(TAG, "onGetResponseError: "+"5555" );
            }
        });

    }

    @Override
    protected void onPause() {
        //页面不可见，将存放二级筛选的sp初始化；
        for (CategoryFilterBean.ListFilterBean bean : filterBeanList) {
            PrefUtils.putInt(MyApplication.mContext,bean.key+"position",-1);

        }
        super.onPause();
    }



    class FilterAdapter<T> extends MyAdapter {

        /**
         * 接收AbsListView要显示的数据
         *
         * @param data 要显示的数据
         */
        public FilterAdapter(List data) {
            super(data);
        }


        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterHolder(MyApplication.mContext);
        }


        @Override
        public boolean hasLoadMore() {
            return false;
        }

    }


    class Filter2Adapter<T> extends MyAdapter {

        /**
         * 接收AbsListView要显示的数据
         *
         * @param data 要显示的数据
         */
        public Filter2Adapter(List data) {
            super(data);
        }


        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Filter2Holder(MyApplication.mContext);
        }


        @Override
        public boolean hasLoadMore() {
            return false;
        }

    }
}
