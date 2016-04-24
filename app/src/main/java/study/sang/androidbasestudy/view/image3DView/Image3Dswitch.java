//package study.sang.androidbasestudy.view.image3DView;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//import android.view.ViewGroup;
//import android.widget.Scroller;
//
//import study.sang.androidbasestudy.error.MyThrowError;
//import study.sang.androidbasestudy.utils.JLog;
//
///**
// * 这是一个用于进行3D图片的类，这个类用做父控件
// * <p/>
// * 作者： ${桑小年} on 2016/4/24.
// * 努力，为梦长留
// */
//public class Image3Dswitch extends ViewGroup {
//
//    /***
//     * 子控件的数量，子控件用来显示图片
//     */
//    private int mCount;
//    /**
//     * 是否强制重新布局
//     */
//    private boolean forceToRelayout;
//
//    /**
//     * 控件的宽度
//     */
//    public static int mWidth;
//
//    /**
//     * 控件的高度
//     */
//    public int mHeight;
//
//    /**
//     * 每张图片的宽度
//     */
//    private int mImageWidth;
//
//    /**
//     * 当前显示的图片
//     */
//    private int mCurrentImage;
//
//    private Scroller msScroller;
//
//    private int[] mItems;
//
//
//    /**
//     * 图片左右两边的空白间距
//     */
//    public static final int IMAGE_PADDING = 10;
//    private static final int TOUCH_STATE_REST = 0;
//    private static final int TOUCH_STATE_SCROLLING = 1;
//
//    private VelocityTracker mVelocityTracker;
//
//    public Image3Dswitch(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        if (changed || forceToRelayout) {
//            mCount = getChildCount();
//            if (mCount < 5) {
//                JLog.i("图片数量不能小于5");
//                throw new MyThrowError("图片数量不能小于5");
//            }
//
//            //获取控件的宽高
//            mWidth = getMeasuredWidth();
//            mHeight = getMeasuredHeight();
//
//            //设置图片的宽度为这个控件宽度的百分之六十
//            mImageWidth = (int) (mWidth * 0.6);
//
//            if (mCurrentImage >= 0 && mCurrentImage < mCount) {
//                //停止正在进行的动画
//                msScroller.abortAnimation();
////                将图片滑动到最开始位置
//                setScrollX(0);
//
//                int left = -mImageWidth * 2 + (mWidth - mImageWidth) / 2;
//
//                //分别获取每个位置上应该显示的图片的下标
//                int[] items = {getIndexForItem(1), getIndexForItem(2), getIndexForItem(3), getIndexForItem(4), getIndexForItem(5)};
//
//                mItems = items;
//
//                //为每个图片设定位置
//                for (int i = 0;i<items.length;i++){
//                    Image3DView childView = (Image3DView) getChildAt(i);
//                    childView.layout(left+IMAGE_PADDING,0,left+mImageWidth-IMAGE_PADDING,mHeight);
//                    changed.initImageVuiewBitmap();
//                    left = left+mImageWidth;
//                }
//                refreshImageShowing();
//
//            }
//            forceToRelayout = false;
//
//        }
//    }
//
//    private float mLastMotionX;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (msScroller.isFinished()){
//            if (mVelocityTracker==null){
//                mVelocityTracker = VelocityTracker.obtain();
//            }
//
//            mVelocityTracker.addMovement(event);
//
//            int action = event.getAction();
//            float X = event.getX();
//
//            switch (action){
//                case MotionEvent.ACTION_DOWN:
//                    mLastMotionX = X;
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                     int disx = (int) (mLastMotionX-X);
//                    mLastMotionX = X;
//                    //移动图片
//                    scrollBy(disx,0);
//
//                    //刷新图片状态
//                    refreshImageShowing();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    mVelocityTracker.computeCurrentVelocity(1000);
//                    int velocityX = (int) mVelocityTracker.getXVelocity();
//                    if ()
//
//            }
//
//        }
//
//        return super.onTouchEvent(event);
//    }
//
//    /**
//     * 刷新所有图片的显示状态，包括当前的旋转角度。
//     */
//    private void refreshImageShowing() {
//
//
//            for (int i = 0; i < mItems.length; i++) {
//                Image3DView childView = (Image3DView) getChildAt(mItems[i]);
//                childView.setRotateData(i, getScrollX());
//                childView.invalidate();
//            }
//
//    }
//
//    private int getIndexForItem(int item) {
//        int index = -1;
//        index = mCurrentImage + item - 3;
//        while (index < 0) {
//            index = index + mCount;
//        }
//        while (index > mCount - 1) {
//            index = index - mCount;
//        }
//        return index;
//
//    }
//}
