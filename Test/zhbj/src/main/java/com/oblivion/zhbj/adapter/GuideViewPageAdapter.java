package com.oblivion.zhbj.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by oblivion on 2016/10/30.
 */
public class GuideViewPageAdapter extends PagerAdapter {
    private int imageViews[];
    private Context context;
    private ImageView iv;
    private ArrayList<ImageView> views;

    public GuideViewPageAdapter(int[] imageViews, Context context) {
        this.imageViews = imageViews;
        this.context = context;
        initView();
    }

    /**
     * 初始化传过来的ImageViewResID数组
     */
    private void initView() {
        //创建List,将循环得到的ImageView加载到集合中
        views = new ArrayList<>();
        //初始化资源
        for (int i = 0; i < imageViews.length; i++) {
            iv = new ImageView(context);
            iv.setImageResource(imageViews[i]);
            views.add(iv);
        }
        System.out.println(views.size() + "-----");
    }

    @Override
    public int getCount() {
        return imageViews.length;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = views.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
