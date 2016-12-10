package com.oblivion.redchildpuls.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.bean.LoginResponse;
import com.oblivion.redchildpuls.bean.OrderDetailResponse;
import com.oblivion.redchildpuls.bean.OrderResponse;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public final int ITEM_TYPE_NULL = 0;
    public final int ITEM_TYPE_GENERAL = 1;
    private RadioGroup rgOrder;
    private ListView lvOrder;
    private int userId = MyApplication.userId;
    private OrderResponse orderResponse;  //请求的数据
    private boolean flag =true;   //此旗子判断是否当前点击状态为取消页面
    private Myadapter mAdapter;
    private int currentTab = 1 ; //默认是第一页

    //订单信息的请求网址:http://192.168.16.240:8080/RedBabyServer/orderlist?type=2&page=1&pageNum=30   请求头加上userid
    //订单详情页的请求网址:http://192.168.16.240:8080/RedBabyServer/orderdetail?orderId=098593  请求头加上userid 20428
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        rgOrder = (RadioGroup) findViewById(R.id.rg_order);
        lvOrder = (ListView) findViewById(R.id.lv_order);
        //打开页面默认加载第一页
        loadDate(1);
        mAdapter = new Myadapter();
        lvOrder.setAdapter(mAdapter);
        lvOrder.setOnItemClickListener(this);

        //切换标签改变数据做法：
        /*
        * listview 设置两个item  如果没有数据   listview 展示一个itemtype  没有数据
        * 如果申请到了数据刷新   在getcount 中进行判断     返回真实的数据
        * getitem中进行判断 如果网络请求的数据信息是空 则将item 展示为 没有数据的提示
        * getitem 中 进行判断 如果网络请求有数据，则将item 展示为有数据的item
        * */
        //3个标签的下拉加载方法  此方法略复杂
        /*
        * 建立3个list bean 的 集合 用于缓存
        * 增加一个item 类型 如果当前的item是当前数据列表的最后一条展示加载条目 并且
        * 根据当前tab 的类型  调用 loadmore的方法   对缓存进行处理  将adapter使用的集合更新 增加条目  并且刷新adapter
        * */
        /*
        * 进行判断 如果我点击了 第三个标签  将 flag 置于true   点击其他的tab 都是false  默认也是 false
        * 打开activity时传入 这个值 判断是否显示 取消订单按钮
        * */

        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_order_1:
                        //最近一个月
                        flag = true;
                        currentTab = 1;
                        loadDate(currentTab);
                     //   mAdapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_order_2:
                        //一个月前
                        flag = true;
                        currentTab = 2;
                        loadDate(currentTab);
                      //  mAdapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_order_3:
                        //已取消订单
                        flag = false;   //只有点击取消页面 此处时flag才为false
                        currentTab = 3;
                        loadDate(currentTab);

                        break;
                }
            }
        });
    }
    /**
     * 在此方法中进行数据的加载,加载数据第一次加载10个信息再次调用增加十个条目
     * @param type  标签的页面   也是 申请的数据类型   1个月内  一个月前   取消
     */
    private void loadDate(int type) {
        String url = RBConstants.URL_ORDERLIST;
        HttpParams params = new HttpParams();
        params.addHeader("userid",userId+"");  //// TODO: 2016/12/8
        params.put("type",type+"");
        params.put("page",1+"");
        params.put("pageNum",100+"");//暂时返回30个条目进行测试
        int code =  RBConstants.REQUEST_CODE_ORDERLIST;
        Class<? extends IResponse> clazz =  OrderResponse.class;
        HttpLoader.HttpListener listener = new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                orderResponse = (OrderResponse) response;
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
            }
        };
        MyApplication.HL.get(url,params,clazz,code,listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//       String proId = (String) parent.getItemAtPosition(position);
        if (orderResponse != null && orderResponse.orderList != null && orderResponse.orderList.size() != 0) {
            final String orderId = (String) lvOrder.getItemAtPosition(position);
           ///////////////////////////在这里必须提前进行网络申请 如果有错误弹出错误
            String url = RBConstants.URL_ORDERDDETAIL;
            HttpParams params = new HttpParams();
            params.addHeader("userid", userId+"");
            params.put("orderId", orderId);
            Class<? extends IResponse> clazz = OrderDetailResponse.class;
            int code = RBConstants.REQUEST_CODE_ORDERDETAIL;
            final OrderDetailResponse[] detailResponse = new OrderDetailResponse[1];
            HttpLoader.HttpListener listener = new HttpLoader.HttpListener() {
                @Override
                public void onGetResponseSuccess(int requestCode, IResponse response) {
                    detailResponse[0] = (OrderDetailResponse) response;
                    if (detailResponse[0].error != null && !detailResponse[0].error.isEmpty()) {
                        Toast.makeText(MyApplication.mContext,detailResponse[0].error,Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        //打开详情页
                        Intent intent = new Intent(MyApplication.mContext,OrderDetailActivity.class);
                        intent.putExtra("orderId",orderId);
                        intent.putExtra("isCancel",flag);  //如果点击事件是在 取消列表里  将此值传入 详情页面 判断是否弹出取消按钮
                        startActivity(intent);
                    }
                }
                @Override
                public void onGetResponseError(int requestCode, VolleyError error) {
                    Toast.makeText(MyApplication.mContext,"网络错误",Toast.LENGTH_SHORT).show();
                    return;
                }
            };
            MyApplication.HL.get(url, params, clazz, code, listener, false);
            //////////////////////////////////////////////////////////////////


            //// TODO: 2016/12/6

        }else {
            Toast.makeText(MyApplication.mContext,"服务器错误,无法打开详情页面,请联系客服",Toast.LENGTH_SHORT).show();
            return;
        }

    }



    private class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (orderResponse == null || orderResponse.orderList == null ||orderResponse.orderList.size() == 0) {
                //|| orderResponse.orderList.size() == 0
                //数据为空
                return 1;
            }
            return orderResponse.orderList.size();
        }

        @Override
        public Object getItem(int position) {
            switch (getItemViewType(position)) {
                //将该位置的商品列表进行返回
                case ITEM_TYPE_NULL:
                   return null;
                case ITEM_TYPE_GENERAL:
                  return orderResponse.orderList.get(position).orderId;
                default:
                    return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            switch (getItemViewType(position)) {
                case ITEM_TYPE_NULL:
                    View nullView = View.inflate(MyApplication.mContext, R.layout.item_order_null, null);
                    return nullView;
                case ITEM_TYPE_GENERAL:
                    convertView = getGeneralView(convertView,orderResponse.orderList.get(position));  //在这里进行convertView的复用
                    return convertView;
                default:
                    return null;
            }
        }

        /**
         * 进行convert view的复用  并且绑定数据
         * @param convertView
         * @param orderList   传递进来相对于item 的数据
         * @return
         */
        private View getGeneralView(View convertView, OrderResponse.OrderListBean orderList) {
            OrderViewHolder orderViewHolder;
            if (convertView == null) {
                convertView = View.inflate(MyApplication.mContext, R.layout.item_list_order, null);
                orderViewHolder = new OrderViewHolder(convertView,orderList);
            }else {
                orderViewHolder = (OrderViewHolder) convertView.getTag();
            }
            orderViewHolder.update(orderList);
            convertView.setTag(orderViewHolder);
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (  orderResponse == null||orderResponse.orderList.size() == 0) {
                return ITEM_TYPE_NULL;
            }
            return ITEM_TYPE_GENERAL;
        }

        public class OrderViewHolder {
            public TextView tvOrderNumber;
            public TextView tvOrderMoney;
            public TextView tvOrderState;
            public TextView tvOrderTime;
            public OrderViewHolder(View convertView, OrderResponse.OrderListBean orderList) {
                tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_order_number);
                tvOrderMoney = (TextView) convertView.findViewById(R.id.tv_order_money);
                tvOrderState = (TextView) convertView.findViewById(R.id.tv_order_state);
                tvOrderTime = (TextView) convertView.findViewById(R.id.tv_order_time);
            }
            /**
             * 数据绑定的方法
             * @param orderList
             */
            public void update(OrderResponse.OrderListBean orderList) {
                //todo 进行绑定操作
                tvOrderMoney.setText(orderList.price+"");
                tvOrderNumber.setText(orderList.orderId);
                tvOrderState.setText(orderList.status);
              SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date  d1 = new Date(Long.parseLong(orderList.time.trim()));
                String time = format.format(d1);
                tvOrderTime.setText(time);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDate(currentTab);   //当页面调用 onResume 方法时  刷新数据
    }


}
