package com.oblivion.zhbj.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.oblivion.zhbj.DetialActivity;
import com.oblivion.zhbj.R;
import com.oblivion.zhbj.domain.NewsMenuMessage;
import com.oblivion.zhbj.domain.TapDetial;
import com.oblivion.zhbj.goble.Golbe;
import com.oblivion.zhbj.ui.PullRefreshLoadMoreListView;
import com.oblivion.zhbj.utils.PrefUtils;
import com.oblivion.zhbj.utils.ToastUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


/**
 * Created by oblivion on 2016/11/2.
 */
public class BaseNewsDetialTap {
    private Activity mActivity;
    public TextView tv;
    public View mRootView;
    private ViewPager mViewPager;
    private String url;
    private ArrayList<TapDetial.TopNews> topnews;
    private View view;
    private CirclePageIndicator mIndicator;
    private com.oblivion.zhbj.ui.PullRefreshLoadMoreListView mListView;
    private ArrayList<TapDetial.News> news;
    private View viewHeader;
    private Handler handler = new Handler();
    private TapDetial tapDetial;
    private MyListViewAdapter myListViewAdapter;

    public BaseNewsDetialTap(Activity activity, NewsMenuMessage.DataBean.ChildrenBean childrenBean) {
        this.mActivity = activity;
        mRootView = initView();
        url = Golbe.BASEURL + childrenBean.getUrl();
        System.out.println(url);
        initDate();
    }

    public View initView() {
//        tv = new TextView(mActivity);
//        tv.setText("test");
//        tv.setGravity(Gravity.CENTER);
        view = View.inflate(mActivity, R.layout.news_tap_viewpager, null);
        viewHeader = View.inflate(mActivity, R.layout.listview_header, null);
        mViewPager = (ViewPager) viewHeader.findViewById(R.id.vp_tap_pager);
        mListView = (PullRefreshLoadMoreListView) view.findViewById(R.id.lv_tap_listview);
        mListView.addHeaderView(viewHeader);
        //设定下拉加载的监听
        mListView.setOnRefreshStateListener(new PullRefreshLoadMoreListView.OnRefreshStateListener() {
            @Override
            public void refreshRunning() {
                ToastUtils.setToast(mActivity, "正在刷新");
                // initDate();//如果正在刷新，那就重新加载数据
                HttpUtils utils = new HttpUtils();
                utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        progressData(result, true);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                mListView.complementRefresh(true);//刷新成功
                            }
                        }, 1000);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        System.out.println("失败");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mListView.complementRefresh(true);//刷新失败
                            }
                        }, 1000);
                    }
                });
            }

            /**
             *
             */
            @Override
            public void loadMore() {
                String moreURL = tapDetial.data.more;//拿到加载更多的URL
                // System.out.println("moreURL"+moreURL);
                if (TextUtils.isEmpty(moreURL)) {
                    //空的话
                    //返回数据空
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.setToast(mActivity, "没有更多了");
                            mListView.complementRefresh(false);
                        }
                    }, 1000);
                } else {
                    //非空，就加载数据
                    HttpUtils utils = new HttpUtils();
                    utils.send(HttpMethod.GET, Golbe.BASEURL + tapDetial.data.more, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            progressData(result, false);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            System.out.println("失败");
                        }
                    });
                }
            }
        });
