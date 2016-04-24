//package study.sang.androidbasestudy.view.image3DView;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Camera;
//import android.graphics.Canvas;
//import android.graphics.Matrix;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
///**
// * 作者： ${桑小年} on 2016/4/24.
// * 努力，为梦长留
// */
//public class Image3DView extends ImageView {
//
//    private Bitmap mBitmap;
//    private Camera mCamera;
//    private Matrix matrix;
//    /**
//     * 旋转角度的基准值
//     */
//    private static final float BASE_DEGREE = 50f;
//    /**
//     * 旋转深度的基准值
//     */
//    private static final float BASE_DEEP = 150f;
//    //图片的宽度
//    private int mWidth;
//
//    /**父控件的宽度*/
//    private int mlayoutWidth;
//
//
//    public Image3DView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        mCamera = new Camera();
//        matrix = new Matrix();
//    }
//
//    /**
//     *  获取图片的宽高、和背景
//     */
//    public void initImageViewBitmap(){
//
//        if (mBitmap ==null){
//            setDrawingCacheEnabled(true);\
//            buildDrawingCache();
//            mBitmap = getDrawingCache();
//        }
//        mWidth = getWidth()+Image3Dswitch.IMAGE_PADDING*2;
//        mlayoutWidth = Image3Dswitch.mWidth;
//    }
//
//    private int mIndex;
//    private int mScrollx;
//    public void setRoatateData(int index,int scrollX){
//        mIndex = index;
//        mScrollx = scrollX;
//    }
//
//    @Override
//    public void setImageResource(int resId) {
//        super.setImageResource(resId);
//        mBitmap=null;
//        initImageViewBitmap();
//    }
//
//    @Override
//    public void setImageDrawable(Drawable drawable) {
//        super.setImageDrawable(drawable);
//        mBitmap=null;
//        initImageViewBitmap();
//    }
//
//    @Override
//    public void setImageURI(Uri uri) {
//        super.setImageURI(uri);
//        mBitmap = null;
//        initImageViewBitmap();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (mBitmap==null){
//            super.onDraw(canvas);
//        }else {
//            if (isIma)
//        }
//
//
//    }
//
//    private boolean isImageVisible(){
//        boolean visible = false;
//        switch (mIndex){
//            case 0:
//                if (mScrollx<(mlayoutWidth-mWidth)/2-mWidth){
//                    visible=true;
//                }else {
//                    visible=false;
//                }
//                break;
//            case
//        }
//    }
//}
