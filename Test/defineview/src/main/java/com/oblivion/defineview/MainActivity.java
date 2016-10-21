package com.oblivion.defineview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rl_menu03)
    RelativeLayout rlMenu03;
    @Bind(R.id.bt_menu02)
    Button btMenu02;
    @Bind(R.id.rl_menu02)
    RelativeLayout rlMenu02;
    @Bind(R.id.bt_menu01)
    Button btMenu01;
    @Bind(R.id.rl_menu01)
    RelativeLayout rlMenu01;
    //设定三级联动的boolean
    private boolean isVisiable = true;
    private boolean is3Visiable = true;
    private boolean isMenuVisiable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 这是一种获取menu键的方式1
     * @param featureId
     * @param menu
     * @return
     */
   /* @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
        return true;
    }*/

    /**
     * 这是获取menu键的方式2
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                if (MyObjectAnimationUtil.getCount()) {
                    return false;
                }
                if (isMenuVisiable) {
                    //判断条目有几个
                    if (!is3Visiable) {
                        MyObjectAnimationUtil.hide(rlMenu02);
                        MyObjectAnimationUtil.hide(rlMenu01, 250);
                    } else if (!isVisiable) {
                        MyObjectAnimationUtil.hide(rlMenu01);
                    } else {
                        MyObjectAnimationUtil.hide(rlMenu03);
                        MyObjectAnimationUtil.hide(rlMenu02, 250);
                        MyObjectAnimationUtil.hide(rlMenu01, 500);
                    }
                } else {//如果flase 就将所有的条目显示出来
                    MyObjectAnimationUtil.show(rlMenu01);
                    MyObjectAnimationUtil.show(rlMenu02, 250);
                    MyObjectAnimationUtil.show(rlMenu03, 500);
                    //如果已经都隐藏设定所有的boolean值为true
                    isVisiable = true;
                    is3Visiable = true;
                }
                isMenuVisiable = !isMenuVisiable;
                break;
        }
        return true;
    }

    @OnClick({R.id.rl_menu03, R.id.bt_menu02, R.id.rl_menu02, R.id.bt_menu01, R.id.rl_menu01})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_menu02:
                if (MyObjectAnimationUtil.getCount()) {
                    return;
                }
                if (is3Visiable) {
                    //隐藏界面3
                    MyObjectAnimationUtil.hide(rlMenu03);
                } else {
                    //显示界面3
                    MyObjectAnimationUtil.show(rlMenu03);
                }
                is3Visiable = !is3Visiable;

                // MyAnimationUtils.hide();
                break;
            case R.id.bt_menu01:
                if (MyObjectAnimationUtil.getCount()) {
                    return;
                }
                if (isVisiable) {
                    //隐藏界面2，3
                    if (!is3Visiable) {
                        //如果界面三已经隐藏，只设定2隐藏
                        MyObjectAnimationUtil.hide(rlMenu02);
                        is3Visiable = !is3Visiable;
                        isVisiable = !isVisiable;
                        return;

                    }
                    MyObjectAnimationUtil.hide(rlMenu02, 250);
                    MyObjectAnimationUtil.hide(rlMenu03);
                } else {
                    //显示界面2，3

                    MyObjectAnimationUtil.show(rlMenu02);
                    MyObjectAnimationUtil.show(rlMenu03, 250);
                }
                isVisiable = !isVisiable;
                break;
        }
    }
}