//       空指针？
// System.out.println(Golbe.BASEURL + childrenBeen.getUrl());
        return view;
    }

    public void initDate() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progressData(result, true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("失败");
            }
        });
    }

    /**
     * 获取到gson类
     */
    private void progressData(String result, boolean loadMore) {
        if (loadMore) {//如果传入的boolean为---true---不加载更多
            Gson gson = new Gson();
            tapDetial = gson.fromJson(result, TapDetial.class);
            //获取到图片地址
            // System.out.println(tapDetial.data.topnews.get(1).topimage);
            //头条新闻的集合
            topnews = tapDetial.data.topnews;
            news = tapDetial.data.news;
            mViewPager.setAdapter(new MyPagerAdapter());
            myListViewAdapter = new MyListViewAdapter();
            mListView.setAdapter(myListViewAdapter);
            //设置点击事件
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    position -= mListView.getHeaderViewsCount();
                    String ids = PrefUtils.getString(mActivity, "ids", "");
                    //获取到条目的id,并将id添加到sharedprefaces中
                    String viewId = news.get(position).id;
                    if(!ids.contains(viewId)){
                       ids  = ids + viewId + ",";
                    }
                    PrefUtils.putString(mActivity,"ids",ids);
                    TextView tv = (TextView) view.findViewById(R.id.tv_tap_title);
                    tv.setTextColor(Color.GRAY);
                    //点击开启新闻界面
                    Intent intent = new Intent(mActivity, DetialActivity.class);
                    //将新闻详情的内容传递到过去
                    intent.putExtra("url", news.get(position).url);
                    mActivity.startActivity(intent);
                }
            });
            //记得用的是ViewHeader
            mIndicator = (CirclePageIndicator) viewHeader.findViewById(R.id.indicator);
            mIndicator.setViewPager(mViewPager);

            //为毛我加不上这个快照模式

            mIndicator.setSnap(true);//---------------原因是强转成了父类，父类没有这个方法

            //这是为了让页面和Indicator的信息同步？？

            mIndicator.onPageSelected(0);//---------------

            //好像加了监听器，也没有卵用
        } else {
            Gson gson = new Gson();
            tapDetial = gson.fromJson(result, TapDetial.class);
            ArrayList<TapDetial.News> moreNews = tapDetial.data.news;//获取加载到的更多的数据集合
            news.addAll(moreNews);//添加集合
            myListViewAdapter.notifyDataSetChanged();//通知适配器更新，适配器更新，，不管他在哪。。都更新
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.setToast(mActivity, "没有更多了");
                    mListView.complementRefresh(false);
                }
            }, 1000);
        }
    }

    /**
     * 头信息的适配器
     */
    class MyPagerAdapter extends PagerAdapter {
        private BitmapUtils bitmapUtils;

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mActivity, R.layout.tap_viewpager_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_tap_item);
            //设置图片
            bitmapUtils = new BitmapUtils(mActivity);
            //设置加载中的默认图片
            bitmapUtils
                    .configDefaultLoadingImage(R.drawable.pic_item_list_default);
            bitmapUtils.display(iv, topnews.get(position).topimage);
            //我在这里就将tv数据加载进去了，，
            TextView tv = (TextView) view.findViewById(R.id.tv_tap_item);
            tv.setText(topnews.get(position).title);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * ListView适配器
     */
    private class MyListViewAdapter extends BaseAdapter {
        private BitmapUtils bitmapUtils;

        @Override
        public int getCount() {
            return news.size();
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
            View view;
            ViewHolder holder;
            bitmapUtils = new BitmapUtils(mActivity);
            //设置加载中的默认图片
            bitmapUtils
                    .configDefaultLoadingImage(R.drawable.pic_item_list_default);
            if (convertView == null) {

                holder = new ViewHolder();
                view = View.inflate(mActivity, R.layout.tap_listview_item, null);
                holder.title = (TextView) view.findViewById(R.id.tv_tap_title);
                holder.pubdate = (TextView) view.findViewById(R.id.tv_tap_pubdate);
                holder.img = (ImageView) view.findViewById(R.id.iv_listview_img);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            if (PrefUtils.getString(mActivity,"ids","").contains( news.get(position).id)) {
                holder.title.setTextColor(Color.GRAY);
            }else {
                holder.title.setTextColor(Color.BLACK);
            }
            holder.title.setText(news.get(position).title);
            holder.pubdate.setText(news.get(position).pubdate);
            bitmapUtils.display(holder.img, news.get(position).listimage);
            return view;
        }
    }

    class ViewHolder {
        TextView pubdate;
        TextView title;
        ImageView img;
    }
}
