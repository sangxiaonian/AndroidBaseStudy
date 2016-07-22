package study.sang.androidbasestudy.view.image3DView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
    private int currentIndex = 2;
    private int index;

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
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        scrollTo(0, currentIndex * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            child.layout(0, childTop, getMeasuredWidth(), childTop + getMeasuredHeight());
            childTop += mHeight;
        }


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
        addIndexView(toIndex);
        if (getChildCount() == 0) return;
        index = toIndex;
//        if (index>=getChildCount()){
//            index = getChildCount();
//        }else if (index<0){
//            index = 0;
//        }

        if (!scroller.isFinished()) {
            scroller.abortAnimation();
            scrollTo(0, mHeight * currentIndex);
        }

        if (index > currentIndex) {
            if (index >= getChildCount()) {
                scrollTo(0, mHeight * (getChildCount()-2));
            }
                scroller.startScroll(0, scroller.getCurrY(), 0,- mHeight, 1000);


        } else if (index<currentIndex){

            if (index<0){
                scrollTo(0, mHeight );
            }
            scroller.startScroll(0, scroller.getCurrY(), 0, -mHeight, 1000);

        }
        currentIndex = toIndex;
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    private void addIndexView(int index) {
        if (index >= currentIndex&&index>=getChildCount()) {
            View childAt = getChildAt(0);
            ViewGroup parent = (ViewGroup) childAt.getParent();
            parent.removeView(childAt);
            addView(childAt, getChildCount());
        } else if (index < currentIndex) {
            View childAt = getChildAt(getChildCount() - 1);
            ViewGroup parent = (ViewGroup) childAt.getParent();
            parent.removeView(childAt);
            addView(childAt, 0);
        }

    }
}
