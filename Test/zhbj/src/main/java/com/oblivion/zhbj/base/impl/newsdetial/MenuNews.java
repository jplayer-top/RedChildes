package com.oblivion.zhbj.base.impl.newsdetial;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oblivion.zhbj.MainActivity;
import com.oblivion.zhbj.R;
import com.oblivion.zhbj.base.BaseMenuPager;
import com.oblivion.zhbj.base.BaseNewsCenterParger;
import com.oblivion.zhbj.base.BaseNewsDetialTap;
import com.oblivion.zhbj.domain.NewsMenuMessage;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oblivion on 2016/11/1.
 */
public class MenuNews extends BaseNewsCenterParger {
    private List<NewsMenuMessage.DataBean.ChildrenBean> children;
    private ArrayList<BaseNewsDetialTap> mList;
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private View view;

    public MenuNews(Activity activity, List<NewsMenuMessage.DataBean.ChildrenBean> children) {
        super(activity);
        this.children = children;
    }


    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.news_detial_tap, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tap_news);
        //设置页签顶部栏
        mList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            mList.add(new BaseNewsDetialTap(mActivity,children.get(i)));
           // System.out.println(children.get(i).getUrl());
        }
        initData();//初始化数据？
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //创建根据数据获取到的条目
        mViewPager.setAdapter(new TapAdapter());
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        //设定滑动事件，但是只能设定给indicator,只有这样才能不会出现滑动不了的后果
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置何时关闭，何时开启
                if (position==0) {
                    setSlideMenuMode(false);
                }else {
                    setSlideMenuMode(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 设定侧边栏可否滑出
     */
    private void setSlideMenuMode(boolean flag) {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.sendSlidingMenu();
        if (flag) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
    }

    /**
     * 适配器
     */
    class TapAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //也可以在这儿加载数据。。。
            View view = mList.get(position).mRootView;
            //mList.get(position).tv.setText(children.get(position).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * indiacor  添加顶部条目,重写的那个Indactor的方法
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

    }
}
