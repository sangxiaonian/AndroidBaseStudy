package study.sang.androidbasestudy.viewpager.transformer;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager的旋转动画，三个页面
 */
public class RotaPagersTransformer implements ViewPager.PageTransformer {

    private static final float ROA_BA=20;
    private float mRoat;

    @Override
    public void transformPage(View page, float position) {

        if (position<-1){//[-Infinity,-1)
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
            page.setRotation(-ROA_BA);

        }else if (position<=1){ //[-1,1]

            if (position<0){
                mRoat = (ROA_BA * position);
                ViewCompat.setPivotX(page, page.getMeasuredWidth() * 0.5f*(1-position));
                ViewCompat.setPivotY(page, page.getMeasuredHeight());
                ViewCompat.setRotation(page, mRoat);
            }else {
                mRoat = (ROA_BA * position);
                ViewCompat.setPivotX(page, page.getMeasuredWidth() * 0.5f*(1-position));
                ViewCompat.setPivotY(page, page.getMeasuredHeight());
                ViewCompat.setRotation(page, mRoat);
            }


        }else { //大于1
            page.setPivotX(0);
            page.setPivotY(page.getHeight());
            page.setRotation(ROA_BA);
        }

    }
}
