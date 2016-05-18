package study.sang.androidbasestudy.viewpager.transformer;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import study.sang.androidbasestudy.utils.JLog;

/**
 * ViewPager的旋转动画，单个页面
 */
public class RotaPagerTransformer implements ViewPager.PageTransformer {

    private static final float ROA_BA=20;
    private float mRoat;

    @Override
    public void transformPage(View page, float position) {

        if (position<-1){//[-Infinity,-1)

            page.setRotation(0);

        }else if (position<=1){ //[-1,1]
            mRoat = (ROA_BA * position);
            ViewCompat.setPivotX(page, page.getMeasuredWidth() * 0.5f);
            ViewCompat.setPivotY(page, page.getMeasuredHeight());
            ViewCompat.setRotation(page, mRoat);
        }else { //大于1
            ViewCompat.setRotation(page, 0);
        }

    }
}
