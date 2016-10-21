package com.oblivion.defineview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by oblivion on 2016/10/21.
 */
public class MyObjectAnimationUtil {
    public static void hide(View view) {
        animationDelayStart(view, 0, 0f, -180f);
    }

    public static void show(View view) {
        animationDelayStart(view, 0, -180f, 0f);
    }

    public static void hide(View view, long delay) {
        animationDelayStart(view, delay, 0f, -180f);
    }

    public static void show(View view, long delay) {
        animationDelayStart(view, delay, -180f, 0f);

    }

    /**
     * 抽取动画效果
     *
     * @param view       需要设定动画的控件
     * @param delay      延时时间
     * @param startAngle 开始的角度
     * @param endAngle   结束的角度
     */
    private static void animationDelayStart(View view, long delay, float startAngle, float endAngle) {
        view.setPivotX(view.getWidth() / 2);//通过设定X旋转点将图片以这个点旋转
        view.setPivotY(view.getHeight());//通过设定Y旋转点将图片以这个点旋转
        //以设定的旋转点进行转动
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", startAngle, endAngle);
        //设定延时
        objectAnimator.setStartDelay(delay);
        //设定动画时间
        objectAnimator.setDuration(500);
        //设定动画监听事件
        objectAnimator.addListener(new myListener());
        //开始动画
        objectAnimator.start();
    }

    //定义一个 值用来记录正在运行的动画的数量
    private static int count = 0;
    //如果动画数量大于0,那就在调用的时候return;
    public static boolean getCount() {
        return count > 0;
    }

    /**
     * 动画的监听事件，重写他的四个方法
     */
    private static class myListener implements Animator.AnimatorListener {


        @Override
        public void onAnimationStart(Animator animation) {
            count++;//如果开始动画count++
    }

        @Override
        public void onAnimationEnd(Animator animation) {
            count--;//如果结束动画count++
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
