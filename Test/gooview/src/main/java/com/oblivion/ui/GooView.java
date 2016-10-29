package com.oblivion.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.oblivion.utils.GeometryUtil;

/**
 * 有一点没有处理掉的地方，当在回调的时候，如果恢复不清楚状态的话，在这个动画过程中再次滑动会出现BUG
 * Created by oblivion on 2016/10/28.
 */
public class GooView extends View {
    /**
     * 固定圆圆心
     */
    private PointF mStickCenter;
    /**
     * 固定圆半径
     */
    private float mStickRodius;
    /**
     * 触摸事件按下X点
     */
    private float moveX_dot;
    /**
     * 初始化移动圆心
     */
    private PointF mDragCenter = new PointF(100f, 200f);
    /**
     * 触摸事件按下y点
     */
    private float moveY_dot;
    /**
     * 主画笔
     */
    private Paint paint;
    /**
     * 固定的外圆环半径
     */
    private float assetsAadius;
    /**
     * 超出外圆环距离之后设定为true,默认为false;
     */
    private boolean beyondDistance = false;
    /**
     * 移动圆的圆心半径
     */
    private float mDragRodius;
    /**
     * 手指按下x点
     */
    private float downX_dot;
    /**
     * 手指按下y点
     */
    private float downY_dot;
    /**
     * 抬起点距离圆心距离
     */
    private float up2stickDistance;
    /**
     * 移动点距离圆心距离
     */
    private float move2stickDistance = 0;
    /**
     * 按下点距离固定圆心点距离
     */
    private float distanceBetween2Points;
    /**
     * 抬起点坐标
     */
    private PointF up_pointF;
    /**
     * 设定是否消失，默认为false
     */
    private boolean cleanAllDraw = false;
    /**
     * 回调监听接口
     */
    private onMessageStateListener monMessageStateListener;

    /**
     * 定义粘性控件三种状态
     */
    private enum State {
        remove, retain, touch
    }

    /**
     * 初始状态为保留状态
     */
    private State state = State.retain;

    public GooView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 移动圆圆心get方法
     *
     * @return
     */
    public PointF getmDragCenter() {
        return mDragCenter;
    }

    /**
     * 移动圆圆心set方法
     *
     * @param mDragCenter
     */
    public void setmDragCenter(PointF mDragCenter) {
        this.mDragCenter = mDragCenter;
    }

    private Canvas canvas;

