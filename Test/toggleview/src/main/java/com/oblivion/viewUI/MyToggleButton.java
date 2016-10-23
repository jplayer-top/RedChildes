package com.oblivion.viewUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by oblivion on 2016/10/22.
 */
public class MyToggleButton extends View {
    /**
     * 根据位图工厂获取开关背景Bitmap
     */
    private Bitmap slide_background;
    /**
     * 根据位图工厂获取开关按钮Bitmap
     */
    private Bitmap slide_icon;
    /**
     * 开关背景的长度
     */
    private int slide_backgroundWidth;
    /**
     * 开关背景的高度
     */
    private int slide_backgroundHeight;
    /**
     * 开关按钮的宽度
     */
    private int slide_iconWidth;
    /**
     * 开关按钮的高度
     */
    private int slide_iconHeight;
    /**
     * 设定初始化开关状态
     */
    private boolean switchState;
    /**
     * 获取所在图层范围的点击下的X坐标
     */
    private float down_dot;
    /**
     * 开关按钮在画布上X的起始画点
     */
    private int left;
    /**
     * 开关按钮在画布上Y的起始画点
     */
    private int top;
    /**
     * 抽取按钮在最右边的起始点
     */
    private int offLeftTop;
    /**
     * 定义回调接口
     */
    private OnStateChangeListener mOnStateChangeListener;

    /**
     * 重写带属性的方法,使用在布局文件中
     */
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int slide_iconResId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "slideIcon", -1);
        int slide_backgroundResId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "slideBackground", -1);
        switchState = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "switchState", true);
        slide_background = BitmapFactory.decodeResource(getResources(), slide_backgroundResId);
        slide_backgroundWidth = slide_background.getWidth();
        slide_backgroundHeight = slide_background.getHeight();
        slide_icon = BitmapFactory.decodeResource(getResources(), slide_iconResId);
        slide_iconWidth = slide_icon.getWidth();
        slide_iconHeight = slide_icon.getHeight();
    }

    /**
     * 重写new 使用的构造方法
     */
    public MyToggleButton(Context context) {
        super(context);
    }

    /**
     * 获取开关背景资源
     *
     * @param slide_backgroundResId 开关背景
     *//*
    public void setSlideBackground(int slide_backgroundResId) {
        slide_background = BitmapFactory.decodeResource(getResources(), slide_backgroundResId);
        slide_backgroundWidth = slide_background.getWidth();
        slide_backgroundHeight = slide_background.getHeight();
    }

    *//**
     * 获取开关按钮资源
     *
     * @param slide_iconResId 开关按钮资源
     *//*
    public void setSlideIcon(int slide_iconResId) {
        slide_icon = BitmapFactory.decodeResource(getResources(), slide_iconResId);
        slide_iconWidth = slide_icon.getWidth();
        slide_iconHeight = slide_icon.getHeight();
    }
*/

    /**
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = slide_backgroundWidth;
        int measureHeight = slide_backgroundHeight;
        offLeftTop = slide_backgroundWidth - slide_iconWidth;
        left = offLeftTop;
        setMeasuredDimension(measureWidth, measureHeight);
    }


    /**
     * 初始化画板
     *
     * @param canvas 画板
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(slide_background, 0, 0, null);
        //根据设定状态，画出初始化开关状态；
        canvas.drawBitmap(slide_icon, left, 0, null);
    }


    /**
     * 设定按钮图片的滑动事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下事件
                down_dot = event.getX();
                changeSwitchState();//触发状态改变方法
                return true;//将事件消耗掉
            case MotionEvent.ACTION_UP://手指抬起事件
                float up_dot = event.getX();//手指抬起的点
                if (up_dot < slide_backgroundWidth / 2) {
                    switchState = false;
                    left = 0;
                } else {
                    switchState = true;
                    left = offLeftTop;
                }
                /**
                 * 状态改变的监听事件
                 * @param switchState 监听到状态改变事件
                 */
                mOnStateChangeListener.OnStateChange(switchState);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE://手指移动事件
                float move_dot = event.getX();//手指移动的点
                float move2down = move_dot - down_dot;//手指移动点相比于初次按下的点
                float lefttopIcon = move_dot - slide_iconWidth / 2;
                if (move_dot < slide_iconWidth / 2) {
                } else if (move_dot > (slide_backgroundWidth - slide_iconWidth / 2)) {
                } else {
                    if (move2down > 0) {
                        if (lefttopIcon >= offLeftTop) {
                            left = offLeftTop;
                        } else {
                            left = (int) lefttopIcon;
                        }
                    } else {
                        if (lefttopIcon <= 0) {
                            left = 0;
                        } else {
                            left = (int) lefttopIcon;
                        }
                    }
                }
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据点击下的位置判断，修改开关状态
     */
    private void changeSwitchState() {

        if (switchState) {//如果是开启状态
            if (down_dot < slide_backgroundWidth / 2) {
                left = 0;//设定按钮画布的初始点为0
                switchState = !switchState;
            }
        } else {//如果是关闭状态
            if (down_dot > slide_backgroundWidth / 2) {
                left = offLeftTop;//设定按钮画布的初始点为offLeftTop
                switchState = !switchState;
            }
        }
        invalidate();//通知画布，重新画布局
    }

    /**
     * 自定义开关开启的状态
     *
     * @param switchState 开关状态
     */
    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
        invalidate();
    }

    /**
     * 创建回调接口
     */
    public interface OnStateChangeListener {
        void OnStateChange(boolean state);
    }

    /**
     * 创建监听器
     *
     * @param listener 回调接口
     */
    public void setOnStateChangeListener(OnStateChangeListener listener) {
        //类似与OnClickListener()方法中的回调方法，具体可看View中的点击监听的源码
        if (mOnStateChangeListener == null) {
            mOnStateChangeListener = listener;
        }

    }
}
