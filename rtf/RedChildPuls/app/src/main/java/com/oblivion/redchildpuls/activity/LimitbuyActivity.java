package com.oblivion.redchildpuls.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.base.MyAdapter;
import com.oblivion.redchildpuls.bean.LimitBuyBean;
import com.oblivion.redchildpuls.holder.LimitBuyHolder;
import com.oblivion.redchildpuls.holder.LoadMoreHolder;
import com.oblivion.redchildpuls.utils.PrefUtils;
import com.oblivion.redchildpuls.view.MyTitleBar;

import org.senydevpkg.base.BaseHolder;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

public class LimitbuyActivity extends AppCompatActivity {

    private static final String TAG = "LimitbuyActivity";


    private int page = 1;
    private ListView lvLimitBuyGoods;
    private ArrayList<LimitBuyBean.ProductListBean> beanList;
    private MyTitleBar myTitleBar;
    private  long startTimeMillis;
    private LimitBuyAdapter limitBuyAdapter;
    private MyRunable myRunable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limitbuy);
        myTitleBar = (MyTitleBar) findViewById(R.id.mtb_limitBuy);
        lvLimitBuyGoods = (ListView) findViewById(R.id.lv_limitBuy_goods);

        //给listview的条目设置点击事件
        lvLimitBuyGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity_.activitystart(MyApplication.mContext,beanList.get(position).id);
            }
        });

        myTitleBar.addOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String url = RBConstants.URL_SERVER + "limitbuy";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "15");
        Class<? extends IResponse> clazz = LimitBuyBean.class;
        int requestCode = 10;
        MyApplication.HL.get(url, params, clazz, requestCode, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                LimitBuyBean limitBuyBean = (LimitBuyBean) response;
                beanList = (ArrayList<LimitBuyBean.ProductListBean>) limitBuyBean.productList;
                limitBuyAdapter = new LimitBuyAdapter(beanList);
                lvLimitBuyGoods.setAdapter(limitBuyAdapter);

                //记录第一次获取服务器数据的时间,bing存到sp中,如果存过一次就不再存
                startTimeMillis = System.currentTimeMillis();
                if ("".equals(PrefUtils.getString(MyApplication.mContext,"startTimeMillis",""))){
                    PrefUtils.putString(MyApplication.mContext,"startTimeMillis",startTimeMillis+"");
                }

                //开始倒计时任务
                if (myRunable == null) {
                    myRunable = new MyRunable();
                    myRunable.start();
                }
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
               // Log.e(TAG, "onGetResponseError: "+"jiazaishibai" );
            }
        });



    }


    /**
     * 描述	      针对MyBaseAdapter里面的getView方法
     * 描述	      在getView方法中引入了BaseHolder这个类
     */
    class LimitBuyAdapter<T> extends MyAdapter {

        private int mState;

        /**
         * 接收AbsListView要显示的数据
         *
         * @param data 要显示的数据
         */
        public LimitBuyAdapter(List data) {
            super(data);
        }

        @Override
        protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LimitBuyHolder(MyApplication.mContext);
        }

        int count =0;
        @Override
        public void loadMoreAndRefeshUi() {
            String url = RBConstants.URL_SERVER + "limitbuy";
            HttpParams params = new HttpParams();
            page++;
            params.put("page", page + "").put("pageNum", "15");
            Class<? extends IResponse> clazz = LimitBuyBean.class;
            int requestCode = 10;
            MyApplication.HL.get(url, params, clazz, requestCode, new HttpLoader.HttpListener() {
                @Override
                public void onGetResponseSuccess(int requestCode, IResponse response) {
                    LimitBuyBean limitBuyBean = (LimitBuyBean) response;
                    final ArrayList<LimitBuyBean.ProductListBean> list = (ArrayList<LimitBuyBean.ProductListBean>) limitBuyBean.productList;

                        /*--------------- 具体刷新ui ---------------*/
                    MyApplication.mMainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //刷新ui-->ListView-->修改数据集(mDataSets.add(loadMoreList))-->adapter.notifyDataSetChanged();
                            count++;
                           // Log.i(TAG, "run: "+count);
                            if (list != null) {
                             //   Log.i(TAG, "run: "+beanList.size()+"   "+mData.size());
                                if (count ==1){
                                    beanList.addAll(list);
                                }

                                notifyDataSetChanged();
                            }
                            //刷新ui-->mLoadMoreHolder-->mLoadMoreHolder.setDataAndRefreshHolder(curState);
                            mLoadMoreHolder.bindData(mState);
                        }

                    });
                }
                @Override
                public void onGetResponseError(int requestCode, VolleyError error) {

                    mState = LoadMoreHolder.LOADMORE_ERROR;//加载更多失败
                }
            });
            count=0;
        }


        /**
         * @return
         * @des 是否有加载更多, 默认没有加载更多
         * @des 子类可以覆写该方法, 可以决定有加载跟多
         */
        public boolean hasLoadMore() {
            return true;//默认没有加载更多
        }
    }


    //倒计时的任务类
    public class MyRunable implements Runnable {

        public void start(){
           // Log.e(TAG, "start: "+"222" );
            stop();
            MyApplication.mMainThreadHandler.postDelayed(this,1000);
        }

        public void stop(){
           // Log.e(TAG, "stop: "+"333" );
            MyApplication.mMainThreadHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
           // Log.e(TAG, "run: "+"111" +"---");
            limitBuyAdapter.notifyDataSetChanged();
            start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (myRunable!=null){
            myRunable.start();
        }

    }

    @Override
    protected void onPause() {
        myRunable.stop();
       // MyApplication.mMainThreadHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

}
