package com.oblivion.slideviewdrag;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_pic;
    private ListView lv_names;
    private ListView lv_randomnames;
    private com.oblivion.slideviewdrag.SlideViewDrag ssd_drag;
    private TextView tv_wocao;
private com.oblivion.slideviewdrag.IntentceptLineaLayout sill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        lv_names = (ListView) findViewById(R.id.lv_names);
        lv_randomnames = (ListView) findViewById(R.id.lv_randomnames);
        tv_wocao = (TextView) findViewById(R.id.tv_wocao);
        ssd_drag = (SlideViewDrag) findViewById(R.id.ssd_drag);
        /**监听回调*/
        ssd_drag.setOnDragStateListener(new SlideViewDrag.OnDragStateListener() {
            /**
             * 监听滑动状态
             */
            @Override
            public void OnDragState(float fraction) {
                ToastUtils.setToast(getApplicationContext(), "drag" + fraction);//动态获取到了fraction 的改变
                tv_wocao.setText("卧槽，我被滑动了？");
                ViewHelper.setAlpha(iv_pic, ssd_drag.evaluate(fraction, 1.0f, 0.0f));//有nineOldAndroid.jar文件中方法获取
            }

            /**
             * 监听开启状态
             */
            @Override
            public void OnOpenState() {
                ToastUtils.setToast(getApplicationContext(), "open");
                tv_wocao.setText("卧槽，我被打开了？");
            }

            /**
             * 监听关闭状态
             */
            @Override
            public void OnCloseState() {
                ToastUtils.setToast(getApplicationContext(), "close");
                tv_wocao.setText("卧槽，我被关闭了？");
                // iv_pic.setTranslationX();
                //设置属性动画。。。和差值动画
                ObjectAnimator moObjectAnimator = ObjectAnimator.ofFloat(iv_pic, "translationX", -10.0f, 10.0f);
                moObjectAnimator.setDuration(300);
                moObjectAnimator.setInterpolator(new CycleInterpolator(3));
                moObjectAnimator.start();
            }
        });
        //设定内部的字体颜色
        lv_randomnames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings) {
            @Override
            /**
             * 修改字体颜色
             */
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.WHITE);//强转成TextView 并设定字体颜色
                return view;
            }
        });
        lv_names.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Cheeses.NAMES) {
        });
        sill = (IntentceptLineaLayout) findViewById(R.id.sill);
        sill.setSlideViewDrag(ssd_drag);
    }

}
