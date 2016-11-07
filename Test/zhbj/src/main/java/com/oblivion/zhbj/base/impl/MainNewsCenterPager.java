package com.oblivion.zhbj.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.oblivion.zhbj.MainActivity;
import com.oblivion.zhbj.base.BaseMenuPager;
import com.oblivion.zhbj.base.BaseNewsCenterParger;
import com.oblivion.zhbj.base.impl.newsdetial.MenuNews;
import com.oblivion.zhbj.base.impl.newsdetial.MenuPicture;
import com.oblivion.zhbj.base.impl.newsdetial.MenuTop;
import com.oblivion.zhbj.base.impl.newsdetial.MenuUnitl;
import com.oblivion.zhbj.fragment.LeftFragment;
import com.oblivion.zhbj.goble.Golbe;
import com.oblivion.zhbj.domain.NewsMenuMessage;
import com.oblivion.zhbj.utils.CacheUtils;
import com.oblivion.zhbj.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by oblivion on 2016/10/31.
 */
public class MainNewsCenterPager extends BaseMenuPager {
    private String result;
    private NewsMenuMessage newsMenuMessage;
    private ArrayList<BaseNewsCenterParger> mNewsList;

    public MainNewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        System.out.println("news加载");
        iv_slide2left.setVisibility(View.VISIBLE);
        tv_title.setText("信息交互");
        String cache = CacheUtils.getCache(mActivity);
        if (!TextUtils.isEmpty(cache)) {
            progressData(cache);
        }
        getNewsMenuParger();
    }

    /**
     * 得到News对象
     */
    private void getNewsMenuParger() {
        HttpUtils utils = new HttpUtils();
        //别倒成了HttpRequest.HttpMethod.GET
        utils.send(HttpMethod.GET, Golbe.URL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // 请求成功
                        result = responseInfo.result;// 获取服务器返回结果
                        //设定缓存
                        CacheUtils.setCache(result, mActivity);
                        //System.out.println("服务器返回结果:" + result);

                        progressData(result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        // 请求失败
                        error.printStackTrace();
                        Toast.makeText(mActivity, "是不是没联网", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 设定数据
     */
    private void progressData(String result) {
        Gson gson = new Gson();
        newsMenuMessage = gson.fromJson(result, NewsMenuMessage.class);
        // System.out.println(newsMenuMessage.getData().get(1).getTitle());
        MainActivity mainActivity = (MainActivity) mActivity;
        LeftFragment mLeftFragment = (LeftFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("LEFT");
        //传输数据
        mLeftFragment.getNewsMenus(newsMenuMessage);
        //接收到数据之前把数据初始化好
        mNewsList = new ArrayList<>();
        mNewsList.add(new MenuNews(mActivity, newsMenuMessage.getData().get(0).getChildren()));//新闻
        mNewsList.add(new MenuUnitl(mActivity));//专题
        mNewsList.add(new MenuPicture(mActivity));//组图
        mNewsList.add(new MenuTop(mActivity));//互动
        captureCurrentItem(0);
    }

    /**
     * 获取到当前ListView点击的对象所在的条目
     */
    public void captureCurrentItem(final int currentPosition) {
        //修改标题,
        tv_title.setText((newsMenuMessage.getData().get(currentPosition).getTitle()));
        if (currentPosition == 2) {
            iv_layoutmanager.setVisibility(View.VISIBLE);
            iv_layoutmanager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean state = PrefUtils.getBoolean(mActivity, "state", false);
                    PrefUtils.putBoolean(mActivity, "state", !state);
                    System.out.println(state);
                    MenuPicture menuPicture = (MenuPicture) mNewsList.get(2);
                    menuPicture.setChangeLayout();
                }
            });
        } else {
            iv_layoutmanager.setVisibility(View.INVISIBLE);
        }
        //在之前清空帧布局中所有布局
        fl_main_parger.removeAllViews();
        //获取到数据,将数据添加到帧布局中
        View view = mNewsList.get(currentPosition).initView();
        fl_main_parger.addView(view);
    }
}
