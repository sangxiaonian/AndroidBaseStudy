package ping.com.customprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ping.com.customprogress.utils.DeviceUtils;
import ping.com.customprogress.utils.JLog;
import ping.com.customprogress.utils.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/9/20 14:34
 */
public class DownLoadProgress extends View {

    private Paint mPaint;
    private Path mPath;
    private int textproColor, textFailColor, textSuccessColor, lineColor, blockColor;
    private float textSize, mWidth, mHeight, cellHeight;
    private int MAX, PROGRESS;
    private float currentX, currentY, scale, startX, startY, endX, endY, lineLength, blockWidth, blockHeight, textStartX, textStartY;
    private Rect textRect;
    private String showText = "";
    int cellTextWidth;
    int cellTextHeight;
    private final int ROTA =15;

    public DownLoadProgress(Context context) {
        super(context);
        initViews(context, null, 0);
    }

    public DownLoadProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs, 0);
    }

    public DownLoadProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr);

    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DownLoadProgress);
        lineColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_linecolor, Color.parseColor("#4B1D17"));
        textproColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textprocolor, Color.parseColor("#A6463A"));
        textFailColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textfailcolor, Color.parseColor("#EC5745"));
        textSuccessColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textsuccesscolor, Color.GREEN);
        blockColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_blockcolor, Color.WHITE);
        textSize = typedArray.getDimensionPixelSize(R.styleable.DownLoadProgress_custom_down_textsize, DeviceUtils.dip2px(getContext(), 8));
        MAX = typedArray.getInt(R.styleable.DownLoadProgress_custom_down_max, 100);
        PROGRESS = typedArray.getInt(R.styleable.DownLoadProgress_custom_down_progress, 0);

        typedArray.recycle();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DeviceUtils.dip2px(getContext(), 3));
        mPaint.setColor(textproColor);
        mPaint.setTextSize(textSize);

        textRect = new Rect();
        startMove();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode =MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (heightMode ==MeasureSpec.EXACTLY){
//            int height = getPaddingBottom()+getPaddingTop()+;
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        }
//        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
//                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        cellHeight =blockHeight;
        startX = mWidth / 20;
        startY = mHeight / 2;
        endX = mWidth - startY;
        endY = startY;
        lineLength = (float) (mWidth * 18.0 / 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.restore();
        scale = (float) (PROGRESS * 1.0 / MAX);
        showText = (String) TextUtils.concat(String.valueOf(Utils.get2Double(scale * 100)), "%");
        currentX = startX + lineLength * scale;
        currentY = startY + cellHeight * (scale < 0.5 ? 2 * scale : 2 * (1 - scale));

        mPaint.getTextBounds("99.0%", 0, "99.0%".length(), textRect);

        cellTextWidth = textRect.width() / 3;
        cellTextHeight = textRect.height() / 3;
        blockWidth = cellTextWidth * 5;
        blockHeight = cellTextHeight * 10;


        mPaint.setColor(lineColor);
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.lineTo(currentX, currentY);
        mPath.lineTo(endX, endY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPaint);
        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setColor(blockColor);
        mPath.reset();
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(currentX - cellTextWidth, currentY - cellTextHeight * 3);
        mPath.lineTo(currentX - blockWidth / 2, currentY - cellTextHeight * 3);
        mPath.lineTo(currentX - blockWidth / 2, currentY - blockHeight);
        mPath.lineTo(currentX + blockWidth / 2, currentY - blockHeight);
        mPath.lineTo(currentX + blockWidth / 2, currentY - cellTextHeight * 3);
        mPath.lineTo(currentX + cellTextWidth, currentY - cellTextHeight * 3);
        mPath.lineTo(currentX, currentY);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(textproColor);
        canvas.rotate(ROTA*scale);
        canvas.drawText(showText, currentX - textRect.width() / 2, currentY - cellTextHeight * 2 - textRect.height(), mPaint);
//        canvas.drawLine(startX,startY,endX,endY,mPaint);


        mPath.reset();
        canvas.restore();
    }


    private void startMove() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    SystemClock.sleep(50);
                    count++;
                    PROGRESS = count % MAX;
                    postInvalidate();
                }

            }
        }).start();

    }
}
