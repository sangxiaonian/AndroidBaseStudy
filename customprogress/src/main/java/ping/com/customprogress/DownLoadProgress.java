package ping.com.customprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import ping.com.customprogress.animation.CustomAnimation;
import ping.com.customprogress.exception.ProgressException;
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
    private int MAX, PROGRESS,currentPRO;
    private float scale, lineLength, blockWidth, blockHeight, textStartX, textStartY;

    private PointF currentPoint, startPoint, endPoint, textPoint;

    private Rect textRect;
    private Rect currentRect;
    private String showText = "";
    int cellTextWidth;
    int cellTextHeight;
    private final int ROTA = 10;
    private final int GAP = DeviceUtils.dip2px(getContext(), 10);
    private int unReachColor;
    private float Angle = 8;


    private final int ONPRO = 0;//正在下载
    private final int SUCCESS = 1;//下载成功
    private final int FAIL = 2;//下载失败

    private int state = ONPRO;//状态

    private float roateValue;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setProgress(PROGRESS);
        }
    };

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
        blockWidth = cellTextWidth * 4;
        blockHeight = cellTextHeight + cellTextWidth * 2;


        startPoint = new PointF(0, 0);
        endPoint = new PointF();
        textPoint = new PointF();
        currentPoint = new PointF();
        scale = 0;
//        startMove();
    }

    int count = 0;
    int pro;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            count++;
        }
