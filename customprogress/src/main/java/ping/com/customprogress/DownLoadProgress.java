package ping.com.customprogress;

import android.animation.AnimatorSet;
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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import ping.com.customprogress.animation.CustomAnimation;
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


    private final int ONPRO = 0;//正在下载
    private final int SUCCESS = 1;//下载成功
    private final int FAIL = 2;//下载失败

    private int state = ONPRO;//状态

    private float roateValue;
    private android.graphics.Matrix matrix;

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
        matrix = new android.graphics.Matrix();

//        startMove();
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
//        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
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

private  Bitmap blockBitmap;
    private float springLength;//弹动的高度
    private float blockX,blocckY;
    @Override
    protected void onDraw(Canvas canvas) {
        if (PROGRESS > MAX) {
            PROGRESS = MAX;
        }
        scale = (float) (PROGRESS * 1.0 / MAX);
        currentX = startX + lineLength * scale;
        currentY = startY +(springLength+ cellHeight )* (scale < 0.5 ? 2 * scale : 2 * (1 - scale));

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

        //绘制进度方块
        blockBitmap=upView(roateValue, state);
        blockX = currentX-blockBitmap.getWidth()/2;
        blocckY =  currentY-blockBitmap.getHeight()/2-mPaint.getStrokeWidth()/2;
        canvas.drawBitmap(blockBitmap,blockX,blocckY,mPaint);
        mPath.reset();

//        if (!isAnim) {
//
//        }

        canvas.save();
    }

    private void drawFail() {
        if (animator != null) {
            animator.cancel();
        }

        ValueAnimator animator = drawPro();

        ValueAnimator failAnimation = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
//                springLength = (float) (* ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 4/ 180-90*Math.PI/180))))*2;
                springLength=cellTextHeight*28*value;
                roateValue = 180*value*value*value*value;
                postInvalidate();
            }
        });
//        failAnimation.setInterpolator(new );
        failAnimation.setDuration(500);

        ValueAnimator last = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                springLength = (float) (cellTextHeight*2* ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 3/ 180))))*2;
                roateValue=  180 +ROTA*2*(float) ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 1 / 180)));
                postInvalidate();
            }
        });
//        failAnimation.setInterpolator(new );
        last.setDuration(800);


        AnimatorSet set = new AnimatorSet();
        set.play(failAnimation).after(animator).before(last);
        set.start();
    }


    private void drawSuccess() {
        if (animator != null) {
            animator.cancel();
        }

        final ValueAnimator success = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                if (value >= 0.7) {
                    upView(0, SUCCESS);
                }
            }
        });
        success.setDuration(1000);
        ValueAnimator animator = drawPro();
        AnimatorSet set = new AnimatorSet();
        set.play(success).after(animator).after(500);
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
               springLength = (float) (cellTextHeight*2* ((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 2/ 180-90*Math.PI/180))));
                roateValue=   ROTA*(float)((1 - value) * Math.sin(Math.sin(360 * Math.PI * (value) * 1 / 180-90*Math.PI/180)));
                postInvalidate( );
            }
        });

        animator.setDuration(500);

        return animator;


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ++PROGRESS;
        if (PROGRESS >= 50) {
            refushState(FAIL);
//            PROGRESS=0;
            return true;
        }
        setProgress(PROGRESS);
        if (PROGRESS >= MAX) {
            PROGRESS = 0;
        }


        return true;
    }


    ValueAnimator animator;


    private Bitmap upView(float value, int state) {
        Bitmap bitmap = Bitmap.createBitmap((int) (blockWidth * 2.5f), (int) (blockHeight * 2.5f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        switch (state) {
            case ONPRO:
                showText = (String) TextUtils.concat(String.valueOf(Utils.get2Double(scale * 100)), "%");
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight()/2);
                textPaint.setColor(textproColor);
                break;
            case SUCCESS:
                showText = "done";
                textPaint.setColor(textSuccessColor);
                break;
            case FAIL:
                showText = "fail";
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2+textPaint.getStrokeWidth()/2);
                textPaint.setColor(textFailColor);
                break;
        }
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.getTextBounds(showText, 0, showText.length(), currentRect);
        mPath.reset();
        int currentX = bitmap.getWidth() / 2;
        int currentY = bitmap.getHeight()/2;
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
        canvas.drawText(showText, currentX - currentRect.width() / 2, currentY - cellTextHeight - currentRect.height(), textPaint);

        return bitmap;
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
//                    postInvalidate(false);
                }

            }
        }).start();

    }

    public void refushState(int state) {
        this.state = state;
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
    }

    public void setMax(int max) {
        this.MAX = max;
    }

    public int getMax() {
        return MAX;
    }

    boolean isfrush = true;

    public void setProgress(int progress) {

        if (state != FAIL) {
            if (progress < MAX) {
                isfrush = true;
                this.PROGRESS = progress;
                refushState(ONPRO);

            } else {
                this.PROGRESS = MAX;

                if (isfrush) {
                    refushState(SUCCESS);
                    isfrush = false;
                }
            }
        }

    }

    public int getProgress() {
        return PROGRESS;
    }


}
