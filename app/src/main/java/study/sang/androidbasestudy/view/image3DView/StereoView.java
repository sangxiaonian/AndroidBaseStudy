package study.sang.androidbasestudy.view.image3DView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/22 15:41
 */
public class StereoView extends ViewGroup {

    private Scroller scroller;
    private Context mContext;
    private int mHeight, mWidth;//控件的宽高
    private int currentIndex = 0;
    private int index;
    private boolean isUp;//设置是否是向上滑动
    private int lastIndex;
    private double rotio;
    private int aniDrution = 1000;


    /**
     * Matrix对象，用于对图片进行矩阵操作。
     */
    private Matrix matrix = new Matrix();

    /**
     * Camera对象，用于对图片进行三维操作。
     */
    private Camera camera = new Camera();


    public StereoView(Context context) {
        super(context);
        initView();
    }

    public StereoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StereoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mContext = getContext();
        scroller = new Scroller(mContext);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        //获取本控件的宽高
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        //滑动到当前的位置
        scrollTo(0, currentIndex * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //在竖直方向上排列这些控件
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
            childTop += mHeight;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return true;
    }

    /**
     * 滑动到指定的页面
     *
     * @param toIndex 指定页面的下标
     */
    public void scrollToIndex(int toIndex) {
        if (getChildCount() == 0) return;
        index = toIndex % getChildCount();

        //用来判断滑动方向
        if (toIndex> lastIndex) {
            isUp = false;
        } else {
            isUp = true;
        }

        //如果滑动上次滑动尚未结束,就直接终止滑动,然后直接滑动到指定位置,然后开始下一次滑动
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
//            scrollTo(0, mHeight * currentIndex);
        }

        int currY = scroller.getCurrY();

        //如果是向上滑动
        if (isUp) {

            //此时处于最上端
            if (currY == 0) {
                //就将最后一个移动到第一个位置
                addIndexView();
                //然后从第二个位置滑动到第一个位置,造成无线滑动的错觉
               scroller.startScroll(0,  currY+mHeight,0,(toIndex - lastIndex) * mHeight,aniDrution);
            }else {
                //普通的滑动
                scroller.startScroll(0, currY,0,(toIndex - lastIndex) * mHeight,aniDrution);
            }

        } else {
            if (currY >= mHeight*getChildCount()-mHeight) {
                addIndexView();
                scroller.startScroll(0, currY-mHeight,0,(toIndex - lastIndex) * mHeight,aniDrution);
            }else {
                scroller.startScroll(0, currY,0,(toIndex - lastIndex) * mHeight,aniDrution);

            }
        }
        postInvalidate();
        currentIndex = index;
        lastIndex = toIndex;

    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());

            float velocity = scroller.getCurrVelocity();
            rotio = 1 - (currentIndex * mHeight - scroller.getCurrY()) * 1.0 / mHeight;


            postInvalidate();
        }
    }

    /**用来绘制子控件的方法,在View绘制完成之后,ChildView绘制之前调用*/
    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {

            int curScreenY = mHeight * i;
            float centerX = mWidth / 2;
            float centerY = (getScrollY() >= curScreenY) ? curScreenY + mHeight : curScreenY;
            float degree = 70 * (getScrollY() - curScreenY) / mHeight;

            canvas.save();

            camera.save();
            camera.rotateX(degree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
            canvas.concat(matrix);
            drawChild(canvas, getChildAt(i), getDrawingTime());
            canvas.restore();


        }
    }

    private void addIndexView() {

        //向下滑动时候,将第一个移动到最后一个的位置
        if (!isUp ) {
            View childAt = getChildAt(0);
            ViewGroup parent = (ViewGroup) childAt.getParent();
            parent.removeView(childAt);
            addView(childAt, getChildCount());

            //想上滑动时候,就将最后一个移动到第一个的位置
        } else if (isUp) {
            View childAt = getChildAt(getChildCount() - 1);
            ViewGroup parent = (ViewGroup) childAt.getParent();
            parent.removeView(childAt);
            addView(childAt, 0);
        }

    }
}
