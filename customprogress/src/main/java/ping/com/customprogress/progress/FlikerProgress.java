package ping.com.customprogress.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import ping.com.customprogress.utils.DeviceUtils;
import ping.com.customprogress.utils.Utils;

import static android.R.attr.max;
import static android.R.attr.start;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/10/13 14:36
 */
public class FlikerProgress extends View {

    private Paint mPaint;
    private Path mPath;
    private Rect mRectf;
    private PointF startPoint, endPoint;
    private int MAX, PROGRESS;
    private float ratio;
    private int reachColor;
    private String showText;

    public FlikerProgress(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public FlikerProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public FlikerProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        mRectf = new Rect();

        startPoint = new PointF();
        endPoint = new PointF();


        MAX = 100;
        PROGRESS = 50;
        reachColor = Color.parseColor("#40c0f8");
        move();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ratio = PROGRESS * 1.0f / MAX;
        startPoint.x = 0;
        startPoint.y = 0;
        endPoint.x = getMeasuredWidth() * ratio;
        endPoint.y = getMeasuredHeight();
        mPaint.setAlpha(255);
        drawBorder(canvas);
        drawReach(canvas);

    }

    /**
     * 绘制边界方块
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(reachColor);
        mPaint.setStrokeWidth(DeviceUtils.dip2px(getContext(), 1));
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }


    /**
     * 绘制进度
     *
     * @param canvans
     */
    private void drawReach(Canvas canvans) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(Math.min(getHeight(), getWidth()) / 3);
        mPaint.getTextBounds("100.0%", 0, "100.0%".length(), mRectf);
        mPaint.setColor(reachColor);
        canvans.drawText(showText + "%", (getWidth() - mRectf.width()) / 2, (getHeight() + mRectf.height()) / 2, mPaint);
        showText = String.valueOf(Utils.get2Double(PROGRESS * 100.0f / MAX));
        mPath.reset();
        mPath.addRect(0, 0, endPoint.x, endPoint.y, Path.Direction.CCW);
        canvans.clipPath(mPath);
        canvans.drawColor(reachColor);

        drawFlike(canvans);
        mPaint.setColor(Color.WHITE);
        canvans.drawText(showText + "%", (getWidth() - mRectf.width()) / 2, (getHeight() + mRectf.height()) / 2, mPaint);
    }

    private float startX;


    /**
     * 绘制滑块
     */
    private void drawFlike(Canvas canvans) {

        mPaint.setColor(Color.parseColor("#24d4f8"));
        canvans.drawCircle(startX, getHeight() / 2, getHeight() * 3 / 4, mPaint);
        mPaint.setColor(Color.parseColor("#15e0f8"));
        canvans.drawCircle(startX, getHeight() / 2, getHeight()*2/5, mPaint);
    }

    private Thread mThread;

    private void move() {
        if (mThread != null) {
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    startX += 30;
                    SystemClock.sleep(50);
                    if (startX > endPoint.x + getHeight() * 2 / 3) {
                        startX = -getHeight() * 2 / 3;

                    }
                    postInvalidate();
                }
            }
        }
        );
        mThread.start();

    }
    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMax(int max) {
        this.MAX = max;
    }

    /**
     * 获取最大进度
     *
     * @return
     */
    public int getMax() {
        return MAX;
    }


    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.PROGRESS = progress;
    }

    /**
     * 获取最大进度
     *
     * @return
     */
    public int getProgress() {
        return PROGRESS;
    }




}
