package study.sang.androidbasestudy.view.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/13 13:47
 */
public class IndexView extends RadioGroup {

    private Paint mPaint;
    private Path mPath;

    private List<String> childs;

    private float bigCenterX, centerY;
    private float smlCenterX;

    //分割的子View数量
    private int count;

    private int currentIndex = 0;

    private float gap;

    private float bigRadius, smlRadius;

    private boolean isMeasure;
    private boolean isComp;
    private ValueAnimator animator;

    public IndexView(Context context) {
        super(context);
        initView();
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        setBackgroundColor(Color.WHITE);
        childs = new ArrayList<>();
        setGravity(Gravity.CENTER);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        bigRadius = (getHeight() - getPaddingTop() - getPaddingBottom()) / 3;
        smlRadius = bigRadius / 2;
        count = getChildCount();
        if (count != 0) {
            gap = (getWidth() - getPaddingLeft() - getPaddingRight()) / count;
        }

        if (!isMeasure) {
            bigCenterX = gap * currentIndex + gap / 2;
            smlCenterX = gap * currentIndex + gap / 2;
        }
        centerY = getHeight() / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        if (count == 0) {
            mPaint.setColor(Color.TRANSPARENT);
        } else {
            mPaint.setColor(Color.GREEN);
        }
        mPath.moveTo(bigCenterX, centerY - bigRadius);
        mPath.quadTo((smlCenterX + bigCenterX) / 2, centerY, smlCenterX, centerY - smlRadius);
        mPath.lineTo(smlCenterX, centerY + smlRadius);
        mPath.quadTo((smlCenterX + bigCenterX) / 2, centerY, bigCenterX, centerY + bigRadius);
        mPath.lineTo(bigCenterX, centerY - bigRadius);
        canvas.drawCircle(bigCenterX, centerY, bigRadius, mPaint);
        canvas.drawCircle(smlCenterX, centerY, smlRadius, mPaint);
        mPath.close();
        canvas.drawPath(mPath, mPaint);

    }


    private void startMove(final int toIndex) {

        final float toSmlCenterX = toIndex * gap + gap / 2;
        final float currentCenterX = bigCenterX;
        isMeasure = true;
        currentIndex = toIndex;
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(smlCenterX, toSmlCenterX);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                smlCenterX = (float) animation.getAnimatedValue();
                float v = (smlCenterX - currentCenterX) / (toSmlCenterX - currentCenterX);
                if (v>0.5 ) {
                    bigCenterX = smlCenterX - (toSmlCenterX - smlCenterX);
                }
                postInvalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    public void addIndex(String name) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(name);
        childs.add(name);
        radioButton.setTag(childs.size() - 1);
        radioButton.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        radioButton.setLayoutParams(lp);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startMove((Integer) buttonView.getTag());
                }
            }
        });
        addView(radioButton,childs.size() - 1);

    }

    public void addLayoutView(int layout,String name){
        RadioButton view = (RadioButton) LayoutInflater.from(getContext()).inflate(layout,this,false);
        view.setText(name);
        childs.add(name);

        addView(view,childs.size() - 1);
        view.setTag(childs.size() - 1);
        LinearLayout.LayoutParams lp = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        view.setLayoutParams(lp);
        view.setClickable(true);
        view.setGravity(Gravity.CENTER);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int)v.getTag()!=currentIndex) {
                    startMove((Integer) v.getTag());
                    currentIndex= (int) v.getTag();
                }
            }
        });

    }


    public int getIndex() {
        return currentIndex;
    }


}
