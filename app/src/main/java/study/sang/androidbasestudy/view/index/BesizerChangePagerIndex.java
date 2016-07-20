package study.sang.androidbasestudy.view.index;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/7/20 14:41
 */
public class BesizerChangePagerIndex extends BesizerPagerIndex {


    private boolean isMoveToRight = true;
    private float startLocation = currentIndex * maxChildWidth;
    private float endLocation = startLocation + maxChildWidth;

    public BesizerChangePagerIndex(Context context) {
        super(context);
    }

    public BesizerChangePagerIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BesizerChangePagerIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
