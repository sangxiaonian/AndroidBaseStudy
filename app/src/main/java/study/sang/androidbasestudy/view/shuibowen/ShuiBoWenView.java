package study.sang.androidbasestudy.view.shuibowen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import study.sang.androidbasestudy.utils.JLog;


/**
 * android5.0 水波纹效果自定义控件,使用时候,用这个控件包裹需要波纹的控件
 * 作者： ${桑小年} on 2016/6/13.
 * 努力，为梦长留
 */
public class ShuiBoWenView extends LinearLayout {

    //被点击到的目标控件，就是要绘制水波纹的控件
    private View targetView;

    //要绘制水波纹的区域，即为被点击的控件的范围
    private RectF rectF;

    //要绘制的水波纹的最大半径
    private int radio;
    //水波纹的中心
    private double centerX;
    private double centerY;

    private Paint mPaint;

    private double currentRadio;

    //父控件的绝对坐标
    private int[] loaction;

    //是否进行绘制
    private boolean isDraw = false;
    //水波纹完成时间
    private long time = 500;
    //水波纹绘制次数
    private double times = time / 30;

    //圆心偏移量
    private double centerOffsetX;

    //圆心在X方向偏差绝对值
    private double gapX;

   //圆心在Y方向偏差绝对值
    private double gapY;

    private int color = Color.RED;

    public ShuiBoWenView(Context context) {
        super(context);
        initView();
    }


    public ShuiBoWenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShuiBoWenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //实心
        mPaint.setStyle(Paint.Style.FILL);
        //颜色
        mPaint.setColor(color);
        mPaint.setAlpha(50);
    }

    private long a;

    //重写触摸事件，每次down事件中，开始测量绘制
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                a = System.currentTimeMillis();
                //down事件中,开始绘制
                isDraw = true;

                currentRadio = 0;
                //点击的位置，也是水波纹的圆心
                int downX = (int) event.getRawX();
                int downY = (int) event.getRawY();

                centerX = downX;
                centerY = downY;

                //找到对应的子控件
                targetView = findTargerView(downX, downY);

                //如果没有子控件被点击，就直接结束
                if (targetView != null) {
                    //矫正绘制区域
                    clipRectf();
                    gapX =  (centerX-(rectF.left+rectF.right)/2);
                    gapY =  (centerY-(rectF.top+rectF.bottom)/2);
                    //获取半径
                    radio = (int) Math.max(rectF.width(),rectF.height())/2;
                    centerOffsetX = (gapX*1.0 / (times));
                    //重新绘制
                    postInvalidate();

                }
                break;
            default:
                isDraw = false;
                currentRadio = 0;
                postInvalidate();
                JLog.i((System.currentTimeMillis() - a) + "");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    //划定区域
    private void clipRectf() {
        loaction = new int[2];
        getLocationOnScreen(loaction);
        rectF.top = rectF.top - loaction[1];
        rectF.bottom = rectF.bottom - loaction[1];
        rectF.left -= loaction[0];
        rectF.right -= loaction[0];

        centerX -= loaction[0];
        centerY -= loaction[1];


    }

    //在draw中被调用的方法，用来绘制子控件
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        //子控件绘制完成后，开始绘制水波纹
        canvas.save();

        if (targetView == null) return;
        if (isDraw) {

            //计算圆心偏移量
            if (currentRadio <= radio) {
                centerX -= centerOffsetX;
//                centerY-=  centerOffsetY;
                JLog.i(centerY+"");
            }
            //限制绘制区域
            canvas.clipRect(rectF);
            canvas.drawCircle((float) centerX,(float) centerY, (float)currentRadio, mPaint);
            currentRadio += radio*1.00 / times;
            postInvalidateDelayed((long) (time / times));
        } else {
            canvas.clipRect(rectF);
            canvas.drawCircle((float)centerX, (float)centerY,(float) currentRadio, mPaint);
        }
        canvas.restore();
    }


    /**
     * 找到被点击的子控件
     *
     * @param downX
     * @param downY
     * @return
     */
    private View findTargerView(int downX, int downY) {
        ArrayList<View> touchables = getTouchables();//此方法会返回所有被点击的控件
        View touchable = null;

        if (touchables == null) {
            return touchable;
        }

        for (View child :
                touchables) {
            int[] location = new int[2];
            //获取该子控件在屏幕上的位置
            child.getLocationOnScreen(location);

            int left = location[0];
            int top = location[1];
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            //获取该控件所在区域的矩形
            RectF rectF = new RectF(left, top, right, bottom);

            //如果这个子控件的位置包含点击的位置，说明点击到该控件了
            if (rectF.contains(downX, downY)) {
                touchable = child;
                this.rectF = rectF;
            }

        }
        return touchable;
    }

    /**
     * 设置水波纹完成时间,默认是500毫秒
     *
     * @param time
     */
    public void setComplete(long time) {
        this.time = time;
        times = time / 30;
    }

    /**
     * 设置水波纹颜色值
     * @param color
     */
    public void setWaveColor(int color){
        this.color=color;
    }
}