//        pro++;
        pro += 10;
        setProgress(pro);
        if (count % 6 == 5) {
            downLoadFail();
        }
        return true;
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
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        cellHeight = blockHeight;
        startPoint.x = Math.max(mWidth / 20, blockWidth / 2 + GAP);
        startPoint.y = mHeight / 2;
        endPoint.x = mWidth - startPoint.x;
        endPoint.y = startPoint.y;
        lineLength = mWidth - startPoint.x * 2;


    }

    private Bitmap blockBitmap;
    private float springLength;//弹动的高度
    private float blockX, blocckY;

    @Override
    protected void onDraw(Canvas canvas) {
        if (PROGRESS > MAX) {
            PROGRESS = MAX;
        }
        scale = (float) (PROGRESS * 1.0 / MAX);

        currentPoint.x = startPoint.x + lineLength * scale;
        currentPoint.y = startPoint.y + (-springLength + cellHeight) * (scale < 0.5 ? 2 * scale : 2 * (1 - scale));

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(unReachColor);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(currentPoint.x, currentPoint.y);
        canvas.drawPath(mPath, mPaint);


        mPath.reset();
        mPaint.setColor(lineColor);
        mPath.moveTo(currentPoint.x, currentPoint.y);
        mPath.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);

        //绘制进度方块
        blockBitmap = upView(roateValue, state);
        blockX = currentPoint.x - blockBitmap.getWidth() / 2;
        blocckY = currentPoint.y - blockBitmap.getHeight() / 2 - mPaint.getStrokeWidth() / 2;
        canvas.drawBitmap(blockBitmap, blockX, blocckY, mPaint);
        mPath.reset();

    }

    /**
     * 失败时候的动画
     */
    private void drawFail() {
        if (animator != null) {
            animator.cancel();
        }if (valueAnimator!=null){
            valueAnimator.cancel();
        }
        isfrush = false;
        isAdd=false;
        ValueAnimator animator = drawPro();

        ValueAnimator failAnimation = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                roateValue = 180 * value * value * value * value;
                postInvalidate();
            }
        });

        failAnimation.setInterpolator(new OvershootInterpolator(1.0f));
        failAnimation.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.play(failAnimation).after(animator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isfrush = true;
                isAdd=true;
            }
        });
        set.start();
    }


    /**
     * 成功时候的动画
     */
    private void drawSuccess() {
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator animator1 = drawPro();
        isfrush = false;
        isAdd=false;
        final ValueAnimator success = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                angle = value;
                postInvalidate();
            }
        });
        success.setInterpolator(new BounceInterpolator());
        success.setDuration(1500);
        AnimatorSet set = new AnimatorSet();
        set.play(success).after(animator1);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isfrush = true;
                isAdd=true;
            }
        });
        set.start();

    }

    /**
     * 绘制正在执行的进度
     */
    private ValueAnimator drawPro() {
        if (animator != null) {
            animator.cancel();
        }
//        final float orCurrentY = currentY;
        animator = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                springLength = (float) (cellTextHeight * 2 * ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 2 / 180 - 90 * Math.PI / 180))));
                roateValue = ROTA * (float) ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 1 / 180 - 90 * Math.PI / 180)));
                postInvalidate();
            }
        });

        animator.setDuration(1000);
        return animator;
    }


    ValueAnimator animator;


    /**
     * 根据状态更新进度状态
     *
     * @param value
     * @param state
     * @return
     */
    private Bitmap upView(float value, int state) {
        Bitmap bitmap = Bitmap.createBitmap((int) (blockWidth * 2.5f), (int) (blockHeight * 2.5f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int currentX = bitmap.getWidth() / 2;
        int currentY = bitmap.getHeight() / 2;
        int cell = cellTextWidth / 2;
        showText = (String) TextUtils.concat(String.valueOf(Utils.get2Double(scale * 100)), "%");

        switch (state) {
            case ONPRO:
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                textPaint.setColor(textproColor);
                drowPro(canvas, currentX, currentY, cell);
                break;
            case SUCCESS:
                if (angle > 0 && angle < 0.5) {
                    canvas.scale(angle > 0.5f ? 2 * (angle - 0.5f) : 2 * (0.5f - angle), 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                } else if (angle >= 0.5) {
                    textPaint.setColor(textSuccessColor);
                    showText = "done";
                    canvas.scale(angle > 0.5f ? 2 * (angle - 0.5f) : 2 * (0.5f - angle), 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                } else if (angle == 0) {
                    textPaint.setColor(textproColor);
                    canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                }
                drowPro(canvas, currentX, currentY, cell);

                break;
            case FAIL:
                showText = "fail";
                JLog.i("fail:" + value);
                textPaint.setColor(textFailColor);
                if (value <= 150) {
                    canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2 + textPaint.getStrokeWidth() / 2);
                    drowPro(canvas, currentX, currentY, cell);
                } else {
                    canvas.rotate(value - 180, bitmap.getWidth() / 2, bitmap.getHeight() / 2 + textPaint.getStrokeWidth() / 2);
                    drawFail(canvas, currentX, currentY, cell);
                }


                break;
        }


        return bitmap;
    }

    /**
     * 绘制失败标示
     *
     * @param canvas
     * @param currentX
     * @param currentY
     * @param cell
     */
    private void drawFail(Canvas canvas, int currentX, int currentY, int cell) {
        currentY = (int) (currentY + textPaint.getStrokeWidth());
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(currentX - cell, currentY + cell);
        mPath.lineTo(currentX + cell, currentY + cell);
        mPath.lineTo(currentX, currentY);
        RectF rectFail = new RectF(currentX - blockWidth / 2, currentY + cell, currentX + blockWidth / 2, currentY + blockHeight);
        mPath.addRoundRect(rectFail, Angle, Angle, Path.Direction.CCW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(blockColor);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        textPoint.x = currentX - currentRect.width() / 2;
        textPoint.y = currentY + cell + (currentY + cellTextHeight - currentRect.height()) / 2;
        canvas.drawText(showText, textPoint.x, textPoint.y, textPaint);
    }


    private float angle;

    /**
     * 绘制下载过程中进度
     *
     * @param canvas
     * @param currentX
     * @param currentY
     * @param cell
     */
    private void drowPro(Canvas canvas, int currentX, int currentY, int cell) {
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.getTextBounds(showText, 0, showText.length(), currentRect);
        mPath.reset();

        //绘制下标三角
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(currentX - cell, currentY - cell);
        mPath.lineTo(currentX + cell, currentY - cell);
        mPath.lineTo(currentX, currentY);
        //绘制方块
        RectF rectF = new RectF(currentX - blockWidth / 2, currentY - blockHeight, currentX + blockWidth / 2, currentY - cell);
        mPath.addRoundRect(rectF, Angle, Angle, Path.Direction.CCW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(blockColor);
        canvas.drawPath(mPath, mPaint);

        textPaint.setTextSize(textSize);
        mPath.reset();
        textPoint.x = currentX - currentRect.width() / 2;
        textPoint.y = currentY - (currentY - currentRect.height()) / 2;
        //绘制文字
        canvas.drawText(showText, textPoint.x, textPoint.y, textPaint);
    }




    /**
     * 刷新当前状态
     *
     * @param state
     */
    public void refushState(int state) {
        this.state = state;
        angle=0f;
        switch (state) {
            case ONPRO:
                drawPro().start();
                break;
            case SUCCESS:
                drawSuccess();
                break;
            case FAIL:
                drawFail();
                break;
        }
        postInvalidate();

    }

    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMax(int max) {
        if (MAX <= 0) {
            throw new ProgressException("最大进度<0");
        }

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

    boolean isfrush = true;
    boolean isAdd = true;



    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (isAdd) {
            synchronized (DownLoadProgress.class) {
                if (isAdd) {
                    this.temPro = progress;
                    if (temPro >= MAX) {
                        this.temPro = MAX;
                        isAdd=false;
                    }
                    JLog.e(temPro+"----"+isAdd);


                        addPro();

                }

            }

        }

    }

    private int temPro;

    private void upProgerss(int progress){
        if (isfrush) {
            synchronized (DownLoadProgress.class) {
                if (isfrush) {
                    this.PROGRESS = progress;
                    if (PROGRESS >= MAX) {
                        this.PROGRESS = MAX;
                        refushState(SUCCESS);

                    } else {
                        refushState(ONPRO);

                    }
                }

            }

        }
    }

    ValueAnimator valueAnimator;
    private void addPro(){

        if (valueAnimator!=null){
            valueAnimator.cancel();
        }

        PROGRESS=PROGRESS>temPro?0:PROGRESS;

        valueAnimator = ValueAnimator.ofInt(PROGRESS, temPro);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                upProgerss(value);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }



    /**
     * 获取当前进度
     *
     * @return 获取当前进度值
     */
    public int getProgress() {
        return PROGRESS;
    }

    /**
     * 显示失败界面
     */
    public void downLoadFail() {
        refushState(FAIL);
    }

    /**
     * 加载成功
     */
    public void downLoadSuccess() {
        refushState(SUCCESS);
    }

}
