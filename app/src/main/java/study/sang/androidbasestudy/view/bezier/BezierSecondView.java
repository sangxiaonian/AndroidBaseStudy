package study.sang.androidbasestudy.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：二阶的贝塞尔曲线
 *
 * @Author：桑小年
 * @Data：2016/7/7 17:05
 */
public class BezierSecondView extends View {


    //绘制贝塞尔曲线使用的类
    private Path path;

    //设置使用的画笔
    private Paint mPaint;

    //贝塞尔曲线的起始点
    private float startX, startY;
    //贝塞尔曲线的结束点
    private float endX, endY;

    //手指的接触点
    private float touchX, touchY;

    private boolean iftouch;

    private float roatY;

    public BezierSecondView(Context context) {
        super(context);
        initView();
    }

    public BezierSecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BezierSecondView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        startX = (float) (getWidth() * 1.0 / 8);
        endY = startY = getHeight() * 4 / 5;
        endX = (float) (getWidth() * 7.0 / 8);
        if (!iftouch) {
            touchX = (float) (getWidth() * 1.0 / 5);
            touchY = endY;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.restore();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制曲线
        path.reset();
        path.moveTo(startX, startY);
        path.quadTo(touchX, touchY, endX, endY);
        canvas.drawPath(path, mPaint);

        path.reset();
        path.moveTo(touchX + getWidth() / 20, touchY - getHeight() / 10);
        path.lineTo(touchX + getWidth() / 20, touchY);
        path.lineTo(touchX - getWidth() / 20, touchY);
        path.lineTo(touchX - getWidth() / 20, touchY - getHeight() / 10);
        path.lineTo(touchX, touchY - getHeight() / 7);
        path.lineTo(touchX + getWidth() / 20, touchY - getHeight() / 10 + 2);
        canvas.drawPath(path, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(touchX, touchY, 10, mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                iftouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                touchY = event.getY();
                JLog.i(touchX + "====" + touchY);
                break;
            case MotionEvent.ACTION_UP:
                iftouch = false;
                break;
        }

        postInvalidate();
        return true;
    }

}
