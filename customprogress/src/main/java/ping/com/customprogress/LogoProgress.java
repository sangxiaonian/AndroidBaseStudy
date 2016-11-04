package ping.com.customprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import ping.com.customprogress.utils.BitmapUtils;
import ping.com.customprogress.utils.DeviceUtils;
import ping.com.customprogress.utils.JLog;

/**
 * Description：设置两个图片,一个为背景,另一个动态覆盖
 *
 * @Author：桑小年
 * @Data：2016/9/14 10:48
 */
public class LogoProgress extends View {

    private int waveColor;
    private int bgBitmapID;
    private Paint mPaint;
    private RectF rectF;
    private float mWidth;
    private float mHeight;
    private Bitmap bitmap;
    private float startX;

    public LogoProgress(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public LogoProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }


    public LogoProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customProgress);
        waveColor = typedArray.getColor(R.styleable.customProgress_custom_color, Color.alpha(0xabcdef));
        bgBitmapID = typedArray.getResourceId(R.styleable.customProgress_custom_bg, R.drawable.bg_a);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(waveColor);


        rectF = new RectF();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(getResources(),bgBitmapID,options);
        mWidth = options.outWidth;
        mHeight = options.outHeight;

        Shader mShader  =new LinearGradient(mWidth/10,0,0,mHeight,new int[]{Color.GRAY,Color.WHITE,Color.GRAY},null, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        JLog.i(mWidth+"===onMeasure======"+mHeight);
        //如果宽高的都是wrap_content,就用图片的宽高作
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heithtMode  =MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode!=MeasureSpec.EXACTLY){
            int exceptWidth = (int) ((getPaddingLeft() + getPaddingRight()) + mWidth);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }

        if (heithtMode != MeasureSpec.EXACTLY){
            int exceptHeight = (int) ((getPaddingBottom() + getPaddingTop()) + mHeight);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {



    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
        rectF.left = startX;
        rectF.right = startX+mWidth/10;
        rectF.top =0;
        rectF.bottom = mHeight;

//        JLog.i(mWidth+"====onDraw====="+mHeight);
        mPaint.setAlpha(255);
        bitmap = BitmapUtils.decodeSampledBitmapFromResource(getResources(),bgBitmapID,(int) mWidth,(int)mHeight);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.clipRect(rectF);
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(creatNewBitmap(),0,0,mPaint);
        mPaint.setAlpha(230);
        canvas.drawRect(rectF,mPaint);
    }

    protected Bitmap creatNewBitmap(){
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas =new Canvas(newBitmap);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return newBitmap;
    }
}
