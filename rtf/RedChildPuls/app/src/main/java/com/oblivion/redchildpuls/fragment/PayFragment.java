package com.oblivion.redchildpuls.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.ToPayActivity;
import com.oblivion.redchildpuls.activity.oblivapi.CartApi;
import com.oblivion.redchildpuls.adapter.CartDetialAdapter;
import com.oblivion.redchildpuls.base.SuperFragment;
import com.oblivion.redchildpuls.bean.CartResponse;
import com.oblivion.redchildpuls.event.EventBadgeView;
import com.oblivion.redchildpuls.event.EventToCart;
import com.oblivion.redchildpuls.holder.CartDetailCartHolder;
import com.oblivion.redchildpuls.holder.NumberDetailHolder;
import com.oblivion.redchildpuls.holder.PriceDetailCartHolder;
import com.oblivion.redchildpuls.view.MyTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class PayFragment extends SuperFragment implements HttpLoader.HttpListener, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.cart_title)
    MyTitleBar cartTitle;
    @Bind(R.id.cart_ll)
    LinearLayout cartLl;
    @Bind(R.id.cart_detail_ll)
    LinearLayout cartDetailLl;
    @Bind(R.id.cart_detail_cart)
    FrameLayout cartDetailCart;
    @Bind(R.id.swipe_cart)
    SwipeRefreshLayout swipe_cart;
    @Bind(R.id.fab_pay)
    FloatingActionButton fabPay;

    private CartResponse cartResponse;
    private NumberDetailHolder numberDetailHolder;
    private CartDetailCartHolder cartHolder;
    private ListView listView;
    private PriceDetailCartHolder priceDetailCartHolder;

    /**
     * 加载界面
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //不能使用Myapplication.mContext
        View view = View.inflate(container.getContext(), R.layout.frag_pay, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        swipe_cart.setColorSchemeColors(Color.parseColor("#0000ff"));
        swipe_cart.setOnRefreshListener(this);
        cartTitle.addOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.mContext, "跳转到编辑", Toast.LENGTH_SHORT).show();
            }
        });
        fabPay.hide();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                MyApplication.mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fabPay.show();
                    }
                });
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        //添加头
        numberDetailHolder = new NumberDetailHolder(MyApplication.mContext);
        // numberDetailCart.addView(numberDetailHolder.rootView);
        //添加listView
        priceDetailCartHolder = new PriceDetailCartHolder(MyApplication.mContext);
        // priceDetailCart.addView(priceDetailCartHolder.rootView);
        cartHolder = new CartDetailCartHolder(MyApplication.mContext);
        listView = new ListView(MyApplication.mContext);
        listView.addHeaderView(numberDetailHolder.rootView);
        listView.addFooterView(priceDetailCartHolder.rootView);
        //   fab.hide();//隐藏
        cartDetailCart.addView(listView);
        /**
         * 解决冲突事件
         */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    swipe_cart.setEnabled(true);
                } else {
                    swipe_cart.setEnabled(false);
                }
                if (listView.getLastVisiblePosition() == totalItemCount - 1) {
                    fabPay.show();
                    fabPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), ToPayActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApplication.mContext.startActivity(intent);
                        }
                    });
                } else {
                    fabPay.hide();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(EventToCart event) {
      //  MyApplication.stringBuffer.append(event.ProdID);
        CartApi.getCheckoutData(this, MyApplication.stringBuffer.toString());
    }

    /**
     * 请求网络操作
     */
    @Override
    protected void requestNetData() {
        //加载数据
        com.oblivion.redchildpuls.activity.oblivapi.CartApi.getCheckoutData(this, MyApplication.stringBuffer.toString());
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        swipe_cart.setRefreshing(true);
        com.oblivion.redchildpuls.activity.oblivapi.CartApi.getCheckoutData(this, MyApplication.stringBuffer.toString());
    }

    /**
     * 请求数据
     */
    @Override
    protected void loadData() {
        swipe_cart.setRefreshing(true);
        CartApi.getCheckoutData(this, MyApplication.stringBuffer.toString());
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        swipe_cart.setRefreshing(false);

        cartResponse = (CartResponse) response;
        //没有数据就设置为空
        if (cartResponse == null) {
            cartLl.setVisibility(View.VISIBLE);
        } else {
            /**
             * 请求完毕数据，发出消息，更新小红点
             */
            System.out.println(cartResponse.prom.size());
            EventBus.getDefault().postSticky(new EventBadgeView(cartResponse.cart.size()));
            cartLl.setVisibility(View.INVISIBLE);
            System.out.println(cartResponse.response);
            //加载数据
            numberDetailHolder.bindData(cartResponse);
            priceDetailCartHolder.bindData(cartResponse);
            CartDetialAdapter adapter = new CartDetialAdapter(cartResponse.cart);
            listView.setAdapter(adapter);
            // cartHolder.bindData(cartResponse);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        Toast.makeText(MyApplication.mContext, "加载失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void handleUserInput() {

    }


    /**
     * 空实现
     *
     * @return
     */
    @Override
    public View initView() {
        return null;
    }


}
