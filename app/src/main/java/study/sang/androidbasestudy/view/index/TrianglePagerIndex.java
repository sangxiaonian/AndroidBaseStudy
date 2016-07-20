package study.sang.androidbasestudy.view.index;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import study.sang.androidbasestudy.utils.DeviceUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/19 15:45
 */
public class TrianglePagerIndex extends BasePagerIndex {

    private Path mPath;

    public TrianglePagerIndex(Context context) {
        super(context);
    }

    public TrianglePagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrianglePagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mPath = new Path();
    }

    @Override
    public void drawIndex(Canvas canvas, int maxChildWidth) {
        super.drawIndex(canvas, maxChildWidth);
        mPath.moveTo(maxChildWidth*currentIndex+maxChildWidth/3,getHeight());
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth*2/3,getHeight());
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth/2,getHeight()- DeviceUtils.dip2px(getContext(),8));
        mPath.lineTo(maxChildWidth*currentIndex+maxChildWidth/3,getHeight());
        canvas.drawPath(mPath,indexPaint);
    }
}
