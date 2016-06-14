package study.sang.androidbasestudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import study.sang.androidbasestudy.utils.ToastUtil;

/**
 *
 * 这是我第一个联系，是一个可以随着手指一动的小球
 */
public class DrawCirleView extends View {
    //画笔
    private Paint mPaint;
    //小球的中心坐标
    private float currentX;
    private float currentY;

    private float loactonX;
    private float loactonY;

    private float disX;
    private float disY;

    private int radius=20;
    public DrawCirleView(Context context) {
        super(context);
        initView();
    }

    public DrawCirleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DrawCirleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化一些数据
    private void initView(){
        mPaint =new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(currentX,currentY,radius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                currentX=event.getX();
                loactonX=event.getX();
                currentY=event.getY();
                loactonY=event.getY();


                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY =event.getY();

                int scollX = (int) (moveX-loactonX);
                int scollY = (int) (moveY- loactonY);

                scrollTo(scollX,scollY);

                loactonX=moveX;
                loactonY=moveY;
                postInvalidate();
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY =event.getY();
                disX=newX-currentY;
                disY =newY-currentY;
                currentY=newY;
                currentX=newX;
                scale(disY);

                break;
        }


        return true;
    }

    //执行放大缩小
    private void scale(float disY){
        if (disY>50){
            radius+=10;
            ToastUtil.showToast(getContext(),"增大了");
        }else if (disY<-50){
            radius-=10;
            if (radius<=3){
                radius=3;
                ToastUtil.showToast(getContext(),"已经最小了");
            }else
                ToastUtil.showToast(getContext(),"缩小了");
        }
        postInvalidate();
    }
}
