package com.oblivion.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oblivion.zhbj.MainActivity;
import com.oblivion.zhbj.R;
import com.oblivion.zhbj.base.BaseMenuPager;
import com.oblivion.zhbj.base.impl.MainGovPager;
import com.oblivion.zhbj.base.impl.MainHomePager;
import com.oblivion.zhbj.base.impl.MainNewsCenterPager;
import com.oblivion.zhbj.base.impl.MainSettingPager;
import com.oblivion.zhbj.base.impl.MainSmartServerPager;
import com.oblivion.zhbj.ui.NoScrollViewParger;

import java.util.ArrayList;

/**
 * Created by oblivion on 2016/10/30.
 */
public class MainFragment extends BaseFragment {
    private NoScrollViewParger mPager;
    private ArrayList<BaseMenuPager> mList;
    private RadioGroup mRadioGroup;
    private innerPagerAdaoter mPageAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_main, null);
        mPager = (NoScrollViewParger) view.findViewById(R.id.vp_main_parger);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_menus);
        mList = new ArrayList<>();
        return view;
    }

    @Override
    public void initDate() {
        //将初始化ViewParger界面数据创建到集合中
        mList.add(new MainHomePager(mActivity));
        mList.add(new MainNewsCenterPager(mActivity));
        mList.add(new MainGovPager(mActivity));
        mList.add(new MainSmartServerPager(mActivity));
        mList.add(new MainSettingPager(mActivity));
        mPageAdapter = new innerPagerAdaoter();
        mPager.setAdapter(mPageAdapter);
        //初始设定为不可滑动
        setSlideMenuMode(true);
        //为每个界面添加选择事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_menu_home:
                        //设定两个参数的，为了让他没有滑动事件
                        //滑动到指点位置
                        mPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_main_menu_news:
                        mPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_main_menu_gov:
                        mPager.setCurrentItem(2, false);

                        break;
                    case R.id.rb_main_menu_server:
                        mPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_main_menu_setting:
                        mPager.setCurrentItem(4, false);
                        break;
                }
            }
        });
        //设定选定的监听，设定当所在界面被加载的时候，才会加载数据
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //初始化每一个ViewParger页面的数据
                mList.get(position).initData();
                if (position == 0 || position == mList.size() - 1) {
                    setSlideMenuMode(true);
                } else {
                    setSlideMenuMode(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //指定初始位置,以及初始化资源
        // mPager.setCurrentItem(0, false);//加上这句话就初次不显示？
        mList.get(0).initData();
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

    class innerPagerAdaoter extends PagerAdapter {
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

            //之前我用的是 View mRootView = mList.get(position).initView()，结果主界面一直不显示
            View mRootView = mList.get(position).mRootView;


            //由于ViewParger的特性,在加载第一张时，下一张已经初始化好了，
            //避免浪费资源，，只能搞掉这种方法，所以只能将他的初始化设定在选择的时候
            //mList.get(position).initData();
            container.addView(mRootView);
            System.out.println("???");
            return mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 获取当前的MainNewsParger对象
     * @return
     */
    public MainNewsCenterPager getnewsCenterPager() {
        MainNewsCenterPager mainNewsCenterPager = (MainNewsCenterPager) mList.get(1);
        return mainNewsCenterPager;
    }
}
