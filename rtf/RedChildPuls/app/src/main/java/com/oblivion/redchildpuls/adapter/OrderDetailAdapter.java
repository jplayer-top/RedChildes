package com.oblivion.redchildpuls.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.bean.OrderDetailResponse;

import java.util.List;

/**
 * Created by nick on 2016/12/7.
 */
public class OrderDetailAdapter extends BaseAdapter {

    /**
     * error : 没有该订单详情
     * error_code : 1538
     * response : error
     */

    public String error;
    public String error_code;
    public String response;
    private  List<OrderDetailResponse.ProductListBean> mList;
    public OrderDetailAdapter(List<OrderDetailResponse.ProductListBean> productList) {
        mList = productList;
    }
    @Override
    public int getCount() {
        if (mList!=null && mList.size()!= 0){
            return mList.size();
        }else {
            return 0;
        }
    }
    @Override
    public Object getItem(int position) { return null;}

    @Override
    public long getItemId(int position) {  return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyDetailViewPager viewPager;
        if (convertView == null){
            convertView = View.inflate(MyApplication.mContext,R.layout.item_order_detail_list,null);
            viewPager = new MyDetailViewPager(convertView,mList.get(position));
        }else {
            viewPager = (MyDetailViewPager) convertView.getTag();
        }
        viewPager.update();
        convertView.setTag(viewPager);
        return convertView;
    }
    public class  MyDetailViewPager{
    public TextView tvOrderDetailProductnameNet;
    public TextView tvOrderDetailProductcolorNet;
    public TextView tvOrderDetailProductsizeNet;
    public TextView tvOrderDetailProductnumberNet;
    public TextView tvOrderDetailProductpriceNet;
        OrderDetailResponse.ProductListBean data;
        public MyDetailViewPager(View view, final OrderDetailResponse.ProductListBean bean) {
            data = bean;
            tvOrderDetailProductnameNet = (TextView) view.findViewById(R.id.tv_order_detail_productname_net);
            tvOrderDetailProductcolorNet = (TextView) view.findViewById(R.id.tv_order_detail_productcolor_net);
            tvOrderDetailProductsizeNet = (TextView) view.findViewById(R.id.tv_order_detail_productsize_net);
            tvOrderDetailProductnumberNet = (TextView) view.findViewById(R.id.tv_order_detail_productnumber_net);
            tvOrderDetailProductpriceNet = (TextView) view.findViewById(R.id.tv_order_detail_productprice_net);
//            view.setOnClickListener(new View.OnClickListener() {
//                //商品详情的跳转来到这里
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(MyApplication.mContext ,DetailActivity_.class);
//                    String pid = bean.product.id + "";
//                 //   i.putExtra("pid",pid);
//                   // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    DetailActivity_.activitystart(OrderDetailActivity.this,pid);
//                    MyApplication.mContext.startActivity(i);
//                }
//            });
        }

        public void update(){
            tvOrderDetailProductnameNet.setText(data.product.name);
            //// TODO: 2016/12/7
            //对颜色,尺寸进行判断
            int count = data.product.productProperty.size();
            String color = "" ;
            String size = "";
            for (int i = 0; i <count ; i++) {
                if ("颜色".equals(data.product.productProperty.get(i).k)){
                    color =  color + data.product.productProperty.get(i).v+",";
                }if ("尺码".equals(data.product.productProperty.get(i).k)){
                    size = size + data.product.productProperty.get(i).v + ",";
                }
            }
            if (color.length() != 0){
                color = color.substring(0,color.length()-1);
                tvOrderDetailProductcolorNet.setText(color);
            }
            if (size.length()!= 0){
                size = size.substring(0,size.length()-1);
                tvOrderDetailProductsizeNet.setText(size);
            }
            tvOrderDetailProductnumberNet.setText(data.prodNum+"");
            tvOrderDetailProductpriceNet.setText(data.product.price+"");
        }
    }

}