    /**
     * 绘图，作画
     *
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        //设定抗锯齿画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔颜色
        paint.setColor(Color.BLUE);
        //固定圆的点
        mStickCenter = new PointF(100f, 200f);
        //固定的圆环半径
        assetsAadius = 100f;
        //固定圆的点半径
        if (distanceBetween2Points > assetsAadius) {
            mStickRodius = 4f;
        } else {
            mStickRodius = 10f - 6f * move2stickDistance / assetsAadius;
        }
        //画固定圆框
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mStickCenter.x, mStickCenter.y, assetsAadius, paint);
        paint.setStyle(Paint.Style.FILL);
        //移动圆的圆心
        mDragCenter = getmDragCenter();
        //移动圆半径
        mDragRodius = 15f;
        //是否不再作画
        if (!cleanAllDraw) {
            //画出移动圆
            canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRodius, paint);
            if (!beyondDistance) {//如果超出方位不再画贝塞尔曲线和原点
                //贝塞尔曲线绘制
                upDraw(canvas, paint);
                //得到当前移动圆圆心与

                //画固定圆
                canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRodius, paint);
            }
            //设定文本画笔
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(10f);
            //画固定圆上边写上文本...
            canvas.drawText("A", mStickCenter.x, mStickCenter.y + mStickRodius * 0.3f, textPaint);
            //画移动圆,并在上边写上文本...
            canvas.drawText("B", mDragCenter.x, mDragCenter.y + mDragRodius * 0.3f, textPaint);
        }
        //重新设置为false;
        beyondDistance = false;
        cleanAllDraw = false;
    }

    /**
     * 根据传入的移动圆的圆心开始作贝塞尔曲线,不管怎样画，最终的画会覆盖之前的画
     *
     * @param canvas 画布
     * @param paint  画笔
     */
    private void upDraw(Canvas canvas, Paint paint) {
        //测到测量的两点圆中心点
        PointF mMiddlePoint = GeometryUtil.getMiddlePoint(mStickCenter, mDragCenter);
        //获得两圆圆心连线的斜率；
        float yOffset = mStickCenter.y - mDragCenter.y;
        float xOffset = mStickCenter.x - mDragCenter.x;
        Double lineK = null;
        if (xOffset != 0) {
            lineK = (double) (yOffset / xOffset);
        }
        //****
        paint.setColor(Color.RED);
        //得到固定圆的两个附着点
        PointF[] mStickPoints = GeometryUtil.getIntersectionPoints(mStickCenter, mStickRodius, lineK);
        //得到移动圆的两个附着点
        PointF[] mDragPoints = GeometryUtil.getIntersectionPoints(mDragCenter, mDragRodius, lineK);
        //画出求出的四个点(测试用)
        canvas.drawCircle(mStickPoints[0].x, mStickPoints[0].y, 2f, paint);
        canvas.drawCircle(mStickPoints[1].x, mStickPoints[1].y, 2f, paint);
        canvas.drawCircle(mDragPoints[0].x, mDragPoints[0].y, 2f, paint);
        canvas.drawCircle(mDragPoints[1].x, mDragPoints[1].y, 2f, paint);
        paint.setColor(Color.BLUE);
        //绘制路径
        Path path = new Path();
        //移动到这个点
        path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
        //画一半贝塞尔曲线
        path.quadTo(mMiddlePoint.x, mMiddlePoint.y, mDragPoints[0].x, mDragPoints[0].y);
        //画出直线
        path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
        //画另一半贝塞尔曲线
        path.quadTo(mMiddlePoint.x, mMiddlePoint.y, mStickPoints[1].x, mStickPoints[1].y);
        //封闭路径
        path.close();
        //根据设定路径作画
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://记录按下点，作为移动圆圆心起始点
                downX_dot = event.getX();
                downY_dot = event.getY();
                PointF down_PointF = new PointF(downX_dot, downY_dot);
                //如果按下点的距离大于外圆环
                distanceBetween2Points = GeometryUtil.getDistanceBetween2Points(down_PointF, mStickCenter);
                setmDragCenter(new PointF(downX_dot, downY_dot));
                invalidate();
                state = State.touch;//状态修改为触摸状态
                setStateCallBack();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX_dot = event.getX();
                moveY_dot = event.getY();
                //移动点所在坐标
                PointF move_pointF = new PointF(moveX_dot, moveY_dot);
                //抬起点与原点的距离
                move2stickDistance = GeometryUtil.getDistanceBetween2Points(move_pointF, mStickCenter);
                //判断移动点是否超出范围
                beyondDistance = move2stickDistance > assetsAadius ? true : false;
                setmDragCenter(new PointF(moveX_dot, moveY_dot));
                invalidate();
                setStateCallBack();
                break;
            case MotionEvent.ACTION_UP:
                float upX_dot = event.getX();
                float upY_dot = event.getY();
                //抬起点所在坐标
                up_pointF = new PointF(upX_dot, upY_dot);
                //抬起点与原点的距离
                up2stickDistance = GeometryUtil.getDistanceBetween2Points(up_pointF, mStickCenter);
                //判断是否移动了
                if (move2stickDistance > assetsAadius) {
                    cleanAllDraw = true;
                    invalidate();
                    state = State.remove;//清除状态
                } else {
                    //开始动画
                    setAnimator();
                    //设定回到默认值
                    cleanAllDraw = false;
                    state = State.retain;//保留状态
                }
                move2stickDistance = 0;
                setStateCallBack();
                break;
        }
        return true;
    }

    /**
     * 设定值动画
     */
    private void setAnimator() {
        ValueAnimator va = ValueAnimator.ofFloat(1.0f);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                //获取手指离开点的距离
                PointF pointByPercent = GeometryUtil.getPointByPercent(up_pointF, mStickCenter, animatedFraction);
                //动态设定距离
                setmDragCenter(pointByPercent);
                invalidate();
                System.out.println(animatedFraction);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = State.retain;//保留状态
            }
        });
        //重力回弹
        va.setInterpolator(new OvershootInterpolator(4));
        //时长
        va.setDuration(500);
        va.start();
    }

    /**
     * 创建粘性控件的消息监听
     */
    public void setOnMessageStateListener(onMessageStateListener listener) {
        monMessageStateListener = listener;
    }

    /**
     * 创建消息监听的回调
     */
    public interface onMessageStateListener {
        /**
         * 正在操作消息
         */
        void onTouchMessage();

        /**
         * 保留消息
         */
        void onRetainMessage();

        /**
         * 清除消息
         */
        void onRemoveMessage();
    }

    /**
     * 响应接口回调
     */
    private void setStateCallBack() {
        if (monMessageStateListener != null) {

            if (state == State.retain) {
                monMessageStateListener.onRetainMessage();
            } else if (state == State.remove) {
                monMessageStateListener.onRemoveMessage();
            } else {
                monMessageStateListener.onTouchMessage();
            }
        }
    }
}
