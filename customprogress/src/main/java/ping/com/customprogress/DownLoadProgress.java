package ping.com.customprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import ping.com.customprogress.utils.DeviceUtils;
import ping.com.customprogress.utils.JLog;
import ping.com.customprogress.utils.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/9/20 14:34
 */
public class DownLoadProgress extends ViewGroup {

    private Paint mPaint, textPaint;
    private Path mPath;
    private int textproColor, textFailColor, textSuccessColor, lineColor, blockColor;
    private float textSize, mWidth, mHeight, cellHeight;
    private int MAX, PROGRESS;
    private float currentX, currentY, scale, startX, startY, endX, endY, lineLength, blockWidth, blockHeight, textStartX, textStartY;
    private Rect textRect;
    private Rect currentRect;
    private String showText = "";
    int cellTextWidth;
    int cellTextHeight;
    private final int ROTA = 10;
    private final int GAP = DeviceUtils.dip2px(getContext(), 10);
    private int unReachColor;
    private float Angle = 8;
    private ImageView view;


    private final int ONPRO = 0;//正在下载
    private final int SUCCESS = 1;//下载成功
    private final int FAIL = 2;//下载失败

    private int state = ONPRO;//状态

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
        lineColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_reachcolor, Color.parseColor("#4B1D17"));
        unReachColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_unreachcolor, Color.WHITE);
        textproColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textprocolor, Color.parseColor("#A6463A"));
        textFailColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textfailcolor, Color.parseColor("#EC5745"));
        textSuccessColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_textsuccesscolor, Color.GREEN);
        blockColor = typedArray.getColor(R.styleable.DownLoadProgress_custom_blockcolor, Color.WHITE);
        textSize = typedArray.getDimensionPixelSize(R.styleable.DownLoadProgress_custom_down_textsize, DeviceUtils.dip2px(getContext(), 12));
        MAX = typedArray.getInt(R.styleable.DownLoadProgress_custom_down_max, 100);
        PROGRESS = typedArray.getInt(R.styleable.DownLoadProgress_custom_down_progress, 0);

        typedArray.recycle();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DeviceUtils.dip2px(getContext(), 3));

        textPaint = new Paint();
        textPaint.setStrokeWidth(DeviceUtils.dip2px(getContext(), 3));
        textPaint.setColor(textproColor);
        textPaint.setTextSize(textSize);

        textRect = new Rect();
        currentRect = new Rect();
        textPaint.getTextBounds("99.0%", 0, "99.0%".length(), textRect);
        cellTextWidth = textRect.width() / 3;
        cellTextHeight = textRect.height() / 3;
        blockWidth = cellTextWidth * 5;
        blockHeight = cellTextHeight + cellTextWidth * 3;

        view = new ImageView(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams((int) blockWidth, (int) blockWidth));
        addView(view);
        upView(0);
        startMove();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            int height = (int) (getPaddingBottom() + getPaddingTop() + 4 * blockHeight + GAP * 2);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(height, getMeasuredHeight()), MeasureSpec.EXACTLY);
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            int width = (int) (getPaddingLeft() + getPaddingRight() + blockWidth + GAP * 2);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(width, getMeasuredWidth()), MeasureSpec.EXACTLY);
        }
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        JLog.i("--------onLayout----------");

        mWidth = getWidth();
        mHeight = getHeight();
        cellHeight = blockHeight;
        startX = Math.max(mWidth / 20, blockWidth / 2 + GAP);
        startY = mHeight / 2;
        endX = mWidth - startX;
        endY = startY;
        lineLength = mWidth - startX * 2;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (PROGRESS > MAX) {
            PROGRESS = MAX;
        }
        scale = (float) (PROGRESS * 1.0 / MAX);
        currentX = startX + lineLength * scale;
        currentY = startY + cellHeight * (scale < 0.5 ? 2 * scale : 2 * (1 - scale));
        getChildAt(0).layout((int) (currentX - blockWidth / 2), (int) (currentY - blockHeight - textPaint.getStrokeWidth() / 2), (int) (currentX + blockWidth / 2), (int) ((int) currentY - textPaint.getStrokeWidth() / 2));
        canvas.restore();
        JLog.i("--------onDraw----------");
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(unReachColor);
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.lineTo(currentX, currentY);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        mPaint.setColor(lineColor);
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(endX, endY);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        switch (state) {
            case ONPRO:
                drawPro(state);
                break;
            case SUCCESS:
                drawSuccess(state);
        }

        canvas.restore();
    }

    private void drawSuccess(int state) {

        RoateAnimation(3, state);
    }

    /**
     * 绘制正在执行的进度
     *
     * @param state
     */
    private void drawPro(int state) {


        RoateAnimation(3, state);//选择子控件

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PROGRESS=0;
//        setProgress(PROGRESS);

//        postInvalidate();
        return true;
    }


    ValueAnimator animator;

    /**
     * 执行动画,旋转周期
     *
     * @param cycle
     * @param state
     */
    private void RoateAnimation(final int cycle, final int state) {
        if (PROGRESS == 0) {
            return;
        }
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(0f, 4.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = animation.getAnimatedFraction();
                switch (state) {
                    case ONPRO:
                        upView((float) (ROTA * (1 - value) * Math.sin(Math.sin(360 * Math.PI * (value - 0.5f) * cycle / 180 + 90 * Math.PI / 180))));
                        break;
                    case SUCCESS:
                        upView(0);
//                        view.setRotationY(360*value,view.getWidth()/2,view.getHeight());
//                        view.setRotationY(90);
                        break;
                }

            }


        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                upView(0);
            }
        });
        animator.setDuration(1000);
        animator.start();

    }

    private void upView(float value) {
        Bitmap bitmap = Bitmap.createBitmap((int) (blockWidth * 1.5f), (int) (blockHeight * 1.5f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        switch (state) {
            case ONPRO:
                showText = (String) TextUtils.concat(String.valueOf(Utils.get2Double(scale * 100)), "%");
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight());
                textPaint.setColor(textproColor);
                break;
            case SUCCESS:
                showText = "done";
                textPaint.setColor(textSuccessColor);
                break;
            case FAIL:
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight());
                showText = "fail";
                textPaint.setColor(textFailColor);
                break;
        }

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.getTextBounds(showText, 0, showText.length(), currentRect);
        mPath.reset();
        int currentX = bitmap.getWidth() / 2;
        int currentY = bitmap.getHeight();
        int cell = cellTextWidth / 2;
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(currentX - cell, currentY - cell);
        mPath.lineTo(currentX + cell, currentY - cell);
        mPath.lineTo(currentX, currentY);
        RectF rectF = new RectF(currentX - blockWidth / 2, currentY - blockHeight, currentX + blockWidth / 2, currentY - cell);
        mPath.addRoundRect(rectF, Angle, Angle, Path.Direction.CCW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(blockColor);
        canvas.drawPath(mPath, mPaint);

        textPaint.setTextSize(textSize);
        canvas.drawText(showText, currentX - currentRect.width() / 2, currentY - cellTextHeight * 2 - currentRect.height(), textPaint);
        view.setImageBitmap(bitmap);
        view.postInvalidate();
    }


    private void startMove() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    SystemClock.sleep(50);
                    count++;
//                    PROGRESS = count % MAX;
//                    PROGRESS=count;
                    PROGRESS++;
                    setProgress(PROGRESS);
//                    postInvalidate();
                }

            }
        }).start();

    }

    public void refushState(int state) {
        this.state = state;
        postInvalidate();
    }

    public void setMax(int max) {
        this.MAX = max;
    }

    public int getMax() {
        return MAX;
    }

    public void setProgress(int progress) {
        boolean isfrush = true;
        if (progress < MAX) {
            isfrush =true;
            this.PROGRESS = progress;
            refushState(ONPRO);
        } else {
//            progress = MAX;
            this.PROGRESS = MAX;
            if (isfrush) {
                refushState(SUCCESS);
                isfrush=false;
            }
        }

//        postInvalidate();
    }

    public int getProgress() {
        return PROGRESS;
    }
}
