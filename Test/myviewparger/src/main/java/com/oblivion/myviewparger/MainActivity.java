package com.oblivion.myviewparger;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int MESSAGE = 0;
    private ViewPager vp_abs;
    private myPargerAdapter adapter;
    private int[] imageResIds = {
            R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d,
            R.mipmap.e,
    };

    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };
    private LinearLayout ll_desc;
    private LinearLayout.LayoutParams layoutParams;
    private TextView tv_desc;
    private myOnPageChangeListener listener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE:
                    vp_abs.setCurrentItem(vp_abs.getCurrentItem() + 1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        listener = new myOnPageChangeListener();
        vp_abs.addOnPageChangeListener(listener);
        adapter = new myPargerAdapter(imageResIds);
        vp_abs.setAdapter(adapter);
        initdot();
        //将初始位置设定为适配器getCount()设定值的一半；
        vp_abs.setCurrentItem(adapter.getCount() / 2);
        int position = (adapter.getCount() / 2) % descs.length;
        tv_desc.setText(descs[position]);
        ll_desc.getChildAt(position).setSelected(true);
        //延时发送3秒发送消息
        handler.sendEmptyMessageDelayed(MESSAGE, 3000);
    }

    /**
     * 初始化控件ID
     */
    private void initView() {
        ll_desc = (LinearLayout) findViewById(R.id.ll_desc);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        vp_abs = (ViewPager) findViewById(R.id.vp_ads);
    }

    private void changeDot8Desc() {
        //获取ll_desc ViewGroup 中的子控件个数，遍历并将当前ViewParger的条目所在的点设置为选中的
        for (int i = 0; i < imageResIds.length; i++) {
            //设置当前文本为所选条目的文本信息
            tv_desc.setText(descs[vp_abs.getCurrentItem() % 5]);
            if (i == (vp_abs.getCurrentItem() % 5)) {
                //将所选的点点亮
                ll_desc.getChildAt(i).setSelected(true);
            } else {
                //其他点设定为灭
                ll_desc.getChildAt(i).setSelected(false);
            }
        }
    }

    /**
     * 创建ViewParger的滑动监听
     */
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //滑动状态为空闲状态时修改UI
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    changeDot8Desc();
                    //当滑动监听到之后，为防止消息干扰，先清除一下消息
                    handler.removeCallbacksAndMessages(null);
                    if (handler != null) {
                        //发送延时消息
                        handler.sendEmptyMessageDelayed(MESSAGE, 3000);
                    }
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    //当条目为选择状态不能发送消息
                    handler.removeCallbacksAndMessages(null);
                    break;
            }
        }
    }

    /**
     * 根据图片的int[] 集合的长度获取要创建的点
     */
    private void initdot() {
        for (int i = 0; i < imageResIds.length; i++) {
            //获取px,并且将点属性设定好
            int _npx = dp2px(5);
            layoutParams = new LinearLayout.LayoutParams(_npx, _npx);
            layoutParams.leftMargin = _npx;
            View dot = new View(this);
            dot.setBackgroundResource(R.drawable.selector_dot);
            dot.setLayoutParams(layoutParams);
            //将点添加到布局文件中
            ll_desc.addView(dot);
        }
    }

    /**
     * 将要设定的dp值转化为px
     *
     * @param dp 传入的dp值
     * @return px
     */
    public int dp2px(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        //0.5f是为了对获取的float值进行四舍五入
        return (int) (dp * density + 0.5f);
    }

    @Override
    protected void onDestroy() {
        //为防止Activity穿透，在销毁之前清除消息
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
