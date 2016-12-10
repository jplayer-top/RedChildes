package com.oblivion.redchildpuls.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.oblivion.redchildpuls.MyApplication;
import com.oblivion.redchildpuls.R;
import com.oblivion.redchildpuls.RBConstants;
import com.oblivion.redchildpuls.activity.BrandActivity;
import com.oblivion.redchildpuls.activity.HotProductActivity;
import com.oblivion.redchildpuls.activity.LimitbuyActivity;
import com.oblivion.redchildpuls.activity.NewProductActivity;
import com.oblivion.redchildpuls.base.BaseFragment;
import com.oblivion.redchildpuls.bean.HomeViewPagerResponse;
import com.oblivion.redchildpuls.factory.FragmentFactory;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;
import org.senydevpkg.utils.DensityUtil;
import org.senydevpkg.utils.MyToast;

import java.util.List;

/**
 * github : https://github.com/oblivion0001/AndroidStudioProjects
 * Blog : http://blog.csdn.net/qq_16666847
 * Created by oblivion on 2016/12/3.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private EditText mHomeSearchInput;
    private Button mHomeSearchBtn;
    private RelativeLayout mRlXsqg;
    private RelativeLayout mRlCxkb;
    private RelativeLayout mRlXpsj;
    private RelativeLayout mRlRmdp;
    private RelativeLayout mRlTjpp;
    private LinearLayout mHomeLlDots;
    private SparseArray<ImageView> mViewSet = new SparseArray<>();// 轮播图图片集合
    private SparseArray<ImageView> mDotSet = new SparseArray<>();// 轮播图图片集合
    private ViewPager mViewPager;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    int position = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(++position);
                    // 发送延迟消息
                    this.sendEmptyMessageDelayed(1, 2000);
                    break;
                default:
                    break;
            }
        };
    };

    private Context mContext;
    private ViewPager.SimpleOnPageChangeListener onPageChangeListener;


    @Override
    public View initView() {
        mContext = MyApplication.mContext;
        View rootView = View.inflate(getContext(), R.layout.frag_home, null);

        mHomeSearchInput = (EditText) rootView.findViewById(R.id.home_search_input);
        ALog.e("heheh");
        mHomeSearchBtn = (Button) rootView.findViewById(R.id.home_search_btn);
        mRlXsqg = (RelativeLayout) rootView.findViewById(R.id.rl_xsqg);
        mRlCxkb = (RelativeLayout) rootView.findViewById(R.id.rl_cxkb);
        mRlXpsj = (RelativeLayout) rootView.findViewById(R.id.rl_xpsj);
        mRlRmdp = (RelativeLayout) rootView.findViewById(R.id.rl_rmdp);
        mRlTjpp = (RelativeLayout) rootView.findViewById(R.id.rl_tjpp);
        mViewPager = (ViewPager) rootView.findViewById(R.id.homefrag_viewpager_viewpager);
        mHomeLlDots = (LinearLayout) rootView.findViewById(R.id.home_ll_dots);

        mHomeSearchBtn.setOnClickListener(this);
        mRlXsqg.setOnClickListener(this);
        mRlCxkb.setOnClickListener(this);
        mRlXpsj.setOnClickListener(this);
        mRlRmdp.setOnClickListener(this);
        mRlTjpp.setOnClickListener(this);
        // 设置输入框enter键的监听
        mHomeSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mHomeSearchBtn.performClick();// 触发搜索键的点击事件，进行搜索
                    return true;
                }
                return false;
            }
        });

        // 初始化ViewPager
        for (int i = 0; i < 6; i++) {
            ImageView imageView = new ImageView(mContext);// 创建轮播图imageView对象
            imageView.setImageResource(R.drawable.product_loading);// 设置默认显示内容
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewSet.put(i, imageView);// 将imageView对象加入图片集合
            MyPagerAdapter adapter = new MyPagerAdapter(mViewSet);
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(adapter.getCount()/2);
        }

        // 初始化Indicator
        initDots();

        // 获取数据集合
        MyApplication.HL.get(RBConstants.URL_HOME_RECOMMEND, null, HomeViewPagerResponse.class, RBConstants.REQUEST_CODE_HOME_RECOMMEND, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                if (requestCode != RBConstants.REQUEST_CODE_HOME_RECOMMEND) {
                    return;
                }

                HomeViewPagerResponse data = (HomeViewPagerResponse) response;
                final List<HomeViewPagerResponse.HomeTopicBean> homeTopicDatas = data.getHomeTopic();

                StringBuilder stringBuilder = new StringBuilder(RBConstants.URL_SERVER);
                int startIndex = RBConstants.URL_SERVER.length() - 1;

                // 获取轮播图片
                for (int i = 0; i < homeTopicDatas.size(); i++) {
                    final ImageView imageView = mViewSet.get(i);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyToast.show(mContext, "点击"+homeTopicDatas.get(finalI).getTitle()+homeTopicDatas.get(finalI).getId());
                        }
                    });

                    String picUrl = stringBuilder.append(homeTopicDatas.get(i).getPic()).toString();// 拼接图片地址
                    ALog.i("首页轮播图地址：" + picUrl);

                    MyApplication.IL.get(picUrl, new ImageLoader.ImageListener() {// 加载图片
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            imageView.setImageBitmap(imageContainer.getBitmap());// 设置图片内容
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

                    stringBuilder.delete(startIndex, stringBuilder.length());// 清除图片地址
                }
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                ALog.e("首页网络请求失败");
            }
        });

        return rootView;
    }

    // 初始化ViewPager的indicator
    private void initDots() {
        for (int i = 0; i < mViewSet.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            if (i == 0) {
                img.setImageResource(R.drawable.home_classify_03);
            } else {
                img.setImageResource(R.drawable.home_classify_03_white);
            }
            int radis = DensityUtil.dip2px(mContext, 10);// dot的宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(radis, radis);
            int margin = DensityUtil.dip2px(mContext, 5);// dot的间距
            params.setMargins(margin, 0, 0, 0);

            // 加载到布局容器
            mHomeLlDots.addView(img, params);
            mDotSet.put(i,img);
        }
    }


    @Override
    public void onClick(View v) {
        Intent starter = null;
        switch (v.getId()) {
            case R.id.home_search_input:// 搜索输入框

                break;
            case R.id.home_search_btn:// 搜索按钮
                String searchInfo = mHomeSearchInput.getText().toString().trim();
                ((SearchFragment)FragmentFactory.getFragment(1)).startSearch(searchInfo);// 获取SearchFragment对象，实现搜索跳转
                mHomeSearchInput.setText("");//  复原输入框
                break;
            case R.id.rl_xsqg:// 限时抢购
                starter = new Intent(getActivity(), LimitbuyActivity.class);
                getActivity().startActivity(starter);
                break;
            case R.id.rl_cxkb:// 促销快报

                break;
            case R.id.rl_xpsj://新品上架
                starter = new Intent(getActivity(), NewProductActivity.class);
                getActivity().startActivity(starter);
                break;
            case R.id.rl_rmdp://热门单品
                starter = new Intent(getActivity(), HotProductActivity.class);
                getActivity().startActivity(starter);
                break;
            case R.id.rl_tjpp://推荐品牌
                starter = new Intent(getActivity(), BrandActivity.class);
                getActivity().startActivity(starter);
                break;
            default:
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private SparseArray<ImageView> viewDatas;

        public MyPagerAdapter(SparseArray<ImageView> viewDatas) {
            this.viewDatas = viewDatas;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position % viewDatas.size();
            ImageView imageView = viewDatas.get(realPosition);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(1, 2000);
                            break;
                    }
                    return false;
                }
            });

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return viewDatas.size() * 10000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
        mViewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(1, 2000);

        onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotSet.size(); i++) {
                    ImageView dot = mDotSet.get(i);
                    if (i == position % mViewSet.size()) {
                        dot.setImageResource(R.drawable.home_classify_03);
                    } else {
                        dot.setImageResource(R.drawable.home_classify_03_white);
                    }
                }
            }
        };
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
