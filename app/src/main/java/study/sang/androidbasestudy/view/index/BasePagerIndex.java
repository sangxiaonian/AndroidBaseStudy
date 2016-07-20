package study.sang.androidbasestudy.view.index;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import study.sang.androidbasestudy.utils.DeviceUtils;
import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/18 10:17
 */
public class BasePagerIndex extends BasePagerLinearLayout {

    /**
     * 用来绘制分割线的画笔
     */
    public Paint mPaint;
    /**
     * 用来绘制下标的画笔
     */
    public Paint indexPaint;
    protected float currentIndex=0;
    private float LastX, LastY, distanceX, distanceY;

    public BasePagerIndex(Context context) {
        super(context);
        initView();
    }

    public BasePagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BasePagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        super.initView();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        indexPaint = new Paint();
        indexPaint.setAntiAlias(true);
        indexPaint.setColor(Color.RED);
        indexPaint.setStrokeWidth(DeviceUtils.dip2px(getContext(), 5f));

    }



    @Override
    public void drawCutLine(Canvas canvas, int maxChildWidth) {
        super.drawCutLine(canvas, maxChildWidth);
        float x = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            x += maxChildWidth  ;
            canvas.drawLine(x, view.getTop(), x, view.getTop() + view.getHeight(), mPaint);
        }
    }



    public void addIndexView(final String indexName) {

    }


    /**
     * 执行下标移动动画
     * @param toIndex
     */
    public void postAni(final int toIndex) {
        final float startIndex = currentIndex;
        final float gapIndex = toIndex - startIndex;
        if (gapIndex == 0) return;
        ValueAnimator animator = ValueAnimator.ofFloat(currentIndex, toIndex);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                currentIndex = value;
                int scrollX = BasePagerIndex.this.getScrollX();
                float v = maxChildWidth * currentIndex;
                JLog.i((v-scrollX+maxChildWidth>width)+"");
                if (scrollX > v&&toIndex<currentIndex) {
                    BasePagerIndex.this.scrollTo((int) (v), 0);
                } else if (v-scrollX+maxChildWidth>width && toIndex > currentIndex) {
                    BasePagerIndex.this.scrollTo((int) (v - width+maxChildWidth), 0);
                }

                postInvalidate();
            }
        });

        animator.setDuration(500);
        animator.start();
    }

    public static void setSelect(int index) {

    }

}
