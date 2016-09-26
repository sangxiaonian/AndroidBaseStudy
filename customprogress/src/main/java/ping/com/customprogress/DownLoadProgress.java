package ping.com.customprogress;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

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


    private Matrix m;
    private Camera camera;


    private Handler handler = new Handler(){
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


        startPoint = new PointF();
        endPoint = new PointF();
        textPoint = new PointF();
        currentPoint = new PointF();


           m = new Matrix();
           camera = new Camera();


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
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        JLog.i("--------onLayout----------");

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

        canvas.restore();
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
        canvas.drawBitmap(blockBitmap, blockX, blocckY-angle, mPaint);
        mPath.reset();
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
                roateValue = 180 * value * value * value * value;
                postInvalidate();
            }
        });

        failAnimation.setInterpolator(new OvershootInterpolator(1.0f));
        failAnimation.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.play(failAnimation).after(animator);
        set.start();
    }


    private void drawSuccess() {
        if (animator != null) {
            animator.cancel();
        }

        final ValueAnimator success = CustomAnimation.getInstance().creatAnimotion(CustomAnimation.normal, new CustomAnimation.AnimotionListener() {
            @Override
            public void onUpData(float value) {
                angle = DeviceUtils.dip2px(getContext(),20)*(value>0.5f?2*(value-0.5f):2*(value));

                postInvalidate();
            }
        });
        success.setInterpolator(new BounceInterpolator());
        success.setDuration(500);
        ValueAnimator animator = drawPro();
        AnimatorSet set = new AnimatorSet();
        set.play(success).after(animator);
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


    int count = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ++count;
        if (PROGRESS >= MAX) {
            PROGRESS = 0;
            count=0;
        }

        if (PROGRESS>=20){
            refushState(SUCCESS);
            return false ;
        }
        setProgress(count);



        return true;
    }


    ValueAnimator animator;


    private Bitmap upView(float value, int state) {
        Bitmap bitmap = Bitmap.createBitmap((int) (blockWidth * 2.5f), (int) (blockHeight * 2.5f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int currentX = bitmap.getWidth() / 2;
        int currentY = bitmap.getHeight() / 2;
        int cell = cellTextWidth / 2;
        switch (state) {
            case ONPRO:
                showText = (String) TextUtils.concat(String.valueOf(Utils.get2Double(scale * 100)), "%");
                canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                textPaint.setColor(textproColor);
                drowPro(canvas, currentX, currentY, cell);
                break;
            case SUCCESS:
                showText = "done";
                textPaint.setColor(textSuccessColor);
                drowPro(canvas, currentX, currentY, cell);
                break;
            case FAIL:
                showText = "fail";
                JLog.i("fail:"+value);
                textPaint.setColor(textFailColor);
                if (value<=150) {
                    canvas.rotate(value, bitmap.getWidth() / 2, bitmap.getHeight() / 2 + textPaint.getStrokeWidth() / 2);
                    drowPro(canvas, currentX, currentY, cell);
                }else {
                    canvas.rotate(value-180, bitmap.getWidth() / 2, bitmap.getHeight() / 2 + textPaint.getStrokeWidth() / 2);
                    drawFail(canvas, currentX, currentY, cell);
                }


                break;
        }





        return bitmap;
    }

    private void drawFail(Canvas canvas, int currentX, int currentY, int cell) {
        currentY = (int) (currentY+textPaint.getStrokeWidth());
        mPath.moveTo(currentX, currentY);
        mPath.lineTo(currentX - cell, currentY + cell);
        mPath.lineTo(currentX + cell, currentY + cell);
        mPath.lineTo(currentX, currentY);
        RectF rectFail = new RectF(currentX - blockWidth / 2, currentY +cell , currentX + blockWidth / 2, currentY +blockHeight);
        mPath.addRoundRect(rectFail, Angle, Angle, Path.Direction.CCW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(blockColor);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        textPoint.x = currentX - currentRect.width() / 2;
        textPoint.y = currentY + cell + (currentY +cellTextHeight -currentRect.height()) / 2;
        canvas.drawText(showText, textPoint.x, textPoint.y, textPaint);
    }


private float angle;
    private void drowPro(Canvas canvas, int currentX, int currentY, int cell) {
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.getTextBounds(showText, 0, showText.length(), currentRect);
        mPath.reset();

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
        mPath.reset();
        textPoint.x = currentX - currentRect.width() / 2;
        textPoint.y = currentY  - (currentY  - currentRect.height()) / 2;

        canvas.drawText(showText, textPoint.x, textPoint.y, textPaint);
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
                    if (PROGRESS>MAX){
                        return;
                    }
                    handler.sendEmptyMessage(0);
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

        if (state==ONPRO){
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
