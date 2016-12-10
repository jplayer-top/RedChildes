package com.oblivion.redchildpuls.zxq.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.activity.FilterActivity;
import com.oblivion.redchildpuls.view.MyTitleBar;
import com.oblivion.redchildpuls.zxq.presenter.SearchResultPresenter;

import org.senydevpkg.utils.ALog;
import org.senydevpkg.utils.MyToast;

/**
 * Created by arkin on 2016/12/7.
 */
public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY_REQUEST_CONTENT = "searchContent";
    public static final String KEY_REQUEST_CODE = "request_code";
    public static final int VALUE_SEARCH = 250;
    public static final int VALUE_PRODUCT = 251;
    public static final int VALUE_FILTER = 252;
    public static final int VALUE_BRAND = 253;


    private RadioGroup mSearchresultRg;
    private RadioButton mSearchresultRbSales;
    private RadioButton mSearchresultRbMyprice;
    private ImageView mSearchresultIvArrow;
    private RadioButton mSearchresultRbScore;
    private RadioButton mSearchresultRbDate;
    private MyTitleBar mSearchresultMytiltebar;
    private ViewPager mSearchresultViewpager;
    private ViewStub myViewstub;


    // 搜索结果界面的“搜索信息”是固定的
    private String mSearchContent;
    private SparseArray<SearchResultFragment> fragments;
    private ViewPager.SimpleOnPageChangeListener mOnPageChangeListener;
    private @SearchResultPresenter.OrderBy String orderBy = SearchResultPresenter.SALE_DOWN;
    private boolean isPriceDown = true;// 记录是否是“价格降序”
    private RotateAnimation mRotateUp;
    private RotateAnimation mRotateDown;
    private FrameLayout mSearchresultFlArrowBg;
    private String filter;
    private int mRequestCode;
    public static final String FILTER_URL = "filterUrl";;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取开启该Activity时intent携带的数据，进行网络请求
        mRequestCode = getIntent().getIntExtra(KEY_REQUEST_CODE, 0);
        ALog.i("搜索结果/商品列表请求码："+mRequestCode);
        mSearchContent = getIntent().getStringExtra(KEY_REQUEST_CONTENT);

        setContentView(R.layout.activity_searchresult);

        onInit();

        switch (mRequestCode) {
            case VALUE_SEARCH:
            case VALUE_BRAND:
                mSearchresultMytiltebar.setFilterButtonVisibility(false);
                break;
            case VALUE_PRODUCT:
                mSearchresultMytiltebar.setFilterButtonVisibility(true);
                mSearchresultMytiltebar.addOnFilterClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent starter = new Intent(SearchResultActivity.this, FilterActivity.class);
                        SearchResultActivity.this.startActivityForResult(starter, VALUE_FILTER);
                    }
                });
                break;
            default:
                MyToast.show(MyApplication.mContext, "数据异常");
                finish();
                break;
        }


        onListener();
    }


    protected void onInit() {
        mSearchresultMytiltebar = (MyTitleBar) findViewById(R.id.searchresult_mytiltebar);

        mSearchresultRg = (RadioGroup) findViewById(R.id.searchresult_rg);
        mSearchresultRbSales = (RadioButton) findViewById(R.id.searchresult_rb_sales);
        mSearchresultRbMyprice = (RadioButton) findViewById(R.id.searchresult_rb_myprice);
        mSearchresultIvArrow = (ImageView) findViewById(R.id.searchresult_iv_arrow);
        mSearchresultRbScore = (RadioButton) findViewById(R.id.searchresult_rb_score);
        mSearchresultRbDate = (RadioButton) findViewById(R.id.searchresult_rb_date);
        mSearchresultViewpager = (ViewPager) findViewById(R.id.searchresult_viewpager);
        mSearchresultFlArrowBg = (FrameLayout) findViewById(R.id.searchresult_fl_arrow_bg);

        initArrowAnim();

        fragments = new SparseArray<SearchResultFragment>();
        for (int i = 0; i < 4; i++) {
            SearchResultFragment fragment = new SearchResultFragment();
            Bundle args = new Bundle();
            args.putString("searchInfo", mSearchContent);// 设置当前fragment的搜索内容
            args.putInt("fragId", i);// 设置当前fragment的id
            args.putInt("requestCode", mRequestCode);
            fragment.setArguments(args);
            fragments.put(i, fragment);
        }

        FragmentManager fm = getSupportFragmentManager();
        SearchResultViewPagerAdapter pagerAdapter = new SearchResultViewPagerAdapter(fm, fragments);
        mSearchresultViewpager.setAdapter(pagerAdapter);

    }

    protected void onListener() {
        mSearchresultRbSales.setOnClickListener(this);
        mSearchresultRbMyprice.setOnClickListener(this);
        mSearchresultRbScore.setOnClickListener(this);
        mSearchresultRbDate.setOnClickListener(this);
        mSearchresultIvArrow.setOnClickListener(this);
        mSearchresultMytiltebar.addOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchresultRg.check(R.id.searchresult_rb_sales);

        mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTabState(position); // 修改Tab的状态
                fragments.get(position).loadData(mSearchContent, 1,10, orderBy, filter, mRequestCode);

            }
        };

        mSearchresultViewpager.addOnPageChangeListener(mOnPageChangeListener);

    }

    @Override
    // 按钮的点击事件（切换viewpager）
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchresult_rb_sales:
                ALog.e("点击"+SearchResultPresenter.SALE_DOWN);

                mSearchresultViewpager.setCurrentItem(0, true);
                break;
            case R.id.searchresult_rb_myprice:
                ALog.e("点击"+SearchResultPresenter.PRICE_DOWN);

                mSearchresultViewpager.setCurrentItem(1, true);
                break;
            case R.id.searchresult_rb_score:
                ALog.e("点击"+SearchResultPresenter.COMMENT_DOWN);

                mSearchresultViewpager.setCurrentItem(2, true);
                break;
            case R.id.searchresult_rb_date:
                ALog.e("点击"+SearchResultPresenter.SHELVES_DOWN);

                mSearchresultViewpager.setCurrentItem(3, true);
                break;

            case R.id.searchresult_iv_arrow:
                ALog.e("点击"+SearchResultPresenter.PRICE_UP);

                if (mSearchresultViewpager.getCurrentItem() == 1) {
                    orderBy = isPriceDown? SearchResultPresenter.PRICE_UP: SearchResultPresenter.PRICE_DOWN;
                    fragments.get(1).loadData(mSearchContent, 1 ,10, orderBy, filter, mRequestCode);
                } else {
                    mSearchresultViewpager.setCurrentItem(1, true);
                }
                isPriceDown = !isPriceDown;
                performArrowAnim(isPriceDown);

                break;
            default:
                break;
        }
    }

    // 根据viewpager的当前页面修改Tab的状态
    private void changeTabState(int position) {
        ALog.e(mSearchresultRbMyprice.isChecked()+"是否选中。。。。。。");
        int radiobuttonId = -1;
        switch (position) {
            case 0:
                radiobuttonId = R.id.searchresult_rb_sales;
                orderBy = SearchResultPresenter.SALE_DOWN;
                break;
            case 1:
                radiobuttonId = R.id.searchresult_rb_myprice;
                /*orderBy = isPriceDown? SearchResultPresenter.PRICE_UP: SearchResultPresenter.PRICE_DOWN;
                isPriceDown = !isPriceDown;*/
                orderBy = SearchResultPresenter.PRICE_DOWN;
                isPriceDown = true;
                // performArrowAnim(isPriceDown);
                break;
            case 2:
                radiobuttonId = R.id.searchresult_rb_score;
                orderBy = SearchResultPresenter.COMMENT_DOWN;

                break;
            case 3:
                radiobuttonId = R.id.searchresult_rb_date;
                orderBy = SearchResultPresenter.SHELVES_DOWN;

                break;
            default:
                break;
        }
        mSearchresultRg.check(radiobuttonId);

        if (position == 1) {
            mSearchresultIvArrow.setImageResource(R.drawable.bitmap_arrow_white);// 当Tab"价格"被选中，箭头变为白色
            mSearchresultFlArrowBg.setBackgroundResource(R.drawable.segment_selected_2_bg);
        } else {
            mSearchresultIvArrow.setImageResource(R.drawable.arrow1);// 当Tab"价格"未选中，箭头变为红色
            mSearchresultFlArrowBg.setBackgroundResource(R.drawable.segment_normal_2_bg);
        }
    }

    private void initArrowAnim() {
        // 初始化arrow的动画
        mRotateUp = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUp.setDuration(500);
        mRotateUp.setFillAfter(true);
        mRotateDown = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDown.setDuration(500);
        mRotateDown.setFillAfter(true);
    }

    private void performArrowAnim(boolean isPriceDown) {
        Animation animation = isPriceDown? mRotateDown : mRotateUp;
        mSearchresultIvArrow.startAnimation(animation);
    }

    /**
     * 初始化标题栏
     * @param content
     */
    public void initTitleBar(String content){
        mSearchresultMytiltebar.setTitle(content);
    }

    class SearchResultViewPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<SearchResultFragment> fragments;

        public SearchResultViewPagerAdapter(FragmentManager fm, SparseArray<SearchResultFragment> fragSets) {
            super(fm);

            fragments = fragSets;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchresultViewpager.removeOnPageChangeListener(mOnPageChangeListener);

    }

    @Override
    /**
     * 在该方法中接收filteractivity回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case VALUE_FILTER:
                mSearchresultMytiltebar.setFilterButtonVisibility(false);
                String filterUrl = data.getStringExtra(FILTER_URL);// 获取filteractivity传递过来的filter参数
                int currentItem = mSearchresultViewpager.getCurrentItem();// 获取当前展示的界面索引
                mRequestCode = VALUE_FILTER;
                fragments.get(currentItem).loadData(mSearchContent, 1 ,10, orderBy, filterUrl, mRequestCode);
                break;
            default:
                break;
        }
    }

    public static void startBrandPage(Context context, int id) {
        Intent starter = new Intent(context, SearchResultActivity.class);
        starter.putExtra(SearchResultActivity.KEY_REQUEST_CONTENT, id+"");
        starter.putExtra(SearchResultActivity.KEY_REQUEST_CODE, SearchResultActivity.VALUE_BRAND);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

}
