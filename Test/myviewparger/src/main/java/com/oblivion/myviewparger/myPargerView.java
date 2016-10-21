package com.oblivion.myviewparger;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by oblivion on 2016/10/21.
 */

/**
 * 创建适配器类继承PargerAdapter
 */
class myPargerAdapter extends PagerAdapter {
    private int[] imageViewIds;

    public myPargerAdapter(int[] imageViewIds) {
        this.imageViewIds = imageViewIds;
    }

    //为了实现图片的死循环将返回的count值设定*10000,模拟实现死循环
    @Override
    public int getCount() {
        return imageViewIds.length * 10000;
    }

    //判断能否复用。固定写法
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //在viewparger中设定条目，并返回条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        //由于getCount()方法中值是大于集合长度的，所以为防止指针越界，对position进行简单取模处理
        /**
         * 0 % 5 = 0;
         * 1 % 5 = 1;
         * .
         * .
         * .
         * 7 % 5 = 2;
         */
        position = position % imageViewIds.length;
        imageView.setBackgroundResource(imageViewIds[position]);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
        object = null;
    }
}

