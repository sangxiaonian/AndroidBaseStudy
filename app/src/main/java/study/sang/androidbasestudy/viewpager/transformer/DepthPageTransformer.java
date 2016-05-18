package study.sang.androidbasestudy.viewpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by ping on 2016/5/18.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {


    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {

        if (position<-1){
            page.setAlpha(0);
        }else if (position<=1){//[-1,1]

            if (position>=0){//[0,1]

                page.setAlpha(MIN_SCALE+(1-MIN_SCALE)*(1-position));
                page.setTranslationX(page.getMeasuredWidth()*-position);
                page.setScaleX(MIN_SCALE+(1-MIN_SCALE)*(1-position));
                page.setScaleY(MIN_SCALE+(1-MIN_SCALE)*(1-position));
            }else {//[-1,0]

                page.setAlpha(1);
                page.setTranslationX(1);
                page.setScaleX(1);
                page.setScaleY(1);
            }
        }else {
            page.setAlpha(0);
        }


    }
}
