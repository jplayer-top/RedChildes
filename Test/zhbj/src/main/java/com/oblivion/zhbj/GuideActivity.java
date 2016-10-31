package com.oblivion.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.oblivion.zhbj.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oblivion on 2016/10/30.
 */
public class GuideActivity extends AppCompatActivity {
    private ViewPager vp_guide;
    private int[] imageViews;
    private List<ImageView> views;
    private LinearLayout ll_pint;
    private ImageView iv_red;
    private RelativeLayout rl_guide;
    private int ponit_dis;
    private Button bt_ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        System.out.println("执行了吗");
        initView();
        //渲染二叉树完成后，获取两点之间的值
        rl_guide.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ponit_dis = ll_pint.getChildAt(1).getLeft() - ll_pint.getChildAt(0).getLeft();
                //去除监听
                rl_guide.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        //添加ViewPage的滑动监听
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //这是滑动是冲开始到结束的分数值
                System.out.println(positionOffset);
                RelativeLayout.LayoutParams redParams =  (RelativeLayout.LayoutParams) iv_red.getLayoutParams();
                // 动态设定红点的左边距
                redParams.leftMargin = (int) (ponit_dis * positionOffset + position * ponit_dis);
                //请求重新布局
                iv_red.requestLayout();
            }

            @Override
            public void onPageSelected(int position) {
                bt_ok = (Button) findViewById(R.id.button_ok);
                if (position == 2) {
                    bt_ok.setVisibility(View.VISIBLE);
                    bt_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //只进入一次
                            PrefUtils.putBoolean(getApplicationContext(), "guide", true);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                } else {
                    bt_ok.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 当窗口获取焦点。在ViewTree之后
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && ponit_dis==0) {
            //重新获取点间距
            ponit_dis = ll_pint.getChildAt(1).getLeft() - ll_pint.getChildAt(0).getLeft();
        }
    }

    private void initView() {
        rl_guide = (RelativeLayout) findViewById(R.id.rl_guide);
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_pint = (LinearLayout) findViewById(R.id.ll_pintparams);
        iv_red = (ImageView) findViewById(R.id.iv_red_point);
        //初始化资源
        views = new ArrayList<>();
        imageViews = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < imageViews.length; i++) {
            //初始化View
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(imageViews[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(iv);
            //初始化灰点
            ImageView iv_pint = new ImageView(getApplicationContext());
            iv_pint.setBackgroundResource(R.drawable.shape_pint);
            //设定位置，点的宽高，以及左右间距，
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.width = 10;
            params.height = 10;
            if (i != 0) {//设定点间距
                params.leftMargin = 10;
            }
            //设定这个布局
            iv_pint.setLayoutParams(params);
            //加入到哪个容器中
            ll_pint.addView(iv_pint);
        }
        //初始化红点

        vp_guide.setAdapter(new mGuideViewPageAdapter());
    }

    private class mGuideViewPageAdapter extends PagerAdapter {

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
            //将初始化好的数据引用出来
            ImageView imageView = views.get(position);
            //别忘记添加到容器中
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁掉无用的View
            container.removeView((ImageView) object);
        }
    }
}
