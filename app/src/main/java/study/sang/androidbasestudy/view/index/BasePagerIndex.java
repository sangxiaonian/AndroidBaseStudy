package study.sang.androidbasestudy.view.index;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import study.sang.androidbasestudy.utils.DeviceUtils;

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
    protected float currentIndex = 0;
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
            x += maxChildWidth;
            canvas.drawLine(x, view.getTop(), x, view.getTop() + view.getHeight(), mPaint);
        }
    }

    @Override
    public void addIndexView(String indexName) {
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(getContext());
        textView.setText(indexName);
        textView.setTextColor(Color.BLACK);
        mDatas.add(textView);
        textView.setTag(mDatas.size() - 1);
        textView.setGravity(Gravity.CENTER);
        setGravity(Gravity.CENTER);
        textView.setClickable(true);
        addView(textView, params);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = (int) v.getTag();
                ((TextView) v).setTextColor(Color.RED);
                for (int i = 0; i < BasePagerIndex.this.getChildCount(); i++) {
                    if (index == i) {
                        moveIndex(index);
                        continue;
                    } else {
                        ((TextView) BasePagerIndex.this.getChildAt(i)).setTextColor(Color.BLACK);
                    }

                }


            }
        });
    }


    /**
     * 执行下标移动动画
     *
     * @param toIndex
     */
    public void moveIndex(final int toIndex) {
        final float startIndex = currentIndex;
        final float gapIndex = toIndex - startIndex;
        if (gapIndex == 0) return;
        ValueAnimator animator = ValueAnimator.ofFloat(currentIndex, toIndex);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollX = BasePagerIndex.this.getScrollX();
                boolean isMoveToRight = toIndex>currentIndex;
                onIndexMove(animation, isMoveToRight,startIndex);

                float v = maxChildWidth * currentIndex;
                //如果向左滑动,而未显示完成,就滑动控件,显示完全
                if (scrollX > v && toIndex < currentIndex) {
                    BasePagerIndex.this.scrollTo((int) ((v) - distance), 0);

                    //如果向左滑动,而未显示完成,就滑动控件,显示完全
                } else if (v - scrollX + maxChildWidth > width && toIndex > currentIndex) {
                    BasePagerIndex.this.scrollTo((int) (v - width + maxChildWidth + distance), 0);
                }

                postInvalidate();
            }
        });

        animator.setDuration(500);
        animator.start();
    }


    /**
     * 在执行下标位移动画时候使用,用来获取在下标间移动时候数值的变化
     *
     * @param animation 用来获取属性动画变化过程中数值的变化
     * @param isMoveToRight 是否是向右移动
     */
    public void onIndexMove(ValueAnimator animation, boolean isMoveToRight,float startIndex) {
        float value = (float) animation.getAnimatedValue();
        currentIndex = value;

    }


    /**
     * 设置当前被选中的下标
     *
     * @param index
     */
    public void setCurrentSelect(int index) {

        if (index < 0) {
            index = 0;
        } else if (index >= getChildCount()) {
            index = getChildCount() - 1;
        }

        moveIndex(index);
    }

}
