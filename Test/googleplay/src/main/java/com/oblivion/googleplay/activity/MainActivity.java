package com.oblivion.googleplay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.astuetz.PagerSlidingTabStripExtends;
import com.oblivion.googleplay.R;
import com.oblivion.googleplay.base.BaseFragment;
import com.oblivion.googleplay.factory.FragmentFactory;
import com.oblivion.googleplay.utils.LogUtils;
import com.oblivion.googleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.main_drawerlayout)
    DrawerLayout mainDrawerlayout;
    @Bind(R.id.main_tabStrip)
    PagerSlidingTabStripExtends mainTabStrip;
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    private ActionBar actionBar;
    private ActionBarDrawerToggle mToggle;
    private FragmentStatePagerAdapter adapter;
    private BaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        final String[] strings = UIUtils.getStrings(R.array.main_titles);
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            /**
             * 返回的条目
             * @param position
             * @return
             */
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = FragmentFactory.create(position);
                return fragment;
            }

            /**
             * 设定数组长度
             * @return
             */
            @Override
            public int getCount() {
                return strings.length;
            }

            /**
             * 标签所需要的返回标题
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return strings[position];
            }
        };
        //设置适配器
        mainViewpager.setAdapter(adapter);
        //标签绑定ViewPager
        mainTabStrip.setViewPager(mainViewpager);
        mainTabStrip.setOnPageChangeListener(listener);
        //获取到首页，并加载数据
        mainViewpager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这里ViewPager已经更新完毕，可以获取更新数据------>更新数据
                FragmentFactory.map.get(mainViewpager.getCurrentItem()).getmLoadingPager().changeState();
                //取消掉View树监听
                mainViewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /**
     * 通过页面改变的监听设定加载数据
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //获取到点击位置所在的对象
            fragment = FragmentFactory.map.get(position);
            //由于在这里并没有初始化好Fragment
            fragment.getmLoadingPager().changeState();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 初始化标题栏
     */
    private void initActionBar() {
        actionBar = getSupportActionBar();
        //设置了ActionBar就不能设置NoTitleBar-------------
        actionBar.setTitle("GooglePlay");
        actionBar.setIcon(R.drawable.ic_launcher);
        //如果同时设置了logo下边的属性是将Logo优先显示
//        actionBar.setLogo(R.drawable.ic_launcher);
//        actionBar.setDisplayUseLogoEnabled(true);
        //设置显示Icon
        actionBar.setDisplayShowHomeEnabled(true);
        //设置显示回退键
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToggle = new ActionBarDrawerToggle(this, mainDrawerlayout, R.string.open, R.string.close);
        mToggle.syncState();
        mainDrawerlayout.setDrawerListener(mToggle);
    }

    /**
     * ActionBar的back按钮点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                LogUtils.s("执行了吗");
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
