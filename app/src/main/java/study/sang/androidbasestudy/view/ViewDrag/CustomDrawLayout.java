package study.sang.androidbasestudy.view.ViewDrag;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：一个自定义的DrawLayout方法
 *
 * @Author：桑小年
 * @Data：2016/6/22 16:39
 */
public class CustomDrawLayout extends ViewGroup {

    private View left,//左侧控件
            center; //中间显示的控件

    private ViewDragHelper dragHelper;
    private int centerWidth;

    public CustomDrawLayout(Context context) {
        super(context);
        intiView();
    }

    public CustomDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiView();
    }

    public CustomDrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }


    private void intiView() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                JLog.i(child.toString());
                return child == left;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                dragHelper.captureChildView(left, pointerId);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left >= 0) {
                    left = 0;
                }
                int l = -child.getMeasuredWidth();
                if (left <= l) {
                    left = l;
                }
                return left;
            }

//            @Override
//            public int getViewHorizontalDragRange(View child) {
//                return child.getMeasuredWidth();
//            }
        });
//        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed,l,t,r,b);
        int centerX=0 ,centerY=0;
        centerX = center.getLeft();
        centerY = center.getTop();
        center.layout(centerX,centerY,centerX+center.getMeasuredWidth(),centerY+center.getMeasuredHeight());
        int leftX = getLeft()-left.getMeasuredWidth();
        int leftY = left.getHeight();
        JLog.i("leftX:"+leftX+"centerX:"+centerX+"getLeft():"+getLeft());
        left.layout(getLeft()-left.getMeasuredWidth(),left.getTop(),getLeft(),left.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(center, widthMeasureSpec, heightMeasureSpec);
        centerWidth = center.getMeasuredWidth();
        measureChild(left, widthMeasureSpec, heightMeasureSpec);
        int leftWidth = left.getMeasuredWidth();
        JLog.i("centerWidth:"+centerWidth+"leftWidth:"+leftWidth);
        setMeasuredDimension(leftWidth+centerWidth,heightMeasureSpec);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        left = getChildAt(0);
        center = getChildAt(1);
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
}
