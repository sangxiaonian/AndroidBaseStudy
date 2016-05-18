package study.sang.androidbasestudy.viewpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

import study.sang.androidbasestudy.utils.JLog;

/**
 * 用于ViewPager的渐变动画,用于三个页面
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {

    //默认透明度
    private static final float DEFAULT_MIN_ALPHA=0.5f;
    //最小透明度
    private float mMinAlpha= DEFAULT_MIN_ALPHA;

    @Override
    public void transformPage(View page, float position) {


        JLog.i( "aaaaa"+page.getId()+"bbbb"+position);
        if (position<-1){
            page.setAlpha(mMinAlpha);
        }else if (position<=1){
            if (position<0){
                float factor = mMinAlpha+(1-mMinAlpha)*(1+position);
                page.setAlpha(factor);
            }else {
                float factor = mMinAlpha+(1-mMinAlpha)*(1-position);
                page.setAlpha(factor);
            }
        }else {
            page.setAlpha(mMinAlpha);
        }
    }
}
