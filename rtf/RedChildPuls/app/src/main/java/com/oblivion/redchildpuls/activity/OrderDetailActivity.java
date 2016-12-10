package com.oblivion.redchildpuls.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.adapter.OrderDetailAdapter;
import com.oblivion.redchildpuls.bean.OrderCancellResponse;
import com.oblivion.redchildpuls.bean.OrderDetailResponse;
import com.oblivion.redchildpuls.view.NoScorllListView;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView tvOrderDetailNumberNet;
    private TextView tvOrderDetailNameNet;
    private TextView tvOrderDetailPhoneNet;
    private TextView tvOrderDetailAddressNet;
    private TextView tvOrderDetailOrderstateNet;
    private TextView tvOrderDetailDeliverytypeNet;
    private TextView tvOrderDetailPaytypeNet;
    private TextView tvOrderDetailOrdertimeNet;
    private TextView tvOrderDetailDeliverytimeNet;
    private TextView tvOrderDetailIsinvoiceNet;
    private TextView tvOrderDetailInvoicetitleNet;
    private TextView tvOrderDetailDeliveryinfoNet;
    private TextView tvOrderDetailProductListNumberNet;
    private TextView tvOrderDetailProductListTotalpriceNet;
    private TextView tvOrderDetailProductListFreightNet;
    private TextView tvOrderDetailProductListBillNet;
    private NoScorllListView lvOrderDetailProduct;
    private Button btOrderCancel;
    private String orderNum;
    private String userId = MyApplication.userId+"";
    private OrderDetailResponse mResponse;
    private  boolean isCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
       orderNum = getIntent().getExtras().get("orderId").toString();      //todo 暂时使用假数据
        isCancel = getIntent().getExtras().getBoolean("isCancel");


        ALog.i("当前条目的 id 是" + orderNum);
        init();
        initData();
        setCancell();
    }

    private void setCancell() {
        btOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                builder.setTitle("提示");
                builder.setMessage("您确定要取消这份订单么?,如果商品已经发货,您将无法及时收到退款");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancell(dialog);
                    }
                });
                AlertDialog dialog = builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
        });
    }

    /**
     * 取消订单的申请
     * @param dialog
     */
    private void cancell(final DialogInterface dialog) {
        String url = RBConstants.URL_ORDERCANCELL;
        HttpParams params= new HttpParams();
        params.addHeader("userid",userId);
        params.put("orderId",orderNum);
        Class<? extends IResponse> clazz = OrderCancellResponse.class;
        int code = RBConstants.REQUEST_CODE_ORDERCANCELL;
            MyApplication.HL.get(url, params, clazz, code, new HttpLoader.HttpListener() {
                @Override
                public void onGetResponseSuccess(int requestCode, IResponse response) {
                    OrderCancellResponse response1 = (OrderCancellResponse) response;
                    switch (response1.response) {
                        case "取消订单失败":
                            Toast.makeText(MyApplication.mContext,"订单取消失败,请联系客服",Toast.LENGTH_SHORT).show();
                            break;
                        case "orderCancel":
                            dialog.dismiss();
                            Toast.makeText(MyApplication.mContext,"订单取消成功",Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
                @Override
                public void onGetResponseError(int requestCode, VolleyError error) {
                    Toast.makeText(MyApplication.mContext,"网络错误,订单取消失败,请联系客服",Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void initView() {
        tvOrderDetailNumberNet.setText(orderNum);
       tvOrderDetailNameNet.setText(mResponse.addressInfo.name);
        tvOrderDetailPhoneNet.setText("138888888");  //服务器接口没有电话 强行设置一个电话
        tvOrderDetailAddressNet.setText(mResponse.addressInfo.addressArea+","+mResponse.addressInfo.addressDetail);
        tvOrderDetailOrderstateNet.setText(mResponse.orderInfo.status);
        /*
        * //1 => 周一至周五送货 2=> 双休日及公众假期送货 3=> 时间不限，工作日双休日及公众假期均可送货
        * */
        String delivery;
        switch (mResponse.deliveryInfo.type) {
            case "1":
                delivery ="周一至周五送货";
                break;
            case "2":
                delivery ="双休日及公众假期送货";
                break;
            case "3":
                delivery ="时间不限";
                break;
            default:
                delivery ="未知";
        }
        tvOrderDetailDeliverytypeNet.setText(delivery);  //设置送货方式
        String paytype;
        switch (mResponse.paymentInfo.type) {
            case 1:
                paytype ="货到付款";
                break;
            case 2:
                paytype ="货到POS机";
                break;
            case 3:
                paytype ="支付宝";
                break;
            default:
                paytype ="未知";
        }
        tvOrderDetailPaytypeNet.setText(paytype);   //设置支付类型
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(Long.parseLong(mResponse.orderInfo.time.trim()));
        String orderTime = format.format(d1);
        tvOrderDetailOrdertimeNet.setText(orderTime);
        tvOrderDetailDeliverytimeNet.setText(orderTime);  //订单时间和 送货时间是一个时间
        tvOrderDetailIsinvoiceNet.setText("是");
        tvOrderDetailInvoicetitleNet.setText("江苏传智播客公司");
        tvOrderDetailDeliveryinfoNet.setText("未知");
        ///////////////////////订单部分///////////////////////////////////////////
        tvOrderDetailProductListNumberNet.setText(mResponse.checkoutAddup.totalCount+"");
        tvOrderDetailProductListTotalpriceNet.setText(mResponse.checkoutAddup.totalPrice+"");
        tvOrderDetailProductListFreightNet.setText(mResponse.checkoutAddup.freight+"");
        String bill = (mResponse.checkoutAddup.totalPrice - mResponse.checkoutAddup.freight)+"";
        tvOrderDetailProductListBillNet.setText(bill);   //设置总应付
        //产品列表加载
        OrderDetailAdapter adapter = new OrderDetailAdapter(mResponse.productList);
        lvOrderDetailProduct.setAdapter(adapter);
        lvOrderDetailProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ALog.i("订单详情页 条件没有通过接受不到点击事件");
                if (mResponse !=null && mResponse.productList != null){
                    ALog.i("订单详情页 条件通过接受到点击事件");
                    DetailActivity_.activitystart(OrderDetailActivity.this,mResponse.productList.get(position).prodNum+"");
                }
            }
        });

    }

    private void initData() {
        String url = RBConstants.URL_ORDERDDETAIL;
        HttpParams params = new HttpParams();
        params.addHeader("userid", userId);
        params.put("orderId", orderNum);
        Class<? extends IResponse> clazz = OrderDetailResponse.class;
        int code = RBConstants.REQUEST_CODE_ORDERDETAIL;
        HttpLoader.HttpListener listener = new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                mResponse = (OrderDetailResponse) response;
                if (mResponse != null&&mResponse.productList != null){           //进行数据的填充
                    initView();
                }
            }
            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
            }
        };
        MyApplication.HL.get(url, params, clazz, code, listener, false);

        return;
    }

    private void init() {
        tvOrderDetailNumberNet = (TextView) findViewById(R.id.tv_order_detail_number_net);
        tvOrderDetailNameNet = (TextView) findViewById(R.id.tv_order_detail_name_net);
        tvOrderDetailPhoneNet = (TextView) findViewById(R.id.tv_order_detail_phone_net);
        tvOrderDetailAddressNet = (TextView) findViewById(R.id.tv_order_detail_address_net);
        tvOrderDetailOrderstateNet = (TextView) findViewById(R.id.tv_order_detail_orderstate_net);
        tvOrderDetailDeliverytypeNet = (TextView) findViewById(R.id.tv_order_detail_deliverytype_net);
        tvOrderDetailPaytypeNet = (TextView) findViewById(R.id.tv_order_detail_paytype_net);
        tvOrderDetailOrdertimeNet = (TextView) findViewById(R.id.tv_order_detail_ordertime_net);
        tvOrderDetailDeliverytimeNet = (TextView) findViewById(R.id.tv_order_detail_deliverytime_net);
        tvOrderDetailIsinvoiceNet = (TextView) findViewById(R.id.tv_order_detail_isinvoice_net);
        tvOrderDetailInvoicetitleNet = (TextView) findViewById(R.id.tv_order_detail_invoicetitle_net);
        tvOrderDetailDeliveryinfoNet = (TextView) findViewById(R.id.tv_order_detail_deliveryinfo_net);
        tvOrderDetailProductListNumberNet = (TextView) findViewById(R.id.tv_order_detail_product_list_number_net);
        tvOrderDetailProductListTotalpriceNet = (TextView) findViewById(R.id.tv_order_detail_product_list_totalprice_net);
        tvOrderDetailProductListFreightNet = (TextView) findViewById(R.id.tv_order_detail_product_list_freight_net);
        tvOrderDetailProductListBillNet = (TextView) findViewById(R.id.tv_order_detail_product_list_bill_net);
        btOrderCancel = (Button) findViewById(R.id.bt_order_cancel);
        lvOrderDetailProduct = (NoScorllListView) findViewById(R.id.lv_order_detail_product);
        //判断  是否显示取消按钮
        if (!isCancel) {
            btOrderCancel.setVisibility(View.GONE);
        }
    }
}
