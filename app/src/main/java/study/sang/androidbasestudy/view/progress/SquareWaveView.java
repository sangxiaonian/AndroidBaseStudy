package study.sang.androidbasestudy.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import study.sang.androidbasestudy.utils.Utils;

/**
 * Description：水波纹效果
 *
 * @Author：桑小年
 * @Data：2016/7/27 9:29
 */
public class SquareWaveView extends View {

    private int count = 100;
    private int pro = 0;
    private Paint mPaint;
    private Path mPath;
    private int color = Color.BLUE;
    private int mWidth, mHeight;
    private int radio;
    private int centerY;
    private int centerX;
    private float currentH = 0.8f;
    private double deep = 0;


    private RectF rectF;

    private float height;//高度
    private Rect rect;
    private String show;

    public SquareWaveView(Context context) {
        super(context);
        initView();
    }

    public SquareWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SquareWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((float) 1);

        mPath = new Path();
        rectF = new RectF();
        rect = new Rect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        mWidth = getWidth();
        mHeight = getHeight();
        radio = Math.min(mWidth, mHeight) / 2 - 5;
        centerX = mWidth / 2;
        centerY = mHeight / 2;

        rectF.left = centerX - radio;
        rectF.top = centerY - radio;
        rectF.right = centerX + radio;
        rectF.bottom = centerY + radio;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        height = (float) (getHeight() - radio * 2 * (pro * 1.0 / count));

        mPaint.setColor(Color.BLUE);
        mPath.reset();
        mPath.moveTo(0, getH(0));
        mPath.cubicTo(mWidth / 4, getH(90), mWidth * 3 / 4, getH(180), mWidth, getH(270));
        mPath.lineTo(mWidth, mHeight * 2);
        mPath.lineTo(0, mHeight * 2);
        mPath.lineTo(0, (float) (height + ((height) * getSin(0) / 10)));
        canvas.drawPath(mPath, mPaint);
        mPath.reset();

        mPaint.setColor(Color.WHITE);

        show = Utils.get2Double(pro * 100 / count) + "%";
        mPaint.setTextSize(Utils.dp2px(15,getContext()));
        mPaint.getTextBounds(show, 0, show.length(), rect);
        mPaint.setTextScaleX(1);
        canvas.drawText(show, (mWidth - rect.width()) / 2,  mHeight/2+rect.height()/2, mPaint);

        startFlow();
    }

    /**
     * 获取相应的sin值
     *
     * @param angle 角度
     * @return
     */
    private double getSin(double angle) {
        return Math.sin(((angle - deep) * Math.PI) / 180) / 10;
    }

    private float getH(double angle) {
        return (float) (height + (mHeight - height) * getSin(angle));
    }


    /**
     * 设置当前进度
     *
     * @param pro
     */
    public void setProgerss(int pro) {
        if (pro >= count) {
            pro = count;
        }
        this.pro = pro;
        postInvalidate();
    }

    /**
     * 设置最大进度,默认我100
     *
     * @param max
     */
    public void setMax(int max) {
        this.count = max;
    }


    private boolean isStart;

    private void startFlow() {
        if (isStart) {
            return;
        }
        isStart = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (deep >= 360) {
                        deep = 0;
                    }
                    deep += 5;
                    SystemClock.sleep(30);
                    postInvalidate();
                }
            }
        }).start();
    }


    /**
     * 返回当前进度
     *
     * @return
     */
    public int getProgress() {
        return pro;
    }

}
