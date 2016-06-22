package study.sang.androidbasestudy.view.ViewDrag;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import study.sang.androidbasestudy.utils.JLog;
import study.sang.androidbasestudy.utils.ToastUtil;

/**
 * Description：一个自定义的ViewDragHelper的联系类,敬肯多的使用方法,因此使用了不同的控件
 *
 * @Author：桑小年
 * @Data：2016/6/22 12:09
 */
public class CustomDragLayout extends ViewGroup {

    private ViewDragHelper dragHelper;

    private View drag1, drag3, drag2, drag4;

    private Point origPoint = new Point();

    public CustomDragLayout(Context context) {
        super(context);
        initView();
    }

    public CustomDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {


            //是否捕捉子控件,只有返回true时候,才会捕捉到子view,执行一系列的操作
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child == drag3) {
                    ToastUtil.showToast(getContext(), "必须从父控件的边缘开始才能控制哦");
                }
                JLog.i(child.toString());


                //只有2 3可以移动
                return child == drag2 || child == drag1 || child == drag4;
            }

            //限制横向移动范围
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left <= getPaddingLeft()) {
                    left = getPaddingLeft();
                }
                int anInt = getWidth() - child.getMeasuredWidth() - getPaddingRight();
                if (left >= anInt)
                    left = anInt;


                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top <= getPaddingTop()) {
                    top = getPaddingTop();
                }
                int anInt = getHeight() - child.getMeasuredHeight() - getPaddingBottom();
                if (top >= anInt)
                    top = anInt;
                return top;


            }

            //松开按钮释放的时候
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == drag2) {
                    //用一定的速度将控件移动到指定位置,并且会调用continueSettling方法,只有continueSettling为true时候才会移动控件,否则不会移动,其内部使用的还是scoll方法,所以会调用 continueSettling方法
                    dragHelper.settleCapturedViewAt(origPoint.x, origPoint.y);
                    postInvalidate();
                }
            }


            //在从父View的边缘开始滑动的时候,才能调用
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                //绕过tryCaptureView直接获取到View,并且执行tryCaptureView之外的所有方法;
                dragHelper.captureChildView(drag3, pointerId);
            }


            //由于以上几个方法都是在OnTouch中被调用的,所以当子View是button或者有click属性的时候,就会失效,不能被执行
            //而下面两个方法则是在shouldInterceptTouchEvent中调用,因此重写这两个方法后,字控件就能获取相应的点击事件了
            @Override
            public int getViewHorizontalDragRange(View child) {


                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
        //设置执行的边界,并且只会调用onEdgeDragStarted
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);

    }


    @Override
    public void computeScroll() {

        if (dragHelper.continueSettling(true)) {
            postInvalidate();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        origPoint.x = drag2.getLeft();
        origPoint.y = drag2.getTop();
    }

    //在填充完毕后调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        drag1 = getChildAt(0);
        drag2 = getChildAt(1);
        drag3 = getChildAt(2);
        drag4 = getChildAt(3);
    }
}
