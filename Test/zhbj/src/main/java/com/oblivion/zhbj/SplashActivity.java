package com.oblivion.zhbj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.oblivion.zhbj.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {
    private ImageView iv_spalsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_spalsh = (ImageView) findViewById(R.id.iv_splash);
        initAnimation();
    }

    /**
     * spalsh界面动画
     */
    private void initAnimation() {
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        //双方动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 45f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        //设置动画集合
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        //开启动画集合
        iv_spalsh.startAnimation(animationSet);
        //创建Listener 当动画完成后进入引导界面
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //判断是不是第一次进入主导界面
                boolean guide = PrefUtils.getBoolean(getApplicationContext(), "guide", false);
                if (guide) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    //进入引导界面
                    startActivity(new Intent(getApplicationContext(), GuideActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
