package com.oblivion.redchildpuls.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.jauker.widget.BadgeView;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.oblivapi.CartApi;
import com.oblivion.redchildpuls.adapter.HomePagerAdapter;
import com.oblivion.redchildpuls.bean.CartResponse;
import com.oblivion.redchildpuls.event.EventBadgeView;
import com.oblivion.redchildpuls.event.EventToCart;
import com.oblivion.redchildpuls.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.home_viewpager)
    NoScrollViewPager homeViewpager;
    @Bind(R.id.rg_select)
    RadioGroup rgSelect;
    @Bind(R.id.main_button)
    Button testButton;
    @Bind(R.id.main_drawerLayout)
    DrawerLayout mMainDrawerLayout;
    @Bind(R.id.navigation)
    NavigationView mNavigation;
    private BadgeView badgeView;
    //  @Bind(R.id.home_title_tv)
    //TextView homeTitleTv;


    public NoScrollViewPager getHomeViewpager() {
        return homeViewpager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        homeViewpager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        homeViewpager.addOnPageChangeListener(listener);
        rgSelect.setOnCheckedChangeListener(checkedChangeListener);
        badgeView = new BadgeView(this);
        // RadioButton targetView = (RadioButton) findViewById(R.id.rb_pay);
        badgeView.setTargetView(testButton);
        badgeView.setBadgeCount(3);
        //首页加载
        // loadData(0);
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_usercenter:
                        Toast.makeText(MainActivity.this, "账号中心", Toast.LENGTH_SHORT).show();
                        System.out.println("SB");
                        break;
                    case R.id.main_browse:
                        //Toast.makeText(MainActivity.this, "订单中心", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                        break;
                    case R.id.main_site:
                       // Toast.makeText(MainActivity.this, "地址管理", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),SelectLocalActivity_.class));
                        break;
                    case R.id.main_help:
                     //   Toast.makeText(MainActivity.this, "帮助中心", Toast.LENGTH_SHORT).show();
                        //跳转到帮助中心
                        Intent intent = new Intent(MyApplication.mContext, HelpCenterActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.main_feedback:
                        //Toast.makeText(MainActivity.this, "用户反馈", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
                        break;
                    case R.id.main_redbaby:
                       // Toast.makeText(MainActivity.this, "关于红孩子", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MyApplication.mContext, AboutActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.main_collect:
                        //是实打实大苏打似的
                        Intent i = new Intent(MyApplication.mContext, CollectActivity.class);
                        startActivity(i);
                        break;
                    case R.id.main_quit://退出当前用户
                        //对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("确认退出吗?");
                        builder.setNegativeButton("切换用户", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        });
                        builder.setPositiveButton("直接退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                        break;
                }
                return false;
            }
        });
        /**
         * 请求数据，小红点
         */
        CartApi.getCheckoutData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                CartResponse cartResponse = (CartResponse) response;
                int size = cartResponse.cart.size();
                badgeView.setBadgeCount(size);
                badgeView.invalidate();
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(MainActivity.this, "客官，请检查一下网络", Toast.LENGTH_SHORT).show();
            }
        }, MyApplication.stringBuffer.toString());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getEventBus(EventBadgeView eventBadgeView) {
        badgeView.setBadgeCount(eventBadgeView.counts);
        System.out.println(eventBadgeView.counts + "-counts-");
        badgeView.invalidate();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getEventDetailBus(EventToCart event) {
        /**
         * 请求数据，小红点
         */
        CartApi.getCheckoutData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                CartResponse cartResponse = (CartResponse) response;
                int size = cartResponse.cart.size();
                badgeView.setBadgeCount(size);
                badgeView.invalidate();
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(MainActivity.this, "客官，请检查一下网络", Toast.LENGTH_SHORT).show();
            }
        }, MyApplication.stringBuffer.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * ViewPager变化的监听
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //加载数据
            //loadData(position);
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

   /* *//**
     * 创建Presenter用来加载数据
     * @param position
     *//*
    private void loadData(int position) {
        BasePresenter presenter = new BasePresenter((IView) FragmentFactory.getFragment(position));
        presenter.getDataFromModel();
    }*/

    /**
     * 点击选中ViewPager位置
     */
    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    //false是为了取消加载动画
                    homeViewpager.setCurrentItem(0, false);
                    //LOCK_MODE_UNLOCKED打开侧滑栏,LOCK_MODE_LOCKED_CLOSED关闭侧滑栏
                    mMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    break;
                case R.id.rb_search:
                    homeViewpager.setCurrentItem(1, false);
                    mMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rb_logo:
                    homeViewpager.setCurrentItem(2, false);
                    mMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rb_pay:
                    homeViewpager.setCurrentItem(3, false);
                    mMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rb_more:
                    homeViewpager.setCurrentItem(4, false);
                    mMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        mMainDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("确认退出吗?");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
