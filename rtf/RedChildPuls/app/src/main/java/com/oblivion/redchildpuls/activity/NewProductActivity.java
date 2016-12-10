package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.HotProductResponse;
import com.oblivion.redchildpuls.view.MyTitleBar;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.List;

public class NewProductActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //get提交   /hotproduct
    //page	第几页
    //pageNum	申请个数
    //orderby  排序

    private HotProductResponse mHotProductResponse;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotproduct);
        MyTitleBar myTitleBar = (MyTitleBar)findViewById(R.id.mytitlebar_hotproduct);
        GridView gvHotProduct = (GridView) findViewById(R.id.gridview_hotproduct);
        //用于保存的当前bean类
        mAdapter = new MyAdapter();
        gvHotProduct.setAdapter(mAdapter);
        gvHotProduct.setOnItemClickListener(this);
        initData();
        myTitleBar.setTitle("新品上架");
        myTitleBar.addOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
     //  MyApplication.HL.get(RBConstants.URL_SERVER, HotProductResponse.class,101,new HttpLoader.HttpListener(){},true);
        /////////////////////////////////开始网络加载////////////////////////////////////////
        String url = RBConstants.URL_NEWPRODUCT;       //暂时使用测试服务器
//       String url = "http://192.168.16.241:8080/RedBabyServer/" +"newproduct";
        HttpParams params = new HttpParams();
        params.put("page","1").put("pageNum","30").put("orderby","saleDown");

        Class<? extends IResponse> clazz = HotProductResponse.class;
        final int mRequestCode = RBConstants.REQUEST_CODE_HOTPRODUCT;
        MyApplication.HL.get(url,params,clazz,mRequestCode,new HttpLoader.HttpListener(){
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                     if (mRequestCode == requestCode && response instanceof HotProductResponse){
                         //数据加载成功
                         mHotProductResponse = (HotProductResponse) response;
                         //通知adapter 更新数据
                         mAdapter.notifyDataSetChanged();
                     }
            }
            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                ALog.i(error.toString());
            }
        },true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //// TODO: 2016/12/5   在这里进行点击的跳转
        Intent i = new Intent(this ,DetailActivity_.class);
        if (mHotProductResponse != null) {
            String pid = mHotProductResponse.productList.get(position).id+"";
//            i.putExtra("pid",pid);
//            startActivity(i);
            DetailActivity_.activitystart(NewProductActivity.this,pid);
        }else{
            Toast.makeText(MyApplication.mContext,"网络问题,请重试",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * HotProduct 的自定义数据
     */
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if (mHotProductResponse != null){
                return mHotProductResponse.listCount;
            }else {
                return 0;
            }
        }
        @Override
        public Object getItem(int position) {return null; }
        @Override
        public long getItemId(int position) { return 0;}
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            List<HotProductResponse.ProductListBean> productList =  mHotProductResponse.productList;
            //viewHolder 创建阶段
            myViewHolder viewHolder ;
            if (convertView == null){
                convertView =  View.inflate(MyApplication.mContext,R.layout.item_hotproduct,null);
                viewHolder = new myViewHolder(convertView);
            }else {
                viewHolder = (myViewHolder) convertView.getTag();
            }
            //数据绑定阶段  与更新  都在 update方法中
            viewHolder.update(productList,position);
            convertView.setTag(viewHolder);
            return convertView;
        }
        public class myViewHolder {
            public ImageView ivHotproductPic;
            public TextView tvHotproductName;
            public TextView tvHotproductMarketprice;

            public myViewHolder(View convertView) {
                ivHotproductPic = (ImageView)convertView.findViewById(R.id.iv_hotproduct_pic);
                tvHotproductName = (TextView) convertView.findViewById(R.id.tv_hotproduct_name);
                tvHotproductMarketprice = (TextView)convertView.findViewById(R.id.tv_hotproduct_marketprice);
            }
            public void update(List<HotProductResponse.ProductListBean> productList, int position){
                //解析数据阶段
                HotProductResponse.ProductListBean productListBean =  productList.get(position);
                String name = productListBean.name;
                int price =  productListBean.marketPrice;
                String picUrl = productListBean.pic;
            //    String imageUrl = RBConstants.URL_SERVER+picUrl;  //暂时用测试服务器
                String imageUrl = RBConstants.URL_SERVER+picUrl;
                //数据绑定阶段
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivHotproductPic,R.drawable.product_loading,R.drawable.product_loading);
                MyApplication.IL.get(imageUrl,listener);
               tvHotproductMarketprice.setText("￥"+price);
               tvHotproductName.setText(name);
            }
        }
    }
}
