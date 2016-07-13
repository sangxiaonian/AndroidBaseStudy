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

/**
 * Description：一个自定义的DrawLayout方法
 *
 * @Author：桑小年
 * @Data：2016/6/22 16:39
 */
public class CustomDrawLayout extends LinearLayout {


    private View centerView, leftView;
    private ViewDragHelper helper;

    private final int LAYOUT = HORIZONTAL;
    private ViewGroup.LayoutParams leftParams;
    private ViewGroup.LayoutParams centerParams;
    private int wideSize;
    private int heightSoze;

    private Point oripoint = new Point();
    private Point resultPoing = new Point(-20,0);

    public CustomDrawLayout(Context context) {
        super(context);
        setOrientation(LAYOUT);
        initView();
    }

    public CustomDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LAYOUT);
        initView();
    }

    public CustomDrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LAYOUT);
        initView();
    }

    private void initView() {
        helper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == leftView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left>=-20){
                    left=-20;
                }
                if (left<=-child.getMeasuredWidth()){
                    left=-child.getMeasuredWidth();
                }

                return left;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                helper.captureChildView(leftView, pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                JLog.i("xvel:"+xvel+"yvel:"+yvel);

                int width = releasedChild.getWidth();
               boolean open= releasedChild.getLeft()+width>width/2;

                if (open){
                    //出现
                    helper.settleCapturedViewAt(resultPoing.x,resultPoing.y);
                }else{
                    //隐藏
                    helper.settleCapturedViewAt(oripoint.x,oripoint.y);

                }
                postInvalidate();
            }
        });

        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    @Override
    public void computeScroll() {
       if (helper.continueSettling(true)){
           postInvalidate();
       }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftView = getChildAt(1);
        centerView = getChildAt(0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        wideSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSoze = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(wideSize, heightSoze);
        JLog.i(leftView.toString());


        MarginLayoutParams lp = (MarginLayoutParams)
                leftView.getLayoutParams();

        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                lp.leftMargin + lp.rightMargin,
                lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);
        leftView.measure(drawerWidthSpec, drawerHeightSpec);

        JLog.i("drawerHeightSpec:"+drawerHeightSpec+"heitht:"+leftView.getMeasuredHeight());


        lp = (MarginLayoutParams) centerView.getLayoutParams();
        final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                wideSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
        final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSoze - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
        centerView.measure(contentWidthSpec, contentHeightSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


        View menuView = leftView;
        View contentView = centerView;

        MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin + contentView.getMeasuredWidth(),
                lp.topMargin + contentView.getMeasuredHeight());

        lp = (MarginLayoutParams) menuView.getLayoutParams();

        final int menuWidth = menuView.getMeasuredWidth();
        int childLeft = -menuWidth ;
        menuView.layout(childLeft, lp.topMargin, childLeft + menuWidth,
                lp.topMargin + menuView.getMeasuredHeight());

        oripoint.x=menuView.getLeft();
        oripoint.y=menuView.getTop();

    }
}
