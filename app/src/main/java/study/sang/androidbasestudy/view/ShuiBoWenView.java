package study.sang.androidbasestudy.view;

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
    private int centerX;
    private int centerY;

    private Paint mPaint;

    private int currentRadio;

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
        mPaint=new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //实心
        mPaint.setStyle(Paint.Style.FILL);
        //颜色
        mPaint.setColor(Color.RED);
    }

    //重写触摸事件，每次down事件中，开始测量绘制
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                JLog.i("被点击了");
                currentRadio=0;
                //点击的位置，也是水波纹的圆心
                int downX = (int) event.getRawX();
                int downY = (int) event.getRawY();

                centerX = downX;
                centerY = downY;

                //找到对应的子控件
                targetView=findTargerView(downX, downY);

                JLog.i(targetView+"centerX:"+centerX+"   centerY:"+centerY);

                //如果没有子控件被点击，就直接结束
                if (targetView==null) return false;
                //寻找圆的半径，要铺满整个子控件，那么半径必须是距离控件边距最大的那个值
                int left = (int) (downX-rectF.left);
                int right = (int) (rectF.right-downX);
                int top = (int) (rectF.top-downY);
                int bottom = (int) (rectF.bottom - downY);

                //获取半径
                radio = Math.max(Math.max(left,right),Math.max(top,bottom));
                JLog.i("半径："+radio);
                //重新绘制
                postInvalidate();
                break;
        }

        return super.onTouchEvent(event);
    }

    //在draw中被调用的方法，用来绘制子控件
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        JLog.i("开始绘制");

        //子控件绘制完成后，开始绘制水波纹
        canvas.save();
        if (targetView!=null){
            //限制绘制区域
            canvas.clipRect(rectF);

            canvas.drawCircle(centerX,centerY,radio,mPaint);
            postInvalidateDelayed(30);
        }
        canvas.restore();
    }

    /**
     * 找到被点击的子控件
     * @param downX
     * @param downY
     * @return
     */
    private View findTargerView(int downX, int downY) {
        ArrayList<View> touchables = getTouchables();//此方法会返回所有被点击的控件
        View touchable = null;

        if (touchables==null){
            return touchable;
        }

        for (View child :
                touchables) {
            int[] location = new int[2];
            //获取该子控件在屏幕上的位置
            child.getLocationOnScreen(location);

            int left = location[0];
            int top =location[1];
            int right = left+child.getMeasuredWidth();
            int bottom = top+child.getMeasuredHeight();
            //获取该控件所在区域的矩形
            rectF = new RectF(left,top,right,bottom);

            //如果这个子控件的位置包含点击的位置，说明点击到该控件了
            if (rectF.contains(downX,downY)){
                touchable = child;
            }

        }
        return touchable;
    }
}
