package study.sang.androidbasestudy.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.wifi.WifiEnterpriseConfig;
import android.util.AttributeSet;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;
import study.sang.androidbasestudy.utils.Utils;

/**
 * 作者： ${桑小年} on 2016/5/22.
 * 努力，为梦长留
 * 由于在数据方面，圆形和长条形是基本一样的，不同的就是在ondraw不同，因此直接继承，然后重写ondraw方法就行
 */
public class RoundProgressBarWidthNumber extends HorizontalProgress {

    /**半径*/
    protected int mRadio = Utils.dp2px(30,getContext());

    public RoundProgressBarWidthNumber(Context context) {
        this(context,null);
    }

    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWidthNumber);
        mRadio = (int) typedArray.getDimension(R.styleable.RoundProgressBarWidthNumber_RoundProgressBarWidthNumber_radius,mRadio);
        typedArray.recycle();

        textSize = Utils.sp2px(14,getContext());

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode =MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //圆的厚度
        int painWidth = mHeight;

        if (heightMode!=MeasureSpec.EXACTLY){
            int exceptHeight = (getPaddingBottom()+getPaddingTop())+mRadio*2+painWidth;
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(exceptHeight,MeasureSpec.EXACTLY);

        }

        if (widthMode!=MeasureSpec.EXACTLY){
            int exceptWidth = (getPaddingLeft()+getPaddingRight())+mRadio*2+painWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth,MeasureSpec.EXACTLY);

        }

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);




    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress()+"%";
        float textWidth = mPaint.measureText(text);
        float textHeight = mPaint.ascent()+mPaint.descent();

        canvas.save();

        canvas.translate(getPaddingLeft(),getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);

        //开始绘制颜色，总体来说，先绘制一个圆形的未完成时候的颜色，然后绘制一个已经完成的颜色
        //的圆弧，圆弧会遮盖掉原先的颜色

        //绘制未到达的颜色
        mPaint.setColor(unRecarchColor);
        mPaint.setStrokeWidth(mHeight);
        canvas.drawCircle(mRadio,mRadio,mRadio,mPaint);

        //绘制已经走过的颜色
        mPaint.setColor(recarchColor);
        mPaint.setStrokeWidth(mHeight*2);
        float sweepAngle =  ((getProgress()*1.0f/getMax())*360);
        canvas.drawArc(new RectF(0,0,mRadio*2,mRadio*2),0,sweepAngle,false,mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,mRadio-textWidth/2,mRadio-textHeight/2,mPaint);

        canvas.restore();


    }
}
