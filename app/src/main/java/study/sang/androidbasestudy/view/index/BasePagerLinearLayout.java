package study.sang.androidbasestudy.view.index;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import study.sang.androidbasestudy.utils.DeviceUtils;
import study.sang.androidbasestudy.utils.JLog;

/**
 * Description：绘制PagerIndex所使用的最基本的 布局文件,用来初始化并且获取一些基本数据;
 *
 * @Author：桑小年
 * @Data：2016/7/18 10:17
 */
public abstract class BasePagerLinearLayout extends LinearLayout {

    public List<View> mDatas = new ArrayList<>();
    public int startx = 0;


    protected   float distance = DeviceUtils.dip2px(getContext(), 5);//子控件间的默认距离
    private float height = DeviceUtils.dip2px(getContext(), 1);//该控件的默认高度
    public int endx;

    //改控件的宽度
    public float width;
    /**
     * 单个控件的总长度
     */
    public int maxChildWidth;

    public BasePagerLinearLayout(Context context) {
        super(context);
        initView();
    }

    public BasePagerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BasePagerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        height = DeviceUtils.dip2px(getContext(), 5);
        int measure = (int) height;
        LinearLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            width = (int) Math.max(child.getMeasuredWidth()+2*distance , width);
            measure = Math.max(getPaddingBottom() + getPaddingTop() + getMeasuredHeight(), child.getMeasuredHeight());
        }

        if (mode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = measure;
        }

        JLog.i(getMeasuredHeight()+"======="+measure);

        maxChildWidth = width;
        setMeasuredDimension(width * getChildCount(), heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childwidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout((int) (childwidth+(maxChildWidth-child.getWidth()-2*distance)/2 + distance), child.getTop(),   (childwidth + maxChildWidth), child.getTop() + child.getHeight());
            childwidth += maxChildWidth ;
        }

        View parent = (View) getParent();
        width = Math.min(getWidth(), parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制分割线
        drawCutLine(canvas,maxChildWidth);

        /**
         * 绘制下标
         */
        drawIndex(canvas,maxChildWidth);
    }

    /**
     * 绘制下标
     *
     * @param canvas
     * @param maxChildWidth 每个子控件的宽度
     */
    public void drawIndex(Canvas canvas, int maxChildWidth) {
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     * @param maxChildWidth 每个子控件的宽度
     */
    public void drawCutLine(Canvas canvas, int maxChildWidth) {
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                endx = (int) event.getX();
                scrollBy(startx - endx, 0);
                startx = endx;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                View parent = (View) getParent();
                int scrollX = getScrollX();
                if (scrollX < 0) {
                    scrollTo(0, 0);
                } else {
                    JLog.i(getWidth() + "======" + parent.getWidth() + "==" + (getWidth() > parent.getWidth()) + "===" + width);
                    if (width == getWidth()) {
                        scrollTo(0, 0);
                    } else if (getWidth() - scrollX < width) {
                        scrollTo((int) (getWidth() - width + distance), 0);
                    }

                }
                break;
        }

        return true;
    }


    private float LastX, LastY, distanceX, distanceY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startx = (int) ev.getX();
                LastX = ev.getX();
                LastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = Math.abs(ev.getX() - LastX);
                distanceY = Math.abs(ev.getY() - LastY);
                if (distanceX > DeviceUtils.getMinScroll(getContext())) {
                    return true;
                } else {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 向控件中添加子控件
     *
     * @param indexName 子控件的显示的数据
     */
    public abstract void addIndexView(final String indexName);

    /**
     * 向控件中添加子控件
     *
     * @param child 添加的子控件
     */
    public  void addIndexView(View child) {
        mDatas.add(child);
        child.setTag(getChildCount()-1);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               moveIndex((Integer) v.getTag());
            }
        });
    }

    public abstract void moveIndex(final int toIndex) ;

}
