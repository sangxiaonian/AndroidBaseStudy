package study.sang.androidbasestudy.view.index;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：头部先身后索的下标
 *
 * @Author：桑小年
 * @Data：2016/7/20 10:21
 */
public class BesizerPagerIndex extends BasePagerIndex {

    private int startLocation;
    private float lastLocation;
    private boolean startIsMove;
    private boolean isMoveToRight=true;
    private float endLocation;
private float toIndex=1;

    public BesizerPagerIndex(Context context) {
        super(context);
    }

    public BesizerPagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BesizerPagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawIndex(Canvas canvas, int maxChildWidth) {


        if (isMoveToRight) {
            endLocation = currentIndex * maxChildWidth + maxChildWidth;
            canvas.drawLine(startLocation, getHeight(), endLocation, getHeight(), indexPaint);
        }else {

            canvas.drawLine(startLocation, getHeight(), endLocation, getHeight(), indexPaint);
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
                for (int i = 0; i < BesizerPagerIndex.this.getChildCount(); i++) {
                    if (index == i) {
                        moveIndex(index);
                        continue;
                    } else {
                        ((TextView) BesizerPagerIndex.this.getChildAt(i)).setTextColor(Color.BLACK);
                    }

                }


            }
        });
    }

    @Override
    public void moveIndex(final int toIndex) {
        this.toIndex=toIndex;
        final float startIndex = currentIndex;
        final float gapIndex = toIndex - startIndex;
        if (gapIndex == 0) return;
        ValueAnimator animator = getValueAnimator(toIndex, startIndex);

        animator.setDuration(500);
        animator.start();
    }

    @NonNull
    protected ValueAnimator getValueAnimator(final int toIndex, final float startIndex) {
        ValueAnimator animator = ValueAnimator.ofFloat(currentIndex, toIndex);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollX = BesizerPagerIndex.this.getScrollX();
                boolean isMoveToRight = toIndex > currentIndex;
                onIndexMove(animation, isMoveToRight, startIndex);

                float v = maxChildWidth * currentIndex;
                //如果向左滑动,而未显示完成,就滑动控件,显示完全
                if (scrollX > v && toIndex < currentIndex) {
                    BesizerPagerIndex.this.scrollTo((int) ((v) - distance), 0);

                    //如果向左滑动,而未显示完成,就滑动控件,显示完全
                } else if (v - scrollX + maxChildWidth > width && toIndex > currentIndex) {
                    BesizerPagerIndex.this.scrollTo((int) (v - width + maxChildWidth + distance), 0);
                }

                postInvalidate();
            }
        });
        return animator;
    }

    @Override
    public void onIndexMove(ValueAnimator animation, boolean isMoveToRight, float startIndex) {
        float value = (float) animation.getAnimatedValue();
        currentIndex = value;

        float fraction = animation.getAnimatedFraction();
        float endlocation = value * maxChildWidth;
        float startL = startIndex * maxChildWidth;
        float endL = startL+maxChildWidth;
        this.isMoveToRight = isMoveToRight;

        if (!isMoveToRight){
            startLocation = (int) endlocation;
        }else {
            endLocation = endlocation+maxChildWidth;
        }

        if (fraction > 0.5 ) {
            if (isMoveToRight) {
                startLocation = (int) (startL + (endlocation - startL) * (fraction - 0.5) * 2);
            } else {

                endLocation = (float) (endL+(endlocation+maxChildWidth-endL)*2*(fraction-0.5));
            }
        }
    }
}
