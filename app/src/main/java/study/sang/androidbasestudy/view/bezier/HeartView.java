package study.sang.androidbasestudy.view.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import study.sang.androidbasestudy.R;
import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：利用贝塞尔曲线,属性动画和PorterDuff是实现的爱心动画效果
 *
 * @Author：桑小年
 * @Data：2016/7/12 13:56
 */
public class HeartView extends RelativeLayout {

    /**
     * 花小球使用的笔
     */
    private Paint criPaint;
    /**
     * 划线使用的笔
     */
    private Paint linPaint;

    private Bitmap bitmap;

    //贝塞尔曲线使用的笔
    private Path path;
    private int startX;
    private int startY;
    private ViewGroup.LayoutParams params;

    private PointF startPoint;

    private int[] base1 = {1, -1};
    private Random random;


    public HeartView(Context context) {
        super(context);
        initView();

    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化一些数据
    private void initView() {
        criPaint = new Paint();
        criPaint.setStrokeWidth(10);
        criPaint.setStyle(Paint.Style.FILL);
        criPaint.setAntiAlias(true);
        criPaint.setColor(Color.GREEN);

        linPaint = new Paint();
        linPaint.setStrokeWidth(10);
        linPaint.setStyle(Paint.Style.STROKE);
        linPaint.setAntiAlias(true);

        path = new Path();
        startPoint = new PointF();
        random = new Random();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);

        setClickable(true);
        JLog.i("---------------initView------------------");
    }

    private int[] colors = {Color.GREEN, Color.BLUE, Color.CYAN, Color.WHITE, Color.YELLOW, Color.red(121212)};

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        startX = (getWidth() - bitmap.getWidth()) / 2;
        startY = getHeight() - bitmap.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addHeart(colors[random.nextInt(colors.length)]);
        return super.onTouchEvent(event);
    }

    public void addHeart(int color) {

        PointF endPoint = new PointF(startX, startY - 600);
        PointF firstPoint = new PointF(startX + 200, startY - 200);
        PointF secondPoint = new PointF(startX - 200, startY - 400);

        startPoint.x = startX;
        startPoint.y = startY;
        endPoint.x = random.nextInt(getWidth());
        endPoint.y = 0;


        firstPoint.x = random.nextInt(getWidth() - 200) + 100;
        firstPoint.y = random.nextInt(getHeight() / 2) + getHeight() / 2;

        secondPoint.x = random.nextInt(getWidth() - 200) + 100;
        secondPoint.y = random.nextInt(getHeight() / 2);
        JLog.i(startX + "====" + (startY - 200) + "====" + firstPoint.y);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ImageView img = new ImageView(getContext());
        img.setImageBitmap(creatHeart(color));
        img.setLayoutParams(params);
        addView(img);
        move(img, firstPoint, secondPoint, endPoint);
        JLog.i("---------------添加控件---------------");
    }

    private void move(final View view, PointF firstPoint, PointF secondPoint, Object endPoint) {

        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointType(firstPoint, secondPoint), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();

                view.setX(pointF.x);
                view.setY(pointF.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                HeartView.this.removeView(view);
            }
        });
        valueAnimator.setDuration(3000);
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        ani.setDuration(3000);
        AnimatorSet set = new AnimatorSet();
        set.play(valueAnimator).with(ani);
        set.start();
    }


    private Bitmap creatHeart(int color) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, criPaint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return newBitmap;

    }


    class PointType implements TypeEvaluator<PointF> {
        private PointF firstPoint, secondPoint;

        public PointType(PointF firstPoint, PointF secondPoint) {
            this.firstPoint = firstPoint;
            this.secondPoint = secondPoint;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF result = new PointF();
            float left = 1 - fraction;
            result.x = (float) (startValue.x * Math.pow(left, 3) + 3 * firstPoint.x * Math.pow(left, 2) * fraction +
                    secondPoint.x * 3 * Math.pow(fraction, 2) * left + endValue.x * Math.pow(fraction, 3));
            result.y = (float) (startValue.y * Math.pow(left, 3) + 3 * firstPoint.y * Math.pow(left, 2) * fraction +
                    secondPoint.y * 3 * Math.pow(fraction, 2) * left + endValue.y * Math.pow(fraction, 3));

            return result;
        }
    }

}
